package jumpingalien.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import jumpingalien.util.Vector;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
/**
 * @Invar       ...
 *              | hasProperMazub()
 * @Invar       ...
 *              | getSchools().size() <= 10
 * @Invar       ...
 * 				| getNbObjects() <= 100                
 * 
 * @author Kevin Zhang & Sven Verret
 *
 */
public class World { 


	/**
	 * Create a new game world with the given parameters.
	 * 
	 * @param tileSize
	 *            Length (in pixels) of a side of each square tile in the world
	 * @param nbTilesX
	 *            Number of tiles in the horizontal direction
	 * @param nbTilesY
	 *            Number of tiles in the vertical direction
	 * @param visibleWindowWidth
	 *            Width of the visible window, in pixels
	 * @param visibleWindowHeight
	 *            Height of the visible window, in pixels
	 * @param targetTileX
	 *            Tile x-coordinate of the target tile of the created world
	 * @param targetTileY
	 *            Tile y-coordinate of the target tile of the created world
	 * @post  tileSize is saved to a final Variable TileSize
	 * 		  | TileSize = tileSize;
	 * 
	 * @post  WorldSize is saved to a final Variable WorldSize
	 *        | WorldSize = new Vector(tileSize*nbTilesX,tileSize*nbTilesY);
	 * 
	 * @post  The number of tiles is saved to NbTiles
	 *        | NbTiles = new Vector(nbTilesX,nbTilesY);
	 * 
	 * @post  A matrix is created with zero's (type air)
	 *        | TileMatrix = createInitialTileMatrix();
	 * 
	 * @post  The TargetTile is set
	 *        | this.setTargetTile(new Vector(targetTileX,targetTileY));
	 * 
	 * @post  visibleWindowWidth is saved to a final Variable visWindowWidth
	 *        | VisWindowWidth = visibleWindowWidth;
	 * 
	 * @post  visibleWindowHeight is saved to a final Variable VisWindowWith
	 *        | VisWindowHeight = visibleWindowHeight;
	 * 
	 * @effect the visible window is set to basic size 0,0,the given width, the given height
	 *         | setCurrentWindow(0,0,getVisWindowWidth(),getVisWindowHeight());
	 * 
	 * @effect the world clock starts running
	 *         | startClock();
	 * 
	 * @throws IllegalArgumentException
	 * 		   An exception is thrown when tileSize is negative
	 *         | tileSize < 0
	 * @throws IllegalArgumentException
	 * 		   An exception is thrown when the number of tiles X or Y is negative
	 * 		   | (nbTilesX < 0) || (nbTilesY < 0)
	 * @throws IllegalArgumentException
	 * 		   An excetion is thrown when the targetTile is not in the dimensions of the
	 * 		   world
	 * 		   | ((targetTileX < 0) || (targetTileY < 0))
	 * 		   | ((targetTileX > nbTilesX) || (targetTileY > nbTilesY))
	 */
	public World(int tileSize, int nbTilesX, int nbTilesY,
			int visibleWindowWidth, int visibleWindowHeight, int targetTileX,
			int targetTileY)throws IllegalArgumentException {

		if (tileSize<= 0){
			throw new IllegalArgumentException("TileSize Negative");
		}

		if ((nbTilesX < 0) || (nbTilesY < 0)){
			throw new IllegalArgumentException("NbTiles Negative");
		}

		if ((targetTileX < 0) || (targetTileY < 0)){
			throw new IllegalArgumentException("Target Negative");
		}

		if ((targetTileX > nbTilesX) || (targetTileY > nbTilesY)){
			throw new IllegalArgumentException("Target Too Big");
		}


		startClock();
		TileSize = tileSize;
		WorldSize = new Vector(tileSize*nbTilesX,tileSize*nbTilesY);
		NbTiles = new Vector(nbTilesX,nbTilesY);
		TileMatrix = createInitialTileMatrix();
		TargetTile = new Vector(targetTileX,targetTileY);
		VisWindowWidth = visibleWindowWidth;
		VisWindowHeight = visibleWindowHeight;
	}



	/**
	 * Advance the time for the world and all its objects by the given amount.
	 *  
	 * @param 	dt
	 *            The time interval (in seconds) by which to advance the given
	 *            world's time.
	 * @post	...
	 * 			| startWorld();
	 * @post 	...
	 * 			| advanceTimeOfClock(dt);
	 * @effect	...(on all objects)
	 * 			| object.dies();
	 * @effect	...(tested on alien)
	 * 			| alien.dies();
	 * @effect	if object is dead, object is terminated
	 * 			| object.terminate();
	 * @effect	Update the schools from slimes in the world
	 * 			| updateSchools();
	 * @throws	IllegalArgumentException
	 * 			if dt is negative or greater than 0.2, an exception is thrown
	 * 			| ((dt < 0.0) || (dt > 0.2))
	 * 
	 */
	public void advanceTime(@Raw double dt)throws IllegalArgumentException{

		if ((dt < 0.0) || (dt > 0.2)){
			throw new IllegalArgumentException();
		}
		startWorld();

		advanceTimeOfClock(dt);

		Set<GameObject> OriginalObjects = getEachAndEveryObject();
		Set<GameObject> CopyObjects = new HashSet<>(OriginalObjects);

		//		Mazub alien = getMazub();
		//		alien.advanceTime(dt);
		//		if ((alien.isOutOfBounds()) || (alien.getHP() == 0)){
		//			alien.dies();
		//		}
		//
		//		Buzam evilalien = getBuzam();
		//		if(evilalien != null){
		//			
		//			if ((evilalien.isOutOfBounds()) || (evilalien.getHP() == 0)){
		//				evilalien.terminate();
		//			}else{
		//				evilalien.advanceTime(dt);
		//			}
		//			
		//		}


		for (GameObject object: CopyObjects){
			if(object.getWorld() != null){
				object.advanceTime(dt);
				if ((object.isOutOfBounds()) || (object.getHP() == 0)){
					object.dies();
				}
			}
		}

		//TERMINATE

		for (GameObject object: CopyObjects){
			if (object.isDead()){
				object.terminate();
			}
		}

		updateSchools();
	}


	//////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	/**
	 * Sets the alien in the world
	 * 
	 * @param 	alien
	 * 			| the alien that we want to add to the world
	 * 
	 * @effect	...
	 * 			this.mazub=alien;
	 * 
	 */
	@Raw
	public void setMazub(Mazub alien){
		if (canHaveAsMazub(alien)){
			alien.setWorld(this);
			this.mazub=alien;

		}
	}


	/**
	 * This method returns Mazub in the world
	 * 
	 * @return   Mazub: this is an alien
	 * 			| return mazub;
	 * 
	 */
	@Basic @Raw
	public Mazub getMazub(){
		return mazub;
	}


	/**
	 * This method checks if the world can have Mazub
	 * 
	 * @param 	alien
	 * 			| the alien that we want to add to the world
	 * @return	boolean: true | false
	 * 			| return ((getMazub() == null));
	 */
	@Raw
	public boolean canHaveAsMazub(Mazub alien){
		return ((getMazub() == null));
	}


	/**
	 * This method checks if the world has a correct mazub
	 * 
	 * @return	boolean:
	 * 			| if (!canHaveAsMazub(mazub))
	 *			|	return false;
	 *			| if (mazub.getWorld() != this)
	 *			|	return false;
	 *			|
	 *			| return true; 
	 *
	 */
	public boolean hasProperMazub() {
		Mazub mazub = getMazub();
		if (!canHaveAsMazub(mazub))
			return false;
		if (mazub.getWorld() != this)
			return false;

		return true;   
	}


	/**
	 * This method checks if the world has alien already as alien
	 * 
	 * @param 	alien
	 * 			| the alien that we want to add to the world
	 * @return  boolean:
	 * 			| this.getMazub() != alien
	 */
	public boolean hasAsMazub(Mazub alien){
		if (this.getMazub() != alien)
			return false;
		else
			return true;
	}

	private Mazub mazub = null;



	//////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	/**
	 * Sets the buzam in the world
	 * 
	 * @param 	buzam
	 * 			| the buzam that we want to add to the world
	 * 
	 * @effect	...
	 * 			this.buzam=buzam;
	 * 
	 */
	@Raw
	public void setBuzam(Buzam buzam){
		if (canHaveAsBuzam(buzam)){
			buzam.setWorld(this);
			this.buzam=buzam;
		}
	}


	/**
	 * This method returns Buzam in the world
	 * 
	 * @return   Buzam: this is an buzam
	 * 			| return buzam;
	 * 
	 */
	@Basic @Raw
	public Buzam getBuzam(){
		return buzam;
	}


	/**
	 * This method checks if the world can have Buzam
	 * 
	 * @param 	buzam
	 * 			| the buzam that we want to add to the world
	 * @return	boolean: true | false
	 * 			| return ((getBuzam() == null));
	 */
	@Raw
	public boolean canHaveAsBuzam(Buzam buzam){
		return ((getBuzam() == null));
	}


	/**
	 * This method checks if the world has a correct buzam
	 * 
	 * @return	boolean:
	 * 			| if (!canHaveAsBuzam(buzam))
	 *			|	return false;
	 *			| if (buzam.getWorld() != this)
	 *			|	return false;
	 *			|
	 *			| return true; 
	 *
	 */
	public boolean hasProperBuzam() {
		Buzam buzam = getBuzam();
		if (!canHaveAsBuzam(buzam))
			return false;
		if (buzam.getWorld() != this)
			return false;

		return true;   
	}


	/**
	 * This method checks if the world has buzam already as buzam
	 * 
	 * @param 	buzam
	 * 			| the buzam that we want to add to the world
	 * @return  boolean:
	 * 			| this.getBuzam() != buzam
	 */
	public boolean hasAsBuzam(Buzam buzam){
		if (this.getBuzam() != buzam)
			return false;
		else
			return true;
	}

	public void removeBuzam(Buzam buzam){
		if (buzam != null && this.buzam == buzam){
			this.buzam = null;
		} 
	}

	private Buzam buzam = null;






	/**
	 * This method returns all objects
	 * 
	 * @return	set<GameObject>
	 * 			| return Collections.unmodifiableSet(Objects);
	 * 
	 * 
	 */
	public Set<GameObject> getAllGameObjects(){

		Set<Plant> Plants = getAllPlants();
		Set<Shark> Sharks = getAllSharks();
		Set<Slime> Slimes = getAllSlimes();

		Set<GameObject> Objects = new HashSet<GameObject>();
		Objects.addAll(Plants);
		Objects.addAll(Sharks);
		Objects.addAll(Slimes);
		return Collections.unmodifiableSet(Objects);      
	}

	/**
	 * This method returns all objects, including Mazub and Buzam
	 * 
	 * @return	set<GameObject>
	 * 			| return Collections.unmodifiableSet(Objects);
	 * 
	 * 
	 */
	public Set<GameObject> getEachAndEveryObject(){

		Set<Plant> Plants = getAllPlants();
		Set<Shark> Sharks = getAllSharks();
		Set<Slime> Slimes = getAllSlimes();

		Set<GameObject> Objects = new HashSet<GameObject>();

		if(getMazub() != null)
			Objects.add(getMazub());
		if(getBuzam() != null)
			Objects.add(getBuzam());
		Objects.addAll(Plants);
		Objects.addAll(Sharks);
		Objects.addAll(Slimes);
		return Collections.unmodifiableSet(Objects); 

	}


	/**
	 * This method returns the number of objects in the world
	 * 
	 * @return	int
	 * 			| return Objects.size();
	 * 
	 */
	public int getNbGameObjects(){
		Set<Plant> Plants = getAllPlants();
		Set<Shark> Sharks = getAllSharks();
		Set<Slime> Slimes = getAllSlimes();

		Set<GameObject> Objects = new HashSet<GameObject>();
		Objects.addAll(Plants);
		Objects.addAll(Sharks);
		Objects.addAll(Slimes);
		return Objects.size();      
	}


	/**
	 * 
	 * @param 	object
	 * 			the object under investigation
	 * 
	 * @return	boolean:
	 * 			| return Objects.contains(object);
	 * 
	 */
	public boolean hasAsGameObject(GameObject object){
		Set<Plant> Plants = getAllPlants();
		Set<Shark> Sharks = getAllSharks();
		Set<Slime> Slimes = getAllSlimes();

		Set<GameObject> Objects = new HashSet<GameObject>();
		Objects.addAll(Plants);
		Objects.addAll(Sharks);
		Objects.addAll(Slimes);
		return Objects.contains(object);

	}


	/**
	 * This method adds the given plant to this world
	 * 
	 * @param 	plant
	 * 			The plant to be added
	 * 
	 * @effect	...
	 * 			| plant.setWorld(this);
	 * 
	 */
	@Raw
	public void addPlant(Plant plant){
		if (plant != null && getNbGameObjects()<100 && !isWorldStarted()){
			Plants.add(plant);
			plant.setWorld(this);
		}              
	}


	/**
	 * This method returns all plants from this world
	 * 
	 * @return	Set<plant>
	 * 			| return Collections.unmodifiableSet(this.Plants);
	 * 
	 */
	public Set<Plant> getAllPlants(){
		return Collections.unmodifiableSet(this.Plants);      
	}


	/**
	 * This method removes the given plant from this world
	 * 
	 * @param 	plant
	 * 			The plant to be removed
	 * 
	 * @effect	...
	 * 			| Plants.remove(plant);
	 * 
	 */
	@Raw   
	public void removePlant(Plant plant){
		if (plant != null && Plants.contains(plant)){
			Plants.remove(plant);
		}   
	}

	private Set<Plant> Plants = new HashSet<>();


	/**
	 * This method adds the given shark to this world
	 * 
	 * @param 	shark
	 * 			The shark to be added
	 * 
	 * @effect	...
	 * 			| shark.setWorld(this);
	 * 
	 */
	@Raw
	public void addShark(Shark shark){
		if (shark != null && getNbGameObjects()<100  && !isWorldStarted()){
			Sharks.add(shark);
			shark.setWorld(this);
		}              
	}


	/**
	 * This method returns all sharks from this world
	 * 
	 * @return	Set<Shark>
	 * 			| return Collections.unmodifiableSet(this.Sharks); 
	 */
	public Set<Shark> getAllSharks(){
		return Collections.unmodifiableSet(this.Sharks);      
	}


	/**
	 * This method removes the given shark from this world
	 * 
	 * @param 	shark
	 * 			The shark to be removed
	 * 
	 * @effect	...
	 * 			| Sharks.remove(shark);
	 */
	@Raw   
	public void removeShark(Shark shark){
		if (shark != null && Sharks.contains(shark)){
			Sharks.remove(shark);
		}      
	}

	private Set<Shark> Sharks = new HashSet<>();


	/**
	 * This method adds the given slime to this world and gives a school
	 * 
	 * @param 	slime
	 * 			The slime to be added
	 * 
	 * @effect	...
	 * 			| slime.setWorld(this);
	 * @effect 	...
	 * 			| this.addSchool(slime.getSchool()); 
	 * 
	 */
	@Raw
	public void addSlime(Slime slime){
		if (slime != null && getNbSchools() < 10 && getNbGameObjects()<100  && !isWorldStarted()){
			Slimes.add(slime);
			slime.setWorld(this);
			this.addSchool(slime.getSchool());	
		}              
	}


	/**
	 * This method returns all slimes from this world
	 * 
	 * @return	Set<Slimes>
	 * 			| return Collections.unmodifiableSet(this.Slimes); 
	 */
	public Set<Slime> getAllSlimes(){
		return Collections.unmodifiableSet(this.Slimes);      
	}


	/**
	 * This method removes the given slime from this world
	 * 
	 * @param 	slime
	 * 			The slime to be removed
	 * 
	 * @effect	...
	 * 			| Slimes.remove(slime);
	 */
	@Raw   
	public void removeSlime(Slime slime){
		if (slime != null && Slimes.contains(slime)){
			Slimes.remove(slime);
		}      
	}


	/**
	 * This method checks if the given Slime is in slimes
	 * 
	 * @param	slime
	 * 			the slime to be investigated
	 * 
	 * @return	boolean
	 * 			| return Slimes.contains(slime);
	 */
	public boolean hasAsSlime(Slime slime) {
		return Slimes.contains(slime);
	}

	private Set<Slime> Slimes = new HashSet<>();


	/**
	 * This method returns all schools from this world
	 * 
	 * @return	Set<School>
	 * 			| return Collections.unmodifiableSet(Schools);
	 */
	@Basic @Raw
	public Set<School> getSchools() {
		return Collections.unmodifiableSet(Schools);
	}


	/**
	 * This method checks if this world can have the given school
	 * 
	 * @param 	school
	 * 			The school to be investigated
	 * 
	 * @return	boolean
	 * 			| return (school != null && !school.isTerminated() && 
	 * 			| getNbSchools() < 10);
	 */
	@Raw
	public boolean canHaveAsSchool(School school) {
		return (school != null && !school.isTerminated() && getNbSchools() < 10);
	}


	/**
	 * This method checks if the given school is in Schools
	 * @param 	school
	 * 			The school to be investigated
	 * 
	 * @return	boolean
	 * 			| return Schools.contains(school);
	 */
	public boolean hasAsSchool(School school){
		return Schools.contains(school);

	}


	/**
	 * This method returns the number of schools in this world
	 * 
	 * @return	int 
	 * 			| int i = Schools.size();
	 *			| return i;
	 * 			
	 */
	public int getNbSchools(){
		int i = Schools.size();
		return i;
	}


	/**
	 * This method adds the given school to this world
	 * 
	 * @param 	school
	 * 			The school to be added
	 * 
	 * @effect	...
	 * 			| school.setWorld(this);
	 * 
	 */
	@Raw
	public void addSchool(School school) {
		if (canHaveAsSchool(school) && !hasAsSchool(school) ){
			Schools.add(school);
			school.setWorld(this);
		}
	}


	/**
	 * This method removes the given school from this World
	 * 
	 * @param 	school
	 * 			The school to be removed
	 * 
	 * @effect	...
	 * 			| Schools.remove(school);
	 * 
	 */
	@Raw   
	public void removeSchool(School school){
		assert school != null;
		if (hasAsSchool(school)){
			Schools.remove(school);
		}      
	}


	/**
	 * @effect	...
	 * 			| if (school.getNbSlimes() == 0)
	 *			| 	school.terminate();
	 */
	public void updateSchools(){
		Set<School> OriginalObjects = getSchools();
		Set<School> CopyObjects = new HashSet<>(OriginalObjects);
		for(School school: CopyObjects){
			if (school.getNbSlimes() == 0)
				school.terminate();
		}
	}


	private Set<School> Schools = new HashSet<>();





	/**
	 * Return a boolean indicating whether or not this world
	 * is terminated.
	 */
	@Basic
	@Raw
	public boolean isTerminated() {
		return this.isTerminated;
	}

	/**
	 * Terminate this world.
	 *
	 * @post    This world is terminated.
	 *          | new.isTerminated()
	 */
	public void terminate() {
		// To be completed.
		this.isTerminated = true;
	}

	private boolean isTerminated;


	/**
	 * This method returns an array of the size of the world in pixels
	 * The first element are the pixels of the width
	 * The second element are the pixels of the height
	 * 
	 * @return	int[]
	 * 			| return pixList ;
	 */
	public int[] getWorldSizeInPixels(){
		int pixX = (int) getWorldSize().getElemx();
		int pixY = (int) getWorldSize().getElemy();
		int[]pixList = {pixX,pixY};
		return pixList ;
	}


	/**
	 * This method returns an array of 4 elements for the visible window
	 * The left bottom pixel and the right top pixel
	 * 
	 * @return	int []
	 * 			| return getCurrentWindow();
	 */
	public int[] getVisibleWindow(){

		int posMazubX = (int) this.getMazub().getPos().getElemx();
		int posMazubY = (int) this.getMazub().getPos().getElemy();

		int WorldX = (int) getWorldSize().getElemx();
		int WorldY = (int) getWorldSize().getElemy();

		if (posMazubX < 0+getVisWindowWidth()/2){
			setVisWindowLeft(0);
			setVisWindowRight(VisWindowWidth);
		}else if (posMazubX > WorldX - getVisWindowWidth()/2){
			setVisWindowLeft(WorldX - getVisWindowWidth());
			setVisWindowRight(WorldX);
		}else{
			setVisWindowLeft(posMazubX - getVisWindowWidth()/2);
			setVisWindowRight(posMazubX + getVisWindowWidth()/2);
		}
		if (posMazubY < getVisWindowHeight()/2){
			setVisWindowBottom(0);
			setVisWindowTop(getVisWindowHeight());
		}else if(posMazubY > WorldY - getVisWindowHeight()/2){
			setVisWindowBottom(WorldY - getVisWindowHeight());
			setVisWindowTop(WorldY);
		}else{
			setVisWindowBottom(posMazubY - getVisWindowHeight()/2);
			setVisWindowTop(posMazubY + getVisWindowHeight()/2);
		}
		return getCurrentWindow();
	}


	/**
	 * This method sets the left pixel of the currentWindow
	 * 
	 * @param 	left
	 * 			The new left pixel
	 * @effect	...
	 * 			| CurrentWindow[0] = left;
	 * 
	 */
	private void setVisWindowLeft(int left){
		CurrentWindow[0] = left;
	}


	/**
	 * This method sets the bottom pixel of the currentWindow
	 * 
	 * @param 	bottom
	 * 			The new bottom pixel
	 * @effect	...
	 * 			| CurrentWindow[1] = bottom;
	 * 
	 */
	private void setVisWindowBottom(int bottom){
		CurrentWindow[1] = bottom;
	}


	/**
	 * This method sets the right pixel of the currentWindow
	 * 
	 * @param 	right
	 * 			The new right pixel
	 * @effect	...
	 * 			| CurrentWindow[2] = right;
	 * 
	 */
	private void setVisWindowRight(int right){
		CurrentWindow[2] = right;
	}


	/**
	 * This method sets the top pixel of the currentWindow
	 * 
	 * @param 	top
	 * 			The new top pixel
	 * @effect	...
	 * 			| CurrentWindow[3] = top;
	 * 
	 */
	private void setVisWindowTop(int top){
		CurrentWindow[3] = top;
	}


	/**
	 * This method returns the current window of this world
	 * 
	 * @return	int []
	 * 			| return CurrentWindow;
	 */
	private int[] getCurrentWindow(){
		return CurrentWindow;
	}


	/**
	 * This method returns the visible window width of this world
	 * 
	 * @return	int
	 * 			| return VisWindowWidth;
	 */
	private int getVisWindowWidth(){
		return VisWindowWidth;
	}


	/**
	 * This method returns the visible window height of this world
	 * 
	 * @return	int
	 * 			| return VisWindowHeight;
	 */
	private int getVisWindowHeight(){
		return VisWindowHeight;
	}




	/**
	 * This method returns the tile length of this world
	 * 
	 * @return	int
	 * 			return TileSize;
	 */
	public int getTileLength(){
		return TileSize;
		//wordt dit in andere klasse gebruikt? anders kopie geven!
	}



	/**
	 * This method converts a given tile position in its left bottom pixel
	 * in this world
	 * 
	 * @param 	tileX
	 * 			The X coordinate of the tile
	 * @param 	tileY
	 * 			The Y coordinate of the tile
	 * @return	int[]
	 * 			| return tileList;
	 */
	public int[] convertXTYTtoXY(int tileX, int tileY){
		int xPixOfTile = tileX*getTileSize();
		int yPixOfTile = tileY*getTileSize();
		int[]tileList = {xPixOfTile,yPixOfTile};
		return tileList;
	}


	/**
	 * This method gives the tile of the given pixel
	 * 
	 * @param 	pixelX
	 * 			The X coordinate of the pixel
	 * @param 	pixelY
	 * 			The Y coordinate of the pixel
	 * @return	int[]
	 * 			return tileList;
	 */
	public int[] convertXYtoXTYT(int pixelX, int pixelY){
		int xt = pixelX/getTileSize();
		int yt = pixelY/getTileSize();
		int[]tileList = {xt,yt};
		return tileList;

	}



	/**
	 * Returns the tile positions of all tiles within the given rectangular
	 * region.
	 * 
	 * @param 	world
	 *            The world from which the tile positions should be returned.
	 * @param 	pixelLeft
	 *            The x-coordinate of the left side of the rectangular region.
	 * @param 	pixelBottom
	 *            The y-coordinate of the bottom side of the rectangular region.
	 * @param 	pixelRight
	 *            The x-coordinate of the right side of the rectangular region.
	 * @param 	pixelTop
	 *            The y-coordinate of the top side of the rectangular region.
	 * 
	 * @return 	An array of tile positions, where each position (x_T, y_T) is
	 *         	represented as an array of 2 elements, containing the horizontal
	 *         	(x_T) and vertical (y_T) coordinate of a tile in that order.
	 *         	The returned array is ordered from left to right,
	 *         	bottom to top: all positions of the bottom row (ordered from
	 *         	small to large x_T) precede the positions of the row above that.
	 * 
	 * @throws 	IllegalArgumentException
	 * 			If the given coordinates do not form a rectangular
	 * 			or the rectangular is not positioned in this world anymore
	 * 			a argument is thrown
	 * 			| throw new IllegalArgumentException();	
	 * 
	 */
	public int[][] getTilePositionsIn(int pixelLeft, int pixelBottom,
			int pixelRight, int pixelTop) throws IllegalArgumentException{

		if ((pixelLeft < 0 || pixelBottom < 0 || pixelRight < 0 || pixelTop < 0)||
				(pixelLeft >= pixelRight)||
				(pixelTop <= pixelBottom)||
				(pixelRight > getWorldSizeInPixels()[0])||
				(pixelLeft > getWorldSizeInPixels()[0])) {

			throw new IllegalArgumentException();
		}

		int xTLeft = convertXYtoXTYT(pixelLeft, pixelBottom)[0];
		int yTBottom = convertXYtoXTYT(pixelLeft, pixelBottom)[1];
		int xTRight = convertXYtoXTYT(pixelRight, pixelTop)[0];
		int yTTop = convertXYtoXTYT(pixelRight, pixelTop)[1];

		ArrayList<int[]> TileMatrixList = new ArrayList<int[]>();

		for(int j = yTBottom; j <= yTTop;j++){
			for(int i = xTLeft; i <= xTRight;i++){
				int [] coord = new int[]{i,j};
				TileMatrixList.add(coord);
			}
		}

		int [][] TileMatrixArr = TileMatrixList.toArray(new int[TileMatrixList.size()][]);
		return TileMatrixArr;
	}





	/**
	 * This method creates a matrix to keep track of every tiletype
	 * 
	 * @return 	int[][]
	 * 			| return matrix;
	 */
	private int[][] createInitialTileMatrix(){
		int[][] matrix= new int[(int) getNbTiles().getElemy()][(int) getNbTiles().getElemx()];
		return matrix;
	}


	/**
	 * This method returns the tileMatrix
	 * 
	 * @return	int[][]
	 * 			return this.TileMatrix;
	 */
	private int[][] getTileMatrix(){
		return this.TileMatrix;
	}


	/**
	 * This method returns the tilesize of this world
	 * 
	 * @return	int
	 * 			| return TileSize;
	 * 
	 */
	public int getTileSize(){
		return TileSize;
	}


	/**
	 * This method returns a vector with it first element the number of X tiles
	 * and the second element the number of Y tiles.
	 * 
	 * @return	Vector
	 * 			| return new Vector(NbTiles.getElemx(),NbTiles.getElemy());
	 */
	private Vector getNbTiles() {
		return new Vector(NbTiles.getElemx(),NbTiles.getElemy());
	}


	/**
	 * This method sets the geologicalfeaturs of tiles
	 * 
	 * @param 	tileX
	 * 			The X coordinate of the tile
	 * @param 	tileY
	 * 			The Y coordinate of the tile
	 * @param 	tileType
	 * 			The type to be set for the given tile
	 * @effect	...
	 * 			| getTileMatrix()[tileY][tileX] = tileType;
	 */
	public void setGeologicalFeature(int tileX, int tileY, int tileType){
		if (tileType >=0 && tileType <=3){
			getTileMatrix()[tileY][tileX] = tileType;
		}
	}


	/**
	 * This method returns the tiletype of a given pixel
	 * 
	 * @param 	posx
	 * 			The X coordinate of the given pixel
	 * @param 	posy
	 * 			The Y coordinate of the given pixel
	 * @return	int
	 * 			| return tile;
	 * 
	 * @throws 	IllegalArgumentException
	 * 			If the position of the given pixel is not in the
	 * 			dimensions of this world an argument is thrown
	 * 
	 */
	public int getGeologicalFeature(int posx, int posy) throws IllegalArgumentException{
		try{
			int posxT= convertXYtoXTYT(posx,posy)[0];
			int posyT= convertXYtoXTYT(posx,posy)[1];

			if ((posxT != 0) && (posx%posxT == 0) && (getWorldSize().getElemx() == posx)){
				posxT --;
			}
			if ((posyT != 0) && (posy%posyT == 0) && (getWorldSize().getElemy() == posy)){
				posyT --;
			}
			int tile = TileMatrix[posyT][posxT];
			return tile;
		}catch(IndexOutOfBoundsException e){
			throw new IllegalArgumentException();
		}
	}	


	/**
	 * This method returns if the given pixel is on passable terrain
	 * 
	 * @param 	posx
	 * 			The X coordinate of the given pixel
	 * @param 	posy
	 * 			The Y coordinate of the given pixel
	 * @return	boolean
	 * 			| if (feature == 1)
	 *			|	return false;
	 *			| else
	 *			|	return true;
	 *
	 */
	public boolean isPassableTerrain(double posx,double posy){
		int feature = getGeologicalFeature((int)posx, (int)posy);
		if (feature == 1)
			return false;
		else
			return true;
	}


	/**
	 * This method returns the size of this world
	 * 
	 * @return	Vector: The first element of the vector is the number of
	 * 			X pixels and the second element of the vector is the number of
	 * 			Y pixels
	 * 			| return new Vector(WorldSize.getElemx(),WorldSize.getElemy());
	 * 
	 */
	public Vector getWorldSize() {
		return new Vector(WorldSize.getElemx(),WorldSize.getElemy());
	}


	/**
	 * This method returns the targettile of this world
	 * 
	 * @return	Vector
	 * 			return new Vector(TargetTile.getElemx(),TargetTile.getElemy());
	 * 
	 */
	public Vector getTargetTile() {
		return new Vector(TargetTile.getElemx(),TargetTile.getElemy());
	}


	/**
	 * This method indicates that this world has started
	 * 
	 * @effect	...
	 * 			| setWorldStarted(true);
	 */
	public void startWorld(){
		if (!isWorldStarted())
			setWorldStarted(true);
	}


	/**
	 * This method returns if the world is already started
	 * 
	 * @return	boolean
	 * 			| boolean i;
	 *			| if (WorldStarted)
	 *			|	i = true;
	 * 			| else
	 *			|	i = false;
	 *			| return i;
	 *
	 */
	public boolean isWorldStarted() {
		boolean i;
		if (WorldStarted)
			i = true;
		else
			i = false;
		return i;
	}


	/**
	 * This method sets WorldStarted
	 * 
	 * @param  	worldStarted
	 * 			The new boolean value of WorldStarted
	 * 			The value to be set
	 * @effect	...
	 * 			| WorldStarted = worldStarted;
	 */
	private void setWorldStarted(boolean worldStarted) {
		WorldStarted = worldStarted;
	}


	/**
	 * This method resets the clock to zero
	 * 
	 * @effect	...
	 * 			clock = 0;
	 */
	private void startClock(){
		clock = 0;
	}


	/**
	 * This method advances the clock wit dt time
	 * 
	 * @param 	dt
	 * 			The amount of new time to be added
	 * @effect	...
	 * 			| clock = clock + dt;
	 */
	private void advanceTimeOfClock(double dt){
		clock = clock + dt;
	}


	/**
	 * This method returns the clock of this world
	 * 
	 * @return	double
	 * 			| return clock;
	 */
	public double getTime(){
		return clock;
	}
























	private final int VisWindowWidth;
	private final int VisWindowHeight;
	private int[] CurrentWindow={0,0,0,0};
	private int[][] TileMatrix;
	private final int TileSize;
	private final Vector NbTiles;
	private final Vector WorldSize;
	private final Vector TargetTile;
	private boolean WorldStarted = false;
	private double clock=0;

	//



}