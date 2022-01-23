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
    final Promotion promotion2 = new TwoSkusForAFixedPricePromotion(SKU.C, SKU.D, 30);

    //When
    promotionEngine.addPromotions(List.of(promotion1, promotion2));

    //Then
    assertEquals(2, promotionEngine.getPromotions().size());
  }

  @Test
  public void canApplyASinglePromotion() {
    //Given
    cart.addSkus(3, SKU.A);
    final Promotion promotion = new NSkusForAFixedPricePromotion(3, SKU.A, 130);
    promotionEngine.addPromotion(promotion);

    //When
    promotionEngine.applyPromotions();

    //Then
    assertEquals(20, promotionEngine.getDiscount());
    assertEquals(130, cart.getTotal());
  }

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
    assertEquals(50, promotionEngine.getDiscount());
    assertEquals(370, cart.getTotal());
  }

  @Test
  public void multiplePromotionsAreApplied_ScenarioC() {
    //Given
    cart.addSkus(3, SKU.A);
    cart.addSkus(5, SKU.B);
    cart.addSkus(1, SKU.C);
    cart.addSkus(1, SKU.D);

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
    assertEquals(55, promotionEngine.getDiscount());
    assertEquals(280, cart.getTotal());
  }

  /*
  If the same promotion is applied twice on a given SKU then only the last one takes effect
   */
  @Test
  public void onlyTheLastAppliedPromotionTakesEffect() {
    //Given
    cart.addSkus(5, SKU.A);
    final Promotion promotion1 = new NSkusForAFixedPricePromotion(3, SKU.A, 130);
    final Promotion promotion2 = new NSkusForAFixedPricePromotion(5, SKU.A, 200);

    //When
    promotionEngine.addPromotions(List.of(promotion1, promotion2));
    promotionEngine.applyPromotions();

    //Then
    assertEquals(50, promotionEngine.getDiscount());
    assertEquals(200, cart.getTotal());
  }
}