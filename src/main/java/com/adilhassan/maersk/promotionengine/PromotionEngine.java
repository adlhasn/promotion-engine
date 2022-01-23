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
      if (alreadyExists(promotion)) {
        int index = findIndex(promotion);
        removePromotion(index);
      }
      promotions.add(promotion);
    }
  }

  private int findIndex(final Promotion promotion) {
    int index = 0;
    for (int i=0; i<promotions.size(); i++) {
      final Promotion p = promotions.get(i);
      if (isSame(p, promotion)) {
        index = i;
      }
    }
    return index;
  }

  private boolean isSame(final Promotion p1, final Promotion p2) {
    return p1.getClass().equals(p2.getClass()) &&
        p1.getSkus().equals(p2.getSkus());
  }

  private void removePromotion(final int index) {
    promotions.remove(index);
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
        discount += promotion.getDiscount();
      }
    }
  }

  public double getDiscount() {
    return discount;
  }

  private boolean alreadyExists(final Promotion promotion) {
    return promotionAlreadyExistsThatIsOfTheSameTypeAndOnTheSameSku(promotion);
  }

  private boolean promotionAlreadyExistsThatIsOfTheSameTypeAndOnTheSameSku(final Promotion promotion) {
    return promotions.stream().anyMatch(p -> isSame(p, promotion));
  }
}
