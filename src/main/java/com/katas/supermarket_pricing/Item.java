package com.katas.supermarket_pricing;

import java.math.BigDecimal;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author liban {@link ItemTest}
 */
public class Item {

	private String name;
	private BigDecimal price;

	public Item(String name, BigDecimal price) {
		super();
		this.name = name;
		this.price = price;
	}

	public static Item createSafeItem(String name, BigDecimal price) {
		if (StringUtils.isBlank(name)) {
			throw new IllegalArgumentException("name must not be null or blank");
		}
		return Optional.ofNullable(price).filter(pr -> pr.compareTo(BigDecimal.valueOf(0)) == 1)
				.map(pr -> new Item(name, pr)).orElseThrow(() -> new IllegalArgumentException("must not be negative"));
	}

	public BigDecimal getPrice() {
		return price;
	}

}
