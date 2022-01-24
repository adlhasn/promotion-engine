package com.adilhassan.project.promotionengine;

import com.adilhassan.project.domain.Cart;

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
    if (promotion != null && !alreadyExists(promotion)) {
      promotions.add(promotion);
    }
  }

  private boolean isSame(final Promotion p1, final Promotion p2) {
    return p1.getClass().equals(p2.getClass()) &&
        p1.getSkus().stream().anyMatch(sku -> p2.getSkus().contains(sku));
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
