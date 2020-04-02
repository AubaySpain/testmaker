package org.jorge2m.testmaker.domain.testfilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jorge2m.testmaker.conf.Channel;

public class GroupsChannelApps {
	
	private final Channel channelTest;
	private final Enum<?> app;
	private final List<List<Channel>> permutationsChannels;
	private final List<List<Enum<?>>> permutationsApps;
	private final List<ChannelsAppsDupla> excludedCombinations = new ArrayList<>();
	private final List<ChannelsAppsDupla> includedCombinations = new ArrayList<>();
	
	private GroupsChannelApps(Channel channel, Enum<?> app) {
		this.channelTest = channel;
		this.app = app;
		List<Channel> listChannels = Arrays.asList(Channel.values());
		List<Enum<?>> listApps = Arrays.asList(app.getDeclaringClass().getEnumConstants());
		permutationsChannels = PermutationsUtil.getPermutations(listChannels.size(), listChannels);
		permutationsApps = PermutationsUtil.getPermutations(listApps.size(), listApps);
		setCombinationsIncludedExcluded();
	}
	
	public static GroupsChannelApps getNew(Channel channel, Enum<?> app) {
		return (new GroupsChannelApps(channel, app));
	}
	
	public List<String> getGroupsExcluded() {
		return (getGroupsFromCombinations(excludedCombinations));
	}
	
	public List<String> getGroupsIncluded() {
		return (getGroupsFromCombinations(includedCombinations));
	}
	
	private List<String> getGroupsFromCombinations(List<ChannelsAppsDupla> listCombinations) {
		List<String> groupsReturn = new ArrayList<>();
		for (ChannelsAppsDupla combination : listCombinations) {
			String listChannelsStr = combination.getChannels().toString().replace("[", "").replace("]", "").replace(" ", "");
			String listAppsStr = combination.getApps().toString().replace("[", "").replace("]", "").replace(" ", "");
			groupsReturn.add("Canal:" + listChannelsStr + "_App:" + listAppsStr);
			if (combination.getChannels().size()==Channel.values().length) {
				groupsReturn.add("Canal:all_App:" + listAppsStr);
			}
			if (combination.getApps().size()==app.getDeclaringClass().getEnumConstants().length) {
				groupsReturn.add("Canal:" + listChannelsStr + "_App:all");
			}
			if (combination.getChannels().size()==Channel.values().length &&
				combination.getApps().size()==app.getDeclaringClass().getEnumConstants().length) {
				groupsReturn.add("Canal:all_App:all");
			}
		}
		return groupsReturn;
	}
	
	private void setCombinationsIncludedExcluded() {
		for (List<Channel> permutationChannels : permutationsChannels) {
			for (List<Enum<?>> permutationApps : permutationsApps) {
				ChannelsAppsDupla dupla = new ChannelsAppsDupla(permutationChannels, permutationApps);
				if (!permutationChannels.contains(channelTest) ||
					!permutationApps.contains(app)) {
					excludedCombinations.add(dupla);
				} else {
					includedCombinations.add(dupla);
				}
				
			}
		}
	}
	
//  public List<String> getListGroupsToInclude() {
//	  ArrayList<String> listOfGroups = new ArrayList<>();
//	  Channel channel = dFilter.getChannel();
//	  AppTest app = dFilter.getAppE();
//	  listOfGroups.add("Canal:all_App:all");
//	  listOfGroups.add("Canal:all_App:" + app);
//	  listOfGroups.add("Canal:" + channel + "_App:all");
//	  listOfGroups.add("Canal:" + channel + "_App:" + app);
//	  return listOfGroups;
//  }
	
	private class ChannelsAppsDupla {
		private final List<Channel> channels;
		private final List<Enum<?>> apps;
		
		public ChannelsAppsDupla(List<Channel> channels, List<Enum<?>> apps) {
			this.channels = channels;
			this.apps = apps;
		}
		
		public List<Channel> getChannels() {
			return this.channels;
		}
		
		public List<Enum<?>> getApps() {
			return this.apps;
		}
	}
}
