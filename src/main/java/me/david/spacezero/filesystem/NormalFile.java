package me.david.spacezero.filesystem;

import lombok.Getter;

import java.io.*;

public class NormalFile extends NormalComponent implements ZeroFile {

    public NormalFile(File file) {
        super(file);
    }

    @Override
    public InputStream getStream() throws IOException {
        return new FileInputStream(file);
    }

}
