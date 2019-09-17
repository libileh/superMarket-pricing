package com.katas.supermarket_pricing;

import java.math.BigDecimal;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * 
 * @author liban {@link Cost}
 */
public class CostTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void add_OneItem_toPackageOfThreeItems_forOneDollar_then_returnCost() {
		Item aItem = Item.createSafeItem("name", BigDecimal.valueOf(0.33D));
		Cost createCostByPackagePrice = Cost.createItemsByPackagePrice(aItem, 3, BigDecimal.valueOf(1D));
		Cost totalCost = createCostByPackagePrice.addSameItem(1);
		Assertions.assertThat(totalCost.getCurrentAmount()).isEqualTo(4);
		Assertions.assertThat(totalCost.getTotalCost()).isEqualTo(BigDecimal.valueOf(1.33D));
	}

	@Test
	public void add_twoItems_toPackageOfThreeItems_forOneDollar_then_returnCost() {
		Item aItem = Item.createSafeItem("name", BigDecimal.valueOf(0.33D));
		Cost createCostByPackagePrice = Cost.createItemsByPackagePrice(aItem, 3, BigDecimal.valueOf(1D));
		Cost totalCost = createCostByPackagePrice.addSameItem(2);
		Assertions.assertThat(totalCost.getCurrentAmount()).isEqualTo(5);
		Assertions.assertThat(totalCost.getTotalCost()).isEqualTo(BigDecimal.valueOf(1.66D));
	}
	
	@Test
	public void add_threeItems_toPackageOfThreeItems_forOneDollar_then_returnCost() {
		Item aItem = Item.createSafeItem("name", BigDecimal.valueOf(0.33D));
		Cost createCostByPackagePrice = Cost.createItemsByPackagePrice(aItem, 3, BigDecimal.valueOf(1D));
		Cost totalCost = createCostByPackagePrice.addSameItem(3);
		Assertions.assertThat(totalCost.getCurrentAmount()).isEqualTo(6);
		Assertions.assertThat(totalCost.getTotalCost().longValueExact())
			.isEqualTo(BigDecimal.valueOf(2D).longValueExact());
	}

	@Test
	public void convert_ounces_toDollars() {
		Item aItem = Item.createSafeItem("name", BigDecimal.valueOf(0.33D));
		Cost createCostByPackagePrice = Cost.createItemsByPackagePrice(aItem, 3, BigDecimal.valueOf(4D));
		BigDecimal ouncesToDollars = createCostByPackagePrice
				.convertOuncesToDollars(createCostByPackagePrice.getTotalCost());
		Assertions.assertThat(ouncesToDollars).isEqualTo(BigDecimal.valueOf(0.4975));

	}

	@Test
	public void convert_ounces_toDollars_throw_exception() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("The ounces values [0] must not be negative or zero");
		Cost createCostByPackagePrice =Cost.zeroCost();
		createCostByPackagePrice
		.convertOuncesToDollars(createCostByPackagePrice.getTotalCost());
	}
	
	@Test
	public void buyPromotedItem_thenReturnCost() {
		Item aItem = Item.createSafeItem("name", BigDecimal.valueOf(1D));
		Cost simpleItemCost = Cost.createSimpleItemCost(aItem);
		Cost promotedItemCost = simpleItemCost.addSamePromotedItem(aItem, 5);
		Assertions.assertThat(promotedItemCost.getTotalCost()).isEqualTo(BigDecimal.valueOf(4D));
	}

	@Test
	public void buyPromotedItem_thenReturnCost_2() {
		Item aItem = Item.createSafeItem("name", BigDecimal.valueOf(1D));
		Cost simpleItemCost = Cost.createSimpleItemCost(aItem);
		Cost promotedItemCost = simpleItemCost.addSamePromotedItem(aItem, 4);
		Assertions.assertThat(promotedItemCost.getTotalCost()).isEqualTo(BigDecimal.valueOf(4D));
	}
}
