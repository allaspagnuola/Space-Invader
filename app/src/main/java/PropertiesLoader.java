import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * PropertiesLoader class responsible for loading properties files.
 */
public class PropertiesLoader {

    /**
     * Loads a properties file and returns a Properties object.
     *
     * @param propertiesFile The path to the properties file.
     * @return A Properties object containing the properties, or null if an error occurs.
     */
    public static Properties loadPropertiesFile(String propertiesFile) {
        try (InputStream input = PropertiesLoader.class.getClassLoader().getResourceAsStream(propertiesFile)) {

            Properties prop = new Properties();

            // Load the properties file into the Properties object
            prop.load(input);

            // Remove properties that are empty strings
            removeEmptyProperties(prop, "space_craft.control");
            removeEmptyProperties(prop, "Powerful.locations");
            removeEmptyProperties(prop, "Invulnerable.locations");
            removeEmptyProperties(prop, "Multiple.locations");
            removeEmptyProperties(prop, "aliens.control");

            return prop;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Helper method to remove properties that are empty strings.
     *
     * @param prop The Properties object.
     * @param key  The key of the property to check.
     */
    private static void removeEmptyProperties(Properties prop, String key) {
        if (prop.getProperty(key).equals("")) {
            prop.remove(key);
        }
    }
}
