package com.adilhassan.maersk.domain;

import com.adilhassan.maersk.promotionengine.NSkusForAFixedPricePromotion;
import com.adilhassan.maersk.promotionengine.Promotion;
import com.adilhassan.maersk.promotionengine.TwoSkusForAFixedPricePromotion;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CartTest {

  final Cart cart = new Cart();

  @Test
  public void canAddASingleSkuToCart() {
    //Given
    final SKU A = SKU.A;

    //When
    cart.addSku(A);

    //Then
    assertEquals(1, cart.getSkus().size());
  }

  @Test
  public void canAddMultipleSkusToCartAtOnce() {
    //Given
    final SKU A = SKU.A;

    //When
    cart.addSkus(3, A);

    //Then
    assertEquals(3, cart.getSkus().size());
  }

  @Test
  public void canGetSkuCount() {
    //Given
    cart.addSku(SKU.A);
    cart.addSkus(2, SKU.B);
    cart.addSkus(3, SKU.C);

    //When
    int skuACount = cart.getSkuCount(SKU.A);
    int skuBCount = cart.getSkuCount(SKU.B);
    int skuCCount = cart.getSkuCount(SKU.C);

    //Then
    assertEquals(1, skuACount);
    assertEquals(2, skuBCount);
    assertEquals(3, skuCCount);
  }

  @Test
  public void canGetSkuPrice() {
    //Given
    cart.addSku(SKU.A);
    cart.addSkus(2, SKU.B);
    cart.addSkus(3, SKU.C);

    //When
    double skuAPrice = cart.getSkuPrice(SKU.A);
    double skuBPrice = cart.getSkuPrice(SKU.B);
    double skuCPrice = cart.getSkuPrice(SKU.C);

    //Then
    assertEquals(50, skuAPrice);
    assertEquals(60, skuBPrice);
    assertEquals(60, skuCPrice);
  }


  @Test
  public void canGetCartTotalWithoutPromotionalDiscount() {
    //Given
    cart.addSkus(3, SKU.A);
    cart.addSkus(2, SKU.B);

    final Promotion promotion = new NSkusForAFixedPricePromotion(3, SKU.A, 130);
    cart.addPromotion(promotion);
    cart.applyPromotions();

    //When
    double cartTotalWithoutPromotionalDiscount = cart.getCartTotalWithoutPromotionalDiscount();

    //Then
    assertEquals(210, cartTotalWithoutPromotionalDiscount);
  }

  @Test
  public void canAddASinglePromotionToCart() {
    //Given
    final Promotion promotion = new NSkusForAFixedPricePromotion(3, SKU.A, 130);

    //When
    cart.addPromotion(promotion);

    //Then
    assertEquals(1, cart.getPromotions().size());
  }

  @Test
  public void canAddMultiplePromotionsToCartAtOnce() {
    //Given
    final Promotion promotion1 = new NSkusForAFixedPricePromotion(3, SKU.A, 130);
    final Promotion promotion2 = new TwoSkusForAFixedPricePromotion(SKU.C, SKU.D, 30);

    //When
    cart.addPromotions(List.of(promotion1, promotion2));

    //Then
    assertEquals(2, cart.getPromotions().size());
  }

  @Test
  public void canApplyPromotionsToCart() {
    //Given
    cart.addSkus(3, SKU.A);
    final Promotion promotion = new NSkusForAFixedPricePromotion(3, SKU.A, 130);

    //When
    cart.addPromotion(promotion);
    cart.applyPromotions();

    //Then
    assertEquals(20, cart.getDiscount());
  }

  @Test
  public void canGetCartTotalWithPromotionalDiscount() {
    //Given
    cart.addSkus(3, SKU.A);
    final Promotion promotion = new NSkusForAFixedPricePromotion(3, SKU.A, 130);

    //When
    cart.addPromotion(promotion);
    cart.applyPromotions();

    //Then
    assertEquals(130, cart.getTotal());
  }

  @Test
  public void canGetCartTotal() {
    //Given
    cart.addSkus(3, SKU.A);
    final Promotion promotion = new NSkusForAFixedPricePromotion(3, SKU.A, 130);

    //When
    cart.addPromotion(promotion);
    cart.applyPromotions();

    //Then
    assertEquals(130, cart.getTotal());
  }
}