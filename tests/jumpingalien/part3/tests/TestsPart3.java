package jumpingalien.part3.tests;

import static org.junit.Assert.*;
import static jumpingalien.tests.util.TestUtils.*;

import java.util.Optional;

import jumpingalien.model.Buzam;
import jumpingalien.model.Mazub;
import jumpingalien.model.Plant;
import jumpingalien.model.World;
import jumpingalien.part3.facade.Facade;
import jumpingalien.part3.facade.IFacadePart3;
import jumpingalien.part3.programs.IProgramFactory;
import jumpingalien.part3.programs.ParseOutcome;
import jumpingalien.part3.programs.IProgramFactory.Direction;
import jumpingalien.part3.programs.ProgramParser;
import jumpingalien.util.Sprite;

import org.junit.Test;

import program.Program;
import program.ProgramFactory;
import program.expression.Expression;
import program.statement.PrintStatement;
import program.statement.SequenceStatement;
import program.statement.Statement;

public class TestsPart3 {

	private World createWorldWithGround50px(){
		World world = new World(50, 10, 10,
				200,300,10,0);
		for (int i =0; i<10;i++){
			world.setGeologicalFeature(i, 0, 1);
		}
		return world;
	}

	@Test
	public void Assignmenttest() {

		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);

		Optional<Program> parse_outcome = parser.parseString("double y; direction dir; bool x := true; y := 1.0; dir := left;");
		Program program = parse_outcome.get();
		program.advanceTime(0.01);

		assertTrue((double)program.getVariables().get("y") == 1.0);
		assertTrue((boolean)program.getVariables().get("x") == true);
		assertTrue(program.getVariables().get("dir") == Direction.LEFT);	
	}

	@Test
	public void BreakInForEachTest() {
		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);

		Optional<Program> parse_outcome = parser.parseString("object o; double i; i := 0; foreach (any, o) do if(gethp o > 0.0) then print i; else i := i+1; fi done");
		Program program = parse_outcome.get();

		Optional<Program> parse_outcome2 = parser.parseString("object o; double i; i := 0; foreach (any, o) do if(gethp o > 0.0) then break; else i := i+1; fi done");
		Program program2 = parse_outcome2.get();

		World world = new World(50, 20, 20, 200,200,10,15);
		Sprite[] sprites = spriteArrayForSize(5, 5, 2);

		Plant plantProgram = new Plant(10, 10, sprites, program);
		program.setGameObject(plantProgram);
		Plant plantProgram2 = new Plant(20, 20, sprites, program);
		program2.setGameObject(plantProgram2);
		Plant plant1 = new Plant(30, 30, sprites);
		Plant plant2 = new Plant(40, 30, sprites);
		Plant plant3 = new Plant(50, 30, sprites);

		world.addPlant(plantProgram);
		world.addPlant(plantProgram2);
		world.addPlant(plant1);
		world.addPlant(plant2);
		world.addPlant(plant3);

		//program2 finished alot faster because of the break called
		program.advanceTime(0.01);
		program2.advanceTime(0.01);

		assertTrue(program.getAmountMainExecuted() < program2.getAmountMainExecuted());        
	}

	@Test
	public void BreakInWhileTest() {
		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);

		//without break
		Optional<Program> parse_outcome = parser.parseString("double i; i := 0; print i; while(i < 10.0) do i := i + 1; print i; done");
		Program program = parse_outcome.get();
		program.advanceTime(0.1);

		//with break
		Optional<Program> parse_outcome2 = parser.parseString("double i; i := 0; print i; while(i < 10.0) do i := i + 1; print i; if(i >= 3.0) then break; fi done");
		Program program2 = parse_outcome2.get();
		program2.advanceTime(0.1);

		//program2 finished alot faster because of the break called
		assertTrue(program.getAmountMainExecuted() < program2.getAmountMainExecuted());

	}

	@Test
	public void BreakTestNotWellFormed1() {
		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);

		Optional<Program> parse_outcome = parser.parseString("double y; y:= 1.0; break;");
		Program program = parse_outcome.get();
		assertFalse(program.isWellFormed());

	}

	@Test
	public void BreakTestNotWellFormed2() {
		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);

		Optional<Program> parse_outcome = parser.parseString("double y; y:= 1.0; if(y == 1.0) then break; fi");
		Program program = parse_outcome.get();
		assertFalse(program.isWellFormed());
	}


	// Movement

	@Test
	public void ForEachTest() {

		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);

		Optional<Program> parse_outcome = parser.parseString("object o; double i; i := 0; foreach (any, o) do i := i+1; print i; done");
		Program program = parse_outcome.get();

		World world = new World(50, 20, 20, 200,200,10,15);
		Sprite[] sprites = spriteArrayForSize(5, 5, 2);

		Plant plantProgram = new Plant(10, 10, sprites, program);
		program.setGameObject(plantProgram);
		Plant plant1 = new Plant(30, 30, sprites);
		Plant plant2 = new Plant(40, 30, sprites);
		Plant plant3 = new Plant(50, 30, sprites);

		world.addPlant(plantProgram);
		world.addPlant(plant1);
		world.addPlant(plant2);
		world.addPlant(plant3);

		program.advanceTime(0.002);
		assertTrue((double)program.getVariables().get("i") == 0.0);

		program.advanceTime(0.002);
		assertTrue((double)program.getVariables().get("i") == 1.0);

		program.advanceTime(0.002);
		assertTrue((double)program.getVariables().get("i") == 2.0);

		program.advanceTime(0.002);
		assertTrue((double)program.getVariables().get("i") == 3.0);

		program.advanceTime(0.002);
		assertTrue((double)program.getVariables().get("i") == 4.0);

		program.advanceTime(0.002);
		assertTrue((double)program.getVariables().get("i") == 0.0);

	}

	@Test
	public void IfTest() {
		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);

		Optional<Program> parse_outcome = parser.parseString("double y; double x; double z; y:= 1.0; z:= 1.0; if( y == z ) then x:= 2.0; else x:= 3.0; fi");
		Program program = parse_outcome.get();
		program.advanceTime(0.005);
		assertTrue((double)program.getVariables().get("x") == 2.0);

		Optional<Program> parse_outcome2 = parser.parseString("double y; double x; double z; y:= 1.0; z:= 2.0; if( y == z ) then x:= 2.0; else x:= 3.0; fi");
		Program program2 = parse_outcome2.get();
		program2.advanceTime(0.005);
		assertTrue((double)program2.getVariables().get("x") == 3.0);
	}

	@Test
	public void RunTest() {
		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);

		Optional<Program> parse_outcome = parser.parseString("start_run left; wait(0.1); stop_run left; start_run right; wait(0.1); stop_run right; wait(0.1);");
		Program program = parse_outcome.get();

		World world = createWorldWithGround50px();
		Sprite[] sprites = spriteArrayForSize(5, 5, 2);

		Plant plantProgram = new Plant(60, 60, sprites, program);
		program.setGameObject(plantProgram);
		world.addPlant(plantProgram);

		program.advanceTime(0.01);
		assertTrue(plantProgram.getVelocity().getElemx() == -0.5);
		program.advanceTime(0.11);
		assertTrue(plantProgram.getVelocity().getElemx() == 0.5);
		program.advanceTime(0.11);
		assertTrue(plantProgram.getVelocity().getElemx() == 0.0);
	}

	@Test
	public void JumpTest() {

		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);

		Optional<Program> parse_outcome = parser.parseString("start_jump; wait(0.05); stop_jump; wait(0.05);");
		Program program = parse_outcome.get();

		World world = createWorldWithGround50px();
		Sprite[] sprites = spriteArrayForSize(5, 5, 10);

		Mazub mazub = new Mazub(200,51,sprites);
		world.setMazub(mazub);
		Buzam buzam = new Buzam(50,51,sprites,program);
		program.setGameObject(buzam);
		world.setBuzam(buzam);

		world.advanceTime(0.1);
		world.advanceTime(0.01);
		assertTrue(buzam.getVelocity().getElemy() > 7.0 && buzam.getVelocity().getElemy() < 8.0);
	}

	@Test
	public void DuckTest() {
		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);

		Optional<Program> parse_outcome = parser.parseString("start_duck; wait(0.05); stop_duck; wait(0.05);");
		Program program = parse_outcome.get();

		World world = createWorldWithGround50px();
		Sprite[] sprites = spriteArrayForSize(5, 5, 10);

		Mazub mazub = new Mazub(200,51,sprites);
		world.setMazub(mazub);
		Buzam buzam = new Buzam(50,51,sprites,program);
		program.setGameObject(buzam);
		world.setBuzam(buzam);

		world.advanceTime(0.001);
		assertTrue(buzam.isDucked());

		world.advanceTime(0.051);
		world.advanceTime(0.001);
		assertFalse(buzam.isDucked());
		
		world.advanceTime(0.051);
		world.advanceTime(0.001);
		assertTrue(buzam.isDucked());

	}

	@Test
	public void MovementNotWellFormedTest() {
		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);
		
		Optional<Program> parse_outcome = parser.parseString("object o; double i; i := 0; foreach (any, o) do i := i+1; if (i == 5.0) then start_run left; fi done");
		Program program = parse_outcome.get();
		assertFalse(program.isWellFormed());
	}
	
	
	// OPERATIONS

	@Test
	public void AddTest() {
		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);

		Optional<Program> parse_outcome = parser.parseString("double y; y := 1.0 + 2.0;");
		Program program = parse_outcome.get();

		program.advanceTime(0.1);
	}

	@Test
	public void SubtractTest() {
		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);

		Optional<Program> parse_outcome = parser.parseString("double y; y := 1.0 - 2.0;");
		Program program = parse_outcome.get();

		program.advanceTime(0.1);
	}

	@Test
	public void MultiplyTest() {
		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);

		Optional<Program> parse_outcome = parser.parseString("double y; y := 4.0 * 2.0;");
		Program program = parse_outcome.get();

		program.advanceTime(0.1);
		System.out.println(program.getVariables());
	}

	@Test
	public void DivideTest() {
		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);

		Optional<Program> parse_outcome = parser.parseString("double y; y := 1.0 / 0.0;");
		Program program = parse_outcome.get();

		program.advanceTime(0.1);
	}

	@Test
	public void EqualsTest() {
		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);

		Optional<Program> parse_outcome = parser.parseString("double y; double x; double rslt;"
				+ "y := 3.0; x := 3.0; rslt := x == y;");
		Program program = parse_outcome.get();

		program.advanceTime(0.1);
		System.out.println(program.getVariables());
	}

	
	// STATEMENTS
	@Test
	public void PrintTest(){
		IFacadePart3 facade = new Facade();
		ParseOutcome<?> outcome = facade.parse("print 1;");
		assertTrue(outcome.isSuccess());


		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);
		Optional<Program> parse_outcome = parser.parseString("print 1;");
		Program program = parse_outcome.get();

		World world = new World(50, 20, 20, 200,200,10,15);
		Sprite[] sprites = spriteArrayForSize(5, 5, 2);
		Plant plantProgram = facade.createPlantWithProgram(10, 10, sprites, program);
		world.addPlant(plantProgram);

		world.advanceTime(0.001);

		assertTrue(program.getMainStatement() instanceof PrintStatement);
	}

	@Test
	public void SequenceTest(){
		IFacadePart3 facade = new Facade();
		ParseOutcome<?> outcome = facade.parse("double o; o := 1.0; o:= o*2; ");
		assertTrue(outcome.isSuccess());


		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);
		Optional<Program> parse_outcome = parser.parseString("double o; o := 1.0; o := o*2;");
		Program program = parse_outcome.get();

		World world = new World(50, 20, 20, 200,200,10,15);
		Sprite[] sprites = spriteArrayForSize(5, 5, 2);
		Plant plantProgram = facade.createPlantWithProgram(10, 10, sprites, program);
		world.addPlant(plantProgram);

		world.advanceTime(0.002);


		assertTrue(program.getMainStatement() instanceof SequenceStatement);
	}

	@Test
	public void ProgramTimerTest(){
		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);

		Optional<Program> parse_outcome = parser.parseString("double o; o := 1.0; o := o*2;");
		Program program = parse_outcome.get();

		program.advanceTime(0.003);
		assertTrue(program.getAmountMainExecuted() == 1);

	}


	@Test
	public void WaitStatementTest(){
		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);

		Optional<Program> parse_outcome = parser.parseString("wait(0.5); print 5;");
		Program program = parse_outcome.get();
		Sprite[] sprites = spriteArrayForSize(5, 5, 30);

		Buzam buzam = new Buzam(20, 0, sprites, program);
		program.setGameObject(buzam);

		program.advanceTime(0.490);
		assertTrue(program.getAmountMainExecuted() == 0); //wait is still going on
		program.advanceTime(0.015);
		assertTrue(program.getAmountMainExecuted() == 1); //wait has been executed

	}

	@Test
	public void WhileStatementTest1(){
		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);

		Optional<Program> parse_outcome = parser.parseString("bool o; o := true; while o do wait(0.5); o := false; print 555; done print 666;");
		Program program = parse_outcome.get();
		Sprite[] sprites = spriteArrayForSize(5, 5, 30);

		Buzam buzam = new Buzam(20, 0, sprites, program);
		program.setGameObject(buzam);


		program.advanceTime(2.100);
		assertTrue(program.getAmountMainExecuted() == 4); //main statement is completed, but program loops further

	}

	@Test
	public void WhileStatementTest2(){
		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);

		Optional<Program> parse_outcome = parser.parseString("bool o; o := true; while o do wait(0.5); print 555; done print 666;");
		Program program = parse_outcome.get();
		Sprite[] sprites = spriteArrayForSize(5, 5, 30);

		Buzam buzam = new Buzam(20, 0, sprites, program);
		program.setGameObject(buzam);


		program.advanceTime(2.100);
		assertTrue(program.getAmountMainExecuted() == 0); //main statement is still going on

	}


	@Test
	public void SearchTileLeftTest(){
		World world = new World(50, 20, 20,
				200,200,10,15);
		Sprite[] sprites = spriteArrayForSize(45, 45, 2);
		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);
		Optional<Program> parse_outcome = parser.parseString("object o := null; o := searchobj ( left );");
		Program program = parse_outcome.get();
		IFacadePart3 facade = new Facade();

		world.setGeologicalFeature(0, 9, 1);
		world.setGeologicalFeature(0, 10, 1);
		world.setGeologicalFeature(0, 11, 1);
		world.setGeologicalFeature(5, 9, 1);
		world.setGeologicalFeature(5, 10, 1);
		world.setGeologicalFeature(5, 11, 1);
		Plant plantProgram = facade.createPlantWithProgram(500, 500, sprites, program);


		world.addPlant(plantProgram);

		world.advanceTime(0.1);

		// searchobject prints coordinates (5,10) this is indeed the closest inpassable left tile
		// seen from the given plant with program

	}



	@Test
	public void SearchTileRightTest1(){
		World world = new World(50, 20, 20,
				200,200,10,15);
		Sprite[] sprites = spriteArrayForSize(45, 45, 2);
		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);
		Optional<Program> parse_outcome = parser.parseString("object o := null; o := searchobj ( right );");
		Program program = parse_outcome.get();
		IFacadePart3 facade = new Facade();

		world.setGeologicalFeature(0, 9, 1);
		world.setGeologicalFeature(0, 10, 1);
		world.setGeologicalFeature(0, 11, 1);
		world.setGeologicalFeature(5, 9, 1);
		world.setGeologicalFeature(5, 10, 1);
		world.setGeologicalFeature(5, 11, 1);
		Plant plantProgram = facade.createPlantWithProgram(500, 500, sprites, program);


		world.addPlant(plantProgram);

		world.advanceTime(0.1);

		// System prints proves that there is no inpassable tile on the right side


	}

	@Test
	public void SearchTileRightTest2(){
		World world = new World(50, 20, 20,
				200,200,10,15);
		Sprite[] sprites = spriteArrayForSize(45, 45, 2);
		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);
		Optional<Program> parse_outcome = parser.parseString("object o := null; o := searchobj ( right );");
		Program program = parse_outcome.get();
		IFacadePart3 facade = new Facade();

		world.setGeologicalFeature(0, 9, 1);
		world.setGeologicalFeature(0, 10, 1);
		world.setGeologicalFeature(0, 11, 1);
		world.setGeologicalFeature(15, 9, 1);
		world.setGeologicalFeature(15, 10, 1);
		world.setGeologicalFeature(15, 11, 1);
		Plant plantProgram = facade.createPlantWithProgram(500, 500, sprites, program);


		world.addPlant(plantProgram);

		world.advanceTime(0.1);

		// searchobject prints coordinates (15,10) this is indeed the closest inpassable right tile
		// seen from the given plant with program


	}

	@Test
	public void IsWaterTile(){
		World world = new World(50, 20, 20,
				200,200,10,15);
		Sprite[] sprites = spriteArrayForSize(45, 45, 2);
		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);
		Optional<Program> parse_outcome = parser.parseString("bool o; double x; double y; x := 10; y:=10; o:= iswater(gettile(x,y)); print o;");
		Program program = parse_outcome.get();
		IFacadePart3 facade = new Facade();

		world.setGeologicalFeature(0, 0, 2);

		Plant plantProgram = facade.createPlantWithProgram(500, 500, sprites, program);


		world.addPlant(plantProgram);

		world.advanceTime(0.005);

		// the program prints a true.
	}



	@Test
	public void IsNotAWaterTile(){
		World world = new World(50, 20, 20,
				200,200,10,15);
		Sprite[] sprites = spriteArrayForSize(45, 45, 2);
		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);
		Optional<Program> parse_outcome = parser.parseString("bool o; double x; double y; x := 10; y:=10; o:= iswater(gettile(x,y)); print o;");
		Program program = parse_outcome.get();
		IFacadePart3 facade = new Facade();

		world.setGeologicalFeature(0, 0, 1);

		Plant plantProgram = facade.createPlantWithProgram(500, 500, sprites, program);


		world.addPlant(plantProgram);

		world.advanceTime(0.005);

		// the program prints a false.
	}

	@Test
	public void IsMagmaTile(){
		World world = new World(50, 20, 20,
				200,200,10,15);
		Sprite[] sprites = spriteArrayForSize(45, 45, 2);
		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);
		Optional<Program> parse_outcome = parser.parseString("bool o; double x; double y; x := 10; y:=10; o:= ismagma(gettile(x,y)); print o;");
		Program program = parse_outcome.get();
		IFacadePart3 facade = new Facade();

		world.setGeologicalFeature(0, 0, 3);

		Plant plantProgram = facade.createPlantWithProgram(500, 500, sprites, program);


		world.addPlant(plantProgram);

		world.advanceTime(0.005);

		// the program prints a true.
	}
	
	@Test
	public void searchObjectLeft(){
		World world = new World(50, 20, 20,
				200,200,10,15);
		Sprite[] sprites = spriteArrayForSize(2, 2, 2);
		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);
		Optional<Program> parse_outcome = parser.parseString("object o := null; o := searchobj ( left );");
		Program program = parse_outcome.get();
		IFacadePart3 facade = new Facade();

		Plant plant1 = facade.createPlant(10, 10, sprites);
		Plant plant2 = facade.createPlant(0, 10, sprites);
		Plant plant3 = facade.createPlant(3, 10, sprites);
		Plant plantProgram = facade.createPlantWithProgram(15, 10, sprites, program);

		world.addPlant(plant1);
		world.addPlant(plant2); 
		world.addPlant(plant3);
		world.addPlant(plantProgram);

		program.advanceTime(0.002);
	}

	@Test
	public void searchObjectRight(){
		World world = new World(50, 20, 20,
				200,200,10,15);
		Sprite[] sprites = spriteArrayForSize(2, 2, 2);
		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);
		Optional<Program> parse_outcome = parser.parseString("object o := null; o := searchobj ( right ); ");
		Program program = parse_outcome.get();
		IFacadePart3 facade = new Facade();

		Plant plant1 = facade.createPlant(50, 10, sprites);
		Plant plant2 = facade.createPlant(30, 10, sprites);
		Plant plant3 = facade.createPlant(4, 10, sprites);
		Plant plantProgram = facade.createPlantWithProgram(0, 10, sprites, program);

		world.addPlant(plant1);
		world.addPlant(plant2); 
		world.addPlant(plant3);
		world.addPlant(plantProgram);

		program.advanceTime(0.002);
	}


	@Test
	public void searchObjectUp(){
		System.out.println("up search");
		World world = new World(50, 20, 20,
				200,200,10,15);
		Sprite[] sprites = spriteArrayForSize(5, 5, 2);
		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);
		Optional<Program> parse_outcome = parser.parseString("object o := null; o := searchobj ( up ); ");
		Program program = parse_outcome.get();
		IFacadePart3 facade = new Facade();

		Plant plant1 = facade.createPlant(11, 100, sprites);
		Plant plant2 = facade.createPlant(11, 20, sprites);
		Plant plant3 = facade.createPlant(11, 50, sprites);
		Plant plantProgram = facade.createPlantWithProgram(10, 10, sprites, program);

		world.addPlant(plant1);
		world.addPlant(plant2); 
		world.addPlant(plant3);
		world.addPlant(plantProgram);

		program.advanceTime(0.002);
	}

}
