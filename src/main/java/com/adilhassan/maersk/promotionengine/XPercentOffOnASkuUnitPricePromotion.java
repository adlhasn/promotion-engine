package com.adilhassan.maersk.promotionengine;

import com.adilhassan.maersk.domain.Cart;
import com.adilhassan.maersk.domain.SKU;

public class XPercentOffOnASkuUnitPricePromotion implements Promotion {
  private final double percent;
  private final SKU sku;

  private double discount;

  public XPercentOffOnASkuUnitPricePromotion(final double percent, final SKU sku) {
    this.percent = percent;
    this.sku = sku;
  }

  @Override
  public boolean isApplicable(final Cart cart) {
    return cart.getSkuCount(sku) > 0;
  }

  @Override
  public void applyPromotion(final Cart cart) {
    double skuPrice = cart.getSkuPrice(sku);
    discount = (percent/100) * skuPrice;
  }

  @Override
  public double getDiscount() {
    return discount;
  }
}
