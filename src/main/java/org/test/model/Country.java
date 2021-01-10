package org.test.model;

import java.util.List;

public class Country {

    private String alpha2Code;
    private String capital;
    private String name;
    private List<Currency> currencies;
    private String region;

    public String getAlpha2Code() {
        return alpha2Code;
    }

    public void setAlpha2Code(String alpha2Code) {
        this.alpha2Code = alpha2Code;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return "Country{" +
                "alpha2Code='" + alpha2Code + '\'' +
                ", capital='" + capital + '\'' +
                ", name='" + name + '\'' +
                ", currencies=" + currencies +
                ", region='" + region + '\'' +
                '}';
    }
}
