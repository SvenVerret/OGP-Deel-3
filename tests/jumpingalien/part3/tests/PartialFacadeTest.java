package jumpingalien.part3.tests;

import static org.junit.Assert.*;

import java.util.Optional;

import jumpingalien.model.Buzam;
import jumpingalien.model.Plant;
import jumpingalien.model.School;
import jumpingalien.model.Slime;
import jumpingalien.model.World;
import jumpingalien.part3.facade.Facade;
import jumpingalien.part3.facade.IFacadePart3;
import jumpingalien.part3.programs.IProgramFactory;
import jumpingalien.part3.programs.ParseOutcome;
import jumpingalien.part3.programs.ProgramParser;
import jumpingalien.util.Sprite;

import org.junit.Test;

import program.Program;
import program.ProgramFactory;
import program.expression.Expression;
import program.statement.PrintStatement;
import program.statement.SequenceStatement;
import program.statement.Statement;
import static jumpingalien.tests.util.TestUtils.*;


public class PartialFacadeTest {

	@Test
	public void testParseSimplestProgram() {
		IFacadePart3 facade = new Facade();
		ParseOutcome<?> outcome = facade.parse("skip;");
		assertTrue(outcome.isSuccess());
	}



	@Test
	public void testParseSimplestProgram2() {
		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);

		Optional<Program> parse_outcome = parser.parseString("double y; object o; o := self;");
		Program program = parse_outcome.get();
		Sprite[] sprites = spriteArrayForSize(2, 2, 2);
		
		IFacadePart3 facade = new Facade();
		facade.createPlantWithProgram(10, 10, sprites, program);
	}



	// OPERATIONS

	@Test
	public void testParseSimplestProgramAdd() {
		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);

		Optional<Program> parse_outcome = parser.parseString("double y; y := 1.0 + 2.0;");
		Program program = parse_outcome.get();

		program.advanceTime(0.1);
	}

	@Test
	public void testParseSimplestProgramSubtract() {
		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);

		Optional<Program> parse_outcome = parser.parseString("double y; y := 1.0 - 2.0;");
		Program program = parse_outcome.get();

		program.advanceTime(0.1);
	}

	@Test
	public void testParseSimplestProgramMultiply() {
		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);

		Optional<Program> parse_outcome = parser.parseString("double y; y := 4.0 * 2.0;");
		Program program = parse_outcome.get();

		program.advanceTime(0.1);
		System.out.println(program.getVariables());
	}

	@Test
	public void testParseSimplestProgramDivide() {
		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);

		Optional<Program> parse_outcome = parser.parseString("double y; y := 1.0 / 0.0;");
		Program program = parse_outcome.get();

		program.advanceTime(0.1);
	}

	@Test
	public void testParseSimplestProgramEquals() {
		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);

		Optional<Program> parse_outcome = parser.parseString("double y; double x; double rslt;"
				+ "y := 3.0; x := 3.0; rslt := x == y;");
		Program program = parse_outcome.get();

		program.advanceTime(0.1);
		System.out.println(program.getVariables());
	}



	@Test
	public void testParseSimplestProgram4() {
		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);

		Optional<Program> parse_outcome = parser.parseString("currentxdir := up; newxdir := left;");
		Program program = parse_outcome.get();

		program.advanceTime(0.1);

	}



	@Test
	public void testParseFails() {
		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);
		Optional<Program> parse_outcome = parser.parseString("print self + 1.45;");
		System.out.println(parse_outcome);
	}


	@Test
	public void testBreakNotWellformed() {
		IFacadePart3 facade = new Facade();
		ParseOutcome<?> outcome = facade.parse("double d := 1.0; break;");
		assertFalse(facade.isWellFormed((Program) outcome.getResult()));
	}


	@Test
	public void testWellformed() {
		IFacadePart3 facade = new Facade();
		ParseOutcome<?> outcome = facade.parse("double d := 1.0; while d < 3 do if random 2 <= 1 then break; fi done");
		assertTrue(outcome.isSuccess());
		assertTrue(facade.isWellFormed((Program) outcome.getResult()));
	}

	@Test
	public void testPlantWithProgramMoveToRightAndStop(){
		World world = new World(50, 10, 20,
				200,300,10,15);
		Sprite[] sprites = spriteArrayForSize(2, 2, 2);

		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);

		Optional<Program> parse_outcome = parser.parseString("start_run right; wait(0.2 - 0.001); stop_run right;wait(1.0 - 0.001);");

		Program program = parse_outcome.get();

		IFacadePart3 facade = new Facade();
		Plant plant = facade.createPlantWithProgram(10, 10, sprites, program);
		world.addPlant(plant); 
		for(int i=0; i<10; i++){
			world.advanceTime(0.1);
		}
		//0.2s * 0.5m/s = rekensom???
		//TODO
		assertTrue(plant.getPos().getElemx() == 28.0);
	}

	@Test
	public void testSlimeWithProgramMoveToRightAndStop(){
		World world = new World(50, 10, 20,
				200,300,10,15);
		Sprite[] sprites = spriteArrayForSize(2, 2, 2);

		School school = new School();

		IProgramFactory<Expression<?>, Statement, Object, Program> factory = new ProgramFactory();
		ProgramParser<Expression<?>, Statement, Object, Program> parser = new ProgramParser<>(factory);

		Optional<Program> parse_outcome = parser.parseString("start_run right;");

		Program program = parse_outcome.get();

		IFacadePart3 facade = new Facade();
		Slime slime = facade.createSlimeWithProgram(0, 0,sprites,school, program);
		world.addSlime(slime); 
		for(int i=0; i<10; i++){
			world.advanceTime(0.1);
			System.out.println(slime.getPos().getElemx());
		}
		//0.2s * 0.5m/s = rekensom???
		//TODO
		//assertTrue(slime.getPos().getElemx() == 28.0);
	}



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
	public void StatementTest(){
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


		world.advanceTime(0.003);


		assertTrue(program.getAmountMainExecuted() == 1);
	}

	@Test
	public void StatementTest2(){
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
