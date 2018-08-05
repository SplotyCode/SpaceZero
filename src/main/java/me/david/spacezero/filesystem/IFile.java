package me.david.spacezero.filesystem;

import java.io.IOException;
import java.io.InputStream;

public interface IFile extends IComponent {

    InputStream getStream() throws IOException;

}
