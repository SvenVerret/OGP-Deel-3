package jumpingalien.model;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import program.Program;
import be.kuleuven.cs.som.annotate.Basic;
import jumpingalien.exception.IllegalVelocityException;
import jumpingalien.exception.OutOfBoundsException;
import jumpingalien.util.Sprite;
import jumpingalien.util.Vector;



public class Shark extends GameObject {

	/**
	 * This method creates a new shark with the given parameters
	 * 
	 * @param 	x
	 * 			The X coordinate for the new shark
	 * @param 	y
	 * 			The Y coordinate for the new shark
	 * @param 	sprites
	 * 			The sprites for the shark
	 * @effect	...
	 * 			| this.setPos(new Vector(x,y));
	 *			| this.setVelocity(new Vector(-getInitVelocityX(),0));
	 *			| this.setAccCurr(new Vector(getACCX(), 0.0));
	 *@effect	...
	 *			| this.Sprites = sprites;
	 *			| this.setSize(new Vector(sprites[0].getWidth(),sprites[0].getHeight()));
	 * @throws 	IllegalArgumentException
	 * 			If the sprites are null, or are not 2
	 * 			an IllegalArgumentException is thrown
	 * 
	 */
	public Shark(int x, int y, Sprite[] sprites,Program program) 
			throws IllegalArgumentException{
		super(program);
		this.setPos(new Vector(x,y));
		this.setVelocity(new Vector(-getInitVelocityX(),0));
		this.setAccCurr(new Vector(getACCX(), 0.0));


		if (sprites.length != 2|| sprites == null)
			throw new IllegalArgumentException("Sprites");
		else{
			this.Sprites = sprites;
			this.setSize(new Vector(sprites[0].getWidth(),sprites[0].getHeight()));
		}
	}

	public Shark(int x, int y, Sprite[] sprites) 
			throws IllegalArgumentException{
		this(x,y,sprites,null);
	}

	/**
	 * @effect	...
	 * 			| this.isTerminated = true;
	 *			| getWorld().removeShark(this);
	 *			| setWorld(null);
	 * 
	 */
	@Override
	protected void terminate(){
		this.isTerminated = true;
		getWorld().removeShark(this);
		setWorld(null);
	}

	/**
	 * This method lets the shark jump
	 * @effect	...
	 * 			| this.setVelocity(new Vector(getVelocity().getElemx(),getInitVelocityY()));
	 * 			
	 */
	protected void startJump(){
		setJumped(true);
		double accx;
		if (getVelocity().getElemx() > 0)
			accx = getACCX();
		else
			accx = -getACCX();

		try{
			this.setAccCurr(new Vector(accx,getACCY()));
			this.setVelocity(new Vector(getVelocity().getElemx(),getInitVelocityY()));
		} catch(IllegalVelocityException e){
			this.setVelocity(new Vector(e.getVelocity().getElemx(),e.getVelocity().getElemy()));	
		}



	}

	/**
	 * This method ends the jump of the shark
	 * @effect	...
	 * 			| setJumped(false)
	 * 			| resetAmountPeriodsAfterJump();
	 */
	protected void endJump(){
		setJumped(false);
		resetAmountPeriodsAfterJump();
	}


	/**
	 * This method returns whether the shark has jumped
	 * @return	boolean
	 * 			| return jumped
	 */
	@Basic
	protected boolean isJumped() {
		return Jumped;
	}


	/**
	 * This method sets Jumped with the given value jumped
	 * @param 	jumped
	 * 			The value to be set
	 * @effect	...
	 * 			| this.Jumped = jumped;
	 */
	protected void setJumped(boolean jumped) {
		this.Jumped = jumped;
	}

	private boolean Jumped = false;


	/**
	 * This method returns the number of periods after a jump
	 *  
	 * @return	int
	 * 			| return AmountPeriodsAfterJump;
	 */
	protected int getAmountPeriodsAfterJump() {
		return AmountPeriodsAfterJump;
	}

	/**
	 * Tis method adds 1 up to AmountPeriodsAfterJump
	 * 
	 * @effect	...
	 * 			| AmountPeriodsAfterJump++;
	 */
	protected void addOneAmountPeriodsAfterJump() {
		AmountPeriodsAfterJump++;
	}

	/**
	 * This method resets the AmountPeriodsAfterJump back to zero
	 * 
	 * @effect	...
	 * 			| AmountPeriodsAfterJump = 0;
	 * 
	 */
	protected void resetAmountPeriodsAfterJump() {
		AmountPeriodsAfterJump=0;
	}

	private int AmountPeriodsAfterJump=0;


	/**
	 * This method sets new random moves for the shark
	 * 
	 * @param 	block
	 * 			Block is a string which indicates in which direction the shark may not move
	 * @effect	...
	 * 			| this.setVelocity(new Vector(velx,vely));
	 *			| this.setAccCurr(new Vector(accx,accy));
	 */
	protected void setRandomMovement(String block){

		Random randomGenerator1 = new Random();
		Random randomGenerator2 = new Random();
		Random randomGenerator3 = new Random();
		Random randomGenerator4 = new Random();

		double velx = (randomGenerator1.nextInt((int) ((getMaxVelocityX()*100) - (20)+ 1)) + 20)/(SLOWDOWN * 100.0);
		double vely = (randomGenerator2.nextInt((int) ((getMaxVelocityY()*100) - (20)+ 1)) + 20)/(SLOWDOWN * 100.0);

		double accx = (randomGenerator3.nextInt((150) + 1))/(SLOWDOWN * 100.0);
		double accy = (randomGenerator3.nextInt((20) + 1))/100.0;

		int signx = randomGenerator4.nextInt((1) + 1);
		int signy = randomGenerator4.nextInt((1) + 1);
		int signaccx = randomGenerator4.nextInt((1) + 1);
		int signaccy = randomGenerator4.nextInt((1) + 1);

		if (signx == 0)
			signx = -1;
		if (signy == 0)
			signy = -1;
		if (signaccx == 0)
			signaccx = -1;
		if (signaccy == 0)
			signaccy = -1;


		if (block == "X"){
			if (getVelocity().getElemx() >= 0.0){
				velx = -velx;
				this.setPos(new Vector(getPos().getElemx()-2,getPos().getElemy()));

			}else{
				this.setPos(new Vector(getPos().getElemx()+2,getPos().getElemy()));	
			}

			vely = signy*vely;
			accx = signaccx*accx;
			accy = signaccy*accy;

			this.setVelocity(new Vector(velx,vely));
			this.setAccCurr(new Vector(accx,accy));

		}
		else if (block == "Y"){
			if (getVelocity().getElemy() >= 0.0){				
				vely = -vely;
				this.setPos(new Vector(getPos().getElemx(),getPos().getElemy()-2));

			}else{
				this.setPos(new Vector(getPos().getElemx(),getPos().getElemy()+2));	
			}

			velx = signx*velx;
			accx = signaccx*accx;
			accy = signaccy*accy;

			this.setVelocity(new Vector(velx,vely));
			this.setAccCurr(new Vector(accx,accy));

		}
		else{

			velx = signx*velx;
			vely = signy*vely;

			accx = signaccx*accx;
			accy = signaccy*accy;

			this.setVelocity(new Vector(velx,vely));
			this.setAccCurr(new Vector(accx,accy));	
		}
	}



	/**
	 * 
	 */
	@Override
	protected void advanceTime(double dt) throws IllegalArgumentException {

		if (!isDead()){
			updateHPTile(dt);
			UpdateAccY();

			if(this.getProgram() == null){

				if (getStartTimeDir() > getPeriodCurrentMove()){
					// END OF THIS MOVEPERIOD
					if (getAmountPeriodsAfterJump() >= 2 
							&& getVelocity().getElemx() != 0 
							&& getWorld().getGeologicalFeature((int)getPos().getElemx(), (int)getPos().getElemy()-1) != 0){

						// RIGHT CONDITIONS FOR JUMP
						Random randomGenerator = new Random();
						int randomjump = randomGenerator.nextInt((1) + 1);

						if (randomjump == 1){
							startJump();
							resetStartTimeDir();
							generateNewPeriodCurrentMove();
						}

					} else if (isJumped()){
						// HAS JUMPED FOR A PERIOD-> END ITs
						endJump();
					}

					if (!isJumped()){

						// NOT JUMPED -> RANDOM MOVEMENT
						setRandomMovement("N");
						resetStartTimeDir();
						generateNewPeriodCurrentMove();
						addOneAmountPeriodsAfterJump();
					}
				}else{
					// NOG IN MOVEPERIODE
					addTimeDir(dt);
				}	

			}else{
				this.getProgram().advanceTime(dt);
			}


			//COLLISION DETECTION

			HashSet<String> hits  = collisionDetection(dt);

			if ((hits != null) && (!hits.isEmpty())){

				if (isJumped())
					endJump();

				if (hits.contains("X")){

					if(getProgram() == null){
						setRandomMovement("X");
						resetStartTimeDir();
					}else{
						stopMoveProgram();
					}

				}else if (hits.contains("Y")){

					if(getProgram() == null){
						setRandomMovement("Y");
						resetStartTimeDir();
					}else{
						stopMoveProgram();
					}

				}
			}
		}

	}

	/**
	 * @effect	...
	 * 			| setVelocity(getCollisionVel());
	 * 			| setPos(getCollisionPos());
	 */
	@Override
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
					if (!isOnGround())
						setVelocity(getCollisionVel());
					else
						setVelocity(new Vector(getCollisionVel().getElemx(),0.0));
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
	 * @effect	for sharks
	 * 			| this.addByHP(-50);
	 *			| if (!mazub.isImmune()){
	 *			| 	mazub.addByHP(-50);
	 *			| 	mazub.setImmune(true);
	 *
	 * @effect	for sharks
	 * 			| this.addByHP(-50);
	 *			| if (!mazub.isImmune()){
	 *			| 	mazub.addByHP(-50);
	 *			| 	mazub.setImmune(true);
	 *
	 */
	@Override
	protected HashSet<String> collisionObject(){
		HashSet<String> hits = new HashSet<String>();

		Mazub mazub = getWorld().getMazub();
		Buzam buzam = getWorld().getBuzam();
		Set<Shark> Sharks = this.getWorld().getAllSharks();
		Set<Slime> Slimes = this.getWorld().getAllSlimes();

		if (overlapsWith(mazub)){
			if (overlapsWithX(mazub)){
				hits.add("X");
			}
			if (overlapsWithY(mazub)){
				//hits.add("Y");
				mazub.setCollisionVel(new Vector(mazub.getCollisionVel().getElemx(), 3));
			}
			if (!this.isDying()){

				this.addByHP(-50);
				if (!mazub.isImmune()){
					mazub.addByHP(-50);
					mazub.setImmune(true);
				}
			}
		}
		
		if (overlapsWith(buzam)){
			if (overlapsWithX(buzam)){
				hits.add("X");
			}
			if (overlapsWithY(buzam)){
				hits.add("Y");
			}
			if (!this.isDying()){

				this.addByHP(-50);
				if (!buzam.isImmune()){
					buzam.addByHP(-50);
					buzam.setImmune(true);
				}
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
					this.addByHP(-50);
					object.slimeGetsHitFor(-50);
				}
			}

		}
		return hits;
	}


	/**
	 * @effect	...
	 * 			| this.setHP(100);
	 */
	@Override
	protected void initializeHP() {
		this.setHP(100);
	}


	/**
	 * @return	int
	 * 			| return 100;
	 */
	@Override
	protected int getMaxHP() {
		return 100;
	}

	/**
	 * @effect	in air
	 * 			| AddByHP(-6);
	 * 			| resetStarttimeInTile();
	 * 
	 * @effect 	in magma
	 * 			| addByHP(-50);
				| resetStarttimeInTile();
	 */
	@Override
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
			if (type == 0){
				addStartTimeInTile(dt);
				if (getStartTimeInTile()>=0.2){
					addByHP(-6);
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



	@Override
	protected boolean isValidVelocity(Vector velocity)
			throws IllegalVelocityException {

		if (velocity.getElemx() > getMaxVelocityX()){
			throw new IllegalVelocityException(new Vector(getMaxVelocityX(),getVelocity().getElemy()));
		}
		if (velocity.getElemx() < -getMaxVelocityX()){
			throw new IllegalVelocityException(new Vector(-getMaxVelocityX(),getVelocity().getElemy()));
		}
		if (velocity.getElemy() > getMaxVelocityY()){
			throw new IllegalVelocityException(new Vector(getVelocity().getElemx(),getMaxVelocityY()));
		}
		if (velocity.getElemy() < -getMaxVelocityY()){
			throw new IllegalVelocityException(new Vector(getVelocity().getElemx(),-getMaxVelocityY()));
		}

		return true;
	}


	/**
	 * 
	 */
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

	/**
	 * 
	 */
	@Override
	protected void UpdateAccY() {
		int type = getWorld().getGeologicalFeature((int)getPos().getElemx(), 
				(int) (getPos().getElemy()+getSize().getElemy()));

		if (type == 0){

			setAccCurr(new Vector(getAccCurr().getElemx(), getACCY()));
			if (isJumped() && getVelocity().getElemx() < 0)
				endJump();
		}else
			setAccCurr(new Vector(getAccCurr().getElemx(), 0.0));
	}


	/**
	 * This method adds an amount dt to the timer
	 * 
	 * @param 	dt
	 * 			the time dt
	 * @effect	...
	 * 			| StartTimeDir = StartTimeDir + dt;
	 */
	private void addTimeDir(double dt){
		StartTimeDir = StartTimeDir + dt;
	}

	/**
	 * This method returns the start time since it was going in the same direction
	 * 
	 * @return	double
	 * 			| return StartTimeDir;
	 */
	private double getStartTimeDir(){
		return StartTimeDir;
	}

	/**
	 * This method resets the start time of the timer
	 * @effect	...
	 * 			| StartTimeDir
	 */
	private void resetStartTimeDir(){
		StartTimeDir = 0;
	}

	private double StartTimeDir=0;


	/**
	 * This method generates new periods for shark
	 * 
	 * @effect	...
	 * 			| setPeriodCurrentMove(Period);
	 */
	private void generateNewPeriodCurrentMove(){
		Random randomGenerator = new Random();
		int Period = randomGenerator.nextInt(4 - 1 + 1) + 1;
		setPeriodCurrentMove(Period);

	}

	/**
	 * This method returns the period of the current move the shark is doing
	 * 
	 * @return	...
	 * 			| return PeriodCurrentMove;
	 */
	private int getPeriodCurrentMove() {
		return PeriodCurrentMove;
	}


	/**
	 * This method sets the current move to the given move
	 * 
	 * @param 	periodCurrentMove
	 * 			| PeriodCurrentMove = PeriodCurrentMove;
	 */
	private void setPeriodCurrentMove(int periodCurrentMove) {
		PeriodCurrentMove = periodCurrentMove;
	}




	private Sprite[] Sprites;
	private final double InitVelocityX = 0.2;
	private final double MaxVelocityX = 4.0;
	private final double InitVelocityY = 2.0;
	private final double MaxVelocityY = 4.0;

	private final double ACCX = 1.0;
	private final double ACCY = -10.0;


	/**
	 * This method returns the initial velocity of this.shark
	 * in the X direction
	 * 
	 * @return	double
	 * 			| return InitVelocityX;
	 */
	private double getInitVelocityX() {
		return InitVelocityX;
	}


	/**
	 * This method returns the maximum velocity of this.shark in the X direciont
	 * 
	 * @return	double
	 * 			| return MaxVelocityX;
	 * 
	 */
	@Override
	protected double getMaxVelocityX() {
		return MaxVelocityX;
	}

	/**
	 * This method returns the maximum velocity of this.shark in the Y dirceciont
	 * 
	 * @return	double
	 * 			| return MaxVelocityY
	 */
	private double getMaxVelocityY() {
		return MaxVelocityY;
	}


	/**
	 * This method returns the acceleration in the X direction
	 * 
	 * @return	double
	 * 			| return ACCX
	 */
	private double getACCX() {
		return ACCX;
	}


	/**
	 * This method returns the acceleration in the Y direction
	 * @return	double
	 * 			| return ACCY;
	 */
	private double getACCY() {
		return ACCY;
	}

	/**
	 * This method returns the initial velocity of this.shark
	 * in the Y direction 
	 * @return	double
	 */
	private double getInitVelocityY() {
		return InitVelocityY;
	}
	private static final double SLOWDOWN = 2;

	private int PeriodCurrentMove;


	@Override
	public void startMoveProgram(Boolean direction) {


		if (!direction){
			this.setVelocity(new Vector(-getInitVelocityX(),0.0));
			this.setAccCurr(new Vector(-ACCX,0.0));
		}else if(direction){
			this.setVelocity(new Vector(getInitVelocityX(),0.0));
			this.setAccCurr(new Vector(ACCX,0.0));
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
		// Shark can't duck
	}

	@Override
	public void stopDuckProgram() {
		// Shark can't duck

	}

	@Override
	public void startJumpProgram() {
		startJump();
	}

	@Override
	public void stopJumpProgram() {
		endJump();
	}

}
