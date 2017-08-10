package com.exercise.application;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.exercise.models.Item;
import com.exercise.models.ItemTypeEnum;
import com.exercise.models.Order;
import com.exercise.models.OrderLineItem;

public class CheckoutTest {
	
	private List<OrderLineItem> lineItems;
	private final BigDecimal importedSalesTax = new BigDecimal(0.05);
	private final BigDecimal basicSalesTax = new BigDecimal(0.10);
	
	@Before
	public void setup() {
		lineItems = new ArrayList<>();
	}

	@Test
	public void input1() {
		Item book = new Item("Book", ItemTypeEnum.BOOK, new BigDecimal(12.49), false);
		Item musicCd = new Item ("music CD", ItemTypeEnum.OTHER, new BigDecimal(14.99), false);
		Item chocolateBar = new Item ("chocolate bar", ItemTypeEnum.FOOD, new BigDecimal(0.85), false);
		
		lineItems.add(new OrderLineItem(1, book));
		lineItems.add(new OrderLineItem(1, musicCd));
		lineItems.add(new OrderLineItem(1, chocolateBar));
		
		Order order = new Order(basicSalesTax, importedSalesTax, lineItems);
		order.calculateTotalAndTaxes();
		
		String response = order.printReceipt();
		String expectedResponse = "1 Book: 12.49\n" +
			"1 music CD: 16.49\n" +
			"1 chocolate bar: 0.85\n" +
			"Sales Taxes: 1.50\n" +
			"Total: 29.83";
		
		System.out.println("Input1 Test: \n" + response);
		assertEquals(new BigDecimal(1.50).setScale(2, BigDecimal.ROUND_HALF_EVEN), order.getSalesTaxes());
		assertEquals(new BigDecimal(29.83).setScale(2, BigDecimal.ROUND_HALF_EVEN), order.getTotal());
		assertEquals(expectedResponse, response);
	}
	
	@Test
	public void input2() {
		Item importedChocolates = new Item("imported box of chocolates", ItemTypeEnum.FOOD, new BigDecimal(10.00), true);
		Item importedPerfume = new Item ("imported bottle of perfume", ItemTypeEnum.OTHER, new BigDecimal(47.50), true);
		
		lineItems.add(new OrderLineItem(1, importedChocolates));
		lineItems.add(new OrderLineItem(1, importedPerfume));
		
		Order order = new Order(basicSalesTax, importedSalesTax, lineItems);
		order.calculateTotalAndTaxes();
		
		String response = order.printReceipt();
		String expectedResponse = "1 imported box of chocolates: 10.50\n" +
			"1 imported bottle of perfume: 54.65\n" +
			"Sales Taxes: 7.65\n" +
			"Total: 65.15";
		
		System.out.println("\nInput2 Test: \n" + response);
		assertEquals(new BigDecimal(7.65).setScale(2, BigDecimal.ROUND_HALF_EVEN), order.getSalesTaxes());
		assertEquals(new BigDecimal(65.15).setScale(2, BigDecimal.ROUND_HALF_EVEN), order.getTotal());
		assertEquals(expectedResponse, response);
	}
	
	@Test
	public void input3() {
		Item importedPerfume = new Item("imported bottle of perfume", ItemTypeEnum.OTHER, new BigDecimal(27.99), true);
		Item perfume = new Item ("bottle of perfume", ItemTypeEnum.OTHER, new BigDecimal(18.99), false);
		Item headachePills= new Item ("packet of headache pills", ItemTypeEnum.MEDICAL, new BigDecimal(9.75), false);
		Item importedChocolates = new Item("imported box of chocolates", ItemTypeEnum.FOOD, new BigDecimal(11.25), true);
		
		lineItems.add(new OrderLineItem(1, importedPerfume));
		lineItems.add(new OrderLineItem(1, perfume));
		lineItems.add(new OrderLineItem(1, headachePills));
		lineItems.add(new OrderLineItem(1, importedChocolates));
		
		Order order = new Order(basicSalesTax, importedSalesTax, lineItems);
		order.calculateTotalAndTaxes();
		
		String response = order.printReceipt();
		String expectedResponse = "1 imported bottle of perfume: 32.19\n"+
				"1 bottle of perfume: 20.89\n"+
				"1 packet of headache pills: 9.75\n"+
				"1 imported box of chocolates: 11.85\n"+
				"Sales Taxes: 6.70\n"+
				"Total: 74.68";
		
		System.out.println("\nInput3 Test: \n" + response);
		assertEquals(new BigDecimal(6.70).setScale(2, BigDecimal.ROUND_HALF_EVEN), order.getSalesTaxes());
		assertEquals(new BigDecimal(74.68).setScale(2, BigDecimal.ROUND_HALF_EVEN), order.getTotal());
		assertEquals(expectedResponse, response);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNegativeItemPrice() {
	    Item item = new Item("test", ItemTypeEnum.BOOK, new BigDecimal(-50.0), false);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testEmptyOrderItemList() {
	    Order order = new Order(basicSalesTax, importedSalesTax, new ArrayList<OrderLineItem>());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNonPositiveTax() {
		Item importedPerfume = new Item("imported bottle of perfume", ItemTypeEnum.OTHER, new BigDecimal(27.99), true);
		lineItems.add(new OrderLineItem(1, importedPerfume));
	    Order order = new Order(new BigDecimal(0.00), new BigDecimal(-0.05), lineItems);
	}

}
