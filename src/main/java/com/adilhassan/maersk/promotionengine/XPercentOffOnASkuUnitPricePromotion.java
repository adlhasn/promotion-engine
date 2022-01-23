package com.adilhassan.maersk.promotionengine;

import com.adilhassan.maersk.domain.Cart;

public class XPercentOffOnASkuUnitPricePromotion implements Promotion {
  @Override
  public boolean isApplicable(Cart cart) {
    return false;
  }

  @Override
  public void applyPromotion(Cart cart) {

  }

  @Override
  public double getDiscount() {
    return 0;
  }
}
