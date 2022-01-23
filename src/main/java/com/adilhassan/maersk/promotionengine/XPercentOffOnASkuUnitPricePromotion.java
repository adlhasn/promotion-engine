package com.adilhassan.maersk.promotionengine;

import com.adilhassan.maersk.domain.Cart;
import com.adilhassan.maersk.domain.SKU;

import java.util.List;

public class XPercentOffOnASkuUnitPricePromotion implements Promotion {
  private final double percent;
  private final SKU sku;

  private double discount;

  public XPercentOffOnASkuUnitPricePromotion(final double percent, final SKU sku) {
    validate(percent, sku);
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

  @Override
  public List<SKU> getSkus() {
    return List.of(sku);
  }

  private void validate(final double percent, final SKU sku) {
    final StringBuilder exceptionMessage = new StringBuilder();

    if (percent <= 0) {
      exceptionMessage.append("Percentage value must be greater than 0.");
    }
    if (sku == null) {
      exceptionMessage.append("Sku must not be null.");
    }
    if (exceptionMessage.length() > 0)
      throw new IllegalArgumentException(exceptionMessage.toString());
  }
}
