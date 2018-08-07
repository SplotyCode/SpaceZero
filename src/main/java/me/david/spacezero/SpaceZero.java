package me.david.spacezero;

import lombok.Getter;
import me.david.spacezero.filesystem.format.Project;
import me.david.spacezero.filesystem.format.ProjectIO;
import me.david.spacezero.gui.MainGUI;
import me.david.spacezero.logger.ZeroLogger;
import org.lwjgl.Version;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class SpaceZero {

    public static void main(String[] args) {
        new SpaceZero();
    }

    @Getter private static SpaceZero instance;

    @Getter private Project project;
    @Getter private MainGUI gui;
    @Getter private ZeroLogger logger = new ZeroLogger(Level.ALL);

    private SpaceZero() {
        instance = this;
        logger.printBanner();
        logger.info("LWJGL Version: " + Version.getVersion());
        System.out.println("a");
        try {
            project = ProjectIO.load(new File("example.sz"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        gui = new MainGUI();
    }

}
