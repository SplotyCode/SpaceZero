package me.david.spacezero.filesystem;

import java.io.File;

public class LinkedFolder extends LinkedComponent implements IFolder {

    private LinkedComponent[] items;

    public LinkedFolder(final File file) {
        super(file);
        if (!file.isDirectory()) throw new IllegalArgumentException("File must be an Directory");
    }

    @Override
    public LinkedComponent[] getItems() {
        if (items == null) {
            File[] files = file.listFiles();
            if (files == null) return new LinkedComponent[0];

            LinkedComponent[] items = new LinkedComponent[files.length];
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                items[i] = file.isDirectory() ? new LinkedFolder(file) : new LinkedFile(file);
            }
            this.items = items;
        }
        return items;
    }

}
