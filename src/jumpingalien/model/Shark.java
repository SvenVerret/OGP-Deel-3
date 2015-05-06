package jumpingalien.model;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import be.kuleuven.cs.som.annotate.Basic;
import jumpingalien.exception.IllegalVelocityException;
import jumpingalien.exception.OutOfBoundsException;
import jumpingalien.util.Sprite;
import jumpingalien.util.Vector;

public class Shark extends GameObject {



	public Shark(int x, int y, Sprite[] sprites) 
			throws IllegalArgumentException{
		this.setPos(new Vector(x,y));
		this.setVelocity(new Vector(-getInitVelocityX(),0));
		this.setAccCurr(new Vector(getACCX(), 0.0));

		if (sprites.length != 2|| sprites == null)
			throw new IllegalArgumentException("Sprites");
		else{
			this.Sprites = sprites;
			this.setSize(new Vector(sprites[0].getWidth(),sprites[0].getHeight()));
		}


		setRandomMovement("N");
	}

	@Override
	protected void terminate(){
		this.isTerminated = true;
		getWorld().removeShark(this);
		setWorld(null);
	}

	private void startJump(){
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
	private void endJump(){
		setJumped(false);
		resetAmountPeriodsAfterJump();
	}

	@Basic
	private boolean isJumped() {
		return Jumped;
	}

	private void setJumped(boolean jumped) {
		this.Jumped = jumped;
	}

	private boolean Jumped = false;

	private int getAmountPeriodsAfterJump() {
		return AmountPeriodsAfterJump;
	}
	private void addOneAmountPeriodsAfterJump() {
		AmountPeriodsAfterJump++;
		//System.out.println("Periods : " + AmountPeriodsAfterJump);
	}
	private void resetAmountPeriodsAfterJump() {
		AmountPeriodsAfterJump=0;
	}

	private int AmountPeriodsAfterJump=0;


	private void setRandomMovement(String block){

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



	@Override
	protected void advanceTime(double dt) throws IllegalArgumentException {
		updateHPTile(dt);
		if (!isDead()){

			UpdateAccY();


			if (getStartTimeDir() > getPeriodCurrentMove()){

				// END OF THIS MOVEPERIOD

				//System.out.println(" Amount of periods after last jump = " +getAmountPeriodsAfterJump());

				if (getAmountPeriodsAfterJump() >= 2 
						&& getVelocity().getElemx() != 0 
						&& getWorld().getGeologicalFeature((int)getPos().getElemx(), (int)getPos().getElemy()-1) != 0){

					// RIGHT CONDITIONS FOR JUMP
					Random randomGenerator = new Random();
					int randomjump = randomGenerator.nextInt((1) + 1);

					if (randomjump == 1){
						//System.out.println("   JUMPED    ");
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


			//COLLISION DETECTION

			HashSet<String> hits  = collisionDetection(dt);

			if ((hits != null) && (!hits.isEmpty())){

				if (isJumped())
					endJump();

				if (hits.contains("X")){
					setRandomMovement("X");
					resetStartTimeDir();

				}else if (hits.contains("Y")){
					setRandomMovement("Y");
					resetStartTimeDir();

				}
			}
		}

	}

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
				//system.out.println("MAZUB: NIET TEGEN TILE GEBOTST");
				hits = collisionObject();
			}else{
				//system.out.println("MAZUB: TEGEN TILE GEBOTST");
				return hits;
			}

			if (!hits.isEmpty()){
				//system.out.println("MAZUB: TEGEN OBJECT GEBOTST");
				return hits;

			}else{
				//system.out.println("MAZUB: NIET TEGEN TILE OF OBJECT GEBOTST");
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


	@Override
	protected HashSet<String> collisionObject(){
		HashSet<String> hits = new HashSet<String>();

		Mazub mazub = getWorld().getMazub();
		Set<Shark> Sharks = this.getWorld().getAllSharks();
		Set<Slime> Slimes = this.getWorld().getAllSlimes();

		if (overlapsWith(mazub)){
			if (overlapsWithX(mazub)){
				hits.add("X");
			}
			if (overlapsWithY(mazub)){
				//hits.add("Y");
				mazub.setCollisionVel(new Vector(getCollisionVel().getElemx(), 3));
			}
			
			this.addByHP(-50);
			if (!mazub.isImmune()){
				mazub.addByHP(-50);
				mazub.setImmune(true);

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
				this.addByHP(-50);
				object.slimeGetsHitFor(-50);
			}
		}
		return hits;
	}

	@Override
	protected void initializeHP() {
		this.setHP(100);
	}

	@Override
	protected int getMaxHP() {
		return 1000;
	}

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

	//	@Override
	//	public void advanceTime(double dt) throws IllegalArgumentException {
	//		if ((dt < 0.0) || (dt > 0.2)){
	//			throw new IllegalArgumentException();
	//		}
	//		
	//		char direction = this.getOrientation();
	//		int posx = (int) this.getPos().getElemx();
	//		int posy = (int) this.getPos().getElemy();
	//		double velx = this.getVelocity().getElemx();
	//		double vely = this.getVelocity().getElemy();
	//		double accx = this.getAccCurr().getElemx();
	//		double accy = this.getAccCurr().getElemy();
	//		double dx = 0;
	//		double dy = 0;
	//		
	//		if (getPos().getElemy() == SharkGround){
	//			try{
	//				setVelocity(new Vector(velx + accx*dt,0));
	//			}catch(IllegalVelocityException e){
	//				this.setVelocity(e.getVelocity());
	//			}
	//			try{
	//			dx = (100*(velx * dt + 0.5*accx*Math.pow(dt,2)));
	//			setPos(new Vector(posx + dx,0));
	//			}catch(OutOfBoundsException e){
	//				this.setPos(new Vector(e.getPosition().getElemx()
	//						,getPos().getElemy()));			
	//			}
	//				
	//		}else{
	//			//in the air
	//		}
	//	}

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

	private void addTimeDir(double dt){
		StartTimeDir = StartTimeDir + dt;
	}

	private double getStartTimeDir(){
		return StartTimeDir;
	}

	private void resetStartTimeDir(){
		StartTimeDir = 0;
	}

	private double StartTimeDir=0;

	private void generateNewPeriodCurrentMove(){
		Random randomGenerator = new Random();
		int Period = randomGenerator.nextInt(4 - 1 + 1) + 1;
		setPeriodCurrentMove(Period);
		//		System.out.println();
		//		System.out.println("Period = " + Period + " seconds");
		//		System.out.println();

	}

	private int getPeriodCurrentMove() {
		return PeriodCurrentMove;
	}

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

	private double getInitVelocityX() {
		return InitVelocityX;
	}

	protected double getMaxVelocityX() {
		return MaxVelocityX;
	}

	private double getMaxVelocityY() {
		return MaxVelocityY;
	}

	private double getACCX() {
		return ACCX;
	}

	private double getACCY() {
		return ACCY;
	}
	private double getInitVelocityY() {
		return InitVelocityY;
	}
	private static final double SLOWDOWN = 3;


	private int PeriodCurrentMove;



}