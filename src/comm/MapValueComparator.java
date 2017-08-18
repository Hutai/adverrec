package comm;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;

public class MapValueComparator implements Comparator<Map.Entry<Integer, Double>> {

	@Override
	public int compare(Entry<Integer, Double> me1, Entry<Integer, Double> me2) {

		return me2.getValue().compareTo(me1.getValue());
	}
}