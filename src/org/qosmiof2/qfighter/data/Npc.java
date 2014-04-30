package org.qosmiof2.qfighter.data;

public enum Npc {

	Man("Man", 0);

	public String name;
	private int id;

	public String getName() {
		return name;
	}
	
	public int getId(){
		return id;
	}

	private Npc(String name, int id) {
		this.name = name;
		this.id = id;
	}

}
