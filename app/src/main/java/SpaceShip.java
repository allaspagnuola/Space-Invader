import ch.aplu.jgamegrid.*;
import java.awt.event.KeyEvent;
import java.awt.*;
import java.util.List;

/**
 * Represents the player's spaceship in the Space Invader game.
 */
public class SpaceShip extends Actor implements GGKeyListener, Explodable {

    private int nbShots = 0;
    private GameVersion host;
    private boolean isAutoTesting = false;
    private List<String> controls = null;
    private int controlIndex = 0;

    /**
     * Constructor for creating a spaceship.
     */
    public SpaceShip(GameVersion host) {
        super("sprites/spaceship.gif");
        this.host = host;
    }

    /**
     * Sets testing conditions for the spaceship.
     * @param isAutoTesting True if auto testing is enabled, false otherwise.
     * @param controls The list of control actions for auto testing.
     */
    public void setTestingConditions(boolean isAutoTesting, List<String> controls) {
        this.isAutoTesting = isAutoTesting;
        this.controls = controls;
    }

    /**
     * Handles auto movement of the spaceship during auto testing.
     */
    /**
     * Automatically performs movements and actions based on auto-testing controls.
     */
    private void autoMove() {
        if (isAutoTesting) {
            if (controls != null && controlIndex < controls.size()) {
                String control = controls.get(controlIndex);
                Location next = null;

                // Check the control and perform corresponding actions
                switch(control) {
                    case "L":
                        // Move spaceship to the left
                        next = getLocation().getAdjacentLocation(Location.WEST);
                        moveTo(next);
                        break;

                    case "R":
                        // Move spaceship to the right
                        next = getLocation().getAdjacentLocation(Location.EAST);
                        moveTo(next);
                        break;

                    case "F":
                        // Create and launch a bomb
                        Bomb bomb = new Bomb();
                        gameGrid.addActor(bomb, getLocation());
                        nbShots++;
                        // Alien will accelerate when version is plus

                        if(host instanceof Plus){
                            ((Plus) host).accelerateAliens(nbShots);
                        }
                        break;

                    case "E":
                        // End the game
                        host.setIsGameOver(true);
                        break;
                }
                controlIndex++;
            }
        }
    }


    /**
     * Defines the behavior of the spaceship.
     */
    @Override
    public void act() {
        // Perform automated movement based on auto-testing controls
        autoMove();

        // Check if the spaceship collides with an alien
        Location location = getLocation();
        if (host.getNumberOfActorsAt(location, Alien.class) > 0) {
            // Display explosion, end the game, and remove the spaceship
            Explode();
            host.setIsGameOver(true);
            return;
        }

        // Check if all aliens are defeated
        if (host.getNumberOfActors(Alien.class) == 0) {
            // Display victory message and end the game
            host.getBg().drawText("Number of shots: " + nbShots, new Point(10, 30));
            host.getBg().drawText("Game constructed with JGameGrid (www.aplu.ch)", new Point(10, 50));
            host.addActor(new Actor("sprites/you_win.gif"), new Location(100, 60));
            host.setIsGameOver(true);
        }
    }


    /**
     * Handles the keyPressed event for manual control of the spaceship.
     */
    /**
     * Handles the keyPressed event for manual control of the spaceship.
     * @param keyEvent The KeyEvent containing information about the key press.
     * @return Always returns false.
     */
    @Override
    public boolean keyPressed(KeyEvent keyEvent) {
        Location next = null;

        // Check the key pressed and perform corresponding actions
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                // Move spaceship to the left
                next = getLocation().getAdjacentLocation(Location.WEST);
                moveTo(next);
                break;

            case KeyEvent.VK_RIGHT:
                // Move spaceship to the right
                next = getLocation().getAdjacentLocation(Location.EAST);
                moveTo(next);
                break;

            case KeyEvent.VK_SPACE:
                // Create and launch a bomb
                Bomb bomb = new Bomb();
                gameGrid.addActor(bomb, getLocation());
                nbShots++;
                // Alien accelerate when version is plus
                if(host instanceof Plus){
                    ((Plus) host).accelerateAliens(nbShots);
                }
                break;
        }

        return false;
    }


    /**
     * Moves the spaceship to the specified location if within bounds.
     * @param location The location to move the spaceship to.
     */
    private void moveTo(Location location) {
        if (location.x > 10 && location.x < 190) {
            setLocation(location);
        }
    }

    @Override
    public void Explode() {
        host.removeAllActors();
        Explosion explosion = new SpaceShipExplosion();
        host.addActor(explosion, getLocation());
        removeSelf();
    }

    /**
     * Handles the keyReleased event.
     */
    @Override
    public boolean keyReleased(KeyEvent keyEvent) {
        return false;
    }
}
