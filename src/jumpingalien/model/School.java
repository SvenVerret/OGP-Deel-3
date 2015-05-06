package jumpingalien.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

public class School {

	public School() {
		Slimes =  new HashSet<>();
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

	@Raw   
	protected void removeSlime(Slime slime){
		assert slime != null;
		if (hasAsSlime(slime)){
			Slimes.remove(slime);
		}      
	}

	protected boolean canHaveAsSlime(Slime slime){
		return (slime != null) && (slime.canHaveAsSchool(this));
	}

	@Basic @Raw
	public boolean hasAsSlime(Slime slime){
		if (!Slimes.contains(slime))
			return false;
		if (slime.getSchool() != this)
			return false;
		return true;
	}
	protected int getNbSlimes(){
		int i = Slimes.size();
		return i;
	}

	private Set<Slime> Slimes;


	@Basic @Raw
	public World getWorld() {
		return this.world;
	}

	@Raw
	protected boolean canHaveAsWorld(World world) {

		return (!isTerminated() && world != null);
	}


	@Raw
	protected boolean hasProperWorld() {
		return canHaveAsWorld(getWorld())
				&& ((getWorld() != null) || (getWorld().hasAsSchool(this)));
	}

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
