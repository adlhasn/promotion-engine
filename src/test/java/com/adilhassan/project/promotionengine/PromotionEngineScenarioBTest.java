package com.adilhassan.project.promotionengine;

import com.adilhassan.project.domain.Cart;
import com.adilhassan.project.domain.SKU;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PromotionEngineScenarioBTest {

  public Cart cart = new Cart();
  public PromotionEngine promotionEngine = cart.getPromotionEngine();

  @Test
  public void multiplePromotionsAreApplied_ScenarioB() {
    //Given
    cart.addSkus(5, SKU.A);
    cart.addSkus(5, SKU.B);
    cart.addSkus(1, SKU.C);

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
    assertEquals(370, cart.getTotal());
    assertEquals(50, promotionEngine.getDiscount());
  }
}