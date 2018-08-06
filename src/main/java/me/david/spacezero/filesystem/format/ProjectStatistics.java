package me.david.spacezero.filesystem.format;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.david.spacezero.filesystem.yaml.ProjectComponent;
import me.david.spacezero.filesystem.yaml.YamlConfiguration;

@AllArgsConstructor
@NoArgsConstructor
public class ProjectStatistics implements ProjectComponent {

    @Getter private int opens;
    @Getter private long onTime;

    @Override
    public String getFileName() {
        return "statistics.yml";
    }

    @Override
    public void read(YamlConfiguration yaml) {
        opens = yaml.getInt("opens");
        onTime = yaml.getLong("ontime");
    }

    @Override
    public void write(YamlConfiguration yaml) {
        yaml.set("opens", opens);
        yaml.set("ontime", onTime);
    }
}
