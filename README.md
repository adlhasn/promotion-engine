## 1. Promotion Engine
The purpose of this project is to demonstrate how to apply promotional offers on a `Cart` to get promotional discounts. 

The `domain` package contains two classes i.e. `Cart` and `SKU`.   

`SKU` is a simple `enum` that has 4 constants i.e. `A`, `B`, `C`, and `D` and each constant has a price associate with it i.e.  
- Each `SKU` unit of `A` costs `$50`.
- Each `SKU` unit of `B` costs `$30`.
- Each `SKU` unit of `C` costs `$20`.
- Each `SKU` unit of `D` costs `$15`.

The `Cart` class, as the name suggests, functions like a cart - that we know. We can add `SKU`s to `Cart` and apply promotions to our `Cart` to get discounts. 

In the `promotionengine` package we have the following classes.  
- `PromotionEngine`  
- `NSkusForAFixedPricePromotion`  
- `TwoSkusForAFixedPricePromotion`
- `XPercentOffOnASkuUnitPricePromotion`

`PromotionEngine` class has all the logic of how to apply active promotions on the `Cart`.  

`Promotion` classes on the other hand have all the logic of calculating discounts on the `Cart` items. 

## 2. How it works
### Active Promotions
Let's say we have the following active promotions. 
```text
3 of A's for $130
2 of B's for $45 
C & D for $30
```

#### Scenario 1
| SKU Count | SKU | COST |
|:---------:|:---:|:----:|
|     1     |  A  |  50  |
|     1     |  B  |  30  |
|     1     |  C  |  20  |

After applying active promotions on the `Cart` total become `$100` (discount=`$0`)

#### Scenario 2
| SKU Count | SKU |      COST      |
|:---------:|:---:|:--------------:|
|     5     |  A  | 130 + (2 * 50) |
|     5     |  B  |  45 + 45 + 30  |
|     1     |  C  |       20       |

After applying active promotions on the `Cart` total become `$370` (discount=`$5`)

#### Scenario 3
| SKU Count | SKU |        COST        |
|:---------:|:---:|:------------------:|
|     3     |  A  |        130         |
|     5     |  B  | 45 + 45 + (1 * 30) |
|     1     |  C  |         -          |
|     1     |  D  |         30         |

After applying active promotions on the `Cart` total become `$280` (discount=`$55`)

To demonstrate the extensibility of the `promotion-engine` I have added `XPercentOffOnASkuUnitPricePromotion` class as well. This applies a percentage discount on `SKU`s.