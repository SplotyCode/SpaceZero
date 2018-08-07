package me.david.spacezero.gui;

import me.david.spacezero.SpaceZero;
import me.david.spacezero.filesystem.IComponent;
import me.david.spacezero.filesystem.IFolder;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.Label;
import org.liquidengine.legui.component.ScrollablePanel;
import org.liquidengine.legui.event.MouseClickEvent;

public class FileExplorer {

    private ScrollablePanel pane = new ScrollablePanel();
    private IFolder currentFolder;

    public FileExplorer(Frame frame) {
        pane.getContainer().setSize(300, 300);
        pane.getContainer().getListenerMap().addListener(MouseClickEvent.class, event -> {
            if (event.getAction() == MouseClickEvent.MouseClickAction.CLICK) {
                int i = (int) ((event.getAbsolutePosition().y-5) / 20);
                if (currentFolder != null) {
                    IComponent component = currentFolder.getItems()[i];
                    if (component instanceof IFolder) {
                        currentFolder = (IFolder) component;
                    } else System.out.println("File - " + component.getName());
                }
            }
        });
        frame.getComponentLayer().add(pane);
    }

    public void render(int w, int h) {
        pane.setSize(w / 5, h / 4);
        pane.getContainer().clearChildComponents();
        if (currentFolder == null) {
            if (SpaceZero.getInstance().getProject() == null) {
                pane.getContainer().add(new Label("No Project Opened!"));
            } else {
                currentFolder = SpaceZero.getInstance().getProject().getBase();
            }
        } else {
            int y = 5;
            for (IComponent component : currentFolder.getItems()) {
                Label text = new Label(component.getName());
                text.setPosition(0, y);
                text.getListenerMap().addListener(MouseClickEvent.class, mouseClickEvent -> {
                    System.out.println(component.getName());

                });
                pane.getContainer().add(text);
                y += 20;
            }
        }
    }

}
