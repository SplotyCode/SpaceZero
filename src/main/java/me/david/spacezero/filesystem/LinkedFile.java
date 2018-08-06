package me.david.spacezero.filesystem;

import java.io.*;

public class LinkedFile extends LinkedComponent implements IFile {

    public LinkedFile(File file) {
        super(file);
    }

    @Override
    public InputStream getStream() throws IOException {
        return new FileInputStream(file);
    }

}
