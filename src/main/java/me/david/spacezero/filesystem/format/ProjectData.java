package me.david.spacezero.filesystem.format;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.david.spacezero.filesystem.yaml.ProjectComponent;
import me.david.spacezero.filesystem.yaml.YamlConfiguration;

@AllArgsConstructor
@NoArgsConstructor
public class ProjectData implements ProjectComponent {

    @Getter private long creationTime;
    @Getter private String user;
    @Getter private String name;

    @Override
    public String getFileName() {
        return "project.yml";
    }

    @Override
    public void read(YamlConfiguration yaml) {
        creationTime = yaml.getLong("creationtime");
        user = yaml.getString("user");
        name = yaml.getString("name");
    }

    @Override
    public void write(YamlConfiguration yaml) {
        yaml.set("creationtime", creationTime);
        yaml.set("user", user);
        yaml.set("name", name);
    }
}
