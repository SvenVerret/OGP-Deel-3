import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class Experiments {
	public static void main(String[] args) {
		List<Integer> obj = new ArrayList<>(Arrays.asList(1,2,3,4,5));
		System.out.println(obj);
		obj.stream().filter((t-> t%2 == 0))
		
		System.out.println(obj);
		obj.stream().forEach(o -> System.out.println(o));
		
		//Stream<Integer> filteredstream = stream.;
	}
}
