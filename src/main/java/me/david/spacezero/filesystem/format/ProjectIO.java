package me.david.spacezero.filesystem.format;

import me.david.spacezero.filesystem.*;
import me.david.spacezero.filesystem.yaml.YamlConfiguration;
import me.david.spacezero.filesystem.yaml.YamlIO;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public final class ProjectIO {

    public static Project load(final File file) throws IOException {
        ZipFile zipFile = new ZipFile(file);

        ProjectData projectData = YamlIO.load(zipFile, new ProjectData());
        ProjectStatistics projectStatistics = YamlIO.load(zipFile, new ProjectStatistics());
        ProjectValues projectValues = YamlIO.load(zipFile, new ProjectValues());

        ZeroFolder baseFolder = loadFileSystem(zipFile);

        zipFile.close();

        return new Project(baseFolder, projectData, projectStatistics, projectValues, file);
    }

    private static IComponent loadFile(ZipEntry entry, String name, IFolder parent, ZipFile file) throws IOException {
        if (entry.getName().endsWith(".ld")) {
            return new LinkedFolder(new File(IOUtils.toString(file.getInputStream(entry), Charset.forName("UTF-8"))));
        }
        if (entry.getName().endsWith(".lf")) {
            return new LinkedFile(new File(IOUtils.toString(file.getInputStream(entry), Charset.forName("UTF-8"))));
        }
        return new ZeroFile(entry, name, parent, file);
    }

    static ZeroFolder loadFileSystem(ZipFile file) throws IOException {
        ZeroFolder folder = new ZeroFolder(null, null, null);

        while (file.entries().hasMoreElements()){
            ZipEntry entry = file.entries().nextElement();
            if (entry.isDirectory() || !entry.getName().startsWith("files/")) continue;
            String fileName = entry.getName().substring(6);

            String name = fileName.substring(fileName.lastIndexOf("/")+1);
            String[] path = fileName.lastIndexOf("/") != -1 ? fileName.substring(0, fileName.lastIndexOf("/")).split("/") : new String[0];

            //System.out.println("name=" + name + " path=" + file.substring(0, file.lastIndexOf(separator)));

            ZeroFolder dir = folder;
            for (String pa : path) {
                boolean found = false;
                for (IComponent comp : dir.getItems()){
                    if (comp instanceof ZeroFolder && comp.getName().equals(pa)) {
                        dir = (ZeroFolder) comp;
                        found = true;
                    }
                }
                if (!found) {
                    ZeroFolder directory = new ZeroFolder(pa, dir, new HashSet<>());
                    dir.addItem(directory);
                    dir = directory;
                }
            }

            dir.addItem(loadFile(entry, name, dir, file));
        }
        return folder;
    }

    private static ProjectData loadProjectData(InputStream stream) {
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(stream);
        return new ProjectData(yaml.getLong("time"), yaml.getString("user"), yaml.getString("name"));
    }

    private static ProjectStatistics loadProjectStatistics(InputStream stream) {
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(stream);
        return new ProjectStatistics(yaml.getInt("opens"), yaml.getLong("ontime"));
    }

    private static ProjectValues loadProjectValues(InputStream stream) {
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(stream);
        return new ProjectValues(yaml.getLong("linkedfiles"), yaml.getLong("linkedfolders"));
    }

    public static Project create(File file, String name) {
        ProjectData projectData = new ProjectData(System.currentTimeMillis(), System.getProperty("user.name"), name);
        ProjectStatistics projectStatistics = new ProjectStatistics();

        try {
            ZipOutputStream zs = new ZipOutputStream(new FileOutputStream(file));
            zs.putNextEntry(new ZipEntry("lock.lock"));

            zs.putNextEntry(new ZipEntry("project.yml"));
            YamlConfiguration project = YamlConfiguration.loadConfiguration(new ByteArrayInputStream(new byte[0]));
            project.set("name", projectData.getName());
            project.set("user", projectData.getUser());
            project.set("time", projectData.getCreationTime());
            zs.write(project.saveToString().getBytes("UTF-8"));

            zs.putNextEntry(new ZipEntry("statistics.yml"));
            YamlConfiguration statstics = YamlConfiguration.loadConfiguration(new ByteArrayInputStream(new byte[0]));
            statstics.set("ontime", projectStatistics.getOnTime());
            statstics.set("opens", projectStatistics.getOpens());
            zs.write(statstics.saveToString().getBytes("UTF-8"));

            zs.close();
            return load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
