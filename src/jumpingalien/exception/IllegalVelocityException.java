package jumpingalien.exception;
import jumpingalien.util.Vector;
import be.kuleuven.cs.som.annotate.*;

public class IllegalVelocityException extends RuntimeException{

	/**
     * Initialize new illegal velocity exception with given velocity.
     * 
     * @param   velocity
     *          The velocity given for the illegal velocity exception.
     * @post    The velocity of the new illegal velocity exception is set
     *          to the given velocity.
     *          | new.getVelocity() == velocity
     */
    public IllegalVelocityException(Vector velocity) {
        Velocity = velocity;
    }
    
    /**
     * Return the velocity registered for the illegal velocity exception.
     */
    @Basic 
    public Vector getVelocity() {
		return Velocity;
	}

	/**
     * Variable registering the velocity involved in this illegal velocity exception.
     * 
     */
	private Vector Velocity;


    /**
     * The Java API strongly recommends to explicitly define a version
     * number for classes that implement the interface Serializable.
     */
    private static final long serialVersionUID = 2003001L;

}
