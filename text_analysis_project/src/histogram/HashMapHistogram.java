package il.ac.tau.cs.sw1.ex8.histogram;

import java.util.*;

/**************************************
 *  Add your code to this class !!!   *
 **************************************/
public class HashMapHistogram<T extends Comparable<T>> implements IHistogram<T>{

	public HashMap<T,Integer> histogram;

	public HashMapHistogram(){
		super();
		histogram = new HashMap<>(){
		};
	}
	
	@Override
	public void addItem(T item) {
		if (item != null) {
			if (histogram.containsKey(item)) {
				int counter = histogram.get(item)+1;
				histogram.replace(item,counter);
			}
			else
				histogram.put(item,1);
		}
	}
	@Override
	public boolean removeItem(T item)  {
		if(histogram.containsKey(item)==false)
			return true;
		int counter = histogram.get(item)-1;
		if (counter <=0){
			histogram.remove(item);
			return true;
		}
		histogram.replace(item,counter);
		return false; //replace this with the correct value
	}
	
	@Override
	public void addAll(Collection<T> items) {
		for (T item : items) {
			if (histogram.containsKey(item)==true) {
				int prev_count = histogram.get(item);
				histogram.replace(item,prev_count+1);
			}
			else
				histogram.put(item,1);
		}
	}

	@Override
	public int getCountForItem(T item) {
		if (item!=null)
			if (histogram.containsKey(item))
				return histogram.get(item);
		return 0;
	}

	@Override
	public void clear() {
		histogram.clear();
	}
	@Override
	public Set<T> getItemsSet() {
		return histogram.keySet();
	}
	
	@Override
	public int getCountsSum() {
		Collection<Integer> values = histogram.values();
		int sum = 0;
		if (values.size()>0)
			for (int val :values) {
				sum += val;
			}
		return sum;
	}

	@Override
	public Iterator<Map.Entry<T, Integer>> iterator() {
		return new HashMapHistogramIterator<>(histogram);
	}
	
	//add private methods here, if needed
}
