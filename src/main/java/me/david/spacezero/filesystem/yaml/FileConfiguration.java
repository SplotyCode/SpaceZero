package me.david.spacezero.filesystem.yaml;

import java.io.*;
import java.nio.charset.Charset;

public abstract class FileConfiguration extends MemoryConfiguration {

    public FileConfiguration() {
        super();
    }


    public void save(File file) throws IOException {
        file.getParentFile().mkdirs();
        String data = saveToString();

        try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), Charset.forName("Utf-8"))) {
            writer.write(data);
        }
    }

    public abstract String saveToString();

    public void load(File file) throws IOException, InvalidConfigurationException {
        load(new FileInputStream(file));
    }

    public void load(InputStream stream) throws IOException, InvalidConfigurationException {
        load(new InputStreamReader(stream, Charset.forName("Utf-8")));
    }

    public void load(Reader reader) throws IOException, InvalidConfigurationException {

        StringBuilder builder = new StringBuilder();

        try (BufferedReader input = reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader)) {
            String line;

            while ((line = input.readLine()) != null) {
                builder.append(line);
                builder.append('\n');
            }
        }

        loadFromString(builder.toString());
    }

    public abstract void loadFromString(String contents) throws InvalidConfigurationException;
}