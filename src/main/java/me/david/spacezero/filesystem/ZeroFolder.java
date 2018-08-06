package me.david.spacezero.filesystem;

import java.util.Set;

public class ZeroFolder extends ZeroComponent implements IFolder {

    private Set<IComponent> items;

    public ZeroFolder(String name, IFolder parent, Set<IComponent> items) {
        super(name, parent);
        this.items = items;
    }

    public void addItem(IComponent component) {
        items.add(component);
    }

    @Override
    public IComponent[] getItems() {
        return items.toArray(new IComponent[items.size()]);
    }
}
