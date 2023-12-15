package com.github.jorge2m.testmaker.domain.testfilter;

import java.util.List;

import com.github.jorge2m.testmaker.conf.Channel;

public class DataFilterTCases {

	private final Channel channel; 
	private final Enum<?> app;
	private List<String> groupsFilter;
	private List<String> testCasesIncludedFilter;
	private List<String> testCasesExcludeFilter;
	
	public DataFilterTCases(Channel channel, Enum<?> app) {
		this.channel = channel;
		this.app = app;
	}
	
	public List<String> getGroupsFilter() {
		return groupsFilter;
	}
	public void setGroupsFilter(List<String> groupsFilter) {
		this.groupsFilter = groupsFilter;
	}
	public List<String> getTestCasesIncludedFilter() {
		return testCasesIncludedFilter;
	}
	public void setTestCasesIncludedFilter(List<String> testCasesIncludedFilter) {
		this.testCasesIncludedFilter = testCasesIncludedFilter;
	}
	public List<String> getTestCasesExcludedFilter() {
		return testCasesExcludeFilter;
	}
	public void setTestCasesExcludedFilter(List<String> testCasesExcludeFilter) {
		this.testCasesExcludeFilter = testCasesExcludeFilter;
	}	
	public Channel getChannel() {
		return channel;
	}
	public Enum<?> getApp() {
		return app;
	}
	
	public boolean isSomeFilterActive() {
		return (
			isActiveFilterByTestCasesIncluded() ||
			isActiveFilterByTestCasesExcluded() ||
			isActiveFilterByGroups()
		);
	}
	
    public boolean isActiveFilterByTestCasesIncluded() {
    	return (
    		testCasesIncludedFilter!=null && 
    		!testCasesIncludedFilter.isEmpty() && 
    		"*".compareTo(testCasesIncludedFilter.get(0))!=0
    	);
    }
    
    public boolean isActiveFilterByTestCasesExcluded() {
    	return (
    		testCasesExcludeFilter!=null && 
    		!testCasesExcludeFilter.isEmpty() && 
    		"*".compareTo(testCasesExcludeFilter.get(0))!=0
    	);
    }
    
    public boolean isActiveFilterByGroups() {
    	return (
    		groupsFilter!=null && 
    		!groupsFilter.isEmpty() && 
    		"*".compareTo(groupsFilter.get(0))!=0
    	);
    }
}