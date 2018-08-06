package me.david.spacezero.filesystem;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZeroFile extends ZeroComponent implements IFile {

    private ZipFile zipFile;
    private ZipEntry entry;

    public ZeroFile(ZipEntry entry, String name, IFolder parent, ZipFile zipFile) {
        super(name, parent);
        this.entry = entry;
        this.zipFile = zipFile;
    }

    @Override
    public InputStream getStream() throws IOException {
        return zipFile.getInputStream(entry);
    }
}
