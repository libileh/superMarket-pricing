package com.katas.supermarket_pricing;

import java.math.BigDecimal;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * {@link Item}
 */
public class ItemTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void createSafiTem_withoutPrice_then_throw_exception() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("must not be negative");
		Item.createSafeItem("item1", BigDecimal.valueOf(0D));
	}

	@Test
	public void createSafiTem_withoutName_then_throw_exception() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("name must not be null or blank");
		Item.createSafeItem(" ", BigDecimal.valueOf(0.33D));
	}

	@Test
	public void createSafiTem_then_create() {
		Item createdSafeItem = Item.createSafeItem("item1", BigDecimal.valueOf(0.33));
		Assertions.assertThat(createdSafeItem.getPrice()).isEqualTo(BigDecimal.valueOf(0.33));
	}

}
