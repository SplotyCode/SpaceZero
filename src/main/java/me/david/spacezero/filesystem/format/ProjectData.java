package me.david.spacezero.filesystem.format;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ProjectData {

    @Getter private long creationTime;
    @Getter private String user;
    @Getter private String name;

}
