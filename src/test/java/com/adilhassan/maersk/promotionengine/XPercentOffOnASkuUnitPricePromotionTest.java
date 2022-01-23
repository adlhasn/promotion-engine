package com.adilhassan.maersk.promotionengine;

import com.adilhassan.maersk.domain.Cart;
import com.adilhassan.maersk.domain.SKU;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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

    //When - Then
    assertEquals(0, promotion.getDiscount());
  }
}