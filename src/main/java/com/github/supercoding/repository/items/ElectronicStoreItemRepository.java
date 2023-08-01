package com.github.supercoding.repository.items;

import com.github.supercoding.web.dto.Item;

import java.util.List;

public interface ElectronicStoreItemRepository {
    List<ItemEntity> findAllItems();

    Integer saveItem(ItemEntity itemEntity);

    ItemEntity findItemById(String id);

    ItemEntity updateItemEntity(Integer idInt, ItemEntity itemEntity);

    void deleteItem(Integer idInt);

    void updateItemStock(Integer itemId, Integer newStock);
}
