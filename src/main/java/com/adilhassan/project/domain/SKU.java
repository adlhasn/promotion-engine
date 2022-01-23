package com.adilhassan.project.domain;

public enum SKU {
  A(50), B(30), C(20), D(15);

  private final double price;

  SKU(final double price) {
    this.price = price;
  }

  public double getPrice() {
    return price;
  }
}
