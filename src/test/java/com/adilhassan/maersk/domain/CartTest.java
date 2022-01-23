package com.adilhassan.maersk.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
  public void canRemoveASkuFromTheCart() {
    //Given
    final SKU A = SKU.A;
    cart.addSku(A);

    //When
    cart.removeSku(A);

    //Then
    assertEquals(0, cart.getSkus().size());
  }

  @Test
  public void canGetSkuCount() {
    //Given
    cart.addSkus(3, SKU.A);
    cart.addSkus(2, SKU.B);

    //When
    int skuACount = cart.getSkuCount(SKU.A);
    int skuBCount = cart.getSkuCount(SKU.B);

    //Then
    assertEquals(3, skuACount);
    assertEquals(2, skuBCount);
  }

  @Test
  public void canGetSkuPrice() {
    //Given
    cart.addSkus(3, SKU.A);
    cart.addSkus(2, SKU.B);

    //When
    double skuAPrice = cart.getSkuPrice(SKU.A);
    double skuBPrice = cart.getSkuPrice(SKU.B);

    //Then
    assertEquals(150, skuAPrice);
    assertEquals(60, skuBPrice);
  }

  @Test
  public void canAddAPromotionToCart() {

  }

  @Test
  public void canAddMultiplePromotionsToCartAtOnce() {

  }

  @Test
  public void canApplyPromotionsToCart() {

  }

  @Test
  public void canGetTotalPriceOfTheCartWithoutPromotionalDiscount() {

  }

  @Test
  public void canGetTotalPriceOfTheCartWithPromotionalDiscount() {

  }
}