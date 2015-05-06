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
 *                      | hasProperMazub()
 * @Invar       ...
 *                      | getSchools().size() <= 10
 * @Invar       ...
 * 						| getNbObjects() <= 100                
 * @author svenverret
 *
 */
public class World {   

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

	public void advanceTime(@Raw double dt)throws IllegalArgumentException{

		if ((dt < 0.0) || (dt > 0.2)){
			throw new IllegalArgumentException();
		}
		startWorld();


		advanceTimeOfClock(dt);

		Set<GameObject> OriginalObjects = getAllObjects();
		Set<GameObject> CopyObjects = new HashSet<>(OriginalObjects);

		Mazub alien = getMazub();

		alien.advanceTime(dt);
		if ((alien.isOutOfBounds()) || (alien.getHP() == 0)){
			alien.dies();
			System.out.println("Mazub Died");
		}

		for (GameObject object: CopyObjects){
			object.advanceTime(dt);
			if ((object.isOutOfBounds()) || (object.getHP() == 0)){


				object.dies();
				System.out.println("Object Died    :    " + object);

			}
		}
		
		Buzam alienbuzam = getBuzam();

		alienbuzam.advanceTime(dt);
		if ((alienbuzam.isOutOfBounds()) || (alienbuzam.getHP() == 0)){
			alienbuzam.dies();
			System.out.println("Buzam Died");
		}

		for (GameObject object: CopyObjects){
			object.advanceTime(dt);
			if ((object.isOutOfBounds()) || (object.getHP() == 0)){


				object.dies();
				System.out.println("Object Died    :    " + object);

			}
		}
		
		for (GameObject object: CopyObjects){
			if (object.isDead()){
				object.terminate();
			}
		}
		updateSchools();
	}


	//////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////

	@Raw
	public void setMazub(Mazub alien){
		if (canHaveAsMazub(alien)){
			alien.setWorld(this);
			this.mazub=alien;
			
		}
	}

	@Basic @Raw
	public Mazub getMazub(){
		return mazub;
	}

	@Raw
	protected boolean canHaveAsMazub(Mazub alien){
		return ((getMazub() == null));
	}

	protected boolean hasProperMazub() {
		Mazub mazub = getMazub();
		if (!canHaveAsMazub(mazub))
			return false;
		if (mazub.getWorld() != this)
			return false;

		return true;   
	}

	public boolean hasAsMazub(Mazub alien){
		if (this.getMazub() != alien)
			return false;
		else
			return true;
	}

	private Mazub mazub;

	
	
	
	
	////
	
	
	
	@Raw
	public void setBuzam(Buzam alien){
		if (canHaveAsBuzam(alien)){
			alien.setWorld(this);
			this.buzam=alien;
			
		}
	}

	@Basic @Raw
	public Buzam getBuzam(){
		return buzam;
	}

	@Raw
	protected boolean canHaveAsBuzam(Buzam alien){
		return ((getBuzam() == null));

	}

	protected boolean hasProperBuzam() {
		Buzam buzam = getBuzam();
		if (!canHaveAsBuzam(buzam))
			return false;
		if (buzam.getWorld() != this)
			return false;

		return true;   
	}

	public boolean hasAsBuzam(Buzam alien){
		if (this.getBuzam() != alien)
			return false;
		else
			return true;
	}

	private Buzam buzam;
	
	////
	
	
	
	
	public Set<GameObject> getAllObjects(){

		Set<Plant> Plants = getAllPlants();
		Set<Shark> Sharks = getAllSharks();
		Set<Slime> Slimes = getAllSlimes();

		Set<GameObject> Objects = new HashSet<GameObject>();
		Objects.addAll(Plants);
		Objects.addAll(Sharks);
		Objects.addAll(Slimes);
		return Collections.unmodifiableSet(Objects);      
	}

	public int getNbObjects(){
		Set<Plant> Plants = getAllPlants();
		Set<Shark> Sharks = getAllSharks();
		Set<Slime> Slimes = getAllSlimes();

		Set<GameObject> Objects = new HashSet<GameObject>();
		Objects.addAll(Plants);
		Objects.addAll(Sharks);
		Objects.addAll(Slimes);
		return Objects.size();      
	}

	public boolean hasAsObject(GameObject object){
		Set<Plant> Plants = getAllPlants();
		Set<Shark> Sharks = getAllSharks();
		Set<Slime> Slimes = getAllSlimes();

		Set<GameObject> Objects = new HashSet<GameObject>();
		Objects.addAll(Plants);
		Objects.addAll(Sharks);
		Objects.addAll(Slimes);
		return Objects.contains(object);

	}



	@Raw
	public void addPlant(Plant plant){
		//System.out.println(plant);
		if (plant != null && getNbObjects()<100 && !isWorldStarted()){
			Plants.add(plant);
			plant.setWorld(this);
		}              
	}

	public Set<Plant> getAllPlants(){
		return Collections.unmodifiableSet(this.Plants);      
	}

	@Raw   
	protected void removePlant(Plant plant){
		if (plant != null && Plants.contains(plant)){
			System.out.println();
			System.out.println("Remove Plant");
			Plants.remove(plant);
		}   
		System.out.println(Plants.contains(plant));
	}

	private Set<Plant> Plants = new HashSet<>();

	@Raw
	public void addShark(Shark shark){
		//System.out.println(shark);
		if (shark != null && getNbObjects()<100  && !isWorldStarted()){
			Sharks.add(shark);
			shark.setWorld(this);
		}              
	}

	public Set<Shark> getAllSharks(){
		return Collections.unmodifiableSet(this.Sharks);      
	}

	@Raw   
	protected void removeShark(Shark shark){
		if (shark != null && Sharks.contains(shark)){
			Sharks.remove(shark);
		}      
	}

	private Set<Shark> Sharks = new HashSet<>();

	@Raw
	public void addSlime(Slime slime){
		//System.out.println(slime);
		if (slime != null && getNbSchools() < 10 && getNbObjects()<100  && !isWorldStarted()){
			Slimes.add(slime);
			slime.setWorld(this);
			this.addSchool(slime.getSchool());	
		}              
	}

	public Set<Slime> getAllSlimes(){
		return Collections.unmodifiableSet(this.Slimes);      
	}

	@Raw   
	protected void removeSlime(Slime slime){
		if (slime != null && Slimes.contains(slime)){
			Slimes.remove(slime);
		}      
	}

	public boolean hasAsSlime(Slime slime) {
		return Slimes.contains(slime);
	}

	private Set<Slime> Slimes = new HashSet<>();



	@Basic @Raw
	public Set<School> getSchools() {
		return Collections.unmodifiableSet(Schools);
	}

	@Raw
	public boolean canHaveAsSchool(School school) {
		return (school != null && !school.isTerminated() && getNbSchools() < 10);
	}

	public boolean hasAsSchool(School school){
		return Schools.contains(school);

	}
	public int getNbSchools(){
		int i = Schools.size();
		return i;
	}

	@Raw
	public void addSchool(School school) {
		if (canHaveAsSchool(school) && !hasAsSchool(school) ){
			Schools.add(school);
			school.setWorld(this);
		}
	}

	@Raw   
	protected void removeSchool(School school){
		assert school != null;
		if (hasAsSchool(school)){
			Schools.remove(school);
		}      
	}

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
	protected boolean isTerminated() {
		return this.isTerminated;
	}

	/**
	 * Terminate this world.
	 *
	 * @post    This world is terminated.
	 *          | new.isTerminated()
	 */
	protected void terminate() {
		// To be completed.
		this.isTerminated = true;
	}

	private boolean isTerminated;

	public int[] getWorldSizeInPixels(){
		int pixX = (int) getWorldSize().getElemx();
		int pixY = (int) getWorldSize().getElemy();
		int[]pixList = {pixX,pixY};
		return pixList ;
	}


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

	private void setVisWindowLeft(int left){
		CurrentWindow[0] = left;
	}

	private void setVisWindowBottom(int bottom){
		CurrentWindow[1] = bottom;
	}

	private void setVisWindowRight(int right){
		CurrentWindow[2] = right;
	}

	private void setVisWindowTop(int top){
		CurrentWindow[3] = top;
	}
	private int[] getCurrentWindow(){
		return CurrentWindow;
	}
	private int getVisWindowWidth(){
		return VisWindowWidth;
	}
	private int getVisWindowHeight(){
		return VisWindowHeight;
	}





	public int getTileLength(){
		return TileSize;
		//wordt dit in andere klasse gebruikt? anders kopie geven!
	}


	public int[] convertXTYTtoXY(int tileX, int tileY){
		int xPixOfTile = tileX*getTileSize();
		int yPixOfTile = tileY*getTileSize();
		int[]tileList = {xPixOfTile,yPixOfTile};
		return tileList;
	}

	public int[] convertXYtoXTYT(int pixelX, int pixelY){
		int xt = pixelX/getTileSize();
		int yt = pixelY/getTileSize();
		int[]tileList = {xt,yt};
		return tileList;

	}

	public int[][] getTilePositionsIn(int pixelLeft, int pixelBottom,
			int pixelRight, int pixelTop) throws IllegalArgumentException{

		if ((pixelLeft < 0 || pixelBottom < 0 || pixelRight < 0 || pixelTop < 0)||
				(pixelLeft > pixelRight)||
				(pixelTop < pixelBottom)||
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






	private int[][] createInitialTileMatrix(){
		int[][] matrix= new int[(int) getNbTiles().getElemy()][(int) getNbTiles().getElemx()];
		return matrix;
	}
	private int[][] getTileMatrix(){
		return this.TileMatrix;
	}

	public int getTileSize(){
		return TileSize;
	}

	private Vector getNbTiles() {
		return new Vector(NbTiles.getElemx(),NbTiles.getElemy());
	}

	public void setGeologicalFeature(int tileX, int tileY, int tileType){
		if (tileType >=0 && tileType <=3){
			getTileMatrix()[tileY][tileX] = tileType;
		}
	}

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

	public boolean isPassableTerrain(double posx,double posy){
		int feature = getGeologicalFeature((int)posx, (int)posy);
		if (feature == 1)
			return false;
		else
			return true;
	}


	public Vector getWorldSize() {
		return new Vector(WorldSize.getElemx(),WorldSize.getElemy());
	}

	public Vector getTargetTile() {
		return new Vector(TargetTile.getElemx(),TargetTile.getElemy());
	}

	public void startWorld(){
		if (!isWorldStarted())
			setWorldStarted(true);
	}

	public boolean isWorldStarted() {
		boolean i;
		if (WorldStarted)
			i = true;
		else
			i = false;
		return i;
	}

	private void setWorldStarted(boolean worldStarted) {
		WorldStarted = worldStarted;
	}


	private void startClock(){
		clock = 0;
	}

	private void advanceTimeOfClock(double dt){
		clock = clock + dt;
	}

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