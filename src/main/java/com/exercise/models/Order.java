package com.exercise.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Order {
	
	private List<OrderLineItem> lineItems = new ArrayList<>();
	private BigDecimal salesTaxes = new BigDecimal(0.00);
	private BigDecimal subTotal = new BigDecimal(0.00);
	private BigDecimal total = new BigDecimal(0.00);
	
	private final BigDecimal importedSalesTax;
	private final BigDecimal basicSalesTax;
		
	public Order(BigDecimal basicSalesTax, BigDecimal importedSalesTax, List<OrderLineItem> lineItems) {
		this.lineItems = lineItems;
		this.importedSalesTax = importedSalesTax;
		this.basicSalesTax = basicSalesTax;
	}
	
	/**
	 * Increment through each OrderLineItem and calculate the
	 * total for the line item, as well as compute the total
	 * sales taxes and final total for the Order itself.
	 */
	public void calculateTotalAndTaxes() {
		for (int i = 0; i < lineItems.size(); i++) {
			// Start ItemTaxes at $0.00 for each item initially
			BigDecimal itemTaxes = new BigDecimal(0.00);
			
			// Compute Intial Item Cost
			BigDecimal lineItemTotal = lineItems.get(i).getItem().getPrice();
			lineItemTotal = lineItemTotal.multiply(new BigDecimal(lineItems.get(i).getCount()));
			
			// Determine the type of the goods being sold and apply sales tax as appropriate
			ItemTypeEnum itemType = lineItems.get(i).getItem().getType();
			if (!itemType.equals(ItemTypeEnum.BOOK) &&
				!itemType.equals(ItemTypeEnum.FOOD) &&
				!itemType.equals(ItemTypeEnum.MEDICAL)) {
				
				itemTaxes = lineItemTotal.multiply(basicSalesTax);
			}
			
			// If an item is imported, apply the imported tax
			if (lineItems.get(i).getItem().isImported()) {
				itemTaxes = itemTaxes.add( lineItemTotal.multiply(importedSalesTax) );			
			}
			
			BigDecimal lineItemSubTotal = lineItems.get(i).getItem().getPrice();
			lineItemSubTotal = lineItemSubTotal.multiply(new BigDecimal(lineItems.get(i).getCount()));
			itemTaxes = itemTaxes.setScale(2, BigDecimal.ROUND_HALF_EVEN);
			
			/* Get the cents value for the item after taxes have been applied.
			 * Determine the number of pennies to add to to the item to makes its
			 * total divisible by $0.05
			 */
			BigDecimal pennies = itemTaxes.multiply(new BigDecimal(100));
			pennies = pennies.remainder(new BigDecimal(100));
			pennies = pennies.remainder(new BigDecimal(10));
			
			/*
			 * Determine the number of pennies needed
			 */
			BigDecimal roundUpDif = new BigDecimal(5);
			roundUpDif = roundUpDif.subtract(pennies);
			
			BigDecimal extraPennies = new BigDecimal(0);
			
			/*
			 * If the roundUpDif is between 0 & 5, use that value and divide it by 100.
			 * Take the result and add it line item's total taxes. 
			 * 
			 * If the value is less than 5, add 5 to offset negative
			 * numbers, and then divide by 100. Add the result to the items taxes.
			 */
			if (roundUpDif.intValue() > 0 && roundUpDif.intValue() < 5) {
				extraPennies = roundUpDif.divide(new BigDecimal(100));
			} else if (roundUpDif.intValue() < 0){
				roundUpDif = roundUpDif.add(new BigDecimal(5));
				extraPennies = roundUpDif.divide(new BigDecimal(100));
			} else {
				// do nothing
			}
			
			// Add the extra pennies to the line item taxes and set the value
			itemTaxes = itemTaxes.add(extraPennies);
			lineItems.get(i).setTaxes(itemTaxes);
			
			// Get the taxes of the line item and add the base total to receive the line item final total
			lineItemTotal = lineItems.get(i).getTaxes().add(lineItemSubTotal);
			lineItemTotal = lineItemTotal.setScale(2, BigDecimal.ROUND_HALF_EVEN);
			lineItems.get(i).setTotal(lineItemTotal);
			
			// Add the line item's taxes to the order's total taxes
			salesTaxes = salesTaxes.add(itemTaxes);
			
			// Add the lineItem's subTotal to the order's subTotal
			subTotal = subTotal.add(lineItemSubTotal);
		}
		
		// Set the precision of the Sales taxes to 2
		salesTaxes = salesTaxes.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		
		// Calculate the Order's total by adding the Order's SalesTaxes and SubTotal
		total = salesTaxes.add(subTotal);
		total = total.setScale(2, BigDecimal.ROUND_HALF_EVEN);
	}
	
	/**
	 * Returns a formatted string response of each line item in an order's output.
	 * @param lineItem The lineItem whose information is to be printed.
	 * @return String - Formatted string response of an outputted lineItem
	 */
	public String receiptPrintLineItem(OrderLineItem lineItem) {
		return lineItem.getCount() + " " + lineItem.getItem().getName() + ": " + lineItem.getTotal();
	}
	
	/**
	 * Returns a string response of the receipt for an order
	 * @return String - formatted receipt.
	 */
	public String printReceipt() {
		String response = new String();
		
		for (OrderLineItem lineItem : lineItems) {
			response = response + receiptPrintLineItem(lineItem) + "\n";
		}
		
		response = response + "Sales Taxes: " + salesTaxes + "\n";
		response = response + "Total: " + total;
		
		return response;
	}
	
	public void addLineItem(OrderLineItem lineItem) {
		if (lineItems == null) {
			lineItems = new ArrayList<>();
		}
		lineItems.add(lineItem);
	}
	
	public void removeLineItem(int index) {
		if (lineItems == null) {
			lineItems = new ArrayList<>();
		}
		if (lineItems.size() > index) {
			lineItems.remove(index);
		}
	}

	public List<OrderLineItem> getLineItems() {
		return lineItems;
	}
	public void setLineItems(List<OrderLineItem> lineItems) {
		this.lineItems = lineItems;
	}
	public BigDecimal getSalesTaxes() {
		return salesTaxes;
	}
	public void setSalesTaxes(BigDecimal salesTaxes) {
		this.salesTaxes = salesTaxes;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
}
