package me.david.spacezero.filesystem;

import lombok.Getter;

import java.util.Set;

public class ZeroFolder extends ZeroComponent implements IFolder {

    @Getter private Set<IComponent> items;

    public ZeroFolder(String name, IFolder parent, Set<IComponent> items) {
        super(name, parent);
        this.items = items;
    }

    public void addItem(IComponent component) {
        items.add(component);
    }

}
