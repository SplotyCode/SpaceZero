package me.david.spacezero.gui;

import me.david.spacezero.SpaceZero;
import me.david.spacezero.filesystem.IComponent;
import me.david.spacezero.filesystem.IFolder;
import me.david.spacezero.filesystem.LinkedFolder;
import org.apache.commons.io.FilenameUtils;
import org.liquidengine.legui.component.*;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.image.BufferedImage;
import org.liquidengine.legui.image.Image;
import org.liquidengine.legui.style.color.ColorConstants;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class FileExplorer {

    private ScrollablePanel pane = new ScrollablePanel();
    private IFolder currentFolder;
    private IComponent selected;
    private HashMap<String, Image> cashedImages = new HashMap<>();

    private static final float ITEM_WEIGHT = 60, ITEM_HEIGHT = 40, IMAGE_SIZE = 50;
    private static final Set<String> SUPPORTED_FILETYPES = new HashSet<>(Arrays.asList("xml", "tiff", "sql", "exe", "css", "rar", "_blank", "h", "hpp", "iso", "java", "tga", "rtf", "jpg", "aiff", "wav", "flv", "php", "eps", "key", "aac", "mp4", "psd", "dxf", "dmg", "odt", "ai", "js", "ods", "less", "gif", "qt", "bmp", "dwg", "ppt", "rb", "zip", "py", "doc", "tgz", "html", "pdf", "cpp", "odf", "dat", "scss", "mp3", "xls", "dotx", "csv", "ots", "otp", "ott", "sass", "avi", "c", "_page", "txt", "mpg", "yml", "mid", "png", "ics", "xlsx"));

    public FileExplorer(Panel frame) {
        pane.getContainer().setSize(300, 300);
        pane.getContainer().getListenerMap().addListener(MouseClickEvent.class, event -> {
            if (event.getAction() == MouseClickEvent.MouseClickAction.CLICK) {
                float scrollX = pane.getHorizontalScrollBar().getCurValue(),
                    scrollY = pane.getVerticalScrollBar().getCurValue();
                int x = (int) ((event.getAbsolutePosition().x - scrollX - 4) / ITEM_WEIGHT);
                int y = (int) ((event.getAbsolutePosition().y - scrollY - 4) / (ITEM_HEIGHT + 4 + 20 + 4));
                int i = ((x + 1) * (y + 1)) - 1;
                System.out.println(i);
                if (currentFolder.getItems().length - 1 >= i)
                    clickComponent(null);
                else clickComponent(currentFolder.getItems()[0]);
            }
        });
        frame.add(pane);
    }

    private void clickComponent(IComponent component) {
        System.out.println(component + "a" + selected);
        if (component == null) {
            selected = null;
            return;
        }
        if (selected == null || selected != component) {
            selected = component;
            return;
        }
        if (selected instanceof IFolder) {
            currentFolder = (IFolder) component;
            return;
        }
        System.out.println("Opening: " + component.getName());
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
            if (currentFolder.getItems().length == 0) {
                //TODO center
                pane.getContainer().add(new Label("The Folder is empty!", 4, 4, 1, 1));
                pane.getContainer().setSize(w / 3, h / 3.5f);
                return;
            }
            int y = 4;
            int x = 4;
            for (IComponent component : currentFolder.getItems()) {
                ImageView image = new ImageView(getImage(getImagePath(component)));
                image.setPosition(x + (ITEM_WEIGHT / 2 - IMAGE_SIZE / 2), y);
                image.setSize(IMAGE_SIZE, IMAGE_SIZE);
                image.getListenerMap().addListener(MouseClickEvent.class, event -> {
                    System.out.println(event.getAction().name());
                    if (event.getAction() == MouseClickEvent.MouseClickAction.RELEASE)
                        clickComponent(component);
                });

                Label text = new Label(component.getName());
                if (selected == component) text.getTextState().setTextColor(ColorConstants.lightBlue());
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
        return "icons/files/" + (SUPPORTED_FILETYPES.contains(FilenameUtils.getExtension(component.getName())) ? FilenameUtils.getExtension(component.getName()) : "_blank") + ".png";
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
