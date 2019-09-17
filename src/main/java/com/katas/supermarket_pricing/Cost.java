package com.katas.supermarket_pricing;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * 
 * @author liban {@link CostTest}
 *
 */
public class Cost {

	public static final BigDecimal ONE_POUND_TO_OUNCES = BigDecimal.valueOf(16);
	public static final BigDecimal ONE_POUND_TO_DOLLAR = BigDecimal.valueOf(1.99);
	private Item item;
	/** quantity of a package of an item */
	private int packageAmount;
	/** price of an package of a item */
	private BigDecimal packagePrice;
	/** amount bought of the same items*/
	private int currentAmount;
	/** price of the amount bought of the same items*/
	private BigDecimal totalCost;

	
	private Cost(Item item, int packageAmount, BigDecimal packagePrice, int currentAmount, BigDecimal totalCost) {
		super();
		this.item = item;
		this.packageAmount = packageAmount;
		this.packagePrice = packagePrice;
		this.currentAmount = currentAmount;
		this.totalCost = totalCost;
	}
	
	public Item getItem() {
		return item;
	}
	
	public static Cost zeroCost() {
		return new Cost(null, 0, BigDecimal.ZERO, 0, BigDecimal.ZERO);
	}

	public static Cost createSimpleItemCost(Item item) {
		return new Cost(item, 1, item.getPrice(), 1, item.getPrice());
	}

	public static Cost createItemsByPackagePrice(Item item, int packageAmount, BigDecimal packagePrice) {
		return new Cost(item, packageAmount, packagePrice, packageAmount, packagePrice);
	}

	public int getPackageAmount() {
		return packageAmount;
	}


	public BigDecimal getPackagePrice() {
		return packagePrice;
	}


	public int getCurrentAmount() {
		return currentAmount;
	}

	public Cost setCurrentAmount(int currentAmount) {
		this.currentAmount = currentAmount;
		return this;
	}

	public BigDecimal getTotalCost() {
		return totalCost;
	}

	public Cost setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
		return this;
	}

	/**
	 * Calculate Cost of 
	 * @param amount
	 * @return Cost
	 */
	public Cost addSameItem(int amount ) {
		this.setCurrentAmount(this.getCurrentAmount() + amount);
		Integer packageAmount = Stream.iterate(this.getCurrentAmount(), x-> x-1)
			.filter(n-> n % this.getPackageAmount() == 0)
			.findFirst()
			.orElse(0);
		return Optional.of(this)
				.map(c -> calculateComplexItem(packageAmount))
				.map(c-> calculateSimpleItem(this.getCurrentAmount() - packageAmount))
				.orElse(this);
	}
	/**
	 * Calculate cost of each 2 of the same items bought, the third is free
	 * @param item
	 * @param amount
	 * @return Cost
	 */
	public Cost addSamePromotedItem(Item item, int amount) {
		this.setCurrentAmount(amount + this.getCurrentAmount());
		int amountOfPromotedItems = Stream.iterate(this.getCurrentAmount(), x -> x - 1)
				.filter(n -> n % 3 == 0)
				.findFirst()
				.orElse(0);
		return Optional.of(this)
				.map(cost-> calculatePromotedItemCost(item, amountOfPromotedItems))
				.map(cost-> calculateCostWithoutDiscount(item, this.getCurrentAmount() - amountOfPromotedItems))
				.orElseGet(()-> this);
				
		
	}
	
	public BigDecimal convertOuncesToDollars(BigDecimal dollars) {
		return Optional.of(dollars).filter(d -> d.compareTo(BigDecimal.valueOf(0)) == 1)
				.map(d-> d.multiply(ONE_POUND_TO_DOLLAR).divide(ONE_POUND_TO_OUNCES))
				.orElseThrow(() -> new IllegalArgumentException(String
						.format("The ounces values [%.0f] must not be negative or zero", dollars)));
	}

	private Cost calculateComplexItem(int amount) {
		int val = amount/this.getPackageAmount();
		return this.setTotalCost(BigDecimal.valueOf(val)
				.multiply(this.getPackagePrice()));
	}

	private Cost calculateSimpleItem(int amount) {
		return this.setTotalCost(this.getTotalCost()
				.add(this.getItem().getPrice().multiply(BigDecimal.valueOf(amount))));
	}

	private Cost calculatePromotedItemCost(Item item, int amount) {
		return this.setTotalCost(BigDecimal
				.valueOf(amount - (amount/ 3))
				.multiply(item.getPrice()));
	}
	
	private Cost calculateCostWithoutDiscount(Item item, int amount) {
		return this.setTotalCost(this.getTotalCost()
				.add((item.getPrice())
				.multiply(BigDecimal.valueOf(amount))));
	}
}
