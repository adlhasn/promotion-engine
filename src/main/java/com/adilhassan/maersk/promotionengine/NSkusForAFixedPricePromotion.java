package com.adilhassan.maersk.promotionengine;

import com.adilhassan.maersk.domain.Cart;
import com.adilhassan.maersk.domain.SKU;

public class NSkusForAFixedPricePromotion implements Promotion {
  private final int skuCount;
  private final SKU sku;
  private final double fixedPrice;

  public NSkusForAFixedPricePromotion(final int skuCount, final SKU sku, final double fixedPrice) {
    validate(skuCount, sku, fixedPrice);
    this.skuCount = skuCount;
    this.sku = sku;
    this.fixedPrice = fixedPrice;
  }

  private void validate(final int skuCount, final SKU sku, final double fixedPrice) {
    final StringBuilder exceptionMessage = new StringBuilder();

    if (skuCount <= 0) {
      exceptionMessage.append("SKU count must be greater than 0.");
    }
    if (sku == null) {
      exceptionMessage.append("SKU must not be null.");
    }
    if (fixedPrice < 0) {
      exceptionMessage.append("Fixed price must be greater than or equal to 0.");
    }
    if (exceptionMessage.length() > 0)
      throw new IllegalArgumentException(exceptionMessage.toString());
  }

  @Override
  public boolean isApplicable(final Cart cart) {
    return false;
  }

  @Override
  public void applyPromotion(final Cart cart) {

  }

  @Override
  public double getDiscount() {
    return 0;
  }
}
