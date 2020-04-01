package org.aubay.testmaker.domain.testfilter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PermutationsUtil {
	
	public static <E> List<List<E>> getPermutations(int maxSizePermutations, List<E> inputElements) {
		List<List<E>> result = new ArrayList<>();
		List<List<E>> combinations = getCombinations(maxSizePermutations, inputElements);
		for (List<E> combination : combinations) {
			result.addAll(getPermutations(combination));
		}
		return result;
	}
	
	public static <E> List<List<E>> getCombinations(int maxSizePermutations, List<E> inputElements) {
		List<List<E>> listCombinations = new ArrayList<>();
		CombinationIterable<E> combinationIterable = new CombinationIterable<>(inputElements);
        for (List<E> combination : combinationIterable)  {
        	if (combination.size()<=maxSizePermutations && combination.size()<=inputElements.size()) {
        		listCombinations.add(combination);
        	} else {
        		break;
        	}
        }
        return listCombinations;
	}
	
	private static <E> List<List<E>> getPermutations(List<E> inputElements) {
		List<List<E>> result = new ArrayList<>();
		permutationsRecursive(0, inputElements, result);
		return result;
	}
	
	private static <E> void permutationsRecursive(int start, List<E> elements, List<List<E>> result) {
	    if(start==elements.size()-1){
	        ArrayList<E> list = new ArrayList<>();
	        for(E element: elements){
	            list.add(element);
	        }
	        result.add(list);
	        return;
	    }
	 
	    for(int i=start; i<elements.size(); i++) {
	    	Collections.swap(elements, i, start);
	    	permutationsRecursive(start+1, elements, result);
	    	Collections.swap(elements, i, start);
	    }
	}
}
