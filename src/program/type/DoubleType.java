package program.type;
 
public class DoubleType extends Type {

        /**
         *
         * @param value
         */
        public DoubleType(Double value){
        	setValue(value);
        }
        
        public DoubleType(){
        	setValue(getDefaultValue());
        }
        
        @Override
    	public Double getDefaultValue() {
    		return 0.0;
    	}
        
    	public void setValue(Double value){
    		this.value = value;
    	}
    	public Double getValue() {
    		 return value;
    	}
    	
    	public Integer getValueInt() {
    		Double value = getValue();
    		if (value > 0){
    			return (int) Math.floor(value);
    		}else if (value < 0){
    			return (int) Math.ceil(value);
    		}else{
    			return 0;
    		}
    	}
    	
    	private Double value;
    	
}



