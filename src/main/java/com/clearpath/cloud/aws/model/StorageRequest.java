package com.clearpath.cloud.aws.model;

public class StorageRequest {

    private String name;

    private StorageType storageType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StorageType getStorageType() {
        return storageType;
    }
}
