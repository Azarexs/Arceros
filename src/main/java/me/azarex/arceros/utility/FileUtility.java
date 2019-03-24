package me.azarex.arceros.utility;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileUtility {

    public static Path createAndOverwrite(InputStream resourceStream, Path path) {
        try {
            if (Files.notExists(path)) {
                Files.createFile(path);
                Files.copy(resourceStream, path, StandardCopyOption.REPLACE_EXISTING);
            }

            return path;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}
