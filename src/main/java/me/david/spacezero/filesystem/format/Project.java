package me.david.spacezero.filesystem.format;

import lombok.Getter;
import me.david.spacezero.filesystem.ZeroFolder;

public class Project {

    @Getter private ZeroFolder base;
    @Getter private ProjectData data;
    @Getter private ProjectStatistics statistics;

    public Project(ZeroFolder base, ProjectData data, ProjectStatistics statistics) {
        this.base = base;
        this.data = data;
        this.statistics = statistics;
    }
}
