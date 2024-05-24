/**
 * The Accelerable interface defines the behavior for objects that can be accelerated.
 * In the context of the SpaceInvader game, it is implemented by the Alien class to control
 * the speed of the alien's movement.
 */
public interface Accelerable {

    /**
     * Accelerates the object based on the given speed parameter.
     *
     * @param speed The speed factor to accelerate the object.
     */
    void accelerate(int speed);
}
