package com.adilhassan.maersk.promotionengine;

import com.adilhassan.maersk.domain.Cart;
import com.adilhassan.maersk.domain.SKU;

import java.util.List;

public interface Promotion {
  boolean isApplicable(Cart cart);
  void applyPromotion(Cart cart);
  double getDiscount();
  List<SKU> getSkus();
}
