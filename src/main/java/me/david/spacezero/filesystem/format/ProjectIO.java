package me.david.spacezero.filesystem.format;

import me.david.spacezero.filesystem.yaml.YamlConfiguration;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public final class ProjectIO {

    public static Project load(final File file) {
        return new Project();
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
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Project();
    }

}
