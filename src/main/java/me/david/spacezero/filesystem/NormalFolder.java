package me.david.spacezero.filesystem;

import java.io.File;

public class NormalFolder extends NormalComponent implements ZeroFolder {

    public NormalFolder(final File file) {
        super(file);
        if (!file.isDirectory()) throw new IllegalArgumentException("File must be an Directory");
    }

    @Override
    public NormalComponent[] getItems() {
        File[] files = file.listFiles();
        if (files == null) return new NormalComponent[0];

        NormalComponent[] items = new NormalComponent[files.length];
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            items[i] = file.isDirectory() ? new NormalFolder(file) : new NormalFile(file);
        }

        return items;
    }

}
