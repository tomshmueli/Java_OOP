package il.ac.tau.cs.sw1.ex8.histogram;

import java.util.*;


/**************************************
 *  Add your code to this class !!!   *
 **************************************/

public class HashMapHistogramIterator<T extends Comparable<T>> 
							implements Iterator<Map.Entry<T, Integer>>{
	
	private Set<Map.Entry<T,Integer>> hist_set;
	
	public HashMapHistogramIterator(HashMap<T,Integer> histogram){
		hist_set = histogram.entrySet();
	}
	
	@Override
	public boolean hasNext() {
		return hist_set.iterator().hasNext();
	}

	@Override
	public Map.Entry<T, Integer> next() {
		Map.Entry<T, Integer> next = hist_set.iterator().next();
		hist_set.remove(next);
		return next;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
	
	Comparator<T> comparator = new Comparator<T>() {
		@Override
		public int compare(T o1, T o2) {
			return o1.compareTo(o2);
		}
	};


}
