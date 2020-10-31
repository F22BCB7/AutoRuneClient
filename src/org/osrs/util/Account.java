package org.osrs.util;

public class Account {
	private String username;
	private String password;
	private boolean members;
	public Account(String user, String pass, boolean p2p){
		setUsername(user);
		setPassword(pass);
		setMembers(p2p);
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isMembers() {
		return members;
	}
	public void setMembers(boolean members) {
		this.members = members;
	}
	
}

