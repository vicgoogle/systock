package com.br.systock.model;

public class Products {

    private String product;

    private String category;

    private String type;

    private String description;



    public Products(String product, String category, String type, String description) {

        this.product = product;
        this.category = category;
        this.type = type;
        this.description = description;
    }

    public Products() {
    }


    public Products(String toString, String toString1, String toString2, String toString3, String toString4) {

    }

    public String getProduct() {
        return this.product;
    }

    public String getType() {
        return this.type;
    }

    public String getCategory() {
        return this.category;
    }

    public String getDescription() {
        return this.description;
    }


    public void setProduct(String Product) {
        this.product = product;
    }

    public void setCategory(String Category) {
        this.category = category;
    }

    public void setType(String Type) {
        this.type = type;
    }

    public void setDescription(String Description) {
        this.description = description;
    }

}


