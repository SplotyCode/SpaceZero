package me.david.spacezero;

import lombok.Getter;
import me.david.spacezero.filesystem.format.Project;
import me.david.spacezero.filesystem.format.ProjectIO;

import java.io.File;

public class SpaceZero {

    public static void main(String[] args) {
        new SpaceZero();
    }

    @Getter private static SpaceZero instance;

    @Getter private Project project;

    private SpaceZero() {
        instance = this;
        project = ProjectIO.create(new File("example.sz"), "Example Projects");
    }

}
