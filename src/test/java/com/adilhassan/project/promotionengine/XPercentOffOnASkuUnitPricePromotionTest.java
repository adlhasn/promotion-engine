package com.adilhassan.project.promotionengine;

import com.adilhassan.project.domain.Cart;
import com.adilhassan.project.domain.SKU;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class XPercentOffOnASkuUnitPricePromotionTest {
  final Cart cart = new Cart();

  @Test
  public void promotionIsApplicable() {
    //Given
    cart.addSku(SKU.A);
    final Promotion promotion = new XPercentOffOnASkuUnitPricePromotion(10, SKU.A);

    //When
    boolean isApplicable = promotion.isApplicable(cart);

    //When - Then
    assertTrue(isApplicable);
  }

  @Test
  public void promotionIsNotApplicable() {
    //Given
    cart.addSku(SKU.A);
    final Promotion promotion = new XPercentOffOnASkuUnitPricePromotion(10, SKU.B);

    //When
    final boolean isApplicable = promotion.isApplicable(cart);

    //Then
    assertFalse(isApplicable);
  }

  @ParameterizedTest
  @CsvSource(value = {"1:A:5", "2:A:10", "1:B:3", "2:C:4"}, delimiter = ':')
  public void promotionIsApplied(int skuCount, SKU sku, double discount) {
    //Given
    cart.addSkus(skuCount, sku);
    final Promotion promotion = new XPercentOffOnASkuUnitPricePromotion(10, sku);

    //When
    cart.addPromotion(promotion);
    cart.applyPromotions();

    //Then
    assertEquals(discount, promotion.getDiscount());
  }

  @Test
  public void promotionIsNotApplied() {
    //Given
    cart.addSku(SKU.A);
    final Promotion promotion = new XPercentOffOnASkuUnitPricePromotion(10, SKU.B);

    //When
    double discount = promotion.getDiscount();

    // Then
    assertEquals(0, discount);
  }

  @Test
  public void canGetSkus() {
    //Given
    final Promotion promotion = new XPercentOffOnASkuUnitPricePromotion(10, SKU.A);

    //When
    final List<SKU> skus = promotion.getSkus();

    //Then
    assertIterableEquals(List.of(SKU.A), skus);
  }

  @ParameterizedTest
  @ValueSource(ints = {-2, -1, 0})
  public void throwsAnExceptionOnInvalidValueForPercent(int percent) {
    assertThrows(IllegalArgumentException.class, () -> new XPercentOffOnASkuUnitPricePromotion(percent, SKU.A));
  }

  @Test
  public void throwsAnExceptionOnNullSku() {
    assertThrows(IllegalArgumentException.class, () -> new XPercentOffOnASkuUnitPricePromotion(10, null));
  }
}