package org.jorge2m.testmaker.domain.testfilter;

import java.util.List;

import org.jorge2m.testmaker.conf.Channel;

public class DataFilterTCases {

	private final Channel channel; 
	private final Enum<?> app;
	private List<String> groupsFilter;
	private List<String> testCasesFilter;
	
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
	public List<String> getTestCasesFilter() {
		return testCasesFilter;
	}
	public void setTestCasesFilter(List<String> testCasesFilter) {
		this.testCasesFilter = testCasesFilter;
	}
	public Channel getChannel() {
		return channel;
	}
	public Enum<?> getApp() {
		return app;
	}
	
	public boolean isSomeFilterActive() {
		return (
			isActiveFilterByTestCases() ||
			isActiveFilterByGroups()
		);
	}
	
    public boolean isActiveFilterByTestCases() {
    	return (
    		testCasesFilter!=null && 
    		testCasesFilter.size()!=0 && 
    		"*".compareTo(testCasesFilter.get(0))!=0
    	);
    }
    
    public boolean isActiveFilterByGroups() {
    	return (
    		groupsFilter!=null && 
    		groupsFilter.size()!=0 && 
    		"*".compareTo(groupsFilter.get(0))!=0
    	);
    }
}