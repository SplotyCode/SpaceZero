package me.david.spacezero.filesystem;

import java.io.File;

public class ExternalFolder extends ExternalComponent implements IFolder {

    public ExternalFolder(final File file) {
        super(file);
        if (!file.isDirectory()) throw new IllegalArgumentException("File must be an Directory");
    }

    @Override
    public ExternalComponent[] getItems() {
        File[] files = file.listFiles();
        if (files == null) return new ExternalComponent[0];

        ExternalComponent[] items = new ExternalComponent[files.length];
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            items[i] = file.isDirectory() ? new ExternalFolder(file) : new ExternalFile(file);
        }

        return items;
    }

}
