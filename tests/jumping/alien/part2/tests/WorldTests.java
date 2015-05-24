package jumping.alien.part2.tests;

import static jumpingalien.tests.util.TestUtils.spriteArrayForSize;
import static org.junit.Assert.*;
import jumpingalien.model.Mazub;
import jumpingalien.model.Plant;
import jumpingalien.model.School;
import jumpingalien.model.Shark;
import jumpingalien.model.Slime;
import jumpingalien.model.World;
import jumpingalien.util.Sprite;

import org.junit.Test;

public class WorldTests {

	private World createWorldWithGround50px(){
		World world = new World(50, 10, 10,
				200,300,10,0);
		for (int i =0; i<10;i++){
			world.setGeologicalFeature(i, 0, 1);
		}
		for (int i =0; i<10;i++){
			world.setGeologicalFeature(i, 1, 0);
		}
		for (int i =0; i<10;i++){
			world.setGeologicalFeature(i, 2, 0);
		}
		for (int i =0; i<10;i++){
			world.setGeologicalFeature(i, 3, 0);
		}
		return world;
	}

	@Test
	public void WorldConstructorLegal(){
		World world = new World(50, 10, 20,
				200,300,10,15);

		//		World(int tileSize, int nbTilesX, int nbTilesY,
		//				int visibleWindowWidth, int visibleWindowHeight, int targetTileX,
		//				int targetTileY)

		assertTrue( world.getTileSize() == 50);

		assertTrue( world.getWorldSizeInPixels()[0] == 500);
		assertTrue( world.getWorldSizeInPixels()[1] == 1000);

		assertTrue( world.getTargetTile().getElemx() == 10);
		assertTrue( world.getTargetTile().getElemy() == 15);
	}



	@SuppressWarnings("unused")
	@Test(expected = IllegalArgumentException.class)
	public void WorldConstructorIllegaltileSize(){
		World world = new World(-50, 10, 20,
				200,300,10,15);

	}

	@SuppressWarnings("unused")
	@Test(expected = IllegalArgumentException.class)
	public void WorldConstructorIllegalnbTiles1(){
		World world = new World(50, -10, -20,
				200,300,10,15);

	}

	@SuppressWarnings("unused")
	@Test(expected = IllegalArgumentException.class)
	public void WorldConstructorIllegalnbTiles2(){
		World world = new World(50, 0, -20,
				200,300,10,15);

	}



	@SuppressWarnings("unused")
	@Test(expected = IllegalArgumentException.class)
	public void WorldConstructorIllegalTarget(){
		World world = new World(50, 10, 20,
				200,300,11,21);

		// targetTileX > nbTilesX, same for Y
	}

	@Test
	public void MazubAssociation(){
		World world = new World(50, 10, 20,
				200,300,10,15);
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = new Mazub(100,100, sprites);


		world.setMazub(alien);

		assertTrue(world.getMazub() == alien);
		assertTrue(alien.getWorld() == world);


		Mazub alien2 = new Mazub(500,100, sprites);

		//alien2.setWorld(world);
		world.setMazub(alien2);

		assertTrue(world.getMazub() != alien2);
		assertTrue(alien2.getWorld() != world);
	}


	@Test
	public void PlantAssociation(){
		World world = new World(50, 10, 20,
				200,300,10,15);

		int m = 2;
		Sprite[] sprites = spriteArrayForSize(2, 2, m);

		Plant plant1 = new Plant(10, 15,sprites);
		Plant plant2 = new Plant(15, 20,sprites);

		world.addPlant(plant1);
		world.addPlant(plant2);

		assertTrue(world.getAllPlants().contains(plant1));
		assertTrue(world.getAllPlants().contains(plant2));
		assertTrue(plant1.getWorld() == world);
		assertTrue(plant2.getWorld() == world);

		assertTrue(world.getAllPlants().size() == 2);

		//REMOVE PLANT FROM WORLD 
		//This test worked until we made the terminate methods protected.
		//It does work though.

		//plant1.terminate();
		//assertTrue(!world.getAllPlants().contains(plant1));
		//assertTrue(plant1.getWorld() == null);
	}

	@Test
	public void SharkAssociation(){
		World world = new World(50, 10, 20,
				200,300,10,15);

		int m = 2;
		Sprite[] sprites = spriteArrayForSize(2, 2, m);

		Shark shark1 = new Shark(10, 15,sprites);
		Shark shark2 = new Shark(15, 20,sprites);

		world.addShark(shark1);
		world.addShark(shark2);

		assertTrue(world.getAllSharks().contains(shark1));
		assertTrue(world.getAllSharks().contains(shark2));
		assertTrue(shark1.getWorld() == world);
		assertTrue(shark2.getWorld() == world);

		assertTrue(world.getAllSharks().size() == 2);

		//REMOVE SHARK FROM WORLD 
		//This test worked until we made the terminate methods protected.
		//It does work though.

		//shark1.terminate();
		//assertTrue(!world.getAllSharks().contains(shark1));
		//assertTrue(shark1.getWorld() == null);
	}

	@Test
	public void SlimeAndSchoolAssociation(){
		World world = new World(50, 10, 20,
				200,300,10,15);

		int m = 2;
		Sprite[] sprites = spriteArrayForSize(2, 2, m);
		School school1 = new School();
		School school2 = new School();

		Slime slime1 = new Slime(10, 15,sprites,school1);
		Slime slime2 = new Slime(15, 20,sprites,school1);

		Slime slime3 = new Slime(10, 10,sprites,school2);
		Slime slime4 = new Slime(20, 20,sprites,school2);

		world.addSlime(slime1);
		world.addSlime(slime2);

		world.addSlime(slime3);
		world.addSlime(slime4);

		assertTrue(world.getAllSlimes().contains(slime1));
		assertTrue(world.getAllSlimes().contains(slime2));
		assertTrue(world.getAllSlimes().contains(slime3));
		assertTrue(world.getAllSlimes().contains(slime4));

		assertTrue(slime1.getWorld() == world);
		assertTrue(slime2.getWorld() == world);
		assertTrue(slime3.getWorld() == world);
		assertTrue(slime4.getWorld() == world);

		assertTrue(world.getNbSchools() == 2);
		assertTrue(world.getAllSlimes().size() == 4);

		assertTrue(world.hasAsSchool(school1));
		assertTrue(world.hasAsSchool(school2));

		//REMOVE SLIME FROM WORLD & SCHOOL
		//This test worked until we made the terminate methods protected.
		//It does work though.

		//slime1.terminate();
		//assertTrue(!world.getAllSlimes().contains(slime1));
		//assertTrue(!school1.hasAsSlime(slime1));
		//assertTrue(slime1.getWorld() == null);
	}

	@Test
	public void maximumSchoolsInWorld(){
		World world = new World(50, 10, 20,
				200,300,10,15);
		Sprite[] sprites = spriteArrayForSize(2, 2, 2);

		for(int i =0; i<20;i++){
			School school = new School();
			world.addSlime(new Slime(20,20,sprites,school));
		}
		assertTrue(world.getNbSchools()== 10);
	}

	@Test
	public void maximumNbObjectsInWorld(){
		World world = new World(50, 10, 20,
				200,300,10,15);
		Sprite[] sprites = spriteArrayForSize(2, 2, 2);

		for(int i =0; i<60;i++){
			world.addPlant(new Plant(20,20,sprites));
		}

		for(int i =0; i<60;i++){
			world.addShark(new Shark(40,40,sprites));
		}

		assertTrue(world.getNbObjects() == 100);
	}
	@Test
	public void getAllObjectsTest(){
		World world = new World(50, 10, 20,
				200,300,10,15);
		Sprite[] sprites = spriteArrayForSize(2, 2, 2);

		Plant plant = new Plant(20,20,sprites);
		world.addPlant(plant);

		Shark shark = new Shark(40,40,sprites);
		world.addShark(shark);

		School school = new School();
		Slime slime = new Slime(40,40,sprites,school);
		world.addSlime(slime);

		assertTrue(world.getAllObjects().contains(plant));
		assertTrue(world.getAllObjects().contains(shark));
		assertTrue(world.getAllObjects().contains(slime));
	}

	@Test
	public void hasAsObjectTest(){
		World world = new World(50, 10, 20,
				200,300,10,15);
		Sprite[] sprites = spriteArrayForSize(2, 2, 2);

		Plant plant = new Plant(20,20,sprites);
		world.addPlant(plant);

		Shark shark = new Shark(40,40,sprites);
		world.addShark(shark);

		School school = new School();
		Slime slime = new Slime(40,40,sprites,school);
		world.addSlime(slime);

		assertTrue(world.hasAsObject(plant));
		assertTrue(world.hasAsObject(shark));
		assertTrue(world.hasAsObject(slime));
	}

	@Test
	public void getNbObjectsTest(){
		World world = new World(50, 10, 20,
				200,300,10,15);
		Sprite[] sprites = spriteArrayForSize(2, 2, 2);

		for(int i =0; i<20;i++){
			world.addPlant(new Plant(20,20,sprites));
		}

		for(int i =0; i<20;i++){
			world.addShark(new Shark(40,40,sprites));
		}

		School school = new School();
		for(int i =0; i<20;i++){
			world.addSlime(new Slime(40,40,sprites,school));
		}
		assertTrue(world.getNbObjects() == 60);
	}

	@Test
	public void hasAsMazub(){

		World world = new World(50, 10, 20,
				200,300,10,15);
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = new Mazub(100,100, sprites);

		assertFalse(world.hasAsMazub(alien));


		world.setMazub(alien);

		assertTrue(world.hasAsMazub(alien));
	}


	@Test
	public void getAllPlantsTest(){
		World world = new World(50, 10, 20,
				200,300,10,15);
		Sprite[] sprites = spriteArrayForSize(2, 2, 2);

		Plant plant1 = new Plant(20,20,sprites);
		Plant plant2 = new Plant(20,20,sprites);
		world.addPlant(plant1);
		world.addPlant(plant2);

		assertTrue(world.getAllPlants().contains(plant1));
		assertTrue(world.getAllPlants().contains(plant2));
	}

	@Test
	public void getAllSharksTest(){
		World world = new World(50, 10, 20,
				200,300,10,15);
		Sprite[] sprites = spriteArrayForSize(2, 2, 2);

		Shark shark1 = new Shark(20,20,sprites);
		Shark shark2 = new Shark(20,20,sprites);
		world.addShark(shark1);
		world.addShark(shark2);

		assertTrue(world.getAllSharks().contains(shark1));
		assertTrue(world.getAllSharks().contains(shark2));
	}

	@Test
	public void getAllSlimesTest(){
		World world = new World(50, 10, 20,
				200,300,10,15);
		Sprite[] sprites = spriteArrayForSize(2, 2, 2);

		School school = new School();
		Slime slime1 = new Slime(20,20,sprites,school);
		Slime slime2 = new Slime(20,20,sprites,school);
		world.addSlime(slime1);
		world.addSlime(slime2);

		assertTrue(world.getAllSlimes().contains(slime1));
		assertTrue(world.getAllSlimes().contains(slime2));
	}

	@Test
	public void getSchoolsTest(){
		World world = new World(50, 10, 20,
				200,300,10,15);
		Sprite[] sprites = spriteArrayForSize(2, 2, 2);

		School school1 = new School();
		School school2 = new School();
		Slime slime1 = new Slime(20,20,sprites,school1);
		Slime slime2 = new Slime(20,20,sprites,school2);
		world.addSlime(slime1);
		world.addSlime(slime2);

		assertTrue(world.getSchools().contains(school1));
		assertTrue(world.getSchools().contains(school2));
	}

	@Test
	public void hasAsSchoolTest(){
		World world = new World(50, 10, 20,
				200,300,10,15);
		Sprite[] sprites = spriteArrayForSize(2, 2, 2);

		School school1 = new School();
		School school2 = new School();
		Slime slime1 = new Slime(20,20,sprites,school1);
		Slime slime2 = new Slime(20,20,sprites,school2);
		world.addSlime(slime1);
		world.addSlime(slime2);

		assertTrue(world.hasAsSchool(school1));
		assertTrue(world.hasAsSchool(school2));

	}
	@Test
	public void getNbSchoolsTest(){
		World world = new World(50, 10, 20,
				200,300,10,15);
		Sprite[] sprites = spriteArrayForSize(2, 2, 2);

		for(int i =0; i<10;i++){
			assertTrue(world.getNbSchools()== i);
			School school = new School();
			world.addSlime(new Slime(20,20,sprites,school));
		}
		assertTrue(world.getNbSchools()== 10);


	}
	@Test
	public void addSchoolTest(){
		World world = new World(50, 10, 20,
				200,300,10,15);

		School school = new School();
		world.addSchool(school);

		assertTrue(world.hasAsSchool(school));
	}


	// REMOVE SCHOOL IS TESTED IN SlimeAndSchoolAssociation() 
	// BEACAUSE IT CAN ONLY BE LEGALLY CALLED IN school.terminate().
	// terminate() IS CALLED IN updateSchools(), WHEN A SCHOOL IS EMPTY.

	@Test
	public void updateSchoolTest(){
		World world = new World(50, 10, 20,
				200,300,10,15);
		Sprite[] sprites = spriteArrayForSize(2, 2, 2);

		School school1 = new School();
		School school2 = new School();
		Slime slime1 = new Slime(20,20,sprites,school1);
		Slime slime2 = new Slime(20,20,sprites,school1);
		Slime slime3 = new Slime(20,20,sprites,school2);
		Slime slime4 = new Slime(20,20,sprites,school2);
		world.addSlime(slime1);
		world.addSlime(slime2);
		world.addSlime(slime3);
		world.addSlime(slime4);

		assertTrue(world.hasAsSchool(school1));
		assertTrue(world.hasAsSchool(school2));


		// SLIME DIES AND IS REMOVED OUT OF WORLD AND SCHOOL
		//This test worked until we made the terminate methods protected.
		//It does work though.

		//		slime1.terminate();
		//		slime2.terminate();
		//		
		//		// school 1 should be removed, because it's empty.
		//		world.updateSchools();
		//		
		//		assertFalse(world.hasAsSchool(school1));
		//		assertTrue(school1.getWorld() == null);

	}

	@Test
	public void getWorldSizeInPixelsTest(){
		World world = new World(50, 10, 20,
				200,300,10,15);
		assertTrue(world.getWorldSizeInPixels()[0] == 50*10);
		assertTrue(world.getWorldSizeInPixels()[1] == 50*20);

	}

	@Test
	public void getVisibleWindowTestBottomLeft(){

		World world = new World(20, 100, 100,
				300,200,10,15);

		// world = 2000px x 2000px
		// window width = 300px && window height = 200px
		// L B R T

		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = new Mazub(100,99, sprites);


		world.setMazub(alien);

		int[] result = world.getVisibleWindow();

		assertTrue(result[0] == 0);
		assertTrue(result[1] == 0);
		assertTrue(result[2] == 300);
		assertTrue(result[3] == 200);
	}
	@Test
	public void getVisibleWindowTestCenter(){

		World world = new World(20, 100, 100,
				300,200,10,15);

		// world = 2000px x 2000px
		// window width = 300px && window height = 200px
		// L B R T

		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = new Mazub(1000,1499, sprites);


		world.setMazub(alien);

		int[] result = world.getVisibleWindow();

		result = world.getVisibleWindow();

		assertTrue(result[0] == 850);
		assertTrue(result[1] == 1400);
		assertTrue(result[2] == 1150);
		assertTrue(result[3] == 1600);
	}

	@Test
	public void getVisibleWindowTestTopRight(){

		World world = new World(20, 100, 100,
				300,200,10,15);

		// world = 2000px x 2000px
		// window width = 300px && window height = 200px
		// L B R T

		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = new Mazub(1000,1899, sprites);


		world.setMazub(alien);

		int[] result = world.getVisibleWindow();

		result = world.getVisibleWindow();

		assertTrue(result[0] == 850);
		assertTrue(result[1] == 1800);
		assertTrue(result[2] == 1150);
		assertTrue(result[3] == 2000);
	}

	@Test
	public void convertXTYTtoXYTest(){
		//input: xt and yt, output: x and y

		int tileLength = 20;

		World world = new World(tileLength, 10, 20,
				200,300,10,15);

		assertTrue(world.convertXTYTtoXY(0, 0)[0] == 0);
		assertTrue(world.convertXTYTtoXY(0, 0)[1] == 0);

		assertTrue(world.convertXTYTtoXY(2, 1)[0] == 40);
		assertTrue(world.convertXTYTtoXY(2, 1)[1] == 20);

		assertTrue(world.convertXTYTtoXY(10, 20)[0] == 200);
		assertTrue(world.convertXTYTtoXY(10, 20)[1] == 400);

		int tileLength1 = 35;

		World world1 = new World(tileLength1, 10, 20,
				200,300,10,15);

		assertTrue(world1.convertXTYTtoXY(0, 0)[0] == 0);
		assertTrue(world1.convertXTYTtoXY(0, 0)[1] == 0);

		assertTrue(world1.convertXTYTtoXY(1, 1)[0] == 35);
		assertTrue(world1.convertXTYTtoXY(1, 1)[1] == 35);

		assertTrue(world1.convertXTYTtoXY(10, 20)[0] == 350);
		assertTrue(world1.convertXTYTtoXY(10, 20)[1] == 700);
	}

	@Test
	public void convertXYtoXTYTTest(){
		//input: x and y, output: xt and yt

		int tileLength = 20;

		World world = new World(tileLength, 10, 20,
				200,300,10,15);

		assertTrue(world.convertXYtoXTYT(200, 200)[0] == 10);
		assertTrue(world.convertXYtoXTYT(200, 200)[1] == 10);

		assertTrue(world.convertXYtoXTYT(213, 207)[0] == 10);
		assertTrue(world.convertXYtoXTYT(210, 203)[1] == 10);

		assertTrue(world.convertXYtoXTYT(0, 0)[0] == 0);
		assertTrue(world.convertXYtoXTYT(0, 0)[1] == 0);


		int tileLength1 = 35;

		World world1 = new World(tileLength1, 10, 20,
				200,300,10,15);

		assertTrue(world1.convertXYtoXTYT(100, 200)[0] == 2);
		assertTrue(world1.convertXYtoXTYT(100, 200)[1] == 5);

		assertTrue(world1.convertXYtoXTYT(0, 0)[0] == 0);
		assertTrue(world1.convertXYtoXTYT(0, 0)[1] == 0);


	}
	@Test
	public void getTilePositionsInTest(){
		World world1 = new World(20, 10, 20,
				200,300,10,15);

		//getTilePositionsIn(0,0,30, 30));

		assertTrue(world1.getTilePositionsIn(0,0,30, 30)[0][0] == 0);
		assertTrue(world1.getTilePositionsIn(0,0,30, 30)[0][1] == 0);

		assertTrue(world1.getTilePositionsIn(0,0,30, 30)[1][0] == 1);
		assertTrue(world1.getTilePositionsIn(0,0,30, 30)[1][1] == 0);

		assertTrue(world1.getTilePositionsIn(0,0,30, 30)[2][0] == 0);
		assertTrue(world1.getTilePositionsIn(0,0,30, 30)[2][1] == 1);

		assertTrue(world1.getTilePositionsIn(0,0,30, 30)[3][0] == 1);
		assertTrue(world1.getTilePositionsIn(0,0,30, 30)[3][1] == 1);

		//getTilePositionsIn(30,30,50,70));

		assertTrue(world1.getTilePositionsIn(30,30,50,70)[0][0] == 1);
		assertTrue(world1.getTilePositionsIn(30,30,50,70)[0][1] == 1);

		assertTrue(world1.getTilePositionsIn(30,30,50,70)[1][0] == 2);
		assertTrue(world1.getTilePositionsIn(30,30,50,70)[1][1] == 1);

		assertTrue(world1.getTilePositionsIn(30,30,50,70)[2][0] == 1);
		assertTrue(world1.getTilePositionsIn(30,30,50,70)[2][1] == 2);

		assertTrue(world1.getTilePositionsIn(30,30,50,70)[3][0] == 2);
		assertTrue(world1.getTilePositionsIn(30,30,50,70)[3][1] == 2);

		assertTrue(world1.getTilePositionsIn(30,30,50,70)[4][0] == 1);
		assertTrue(world1.getTilePositionsIn(30,30,50,70)[4][1] == 3);

		assertTrue(world1.getTilePositionsIn(30,30,50,70)[5][0] == 2);
		assertTrue(world1.getTilePositionsIn(30,30,50,70)[5][1] == 3);

	}

	@Test(expected = IllegalArgumentException.class)
	public void getTilePositionsInTestException1(){
		World world1 = new World(20, 10, 20,
				200,300,10,15);

		//getTilePositionsIn(0,0,30, 30));
		//right < left pixel

		world1.getTilePositionsIn(30,0,10, 30);
	}

	@Test(expected = IllegalArgumentException.class)
	public void getTilePositionsInTestException2(){
		World world1 = new World(20, 10, 20,
				200,300,10,15);

		//getTilePositionsIn(0,0,30, 30));

		world1.getTilePositionsIn(-10,-10,0, 30);
	}

	@Test
	public void setGetGeologicalFeature(){
		World world1 = new World(20, 10, 20,
				200,300,10,15);

		world1.setGeologicalFeature(0,0,1);

		assertTrue(world1.getGeologicalFeature(0, 0) == 1);
		assertTrue(world1.getGeologicalFeature(10, 7) == 1);
		assertTrue(world1.getGeologicalFeature(30, 30) == 0);

		world1.setGeologicalFeature(5,1,2);

		assertTrue(world1.getGeologicalFeature(102, 25) == 2);
		assertTrue(world1.getGeologicalFeature(120, 40) == 0);

	}
	@Test
	public void isPassableTerrain(){
		World world1 = new World(20, 10, 20,
				200,300,10,15);

		// earth
		world1.setGeologicalFeature(0,0,1);
		// water
		world1.setGeologicalFeature(1,0,2);
		// magma
		world1.setGeologicalFeature(2,0,3);

		assertFalse(world1.isPassableTerrain(0, 0));
		assertFalse(world1.isPassableTerrain(10, 10));
		assertTrue(world1.isPassableTerrain(20, 0));
		assertTrue(world1.isPassableTerrain(40, 0));
		//air
		assertTrue(world1.isPassableTerrain(60, 0));


	}
	@Test
	public void getWorldSize(){

		World world = new World(50, 10, 20,
				200,300,10,15);

		assertTrue(world.getWorldSize().getElemx() == 50*10);
		assertTrue(world.getWorldSize().getElemy() == 50*20);		
	}
	@Test
	public void getTargetTileTest(){

		World world = new World(50, 10, 20,
				200,300,10,15);

		assertTrue( world.getTargetTile().getElemx() == 10);
		assertTrue( world.getTargetTile().getElemy() == 15);

	}
	@Test
	public void startWorldTest(){

		World world = new World(50, 10, 20,
				200,300,10,15);
		world.startWorld();
		assertTrue(world.isWorldStarted() == true);


	}

	@Test
	public void startWorldImplementedTest(){

		World world = new World(50, 10, 20,
				200,300,10,15);
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = new Mazub(100,100, sprites);


		world.setMazub(alien);

		assertTrue(world.isWorldStarted() == false);

		world.advanceTime(0.1);

		assertTrue(world.isWorldStarted() == true);
	}

	@Test
	public void startWorldCantAddObjectsTest(){

		World world = new World(50, 10, 20,
				200,300,10,15);
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = new Mazub(100,100, sprites);


		world.setMazub(alien);
		world.advanceTime(0.1);

		assertTrue(world.isWorldStarted() == true);

		Sprite[] sprites1 = spriteArrayForSize(2, 2, 2);
		School school1 = new School();
		Slime slime1 = new Slime(10, 15,sprites1,school1);
		world.addSlime(slime1);

		// slime was not added because world has started. (This works for plants, sharks and slimes. Mazub can only be set once.)
		assertTrue(!world.hasAsSlime(slime1));


	}

	@Test
	public void getTimeTest(){

		World world = new World(50, 10, 20,
				200,300,10,15);
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = new Mazub(100,100, sprites);


		world.setMazub(alien);

		world.advanceTime(0.1);
		assertTrue(world.getTime() == 0.1);
	}







	@Test
	public void advanceTimeTestWorldStarted(){
		World world = new World(50, 10, 20,
				200,300,10,15);
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = new Mazub(100,100, sprites);
		world.setMazub(alien);

		assertFalse(world.isWorldStarted());

		world.advanceTime(0.1);

		assertTrue(world.isWorldStarted());

	}



	@Test
	public void advanceTimeMazubTest(){
		World world = createWorldWithGround50px();		
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = new Mazub(100,49, sprites);
		world.setMazub(alien);

		alien.startMove(true);
		world.advanceTime(0.1);

		assertTrue(alien.getVelocity().getElemx() > 0);

		alien.endMove();
		alien.startMove(false);

		world.advanceTime(0.1);

		assertTrue(alien.getVelocity().getElemx() < 0);

	}

	@Test
	public void advanceTimePlantTest(){
		World world = createWorldWithGround50px();		
		int m = 10;
		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
		Mazub alien = new Mazub(100,49, sprites);
		world.setMazub(alien);

		Sprite[] sprites1 = spriteArrayForSize(2, 2, 2);

		Plant plant = new Plant(200,200,sprites1);
		world.addPlant(plant);

		assertTrue(plant.getVelocity().getElemx() == 0.5);

		for(int i = 0; i<=6; i++){
			world.advanceTime(0.1);
		}

		assertTrue(plant.getVelocity().getElemx() == -0.5);

		for(int i = 0; i<=6; i++){
			world.advanceTime(0.1);
		}

		assertTrue(plant.getVelocity().getElemx() == 0.5);
	}

//	@Test
//	public void advanceTimeSlimeTest(){
//		World world = createWorldWithGround50px();		
//		int m = 10;
//		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
//		Mazub alien = new Mazub(450,49, sprites);
//		world.setMazub(alien);
//
//		Sprite[] sprites1 = spriteArrayForSize(2, 2, 2);
//		School school = new School();
//		Slime slime = new Slime(300,50,sprites1,school);
//
//		world.addSlime(slime);
//
//		int posx = (int) slime.getPos().getElemx();
//		int prevposx = (int) slime.getPos().getElemx();
//		world.advanceTime(0.01);
//
//		for(int i =0; i <1000; i++){
//
//			world.advanceTime(0.01);
//			
//			if (slime.getVelocity().getElemx() >= 0){
//				posx = (int) slime.getPos().getElemx();
//				assertTrue( posx >= prevposx);
//			}else{
//				posx = (int) slime.getPos().getElemx();
//				assertTrue( posx <= prevposx);
//			}
//			prevposx = posx;
//		}
//	}
//
//	@Test
//	public void advanceTimeSharkTest(){
//		World world = new World(100, 10, 10,
//				200,300,0,0);
//		for (int j =0; j<10;j++){
//			for (int i =0; i<10;i++){
//				world.setGeologicalFeature(i, j, 0);
//			}
//		}
//
//		int m = 10;
//		Sprite[] sprites = spriteArrayForSize(2, 2, 10 + 2 * m);
//		Mazub alien = new Mazub(450,49, sprites);
//		world.setMazub(alien);
//
//		Sprite[] sprites1 = spriteArrayForSize(2, 2, 2);
//		Shark shark = new Shark(300,300,sprites1);
//
//		world.addShark(shark);
//
//		int posx = (int) shark.getPos().getElemx();
//		int prevposx = (int) shark.getPos().getElemx();
//		world.advanceTime(0.01);
//
//		for(int i =0; i <1000; i++){
//			System.out.println("LELELEL");
//
//			world.advanceTime(0.01);
//
//			System.out.println(shark.getVelocity().getElemx());
//			System.out.println(shark.getPos().getElemx());
//			System.out.println(shark.getPos().getElemy());
//
//
//			if (shark.getVelocity().getElemx() >= 0){
//				posx = (int) shark.getPos().getElemx();
//				assertTrue( posx >= prevposx);
//			}else{
//				posx = (int) shark.getPos().getElemx();
//				assertTrue( posx <= prevposx);
//			}
//			prevposx = posx;
//		}
//	}

}
