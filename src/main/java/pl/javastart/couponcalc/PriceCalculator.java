package pl.javastart.couponcalc;

import java.util.List;

public class PriceCalculator {

    public double calculatePrice(List<Product> products, List<Coupon> coupons) {
        if (products == null) {
            return 0;
        }
        if (coupons == null || coupons.isEmpty()) {
            return products.stream()
                    .mapToDouble(Product::getPrice)
                    .sum();
        }

        double lowestPriceSum = Double.MAX_VALUE;
        for (Coupon coupon : coupons) {
            double sumWithCoupon = calculatePriceForCoupon(products, coupon);
            if (lowestPriceSum > sumWithCoupon) {
                lowestPriceSum = sumWithCoupon;
            }
        }
        return lowestPriceSum;
    }

    private double calculatePriceForCoupon(List<Product> products, Coupon coupon) {
        double sum = 0;
        for (Product product : products) {
            if (product.getCategory().equals(coupon.getCategory())) {
                sum += getPriceAfterDiscount(product, coupon.getDiscountValueInPercents());
            } else {
                sum += product.getPrice();
            }
        }
        return sum;
    }

    private double getPriceAfterDiscount(Product product, double discount) {
        double tempPrice = product.getPrice() - (product.getPrice() * discount) / 100;
        return (double) Math.round(tempPrice * 100) / 100;
    }

}