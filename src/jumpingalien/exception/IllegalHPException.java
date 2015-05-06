package jumpingalien.exception;
import be.kuleuven.cs.som.annotate.*;

public class IllegalHPException extends RuntimeException{


	/**
     * Initialize new illegal HP exception with given HP.
     * 
     * @param   HP
     *          The HP given for the illegal HP exception.
     * @post    The HP of the new illegal HP exception is set
     *          to the given HP.
     *          | new.getHP() == HP
     */
    public IllegalHPException(int hp) {
        HP = hp;
    }
    
    /**
     * Return the HP registered for the illegal HP exception.
     */
    @Basic 
    public int getHP() {
		return HP;
	}

	/**
     * Variable registering the HP involved in this illegal HP exception.
     * 
     */
	private int HP;

    /**
     * The Java API strongly recommends to explicitly define a version
     * number for classes that implement the interface Serializable.
     * At this stage, that aspect is of no concern to us. 
     */
    private static final long serialVersionUID = 2003001L;

}

