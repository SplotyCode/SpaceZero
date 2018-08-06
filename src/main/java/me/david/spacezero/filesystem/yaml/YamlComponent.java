package me.david.spacezero.filesystem.yaml;

public interface YamlComponent {

    void read(YamlConfiguration yaml);
    void write(YamlConfiguration yaml);

}
