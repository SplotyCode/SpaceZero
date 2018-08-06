package me.david.spacezero.filesystem.format;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ProjectValues {

    @Getter private long linkedFoldersCount, linkedFilesCount;

}
