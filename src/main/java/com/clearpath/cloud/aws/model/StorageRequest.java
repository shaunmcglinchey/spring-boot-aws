package com.clearpath.cloud.aws.model;

public class StorageRequest {

    private OrderResult name;

    private StorageType storageType;

    public OrderResult getName() {
        return name;
    }

    public void setName(OrderResult name) {
        this.name = name;
    }

    public StorageType getStorageType() {
        return storageType;
    }
}
