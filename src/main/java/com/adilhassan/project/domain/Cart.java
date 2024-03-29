package com.adilhassan.project.domain;

import com.adilhassan.project.promotionengine.Promotion;
import com.adilhassan.project.promotionengine.PromotionEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cart {
  private final List<SKU> skus;
  private final Map<SKU, Integer> skuCount;
  private final Map<SKU, Double> skuPrice;
  private final PromotionEngine promotionEngine;

  private double cartTotalWithoutPromotionalDiscount = 0;

  public Cart() {
    skus = new ArrayList<>();
    skuCount = new ConcurrentHashMap<>();
    skuPrice = new ConcurrentHashMap<>();
    promotionEngine = new PromotionEngine(this);
  }

  public boolean addSku(final SKU sku) {
    if (sku != null) {
      skuCount.compute(sku, (k, v) -> v == null ? 1 : v + 1);
      skuPrice.compute(sku, (k, v) -> v == null ? k.getPrice() : v + k.getPrice());
      return skus.add(sku);
    }
    return false;
  }

  public boolean addSkus(int count, final SKU sku) {
    if (count > 0 && sku != null) {
      for (int i=0; i<count; i++) {
        addSku(sku);
      }
      return true;
    }
    return false;
  }

  public List<SKU> getSkus() {
    return skus;
  }

  public int getSkuCount(final SKU sku) {
    return skuCount.get(sku) == null ? 0 : skuCount.get(sku);
  }

  public double getSkuPrice(final SKU sku) {
    return skuPrice.get(sku) == null ? 0 : skuPrice.get(sku);
  }

  public PromotionEngine getPromotionEngine() {
    return promotionEngine;
  }

  public void addPromotion(final Promotion promotion) {
    promotionEngine.addPromotion(promotion);
  }

  public void addPromotions(final List<Promotion> promotions) {
    promotionEngine.addPromotions(promotions);
  }

  public List<Promotion> getPromotions() {
    return promotionEngine.getPromotions();
  }

  public void applyPromotions() {
    promotionEngine.applyPromotions();
  }

  public double getDiscount() {
    return promotionEngine.getDiscount();
  }

  public double getTotal() {
    return getCartTotalWithPromotionalDiscount();
  }

  public double getCartTotalWithoutPromotionalDiscount() {
    skuPrice.entrySet().forEach(entry -> cartTotalWithoutPromotionalDiscount += entry.getValue());
    return cartTotalWithoutPromotionalDiscount;
  }

  public double getCartTotalWithPromotionalDiscount() {
    return getCartTotalWithoutPromotionalDiscount() - getDiscount();
  }
}
