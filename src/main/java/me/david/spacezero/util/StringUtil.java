package me.david.spacezero.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public final class StringUtil {

    public static String fromException(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        return sw.toString();
    }
}
