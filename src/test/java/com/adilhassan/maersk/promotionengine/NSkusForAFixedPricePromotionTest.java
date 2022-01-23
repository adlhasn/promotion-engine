package com.adilhassan.maersk.promotionengine;

import com.adilhassan.maersk.domain.Cart;
import com.adilhassan.maersk.domain.SKU;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NSkusForAFixedPricePromotionTest {
  final Cart cart = new Cart();

  @ParameterizedTest
  @ValueSource(ints = {3, 4, 5, 6, 7})
  public void promotionIsApplicable(int count) {
    //Given
    cart.addSkus(count, SKU.A);
    final Promotion promotion = new NSkusForAFixedPricePromotion(3, SKU.A, 130);

    //When
    final boolean isApplicable = promotion.isApplicable(cart);

    //Then
    assertTrue(isApplicable);
  }

  @ParameterizedTest
  @ValueSource(ints = {1, 2})
  public void promotionIsNotApplicable(int count) {
    //Given
    cart.addSkus(count, SKU.A);
    final Promotion promotion = new NSkusForAFixedPricePromotion(3, SKU.A, 130);

    //When
    final boolean isApplicable = promotion.isApplicable(cart);

    //Then
    assertFalse(isApplicable);
  }

  @ParameterizedTest
  @CsvSource(value = {"3:20", "4:20", "5:20", "6:40"}, delimiter = ':')
  public void promotionIsApplied(int skuCount, double discount) {
    //Given
    cart.addSkus(skuCount, SKU.A);
    final Promotion promotion = new NSkusForAFixedPricePromotion(3, SKU.A, 130);

    //When
    cart.addPromotion(promotion);
    cart.applyPromotions();

    //Then
    assertEquals(discount, promotion.getDiscount());
  }

  @Test
  public void canGetSkus() {
    //Given
    final Promotion promotion = new NSkusForAFixedPricePromotion(3, SKU.A, 130);

    //When
    final List<SKU> skus = promotion.getSkus();

    //Then
    assertIterableEquals(List.of(SKU.A), skus);
  }

  @ParameterizedTest
  @ValueSource(ints = {-2, -1, 0})
  public void throwsAnExceptionOnInvalidValueForSkuCount(int count) {
    assertThrows(IllegalArgumentException.class, () -> new NSkusForAFixedPricePromotion(count, SKU.A, 130));
  }

  @Test
  public void throwsAnExceptionOnNullSku() {
    assertThrows(IllegalArgumentException.class, () -> new NSkusForAFixedPricePromotion(3, null, 130));
  }

  @ParameterizedTest
  @ValueSource(ints = {-2, -1})
  public void throwsAnExceptionOnInvalidValueForFixedPrice(double fixedPrice) {
    assertThrows(IllegalArgumentException.class, () -> new NSkusForAFixedPricePromotion(3, SKU.A, fixedPrice));
  }
}