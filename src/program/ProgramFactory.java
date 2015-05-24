package program;


import java.util.List;
import java.util.Map;

import program.expression.Expression;
import program.expression.SearchObjectExpression;
import program.expression.SelfExpression;
import program.expression.TileExpression;
import program.expression.ValueExpression;
import program.expression.VariableValueExpression;
import program.expression.inspector.IsAirExpression;
import program.expression.inspector.IsInpassableExpression;
import program.expression.inspector.IsMagmaExpression;
import program.expression.inspector.IsPassableExpression;
import program.expression.inspector.IsWaterExpression;
import program.expression.operation.BinaryExpressionOperation;
import program.expression.operation.EqualsExpression;
import program.expression.operation.NotEqualsExpression;
import program.expression.operation.SingleExpressionOperation;
import program.statement.AssignmentStatement;
import program.statement.BreakStatement;
import program.statement.ForEachStatement;
import program.statement.IfStatement;
import program.statement.MoveStatement;
import program.statement.PrintStatement;
import program.statement.SequenceStatement;
import program.statement.Statement;
import program.statement.WaitStatement;
import program.statement.WhileStatement;
import jumpingalien.model.GameObject;
import jumpingalien.model.Mazub;
import jumpingalien.model.Plant;
import jumpingalien.model.Shark;
import jumpingalien.model.Slime;
import jumpingalien.part3.programs.IProgramFactory;
import jumpingalien.part3.programs.SourceLocation;

@SuppressWarnings("unchecked")
public class ProgramFactory implements IProgramFactory<Expression<?>, Statement, Object, Program> {

	@Override
	public Expression<?> createReadVariable(String variableName,
			Object variableType, SourceLocation sourceLocation) {
		return new VariableValueExpression<Object>(variableName,variableType,sourceLocation);
	}

	@Override
	public Expression<Double> createDoubleConstant(double value,
			SourceLocation sourceLocation) {
		return new ValueExpression<Double>(new Double(value), sourceLocation);
	}

	@Override
	public Expression<Boolean> createTrue(SourceLocation sourceLocation) {
		return new ValueExpression<Boolean>(true, sourceLocation);
	}

	@Override
	public Expression<Boolean> createFalse(SourceLocation sourceLocation) {
		return new ValueExpression<Boolean>(false, sourceLocation);
	}

	@Override
	public Expression<?> createNull(SourceLocation sourceLocation) {
		return new ValueExpression<>(null,sourceLocation);
	}

	@Override
	public Expression<?> createSelf(SourceLocation sourceLocation) {
		return new SelfExpression(sourceLocation);
	}

	@Override
	public Expression<?> createDirectionConstant(
			Direction value, SourceLocation sourceLocation) {
		return new ValueExpression<Direction>(value,sourceLocation);
	}

	@Override
	public Expression<?> createAddition(Expression<?> left,
			Expression<?> right, SourceLocation sourceLocation) {

		BinaryExpressionOperation<Double, Double, Double> result =
				new BinaryExpressionOperation<Double, Double, Double>(
						(Expression<Double>) left, (Expression<Double>) right,
						(a,b) -> (Double)a+(Double)b, sourceLocation);

		return result;
	}

	@Override
	public Expression<?> createSubtraction(Expression<?> left,
			Expression<?> right, SourceLocation sourceLocation) {
		BinaryExpressionOperation<Double, Double, Double> result =
				new BinaryExpressionOperation<Double, Double, Double>(
						(Expression<Double>) left, (Expression<Double>) right,
						(a,b) -> (Double)a-(Double)b, sourceLocation);

		return result;
	}

	@Override
	public Expression<?> createMultiplication(Expression<?> left,
			Expression<?> right, SourceLocation sourceLocation) {
		BinaryExpressionOperation<Double, Double, Double> result =
				new BinaryExpressionOperation<Double, Double, Double>(
						(Expression<Double>) left, (Expression<Double>) right,
						(a,b) -> (Double)a*(Double)b, sourceLocation);

		return result;
	}

	@Override
	public Expression<?> createDivision(Expression<?> left,
			Expression<?> right, SourceLocation sourceLocation) {
		BinaryExpressionOperation<Double, Double, Double> result =
				new BinaryExpressionOperation<Double, Double, Double>(
						(Expression<Double>) left, (Expression<Double>) right,
						(a,b) -> (Double)a/(Double)b, sourceLocation);

		return result;
	}

	@Override
	public Expression<?> createSqrt(Expression<?> expr,
			SourceLocation sourceLocation) {
		SingleExpressionOperation<Double, Double> result =
				new SingleExpressionOperation<Double, Double>(
						(Expression<Double>)expr, 
						e -> Math.sqrt((Double)e), sourceLocation);

		return result;
	}

	@Override
	public Expression<?> createRandom(Expression<?> maxValue,
			SourceLocation sourceLocation) {
		SingleExpressionOperation<Double, Double> result =
				new SingleExpressionOperation<Double, Double>(
						(Expression<Double>)maxValue, 
						e -> Math.random()*(Double)e, sourceLocation);

		return result;
	}

	@Override
	public Expression<?> createAnd(Expression<?> left, Expression<?> right,
			SourceLocation sourceLocation) {
		System.out.println("AND");
		BinaryExpressionOperation<Boolean, Boolean, Boolean> result =
				new BinaryExpressionOperation<Boolean, Boolean, Boolean>(
						(Expression<Boolean>) left, (Expression<Boolean>) right,
						(a,b) -> ((Boolean)a && (Boolean)b), sourceLocation);
		return result;
	}

	@Override
	public Expression<?> createOr(Expression<?> left, Expression<?> right,
			SourceLocation sourceLocation) {
		BinaryExpressionOperation<Boolean, Boolean, Boolean> result =
				new BinaryExpressionOperation<Boolean, Boolean, Boolean>(
						(Expression<Boolean>) left, (Expression<Boolean>) right,
						(a,b) -> ((Boolean)a || (Boolean)b), sourceLocation);

		return result;
	}

	@Override
	public Expression<?> createNot(Expression<?> expr,
			SourceLocation sourceLocation) {
		SingleExpressionOperation<Boolean, Boolean> result =
				new SingleExpressionOperation<Boolean, Boolean>(
						(Expression<Boolean>)expr, 
						e -> !(Boolean)e, sourceLocation);

		return result;
	}

	@Override
	public Expression<?> createLessThan(Expression<?> left,
			Expression<?> right, SourceLocation sourceLocation) {
		BinaryExpressionOperation<Boolean, Double, Double> result =
				new BinaryExpressionOperation<Boolean, Double, Double>(
						(Expression<Double>)left, (Expression<Double>) right,
						(a,b) -> ((Double)a<(Double)b), sourceLocation);

		return result;
	}

	@Override
	public Expression<?> createLessThanOrEqualTo(Expression<?> left,
			Expression<?> right, SourceLocation sourceLocation) {
		BinaryExpressionOperation<Boolean, Double, Double> result =
				new BinaryExpressionOperation<Boolean, Double, Double>(
						(Expression<Double>) left, (Expression<Double>) right,
						(a,b) -> ((Double)a<=(Double)b), sourceLocation);

		return result;
	}

	@Override
	public Expression<?> createGreaterThan(Expression<?> left,
			Expression<?> right, SourceLocation sourceLocation) {
		BinaryExpressionOperation<Boolean, Double, Double> result =
				new BinaryExpressionOperation<Boolean, Double, Double>(
						(Expression<Double>) left, (Expression<Double>) right,
						(a,b) -> ((Double)a>(Double)b), sourceLocation);

		return result;
	}

	@Override
	public Expression<?> createGreaterThanOrEqualTo(Expression<?> left,
			Expression<?> right, SourceLocation sourceLocation) {
		BinaryExpressionOperation<Boolean, Double, Double> result =
				new BinaryExpressionOperation<Boolean, Double, Double>(
						(Expression<Double>) left, (Expression<Double>) right,
						(a,b) -> ((Double)a>=(Double)b), sourceLocation);

		return result;
	}

	@Override
	public Expression<?> createEquals(Expression<?> left, Expression<?> right,
			SourceLocation sourceLocation) {
		EqualsExpression<Boolean, Object, Object> result =
				new EqualsExpression<Boolean, Object, Object>(
						(Expression<Object>) left, (Expression<Object>) right, sourceLocation);

		return result;
	}

	@Override
	public Expression<?> createNotEquals(Expression<?> left,
			Expression<?> right, SourceLocation sourceLocation) {
		NotEqualsExpression<Boolean, Object, Object> result =
				new NotEqualsExpression<Boolean, Object, Object>(
						(Expression<Object>) left, (Expression<Object>) right, sourceLocation);

		return result;
	}

	@Override
	public Expression<?> createGetX(Expression<?> expr,
			SourceLocation sourceLocation) {
		SingleExpressionOperation<Double, GameObject> result =
				new SingleExpressionOperation<Double, GameObject>(
						(Expression<GameObject>)expr, 
						e -> ((GameObject) e).getPos().getElemx(), sourceLocation);

		return result;
	}

	@Override
	public Expression<?> createGetY(Expression<?> expr,
			SourceLocation sourceLocation) {
		SingleExpressionOperation<Double, GameObject> result =
				new SingleExpressionOperation<Double, GameObject>(
						(Expression<GameObject>)expr, 
						e -> ((GameObject) e).getPos().getElemy(), sourceLocation);

		return result;
	}

	@Override
	public Expression<?> createGetWidth(Expression<?> expr,
			SourceLocation sourceLocation) {
		SingleExpressionOperation<Double, GameObject> result =
				new SingleExpressionOperation<Double, GameObject>(
						(Expression<GameObject>)expr, 
						e -> ((GameObject) e).getSize().getElemx(), sourceLocation);

		return result;
	}

	@Override
	public Expression<?> createGetHeight(Expression<?> expr,
			SourceLocation sourceLocation) {
		SingleExpressionOperation<Double, GameObject> result =
				new SingleExpressionOperation<Double, GameObject>(
						(Expression<GameObject>)expr, 
						e -> ((GameObject) e).getSize().getElemy(), sourceLocation);

		return result;
	}

	@Override
	public Expression<?> createGetHitPoints(Expression<?> expr,
			SourceLocation sourceLocation) {
		SingleExpressionOperation<Integer, GameObject> result =
				new SingleExpressionOperation<Integer, GameObject>(
						(Expression<GameObject>)expr, 
						e -> ((GameObject) e).getHP(), sourceLocation);

		return result;
	}

	@Override
	public Expression<?> createGetTile(Expression<?> x, Expression<?> y,
			SourceLocation sourceLocation) {
		return new TileExpression((Expression<Double>)x, (Expression<Double>)y, sourceLocation);
	}

	@Override
	public Expression<?> createSearchObject(Expression<?> direction, SourceLocation sourceLocation) {
		return new SearchObjectExpression((Expression<Direction>) direction, sourceLocation);
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
						e -> ((GameObject) e).isDead(), sourceLocation);

		return result;
	}

	@Override
	public Expression<?> createIsTerrain(Expression<?> expr,
			SourceLocation sourceLocation) {
		return new IsInpassableExpression(expr, sourceLocation);
	}

	@Override
	public Expression<?> createIsPassable(Expression<?> expr,
			SourceLocation sourceLocation) {
		return new IsPassableExpression(expr, sourceLocation);
	}

	@Override
	public Expression<?> createIsWater(Expression<?> expr,
			SourceLocation sourceLocation) {
		return new IsWaterExpression(expr, sourceLocation);
	}

	@Override
	public Expression<?> createIsMagma(Expression<?> expr,
			SourceLocation sourceLocation) {
		return new IsMagmaExpression( expr, sourceLocation);
	}

	@Override
	public Expression<?> createIsAir(Expression<?> expr,
			SourceLocation sourceLocation) {
		return new IsAirExpression(expr, sourceLocation);
	}

	@Override
	public Expression<?> createIsMoving(Expression<?> expr,
			Expression<?> direction, SourceLocation sourceLocation) {
		SingleExpressionOperation<Boolean, GameObject> result =
				new SingleExpressionOperation<Boolean, GameObject>(
						(Expression<GameObject>)expr, 
						e -> ((GameObject) e).getVelocity().normOfVector() != 0.0, sourceLocation);

		return result;
	}

	@Override
	public Expression<?> createIsDucking(Expression<?> expr,
			SourceLocation sourceLocation) {
		SingleExpressionOperation<Boolean, Mazub> result =
				new SingleExpressionOperation<Boolean, Mazub>(
						(Expression<Mazub>)expr, 
						e -> ((Mazub) e).isDucked(), sourceLocation);

		return result;
	}

	@Override
	public Expression<?> createIsJumping(Expression<?> expr,
			SourceLocation sourceLocation) {
		SingleExpressionOperation<Boolean, GameObject> result =
				new SingleExpressionOperation<Boolean, GameObject>(
						(Expression<GameObject>)expr, 
						e -> (!((GameObject) e).isOnGround()), sourceLocation);

		return result;
	}

	@Override
	public Statement createAssignment(String variableName, Object variableType,
			Expression<?> value, SourceLocation sourceLocation) {
		return new AssignmentStatement(variableName, variableType,
				value, sourceLocation);
	}

	@Override
	public Statement createWhile(Expression<?> condition, Statement body,
			SourceLocation sourceLocation) {
		return new WhileStatement((Expression<Boolean>) condition, body, sourceLocation);
	}

	@Override
	public Statement createForEach(
			String variableName,
			jumpingalien.part3.programs.IProgramFactory.Kind variableKind,
			Expression<?> where,
			Expression<?> sort,
			jumpingalien.part3.programs.IProgramFactory.SortDirection sortDirection,
			Statement body, SourceLocation sourceLocation) {
		return new ForEachStatement(variableName, variableKind, (Expression<Boolean>)where, (Expression<Double>)sort, sortDirection, body, sourceLocation);
	}

	@Override
	public Statement createBreak(SourceLocation sourceLocation) {
		return new BreakStatement(sourceLocation);
	}

	@Override
	public Statement createIf(Expression<?> condition, Statement ifBody,
			Statement elseBody, SourceLocation sourceLocation) {
		return new IfStatement((Expression<Boolean>) condition,ifBody,elseBody,sourceLocation);
	}

	@Override
	public Statement createPrint(Expression<?> value,
			SourceLocation sourceLocation) {
		return new PrintStatement(value,sourceLocation);
	}

	
	public enum Movement{
		STARTRUN,STARTJUMP,STARTDUCK,STOPRUN,STOPJUMP,STOPDUCK;
	}
	
	@Override
	public Statement createStartRun(Expression<?> direction,
			SourceLocation sourceLocation) {
		return new MoveStatement(Movement.STARTRUN,(Expression<jumpingalien.part3.programs.IProgramFactory.Direction>) direction, sourceLocation);
	}

	@Override
	public Statement createStopRun(Expression<?> direction,
			SourceLocation sourceLocation) {
		return new MoveStatement(Movement.STOPRUN, (Expression<jumpingalien.part3.programs.IProgramFactory.Direction>) direction, sourceLocation);
	}

	@Override
	public Statement createStartJump(SourceLocation sourceLocation) {
		return new MoveStatement(Movement.STARTJUMP, null, sourceLocation);
	}

	@Override
	public Statement createStopJump(SourceLocation sourceLocation) {
		return new MoveStatement(Movement.STOPJUMP, null, sourceLocation);
	}

	@Override
	public Statement createStartDuck(SourceLocation sourceLocation) {
		return new MoveStatement(Movement.STARTDUCK, null, sourceLocation);
	}

	@Override
	public Statement createStopDuck(SourceLocation sourceLocation) {
		return new MoveStatement(Movement.STOPDUCK, null, sourceLocation);
	}

	@Override
	public Statement createWait(Expression<?> duration,
			SourceLocation sourceLocation) {
		return new WaitStatement((Expression<Double>)duration,sourceLocation);
	}

	@Override
	public Statement createSkip(SourceLocation sourceLocation) {
		ValueExpression<Double> time = new ValueExpression<Double>((double) 1,sourceLocation);
		return new WaitStatement(time,sourceLocation);
	}

	@Override
	public Statement createSequence(List<Statement> statements,
			SourceLocation sourceLocation) {
		return new SequenceStatement(statements,sourceLocation);
	}

	@Override
	public Object getDoubleType() {
		return new Double(0.0);
	}

	@Override
	public Object getBoolType() {
		return new Boolean(null);
	}

	@Override
	public Object getGameObjectType() {
		System.out.println("GameObject");
		return null;
	}

	@Override
	public Object getDirectionType() {
		return Direction.DOWN;
	}

	@Override
	public Program createProgram(Statement mainStatement,
			Map<String, Object> globalVariables) {
		return new Program(mainStatement, globalVariables);
	}
}

