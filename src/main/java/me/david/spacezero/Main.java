package me.david.spacezero;

import me.david.spacezero.filesystem.format.ProjectIO;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        ProjectIO.create(new File("example.sz"), "Example Projects");
    }

}
