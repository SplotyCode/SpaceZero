package me.david.spacezero.filesystem;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ZeroComponent implements IComponent {

    @Getter private String name;
    private IFolder parent;

    @Override
    public IFolder getParent() {
        return parent;
    }

}
