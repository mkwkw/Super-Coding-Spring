package com.github.supercoding.repository;

import com.github.supercoding.web.dto.Item;
import com.github.supercoding.web.dto.ItemBody;

import java.util.List;

public interface ElectronicStoreItemRepository {
    List<ItemEntity> findAllItems();

    Integer saveItem(ItemEntity itemEntity);

    Item findItemById(String id);

    ItemEntity updateItemEntity(Integer idInt, ItemEntity itemEntity);
}
