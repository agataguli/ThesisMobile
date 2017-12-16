package com.thesis.visageapp.domain;

import java.util.List;


public class Cart<T> {
    private List<T> products;

    public Cart() {}

    public Cart(List<T> products) {
        this.products = products;
    }

    public List<T> getAllProducts() {
        return products;
    }

    public void addProduct(T product) {
        products.add(product);
    }

    public void removeProduct(T product) {
        products.remove(product);
    }

    public int getSize() {
        return products.size();
    }

    public void setProducts(List<T> products) {
        this.products = products;
    }
}
