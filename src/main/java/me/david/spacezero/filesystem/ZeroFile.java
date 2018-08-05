package me.david.spacezero.filesystem;

import java.io.IOException;
import java.io.InputStream;

public interface ZeroFile extends ZeroComponent {

    InputStream getStream() throws IOException;

}
