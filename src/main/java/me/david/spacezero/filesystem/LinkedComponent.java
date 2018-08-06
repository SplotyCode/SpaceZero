package me.david.spacezero.filesystem;

import com.google.common.collect.Lists;
import lombok.Getter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LinkedComponent implements IComponent {

    @Getter protected File file;

    public LinkedComponent(final File file) {
        this.file = file;
        if (!file.exists()) throw new IllegalArgumentException("Folder must exists");
    }

    public List<LinkedFolder> getParents(File baseDir) {
        List<LinkedFolder> parents = new ArrayList<>();
        LinkedFolder currentParent = getParent();
        while (currentParent.getParent().file != baseDir) {
            parents.add(currentParent.getParent());
            currentParent = currentParent.getParent();
        }
        return parents;
    }

    public String getFullPath(File baseDir) {
        StringBuilder builder = new StringBuilder();
        Lists.reverse(getParents(baseDir)).forEach(directory -> builder.append(directory).append("/"));
        return builder.append(getName()).toString();
    }

    @Override
    public LinkedFolder getParent() {
        return new LinkedFolder(file.getParentFile());
    }

    @Override
    public String getName() {
        return file.getName();
    }
}
