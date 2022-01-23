package com.adilhassan.maersk.promotionengine;

import com.adilhassan.maersk.domain.Cart;
import com.adilhassan.maersk.domain.SKU;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class XPercentOffOnASkuUnitPricePromotionTest {
  final Cart cart = new Cart();

  @Test
  public void promotionIsApplicable() {
    //Given
    cart.addSku(SKU.A);
    final Promotion promotion = new XPercentOffOnASkuUnitPricePromotion();

    //When - Then
    assertTrue(promotion.isApplicable(cart));
  }

  @Test
  public void promotionIsNotApplicable() {
    //Given
    cart.addSku(SKU.A);
    final Promotion promotion = new XPercentOffOnASkuUnitPricePromotion();

    //When - Then
    assertFalse(promotion.isApplicable(cart));
  }

  @Test
  public void promotionIsApplied() {
    //Given
    cart.addSku(SKU.A);
    final Promotion promotion = new XPercentOffOnASkuUnitPricePromotion();

    //When
    cart.addPromotion(promotion);
    cart.applyPromotions();

    //Then
    assertEquals(5, promotion.getDiscount());
  }
}