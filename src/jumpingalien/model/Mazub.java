package jumpingalien.model;

import java.util.HashSet;
import java.util.Set;

import program.Program;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import jumpingalien.exception.IllegalVelocityException;
import jumpingalien.exception.OutOfBoundsException;
import jumpingalien.util.Sprite;
import jumpingalien.util.Vector;

/**
 * @Invar	The x and y position of the most left and bottom pixel of Mazub should always be onscreen.
 *			| isValidPos(getPos());
 * @Invar	The x velocity of Mazub should always be higher than
 * 			the initial velocity and lower than the maximum velocity
 * 			| isValidVelocity();
 * @Invar	The maximum x velocity needs to be at least the initial velocity.
 * 			| getMaxVelocityX() >= getInitVelocityX()
 * @Invar	The initial velocity on the x axis should always be at least 1m/s.
 * 			| getInitVelocityX() >= 1m/s
 * @Invar	The size of the used sprite should always be stored in Width and Height.
 * 			| getSize() == {getWidth(),getHeight()}
 * @author 	Sven Verret & Kevin Zhang
 * @version 2.0
 */

public class Mazub extends GameObject{


	/**
	 * 
	 * @param 	pixelLeftX
	 * 			The position of the most left pixel of Mazub on the x-axis.
	 * @param 	pixelBottomY
	 * 			The position of the most left pixel of Mazub on the y-axis.
	 * @param 	sprites
	 * 			A list of images that will be used to represent a Mazub class in the GUI.
	 * @post 	Initial height and width are set, based on the initial sprite.
	 * 			| new.getHeight() == sprites[0].getHeight()
	 * 			| new.getWidth() == sprites[0].getWidth()
	 * @post 	Maximum value for the velocity of Mazub is set.
	 * 			| new.getMaxVelocityX() == maxvelocityx
	 * 			| new.getMaxVelocityXCurr() == maxvelocityx
	 * @post 	Minimum value for the velocity of Mazub is set.
	 * 			| new.getInitVelocityX() == initvelocityx
	 * @post	The list of sprites used for a Mazub class are now stored in Sprites.
	 * 			| new.Sprites = sprites
	 * @effect 	PixelLeftX is set as the position on the x-axis. PixelBottomY is set as the position on the y-axis.
	 * 			If these positions are not on the screen, setPosX or setPoxY will throw an OutOfBoundsException.
	 * 			| setPosX(pixelLeftX) && setPosY(pixelBottomY)			
	 * @throws 	IllegalArgumentException("Sprites")
	 * 			An exception is thrown when the list of sprites is shorter than 10. 
	 * 			Some states of Mazub won't have images if the list is too short.
	 * 			| sprites.length < 10
	 * @throws 	IllegalArgumentException("Velocity")
	 * 			An exception is thrown when the initial velocity is lower than 1m/s or
	 * 			when the maximum velocity is lower than the initial velocity.
	 * 			| (initvelocityx < 1.0) || (maxvelocityx < initvelocityx)
	 * 
	 */
	public Mazub(int pixelLeftX, int pixelBottomY, Sprite[] sprites, double initvelocityx,
			double maxvelocityx, Program program)
					throws IllegalArgumentException, OutOfBoundsException{
		super(program);

		this.setPos(new Vector(pixelLeftX,pixelBottomY+1));

		if ((initvelocityx >= 1.0) && (maxvelocityx >= initvelocityx)){
			this.MaxVelocityX = maxvelocityx;
			this.InitVelocityX = initvelocityx;
			this.setMaxVelocityXCurr(MaxVelocityX);
		} else
			throw new IllegalArgumentException("Velocity");

		if (sprites.length < 10 || (sprites.length)%2 != 0 || sprites == null)
			throw new IllegalArgumentException("Sprites");
		else
			this.Sprites = sprites;

		this.setAccCurr(new Vector(0.0,0.0));

		this.setSize(new Vector(sprites[0].getWidth(),sprites[0].getHeight()));
	}

	public Mazub(int pixelLeftX, int pixelBottomY,Sprite[] sprites, program.Program program){
		this(pixelLeftX, pixelBottomY, sprites, 1.0,3.0, program);
	}

	public Mazub(int pixelLeftX, int pixelBottomY,Sprite[] sprites){
		this(pixelLeftX, pixelBottomY, sprites, 1.0,3.0,null);

	}


	// Methods

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
		if (world.getMazub() == null){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 
	 * @param 	direction
	 * 			The direction in which Mazub has to move.
	 * 
	 * @pre		Every Mazub must have at least a minimum velocity of 1m/s
	 * 			| getInitVelocityX() >= 1m/s
	 * 
	 * @post	If the given direction equals true, orientation is set to 'R' from Right.
	 * 			The velocity of Mazub changes to the inital velocity, the minimum velocity.
	 * 			The acceleration changes from zero to a new acceleration based the first dt
	 * 			and laws of Newton. 
	 * 			Else the orientation is set to L from Left, the velocity becomes negative.
	 * 			Because acceleration has to be positive at any time, this remains the same as
	 * 			in the situation to the Right Side.
	 * 			| if direction == true:
	 * 			|	new.getOrientation() == 'R'
	 * 			|	new.getVelocity().getElemx() == new.getInitVelocityX()
	 * 			|	new.getAccXCurr() == new.getAccXFwd
	 * 			| else:
	 * 			|	new.getOrientation() == 'L'
	 * 			|	new.getVelocity().getElemx() == -1*new.getInitVelocityX()
	 * 			|	new.getAccXCurr() == new.getAccXBkw()
	 * @post	the sum of dt is reset to 0s.	
	 * 			| new.getSumdt() == 0.0
	 * 
	 */
	public void startMove(boolean direction){

		setStartTime();

		if (direction == true){

			if (getPos().getElemx() != getWorld().getWorldSizeInPixels()[0] ){
				this.setOrientation('R');
				this.setLastMove('R');
				this.setVelocity(new Vector(this.getInitVelocityX(),getVelocity().getElemy()));
				this.setAccCurr(new Vector(this.getAccXFwd(), getAccCurr().getElemy()));
			}
		}
		else{

			if (getPos().getElemx() != 0){
				this.setOrientation('L');
				this.setLastMove('L');
				this.setVelocity(new Vector(-1*this.getInitVelocityX(),getVelocity().getElemy()));
				this.setAccCurr(new Vector(this.getAccXBkw(), getAccCurr().getElemy()));
			}
		}



	}

	/**
	 * 
	 * @pre 	The current x-velocity has to be bigger than a given initial velocity,
	 * 			but it doesn't matter in which direction.
	 * 			| abs(this.getVelocity().getElemx()) >= this.getInitVelocityX();
	 * 
	 * @pre		The current x-velocity has to be less than the current maximum velocity,
	 * 			but it doesn't matter in which direction.
	 * 			| abs(this.getVelocity().getElemx()) <= this.getMaxVelocityXCurr();
	 * 
	 * @post	The direction of the moving alien is saved in the variable LastMove, but only if
	 * 			the alien isn't in the corner of the screen. If this is the case, LastMove is set to 'X'.
	 * 			This is because we don't want a sprite of Mazub walking when it is against a wall.
	 * 			| if ((this.getOrientation() == 'L') && (getPos().getElemx() == 0)) &&
	 * 			|	((this.getOrientation() == 'R') && (getPos().getElemx() == 1023))
	 * 			|		new.getLastMove() == 'X'
	 * 			| else
	 * 			| 		new.getLastMove() == this.getOrientation()
	 * 
	 * @post	The new direction of the alien is 'X',
	 * 			which means neither to the left or right.
	 * 			| new.getOrientation() == 'X'
	 * 
	 * @post	The new x-velocity of the alien is zero.
	 * 			| new.getVelocity().getElemx() == 0.0
	 * 
	 * @post	The new x-acceleration of the alien is zero.
	 * 			| new.getAccXCurr() == 0.0
	 * 
	 * @post 	The sum of dt is reset to 0s.
	 * 			| new.getSumdt() == 0.0
	 */
	public void endMove(){
		StartTime = getWorld().getTime();
		if ((this.getOrientation() == 'L'))
			this.setLastMove('L');

		else if ((this.getOrientation() == 'R'))
			this.setLastMove('R');
		else
			this.setLastMove(this.getOrientation());
		this.setOrientation('X');
		this.setVelocity(new Vector(0.0,getVelocity().getElemy()));
		this.setAccCurr(new Vector(0.0,getAccCurr().getElemy()));		


	}

	/**
	 * 
	 * @post	If Mazub was on the ground and Jumped was false, the new y-velocity has been set to a constant 
	 * 			initial value.
	 * 			| if ((this.getPos().getElemy() == 0) && (!this.isJumped()):
	 * 			| 	new.getVelocityY() == INITVELOCITYY
	 * 
	 * @post	Once startJump() is called and if Mazub was on the ground and Jumped was false, 
	 * 			the variable Jumped is set to true.
	 * 			| if ((this.getPos().getElemy() == 0) && (!this.isJumped()):
	 * 			| 	new.getJumped() == true
	 * 
	 */
	public void startJump(){

		if(isEndDuckPressed() && isDucked()){
			this.setJumped(false);

		}else if(isOnGround()){
			this.setVelocity(new Vector(getVelocity().getElemx(),INITVELOCITYY));
			this.setJumped(true);
			this.setAccCurr(new Vector(getAccCurr().getElemx(),ACCY));
			setOnGround(false);

		}
	}

	/**
	 * 
	 * @post	If endJump is correctly called, after startJump, Jumped is always set to false.
	 * 			| new.getJumped() == false
	 * 
	 * @throws 	IllegalStateException()
	 * 			An exception is thrown if endJump is called when Mazub is on the ground and Jumped is false.
	 * 			This method can only be called after startJump, which would make Jumped true.
	 * 			| this.getPos().getElemy() == 0 && !this.getJumped()
	 * 			
	 */
	public void endJump() throws IllegalStateException{
		if (this.isJumped()){
			this.setJumped(false);
		}else if (!this.isJumped() && this.isOnGround() && !isDucked()){
			throw new IllegalStateException();
		}	
	}

	/**
	 * @post	When startDuck is called, Ducked is set to true and Mazub is in ducking state.
	 * 			| new.isDucked() == true
	 * @post	When Mazub is ducking, its maximum x-velocity is limited to 1m/s.
	 * 			| new.getMaxVelocityXCurr() == 1.0
	 */
	public void startDuck(){
		this.setDucked(true);
		this.setMaxVelocityXCurr(1.0);

	}
	/**
	 * @post	After endDuck is called, Ducked is false and Mazub isn't in a ducking state anymore.
	 * 			| new.isDucked() == false
	 * 
	 * @post	The maximum x-velocity is now restored to the initial maximum velocity.
	 * 			| new.getMaxVelocityXCurr() == this.getMaxVelocityX()
	 * @throws 	IllegalStateException()
	 * 			An exception is thrown when endDuck is called if Mazub isn't ducking.
	 * 			| !(isDucked())
	 */
	protected void endDuck() throws IllegalStateException{
		if (isEndDuckPressed()){
			int thisObjectX = (int) getPos().getElemx();
			int thisObjectY = (int) getPos().getElemy();
			int thisObjectW = (int) getSize().getElemx();
			int thisObjectHStand = (int) Sprites[0].getHeight();

			if ((isPassableTerrain(thisObjectX+thisObjectW, thisObjectY+thisObjectHStand+1)) &&
					(isPassableTerrain(thisObjectX, thisObjectY+thisObjectHStand+1))){
				setDucked(false);
				setMaxVelocityXCurr(getMaxVelocityX());
				setEndDuckPressed(false);
			}
			else{
			}
		} else{
			throw new IllegalStateException();
		}
	}


	/**
	 * This method set sets endDuckPressed on true
	 * when the player releases the duck button
	 * @effect	...
	 * 			| setEndDuckPressed(true);
	 * 
	 * @throws 	IllegalStateException
	 * 			if mazub is not ducked and pressEndDuck() is called
	 * 			this method throws an IllegalStateException.
	 */
	public void pressEndDuck() throws IllegalStateException{
		if (this.isDucked()){
			setEndDuckPressed(true);
		}else{
			throw new IllegalStateException();
		}	
	}


	/**
	 * This method returns EndDuckPressed
	 * 
	 * @return	boolean:
	 * 			| return EndDuckPressed;
	 * 
	 */
	public boolean isEndDuckPressed() {
		return EndDuckPressed;
	}

	/**
	 * This method sets EndDuckPressed
	 * 
	 * @param 	endDuckPressed
	 * 			value to be set
	 * @effect	...
	 * 			| EndDuckPressed = endDuckPressed;
	 */
	private void setEndDuckPressed(boolean endDuckPressed) {
		EndDuckPressed = endDuckPressed;
	}

	private boolean EndDuckPressed=false;


	/**
	 * 
	 * 
	 * @effect	when X velocity >=0 and colides in the X direction
	 * 			| setPos(new Vector(getPos().getElemx()-1,getPos().getElemy()));
	 *			| setVelocity(new Vector(0.0,getVelocity().getElemy()));
	 *			| setAccCurr(new Vector(0.0,getAccCurr().getElemy()));
	 *
	 * @effect	when X velocity < 0 and colides in the Y direction
	 * 			| setPos(new Vector(getPos().getElemx()+1,getPos().getElemy()));
	 *			| setVelocity(new Vector(0.0,getVelocity().getElemy()));
	 *			| setAccCurr(new Vector(0.0,getAccCurr().getElemy()));
	 *
	 *@effect	when Y velocity >= 0 and collides in the Y direction
	 *			| setPos(new Vector(getPos().getElemx(),getPos().getElemy()-1));
	 *			| setVelocity(new Vector(getVelocity().getElemx(),0.0));
	 *
	 *@effect	when Y velocity < 0 and collides in the Y direction
	 *			| setPos(new Vector(getPos().getElemx(),getPos().getElemy()+1));
	 *			| setVelocity(new Vector(getVelocity().getElemx(),0.0));
	 *
	 *@effect	Mazub is on ground an has just jumped
	 *			| setOnGround(false);
	 *
	 *
	 */
	@Override
	public void advanceTime(double dt) throws IllegalArgumentException{

		if ((dt < 0.0) || (dt > 0.2)){
			throw new IllegalArgumentException();
		}
		updateHPTile(dt);              
		UpdateAccY();
		if(isEndDuckPressed())
			endDuck();

		if (isImmune()){
			addToImmuneTimer(dt);
			setImmune(false);
		}


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
	 * @effect	Contact with plant and Mazub hasn't reached maxHP
	 * 			| this.addByHP(50);
	 *			| object.dies();
	 * 
	 * @effect	Contact with shark
	 * 			| if (overlapsWithX(object)){
	 *			| 	hits.add("X");
	 *			| }
	 *			| if (overlapsWithY(object)){
	 *			|	hits.add("Y");
	 *			| }
	 * 
	 * @effect	Contact with slime
	 * 			| if (overlapsWithX(object)){
	 *			|	hits.add("X");
	 *			|	}
	 *			| if (overlapsWithY(object)){
	 *			|	hits.add("Y")
	 *			| 	if (!object.isDying()){
	 *			|		object.slimeGetsHitFor(-50);
	 *			|		if (!isImmune()){
	 *			|			this.addByHP(-50);
	 *			|			setImmune(true);
	 *			|		}
	 *			|	}
	 *			| }
	 * 			
	 * 
	 */
	@Override
	protected HashSet<String> collisionObject(){
		HashSet<String> hits = new HashSet<String>();

		Set<Shark> Sharks = this.getWorld().getAllSharks();
		Set<Slime> Slimes = this.getWorld().getAllSlimes();
		Set<Plant> Plants = this.getWorld().getAllPlants();
		
		if(getWorld().getBuzam() != null){
			
			Buzam buzam = this.getWorld().getBuzam();

			if (overlapsWith(buzam)){
				
				if (overlapsWithX(buzam)){
					hits.add("X");
				}
				if (overlapsWithY(buzam)){
					if(this.getVelocity().getElemy() < buzam.getVelocity().getElemy()){
						this.setCollisionVel(new Vector(getVelocity().getElemx() , 3));
					}else{
						hits.add("Y");
					}
					
				}
				if (!buzam.isImmune()){
					buzam.addByHP(-50);
					buzam.setImmune(true);
				}
				if (!this.isImmune()){
					this.addByHP(-50);
					this.setImmune(true);
				}
			}
		}

		for (Plant object: Plants){
			if (overlapsWith(object)){
				if (!object.isDying() && (getHP() < getMaxHP())){
					this.addByHP(50);
					object.addByHP(-1);
					object.dies();
				}
			}
		}

		for (Shark object: Sharks){
			if (overlapsWith(object)){

				if (overlapsWithX(object)){
					hits.add("X");
				}
				if (overlapsWithY(object)){
					if(this.getVelocity().getElemy() < object.getVelocity().getElemy()){
						this.setCollisionVel(new Vector(getVelocity().getElemx() , 3));
					}else{
						hits.add("Y");
					}
				}
			}
		}

		for (Slime object: Slimes){
			if (overlapsWith(object)){

				if (overlapsWithX(object)){
					hits.add("X");
				}
				if (overlapsWithY(object)){
					if(this.getVelocity().getElemy() < object.getVelocity().getElemy()){
						this.setCollisionVel(new Vector(getVelocity().getElemx() , 3));
					}else{
						hits.add("Y");
					}

					if (!object.isDying()){
						object.slimeGetsHitFor(-50);
						if (!isImmune()){
							addByHP(-50);
							setImmune(true);
						}
					}
				}      
			}

		}
		return hits;
	}




	/**
	 * 
	 */
	@Override
	protected HashSet<String> collisionTile(){

		HashSet<String> hits = new HashSet<String>();

		int thisObjectX = (int) getPos().getElemx();
		int thisObjectY = (int) getPos().getElemy();
		int thisObjectW = (int) getSize().getElemx();
		int thisObjectH = (int) getSize().getElemy();

		if(isDucked()){
			thisObjectH --;
		}

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

		if (!isPassableTerrain(thisObjectX+thisObjectW, thisObjectY+1)){
			hits.add("X");
		}
		else if (!isPassableTerrain(thisObjectX+thisObjectW, thisObjectY+(thisObjectH/2))){
			hits.add("X");
		}
		else if (!isPassableTerrain(thisObjectX, thisObjectY+(thisObjectH/2))){
			hits.add("X");
		}
		else if (!isPassableTerrain(thisObjectX+thisObjectW, thisObjectY+thisObjectH-1)){
			hits.add("X");
		}
		else if (!isPassableTerrain(thisObjectX, thisObjectY+thisObjectH-1)){
			hits.add("X");
		}
		else if (!isPassableTerrain(thisObjectX, thisObjectY+1)){
			hits.add("X");
		}

		if (hits.isEmpty()){

			if (!isPassableTerrain(thisObjectX, thisObjectY)){
				hits.add("X");
				hits.add("Y");
			}
			if (!isPassableTerrain(thisObjectX+thisObjectW, thisObjectY+thisObjectH)){
				hits.add("X");
				hits.add("Y");
			}
			if (!isPassableTerrain(thisObjectX, thisObjectY+thisObjectH)){
				hits.add("X");
				hits.add("Y");
			}
			if (!isPassableTerrain(thisObjectX+thisObjectW, thisObjectY)){
				hits.add("X");
				hits.add("Y");
			}

		}
		return hits;
	}

	/**
	 * This method returns if mazub is immume
	 * 
	 * @return	boolean
	 * 			| if (Immune)
	 *			|	return true;
	 *			| else
	 *			|	return false;
	 */
	public boolean isImmune(){
		if (Immune)
			return true;
		else
			return false;
	}


	/**
	 * This method sets immune to the given value
	 * @param 	x
	 * 			The value to be set
	 * @effect	if x is true
	 * 			| Immune = true;
	 * 			| resetImmuneTimer();
	 * @effect	if x is false
	 * 			| if(getImmuneTimer()>0.6)
	 * 			|	Immune = x
	 */
	protected void setImmune(boolean x){
		if (x){
			Immune = true;
			resetImmuneTimer();
		}else{
			if (getImmuneTimer() > 0.6)
				Immune = x;
		}
	}

	/**
	 * This method resets the timer
	 * @effect	...
	 * 			| ImmuneTimer = 0.0;
	 */
	private void resetImmuneTimer(){
		ImmuneTimer = 0.0;
	}

	/**
	 * This method returns the timer of immunity
	 * 
	 * @return	double
	 * 			| return ImmuneTimer
	 */
	public double getImmuneTimer(){
		return ImmuneTimer;
	}


	/**
	 * 
	 * @param time
	 */
	protected void addToImmuneTimer(double time){
		if (time >= 0)
			ImmuneTimer = ImmuneTimer + time;
	}


	private boolean Immune = false;
	private double ImmuneTimer = 0.0;





	/**
	 * 
	 */
	@Override
	protected void UpdateAccY(){

		if (isOnGround()){
			if (isPassableTerrain(getPos().getElemx()+1, getPos().getElemy()-1) && 
					isPassableTerrain(getPos().getElemx()+getSize().getElemx()-1, getPos().getElemy()-1))
				setOnGround(false);

		}else if (!isOnGround() && getVelocity().getElemy() < 0){
			if (!isPassableTerrain(getPos().getElemx()+1, getPos().getElemy()-1) && 
					!isPassableTerrain(getPos().getElemx()+getSize().getElemx()-1, getPos().getElemy()-1))
				setOnGround(true);
		}

		if (isOnGround()){
			setAccCurr(new Vector(getAccCurr().getElemx(),0.0));
		}else
			setAccCurr(new Vector(getAccCurr().getElemx(),-10.0));
	}



	/**
	 * @post 	The index of the sprite depends on what Mazub is doing
	 * 			Every index has different conditions. Depending on which conditions 
	 * 			are fulfilled an index is chosen.
	 * @post 	When the conditions state index 8 or 9+m and sumdt is more than 75ms
	 * 			the sprite has to change under the same conditions.
	 * @post 	The new height and width of Mazub are set depending on the size
	 * 			of the new sprite.
	 * 
	 */
	public Sprite getCurrentSprite(){

		int m = (Sprites.length-10)/2;

		if( getTimeTimedCurr(getStartTime()) >= 1.0) {
			this.setLastMove('X');
		}

		int i = 0;
		if ((this.getVelocity().getElemx() == 0) && (this.isDucked() == false) && 
				(this.getLastMove() == 'X') ){
			i = 0;
		} else if ((this.getVelocity().getElemx() == 0) && (this.isDucked() == true) && 
				(this.getLastMove() == 'X')){
			i = 1;
		} else if ((this.getVelocity().getElemx() == 0) && (this.isDucked() == false) && 
				(this.getLastMove() == 'R')){
			i = 2;
		} else if ((this.getVelocity().getElemx() == 0) && (this.isDucked() == false) && 
				(this.getLastMove() == 'L')){
			i = 3;
		} else if ((this.getVelocity().getElemx() > 0) && (!isOnGround()) && 
				(this.isDucked() == false)){
			i = 4;
		} else if ((this.getVelocity().getElemx() < 0) && (!isOnGround()) && 
				(this.isDucked() == false)){
			i = 5;
		} else if ((this.isDucked() == true) && ( this.getOrientation() == 'R'  )){
			i = 6;
		} else if ((this.isDucked() == true) && ( this.getOrientation() == 'X'  ) &&
				( this.getLastMove() == 'R'  )){
			i = 6;
		} else if ((this.isDucked() == true) && (  this.getOrientation() == 'L'  )){
			i = 7;
		} else if ((this.isDucked() == true) && ( this.getOrientation() == 'X'  ) &&
				( this.getLastMove() == 'L'  )){
			i = 7;

		} else if ((this.isDucked() == false) && (isOnGround())
				&& (this.getVelocity().getElemx() != 0) && (this.getOrientation() == 'R')){

			int j = (int) (getTimeTimedCurr(getStartTime()) / 0.075);


			if (j>m){
				j=0;
				i=8;
				setStartTime();

			}
			i = 8+j;


		} else if ((this.isDucked() == false) && (isOnGround())
				&& (this.getVelocity().getElemx() != 0) && (this.getOrientation() == 'L')){

			int j =(int) (getTimeTimedCurr(getStartTime()) /0.075);



			if (j>m){
				j=0;
				i=9;
				setStartTime();
			}
			i = 9+m+j;
		}

		this.setSize(new Vector(Sprites[i].getWidth()-1,Sprites[i].getHeight()));

		return Sprites[i];
	}

	private Sprite[] Sprites;





	/**
	 * This method checks if Mazub has reached the targettile
	 * 
	 * @return	boolean
	 * 			| return ((TargetXT == PosXT) && (TargetYT == PosYT));
	 */
	public boolean didPlayerWin(){
		World world = getWorld();
		int MazubX = (int) world.getMazub().getPos().getElemx();
		int MazubY = (int) world.getMazub().getPos().getElemy();
		int MazubW = (int) world.getMazub().getSize().getElemx();
		int MazubH = (int) world.getMazub().getSize().getElemy();


		int [][] MazubTilesOn = world.getTilePositionsIn(MazubX, MazubY,
				MazubX+MazubW, MazubY+MazubH);

		for (int[] MazubTile: MazubTilesOn){

			if (MazubTile[0]== world.getTargetTile().getElemx()
					&& MazubTile[1] == world.getTargetTile().getElemy()){
				//this.setHP(0);

				return true;
			}
		}
		return false;
	}



	// Getters & setters


	/**
	 * 
	 * @return 	Character: The executed move before the current move Mazub is doing now, is returned.
	 * 			'R' means Mazub is facing right, 'L' means Mazub is facing left and
	 * 			'X' means Mazub is facing the screen and isn't moving to the left or to the right.
	 * 			| result == this.LastMove
	 * 			
	 */
	@Basic
	private char getLastMove() {
		return LastMove;
	}



	/**
	 * 
	 * @param	lastMove
	 * 			A character which indicates where Mazub was facing. 'R' means Mazub was facing right, 
	 * 			'L' means Mazub was facing left and 'X' means Mazub was facing the screen and isn't moving.
	 * @pre		lastMove will only be 'R','L' or 'X'. Other characters won't be set using this method.
	 * 			| lastMove == 'R' || lastMove == 'X' || lastMove == 'L'
	 * @post	The variable LastMove is set to the given character lastMove.
	 * 			| new.getLastMove() == lastMove	
	 */
	private void setLastMove(char lastMove) {
		LastMove = lastMove;
	}




	/**
	 * 
	 * @return	integer: the minimum velocity of a starting move, this is a value set from the beginning
	 * 			of the creation of a Mazub.
	 * 			|  result == this.InitVelocityX
	 */
	@Basic @Immutable
	protected double getInitVelocityX() {
		return InitVelocityX;
	}
	/**
	 * 
	 * @return	integer: the maximum velocity of a move in the x direction, this is a value set
	 * 			from the beginning of the creation of a Mazub.
	 * 			|  result == this.MaxVelocityX
	 */
	@Basic @Immutable
	@Override
	protected double getMaxVelocityX() {
		return MaxVelocityX;
	}
	/**
	 * 
	 * @return	integer: the current velocity of Mazub in the x direction.
	 * 			|  result == this.MaxVelocityXCurr
	 */
	@Basic
	public double getMaxVelocityXCurr() {
		return MaxVelocityXCurr;
	}
	/**
	 * 
	 * @param 	maxVelocityXCurr
	 * 			The maximum velocity in the x direction of Mazub dependable 
	 * 			on position and his current move.
	 * @post	The new current maximum velocity is set to maxVelocityXCurr.
	 * 			|  new.getMaxVelocityXCurr() = maxVelocityXCurr
	 * 		
	 */
	public void setMaxVelocityXCurr(double maxVelocityXCurr) {
		MaxVelocityXCurr = maxVelocityXCurr;
	}

	@Override
	protected boolean isValidVelocity(Vector velocity)
			throws IllegalVelocityException {

		if (velocity.getElemx() > getMaxVelocityXCurr()){
			throw new IllegalVelocityException(new Vector(getMaxVelocityXCurr(),velocity.getElemy()));		
		}
		if (velocity.getElemx() < -getMaxVelocityXCurr()){
			throw new IllegalVelocityException(new Vector(-getMaxVelocityXCurr(),velocity.getElemy()));		
		}
		return true;
	}

	/**
	 * 
	 * @return	integer: the current x acceleration of Mazub when going forward.
	 * 			| result == this.AccXFwd
	 * 
	 */
	@Basic
	public double getAccXFwd() {
		return AccXFwd;
	}

	/**
	 * 
	 * @return	integer: the current x acceleration of Mazub when going backwards.
	 * 			| result == this.AccXBkw
	 * 
	 */
	@Basic
	public double getAccXBkw() {
		return AccXBkw;
	}


	/**
	 * 
	 * @return	boolean: if mazub has jumped, isJumped returns true,
	 * 			else it returns false.
	 * 			| result == this.Jumped
	 */
	@Basic
	public boolean isJumped() {
		return Jumped;
	}

	/**
	 * 
	 * @param 	jumped
	 * 			If mazub jumped, Jumped is set to true. 
	 * 			If endJump is called, Jumped is set to false.
	 * @post	The new state of Jumped is jumped.
	 * 			| new.isJumped() == jumped
	 */
	private void setJumped(boolean jumped) {
		this.Jumped = jumped;
	}

	/**
	 * 
	 * @return	boolean: If mazub is ducking, isDucked returns true
	 * 			else isDucked returns False.
	 * 			| result == this.Ducked
	 */
	@Basic
	public boolean isDucked() {
		return Ducked;
	}

	/**
	 * 
	 * @param 	ducked
	 * 			When mazub is ducking, Ducked equals true,
	 * 			else when mazub is nog ducking, Ducked equals false.
	 * @post	Duckes is set to ducked.
	 * 			| new.isDucked() == ducked
	 * 
	 */
	private void setDucked(boolean ducked) {
		Ducked = ducked;
	}




	// Variables

	/**
	 * @effect	...
	 * 			| setHP(100); 
	 */
	@Override
	protected void initializeHP() {

		this.setHP(100);

	}

	/**
	 * @effect	...
	 * 			| return 500;
	 */
	@Override
	public int getMaxHP() {
		return 500;
	}




	private char LastMove = 'X';

	private final double InitVelocityX;
	private final double MaxVelocityX;
	private double MaxVelocityXCurr;

	private static final double INITVELOCITYY = 8.0;

	private final static double AccXFwd = 0.9;
	private final static double AccXBkw = -0.9;

	private static final double ACCY = -10.0;

	private boolean Jumped = false;
	private boolean Ducked = false;

	@Override
	protected void terminate() {
		this.isTerminated = true;
		//getWorld().setMazub(null);
		//this.setWorld(null);

	}

	@Override
	public void startMoveProgram(Boolean direction) {}

	@Override
	public void stopMoveProgram(Boolean direction) {}

	@Override
	public void startDuckProgram() {}

	@Override
	public void stopDuckProgram() {}

	@Override
	public void startJumpProgram() {}

	@Override
	public void stopJumpProgram() {}




}
