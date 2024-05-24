// SpaceInvader.java
// Sprite images from http://www.cokeandcode.com/tutorials
// Nice example how the different actor classes: SpaceShip, Bomb, SpaceInvader, Explosion
// act almost independently of each other. This decoupling simplifies the logic of the application

import java.util.Properties;
/**
 * SpaceInvader class represents the main game logic.
 * It extends the GameGrid class and implements the GGKeyListener interface.
 */
public class SpaceInvader
{
  private GameVersion gameVersion;
  private Properties properties;
  private StringBuilder logResult = new StringBuilder();

  /**
   * Constructor for creating a SpaceInvader object.
   * @param properties The properties object containing game settings.
   */
  public SpaceInvader(Properties properties) {
    this.properties = properties;
    setGame();
  }

  /**
   * used to set version for the game
   */
  public void setGame(){
    String version = properties.getProperty("version");
    if (version.equals("simple")) {
      gameVersion = new Simple(properties, this);
    } else {
      gameVersion = new Plus(properties, this);
    }
  }

  /**
   * Main method to run the application. It sets up the game, starts the simulation,
   * and runs the main game loop.
   *
   * @param isDisplayingUI Boolean to check if the UI should be displayed.
   * @return String log result containing the game's events.
   */
    public String runApp(boolean isDisplayingUI) {
        gameVersion.run(isDisplayingUI);
        return logResult.toString();
    }

    public void log(String s){
        logResult.append(s);
    }
}
