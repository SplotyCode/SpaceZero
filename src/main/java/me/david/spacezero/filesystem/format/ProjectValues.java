package me.david.spacezero.filesystem.format;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.david.spacezero.filesystem.yaml.ProjectComponent;
import me.david.spacezero.filesystem.yaml.YamlConfiguration;

@AllArgsConstructor
@NoArgsConstructor
public class ProjectValues implements ProjectComponent {

    @Getter private long linkedFoldersCount, linkedFilesCount;

    @Override
    public String getFileName() {
        return "projectvalues.yml";
    }

    @Override
    public void read(YamlConfiguration yaml) {
        linkedFilesCount = yaml.getLong("linkedfiles");
        linkedFoldersCount = yaml.getLong("linkedfolders");
    }

    @Override
    public void write(YamlConfiguration yaml) {
        yaml.set("linkedfiles", linkedFilesCount);
        yaml.set("linkedfolders", linkedFoldersCount);
    }
}
