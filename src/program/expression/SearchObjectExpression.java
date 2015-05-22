package program.expression;
 
import java.util.Comparator;
import java.util.Set;

import program.Program;
import jumpingalien.model.GameObject;
import jumpingalien.part3.programs.IProgramFactory.Direction;
import jumpingalien.part3.programs.SourceLocation;
 
 
public class SearchObjectExpression extends Expression<SourceLocation>{
 
        /**
         *
         * @param direction
         *  
         */
        public SearchObjectExpression(Expression<Direction> direction, SourceLocation sourceLocation){
                super(sourceLocation);
                dir = direction;
        }
 
        public Expression<Direction> getDirection(){
                return dir;
        }
       
        @Override
        public Object evaluate(Program program) {
               
                GameObject gameObject = program.getGameObject();
                Set<GameObject> GameObjectsWorld = program.getGameObject().getWorld().getEachAndEveryObject();
                //set nog uitbreiden met tiles
               
                final Comparator<GameObject> compX = (p1, p2) ->
                Double.compare(p1.getPos().getElemx(),p2.getPos().getElemx());
               
                final Comparator<GameObject> compY = (p1, p2) ->
                Double.compare(p1.getPos().getElemy(),p2.getPos().getElemy());
                
                Direction direction = (Direction) getDirection().evaluate(program);
 
                switch(direction){
               
                case DOWN:
                        GameObject result1 = GameObjectsWorld.stream().
                        filter(e ->e.getPos().getElemy()< gameObject.getPos().getElemy() &&
                                        gameObject.overlapsWithX(e)).min(compY).get();
                        return result1;
                case UP:
                        GameObject result2 = GameObjectsWorld.stream().
                        filter(e ->e.getPos().getElemy()> gameObject.getPos().getElemy() &&
                        gameObject.overlapsWithX(e)).min(compY).get();
                        return result2;
                case RIGHT:
                        GameObject result3 = GameObjectsWorld.stream().
                        filter(e ->e.getPos().getElemx()< gameObject.getPos().getElemx() &&
                                        gameObject.overlapsWithY(e)).min(compX).get();
                        return result3;
                case LEFT:
                        GameObject result4 = GameObjectsWorld.stream().
                        filter(e ->e.getPos().getElemx()< gameObject.getPos().getElemx() &&
                                        gameObject.overlapsWithY(e)).min(compX).get();
                        return result4;
                default:
                        return null;
 
                }
 
        }
        
        private Expression<Direction> dir;
       
}
