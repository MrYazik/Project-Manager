package mryazik.github.io.util;

public class normalizeFileName {
    public static String normFile(String fileText)
    {
        return fileText.replaceAll("[\\\\/:*?\"<>|]", "_");
    }
}
