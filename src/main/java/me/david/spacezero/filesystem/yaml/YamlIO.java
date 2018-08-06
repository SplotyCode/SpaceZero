package me.david.spacezero.filesystem.yaml;

import me.david.spacezero.filesystem.format.Project;

import java.io.*;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class YamlIO {

    public static YamlComponent load(InputStream stream, YamlComponent component) {
        component.read(YamlConfiguration.loadConfiguration(stream));
        return component;
    }

    public static YamlComponent load(String name, ZipFile file, YamlComponent component) {
        try {
            return load(file.getInputStream(new ZipEntry(name)), component);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static YamlComponent load(String file, Project project, YamlComponent component) {
        return load(file, project.getZipFile(), component);
    }

    public static YamlComponent load(ZipFile file, ProjectComponent component) {
        return load(component.getFileName(), file, component);
    }

    public static YamlComponent load(Project project, ProjectComponent component) {
        return load(component.getFileName(), project, component);
    }

    public static void write(OutputStream stream, YamlComponent component) {
        YamlConfiguration yaml = createEmpty();
        component.write(yaml);
        try {
            stream.write(yaml.saveToString().getBytes(Charset.forName("UTF-8")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void write(String name, File zipFile, YamlComponent component) {
        try {
            ZipOutputStream stream = new ZipOutputStream(new FileOutputStream(zipFile));
            stream.putNextEntry(new ZipEntry(name));
            write(stream, component);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void write(String file, Project project, YamlComponent component) {
        write(file, project.getFile(), component);
    }

    public static void write(File file, ProjectComponent component) {
        write(component.getFileName(), file, component);
    }

    public static void write(Project project, ProjectComponent component) {
        write(component.getFileName(), project, component);
    }

    public static YamlConfiguration createEmpty() {
        return YamlConfiguration.loadConfiguration(new ByteArrayInputStream(new byte[0]));
    }

}
