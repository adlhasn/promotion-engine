package com.adilhassan.project.promotionengine;

import com.adilhassan.project.domain.Cart;
import com.adilhassan.project.domain.SKU;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

  /*
  If the same promotion is applied twice on a given SKU then only the first one takes effect, the other will be ignored
   */
  @Test
  public void onlyTheFirstAppliedPromotionTakesEffect_Scenario1() {
    //Given
    cart.addSkus(5, SKU.A);
    final Promotion promotion1 = new NSkusForAFixedPricePromotion(3, SKU.A, 130);
    final Promotion promotion2 = new NSkusForAFixedPricePromotion(5, SKU.A, 200);

    //When
    promotionEngine.addPromotions(List.of(promotion1, promotion2));
    promotionEngine.applyPromotions();

    //Then
    assertEquals(20, promotionEngine.getDiscount());
    assertEquals(230, cart.getTotal());
  }

  @Test
  public void onlyTheFirstAppliedPromotionTakesEffect_Scenario2() {
    //Given
    cart.addSku(SKU.A);
    cart.addSku(SKU.B);
    cart.addSku(SKU.C);
    cart.addSku(SKU.D);

    final Promotion promotion1 = new TwoSkusForAFixedPricePromotion(SKU.A, SKU.B, 70);
    final Promotion promotion2 = new TwoSkusForAFixedPricePromotion(SKU.B, SKU.C, 40);
    final Promotion promotion3 = new TwoSkusForAFixedPricePromotion(SKU.C, SKU.D, 30);

    //When
    promotionEngine.addPromotions(List.of(promotion1, promotion2, promotion3));
    promotionEngine.applyPromotions();

    //Then
    //promotion1 Discount = (80 - 70) = 10
    //promotion2 Discount = N/A
    //promotion2 Discount = (35 - 30) = 5
    //total promotional discount = 10 + 5
    assertEquals(15, promotionEngine.getDiscount());
    assertEquals(100, cart.getTotal());
  }

  @Test
  public void differentTypesOfPromotionsCanBeAppliedOnTheSameSku() {
    //Given
    cart.addSkus(5, SKU.A);
    final Promotion promotion1 = new NSkusForAFixedPricePromotion(3, SKU.A, 130);
    final Promotion promotion2 = new XPercentOffOnASkuUnitPricePromotion(10, SKU.A);

    //When
    promotionEngine.addPromotions(List.of(promotion1, promotion2));
    promotionEngine.applyPromotions();

    //Then
    //promotion1 Discount = 250 - (130 + 100) = 20
    //promotion2 Discount = 250 * (10/100) = 25
    //total promotional discount = 20 + 25
    assertEquals(45, promotionEngine.getDiscount());
    assertEquals(205, cart.getTotal());
  }
}