package jumpingalien.model;

import java.util.HashSet;

import program.Program;

import be.kuleuven.cs.som.annotate.Raw;
import jumpingalien.exception.OutOfBoundsException;
import jumpingalien.util.Sprite;
import jumpingalien.util.Vector;

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
			double initvelocityx, double maxvelocityx, Program program)
					throws IllegalArgumentException, OutOfBoundsException {
		super(pixelLeftX, pixelBottomY, sprites, initvelocityx, maxvelocityx, program);	
	}

	public Buzam(int pixelLeftX, int pixelBottomY,Sprite[] sprites,Program program){
		super(pixelLeftX, pixelBottomY, sprites, 1.0,3.0, program);
	}

	@Override
	public void advanceTime(double dt) throws IllegalArgumentException{

		if ((dt < 0.0) || (dt > 0.2)){
			throw new IllegalArgumentException();
		}
		updateHPTile(dt);              
		UpdateAccY();

		//		if(isEndDuckPressed())
		//			endDuck();

		if (isImmune()){
			addToImmuneTimer(dt);
			setImmune(false);
		}

		getProgram().advanceTime(dt);

		correctSpawnedInGround();
		HashSet<String> hits  = collisionDetection(dt);

		if ((hits != null) && (!hits.isEmpty())){

			if (hits.contains("X")){

				if (getVelocity().getElemx() >= 0.0){

					setPos(new Vector(getPos().getElemx()-1,getPos().getElemy()));
					setVelocity(new Vector(0.0,getVelocity().getElemy()));
					setAccCurr(new Vector(0.0,getAccCurr().getElemy()));

				}else if(getVelocity().getElemx() < 0.0) {

					setPos(new Vector(getPos().getElemx()+1,getPos().getElemy()));
					setVelocity(new Vector(0.0,getVelocity().getElemy()));
					setAccCurr(new Vector(0.0,getAccCurr().getElemy()));
				}

			}else if (hits.contains("Y")){

				if (isOnGround() && isJumped()){
					setOnGround(false);

				}else if (getVelocity().getElemy() >= 0.0){
					setPos(new Vector(getPos().getElemx(),getPos().getElemy()-1));
					setVelocity(new Vector(getVelocity().getElemx(),0.0));

				}else if (getVelocity().getElemy() < 0.0){

					setPos(new Vector(getPos().getElemx(),getPos().getElemy()+1));
					setVelocity(new Vector(getVelocity().getElemx(),0.0));
					setAccCurr(new Vector(getAccCurr().getElemx(),0.0));
				}
			}
		}
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
	
	
	@Override
	public void startMoveProgram(Boolean direction) {
		
		

		if (!direction){
			this.setVelocity(new Vector(-getInitVelocityX(),0.0));
			this.setAccCurr(new Vector(AccXBkw,0.0));
		}else if (direction){
			this.setVelocity(new Vector(getInitVelocityX(),0.0));
			this.setAccCurr(new Vector(AccXFwd,0.0));
		}else
			this.setVelocity(new Vector(0,0));
		}


	@Override
	public void stopMoveProgram() {
		this.setVelocity(new Vector(0,0));
		this.setAccCurr(new Vector(0.0,0.0));
	}

	@Override
	public void startDuckProgram() {
		startDuck();
	}

	@Override
	public void stopDuckProgram() {
		pressEndDuck();
	}

	@Override
	public void startJumpProgram() {
		startJump();
	}

	@Override
	public void stopJumpProgram() {
		endJump();
	}

	private final static double AccXFwd = 0.9;
	private final static double AccXBkw = -0.9;
}
