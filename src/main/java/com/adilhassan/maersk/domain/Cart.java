package com.adilhassan.maersk.domain;

import com.adilhassan.maersk.promotionengine.PromotionEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cart {
  private final List<SKU> skus;
  private final Map<SKU, Integer> skuCount;
  private final Map<SKU, Double> skuPrice;
  private final PromotionEngine promotionEngine;

  public Cart() {
    skus = new ArrayList<>();
    skuCount = new ConcurrentHashMap<>();
    skuPrice = new ConcurrentHashMap<>();
    promotionEngine = new PromotionEngine();
  }

  public boolean addSku(final SKU sku) {
    if (sku != null)
      return skus.add(sku);

    return false;
  }

  public boolean addSkus(int count, final SKU sku) {
    if (count > 0 && sku != null) {
      for (int i=0; i<count; i++) {
        skus.add(sku);
      }
      return true;
    }

    return false;
  }

  public List<SKU> getSkus() {
    return skus;
  }

  public void removeSku(final SKU sku) {
    skus.remove(sku);
  }

  public int getSkuCount(final SKU sku) {
    return skuCount.get(sku);
  }

  public double getSkuPrice(SKU sku) {
    return 0;
  }
}
