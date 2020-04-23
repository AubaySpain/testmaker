package com.github.jorge2m.testmaker.testreports.browserstack;

public class BuildAutomation {

	String name;
	String duration;
	String status;
	String hashed_id;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getHashed_id() {
		return hashed_id;
	}
	public void setHashed_id(String hashed_id) {
		this.hashed_id = hashed_id;
	}

}
