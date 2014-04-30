package org.qosmiof2.qfighter.data;

public enum Food {
	Shrimp("Shrimp", 315),
	Beef("Beef", 2142),
	Chicken("Chicken", 2140),
	Crayfish("Crayfish", 13433),
	Anchovies("Anchovies", 319),
	Sardine("Sardine", 325),
	Salmon("Salmon", 331), 
	Trout("Trout", 333), 
	Cod("Cod", 339), 
	Herring("Herring", 347), 
	Pike("Pike", 351), 
	Tuna("Tuna", 361), 
	Lobster("Lobster", 379), 
	Shark("Shark", 385), 
	Swordfish("Swordfish", 373);
	
	private final String name;
	private final int cookedId;

	private Food(String name, int cookedId) {
		this.name = name;
		this.cookedId = cookedId;
	}
	
	public int getCookedId() {
		return cookedId;
	}

}
