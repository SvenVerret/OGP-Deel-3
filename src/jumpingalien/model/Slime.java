package jumpingalien.model;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import program.Program;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import jumpingalien.util.Sprite;
import jumpingalien.util.Vector;

/**
 * @Invar	...
 * 			| hasProperSchool()
 * 
 * @author Kevin Zhang & Sven Verret
 *
 */
public class Slime extends GameObject {

	/**
	 * This method creates a new slime with the given parameters
	 * @param 	x
	 * 			The X coordinate of the slime
	 * @param 	y
	 * 			The Y coordinate of the slime
	 * @param 	sprites
	 * 			The sprites of the slime
	 * @param 	school
	 * 			The school of which the slime is part of
	 * 
	 * @effect	...
	 * 			this.setSchool(school);
	 * @effect	...
	 * 			| this.setPos(new Vector(x, y));
	 *			| setAccCurr(new Vector(0, 0));
	 *			| generateNewPeriodCurrentMove();
	 *			| setRandomMovement("N");
	 *
	 * @effect	...
	 * 			|this.setSize(new Vector(sprites[0].getWidth(),sprites[0].getHeight()));
	 * 
	 * @throws 	IllegalArgumentException
	 * 			If the sprites are nog 2 or null
	 * 			an IllegalArgumentException is thrown
	 */

	public Slime(int x, int y, Sprite[] sprites, School school, Program program)
			throws IllegalArgumentException{
		super(program);
		this.setSchool(school);
		if (sprites.length != 2|| sprites == null)
			throw new IllegalArgumentException("Sprites");
		else{
			this.Sprites = sprites;
			this.setSize(new Vector(sprites[0].getWidth(),sprites[0].getHeight()));
		}

		this.setPos(new Vector(x, y));
		setAccCurr(new Vector(0, 0));

	}

	public Slime(int x, int y, Sprite[] sprites, School school)
			throws IllegalArgumentException{
		this(x,y,sprites,school,null);
	}

	/**
	 * This method returns if a slime is just spawned
	 * 
	 * @return	boolean
	 * 			& return JustSpanwed
	 */
	private boolean isJustSpawned() {
		return JustSpawned;
	}

	/**
	 * This method sets if the object has just been spawned
	 * @param 	justSpawned
	 * 			| JustSpawned = justSpawned;
	 */
	private void setJustSpawned(boolean justSpawned) {
		JustSpawned = justSpawned;
	}

	private boolean JustSpawned = true;

	/**
	 * 
	 * @effect
	 * 			| getSchool().removeSlime(this);
	 *			| setSchool(null);
	 *			| getWorld().removeSlime(this);
	 *			| setWorld(null);
	 */
	@Override
	protected void terminate(){
		this.isTerminated = true;
		getSchool().removeSlime(this);
		setSchool(null);
		getWorld().removeSlime(this);
		setWorld(null);
	}

	/**
	 * @effect	...
	 * 			| setHP(100);
	 */
	@Override
	protected void initializeHP() {
		setHP(100);
	}

	/**
	 * @effect	...
	 * 			| return (int) Double.POSITIVE_INFINITY;
	 */
	@Override
	protected int getMaxHP() {
		return (int) Double.POSITIVE_INFINITY;
	}

	/**
	 * This method 
	 * @param 	hp
	 * 			
	 */
	protected void slimeGetsHitFor(int hp){
		addByHP(hp+1);
		for( Slime slime: getSchool().getAllSlimes()){
			slime.addByHP(-1);
		}
	}



	/**
	 * This method returns the school of this slime
	 * 
	 * @return	...
	 * 			| return this.school;
	 */
	@Basic @Raw
	public School getSchool() {
		return this.school;
	}

	/**
	 * This method checks if this.slime can have the given school
	 * 
	 * @param 	school
	 * 			The school to be checked for this slime
	 * 
	 * @return	boolean
	 * 			| return (school != null)
	 *			| && (!school.isTerminated());
	 */
	@Raw
	protected boolean canHaveAsSchool(School school) {
		return (school != null)
				&& (!school.isTerminated());
	}

	/**
	 * This method checks whether a slime has a proper school
	 * 
	 * @return	boolean
	 * 			| return canHaveAsSchool(getSchool())
	 *			| && ((getSchool() != null) || (getSchool().hasAsSlime(this)));
	 */
	@Raw
	protected boolean hasProperSchool() {
		return canHaveAsSchool(getSchool())
				&& ((getSchool() != null) || (getSchool().hasAsSlime(this)));
	}


	/**
	 * This method sets the given school for this.slime
	 * 
	 * @param 	school
	 * 			The school for this slime
	 * @effect	...
	 * 			| this.school = school;
	 *			| school.addSlime(this);
	 */
	@Raw
	private void setSchool(School school) {
		if (canHaveAsSchool(school)){
			this.school = school;
			school.addSlime(this);
		}
	}


	/**
	 * This method is for switching schools between slimes
	 * this depends on the number of slimes in a school
	 * 
	 * @param 	other
	 * 			The other slime to be compared
	 * @effect	...
	 * 			| other.setSchool(null);
	 *			| other.setSchool(ThisSchool);
	 *
	 * @effect	...
	 * 			| for(Slime slime: AllSlimesThis){
	 *			|	if(slime != this){
	 *			|		slime.addByHP(+1);
	 *			|	}
	 *			| }
	 *			| for(Slime slime: AllSlimesOther){
	 *			| 	slime.addByHP(-1);
	 *			| }
	 */
	protected void switchSchools(Slime other){
		School ThisSchool = getSchool();
		School OtherSchool = other.getSchool();

		int LengthThis = ThisSchool.getNbSlimes();
		int LengthOther = OtherSchool.getNbSlimes();

		if (LengthThis == LengthOther){
		}else if (LengthThis > LengthOther){

			Set<Slime> AllSlimesThis = ThisSchool.getAllSlimes();
			Set<Slime> AllSlimesOther = OtherSchool.getAllSlimes();

			for(Slime slime: AllSlimesThis){

				slime.addByHP(-1);
			}
			for(Slime slime: AllSlimesOther){
				if(slime != this){
					slime.addByHP(+1);
				}
			}

			this.addByHP( -(OtherSchool.getNbSlimes()) + ThisSchool.getNbSlimes() );
			OtherSchool.removeSlime(other);
			other.setSchool(null);
			other.setSchool(ThisSchool);

		}else{
			Set<Slime> AllSlimesThis = ThisSchool.getAllSlimes();
			Set<Slime> AllSlimesOther = OtherSchool.getAllSlimes();

			for(Slime slime: AllSlimesThis){
				if(slime != this){
					slime.addByHP(+1);
				}
			}
			for(Slime slime: AllSlimesOther){
				slime.addByHP(-1);
			}

			this.addByHP( -(ThisSchool.getNbSlimes()) + OtherSchool.getNbSlimes() );
			ThisSchool.removeSlime(this);
			this.setSchool(null);
			this.setSchool(OtherSchool);
		}
	}

	private School school;


	/**
	 * This method calculates and sets random movements for this.slime
	 * 
	 * @param 	block
	 * 			The Direction in which the slime may not move
	 * 			N, means nothing
	 * 			X+: right
	 * 			X-: left
	 * @effect	Move to the left
	 * 			| this.setPos(new Vector(getPos().getElemx()-1,getPos().getElemy()));
	 * @effect	Move to the right
	 * 			| this.setPos(new Vector(getPos().getElemx()+1,getPos().getElemy()));
	 * 
	 * @effect	...
	 * 			| this.setVelocity(new Vector(velx,vely));
	 *			| this.setAccCurr(new Vector(accx,accy));	
	 * 
	 */
	protected void setRandomMovement(String block){
		double velx = 0.5;
		double vely = getVelocity().getElemy();

		double accx = ACCX;
		double accy = getAccCurr().getElemy();
		int signx= 0;

		if (block == "N"){
			Random randomGenerator1 = new Random();
			signx = randomGenerator1.nextInt((1) + 1);
			if (signx == 0){
				signx = -1;
				this.setOrientation('L');
			}else{
				this.setOrientation('R');
			}


		} else if (block == "X+"){
			signx = -1;
			this.setOrientation('L');
			this.setPos(new Vector(getPos().getElemx()-1,getPos().getElemy()));
		} else if (block == "X-"){
			signx = 1;	
			this.setOrientation('R');
			this.setPos(new Vector(getPos().getElemx()+1,getPos().getElemy()));
		}

		velx = signx*velx;
		accx = signx*accx;

		this.setVelocity(new Vector(velx,vely));
		this.setAccCurr(new Vector(accx,accy));	
	}



	/**
	 * @effect	If a slime is spawning in ground
	 * 			| setJustSpawned(false);
	 *			| UpdateAccY();
	 *
	 * @effect	Move to the right
	 * 			| setPos(new Vector(getPos().getElemx()-1,getPos().getElemy()));
	 *			| setVelocity(new Vector(0.0,getVelocity().getElemy()));
	 *			| setAccCurr(new Vector(0.0,getAccCurr().getElemy()));
	 * @effect	Move to the left
	 * 			| setPos(new Vector(getPos().getElemx()+1,getPos().getElemy()));
	 *			| setVelocity(new Vector(0.0,getVelocity().getElemy()));
	 *			| setAccCurr(new Vector(0.0,getAccCurr().getElemy()));
	 *
	 * @effect	Blocked in the Y direction and going up
	 * 			| setPos(new Vector(getPos().getElemx(),getPos().getElemy()-1));
	 *			| setVelocity(new Vector(getVelocity().getElemx(),0.0));
	 *
	 * @effect	Blocked in the Y direction and going down
	 * 			| setPos(new Vector(getPos().getElemx(),getPos().getElemy()+1));
	 *			| setVelocity(new Vector(getVelocity().getElemx(),0.0));
	 *			| setAccCurr(new Vector(getAccCurr().getElemx(),0.0));
	 */
	@Override
	protected void advanceTime(double dt) throws IllegalArgumentException{

		if ((dt < 0.0) || (dt > 0.2)){
			throw new IllegalArgumentException();
		}

		if (isJustSpawned()){
			correctSpawnedInGround();
			setJustSpawned(false);
			UpdateAccY();
			if(getProgram() == null){
				generateNewPeriodCurrentMove();
				setRandomMovement("N");
			}
		}

		if(!isDying()){
			updateHPTile(dt);
			UpdateAccY();

			if(getProgram() == null){
				if (getStartTimeDir() > getPeriodCurrentMove()){
					//RANDOM MOVEMENT
					setRandomMovement("N");
					resetStartTimeDir();
					generateNewPeriodCurrentMove();
				}else{
					// NOG IN MOVEPERIODE
					addTimeDir(dt);
				}
			}else{
				getProgram().advanceTime(dt);
			}


			HashSet<String> hits  = collisionDetection(dt);
			if ((hits != null) && (!hits.isEmpty())){

				if (hits.contains("X")){

					if (getOrientation() == 'R'){

						setPos(new Vector(getPos().getElemx()-1,getPos().getElemy()));
						setVelocity(new Vector(0.0,getVelocity().getElemy()));
						setAccCurr(new Vector(0.0,getAccCurr().getElemy()));

						if(getProgram() == null){
							setRandomMovement("X+");
						}


					}else if(getOrientation() == 'L') {

						setPos(new Vector(getPos().getElemx()+1,getPos().getElemy()));
						setVelocity(new Vector(0.0,getVelocity().getElemy()));
						setAccCurr(new Vector(0.0,getAccCurr().getElemy()));

						if(getProgram() == null){
							setRandomMovement("X-");
						}

					}

				}else if (hits.contains("Y")){

					if (getVelocity().getElemy() >= 0.0){

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
	}

	/**
	 * @return	<String>
	 * 			| return hits;
	 */
	@Override
	protected HashSet<String> collisionObject(){
		HashSet<String> hits = new HashSet<String>();

		Mazub mazub = getWorld().getMazub();
		Set<Shark> Sharks = this.getWorld().getAllSharks();
		Set<Slime> Slimes = this.getWorld().getAllSlimes();

		if (overlapsWith(mazub)){
			if (overlapsWithX(mazub)){

				hits.add("X");

				if (!this.isDying()){
					this.slimeGetsHitFor(-50);
					if (!mazub.isImmune()){
						mazub.addByHP(-50);
						mazub.setImmune(true);
					}
				}
			}
			if (overlapsWithY(mazub)){
				//hits.add("Y");
				mazub.setCollisionVel(new Vector(mazub.getCollisionVel().getElemx(), 3));
			}
		}

		for (Shark object: Sharks){
			if (overlapsWith(object)){

				if (overlapsWithX(object)){
					hits.add("X");
				}
				if (overlapsWithY(object)){
					hits.add("Y");
				}
			}
		}

		for (Slime object: Slimes){
			if (overlapsWith(object)){

				if (overlapsWithX(object)){
					hits.add("X");
				}
				if (overlapsWithY(object)){
					hits.add("Y");
				}      
				if (!this.isDying() && !object.isDying()){
					this.switchSchools(object);
				}
			}
		}
		return hits;
	}


	@Override
	public Sprite getCurrentSprite() {
		if (getVelocity().getElemx() > 0){
			this.setSize(new Vector(Sprites[1].getWidth(),Sprites[1].getHeight()));
			return Sprites[1];
		}
		else{
			this.setSize(new Vector(Sprites[0].getWidth(),Sprites[0].getHeight()));
			return Sprites[0];
		}
	}

	private Sprite[] Sprites;



	/**
	 * This method adds time to the timer
	 * @param	dt
	 * 			The amount of time to add to the timer
	 * @effect	...
	 * 			| StartTimeDir = StartTimeDir + dt;
	 */
	private void addTimeDir(double dt){
		StartTimeDir = StartTimeDir + dt;
	}

	/**
	 * This method returns the Start time of the direction
	 * 
	 * @return	double
	 * 			| return StartTimeDir;
	 */
	private double getStartTimeDir(){
		return StartTimeDir;
	}

	/**
	 * This method resets the timer
	 * @effect	...
	 * 			| StartTimeDir = 0;
	 */
	private void resetStartTimeDir(){
		StartTimeDir = 0;
	}

	private double StartTimeDir=0;

	/**
	 * This method generates ah period for the current move
	 * @effect	...
	 * 			| setPeriodCurrentMove(Period);
	 */
	private void generateNewPeriodCurrentMove(){
		Random randomGenerator = new Random();
		int Period = randomGenerator.nextInt(6 - 2 + 1) + 2;
		setPeriodCurrentMove(Period);

	}

	/**
	 * This method returns the current move
	 * 
	 * @return	int
	 * 			| return PeriodCurrentMove;
	 */
	private int getPeriodCurrentMove() {
		return PeriodCurrentMove;
	}

	/**
	 * This method sets the period of the current move
	 * 
	 * @param 	periodCurrentMove
	 * 			The period to be set
	 * @effect	...
	 * 			| PeriodCurrentMove = periodCurrentMove
	 */
	private void setPeriodCurrentMove(int periodCurrentMove) {
		PeriodCurrentMove = periodCurrentMove;
	}


	private int PeriodCurrentMove=0;



	private static final double MaxVelocityX = 2.5;
	private static final double ACCX = 0.7;


	@Override
	protected double getMaxVelocityX() {
		return MaxVelocityX;
	}

	@Override

	public void startDuckProgram() {} // slimes cannot duck

	@Override
	public void stopDuckProgram() {} // slimes cannot duck

	@Override
	public void startJumpProgram() {} // slimes cannot jump}

	@Override
	public void stopJumpProgram() {} // slimes cannot jump

	@Override
	public void startMoveProgram(Boolean direction) {


		if (!direction){
			this.setVelocity(new Vector(-0.5,0.0));
			this.setAccCurr(new Vector(-ACCX,0.0));
		}else if (direction){
			this.setVelocity(new Vector(0.5,0.0));
			this.setAccCurr(new Vector(ACCX,0.0));
		}else
			this.setVelocity(new Vector(0,0));
		}

	

	@Override
	public void stopMoveProgram() {
		this.setVelocity(new Vector(0.0,0.0));
		this.setAccCurr(new Vector(0.0,0.0));	
		
	}


}

