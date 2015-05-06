package jumpingalien.model;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import jumpingalien.util.Sprite;
import jumpingalien.util.Vector;

/**
 * @author svenverret
 *
 */
public class Slime extends GameObject {


	public Slime(int x, int y, Sprite[] sprites, School school)
			throws IllegalArgumentException{
		this.setSchool(school);

		if (sprites.length != 2|| sprites == null)
			throw new IllegalArgumentException("Sprites");
		else{
			this.Sprites = sprites;
			this.setSize(new Vector(sprites[0].getWidth(),sprites[0].getHeight()));
		}

		this.setPos(new Vector(x, y));
		setAccCurr(new Vector(0, 0));
		setRandomMovement("N");
		resetStartTimeDir();
		generateNewPeriodCurrentMove();
	}

	private boolean isJustSpawned() {
		return JustSpawned;
	}
	private void setJustSpawned(boolean justSpawned) {
		JustSpawned = justSpawned;
	}

	private boolean JustSpawned = true;

	@Override
	protected void terminate(){
		this.isTerminated = true;
		getSchool().removeSlime(this);
		setSchool(null);
		getWorld().removeSlime(this);
		setWorld(null);
		
	}
	@Override
	protected void initializeHP() {
		setHP(100);
	}

	@Override
	protected int getMaxHP() {
		return (int) Double.POSITIVE_INFINITY;
	}

	protected void slimeGetsHitFor(int hp){
		addByHP(hp+1);
		for( Slime slime: getSchool().getAllSlimes()){
			slime.addByHP(-1);
		}
	}




	@Basic @Raw
	public School getSchool() {
		return this.school;
	}

	@Raw
	protected boolean canHaveAsSchool(School school) {
		return (school != null)
				&& (!school.isTerminated());
	}

	@Raw
	protected boolean hasProperSchool() {
		return canHaveAsSchool(getSchool())
				&& ((getSchool() != null) || (getSchool().hasAsSlime(this)));
	}

	@Raw
	protected void setSchool(School school) {
		if (canHaveAsSchool(school)){
			this.school = school;
			school.addSlime(this);
		}
	}



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
				System.out.println();
				System.out.println(slime);

				slime.addByHP(-1);
			}
			for(Slime slime: AllSlimesOther){
				System.out.println();
				System.out.println(slime);
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
				System.out.println();
				System.out.println(slime);
				if(slime != this){
					slime.addByHP(+1);
				}
			}
			for(Slime slime: AllSlimesOther){
				System.out.println();
				System.out.println(slime);
				slime.addByHP(-1);
			}

			this.addByHP( -(ThisSchool.getNbSlimes()) + OtherSchool.getNbSlimes() );
			ThisSchool.removeSlime(this);
			this.setSchool(null);
			this.setSchool(OtherSchool);
		}
	}

	private School school;

	private void setRandomMovement(String block){
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




	@Override
	protected void advanceTime(double dt) throws IllegalArgumentException{

		if ((dt < 0.0) || (dt > 0.2)){
			throw new IllegalArgumentException();
		}
		if (isJustSpawned()){
			correctSpawnedInGround();
			setJustSpawned(false);	
		}

		if(!isDying()){
			updateHPTile(dt);
			UpdateAccY();

			if (getStartTimeDir() > getPeriodCurrentMove()){
				setRandomMovement("N");
				resetStartTimeDir();
				generateNewPeriodCurrentMove();
			}else{
				addTimeDir(dt);
			}


			HashSet<String> hits  = collisionDetection(dt);
			if ((hits != null) && (!hits.isEmpty())){

				if (hits.contains("X")){

					if (getOrientation() == 'R'){

						setPos(new Vector(getPos().getElemx()-1,getPos().getElemy()));
						setVelocity(new Vector(0.0,getVelocity().getElemy()));
						setAccCurr(new Vector(0.0,getAccCurr().getElemy()));

						setRandomMovement("X+");

					}else if(getOrientation() == 'L') {

						setPos(new Vector(getPos().getElemx()+1,getPos().getElemy()));
						setVelocity(new Vector(0.0,getVelocity().getElemy()));
						setAccCurr(new Vector(0.0,getAccCurr().getElemy()));

						setRandomMovement("X-");
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

	@Override
	protected HashSet<String> collisionObject(){
		HashSet<String> hits = new HashSet<String>();

		Mazub mazub = getWorld().getMazub();
		Set<Shark> Sharks = this.getWorld().getAllSharks();
		Set<Slime> Slimes = this.getWorld().getAllSlimes();

		if (overlapsWith(mazub)){
			if (overlapsWithX(mazub)){
				hits.add("X");
				this.slimeGetsHitFor(-50);
				if (!mazub.isImmune()){
					mazub.addByHP(-50);
					mazub.setImmune(true);
				}
			}
			if (overlapsWithY(mazub)){
				
				if (getVelocity().getElemy() > mazub.getVelocity().getElemy()){
					mazub.setCollisionVel(new Vector(getCollisionVel().getElemx(), 3));
				}else{
					hits.add("Y");
				}
				//hits.add("Y");
				mazub.setCollisionVel(new Vector(getCollisionVel().getElemx(), 3));
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
				if (!object.isDying()){
					this.switchSchools(object);
				}
			}
		}
		return hits;
	}

	@Override
	public Sprite getCurrentSprite() {
		if (getVelocity().getElemx() >= 0){
			this.setSize(new Vector(Sprites[1].getWidth(),Sprites[1].getHeight()));
			return Sprites[1];
		}
		else{
			this.setSize(new Vector(Sprites[0].getWidth(),Sprites[0].getHeight()));
			return Sprites[0];
		}
	}

	private Sprite[] Sprites;




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
		int Period = randomGenerator.nextInt(6 - 2 + 1) + 2;
		setPeriodCurrentMove(Period);

	}

	private int getPeriodCurrentMove() {
		return PeriodCurrentMove;
	}

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

}