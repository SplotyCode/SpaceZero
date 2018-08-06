package me.david.spacezero.filesystem.format;

import lombok.Getter;
import me.david.spacezero.filesystem.ZeroFolder;

import java.io.IOException;
import java.util.zip.ZipFile;

public class Project {

    @Getter private ZeroFolder base;
    @Getter private ProjectData data;
    @Getter private ProjectStatistics statistics;
    @Getter private ZipFile zipFile;

    public Project(ZeroFolder base, ProjectData data, ProjectStatistics statistics, ZipFile zipFile) {
        this.base = base;
        this.data = data;
        this.statistics = statistics;
        this.zipFile = zipFile;
    }

    public void refeshFileSystem() {
        try {
            base = ProjectIO.loadFileSystem(zipFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
