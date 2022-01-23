package com.adilhassan.assignment.promotionengine;

import com.adilhassan.assignment.domain.Cart;
import com.adilhassan.assignment.domain.SKU;

import java.util.List;

public class TwoSkusForAFixedPricePromotion implements Promotion {
  private final SKU skuOne;
  private final SKU skuTwo;
  private final double fixedPrice;

  private double discount;

  public TwoSkusForAFixedPricePromotion(final SKU skuOne, final SKU skuTwo, final double fixedPrice) {
    validate(skuOne, skuTwo, fixedPrice);
    this.skuOne = skuOne;
    this.skuTwo = skuTwo;
    this.fixedPrice = fixedPrice;
  }

  @Override
  public boolean isApplicable(final Cart cart) {
    final int skuOneCount = cart.getSkuCount(skuOne);
    final int skuTwoCount = cart.getSkuCount(skuTwo);

    return skuOneCount >= 1 && skuTwoCount >= 1;
  }

  @Override
  public void applyPromotion(final Cart cart) {
    final int skuOneCount = cart.getSkuCount(skuOne);
    final int skuTwoCount = cart.getSkuCount(skuTwo);

    final int numberOfPromotionsToApply = Math.min(skuOneCount, skuTwoCount);
    final double promotionalPriceOfSkus = numberOfPromotionsToApply * fixedPrice;

    final int numberOfSkusOneOnStandardPrice = skuOneCount - numberOfPromotionsToApply;
    final int numberOfSkusTwoOnStandardPrice = skuTwoCount - numberOfPromotionsToApply;

    final double skusStandardPrice = (numberOfSkusOneOnStandardPrice * skuOne.getPrice()) +
        (numberOfSkusTwoOnStandardPrice * skuTwo.getPrice());

    final double totalPriceOfSkuWithPromotion = skusStandardPrice + promotionalPriceOfSkus;

    discount = (cart.getSkuPrice(skuOne) + cart.getSkuPrice(skuTwo)) - totalPriceOfSkuWithPromotion;
  }

  @Override
  public double getDiscount() {
    return discount;
  }

  @Override
  public List<SKU> getSkus() {
    return List.of(skuOne, skuTwo);
  }

  private void validate(final SKU skuOne, final SKU skuTwo, final double fixedPrice) {
    final StringBuilder exceptionMessage = new StringBuilder();

    if (skuOne == null || skuTwo == null) {
      exceptionMessage.append("SKU must not be null.");
    }
    if (fixedPrice < 0) {
      exceptionMessage.append("Fixed price must be greater than or equal to 0.");
    }
    if (exceptionMessage.length() > 0)
      throw new IllegalArgumentException(exceptionMessage.toString());
  }
}