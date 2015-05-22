package jumpingalien.model;
import java.util.HashSet;

import program.Program;
import program.expression.Expression;
import jumpingalien.exception.IllegalVelocityException;
import jumpingalien.exception.OutOfBoundsException;
import jumpingalien.part3.programs.IProgramFactory.Direction;
import jumpingalien.util.Sprite;
import jumpingalien.util.Vector;
import be.kuleuven.cs.som.annotate.*;

/**
 * @Invar	| isValidPos(getPos());
 * @Invar	| isValidVelocity(getVelocity());
 * @Invar	| hasProperWorld();
 * @Invar	| isValidHP(getHP());
 * @Invar	| isValidSize(getSize());
 * 
 * @author 	Kevin Zhang & Sven Verret
 * @version 2.0
 *
 */
public abstract class GameObject {

	/**
	 * This method creates GameObject
	 * 
	 * @effect	...
	 * 			| initializeHP();
	 */
	public GameObject(Program program) {
		if (program != null){
			this.program = program;
		} else{
			this.program = null;
		}
		initializeHP();
	}
	
	public GameObject(){
		this(null);
	}
	
	



	/**
	 * Return a boolean indicating whether or not this game object
	 * is terminated.
	 * 
	 * @return 	boolean
	 * 			| return this.isTerminated;
	 * 
	 */
	@Basic @Raw
	protected boolean isTerminated() {
		return this.isTerminated;
	}


	/**
	 * Terminate this.object
	 * and removes the appropriate associations
	 *
	 * @post    This object is terminated.
	 *          | new.isTerminated()
	 */
	protected abstract void terminate();


	protected boolean isTerminated;

	/**
	 * Return the world to which this object belongs.
	 * 
	 * @return	boolean
	 * 			| return this.world;
	 */
	@Basic @Raw
	public World getWorld() {
		return this.world;
	}


	/**
	 * Check whether this object can have the given world as
	 * its world.
	 *
	 * @param  world
	 *                 The world to check.
	 * @return If this object is terminated, true if and only if the
	 *         given world is not effective.
	 *       | if (this.isTerminated())
	 *       |   result == (world == null)
	 * @return If this object is not terminated, true if and only if the given
	 *         world is effective and not yet terminated.
	 *       | if (! this.isTerminated())
	 *       |   result ==
	 *       |     (world != null) &&
	 *       |     (! world.isTerminated())
	 */
	@Raw
	protected boolean canHaveAsWorld(World world) {
		if (this.isTerminated())
			return world == null;
		return (world != null)
				&& (!world.isTerminated());
	}

	/**
	 * Check whether this object has a proper world.
	 *
	 * @return True if and only if this object can have its world as its
	 *         world, and if the world of this object is either not effective
	 *         or if it has this object as one of its objects.
	 *       | result ==
	 *       |   canHaveAsWorld(getWorld()) &&
	 *       |   ( (getWorld() == null) || getWorld().hasAsObject(this))
	 **/
	@Raw
	protected boolean hasProperWorld() {
		return canHaveAsWorld(getWorld())
				&& ((getWorld() != null) || (getWorld().hasAsGameObject(this)));
	}



	/**
	 * Set the world of this object to the given world.
	 *
	 * @param  world
	 *         The new world for this object.
	 * @post   The world of this object is the same as the
	 *         given world.
	 *       | new.getWorld() == world
	 * @throws IllegalArgumentException
	 *         This object cannot have the given world as its world.
	 *       | ! canHaveAsWorld(world)
	 */
	@Raw
	protected void setWorld(World world) {
		if (canHaveAsWorld(world))
			this.world = world;
		else{
			System.out.println("WOOOORLD");
			this.world = null;
		}
			
	}

	private World world = null;

	/**
	 * This method initializes the HP of this.object
	 * 
	 * @effect	...
	 * 			| this.setHP(int)
	 * 
	 */
	protected abstract void initializeHP();

	/**
	 * This object returns the maximum HP of this.object
	 * @return	int
	 * 			| return int;
	 */
	protected abstract int getMaxHP();


	/**
	 * This method adds HP to the object
	 * 
	 * @param 	hp
	 * 			The amount of HP to be added
	 * 
	 * @effect	...
	 * 			| this.setHP(newHP);
	 */
	protected void addByHP(int hp){
		HP = this.getHP();
		int MaxHP = this.getMaxHP();
		int newHP = HP + hp;

		if (isValidHP(newHP))
			this.setHP(newHP);

		else if (newHP <= 0)
			this.setHP(0); 
		else if (newHP > MaxHP)
			this.setHP(MaxHP);
	}

	/**
	 * This method returns if an object has a valid amout of HP
	 * 
	 * @param 	hp
	 * 			The amount of HP to be checked
	 * @return	Boolean
	 * 			| return hp > 0 && hp <= getMaxHP();
	 */
	protected boolean isValidHP(int hp){
		return hp > 0 && hp <= getMaxHP();
	}

	/**
	 * This method returns an objects HP
	 * 
	 * @return	int
	 * 			| return HP
	 */
	public int getHP() {
		return HP;
	}

	/**
	 * This method sets an HP amount
	 * 
	 * @param 	hP
	 * 			The amount of HP to be set
	 * 
	 */
	protected void setHP(int hP) {
		HP = hP;
	}
	private int HP;

	/**
	 * Advance the time for the object by the given dt
	 * 
	 * @param 	dt
	 * 			The time of advance
	 * 
	 * @throws 	IllegalArgumentException
	 * 			...
	 */
	protected abstract void advanceTime(double dt) throws IllegalArgumentException;
	
	/**
	 * This method updates the acceleration in the Y direction
	 * 
	 * @effect	...
	 * 			| setAccCurr(new Vector(getAccCurr().getElemx(),0.0));
	 * 			| or
	 * 			| setAccCurr(new Vector(getAccCurr().getElemx(),-10.0));
	 * 
	 * 
	 */
	protected void UpdateAccY(){

		if (isOnGround()){
			if (isPassableTerrain(getPos().getElemx()+1, getPos().getElemy()-1) && 
					isPassableTerrain(getPos().getElemx()+getSize().getElemx()-1, getPos().getElemy()-1))
				setOnGround(false);

		}else if (!isOnGround()){
			if (!isPassableTerrain(getPos().getElemx()+1, getPos().getElemy()-1) && 
					!isPassableTerrain(getPos().getElemx()+getSize().getElemx()-1, getPos().getElemy()-1))
				setOnGround(true);
		}

		if (isOnGround()){
			setVelocity(new Vector(getVelocity().getElemx(),0.0));
			setAccCurr(new Vector(getAccCurr().getElemx(),0.0));
		}else
			setAccCurr(new Vector(getAccCurr().getElemx(),-10.0));
	}


	/**
	 * This method returns if the given pixel is in passable terrain
	 * 
	 * @param 	posx
	 * 			The x coordinate of the pixel
	 * 
	 * @param 	posy
	 * 			The y coordinate of the pixel
	 * 
	 * @return	boolean
	 * 			| if (feature == 1)
	 *			|	return false;
	 *			| else
	 *			|	return true;
	 */
	protected boolean isPassableTerrain(double posx,double posy){
		World world = this.getWorld();
		int feature = world.getGeologicalFeature((int)posx, (int)posy);
		if (feature == 1)
			return false;
		else
			return true;

	}

	/**
	 * This method returns a string of what is hits and where
	 * 
	 * @param 	dt
	 * 			A time interval to calculate objects their future positions
	 * 
	 * @effect	This method sets also new positions of the object, as long they
	 * 			do not hit something
	 * 			This happens in the timeinterval of dt
	 * 			| try{
	 *			|	if(isOnGround() && getCollisionVel().getElemy() < 0)
	 *			|		setVelocity(new Vector(getCollisionVel().getElemx(),0.0));
	 *			|	else
	 *			|		setVelocity(getCollisionVel());
	 *			|
	 *			|		} catch(IllegalVelocityException e){
	 *			|			setVelocity(new Vector(e.getVelocity().getElemx(),e.getVelocity().getElemy()));
	 *			|		}
	 *			|
	 *			|
	 *			| try{
	 *			|		setPos(getCollisionPos());
	 *			|	} catch(OutOfBoundsException e){
	 *			|		setPos(new Vector(e.getPosition().getElemx(),e.getPosition().getElemy()));
	 *			|	}
	 * 
	 * 
	 * 
	 * @return	<String>
	 * 			| return hits;
	 */
	protected HashSet<String> collisionDetection(double dt){

		HashSet<String> hits = new HashSet<String>();
		double smalldt = 0.01/(getVelocity().normOfVector() +
				getAccCurr().normOfVector()*dt);
		setCollisionSmalldt(smalldt);
		double sumSmalldt = getCollisionSmalldt();


		while (sumSmalldt < dt){

			Vector newVel = getVelocity().addVector(getAccCurr().multiplyBy(getCollisionSmalldt()));
			this.setCollisionVel(newVel);

			Vector dxy = getVelocity().multiplyBy(getCollisionSmalldt())
					.addVector((getAccCurr().multiplyBy(Math.pow(getCollisionSmalldt(),2))).multiplyBy(0.5));
			Vector newPos = getPos().addVector(dxy.multiplyBy(100));
			this.setCollisionPos(newPos);

			hits = collisionTile();

			if (hits.isEmpty()){
				hits = collisionObject();
			}else{
				return hits;
			}

			if (!hits.isEmpty()){
				return hits;

			}else{

				try{
					if(isOnGround() && getCollisionVel().getElemy() < 0)
						setVelocity(new Vector(getCollisionVel().getElemx(),0.0));
					else
						setVelocity(getCollisionVel());

				} catch(IllegalVelocityException e){
					setVelocity(new Vector(e.getVelocity().getElemx(),e.getVelocity().getElemy()));
				}


				try{
					setPos(getCollisionPos());
				} catch(OutOfBoundsException e){
					setPos(new Vector(e.getPosition().getElemx(),e.getPosition().getElemy()));
				}


				double newSmalldt = 0.01/(getCollisionVel().normOfVector() +
						getAccCurr().normOfVector()*dt);
				setCollisionSmalldt(newSmalldt);


				sumSmalldt = sumSmalldt + smalldt;
			}
		}
		return hits;
	}

	/**
	 * This method returns an HashSet, concerning the collision with objects
	 * 
	 * @return	HashSet<String>
	 * 			
	 */
	protected abstract HashSet<String> collisionObject();

	/**
	 * This method returns if this.object overlaps with another object
	 * 
	 * @param 	object
	 * 			The object to be investigated
	 * @return	boolean
	 * 			| return ((thisObjectX + (thisObjectW-1) >= objectX) && (objectX + (objectW-1) >= thisObjectX)
	 *			| && (thisObjectY +(thisObjectH-1) >= objectY) && (objectY + (objectH-1) >= thisObjectY));
	 */
	protected boolean overlapsWith(GameObject object){
		double thisObjectX = getCollisionPos().getElemx();
		double thisObjectY = getCollisionPos().getElemy();
		double thisObjectW = getSize().getElemx();
		double thisObjectH = getSize().getElemy();

		double objectX = object.getPos().getElemx();
		double objectY = object.getPos().getElemy();
		double objectW = object.getSize().getElemx();
		double objectH = object.getSize().getElemy();
		return ((thisObjectX + (thisObjectW-1) >= objectX) && (objectX + (objectW-1) >= thisObjectX)
				&& (thisObjectY +(thisObjectH-1) >= objectY) && (objectY + (objectH-1) >= thisObjectY));
	}

	/**
	 * This method returns if this.object overlaps with another object
	 * in the Y directions
	 * 
	 * @param 	object
	 * 			The object to be investigated
	 * @return	boolean
	 * 			| return (((int)(thisObjectY + thisObjectH) == (int)objectY+2) || 
	 *			| ((int)(objectY + objectH) == (int)thisObjectY+2));
	 */
	public boolean overlapsWithY(GameObject object){
		double thisObjectY = getCollisionPos().getElemy();
		double thisObjectH = getSize().getElemy();
		double objectY = object.getPos().getElemy();
		double objectH = object.getSize().getElemy();

		return (((int)(thisObjectY + thisObjectH) == (int)objectY+2) || 
				((int)(objectY + objectH) == (int)thisObjectY+2));
	}

	/**
	 * This method returns if this.object overlaps with another object
	 * in the X directions
	 * 
	 * @param 	object
	 * 			The object to be investigated
	 * @return	boolean
	 * 			| return (((int)thisObjectX+2== (int)(objectX + objectW)) || 
	 *			| (int)objectX+2== (int)(thisObjectX + thisObjectW));
	 */
	public boolean overlapsWithX(GameObject object){

		double thisObjectX = getCollisionPos().getElemx();
		double thisObjectW = getSize().getElemx();
		double objectX = object.getPos().getElemx();
		double objectW = object.getSize().getElemx();


		return (((int)thisObjectX+2== (int)(objectX + objectW)) || 
				(int)objectX+2== (int)(thisObjectX + thisObjectW));
	}

	/**
	 * This method sets an objects, that's positioned in the ground back on groundlevel
	 * 
	 * @effect	...
	 * 			| setPos(new Vector(thisObjectX,thisObjectY+i+1));
	 */
	protected void correctSpawnedInGround(){
		int thisObjectX = (int) getPos().getElemx();
		int thisObjectY = (int) getPos().getElemy();
		int thisObjectW = (int) getSize().getElemx();

		int typeLeft = getWorld().getGeologicalFeature(thisObjectX,thisObjectY);
		int typeRight = getWorld().getGeologicalFeature(thisObjectX+thisObjectW,thisObjectY);

		if(typeLeft == 1 && typeRight == 1){

			int i =0;
			while (typeLeft == 1 && typeRight == 1){
				typeLeft = getWorld().getGeologicalFeature(thisObjectX,thisObjectY+i);
				typeRight = getWorld().getGeologicalFeature(thisObjectX+thisObjectW,thisObjectY+i);
				i++;	
			}
			setPos(new Vector(thisObjectX,thisObjectY+i+1));
		}
	}

	/**
	 * This method returns a string of a tile, where this.object is colliding with
	 * 
	 * @return	<String>
	 * 			| return hits;
	 */
	protected HashSet<String> collisionTile(){

		HashSet<String> hits = new HashSet<String>();

		int thisObjectX = (int) getCollisionPos().getElemx();
		int thisObjectY = (int) getCollisionPos().getElemy();
		int thisObjectW = (int) getSize().getElemx();
		int thisObjectH = (int) getSize().getElemy();


		if (!isPassableTerrain(thisObjectX+1, thisObjectY)){
			hits.add("Y");
			setOnGround(true);
		}
		else if (!isPassableTerrain(thisObjectX+thisObjectW-1, thisObjectY)){
			hits.add("Y");
			setOnGround(true);
		}
		else if (!isPassableTerrain(thisObjectX+thisObjectW-1, thisObjectY+thisObjectH)){
			hits.add("Y");
		}
		else if (!isPassableTerrain(thisObjectX+1, thisObjectY+thisObjectH)){
			hits.add("Y");
		}

		if (!isPassableTerrain(thisObjectX+thisObjectW-1, thisObjectY+1)){
			hits.add("X");
		}
		else if (!isPassableTerrain(thisObjectX+thisObjectW-1, thisObjectY+(thisObjectH/2))){
			hits.add("X");
		}
		else if (!isPassableTerrain(thisObjectX+1, thisObjectY+(thisObjectH/2))){
			hits.add("X");
		}
		else if (!isPassableTerrain(thisObjectX+thisObjectW-1, thisObjectY+thisObjectH-1)){
			hits.add("X");
		}
		else if (!isPassableTerrain(thisObjectX, thisObjectY+thisObjectH-1)){
			hits.add("X");
		}
		else if (!isPassableTerrain(thisObjectX, thisObjectY+1)){
			hits.add("X");
		}

		if (hits.isEmpty()){

			if (!isPassableTerrain(thisObjectX+1, thisObjectY+1)){
				hits.add("X");
				hits.add("Y");
			}
			if (!isPassableTerrain(thisObjectX+thisObjectW-1, thisObjectY+thisObjectH-1)){
				hits.add("X");
				hits.add("Y");
			}
			if (!isPassableTerrain(thisObjectX+1, thisObjectY+thisObjectH-1)){
				hits.add("X");
				hits.add("Y");
			}
			if (!isPassableTerrain(thisObjectX+thisObjectW-1, thisObjectY+1)){
				hits.add("X");
				hits.add("Y");
			}

		}


		if (!hits.contains("Y") && isOnGround()){
			if (isPassableTerrain(thisObjectX+1, thisObjectY-1) && 
					isPassableTerrain(thisObjectX+thisObjectW-1, thisObjectY-1)){
				setOnGround(false);
			}
		}
		return hits;
	}


	/**
	 * This method sets the CollisionVel
	 * 
	 * @param 	vel
	 * 			The velocity to be set
	 * @post	...
	 * 			| CollisionVel = vel;
	 * 
	 */
	protected void setCollisionVel(Vector vel){
		CollisionVel = vel;
	}

	/**
	 * This method returns the CollisionVel
	 * 
	 * @return	Vector
	 * 			| return CollisionVel;
	 */
	protected Vector getCollisionVel(){
		return CollisionVel;
	}


	/**
	 * This method sets the CollisionPos
	 * 
	 * @param 	pos
	 * 			The new position to be set
	 * @post	...
	 * 			| CollisionPos = pos
	 */
	protected void setCollisionPos(Vector pos){
		CollisionPos = pos;
	}


	/**
	 * This method returns the positions of collision
	 * 
	 * @return	Vector
	 * 			| return CollisionPos;
	 */
	protected Vector getCollisionPos(){
		return CollisionPos;
	}


	/**
	 * This method sets the new time dt
	 * 
	 * @param 	smalldt
	 * 			The new small dt
	 * 
	 * @post	...
	 * 			| CollisionSmalldt = smalldt;
	 */
	protected void setCollisionSmalldt(double smalldt){
		CollisionSmalldt = smalldt;
	}


	/**
	 * This method returns the small dt
	 * 
	 * @return	...
	 * 			return CollisionSmalldt
	 */
	protected double getCollisionSmalldt(){
		return CollisionSmalldt;
	}

	private Vector CollisionPos;
	private Vector CollisionVel;
	private double CollisionSmalldt;


	/**
	 * This method updates the HP of an object
	 * depending on what kind of tiles the object overlaps
	 * 
	 * @param 	dt
	 * 			The time of advance Time
	 * 			This if for the counter
	 * 
	 * @effect	Every 0.2 seconds in water
	 * 			| addByHP(-2)
	 * 			
	 * @effect	Every 0.2 secons in Magma
	 * 			| addByHP(-50)
	 * 
	 */
	protected void updateHPTile(double dt){
		int thisObjectX = (int) getPos().getElemx();
		int thisObjectY = (int) getPos().getElemy();
		int thisObjectW = (int) getSize().getElemx();
		int thisObjectH = (int) getSize().getElemy();


		int [][] tiles = getWorld().getTilePositionsIn(thisObjectX, thisObjectY,
				thisObjectX + thisObjectW, thisObjectY +thisObjectH);

		HashSet<Integer> TileTypes = new HashSet<Integer>();


		for (int[] tile: tiles){
			int X = getWorld().convertXTYTtoXY(tile[0], tile[1])[0];
			int Y = getWorld().convertXTYTtoXY(tile[0], tile[1])[1];

			TileTypes.add(getWorld().getGeologicalFeature(X,Y));


		}

		for (int type: TileTypes){
			if (type == 2){
				addStartTimeInTile(dt);
				if (getStartTimeInTile()>=0.2){
					addByHP(-2);
					resetStarttimeInTile();
					break;
				}else{
					break;
				}
			}else if (type == 3){
				addStartTimeInTile(dt);
				if (getStartTimeInTile()>=0.2){
					addByHP(-50);
					resetStarttimeInTile();
					break;
				}else{
					break;
				}
			}
		}



	}


	/**
	 * This method adds time to the counter of StartTimeInTile
	 * 
	 * @param 	dt
	 * 			The new amount to be added
	 * @effect	...
	 * 			| StartTimeInTile = StartTimeInTile + dt;
	 */
	protected void addStartTimeInTile(double dt){
		StartTimeInTile = StartTimeInTile + dt;
	}

	/**
	 * This method returns the StartTimeInTile
	 * 
	 * @return	...
	 * 			| return StartTimeInTile
	 * 
	 */
	protected double getStartTimeInTile(){
		return StartTimeInTile;
	}


	/**
	 * This method resets the timer of StartTimeInTile
	 * 
	 * @post	...
	 * 			| StartTimeInTile = 0;
	 */
	protected void resetStarttimeInTile(){
		StartTimeInTile = 0;
	}

	private double StartTimeInTile = 0;

	/**
	 * This method sets if an object is on ground
	 * 
	 * @param 	x
	 * 			The boolean value to be set
	 * @post	...
	 * 			| OnGround = x;
	 */
	protected void setOnGround(boolean x){
		OnGround = x;
	}

	/**
	 * This method returns if an object is on ground
	 * 
	 * @return	boolean
	 * 			| return OnGround;
	 */
	public boolean isOnGround(){
		return OnGround;
	}
	private boolean OnGround;


	/**
	 * This method returns the position of an object
	 * 
	 * @return Integer: value of the bottom left pixel of the GameObject on the x-axis.
	 *                      | result == this.PosX
	 */
	public Vector getPos() {
		return new Vector(this.Pos.getElemx(),this.Pos.getElemy());
	}

	/**
	 * 
	 * This method set the given position for this.object
	 * 
	 * @param  	pos
	 *     		Integer value of the bottom left pixel of the GameObject 
	 *     		on the x-axis that has to be set on this.pos.
	 * @post 	The variable pos is set to the given pos.
	 *    		| this.Pos = new Vector(pos.getElemx(),pos.getElemy());
	 * @throws	OutOfBoundsException(pos)
	 *   		If the given position is out of the dimensions of the world
	 *   		an OutOfBoundsException is thrown
	 */
	protected void setPos(Vector pos) throws OutOfBoundsException {
		if (isValidPos(pos)){
			this.Pos = new Vector(pos.getElemx(),pos.getElemy());
		}
	}


	/**
	 * This method checks if a given position is valid for this.object
	 * 
	 * @param 	pos
	 * 			The position to be investigated
	 * @return	boolean:
	 * 			| if getWorld() == null
	 * 			| 	return (pos.getElemx() >= 0) && (pos.getElemy() >= 0);
	 * 			| else if(
	 * 			| return (pos.getElemx() >= 0) && (pos.getElemy() >= 0) 
	 *			| && pos.getElemx() < this.getWorld().getWorldSizeInPixels()[0]
	 *			|		&& pos.getElemy() < this.getWorld().getWorldSizeInPixels()[1]){
	 *			| 	return true;
	 *			| else
	 *			|	return false;
	 * @throws 	OutOfBoundsException
	 * 			If the position is not valid an exception is thrown
	 */
    protected boolean isValidPos(Vector pos) throws OutOfBoundsException {
    	 
        if (getWorld() == null) {
                if (pos.getElemx() >= 0 && pos.getElemy() >= 0){
                        return true;
                } else{
                        throw new OutOfBoundsException(new Vector(0,0));
                }
        }else if ((pos.getElemx() >= 0) && (pos.getElemy() >= 0)
                        && pos.getElemx() < this.getWorld().getWorldSizeInPixels()[0]
                                        && pos.getElemy() < this.getWorld().getWorldSizeInPixels()[1]){
                return true;
        }else{
                return false;
        }

}

	/**
	 * This method returns if this.object is out of the borders of the world
	 * 
	 * @return	boolean
	 * 			| if (PosX < 0 || PosY < 0){
	 *			|	return true;
	 *			| } else if (PosX + Width > WorldWidth || PosY + Height > WorldHeight){
	 *			|	return true;
	 *			| } else{
	 *			|	return false;
	 *			| }
	 *			
	 */
	protected boolean isOutOfBounds() {

		int WorldWidth = (int) getWorld().getWorldSize().getElemx();
		int WorldHeight = (int) getWorld().getWorldSize().getElemy();
		int Width = (int) getSize().getElemx();
		int Height= (int) getSize().getElemy();
		int PosX = (int) getPos().getElemx();
		int PosY = (int) getPos().getElemy();

		if (PosX < 0 || PosY < 0){
			return true;
		} else if (PosX + Width > WorldWidth || PosY + Height > WorldHeight){
			return true;
		} else{
			return false;
		}
	}


	private Vector Pos;

	/**
	 *
	 * @return A list of 2 integers, the height and width of the current sprite.
	 *                      The values are set in getCurrentSprite.
	 *                      | result == {this.getHeight(),this.getWidth()}
	 */
	public Vector getSize(){
		return new Vector(this.Size.getElemx(),this.Size.getElemy());
	}


	/**
	 * This method sets the size of an object
	 * 
	 * @param 	size
	 * 			The new size to be set
	 * @post	...
	 * 			| this.Size = new Vector(size.getElemx(),size.getElemy());
	 * @throws 	IllegalArgumentException
	 * 			If the size is nog valid an exception is thrown
	 */
	protected void setSize(Vector size) throws IllegalArgumentException {
		if (isValidSize(size)){
			this.Size = new Vector(size.getElemx(),size.getElemy());
		}
	}


	/**
	 * This method returns if size of an object is valid
	 * 
	 * @param 	size
	 * 			The size to be investigated
	 * @return	boolean
	 * 			| if ((width > 0) && (height > 0)){
	 *			|	return true;
	 * @throws 	IllegalArgumentException
	 * 			If the size is not valid an exception is thrown
	 */
	private boolean isValidSize(Vector size) throws IllegalArgumentException{
		double width = size.getElemx();
		double height = size.getElemy();
		if ((width > 0) && (height > 0)){
			return true;
		}else{
			throw new IllegalArgumentException();
		}
	}

	private Vector Size;



	/**
	 * This method returns the velocity of this.object
	 * 
	 * @return	Vector
	 * 			| return new Vector(Velocity.getElemx(),Velocity.getElemy());
	 */
	@Basic
	public Vector getVelocity() {
		return new Vector(Velocity.getElemx(),Velocity.getElemy());
	}


	/**
	 * This method sets a new velocity to an object
	 * 
	 * @param 	velocity
	 * 			The new velocity to be set
	 * @throws 	IllegalVelocityException
	 * 			If the Velocity is not valid an exception is thrown
	 * @post	...
	 * 			| Velocity = new Vector(velocity.getElemx(),velocity.getElemy());
	 */
	protected void setVelocity(Vector velocity) throws IllegalVelocityException{

		if (isValidVelocity(velocity)){
			Velocity = new Vector(velocity.getElemx(),velocity.getElemy());
		}	

	}


	/**
	 * This method checks if the given velocity is legal for this.object
	 * 
	 * @param 	velocity
	 * 			The velocity to be investigated
	 * @return	boolean
	 * 			| return true;
	 * @throws 	IllegalVelocityException
	 * 			if the velocity is not valid an exception is thrown
	 */
	protected boolean isValidVelocity(Vector velocity)
			throws IllegalVelocityException {
		if (velocity.getElemx() > getMaxVelocityX()){
			throw new IllegalVelocityException(new Vector(getMaxVelocityX(),velocity.getElemy()));		
		}
		if (velocity.getElemx() < -getMaxVelocityX()){
			throw new IllegalVelocityException(new Vector(-getMaxVelocityX(),velocity.getElemy()));		
		}
		return true;
	}

	/**
	 * This method returns the maximum velocity of an object
	 * 
	 * @return	Vector
	 * 			|
	 */
	protected abstract double getMaxVelocityX();

	private Vector Velocity = new Vector(0.0,0.0);

	/**
	 * This method returns the current acceleration of this object
	 * 
	 * @return	Vector
	 * 			| return new Vector(AccCurr.getElemx(),AccCurr.getElemy());
	 */
	@Basic
	public Vector getAccCurr() {
		return new Vector(AccCurr.getElemx(),AccCurr.getElemy());
	}


	/**
	 * This method sets an acceleration for this.object
	 * 
	 * @param 	accCurr
	 * 			The new acceleration to be set
	 * 
	 * @post	...
	 * 			| AccCurr = new Vector(accCurr.getElemx(),accCurr.getElemy());
	 * 
	 */
	protected void setAccCurr(Vector accCurr) {
		AccCurr = new Vector(accCurr.getElemx(),accCurr.getElemy());
	}

	private Vector AccCurr = new Vector(0.0,0.0);

	/**
	 * This method returns the sprite of this.object at the moment
	 * 
	 * @return	Sprite: depending on the movement the right index i is chosen.
	 * 			| return sprites[i];
	 */
	public abstract Sprite getCurrentSprite();

	/**
	 * This method returns the orientation of the object
	 * 
	 * @return      Character: 'R' means the GameObject is facing right, 'L' means the GameObject is facing left and
	 *                      'X' means the GameObject is facing the screen and isn't moving to the left or to the right.
	 *                      | result == this.Orientation
	 */
	@Basic
	public char getOrientation() {
		return Orientation;
	}

	/**
	 *
	 * @param       orientation
	 *                      A character which indicates where the GameObject is facing. 'R' means the GameObject is facing right,
	 *                      'L' means the GameObject is facing left and 'X' means the GameObject is facing the screen and isn't moving.
	 * @pre         Orientation will only be 'R','L' or 'X'. Other characters won't be set using this method.
	 *                      | orientation == 'R' || orientation == 'X' || orientation == 'L'
	 * @post        The variable Orientation is set to the given character orientation.
	 *                      | new.getLastMove() == lastMove
	 */
	protected void setOrientation(char orientation) {
		Orientation = orientation;
	}

	private char Orientation = 'X';


	/**
	 * This method sets an object on dying
	 * 
	 * @effect	...
	 * 			| setDying(true);
	 * 
	 * @effect	if the object if a plant of mazub no delay is called up
	 * 			| setDying(true);
	 */
	protected void dies(){
		setDying(true);
		if (this instanceof Mazub)
			setDead(true);
		else{
			new java.util.Timer().schedule( 
					new java.util.TimerTask() {
						@Override
						public void run() {
							setDead(true);		            
						}
					}, 
					600 
					);
		}

	}


	/**
	 * This method sets Dying on true
	 * 
	 * @param 	status
	 * 			the new value of the object
	 * @post	...
	 * 			| Dying = status;
	 */
	protected void setDying(boolean status){
		Dying = status;
	}


	/**
	 * This method returns if an object is dying
	 * 
	 * @return	boolean
	 * 			| return Dying;
	 */
	protected boolean isDying(){
		return Dying;
	}

	private boolean Dying = false;

	/**
	 * This method returns if an object is dead
	 * 
	 * @return	boolean
	 * 			| return true;
	 * 			| or
	 * 			| return false;
	 */
	public boolean isDead() {
		if (isDead){
			return true;
		} else{
			return false;
		}
	}

	/**
	 * This method set an object to dead
	 * 
	 * @param 	isDead
	 * 			the value to be set
	 * @post	...
	 * 			| this.isDead = isDead;
	 */
	private void setDead(boolean isDead) {
		this.isDead = isDead;
	}

	private boolean isDead = false;


	/**
	 * This method sets the startTime
	 * @post	...
	 * 			| StartTime = getWorld().getTime();
	 */
	protected void setStartTime(){
		StartTime = getWorld().getTime();
	}


	/**
	 * This method returns the StartTime
	 * 
	 * @return	double
	 * 			| return StartTime;
	 */
	public double getStartTime(){
		return StartTime;
	}


	/**
	 * This method returns time that has been timed
	 * starting from StarTime
	 * 
	 * @param 	StartTime
	 * 			The point we have started timing
	 * @return	double
	 * 			| return getWorld().getTime()-StartTime;
	 */
	public double getTimeTimedCurr(double StartTime){
		return getWorld().getTime()-StartTime;
	}

	public double StartTime;
	public double TimeTimed;
	public double TimeDying;
	
	
	
	
	/////////////////////////////////
	
	
//	public abstract double getRightVelocity();
//	public abstract double getLeftVelocity();
//	public abstract double getJumpVelocity();
//	public abstract double getRightAcc();
//	public abstract double getLeftAcc();
//	
//	
//	
//	public void startMoveProgram(boolean direction){
//		
//		if(direction){
//			this.setVelocity(new Vector(getRightVelocity(),getVelocity().getElemy()));
//			this.setAccCurr(new Vector(getRightAcc(),getAccCurr().getElemy()));
//			this.setOrientation('R');
//		}
//		else{
//			this.setVelocity(new Vector(getLeftVelocity(),getVelocity().getElemy()));
//			this.setAccCurr(new Vector(getLeftAcc(),getAccCurr().getElemy()));
//			this.setOrientation('L');
//		}
//	}
//	public void stopMoveProgram(){
//		this.setVelocity(new Vector(0,getVelocity().getElemy()));
//		this.setAccCurr(new Vector(0,getAccCurr().getElemy()));
//		this.setOrientation('X');
//	}
//	public void startJumpProgram(){
//		this.setVelocity(new Vector(getVelocity().getElemx(),getJumpVelocity()));
//		this.setAccCurr(new Vector(getAccCurr().getElemx(),ACCY));
//	}
//	public void stopJumpProgram(){
//		this.setVelocity(new Vector(getVelocity().getElemx(),0));
//		this.setAccCurr(new Vector(getAccCurr().getElemx(),0));
//	}
	
	public Program getProgram() {
		return program;
	}
	private final Program program;
	
	public abstract void startMoveProgram(Boolean direction);
	public abstract void stopMoveProgram();
	
	
	public abstract void startDuckProgram();
	public abstract void stopDuckProgram();
	
	public abstract void startJumpProgram();
	public abstract void stopJumpProgram();
	
	private static final double ACCY = -10.0;


	
	
	
	
	
	
	




}
