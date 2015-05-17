package jumpingalien.part2.facade;

import java.util.Collection;

import jumpingalien.model.Mazub;
import jumpingalien.model.Plant;
import jumpingalien.model.School;
import jumpingalien.model.Shark;
import jumpingalien.model.Slime;
import jumpingalien.model.World;
import jumpingalien.part2.facade.IFacadePart2;
import jumpingalien.util.ModelException;
import jumpingalien.util.Sprite;

public class Facade implements IFacadePart2{

	@Override
	public Mazub createMazub(int pixelLeftX, int pixelBottomY, Sprite[] sprites) {
		try{
			return new Mazub(pixelLeftX, pixelBottomY, sprites);
		} catch (IllegalArgumentException e){
			throw new ModelException(e.getMessage());

		}
	}

	@Override
	public int[] getLocation(Mazub alien) {
		int[] i = {(int) alien.getPos().getElemx(),(int) alien.getPos().getElemy()};
		return i;
	}

	@Override
	public double[] getVelocity(Mazub alien) {
		double[] i = {alien.getVelocity().getElemx(),alien.getVelocity().getElemy()};
		return i;
	}

	@Override
	public double[] getAcceleration(Mazub alien) {
		double[] i = {alien.getAccCurr().getElemx(),alien.getAccCurr().getElemy()};
		return i;
	}

	@Override
	public int[] getSize(Mazub alien) {
		int[] i = {(int) alien.getSize().getElemx(),(int) alien.getSize().getElemy()};
		return i;
	}

	@Override
	public Sprite getCurrentSprite(Mazub alien) {
		Sprite sprite = alien.getCurrentSprite();
		return sprite;
	}

	@Override
	public void startJump(Mazub alien) {
		try{
			alien.startJump();     
		} catch(IllegalStateException exc){
			throw new ModelException("State Exception: Start Jumping");
		}
	}      


	@Override
	public void endJump(Mazub alien) {
		try{
			alien.endJump();       
		} catch(IllegalStateException exc){
			throw new ModelException("State Exception: End Jumping");
		}
	}

	@Override
	public void startMoveLeft(Mazub alien) {
		alien.startMove(false);

	}

	@Override
	public void endMoveLeft(Mazub alien) {
		alien.endMove();

	}

	@Override
	public void startMoveRight(Mazub alien) {
		alien.startMove(true);

	}

	@Override
	public void endMoveRight(Mazub alien) {
		alien.endMove();

	}

	@Override
	public void startDuck(Mazub alien) {
		alien.startDuck();

	}

	@Override
	public void endDuck(Mazub alien) {
		try{
			alien.pressEndDuck();
		} catch(IllegalStateException e){
			throw new ModelException("Illegal State: Ducking");
		}

	}

	@Override
	public void advanceTime(Mazub alien, double dt) {
		try{
			alien.advanceTimeWithoutProgram(dt);
		}catch(IllegalArgumentException e){
			throw new ModelException("Wrong dT");
		}

	}





	@Override
	public int getNbHitPoints(Mazub alien) {
		return alien.getHP();
	}

	@Override
	public World createWorld(int tileSize, int nbTilesX, int nbTilesY,
			int visibleWindowWidth, int visibleWindowHeight, int targetTileX,
			int targetTileY) {

		return new World(tileSize,nbTilesX, nbTilesY,
				visibleWindowWidth, visibleWindowHeight, targetTileX,
				targetTileY);
	}

	@Override
	public int[] getWorldSizeInPixels(World world) {


		int[] result = new int[]{(int) (world.getWorldSize().getElemx()),(int) (world.getWorldSize().getElemy())};
		return result;

	}

	@Override
	public int getTileLength(World world) {
		return world.getTileSize();
	}

	@Override
	public void startGame(World world) {
		world.startWorld();
	}

	@Override
	public boolean isGameOver(World world) {
		return (world.getMazub().getHP() == 0 || world.getMazub().didPlayerWin());
	}

	@Override
	public boolean didPlayerWin(World world) {
		
		return world.getMazub().didPlayerWin();
	}

	@Override
	public void advanceTime(World world, double dt) {
		world.advanceTime(dt);

	}

	@Override
	public int[] getVisibleWindow(World world) {
		return world.getVisibleWindow();
	}

	@Override
	public int[] getBottomLeftPixelOfTile(World world, int tileX, int tileY) {
		return world.convertXTYTtoXY(tileX, tileY);
	}

	@Override
	public int[][] getTilePositionsIn(World world, int pixelLeft,
			int pixelBottom, int pixelRight, int pixelTop) {
		return world.getTilePositionsIn(pixelLeft, pixelBottom, pixelRight, pixelTop);
	}

	@Override
	public int getGeologicalFeature(World world, int pixelX, int pixelY)
			throws ModelException {

		try{
			return world.getGeologicalFeature(pixelX, pixelY);
		}catch(IllegalArgumentException e){
			throw new ModelException("Get geological feature has failed");
		}
	}

	@Override
	public void setGeologicalFeature(World world, int tileX, int tileY,
			int tileType) {
		if (!world.isWorldStarted())
			world.setGeologicalFeature(tileX,tileY,tileType);
	}

	@Override
	public void setMazub(World world, Mazub alien) {
		world.setMazub(alien);

	}

	@Override
	public boolean isImmune(Mazub alien) {
		if (alien.isImmune())
			return true;
		else
			return false;
	}

	@Override
	public Plant createPlant(int x, int y, Sprite[] sprites) {
		try{return new Plant(x,y,sprites);
		}catch(IllegalArgumentException e){
			throw new ModelException("Illegal Plant Sprites");
		}
	}

	@Override
	public void addPlant(World world, Plant plant) {
		world.addPlant(plant);

	}

	@Override
	public Collection<Plant> getPlants(World world) {	
		return world.getAllPlants();
	}

	@Override
	public int[] getLocation(Plant plant) {
		int x= (int) plant.getPos().getElemx();
		int y= (int) plant.getPos().getElemy();
		int[] a ={x,y};
		return a;

	}

	@Override
	public Sprite getCurrentSprite(Plant plant) {
		return plant.getCurrentSprite();
	}

	@Override
	public Shark createShark(int x, int y, Sprite[] sprites) {
		try{return new Shark(x,y,sprites);
		}catch(IllegalArgumentException e){
			throw new ModelException("Illegal Shark Sprites");
		}
	}

	@Override
	public void addShark(World world, Shark shark) {
		world.addShark(shark);

	}

	@Override
	public Collection<Shark> getSharks(World world) {
		return world.getAllSharks();
	}

	@Override
	public int[] getLocation(Shark shark) {
		int x= (int) shark.getPos().getElemx();
		int y= (int) shark.getPos().getElemy();
		int[] a ={x,y};
		return a;
	}

	@Override
	public Sprite getCurrentSprite(Shark shark) {
		return shark.getCurrentSprite();
	}

	@Override
	public School createSchool() {
		return new School();
	}

	@Override
	public Slime createSlime(int x, int y, Sprite[] sprites, School school) {
		try{
			Slime slime = new Slime(x,y,sprites,school);
			return slime;
		}catch(IllegalArgumentException e){
			if (e.getCause().toString() == "Sprites")
				throw new ModelException("Illegal Slime Sprites");
			return null;
		}
	}

	@Override
	public void addSlime(World world, Slime slime) {
		world.addSlime(slime);
	}

	@Override
	public Collection<Slime> getSlimes(World world) {
		return world.getAllSlimes();
	}

	@Override
	public int[] getLocation(Slime slime) {
		int x= (int) slime.getPos().getElemx();
		int y= (int) slime.getPos().getElemy();
		int[] a ={x,y};
		return a;
	}

	@Override
	public Sprite getCurrentSprite(Slime slime) {
		return slime.getCurrentSprite();
	}

	@Override
	public School getSchool(Slime slime) {
		return slime.getSchool();
	}

}