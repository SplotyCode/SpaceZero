package me.david.spacezero.filesystem;

import lombok.Getter;

import java.util.zip.ZipEntry;

public class ZeroFolder extends ZeroComponent implements IFolder {

    @Getter private IComponent[] items;

    public ZeroFolder(ZipEntry entry, IFolder parent, IComponent[] items) {
        super(entry, parent);
        this.items = items;
    }

}
