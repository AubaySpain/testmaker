package org.aubay.testmaker.utils.filter;

import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;

import org.aubay.testmaker.domain.testfilter.PermutationsUtil;
import org.aubay.testmaker.unittestdata.AppEcom;
import org.junit.Test;

public class PermutationsUtilTest {

	private static List<AppEcom> listApps = Arrays.asList(AppEcom.shop, AppEcom.outlet, AppEcom.votf);
	
	@Test
	public void getPermutationsTest() {
		int maxSizeCombinations = 2;
		List<List<AppEcom>> permutations = PermutationsUtil.getPermutations(maxSizeCombinations, listApps);
		
		assertTrue(permutations.size()==9);
		assertTrue(isContained(permutations, Arrays.asList(AppEcom.shop)));
		assertTrue(isContained(permutations, Arrays.asList(AppEcom.outlet)));
		assertTrue(isContained(permutations, Arrays.asList(AppEcom.votf)));
		assertTrue(isContained(permutations, Arrays.asList(AppEcom.shop, AppEcom.outlet)));
		assertTrue(isContained(permutations, Arrays.asList(AppEcom.shop, AppEcom.votf)));
		assertTrue(isContained(permutations, Arrays.asList(AppEcom.outlet, AppEcom.shop)));
		assertTrue(isContained(permutations, Arrays.asList(AppEcom.outlet, AppEcom.votf)));
		assertTrue(isContained(permutations, Arrays.asList(AppEcom.votf, AppEcom.shop)));
		assertTrue(isContained(permutations, Arrays.asList(AppEcom.votf, AppEcom.outlet)));
	}
	
	@Test
	public void getCombinationsTest() {
		int maxSizeCombinations = 2;
		List<List<AppEcom>> combinations = PermutationsUtil.getCombinations(maxSizeCombinations, listApps);
		
		assertTrue(combinations.size()==6);
		assertTrue(isContained(combinations, Arrays.asList(AppEcom.shop)));
		assertTrue(isContained(combinations, Arrays.asList(AppEcom.outlet)));
		assertTrue(isContained(combinations, Arrays.asList(AppEcom.votf)));
		assertTrue(isContained(combinations, Arrays.asList(AppEcom.shop, AppEcom.outlet)));
		assertTrue(isContained(combinations, Arrays.asList(AppEcom.shop, AppEcom.votf)));
		assertTrue(isContained(combinations, Arrays.asList(AppEcom.outlet, AppEcom.votf)));
	}
	
	private <E> boolean isContained(List<List<E>> listOfLists, List<E> listExpected) {
		for (List<E> list : listOfLists) {
			if (areEqual(list, listExpected)) {
				return true;
			}
		}
		return false;
	}
	
	private <E> boolean areEqual(List<E> combination1, List<E> combination2) {
		if (combination1.size()!=combination2.size()) {
			return false;
		}
		for (int i=0; i<combination1.size(); i++) {
			if (combination1.get(i)!=combination2.get(i)) {
				return false;
			}
		}
		
		return true;
	}
}
