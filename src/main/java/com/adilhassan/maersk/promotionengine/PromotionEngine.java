package com.adilhassan.maersk.promotionengine;

import com.adilhassan.maersk.domain.Cart;

import java.util.ArrayList;
import java.util.List;

public class PromotionEngine {
  private final Cart cart;
  private final List<Promotion> promotions;
  private double discount = 0;

  public PromotionEngine(final Cart cart) {
    this.cart = cart;
    promotions = new ArrayList<>();
  }

  public void addPromotion(final Promotion promotion) {
    if (promotion != null) {
      promotions.add(promotion);
    }
  }

  public void addPromotions(final List<Promotion> promotions) {
    for (final Promotion promotion : promotions) {
      addPromotion(promotion);
    }
  }

  public List<Promotion> getPromotions() {
    return promotions;
  }

  public void applyPromotions() {
    for (final Promotion promotion : promotions) {
      if (promotion.isApplicable(cart)) {
        promotion.applyPromotion(cart);
      }
      discount += promotion.getDiscount();
    }
  }

  public double getDiscount() {
    return discount;
  }
}
