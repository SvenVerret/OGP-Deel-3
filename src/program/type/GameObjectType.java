package program.type;
 
import program.expression.Expression;
import jumpingalien.model.GameObject;
 
public class GameObjectType<T extends GameObject> extends Type{
    
    	@Override
    	public Expression<?> getDefaultValue() {
    		return null;
    	}
}

