package program;


import java.util.List;
import java.util.Map;

import program.expression.Expression;
import program.expression.ValueExpression;
import program.expression.VariableValueExpression;
import program.expression.operation.BinaryExpressionOperation;
import program.expression.operation.SingleExpressionOperation;
import program.statement.AssignmentStatement;
import program.statement.Statement;
import program.statement.WaitStatement;
import program.type.DoubleType;
import program.type.Type;
import jumpingalien.model.GameObject;
import jumpingalien.model.Mazub;
import jumpingalien.model.Plant;
import jumpingalien.model.Shark;
import jumpingalien.model.Slime;
import jumpingalien.model.World;
import jumpingalien.part3.programs.IProgramFactory;
import jumpingalien.part3.programs.SourceLocation;


//supress warning needed
public class ProgramFactory implements IProgramFactory<Expression<?>, Statement, Type<?>, Program> {

	@Override
	public Expression<?> createReadVariable(String variableName,
			Type<?> variableType, SourceLocation sourceLocation) {
		System.out.println("ReadVariables");
		return new VariableValueExpression<Type>(variableName,variableType,sourceLocation);
	}
	
	@Override
	public Expression<Double> createDoubleConstant(double value,
			SourceLocation sourceLocation) {
		System.out.println("DoubleConstant");
		return new ValueExpression<Double>(value, sourceLocation);
	}

	@Override
	public Expression<Boolean> createTrue(SourceLocation sourceLocation) {
		return new ValueExpression<Boolean>(true, sourceLocation);

	}

	@Override
	public Expression<Boolean> createFalse(SourceLocation sourceLocation) {
		return new ValueExpression<Boolean>(true, sourceLocation);
	}

	@Override
	public Expression<?> createNull(SourceLocation sourceLocation) {
		return new ValueExpression<>(null,sourceLocation);
	}

	@Override
	public Expression<?> createSelf(SourceLocation sourceLocation) {
		return new ValueExpression<Object>("self",sourceLocation);
	}

	@Override
	public Expression<?> createDirectionConstant(
			Direction value, SourceLocation sourceLocation) {
		return new ValueExpression<Direction>(value,sourceLocation);
	}

	@SuppressWarnings({"unchecked"})
	@Override
	public Expression<?> createAddition(Expression<?> left,
			Expression<?> right, SourceLocation sourceLocation) {
		BinaryExpressionOperation<Double, Double, Double> result =
				new BinaryExpressionOperation<Double, Double, Double>(
						(ValueExpression<Double>) left, (ValueExpression<Double>) right,
						(a,b) -> (Double)a+(Double)b, sourceLocation);

		return result;
	}

	@SuppressWarnings({"unchecked"})
	@Override
	public Expression<?> createSubtraction(Expression<?> left,
			Expression<?> right, SourceLocation sourceLocation) {
		BinaryExpressionOperation<Double, Double, Double> result =
				new BinaryExpressionOperation<Double, Double, Double>(
						(ValueExpression<Double>) left, (ValueExpression<Double>) right,
						(a,b) -> (Double)a-(Double)b, sourceLocation);

		return result;
	}

	@SuppressWarnings({ "unchecked"})
	@Override
	public Expression<?> createMultiplication(Expression<?> left,
			Expression<?> right, SourceLocation sourceLocation) {
		BinaryExpressionOperation<Double, Double, Double> result =
				new BinaryExpressionOperation<Double, Double, Double>(
						(ValueExpression<Double>) left, (ValueExpression<Double>) right,
						(a,b) -> (Double)a*(Double)b, sourceLocation);

		return result;
	}
	@SuppressWarnings({ "unchecked"})
	@Override
	public Expression<?> createDivision(Expression<?> left,
			Expression<?> right, SourceLocation sourceLocation) {
		BinaryExpressionOperation<Double, Double, Double> result =
				new BinaryExpressionOperation<Double, Double, Double>(
						(ValueExpression<Double>) left, (ValueExpression<Double>) right,
						(a,b) -> (Double)a/(Double)b, sourceLocation);

		return result;
	}

	@SuppressWarnings({ "unchecked"})
	@Override
	public Expression<?> createSqrt(Expression<?> expr,
			SourceLocation sourceLocation) {
		SingleExpressionOperation<Double, Double> result =
				new SingleExpressionOperation<Double, Double>(
						(ValueExpression<Double>)expr, 
						e -> Math.sqrt((Double)e), sourceLocation);

		return result;
	}

	@SuppressWarnings({ "unchecked"})
	@Override
	public Expression<?> createRandom(Expression<?> maxValue,
			SourceLocation sourceLocation) {
		SingleExpressionOperation<Double, Double> result =
				new SingleExpressionOperation<Double, Double>(
						(ValueExpression<Double>)maxValue, 
						e -> Math.random()*e, sourceLocation);

		return result;
	}

	@SuppressWarnings({ "unchecked"})
	@Override
	public Expression<?> createAnd(Expression<?> left, Expression<?> right,
			SourceLocation sourceLocation) {
		BinaryExpressionOperation<Boolean, Boolean, Boolean> result =
				new BinaryExpressionOperation<Boolean, Boolean, Boolean>(
						(ValueExpression<Boolean>) left, (ValueExpression<Boolean>) right,
						(a,b) -> (a&&b), sourceLocation);

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Expression<?> createOr(Expression<?> left, Expression<?> right,
			SourceLocation sourceLocation) {
		BinaryExpressionOperation<Boolean, Boolean, Boolean> result =
				new BinaryExpressionOperation<Boolean, Boolean, Boolean>(
						(ValueExpression<Boolean>) left, (ValueExpression<Boolean>) right,
						(a,b) -> (a||b), sourceLocation);

		return result;
	}

	@Override
	public Expression<?> createNot(Expression<?> expr,
			SourceLocation sourceLocation) {
		@SuppressWarnings("unchecked")
		SingleExpressionOperation<Boolean, Boolean> result =
				new SingleExpressionOperation<Boolean, Boolean>(
						(Expression<Boolean>)expr, 
						e -> !e, sourceLocation);

		return result;
	}

	@Override
	public Expression<?> createLessThan(Expression<?> left,
			Expression<?> right, SourceLocation sourceLocation) {
		BinaryExpressionOperation<Boolean, Double, Double> result =
				new BinaryExpressionOperation<Boolean, Double, Double>(
						 (Expression<Double>)left, (Expression<Double>) right,
						(a,b) -> (a<b), sourceLocation);

		return result;
	}

	@Override
	public Expression<?> createLessThanOrEqualTo(Expression<?> left,
			Expression<?> right, SourceLocation sourceLocation) {
		BinaryExpressionOperation<Boolean, Double, Double> result =
				new BinaryExpressionOperation<Boolean, Double, Double>(
						(ValueExpression<Double>) left, (ValueExpression<Double>) right,
						(a,b) -> (a<=b), sourceLocation);

		return result;
	}

	@Override
	public Expression<?> createGreaterThan(Expression<?> left,
			Expression<?> right, SourceLocation sourceLocation) {
		BinaryExpressionOperation<Boolean, Double, Double> result =
				new BinaryExpressionOperation<Boolean, Double, Double>(
						(ValueExpression<Double>) left, (ValueExpression<Double>) right,
						(a,b) -> (a>b), sourceLocation);

		return result;
	}

	@Override
	public Expression<?> createGreaterThanOrEqualTo(Expression<?> left,
			Expression<?> right, SourceLocation sourceLocation) {
		BinaryExpressionOperation<Boolean, Double, Double> result =
				new BinaryExpressionOperation<Boolean, Double, Double>(
						(ValueExpression<Double>) left, (ValueExpression<Double>) right,
						(a,b) -> (a>=b), sourceLocation);

		return result;
	}

	@Override
	public Expression<?> createEquals(Expression<?> left, Expression<?> right,
			SourceLocation sourceLocation) {
		BinaryExpressionOperation<Boolean, Object, Object> result =
				new BinaryExpressionOperation<Boolean, Object, Object>(
						(ValueExpression<Object>) left, (ValueExpression<Object>) right,
						(a,b) -> (a==b), sourceLocation);

		return result;
	}

	@Override
	public Expression<?> createNotEquals(Expression<?> left,
			Expression<?> right, SourceLocation sourceLocation) {
		BinaryExpressionOperation<Boolean, Object, Object> result =
				new BinaryExpressionOperation<Boolean, Object, Object>(
						(ValueExpression<Object>) left, (ValueExpression<Object>) right,
						(a,b) -> (a!=b), sourceLocation);

		return result;
	}

	@Override
	public Expression<?> createGetX(Expression<?> expr,
			SourceLocation sourceLocation) {
		SingleExpressionOperation<Double, GameObject> result =
				new SingleExpressionOperation<Double, GameObject>(
						(ValueExpression<GameObject>)expr, 
						e -> e.getPos().getElemx(), sourceLocation);

		return result;
	}

	@Override
	public Expression<?> createGetY(Expression<?> expr,
			SourceLocation sourceLocation) {
		SingleExpressionOperation<Double, GameObject> result =
				new SingleExpressionOperation<Double, GameObject>(
						(ValueExpression<GameObject>)expr, 
						e -> e.getPos().getElemy(), sourceLocation);

		return result;
	}

	@Override
	public Expression<?> createGetWidth(Expression<?> expr,
			SourceLocation sourceLocation) {
		SingleExpressionOperation<Double, GameObject> result =
				new SingleExpressionOperation<Double, GameObject>(
						(ValueExpression<GameObject>)expr, 
						e -> e.getSize().getElemx(), sourceLocation);

		return result;
	}

	@Override
	public Expression<?> createGetHeight(Expression<?> expr,
			SourceLocation sourceLocation) {
		SingleExpressionOperation<Double, GameObject> result =
				new SingleExpressionOperation<Double, GameObject>(
						(ValueExpression<GameObject>)expr, 
						e -> e.getSize().getElemy(), sourceLocation);

		return result;
	}

	@Override
	public Expression<?> createGetHitPoints(Expression<?> expr,
			SourceLocation sourceLocation) {
		SingleExpressionOperation<Integer, GameObject> result =
				new SingleExpressionOperation<Integer, GameObject>(
						(ValueExpression<GameObject>)expr, 
						e -> e.getHP(), sourceLocation);

		return result;
	}

	@Override
	public Expression<?> createGetTile(Expression<?> x, Expression<?> y,
			SourceLocation sourceLocation) {
//		BinaryExpressionOperation<int[], Integer, Integer> result =
//				new BinaryExpressionOperation<int[], Integer, Integer>(
//						(ValueExpression<Integer>) x, (ValueExpression<Integer>) y,
//						(a,b) -> World.convertXYtoXTYT(x, y) , sourceLocation);
//
//		return result;
		return null;
	}

	@Override
	public Expression<?> createSearchObject(Expression<?> direction,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<?> createIsMazub(Expression<?> expr,
			SourceLocation sourceLocation) {
		SingleExpressionOperation<Boolean, GameObject> result =
				new SingleExpressionOperation<Boolean, GameObject>(
						(ValueExpression<GameObject>)expr, 
						e -> e instanceof Mazub, sourceLocation);

		return result;
	}

	@Override
	public Expression<?> createIsShark(Expression<?> expr,
			SourceLocation sourceLocation) {
		SingleExpressionOperation<Boolean, GameObject> result =
				new SingleExpressionOperation<Boolean, GameObject>(
						(ValueExpression<GameObject>)expr, 
						e -> e instanceof Shark, sourceLocation);

		return result;
	}

	@Override
	public Expression<?> createIsSlime(Expression<?> expr,
			SourceLocation sourceLocation) {
		SingleExpressionOperation<Boolean, GameObject> result =
				new SingleExpressionOperation<Boolean, GameObject>(
						(ValueExpression<GameObject>)expr, 
						e -> e instanceof Slime, sourceLocation);

		return result;
	}

	@Override
	public Expression<?> createIsPlant(Expression<?> expr,
			SourceLocation sourceLocation) {
		SingleExpressionOperation<Boolean, GameObject> result =
				new SingleExpressionOperation<Boolean, GameObject>(
						(ValueExpression<GameObject>)expr, 
						e -> e instanceof Plant, sourceLocation);

		return result;
	}

	@Override
	public Expression<?> createIsDead(Expression<?> expr,
			SourceLocation sourceLocation) {
		SingleExpressionOperation<Boolean, GameObject> result =
				new SingleExpressionOperation<Boolean, GameObject>(
						(ValueExpression<GameObject>)expr, 
						e -> e.isDead(), sourceLocation);

		return result;
	}

	@Override
	public Expression<?> createIsTerrain(Expression<?> expr,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
				return null;
			}

	@Override
	public Expression<?> createIsPassable(Expression<?> expr,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<?> createIsWater(Expression<?> expr,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<?> createIsMagma(Expression<?> expr,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<?> createIsAir(Expression<?> expr,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<?> createIsMoving(Expression<?> expr,
			Expression<?> direction, SourceLocation sourceLocation) {
		SingleExpressionOperation<Boolean, GameObject> result =
				new SingleExpressionOperation<Boolean, GameObject>(
						(ValueExpression<GameObject>)expr, 
						e -> e.getVelocity().normOfVector() != 0.0, sourceLocation);

		return result;
	}

	@Override
	public Expression<?> createIsDucking(Expression<?> expr,
			SourceLocation sourceLocation) {
		SingleExpressionOperation<Boolean, Mazub> result =
				new SingleExpressionOperation<Boolean, Mazub>(
						(ValueExpression<Mazub>)expr, 
						e -> e.isDucked(), sourceLocation);

		return result;
	}

	@Override
	public Expression<?> createIsJumping(Expression<?> expr,
			SourceLocation sourceLocation) {
		SingleExpressionOperation<Boolean, GameObject> result =
				new SingleExpressionOperation<Boolean, GameObject>(
						(ValueExpression<GameObject>)expr, 
						e -> e.getVelocity().getElemy() != 0.0, sourceLocation);

		return result;
	}

	@Override
	public Statement createAssignment(String variableName, Type<?> variableType,
			Expression<?> value, SourceLocation sourceLocation) {
		System.out.println("Assignment");
		return new AssignmentStatement(variableName, variableType,
				value, sourceLocation);
	}

	@Override
	public Statement createWhile(Expression<?> condition, Statement body,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createForEach(
			String variableName,
			jumpingalien.part3.programs.IProgramFactory.Kind variableKind,
			Expression<?> where,
			Expression<?> sort,
			jumpingalien.part3.programs.IProgramFactory.SortDirection sortDirection,
			Statement body, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createBreak(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createIf(Expression<?> condition, Statement ifBody,
			Statement elseBody, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createPrint(Expression<?> value,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createStartRun(Expression<?> direction,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createStopRun(Expression<?> direction,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createStartJump(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createStopJump(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createStartDuck(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createStopDuck(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createWait(Expression<?> duration,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createSkip(SourceLocation sourceLocation) {
		System.out.println("Skip");
		ValueExpression<Double> time = new ValueExpression<Double>((double) 1,sourceLocation);
		WaitStatement result = new WaitStatement(time,sourceLocation);
		return result;
	}

	@Override
	public Statement createSequence(List<Statement> statements,
			SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type<?> getDoubleType() {
		System.out.println("Getdoubletypes");
		return new DoubleType();
	}

	@Override
	public Type<?> getBoolType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type<?> getGameObjectType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type<?> getDirectionType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Program createProgram(Statement mainStatement,
			Map<String, Type<?>> globalVariables) {
		System.out.println(mainStatement);
		System.out.println(globalVariables);
		return new Program(mainStatement, globalVariables);
	}
}

