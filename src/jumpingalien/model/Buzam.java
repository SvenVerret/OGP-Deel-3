package jumpingalien.model;

import be.kuleuven.cs.som.annotate.Raw;
import jumpingalien.exception.OutOfBoundsException;
import jumpingalien.util.Sprite;

public class Buzam extends Mazub{

	/**
	 *
	 * @param       pixelLeftX
	 *                      The position of the most left pixel of Mazub on the x-axis.
	 * @param       pixelBottomY
	 *                      The position of the most left pixel of Mazub on the y-axis.
	 * @param       sprites
	 *                      A list of images that will be used to represent a Mazub class in the GUI.
	 * @post        Initial height and width are set, based on the initial sprite.
	 *                      | new.getHeight() == sprites[0].getHeight()
	 *                      | new.getWidth() == sprites[0].getWidth()
	 * @post        Maximum value for the velocity of Mazub is set.
	 *                      | new.getMaxVelocityX() == maxvelocityx
	 *                      | new.getMaxVelocityXCurr() == maxvelocityx
	 * @post        Minimum value for the velocity of Mazub is set.
	 *                      | new.getInitVelocityX() == initvelocityx
	 * @post        The list of sprites used for a Mazub class are now stored in Sprites.
	 *                      | new.Sprites = sprites
	 * @effect      PixelLeftX is set as the position on the x-axis. PixelBottomY is set as the position on the y-axis.
	 *                      If these positions are not on the screen, setPosX or setPoxY will throw an OutOfBoundsException.
	 *                      | setPosX(pixelLeftX) && setPosY(pixelBottomY)                 
	 * @throws      IllegalArgumentException("Sprites")
	 *                      An exception is thrown when the list of sprites is shorter than 10.
	 *                      Some states of Mazub won't have images if the list is too short.
	 *                      | sprites.length < 10
	 * @throws      IllegalArgumentException("Velocity")
	 *                      An exception is thrown when the initial velocity is lower than 1m/s or
	 *                      when the maximum velocity is lower than the initial velocity.
	 *                      | (initvelocityx < 1.0) || (maxvelocityx < initvelocityx)
	 *
	 */
	public Buzam(int pixelLeftX, int pixelBottomY, Sprite[] sprites,
			double initvelocityx, double maxvelocityx)
					throws IllegalArgumentException, OutOfBoundsException {
		super(pixelLeftX, pixelBottomY, sprites, initvelocityx, maxvelocityx);	
	}

	public Buzam(int pixelLeftX, int pixelBottomY,Sprite[] sprites){
		super(pixelLeftX, pixelBottomY, sprites, 1.0,3.0);
	}


	/**
	 * This method checks whether Mazub can have the given world as world
	 * 
	 * @return	boolean: 	true if the given world is not null
	 * 						Mazub must have no world
	 * 			| if (world == null){
	 *			|	return false;
	 *			| }
	 *			| if (world.getMazub() == null){
	 *			|	return true;
	 *			| }else{
	 *			|	return false;
	 *			| }
	 */
	@Raw
	@Override
	public boolean canHaveAsWorld(World world) {
		if (world == null){
			return false;
		}
		if (world.getBuzam() == null){
			return true;
		}else{
			return false;
		}
	}


	/**
	 * @effect      ...
	 *                      | setHP(100);
	 */
	@Override
	protected void initializeHP() {
		this.setHP(500);
	}




}
