/*
 * Copyright (c) 2018 Nimbly Games, LLC all rights reserved
 */

package steam;

public class SteamFileNameAndSize {
	private String name;
	private int size;
	
	public SteamFileNameAndSize(String name, int size) {
		super();
		this.name = name;
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public int getSize() {
		return size;
	}

	public String toString() {
		return "SteamFileNameAndSize [name=" + name + ", size=" + size + "]";
	}
}
