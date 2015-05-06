package jumpingalien.exception;
import jumpingalien.util.Vector;
import be.kuleuven.cs.som.annotate.*;

public class OutOfBoundsException extends RuntimeException{
	
	/**
     * Initialize new out of bounds exception with given position.
     * 
     * @param   position
     *          The position given for the out of bounds exception.
     * @post    The position of the new out of bounds exception is set
     *          to the given position.
     *          | new.getPosition() == position
     */
    public OutOfBoundsException(Vector position) {
        Position = position;
    }
    
    /**
     * Return the position registered for the out of bounds exception.
     */
    @Basic 
    public Vector getPosition() {
		return Position;
	}

	/**
     * Variable registering the position involved in this out of bounds exception.
     * 
     */
	private Vector Position;


    /**
     * The Java API strongly recommends to explicitly define a version
     * number for classes that implement the interface Serializable.
     * At this stage, that aspect is of no concern to us. 
     */
    private static final long serialVersionUID = 2003001L;

}

