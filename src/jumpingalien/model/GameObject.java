package jumpingalien.model;



import java.util.HashSet;

import jumpingalien.exception.IllegalVelocityException;
import jumpingalien.exception.OutOfBoundsException;
import jumpingalien.util.Sprite;
import jumpingalien.util.Vector;
import be.kuleuven.cs.som.annotate.*;

public abstract class GameObject {
	public GameObject() {

		initializeHP();
		setVelocity(new Vector(0,0));
	}


	/**
	 * Return a boolean indicating whether or not this game object
	 * is terminated.
	 */
	@Basic @Raw
	protected boolean isTerminated() {
		return this.isTerminated;
	}

	/**
	 * Terminate this object.
	 *
	 * @post    This object is terminated.
	 *          | new.isTerminated()
	 */
	protected abstract void terminate();

	protected boolean isTerminated;

	/**
	 * Return the world to which this object belongs.
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
				&& ((getWorld() != null) || (getWorld().hasAsObject(this)));
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
		else
			this.world = null;
	}

	private World world = null;
	protected abstract void initializeHP();
	protected abstract int getMaxHP();

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

	protected boolean isValidHP(int hp){
		return hp > 0 && hp <= getMaxHP();
	}
	public int getHP() {
		return HP;
	}
	protected void setHP(int hP) {
		HP = hP;
	}
	private int HP;

	protected abstract void advanceTime(double dt) throws IllegalArgumentException;

	protected void UpdateAccY(){

		if (isOnGround()){
			if (collisionObject().isEmpty()){
				if (isPassableTerrain(getPos().getElemx()+1, getPos().getElemy()-1) && 
						isPassableTerrain(getPos().getElemx()+getSize().getElemx()-1, getPos().getElemy()-1))
					setOnGround(false);
			}
		}

		if (!isOnGround()){
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


	protected boolean isPassableTerrain(double posx,double posy){
		World world = this.getWorld();
		int feature = world.getGeologicalFeature((int)posx, (int)posy);
		if (feature == 1)
			return false;
		else
			return true;

	}

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
				//System.out.println("OBJECT: NIET TEGEN TILE GEBOTST");
				hits = collisionObject();
			}else{
				//System.out.println("OBJECT: TEGEN TILE GEBOTST");
				return hits;
			}

			if (!hits.isEmpty()){
				//System.out.println("OBJECT: TEGEN OBJECT GEBOTST");
				return hits;

			}else{
				//System.out.println("OBJECT: NIET TEGEN TILE OF OBJECT GEBOTST");

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


	protected abstract HashSet<String> collisionObject();

	//		HashSet<String> hits = new HashSet<String>();
	//
	//		double thisObjectX = getCollisionPos().getElemx();
	//		double thisObjectY = getCollisionPos().getElemy();
	//		double thisObjectW = getSize().getElemx();
	//		double thisObjectH = getSize().getElemy();
	//
	//		Set<Shark> Sharks = this.getWorld().getAllSharks();
	//		Set<Slime> Slimes = this.getWorld().getAllSlimes();
	//
	//		Mazub mazub = getWorld().getMazub();
	//
	//		double mazubX = mazub.getPos().getElemx();
	//		double mazubY = mazub.getPos().getElemy();
	//		double mazubW = mazub.getSize().getElemx();
	//		double mazubH = mazub.getSize().getElemy();
	//		if ((thisObjectX + (thisObjectW-1) >= mazubX) && (mazubX + (mazubW-1) >= thisObjectX)
	//				&& (thisObjectY +(thisObjectH-1) >= mazubY) && (mazubY + (mazubH-1) >= thisObjectY)){
	//
	//			if (((int)thisObjectX+2== (int)(mazubX + mazubW)) || 
	//					(int)mazubX+2== (int)(thisObjectX + thisObjectW)){
	//				//system.out.println("--X");
	//				//object.terminate();
	//				hits.add("X");
	//			}
	//			if (((int)(thisObjectY + thisObjectH) == (int)mazubY+2) || 
	//					((int)(mazubY + mazubH) == (int)thisObjectY+2)){
	//				//system.out.println("--Y");
	//				//object.terminate();
	//				hits.add("Y");
	//			}	
	//
	//		}
	//
	//		for (Shark object: Sharks){
	//			if (object != this){
	//				double objectX = object.getPos().getElemx();
	//				double objectY = object.getPos().getElemy();
	//				double objectW = object.getSize().getElemx();
	//				double objectH = object.getSize().getElemy();
	//				if ((thisObjectX + (thisObjectW-1) >= objectX) && (objectX + (objectW-1) >= thisObjectX)
	//						&& (thisObjectY +(thisObjectH-1) >= objectY) && (objectY + (objectH-1) >= thisObjectY)){
	//
	//					if (((int)thisObjectX+2== (int)(objectX + objectW)) || 
	//							(int)objectX+2== (int)(thisObjectX + thisObjectW)){
	//						//system.out.println("--X");
	//						//object.terminate();
	//						hits.add("X");
	//					}
	//					if (((int)(thisObjectY + thisObjectH) == (int)objectY+2) || 
	//							((int)(objectY + objectH) == (int)thisObjectY+2)){
	//						//system.out.println("--Y");
	//						//object.terminate();
	//						hits.add("Y");
	//					}	
	//				}
	//			}
	//		}
	//		//////////////
	//
	//		for (Slime object: Slimes){
	//			if (object != this){
	//				double objectX = object.getPos().getElemx();
	//				double objectY = object.getPos().getElemy();
	//				double objectW = object.getSize().getElemx();
	//				double objectH = object.getSize().getElemy();
	//				if ((thisObjectX + (thisObjectW-1) >= objectX) && (objectX + (objectW-1) >= thisObjectX)
	//						&& (thisObjectY +(thisObjectH-1) >= objectY) && (objectY + (objectH-1) >= thisObjectY)){
	//
	//					if (((int)thisObjectX+2== (int)(objectX + objectW)) || 
	//							(int)objectX+2== (int)(thisObjectX + thisObjectW)){
	//						hits.add("X");
	//					}
	//					if (((int)(thisObjectY + thisObjectH) == (int)objectY+2) || 
	//							((int)(objectY + objectH) == (int)thisObjectY+2)){
	//						hits.add("Y");
	//
	//
	//					}	
	//				}
	//			}
	//		}
	//		//////////////
	//
	//		return hits;
	//	}
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

	protected boolean overlapsWithY(GameObject object){
		double thisObjectY = getCollisionPos().getElemy();
		double thisObjectH = getSize().getElemy();
		double objectY = object.getPos().getElemy();
		double objectH = object.getSize().getElemy();

		return (((int)(thisObjectY + thisObjectH) == (int)objectY+2) || 
				((int)(objectY + objectH) == (int)thisObjectY+2));
	}

	protected boolean overlapsWithX(GameObject object){

		double thisObjectX = getCollisionPos().getElemx();
		double thisObjectW = getSize().getElemx();
		double objectX = object.getPos().getElemx();
		double objectW = object.getSize().getElemx();


		return (((int)thisObjectX+2== (int)(objectX + objectW)) || 
				(int)objectX+2== (int)(thisObjectX + thisObjectW));
	}

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
			setPos(new Vector(thisObjectX,thisObjectY+i));
		}
	}

	protected HashSet<String> collisionTile(){

		HashSet<String> hits = new HashSet<String>();

		int thisObjectX = (int) getCollisionPos().getElemx();
		int thisObjectY = (int) getCollisionPos().getElemy();
		int thisObjectW = (int) getSize().getElemx();
		int thisObjectH = (int) getSize().getElemy();


		if (!isPassableTerrain(thisObjectX+1, thisObjectY)){
			hits.add("Y");
			//System.out.println("Collision Y 1");
			setOnGround(true);
		}
		else if (!isPassableTerrain(thisObjectX+thisObjectW-1, thisObjectY)){
			hits.add("Y");
			//System.out.println("Collision Y 2");
			setOnGround(true);
		}
		else if (!isPassableTerrain(thisObjectX+thisObjectW-1, thisObjectY+thisObjectH)){
			hits.add("Y");
			//System.out.println("Collision Y 3");
		}
		else if (!isPassableTerrain(thisObjectX+1, thisObjectY+thisObjectH)){
			hits.add("Y");

			//System.out.println("Collision Y 4");
		}

		if (!isPassableTerrain(thisObjectX+thisObjectW-1, thisObjectY+1)){
			hits.add("X");
			//System.out.println("Collision X 1");
		}
		else if (!isPassableTerrain(thisObjectX+thisObjectW-1, thisObjectY+(thisObjectH/2))){
			hits.add("X");
			//System.out.println("Collision X MID R");
		}
		else if (!isPassableTerrain(thisObjectX+1, thisObjectY+(thisObjectH/2))){
			hits.add("X");
			//System.out.println("Collision X MID L");
		}
		else if (!isPassableTerrain(thisObjectX+thisObjectW-1, thisObjectY+thisObjectH-1)){
			hits.add("X");
			//System.out.println("Collision X 2");
		}
		else if (!isPassableTerrain(thisObjectX, thisObjectY+thisObjectH-1)){
			hits.add("X");
			//System.out.println("Collision X 3");
		}
		else if (!isPassableTerrain(thisObjectX, thisObjectY+1)){
			hits.add("X");
			//System.out.println("Collision X 4");
		}

		if (hits.isEmpty()){

			if (!isPassableTerrain(thisObjectX+1, thisObjectY+1)){
				hits.add("X");
				hits.add("Y");
				//System.out.println("Collision XY 1");
			}
			if (!isPassableTerrain(thisObjectX+thisObjectW-1, thisObjectY+thisObjectH-1)){
				hits.add("X");
				hits.add("Y");
				//System.out.println("Collision XY 2");
			}
			if (!isPassableTerrain(thisObjectX+1, thisObjectY+thisObjectH-1)){
				hits.add("X");
				hits.add("Y");
				//System.out.println("Collision XY 3");
			}
			if (!isPassableTerrain(thisObjectX+thisObjectW-1, thisObjectY+1)){
				hits.add("X");
				hits.add("Y");
				//System.out.println("Collision XY 4");
			}

		}


		if (!hits.contains("Y") && isOnGround()){
			//System.out.println("On ground and doens't collide with Y");
			if (isPassableTerrain(thisObjectX+1, thisObjectY-1) && 
					isPassableTerrain(thisObjectX+thisObjectW-1, thisObjectY-1)){
				//System.out.println("Not on ground");
				setOnGround(false);
			}
		}
		return hits;
	}

	protected void setCollisionVel(Vector vel){
		CollisionVel = vel;
	}

	protected Vector getCollisionVel(){
		return CollisionVel;
	}

	protected void setCollisionPos(Vector pos){
		CollisionPos = pos;
	}

	protected Vector getCollisionPos(){
		return CollisionPos;
	}

	protected void setCollisionSmalldt(double smalldt){
		CollisionSmalldt = smalldt;
	}

	protected double getCollisionSmalldt(){
		return CollisionSmalldt;
	}

	private Vector CollisionPos;
	private Vector CollisionVel;
	private double CollisionSmalldt;


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

	protected void addStartTimeInTile(double dt){
		StartTimeInTile = StartTimeInTile + dt;
	}

	protected double getStartTimeInTile(){
		return StartTimeInTile;
	}

	protected void resetStarttimeInTile(){
		StartTimeInTile = 0;
	}

	private double StartTimeInTile = 0;
	protected void setOnGround(boolean x){
		OnGround = x;
	}
	protected boolean isOnGround(){
		return OnGround;
	}
	private boolean OnGround;


	/**
	 *
	 * @return Integer: value of the bottom left pixel of the GameObject on the x-axis.
	 *                      | result == this.PosX
	 */
	public Vector getPos() {
		return new Vector(this.Pos.getElemx(),this.Pos.getElemy());
	}

	/**
	 * @param       pos
	 *                      Integer value of the bottom left pixel of the GameObject on the x-axis that has to be set on this.pos.
	 * @post        The variable pos is set to the given pos.
	 *                      | new.getpos() == pos
	 * @throws      OutOfBoundsException(pos)
	 *                      The given position is out of bounds. It has to be on the screen, between a value of 0 and 1023.
	 *                      | (pos > 1023) || (pos < 0)
	 */
	protected void setPos(Vector pos) throws OutOfBoundsException {
		if (isValidPos(pos)){
			this.Pos = new Vector(pos.getElemx(),pos.getElemy());
		}
	}

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

	protected void setSize(Vector size) throws IllegalArgumentException {
		if (isValidSize(size)){
			this.Size = new Vector(size.getElemx(),size.getElemy());
		}
	}

	protected boolean isValidSize(Vector size) throws IllegalArgumentException{
		double width = size.getElemx();
		double height = size.getElemy();
		if ((width > 0) && (height > 0)){
			return true;
		}else{
			throw new IllegalArgumentException();
		}
	}

	private Vector Size;




	@Basic
	public Vector getVelocity() {
		return new Vector(Velocity.getElemx(),Velocity.getElemy());
	}

	protected void setVelocity(Vector velocity) throws IllegalVelocityException{

		if (isValidVelocity(velocity)){
			Velocity = new Vector(velocity.getElemx(),velocity.getElemy());
		}	

	}

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
	protected abstract double getMaxVelocityX();

	private Vector Velocity = new Vector(0.0,0.0);

	@Basic
	public Vector getAccCurr() {
		return new Vector(AccCurr.getElemx(),AccCurr.getElemy());
	}

	protected void setAccCurr(Vector accCurr) {
		AccCurr = new Vector(accCurr.getElemx(),accCurr.getElemy());
	}

	private Vector AccCurr = new Vector(0.0,0.0);

	public abstract Sprite getCurrentSprite();

	/**
	 *
	 * @return      Character: 'R' means the GameObject is facing right, 'L' means the GameObject is facing left and
	 *                      'X' means the GameObject is facing the screen and isn't moving to the left or to the right.
	 *                      | result == this.Orientation
	 */
	@Basic
	protected char getOrientation() {
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


	protected void setDying(boolean status){
		Dying = status;
	}

	protected boolean isDying(){
		return Dying;
	}

	private boolean Dying = false;

	public boolean isDead() {
		if (isDead){
			return true;
		} else{
			return false;
		}
	}

	private void setDead(boolean isDead) {
		this.isDead = isDead;
	}

	private boolean isDead = false;

	protected void setStartTime(){
		StartTime = getWorld().getTime();
	}

	public double getStartTime(){
		return StartTime;
	}

	public double getTimeTimedCurr(double StartTime){
		return getWorld().getTime()-StartTime;
	}

	protected double StartTime;
	protected double TimeTimed;
	protected double TimeDying;




}