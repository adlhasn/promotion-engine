package com.adilhassan.maersk.promotionengine;

import com.adilhassan.maersk.domain.Cart;

public interface Promotion {
  boolean isApplicable(Cart cart);
  void applyPromotion(Cart cart);
  double getDiscount();
}
