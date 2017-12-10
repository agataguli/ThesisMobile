package com.thesis.visageapp.objects;

public class ProductFilter {
    private String productName;
    private String productBrand;
    private Double productPriceMin = 0.0;
    private Double productPriceMax = 100000.0;
    private boolean productCategoryFurniture;
    private boolean productCategoryBrushes;
    private boolean productCategoryAccessories;

    public ProductFilter() {
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public void setProductPriceMin(Double productPriceMin) {
        this.productPriceMin = productPriceMin;
    }

    public void setProductPriceMax(Double productPriceMax) {
        this.productPriceMax = productPriceMax;
    }

    public void setProductPriceMin(String productPriceMin) {
        if(!productPriceMin.isEmpty()) {
            this.productPriceMin =Double.parseDouble(productPriceMin);
        }
    }

    public void setProductPriceMax(String productPriceMax) {
        if(!productPriceMax.isEmpty()) {
            this.productPriceMax =Double.parseDouble(productPriceMax);
        }    }

    public void setProductCategoryFurniture(boolean productCategoryFurniture) {
        this.productCategoryFurniture = productCategoryFurniture;
    }

    public void setProductCategoryBrushes(boolean productCategoryBrushes) {
        this.productCategoryBrushes = productCategoryBrushes;
    }

    public void setProductCategoryAccessories(boolean productCategoryAccessories) {
        this.productCategoryAccessories = productCategoryAccessories;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public Double getProductPriceMin() {
        return productPriceMin;
    }

    public Double getProductPriceMax() {
        return productPriceMax;
    }

    public boolean isProductCategoryFurniture() {
        return productCategoryFurniture;
    }

    public boolean isProductCategoryBrushes() {
        return productCategoryBrushes;
    }

    public boolean isProductCategoryAccessories() {
        return productCategoryAccessories;
    }
}
