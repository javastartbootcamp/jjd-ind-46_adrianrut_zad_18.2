package pl.javastart.couponcalc;

import java.util.List;

public class PriceCalculator {

    public double calculatePrice(List<Product> products, List<Coupon> coupons) {
        double price;
        if (products == null) {
            return 0;
        } else {
            for (Product product : products) {
                if (coupons == null) {
                    return product.getPrice();
                } else {
                    for (Coupon coupon : coupons) {
                        if (products.size() > 1) {
                            if (coupons.size() > 1) {
                                return getSumPriceForFewCoupons(products, coupons);
                            } else {
                                return getSumPriceForFewProducts(products, coupons);
                            }
                        } else if (product.getCategory().equals(coupon.getCategory())) {
                            return getPriceAfterDiscount(product, coupon.getDiscountValueInPercents());
                        }
                    }
                }
            }
        }
        return 0;
    }

    private double getSumPriceForFewCoupons(List<Product> products, List<Coupon> coupons) {
        double sum = 0;
        double tempPrice = 0;
        for (Product product : products) {
            for (Coupon coupon : coupons) {
                double priceAfterDiscount = getPriceAfterDiscount(product, coupon.getDiscountValueInPercents());
                if (tempPrice < priceAfterDiscount) {
                    tempPrice = priceAfterDiscount;
                }
                sum = priceAfterDiscount + tempPrice;
            }

        }
        return sum;
    }

    private double getSumPriceForFewProducts(List<Product> products, List<Coupon> coupons) {
        double sum = 0;
        for (Product product : products) {
            for (Coupon coupon : coupons) {
                if (product.getCategory().equals(coupon.getCategory())) {
                    double priceAfterDiscount = getPriceAfterDiscount(product, coupon.getDiscountValueInPercents());
                    sum += priceAfterDiscount;
                } else {
                    sum += product.getPrice();
                }
            }
        }
        return sum;
    }

    private double getPriceAfterDiscount(Product product, double coupon) {
        double tempPrice = product.getPrice() - (product.getPrice() * coupon) / 100;
        return (double) Math.round(tempPrice * 100) / 100;
    }

}