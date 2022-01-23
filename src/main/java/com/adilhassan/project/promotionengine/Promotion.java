package com.adilhassan.project.promotionengine;

import com.adilhassan.project.domain.Cart;
import com.adilhassan.project.domain.SKU;

import java.util.List;

public interface Promotion {
  boolean isApplicable(Cart cart);
  void applyPromotion(Cart cart);
  double getDiscount();
  List<SKU> getSkus();
}
