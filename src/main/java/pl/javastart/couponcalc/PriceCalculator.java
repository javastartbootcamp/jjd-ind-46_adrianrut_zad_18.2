package pl.javastart.couponcalc;

import java.util.List;

public class PriceCalculator {

    public double calculatePrice(List<Product> products, List<Coupon> coupons) {
        double price = 0;
        if (products == null) {
            return 0;
        }
        for (Product product : products) {
            if (coupons == null) {
                price += product.getPrice();
            } else {
                for (Coupon coupon : coupons) {
                    if (products.size() > 1) {
                        price = getSumPriceForFewProducts(products, coupons);
                        if (coupons.size() > 1) {
                            price = calculatePriceForCoupon(products, coupon);
                        }
                    } else if (product.getCategory().equals(coupon.getCategory())) {
                        price = getPriceAfterDiscount(product, coupon.getDiscountValueInPercents());
                    }
                }
            }

        }
        return price;
    }

    private double calculatePriceForCoupon(List<Product> products, Coupon coupon) {
        double sum = 0;
        for (Product product : products) {
            if (coupon != null) {
                if (product.getCategory().equals(coupon.getCategory())) {
                    sum = getPriceAfterDiscount(product, coupon.getDiscountValueInPercents());
                } else {
                    sum += product.getPrice();
                }
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