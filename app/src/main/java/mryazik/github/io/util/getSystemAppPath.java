package mryazik.github.io.util;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class getSystemAppPath {
    private static String appName = "ProjectManager";
    private static Logger logger = Logger.getLogger(getSystemAppPath.class.getName());

    public static Path get()
    {
        String systemName = getOSName.get();

        if (systemName.contains("mac")) {
            String userHome = System.getProperty("user.home") + "/Applications/" + appName + "/projects";
            return Paths.get(userHome);
        } else if (systemName.contains("win")) {
            String userHome = System.getProperty("user.home") + "/AppData/" + appName + "/projects";
            return Paths.get(userHome);
        } else if (systemName.contains("nix"))
        {
            String userHome = "/var/lib/" + appName + "/projects";
            return Paths.get(userHome);
        } else {
            logger.log(Level.SEVERE, "Не удалось определить имя системы. systemName: " + systemName);
            return null;
        }
    }
}
