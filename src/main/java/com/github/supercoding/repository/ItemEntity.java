package com.github.supercoding.repository;

import java.util.Objects;

public class ItemEntity {

    //필드 정의
    private Integer id;
    private String name;
    private String type;
    private Integer price;
    private String cpu;
    private String capacity;

    //생성자
    public ItemEntity(Integer id, String name, String type, Integer price, String cpu, String capacity) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.cpu = cpu;
        this.capacity = capacity;
    }

    //getter
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Integer getPrice() {
        return price;
    }

    public String getCpu() {
        return cpu;
    }

    public String getCapacity() {
        return capacity;
    }

    //setter
    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    //equals, hashcode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemEntity that = (ItemEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
