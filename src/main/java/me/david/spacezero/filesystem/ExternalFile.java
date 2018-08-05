package me.david.spacezero.filesystem;

import java.io.*;

public class ExternalFile extends ExternalComponent implements IFile {

    public ExternalFile(File file) {
        super(file);
    }

    @Override
    public InputStream getStream() throws IOException {
        return new FileInputStream(file);
    }

}
