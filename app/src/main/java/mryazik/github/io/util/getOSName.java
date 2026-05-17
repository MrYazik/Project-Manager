package mryazik.github.io.util;

import java.util.logging.Level;
import java.util.logging.Logger;

public class getOSName {
    static Logger logger = Logger.getLogger(getOSName.class.getName());

    public static String get()
    {
        String osName = System.getProperty("os.name").toLowerCase();
        logger.log(Level.INFO, "os_name: " + osName); // win/mac/nix
        return osName;
    }
}
