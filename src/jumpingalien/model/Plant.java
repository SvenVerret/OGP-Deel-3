package jumpingalien.model;

import java.util.HashSet;
import program.Program;
import jumpingalien.exception.IllegalVelocityException;
import jumpingalien.exception.OutOfBoundsException;
import jumpingalien.util.Sprite;
import jumpingalien.util.Vector;



public class Plant extends GameObject {
	/**
	 * This method creates a new plant, with the given parameters
	 * 
	 * @param 	x
	 * 			The x coordinate of the new plant
	 * @param 	y
	 * 			The Y coordinate of hte new plant
	 * @param 	sprites
	 * 			The sprites of the plant
	 * @effect	...
	 * 			| this.setPos(new Vector(x, y));
	 *			| this.setVelocity(new Vector(RightVelocity,0));
	 * @effect	...
	 * 			| this.Sprites = sprites;
	 *
	 * @throws 	IllegalArgumentException
	 * 			If the amount of sprites is not correct, nor null
	 * 			an exception is thrown
	 */
	public Plant(int x, int y, Sprite[] sprites, Program program)
			throws IllegalArgumentException{
		super(program);
		this.setPos(new Vector(x, y));
		this.setVelocity(new Vector(RightVelocity,0));



		if (sprites.length != 2|| sprites == null)
			throw new IllegalArgumentException("Sprites");
		else{
			this.Sprites = sprites;
			this.setSize(new Vector(sprites[0].getWidth(),sprites[0].getHeight()));
		}
	}

	public Plant(int x, int y, Sprite[] sprites)
			throws IllegalArgumentException{
		this(x,y,sprites,null);
	}


	/**
	 * This method terminates a plant from his world
	 * 
	 * @effect	...
	 * 			| this.isTerminated = true;
	 * 			| getWorld().removePlant(this);
	 * 			| setWorld(null);
	 */
	@Override
	protected void terminate(){
		this.isTerminated = true;
		getWorld().removePlant(this);
		setWorld(null);
	}


	/**
	 * This method initializes the HP of a plant
	 * 
	 * @post	...
	 * 			|this.setHP(1);
	 */
	@Override
	protected void initializeHP() {
		this.setHP(1);

	}

	/**
	 * This method returns the maximum HP of a plant
	 * 
	 * @return	int
	 * 			| return 1;
	 */
	@Override
	protected int getMaxHP() {
		return 1;
	}


	/**
	 * This method sets new positions and velocities with a time dt further
	 * 
	 * @effect	...
	 * 			| this.setPos(newPos);
	 * 
	 * @throws	IllegalArgumentException
	 * 			If a position of a velocity are not valid an exception is thrown
	 */
	@Override
	protected void advanceTime(double dt) throws IllegalArgumentException {
		if (!isDying()){
			
			if(getProgram() == null){
				
				if (getStartTimeDir() > 0.5){
					if (getVelocity().getElemx() == RightVelocity)
						this.setVelocity(new Vector(LeftVelocity, 0));
					else
						this.setVelocity(new Vector(RightVelocity,0));

					resetStartTimeDir();
				}else
					addTimeDir(dt);
				
			}else{
				getProgram().advanceTime(dt);
			}

			HashSet<String> hits  = collisionDetection(dt);

			if ((hits != null) && (!hits.isEmpty())){
				if (hits.contains("X")){

					if(getProgram() == null){
						if (getVelocity().getElemx() >= 0){
							setPos(new Vector(getPos().getElemx(),getPos().getElemy()));
							setVelocity(new Vector(LeftVelocity,0.0));
						}else if(getVelocity().getElemx() < 0){
							setPos(new Vector(getPos().getElemx(),getPos().getElemy()));
							setVelocity(new Vector(RightVelocity,0.0));
						}
						resetStartTimeDir();
					}else{
						stopMoveProgram();
					}

				}
			}
			Vector dxy = getVelocity().multiplyBy(dt);
			Vector newPos = getPos().addVector(dxy.multiplyBy(100));
			this.setPos(newPos);

		}
	}


	/**
	 * This method adds time to the timer
	 * 
	 * @param 	dt
	 * 			The amount of time to be added
	 * @post	...
	 * 			| startTimeDir = StartTimeDir + dt;
	 */
	protected void addTimeDir(double dt){
		StartTimeDir = StartTimeDir + dt;
	}

	/**
	 * This method returns the StartTimeDir
	 * 
	 * @return	double
	 * 			| return StartTimeDir;
	 */
	protected double getStartTimeDir(){
		return StartTimeDir;
	}

	/**
	 * This method resets the timer of StartTimeDir
	 * 
	 * @post	...
	 * 			| StartTimeDir = 0;
	 */
	protected void resetStartTimeDir(){
		StartTimeDir = 0;
	}

	protected double StartTimeDir=0;


	/**
	 * This method checks if the given velocity is valid
	 * 
	 * @return	boolean
	 * 			| return true
	 * @throws	IllegalVelocityException
	 * 			If the velocity is not valid, an IllegalVelocityException is thrown
	 * 
	 */
	@Override
	protected boolean isValidVelocity(Vector velocity)
			throws IllegalVelocityException { 

		if (Math.abs(velocity.getElemx()) > 0.5)
			throw new IllegalVelocityException(new Vector(0.5,velocity.getElemy()));
		else
			return true;
	}

	/**
	 * This method returns a string in which directions Mazub is Colliding
	 * 
	 * 
	 * @effect	if no collision happened for smalldt
	 * 			| try{
	 *			|	setPos(getCollisionPos());
	 *			| } catch(OutOfBoundsException e){
	 *			|	setPos(new Vector(e.getPosition().getElemx(),e.getPosition().getElemy()));
	 *			|}
	 *
	 * @return	<String>
	 * 			| return hits;
	 *  
	 * 
	 */
	@Override
	protected HashSet<String> collisionDetection(double dt){

		HashSet<String> hits = new HashSet<String>();
		double smalldt = 0.01/(getVelocity().normOfVector());
		setCollisionSmalldt(smalldt);
		double sumSmalldt = getCollisionSmalldt();


		while (sumSmalldt < dt){
			Vector dxy = getVelocity().multiplyBy(getCollisionSmalldt());
			Vector newPos = getPos().addVector(dxy.multiplyBy(100));
			this.setCollisionPos(newPos);

			hits = collisionTile();

			if (hits.isEmpty()){

				try{
					setPos(getCollisionPos());
				} catch(OutOfBoundsException e){
					setPos(new Vector(e.getPosition().getElemx(),e.getPosition().getElemy()));
				}

				sumSmalldt = sumSmalldt + smalldt;
			}else{
				return hits;
			}
		}
		return hits;
	}


	@Override
	public Sprite getCurrentSprite() {
		if (getVelocity().getElemx() == LeftVelocity){
			this.setSize(new Vector(Sprites[0].getWidth(),Sprites[0].getHeight()));
			return Sprites[0];
		}
		else{
			this.setSize(new Vector(Sprites[1].getWidth(),Sprites[1].getHeight()));
			return Sprites[1];
		}
	}

	private Sprite[] Sprites;
	static final private double RightVelocity = 0.5;
	static final private double LeftVelocity = -0.5;

	@Override
	protected double getMaxVelocityX() {
		return RightVelocity;
	}

	@Override
	protected HashSet<String> collisionObject() {
		HashSet<String> emptyset = new HashSet<String>();
		return emptyset;
	}



	//	@Override
	//	public double getRightVelocity() {
	//		return RightVelocity;
	//	}
	//
	//
	//	@Override
	//	public double getLeftVelocity() {
	//		return LeftVelocity;
	//	}
	//
	//
	//	@Override
	//	public double getJumpVelocity() {
	//		return 0.0;
	//	}



	@Override
	public void startDuckProgram() {} // plants cannot duck


	@Override
	public void stopDuckProgram() {} // plants cannot duck


	@Override
	public void startJumpProgram() {} // plants cannot jump


	@Override
	public void stopJumpProgram() {} // plants cannot jump

	@Override
	public void startMoveProgram(boolean direction) {
		// TODO Auto-generated method stub	
	}
	
	@Override
	public void stopMoveProgram() {
		// TODO Auto-generated method stub
	}



}
