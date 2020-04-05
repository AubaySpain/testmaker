package com.github.jorge2m.testmaker.utils.filter;

import static org.junit.Assert.*;
import java.util.List;

import org.junit.Test;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.domain.testfilter.GroupsChannelApps;
import com.github.jorge2m.testmaker.unittestdata.AppEcom;

public class GroupsChannelAppsTest {

	@Test
	public void getGroupsExcludedTest() {
		GroupsChannelApps groups = GroupsChannelApps.getNew(Channel.desktop, AppEcom.shop);
		
		List<String> groupsExcluded = groups.getGroupsExcluded();
		List<String> groupsIncluded = groups.getGroupsIncluded();
		
		assertTrue(groupsExcluded.contains("Canal:desktop_App:outlet"));
		assertTrue(groupsExcluded.contains("Canal:desktop_App:outlet,votf"));
		assertTrue(groupsExcluded.contains("Canal:desktop_App:votf,outlet"));
		assertTrue(groupsExcluded.contains("Canal:movil_web_App:shop"));
		assertTrue(groupsExcluded.contains("Canal:movil_web_App:all"));
		
		assertTrue(!groupsExcluded.contains("Canal:desktop_App:shop"));
		assertTrue(!groupsExcluded.contains("Canal:all_App:shop"));
		assertTrue(!groupsExcluded.contains("Canal:desktop_App:all"));
		assertTrue(!groupsExcluded.contains("Canal:all_App:all"));
		
		assertTrue(groupsIncluded.contains("Canal:desktop_App:shop"));
		assertTrue(groupsIncluded.contains("Canal:all_App:shop"));
		assertTrue(groupsIncluded.contains("Canal:desktop_App:all"));
		assertTrue(groupsIncluded.contains("Canal:all_App:all"));
	}
}
