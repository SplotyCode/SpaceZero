package me.david.spacezero.gui;

import me.david.spacezero.SpaceZero;
import me.david.spacezero.filesystem.IComponent;
import me.david.spacezero.filesystem.IFolder;
import me.david.spacezero.filesystem.LinkedFolder;
import org.liquidengine.legui.component.*;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.image.BufferedImage;
import org.liquidengine.legui.image.Image;

import java.util.HashMap;

public class FileExplorer {

    private ScrollablePanel pane = new ScrollablePanel();
    private IFolder currentFolder;
    private HashMap<String, Image> cashedImages = new HashMap<>();

    private static float ITEM_WEIGHT = 60, ITEM_HEIGHT = 40, IMAGE_SIZE = 50;

    public FileExplorer(Panel frame) {
        pane.getContainer().setSize(300, 300);
        pane.getContainer().getListenerMap().addListener(MouseClickEvent.class, event -> {
            if (event.getAction() == MouseClickEvent.MouseClickAction.CLICK) {
                int i = (int) ((event.getAbsolutePosition().y-5) / 20);
                if (currentFolder != null && i >= currentFolder.getItems().length - 1) {
                    IComponent component = currentFolder.getItems()[i];
                    if (component instanceof IFolder) {
                        currentFolder = (IFolder) component;
                        cashedImages.clear();
                    } else System.out.println("File - " + component.getName());
                }
            }
        });
        frame.add(pane);
    }

    public void render(int w, int h) {
        pane.setSize(w / 3, h / 3.5f);
        pane.getContainer().clearChildComponents();
        if (currentFolder == null) {
            if (SpaceZero.getInstance().getProject() == null) {
                pane.getContainer().add(new Label("No Project Opened!"));
            } else {
                currentFolder = SpaceZero.getInstance().getProject().getBase();
            }
            pane.getContainer().setSize(w / 3, h / 3.5f);
        } else {
            int y = (int) (0.01f * h);
            int x = 4;
            for (IComponent component : currentFolder.getItems()) {
                ImageView image = new ImageView(getImage(getImagePath(component)));
                image.setPosition(x + (ITEM_WEIGHT / 2 - IMAGE_SIZE / 2), y);
                image.setSize(IMAGE_SIZE, IMAGE_SIZE);
                Label text = new Label(component.getName());
                text.setPosition(x, y + IMAGE_SIZE + 4);
                pane.getContainer().add(image);
                pane.getContainer().add(text);
                x += ITEM_WEIGHT;
                if (x + ITEM_WEIGHT + 4 >= w / 3) {
                    x = 4;
                    y += ITEM_HEIGHT + 4 + 20 + 4;
                }
            }
            pane.getContainer().setSize(w / 3, Math.max(y, h / 3.5f));
        }
    }

    private String getImagePath(IComponent component) {
        if (component instanceof IFolder) {
            if (component instanceof LinkedFolder) {
                return "icons/explorer/folder_linked.png";
            }
            return "icons/explorer/folder.png";
        }
        return "icons/files/_blank.png";
    }

    private Image getImage(String path) {
        Image image = cashedImages.get(path);
        if (image == null) {
            image = new BufferedImage(path);
            cashedImages.put(path, image);
        }
        return image;
    }

}
