package org.jorge2m.testmaker.domain.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RoundRobin<T> implements Iterable<T> {

	private final List<T> collection = new ArrayList<>();
	private int index = 0;
	
	public int size() {
		return collection.size();
	}
	
	public boolean add(T item) {
		if (!collection.contains(item)) {
			collection.add(item);
			return true;
		}
		return false;
	}
	
	public void remove(T item) {
		collection.remove(item);
	}
	
	public T next() {
		T res = collection.get(index);
		index = (index + 1) % collection.size();
		return res;
	}

	@Override
	public Iterator<T> iterator() { 
		return new Iterator<T>() {

			@Override
			public boolean hasNext() {
				return true;
			}

			@Override
			public T next() {
				return RoundRobin.this.next();
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
}
