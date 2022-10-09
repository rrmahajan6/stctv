package com.qa.util;

public enum GetAmount {
    KSALiteDiscovery(17.25f),
    KSAClassicDiscovery(17.25f),
    KSAPremiumDiscovery(0),

    KuwaitLiteDiscovery(1.15f),
    KuwaitPremiumDiscovery(0),
    KuwaitClassicDiscovery(1.15f),

    BahrainLiteDiscovery(1.60f),
    BahrainClassicDiscovery(1.60f),
    BahrainPremiumDiscovery(0),

    KSALiteSports(23.00f),
    KSAClassicSports(23.00f),
    KSAPremiumSports(23.00f),

    KuwaitLiteSports(1.67f),
    KuwaitPremiumSports(1.67f),
    KuwaitClassicSports(1.67f),

    BahrainLiteSports(2.00f),
    BahrainClassicSports(2.00f),
    BahrainPremiumSports(2.00f),

    KSALite(15.00f),
    KSAClassic(25.00f),
    KSAPremium(60.00f),

    KuwaitLite(1.20f),
    KuwaitClassic(2.50f),
    KuwaitPremium(4.80f),

    BahrainLite(2.00f),
    BahrainClassic(3.00f),
    BahrainPremium(6.00f);

    private final float price;

    GetAmount(float price) {
        this.price = price;
    }

    public float getPrice() {
        return price;
    }
}

