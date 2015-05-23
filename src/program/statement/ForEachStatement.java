package program.statement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import program.Program;
import program.expression.Expression;
import program.util.BreakException;
import jumpingalien.model.GameObject;
import jumpingalien.model.World;
import jumpingalien.part3.programs.SourceLocation;
import jumpingalien.part3.programs.IProgramFactory.Kind;
import jumpingalien.part3.programs.IProgramFactory.SortDirection;

public class ForEachStatement extends Statement{

	/**
	 * 
	 * @param variableName
	 * @param variableKind
	 * @param where
	 * @param sort
	 * @param sortDirection
	 * @param body
	 *  
	 */
	public ForEachStatement(String variableName, Kind variableKind, Expression<Boolean> where, 
			Expression<Double> sort, SortDirection sortDirection, Statement body, 
			SourceLocation sourceLocation) throws IllegalArgumentException{
		super(sourceLocation);

		setVariableName(variableName);
		setVariableKind(variableKind);
		setWhereExpression(where);
		setSortExpression(sort);
		setSortDirection(sortDirection);
		setForBody(body);
	}


	@Override
	public void advanceTime(double dt, Program program) {

		// FIRST EXECUTION OF THIS STATEMENT
		if(FirstExecution){
			List<GameObject> Objects = ConstructAllVariablesWithKind(getVariableKind(), program);

			if(getWhereExpression() != null){
				Objects = FilterWhere(Objects,program);
			}
			if(getSortExpression() != null && getSortDirection() != null){
				Objects = SortList(Objects,program);
			}
			setSelectedObjects(Objects);
			boolean[] ObjectsHandeled = new boolean[Objects.size()];
			setObjectsHandeled(ObjectsHandeled);
			
			FirstExecution = false;
			program.decreaseRemainingTime();
		}

		try{

			if(!isExecutionComplete()){
				int index = 0;
				boolean[] ObjectsHandeled = getObjectsHandeled();

				for(GameObject obj : getSelectedObjects()){
					if(!ObjectsHandeled[index]){
						program.getVariables().put(getVariableName(),obj);
						getForBody().advanceTime(dt, program);

						if(getForBody().isExecutionComplete()){
							ObjectsHandeled[index] = true;
							index ++;
							getForBody().Reset();
						}			
					}else{
						index ++;
					}
				}

				// CHECK IF ALL OBJECTS ARE CORRECTLY HANDELED -> FOR STATEMENT IS DONE
				if(CheckExecutionDone(ObjectsHandeled))
					ExecutionDone = true;

			}

		} catch(BreakException b){
			ExecutionDone = true;
		}
	}

	private boolean CheckExecutionDone(boolean[] objectsHandeled){
		for(boolean b : ObjectsHandeled){
			if(!b)
				return false;
		}
		return true;
	}

	private List<GameObject> ConstructAllVariablesWithKind(Kind VariableKind, Program program){
		World world = program.getGameObject().getWorld();

		Set<GameObject> Objects = new HashSet<GameObject>();
		switch (VariableKind) {
		case MAZUB:  
			Objects.add(world.getMazub());
			break;
		case BUZAM:
			Objects.add(world.getBuzam());
			break;
		case SLIME: 
			Objects.addAll(world.getAllSlimes());
			break;
		case SHARK:  
			Objects.addAll(world.getAllSharks());
			break;
		case PLANT: 
			Objects.addAll(world.getAllPlants());
			break;
		case TERRAIN:  /*getter = world.getTiles();*/
			break;
		case ANY: 
			Objects.addAll(world.getEachAndEveryObject());
		}
		List<GameObject> ObjectList = new ArrayList<GameObject>(Objects);
		return ObjectList;
	}

	private List<GameObject> FilterWhere(List<GameObject> Objects, Program program){
		Objects = Objects.stream().filter(o -> CommitObjectAndCheckWhere(o,program))
				.collect((Collectors.toCollection(ArrayList::new)));
		program.getVariables().put(getVariableName(),null);
		return Objects;
	}

	private Boolean CommitObjectAndCheckWhere(GameObject object, Program program){
		program.getVariables().put(getVariableName(),object);
		return (Boolean) getWhereExpression().evaluate(program);
	}


	private List<GameObject> SortList(List<GameObject> Objects, Program program){

		HashMap<GameObject,Double> map = new HashMap<>();
		for(GameObject obj: Objects){
			program.getVariables().put(getVariableName(),obj);
			map.put(obj,(Double) getSortExpression().evaluate(program));
		}
		program.getVariables().put(getVariableName(),null);

		if(getSortDirection() == jumpingalien.part3.programs.IProgramFactory.SortDirection.ASCENDING){
			Collections.sort(Objects, (o1, o2) -> map.get(o1).compareTo(map.get(o2)));
		}else if(getSortDirection() == jumpingalien.part3.programs.IProgramFactory.SortDirection.DESCENDING){
			Collections.sort(Objects, (o1, o2) -> map.get(o2).compareTo(map.get(o1)));
		}
		return Objects;
	}


	@Override
	public boolean isExecutionComplete() {
		return ForceReset || ExecutionDone;
	}

	@Override
	public void forceReset() {
		ForceReset = true;
	}

	@Override
	public void Reset() {
		ExecutionDone = false;
		FirstExecution = true;
		getForBody().Reset();
	}

	private String VariableName;
	private Kind VariableKind;
	private Expression<Boolean> WhereExpression;
	private Expression<Double> SortExpression;
	private SortDirection SortDirection;
	private Statement ForBody;

	private boolean[] ObjectsHandeled;
	private List<GameObject> SelectedObjects;
	private boolean ForceReset = false;
	private boolean ExecutionDone = false;	
	private boolean FirstExecution = true;

	private String getVariableName() {
		return VariableName;
	}

	private void setVariableName(String variableName) {
		VariableName = variableName;
	}

	private Kind getVariableKind(){
		return VariableKind;
	}

	private void setVariableKind(Kind variableKind){
		VariableKind = variableKind;
	}

	private Expression<Boolean> getWhereExpression() {
		return WhereExpression;
	}


	private void setWhereExpression(Expression<Boolean> whereExpression) {
		WhereExpression = whereExpression;
	}


	private Expression<Double> getSortExpression() {
		return SortExpression;
	}


	private void setSortExpression(Expression<Double> sortExpression) {
		SortExpression = sortExpression;
	}


	private SortDirection getSortDirection() {
		return SortDirection;
	}


	private void setSortDirection(SortDirection sortDirection) {
		SortDirection = sortDirection;
	}


	private Statement getForBody() {
		return ForBody;
	}


	private void setForBody(Statement forBody) {
		ForBody = forBody;
	}


	private boolean[] getObjectsHandeled() {
		return ObjectsHandeled;
	}


	private void setObjectsHandeled(boolean[] objectsHandeled) {
		ObjectsHandeled = objectsHandeled;
	}


	private List<GameObject> getSelectedObjects() {
		return SelectedObjects;
	}


	private void setSelectedObjects(List<GameObject> selectedObjects) {
		SelectedObjects = selectedObjects;
	}


	@Override
	public boolean isWellFormed(HashSet<String> parentStatements) {
		parentStatements.add("For");
		return getForBody().isWellFormed(parentStatements);
	}

}
