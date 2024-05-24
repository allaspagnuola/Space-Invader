import java.util.Properties;

/**
 * The Driver class serves as the entry point for the SpaceInvader game.
 * It loads the game properties and initiates the game.
 */
public class Driver {

    // Default path for the properties file
    public static final String DEFAULT_PROPERTIES_PATH = "properties/game2.properties";

    /**
     * The main method that serves as the entry point for the application.
     * @param args Command-line arguments, which can include a custom properties file path.
     */
    public static void main(String[] args) {

        // Initialize the properties path with the default value
        String propertiesPath = DEFAULT_PROPERTIES_PATH;

        // If a custom properties path is provided as a command-line argument, use it
        if (args.length > 0) {
            propertiesPath = args[0];
        }

        // Load the properties file
        final Properties properties = PropertiesLoader.loadPropertiesFile(propertiesPath);

        // Initialize and run the SpaceInvader game, capturing the log result
        String logResult = new SpaceInvader(properties).runApp(true);

        // Output the log result
        System.out.println("logResult = " + logResult);
    }
}
