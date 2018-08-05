package me.david.spacezero.filesystem;

import com.google.common.collect.Lists;
import lombok.Getter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class NormalComponent implements ZeroComponent {

    @Getter protected File file;

    public NormalComponent(final File file) {
        this.file = file;
        if (!file.exists()) throw new IllegalArgumentException("Folder must exists");
    }

    public List<NormalFolder> getParents(File baseDir) {
        List<NormalFolder> parents = new ArrayList<>();
        NormalFolder currentParent = getParent();
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
    public NormalFolder getParent() {
        return new NormalFolder(file.getParentFile());
    }

    @Override
    public String getName() {
        return file.getName();
    }
}
