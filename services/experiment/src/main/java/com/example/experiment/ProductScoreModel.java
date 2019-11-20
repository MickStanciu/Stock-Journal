package com.example.experiment;

/**
 * Wrapper class type where we want to combine product code, language code and revman score in a single place
 */
public class ProductScoreModel {

    private String productCode;
    private String languageCode;
    private int revmanScore;

    public ProductScoreModel(String productCode, String languageCode, int revmanScore) {
        this.productCode = productCode;
        this.languageCode = languageCode;
        this.revmanScore = revmanScore;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public int getRevmanScore() {
        return revmanScore;
    }

    @Override
    public String toString() {
        return "ProductScoreModel{" +
                "productCode='" + productCode + '\'' +
                ", languageCode='" + languageCode + '\'' +
                ", revmanScore=" + revmanScore +
                '}';
    }
}
