package me.david.spacezero.filesystem;

import com.google.common.collect.Lists;
import lombok.Getter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ExternalComponent implements IComponent {

    @Getter protected File file;

    public ExternalComponent(final File file) {
        this.file = file;
        if (!file.exists()) throw new IllegalArgumentException("Folder must exists");
    }

    public List<ExternalFolder> getParents(File baseDir) {
        List<ExternalFolder> parents = new ArrayList<>();
        ExternalFolder currentParent = getParent();
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
    public ExternalFolder getParent() {
        return new ExternalFolder(file.getParentFile());
    }

    @Override
    public String getName() {
        return file.getName();
    }
}
