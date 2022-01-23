package com.adilhassan.maersk.promotionengine;

import com.adilhassan.maersk.domain.Cart;
import com.adilhassan.maersk.domain.SKU;

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
  public boolean isApplicable(Cart cart) {
    return false;
  }

  @Override
  public void applyPromotion(Cart cart) {

  }

  @Override
  public double getDiscount() {
    return discount;
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
