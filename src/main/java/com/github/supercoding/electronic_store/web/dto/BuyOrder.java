package com.github.supercoding.electronic_store.web.dto;

public class BuyOrder {

    private Integer itemId;
    private Integer itemNums;

    public BuyOrder() {
    }

    public BuyOrder(Integer itemId, Integer itemNums) {
        this.itemId = itemId;
        this.itemNums = itemNums;
    }

    public Integer getItemId() {
        return itemId;
    }

    public Integer getItemNums() {
        return itemNums;
    }
}
