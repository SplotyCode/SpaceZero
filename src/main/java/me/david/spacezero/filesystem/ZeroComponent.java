package me.david.spacezero.filesystem;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.zip.ZipEntry;

@AllArgsConstructor
public class ZeroComponent implements IComponent {

    @Getter protected ZipEntry entry;
    private IFolder parent;

    @Override
    public IFolder getParent() {
        return parent;
    }

    @Override
    public String getName() {
        return entry.getName();
    }
}
