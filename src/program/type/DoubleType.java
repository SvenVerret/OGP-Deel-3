package program.type;
 
public class DoubleType extends Type {

 
        /**
         *
         * @param value
         */
        public DoubleType(Double e){
                value = e;
        }
       
 
 
        @Override
        public Double getValue() {
                return value;
        }
       
        private final Double value;
}


