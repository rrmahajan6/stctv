package com.qa.util;

public enum CurrencyType {
    Kuwait("KWD"),
    KSA("SAR"),
    Bahrain("BHD");

    String currencyType;
    CurrencyType(String currencyType){
        this.currencyType=currencyType;
    }
    public String getCurrencyType(){
        return currencyType;
    }
}
