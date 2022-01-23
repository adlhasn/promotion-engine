package com.adilhassan.assignment.promotionengine;

import com.adilhassan.assignment.domain.Cart;
import com.adilhassan.assignment.domain.SKU;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PromotionEngineScenarioATest {

  public Cart cart = new Cart();
  public PromotionEngine promotionEngine = cart.getPromotionEngine();

  @Test
  public void noPromotionsAreApplied_ScenarioA() {
    //Given
    cart.addSku(SKU.A);
    cart.addSku(SKU.B);
    cart.addSku(SKU.C);

    final Promotion nSkusForAFixedPricePromotion_A = new NSkusForAFixedPricePromotion(3, SKU.A, 130);
    final Promotion nSkusForAFixedPricePromotion_B = new NSkusForAFixedPricePromotion(2, SKU.B, 45);
    final Promotion twoSkusForAFixedPricePromotion_CD = new TwoSkusForAFixedPricePromotion(SKU.C, SKU.D, 30);

    promotionEngine.addPromotions(List.of
        (nSkusForAFixedPricePromotion_A,
        nSkusForAFixedPricePromotion_B,
        twoSkusForAFixedPricePromotion_CD));

    //When
    promotionEngine.applyPromotions();

    //Then
    assertEquals(0, promotionEngine.getDiscount());
    assertEquals(100, cart.getTotal());
  }
}