package com.adilhassan.assignment.promotionengine;

import com.adilhassan.assignment.domain.Cart;
import com.adilhassan.assignment.domain.SKU;

import java.util.List;

public interface Promotion {
  boolean isApplicable(Cart cart);
  void applyPromotion(Cart cart);
  double getDiscount();
  List<SKU> getSkus();
}
