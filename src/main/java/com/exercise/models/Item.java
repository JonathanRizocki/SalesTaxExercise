package com.exercise.models;

import java.math.BigDecimal;

public class Item {
	
	private String name;
	private ItemTypeEnum type;
	private BigDecimal price;
	private boolean imported;
	
	public Item() {
		name = "";
		type = ItemTypeEnum.OTHER;
		price = new BigDecimal(0.0);
		imported = false;
	}

	/**
	 * Create a new item to be added to as part of an Order's line item.
	 * @param name String - Item Name
	 * @param type ItemTypeEnum - Item Type
	 * @param price BigDecial - Item price
	 * @param imported Boolean - item imported = true. Not imported = false
	 */
	public Item(String name, ItemTypeEnum type, BigDecimal price, boolean imported) {
		
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Name must be a non-empty string.");
		}
		
		if (type == null) {
			throw new IllegalArgumentException("ItemType cannot be null.");
		}
		
		if (price == null) {
			throw new IllegalArgumentException("Price must be greater than 0");
		} else if (price.signum() == -1 || price.signum() == 0) {
			throw new IllegalArgumentException("Price must be greater than 0");
		}
		
		this.name = name;
		this.type = type;
		this.price = price;
		this.imported = imported;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Name must be a non-empty string.");
		}
		this.name = name;
	}
	public ItemTypeEnum getType() {
		return type;
	}
	public void setType(ItemTypeEnum type) {
		if (type == null) {
			throw new IllegalArgumentException("ItemType cannot be null.");
		}
		this.type = type;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		if (price == null) {
			throw new IllegalArgumentException("Price must be greater than 0");
		} else if (price.signum() == -1 || price.signum() == 0) {
			throw new IllegalArgumentException("Price must be greater than 0");
		}
		this.price = price;
	}
	public boolean isImported() {
		return imported;
	}
	public void setImported(boolean imported) {
		this.imported = imported;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (imported ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (imported != other.imported)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Item [name=" + name + ", type=" + type + ", price=" + price + ", imported=" + imported + "]";
	}
	
}
	
	