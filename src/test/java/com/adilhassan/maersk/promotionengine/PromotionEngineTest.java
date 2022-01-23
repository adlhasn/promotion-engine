package com.adilhassan.maersk.promotionengine;

import com.adilhassan.maersk.domain.Cart;
import com.adilhassan.maersk.domain.SKU;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PromotionEngineTest {

  final Cart cart = new Cart();
  final PromotionEngine promotionEngine = cart.getPromotionEngine();

  @Test
  public void canAddASinglePromotion() {
    //Given
    final Promotion promotion = new NSkusForAFixedPricePromotion(3, SKU.A, 130);

    //When
    promotionEngine.addPromotion(promotion);

    //Then
    assertEquals(1, promotionEngine.getPromotions().size());
  }

  @Test
  public void canAddMultiplePromotionsAtOnce() {
    //Given
    final Promotion promotion1 = new NSkusForAFixedPricePromotion(3, SKU.A, 130);
    final Promotion promotion2 = new TwoSkusForAFixedPricePromotion();

    //When
    promotionEngine.addPromotions(List.of(promotion1, promotion2));

    //Then
    assertEquals(2, promotionEngine.getPromotions().size());
  }

  @Test
  public void canApplyPromotions() {
    //Given
    cart.addSkus(3, SKU.A);
    final Promotion promotion = new NSkusForAFixedPricePromotion(3, SKU.A, 130);
    promotionEngine.addPromotion(promotion);

    //When
    promotionEngine.applyPromotions();

    //Then
    assertEquals(20, promotionEngine.getDiscount());
  }
}