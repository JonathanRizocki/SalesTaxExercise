package com.exercise.application;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.exercise.models.Item;
import com.exercise.models.ItemTypeEnum;
import com.exercise.models.Order;
import com.exercise.models.OrderLineItem;

public class Application {
	
	public static void main(String[] args) {
		Item book = new Item("Book", ItemTypeEnum.BOOK, new BigDecimal(12.49), false);
		Item musicCd = new Item ("music CD", ItemTypeEnum.OTHER, new BigDecimal(14.99), false);
		Item chocolateBar = new Item ("chocolate bar", ItemTypeEnum.FOOD, new BigDecimal(0.85), false);
		
		List<OrderLineItem> lineItems = new ArrayList<>();
		lineItems.add(new OrderLineItem(1, book));
		lineItems.add(new OrderLineItem(1, musicCd));
		lineItems.add(new OrderLineItem(1, chocolateBar));
		
		Order order = new Order(new BigDecimal(0.10), new BigDecimal(0.05), lineItems);
		order.calculateTotalAndTaxes();
		
		System.out.println(order.printReceipt());
		
		System.out.println("\n\nPlese run CheckoutTest.java in the src/main/test source folder for unit test demonstration.");
		
	}

}
