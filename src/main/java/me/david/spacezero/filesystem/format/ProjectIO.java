package me.david.spacezero.filesystem.format;

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

            zs.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Project();
    }

}
