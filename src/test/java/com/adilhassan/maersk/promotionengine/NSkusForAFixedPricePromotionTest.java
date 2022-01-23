package com.adilhassan.maersk.promotionengine;

import com.adilhassan.maersk.domain.Cart;
import com.adilhassan.maersk.domain.SKU;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NSkusForAFixedPricePromotionTest {
  final Cart cart = new Cart();

  @Test
  public void promotionIsApplicable() {
    //Given
    cart.addSkus(3, SKU.A);
    final Promotion promotion = new NSkusForAFixedPricePromotion(3, SKU.A, 130);

    //When
    cart.addPromotion(promotion);
    cart.applyPromotions();

    //Then
    assertTrue(promotion.isApplicable(cart));
  }

  @Test
  public void promotionIsApplied() {
    //Given
    cart.addSkus(3, SKU.A);
    final Promotion promotion = new NSkusForAFixedPricePromotion(3, SKU.A, 130);

    //When
    cart.addPromotion(promotion);
    cart.applyPromotions();

    //Then
    assertEquals(130, cart.getTotal());
    assertEquals(20, cart.getDiscount());
  }
}