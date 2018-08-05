package me.david.spacezero.filesystem.yaml;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.representer.Representer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class YamlConfiguration extends FileConfiguration {

    protected static final String BLANK_CONFIG = "{}\n";
    private final Representer yamlRepresenter = new YamlRepresenter();
    private final Yaml yaml = new Yaml(yamlRepresenter);

    @Override
    public String saveToString() {
        yamlRepresenter.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        String dump = yaml.dump(getValues(false));

        if (dump.equals(BLANK_CONFIG)) {
            dump = "";
        }

        return dump;
    }

    @Override
    public void loadFromString(String contents) throws InvalidConfigurationException {
        Map<?, ?> input;
        try {
            input = (Map<?, ?>) yaml.load(contents);
        } catch (YAMLException e) {
            throw new InvalidConfigurationException(e);
        } catch (ClassCastException e) {
            throw new InvalidConfigurationException("Top level is not a Map.");
        }

        if (input != null) {
            convertMapsToSections(input, this);
        }
    }

    protected void convertMapsToSections(Map<?, ?> input, ConfigurationSection section) {
        for (Map.Entry<?, ?> entry : input.entrySet()) {
            String key = entry.getKey().toString();
            Object value = entry.getValue();

            if (value instanceof Map) {
                convertMapsToSections((Map<?, ?>) value, section.createSection(key));
            } else {
                section.set(key, value);
            }
        }
    }

    public static YamlConfiguration loadConfiguration(File file) {
        YamlConfiguration config = new YamlConfiguration();

        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException ex) {
            ex.printStackTrace();
        }

        return config;
    }

    public static YamlConfiguration loadConfiguration(InputStream stream) {
        YamlConfiguration config = new YamlConfiguration();

        try {
            config.load(stream);
        } catch (IOException | InvalidConfigurationException ex) {
            ex.printStackTrace();
        }

        return config;
    }
}
