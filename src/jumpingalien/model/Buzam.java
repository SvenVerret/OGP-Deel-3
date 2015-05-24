package jumpingalien.model;

import java.util.HashSet;
import java.util.Set;

import program.Program;
import be.kuleuven.cs.som.annotate.Raw;
import jumpingalien.exception.OutOfBoundsException;
import jumpingalien.util.Sprite;
import jumpingalien.util.Vector;

public class Buzam extends Mazub{

	/**
	 *
	 * @param       pixelLeftX
	 *                      The position of the most left pixel of buzam on the x-axis.
	 * @param       pixelBottomY
	 *                      The position of the most left pixel of buzam on the y-axis.
	 * @param       sprites
	 *                      A list of images that will be used to represent a buzam class in the GUI.
	 * @post        Initial height and width are set, based on the initial sprite.
	 *                      | new.getHeight() == sprites[0].getHeight()
	 *                      | new.getWidth() == sprites[0].getWidth()
	 * @post        Maximum value for the velocity of buzam is set.
	 *                      | new.getMaxVelocityX() == maxvelocityx
	 *                      | new.getMaxVelocityXCurr() == maxvelocityx
	 * @post        Minimum value for the velocity of buzam is set.
	 *                      | new.getInitVelocityX() == initvelocityx
	 * @post        The list of sprites used for a buzam class are now stored in Sprites.
	 *                      | new.Sprites = sprites
	 * @effect      PixelLeftX is set as the position on the x-axis. PixelBottomY is set as the position on the y-axis.
	 *                      If these positions are not on the screen, setPosX or setPoxY will throw an OutOfBoundsException.
	 *                      | setPosX(pixelLeftX) && setPosY(pixelBottomY)                 
	 * @throws      IllegalArgumentException("Sprites")
	 *                      An exception is thrown when the list of sprites is shorter than 10.
	 *                      Some states of buzam won't have images if the list is too short.
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

	
	/**
	 * @effect	If a program is loaded, execute the program
	 * 			|if(getProgram() != null){
	 * 			|	getProgram().advanceTime(dt);
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
	 *@effect	Buzam is on ground an has just jumped
	 *			| setOnGround(false);
	 *
	 *
	 */
	@Override
	public void advanceTime(double dt) throws IllegalArgumentException{

		if ((dt < 0.0) || (dt > 0.2)){
			throw new IllegalArgumentException();
		}
		if(!isDying()){
			correctSpawnedInGround();
			updateHPTile(dt); 
			UpdateAccY();

			if(isEndDuckPressed())
				endDuck();

			if (isImmune()){
				addToImmuneTimer(dt);
				setImmune(false);
			}
			if(getProgram() != null){
				getProgram().advanceTime(dt);
			}

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
					if (isOnGround() && isJumped())
						setOnGround(false);

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
	 * 
	 * @effect	Contact with Mazub
	 * 			|if(overlapsWith(mazub))
	 * 			|	if (overlapsWithX(mazub))
	 * 			|		hits.add("X");
	 * 			|	if (overlapsWithY(mazub))
	 * 			|		hits.add("Y");
	 * 			|
	 * 
	 * @effect	Contact with plant and Buzam hasn't reached maxHP
	 * 			| this.addByHP(50);
	 *			| object.dies();
	 * 
	 * @effect	Contact with shark
	 * 			| if (overlapsWithX(shark)){
	 *			| 	hits.add("X");
	 *			| }
	 *			| if (overlapsWithY(shark)){
	 *			|	hits.add("Y");
	 *			| }
	 * 
	 * @effect	Contact with slime
	 * 			| if (overlapsWithX(slime)){
	 *			|	hits.add("X");
	 *			|	}
	 *			| if (overlapsWithY(slime)){
	 *			|	hits.add("Y")
	 *			| 	if (!slime.isDying()){
	 *			|		slime.slimeGetsHitFor(-50);
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

		Mazub mazub = this.getWorld().getMazub();
		Set<Shark> Sharks = this.getWorld().getAllSharks();
		Set<Slime> Slimes = this.getWorld().getAllSlimes();
		Set<Plant> Plants = this.getWorld().getAllPlants();

		if(getWorld().getMazub() != null){

			if(overlapsWith(mazub)){
				if (overlapsWithX(mazub))
					hits.add("X");

				if (overlapsWithY(mazub))
					hits.add("Y");
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
	 * This method checks whether Buzam can have the given world as world
	 * 
	 * @return	boolean:
	 *			| return true;
	 */
	@Raw
	@Override
	public boolean canHaveAsWorld(World world) {
		return true;
	}


	/**
	 * @effect	...
	 *			| setHP(500);
	 */
	@Override
	protected void initializeHP() {
		this.setHP(500);
	}

	/**
	 * 
	 * @effect	...
	 *			| getWorld().removeBuzam(this);
	 *			| setWorld(null);
	 */
	@Override
	protected void terminate(){
		this.isTerminated = true;
		getWorld().removeBuzam(this);
		setWorld(null);
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
	public void stopMoveProgram(Boolean dir) {
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
