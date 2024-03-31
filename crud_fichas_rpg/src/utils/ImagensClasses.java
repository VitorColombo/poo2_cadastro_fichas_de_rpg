package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImagensClasses {
    public static Map<String, String> getPaths() {
        Map<String, String> paths = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(ImagensClasses.class.getResourceAsStream("/utils/imgPaths")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    paths.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return paths;
    }
    public static String getImagemClasse(String className) {
        Map<String, String> paths = getPaths();
        return paths.get(className);
    }
}