package me.david.spacezero.filesystem.yaml;

public class InvalidConfigurationException extends RuntimeException {

    public InvalidConfigurationException() {}
    public InvalidConfigurationException(String msg) {
        super(msg);
    }
    public InvalidConfigurationException(Throwable cause) {
        super(cause);
    }
}
