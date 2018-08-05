package me.david.spacezero.filesystem;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZeroFile extends ZeroComponent implements IFile {

    private ZipFile zipFile;

    public ZeroFile(ZipEntry entry, IFolder parent, ZipFile zipFile) {
        super(entry, parent);
        this.zipFile = zipFile;
    }

    @Override
    public InputStream getStream() throws IOException {
        return zipFile.getInputStream(entry);
    }
}
