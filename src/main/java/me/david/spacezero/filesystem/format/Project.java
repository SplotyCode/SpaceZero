package me.david.spacezero.filesystem.format;

import lombok.Getter;
import me.david.spacezero.filesystem.ZeroFolder;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipFile;

public class Project {

    @Getter private ZeroFolder base;
    @Getter private ProjectData data;
    @Getter private ProjectStatistics statistics;
    @Getter private ProjectValues values;
    @Getter private ZipFile zipFile;
    @Getter private File file;

    public Project(ZeroFolder base, ProjectData data, ProjectStatistics statistics,
                   ProjectValues values, File file) {
        this.base = base;
        this.data = data;
        this.statistics = statistics;
        this.values = values;
        try {
            this.zipFile = new ZipFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.file = file;
    }

    public void refeshFileSystem() {
        try {
            base = ProjectIO.loadFileSystem(zipFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
