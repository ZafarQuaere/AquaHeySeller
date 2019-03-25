package com.aquaheyseller.network_call.response_model.product_list;

public class Data {

    private String pName;

    private String dealerId;

    private String imagePath;

    private String price;

    private String IsBrand;

    private String brandId;

    private String rating;

    private String id;

    private String productType;

    public String getPName() {
        return pName;
    }

    public void setPName(String pName) {
        this.pName = pName;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getIsBrand() {
        return IsBrand;
    }

    public void setIsBrand(String IsBrand) {
        this.IsBrand = IsBrand;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    @Override
    public String toString() {
        return "ClassPojo [pName = " + pName + ", dealerId = " + dealerId + ", imagePath = " + imagePath + ", price = " + price + ", IsBrand = " + IsBrand + ", brandId = " + brandId + ", rating = " + rating + ", id = " + id + ", productType = " + productType + "]";
    }
}
