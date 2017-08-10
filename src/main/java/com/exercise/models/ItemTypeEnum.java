package com.exercise.models;

public enum ItemTypeEnum {
	BOOK("book"),
	FOOD("food"),
	MEDICAL("medical"),
	OTHER("other");
	
	private final String displayName;
	
	ItemTypeEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
