package jumpingalien.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;


/**
 * 
 * @Invar	| hasProperWorld();
 *
 */
public class School {

	public School() {

	}

	/**
	 * Return a boolean indicating whether or not this game object
	 * is terminated.
	 */
	@Basic
	@Raw
	protected boolean isTerminated() {
		return this.isTerminated;
	}

	/**
	 * Terminate this object.
	 *
	 * @post    This object is terminated.
	 *          | new.isTerminated()
	 */
	protected void terminate() {
		isTerminated = true;
		getWorld().removeSchool(this);
		this.setWorld(null);
	}

	protected boolean isTerminated;

	/**
	 * This method adds a slime to a school
	 * 
	 * @param 	slime
	 * 			slime to be add to this school
	 * @effect	...
	 * 			| Slimes.add(slime);
	 */
	@Raw
	protected void addSlime(Slime slime){
		if ((canHaveAsSlime(slime)) &&
				(!hasAsSlime(slime))){
			Slimes.add(slime);
		}              
	}

	protected Set<Slime> getAllSlimes(){
		return Collections.unmodifiableSet(Slimes);      
	}

	
	/**
	 * This method removes a slime from his school
	 * 
	 * @param 	slime
	 * 			The slime to be removed
	 * 
	 * @effect	...
	 * 			| Slimes.remove(slime);
	 */
	@Raw   
	protected void removeSlime(Slime slime){
		assert slime != null;
		if (hasAsSlime(slime)){
			Slimes.remove(slime);
		}      
	}

	/**
	 * This method checks whether a given slime can have this.school
	 * 
	 * @param 	slime
	 * 			The slime to be investigated
	 * @return	boolean
	 * 			| return (slime != null) && (slime.canHaveAsSchool(this));
	 */
	public boolean canHaveAsSlime(Slime slime){
		return (slime != null) && (slime.canHaveAsSchool(this));
	}

	/**
	 * This method checks if a given slime is in this school
	 * 
	 * @param 	slime
	 * 			the slime to be investigated
	 * @return	boolean
	 * 			| if (!Slimes.contains(slime))
	 *			| 	return false;
	 *			| if (slime.getSchool() != this)
	 *			| 	return false;
	 *			| return true;
	 *
	 */
	@Basic @Raw
	public boolean hasAsSlime(Slime slime){
		if (!Slimes.contains(slime))
			return false;
		if (slime.getSchool() != this)
			return false;
		return true;
	}
	
	/**
	 * This method returns the number of slimes in this school
	 * 
	 * @return	int
	 * 			| return i;
	 */
	protected int getNbSlimes(){
		int i = Slimes.size();
		return i;
	}

	private Set<Slime> Slimes = new HashSet<>();


	/**
	 * This method returns the world of this.school
	 * 
	 * @return	World
	 * 			| return this.world;
	 */
	@Basic @Raw
	public World getWorld() {
		return this.world;
	}

	/**
	 * This method checks whether this.school can have the given world
	 * @param 	world
	 * 			The world to be investigated
	 * @return	boolean
	 * 			| return (!isTerminated() && world != null);
	 */
	@Raw
	protected boolean canHaveAsWorld(World world) {

		return (!isTerminated() && world != null);
	}


	/**
	 * This method checks whether this.school has a proper world
	 * 
	 * @return	boolean
	 * 			| return canHaveAsWorld(getWorld())
	 *			| && ((getWorld() != null) || (getWorld().hasAsSchool(this)));
	 */
	@Raw
	protected boolean hasProperWorld() {
		return canHaveAsWorld(getWorld())
				&& ((getWorld() != null) || (getWorld().hasAsSchool(this)));
	}

	
	/**
	 * This method gives a school a world
	 * 
	 * @param 	world
	 * 			The world, where the school has to be associated with
	 * @effect	...
	 * 			| world.addSchool(this);
	 * 
	 */
	@Raw
	protected void setWorld(World world) {
		if (canHaveAsWorld(world)){
			this.world = world;
			world.addSchool(this);
		} else{
			this.world = null;
		}
	}
	private World world;




}
