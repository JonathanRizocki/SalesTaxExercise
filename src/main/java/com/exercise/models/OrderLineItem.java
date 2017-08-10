package com.exercise.models;

import java.math.BigDecimal;

public class OrderLineItem {
	
	private int count;
	private Item item;
	private BigDecimal taxes;
	private BigDecimal total;
	
	/**
	 * @param count
	 * @param item
	 * @param taxes
	 * @param total
	 */
	public OrderLineItem(int count, Item item) {
		
		if (item == null) {
			throw new IllegalArgumentException("Item cannot be null");
		}
		
		if (count < 1) {
			throw new IllegalArgumentException("Item Count must be greater than 0");
		}
		
		this.count = count;
		this.item = item;
		this.taxes = new BigDecimal(0.0);
		this.total = new BigDecimal(0.0);
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public BigDecimal getTaxes() {
		return taxes;
	}

	public void setTaxes(BigDecimal taxes) {
		this.taxes = taxes;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + count;
		result = prime * result + ((item == null) ? 0 : item.hashCode());
		result = prime * result + ((taxes == null) ? 0 : taxes.hashCode());
		result = prime * result + ((total == null) ? 0 : total.hashCode());
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
		OrderLineItem other = (OrderLineItem) obj;
		if (count != other.count)
			return false;
		if (item == null) {
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;
		if (taxes == null) {
			if (other.taxes != null)
				return false;
		} else if (!taxes.equals(other.taxes))
			return false;
		if (total == null) {
			if (other.total != null)
				return false;
		} else if (!total.equals(other.total))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OrderLineItem [count=" + count + ", item=" + item + ", taxes=" + taxes + ", total=" + total + "]";
	}
	
	
	
	
}
