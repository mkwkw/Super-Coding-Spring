package com.github.supercoding.electronic_store.repository.storeSales;

import java.util.Objects;

public class StoreSalesEntity {

    private Integer id;
    private String storeName;
    private Integer amount;

    public StoreSalesEntity() {
    }

    public StoreSalesEntity(Integer id, String storeName, Integer amount) {
        this.id = id;
        this.storeName = storeName;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoreSalesEntity that = (StoreSalesEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
