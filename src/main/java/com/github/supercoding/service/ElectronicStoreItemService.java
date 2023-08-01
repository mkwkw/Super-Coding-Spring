package com.github.supercoding.service;

import com.github.supercoding.repository.ElectronicStoreItemRepository;
import com.github.supercoding.repository.ItemEntity;
import com.github.supercoding.web.dto.Item;
import com.github.supercoding.web.dto.ItemBody;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ElectronicStoreItemService {

    private ElectronicStoreItemRepository electronicStoreItemRepository;

    public List<Item> findAllItems() {
        List<ItemEntity> itemEntities = electronicStoreItemRepository.findAllItems();
        return itemEntities.stream().map(Item::new).collect(Collectors.toList());
    }

    public Item findItemById(String id){
        Item itemFounded = electronicStoreItemRepository.findItemById(id);
        return itemFounded;
    }

    public List<Item> findItemsByIds(List<String> ids){
        List<Item> items = new ArrayList<>();
        for(String id : ids){
            items.add(electronicStoreItemRepository.findItemById(id));
        }

        return items;
    }

    public Integer saveItem(ItemBody itemBody){
        ItemEntity itemEntity = new ItemEntity(null, itemBody.getName(), itemBody.getType(), itemBody.getPrice(),
                itemBody.getSpec().getCpu(), itemBody.getSpec().getCapacity());
        electronicStoreItemRepository.saveItem(itemEntity);
        return itemEntity.getId();
    }

    public void deleteItem(String id) {
        Integer idInt = Integer.parseInt(id);
        electronicStoreItemRepository.deleteItem(idInt);
    }

    public Item updateItem(String id, ItemBody itemBody) {
        Integer idInt = Integer.valueOf(id);
        ItemEntity itemEntity = new ItemEntity(idInt, itemBody.getName(), itemBody.getType(), itemBody.getPrice(), itemBody.getSpec().getCpu(), itemBody.getSpec().getCpu());
        ItemEntity itemEntityUpdated = electronicStoreItemRepository.updateItemEntity(idInt, itemEntity);
        Item itemUpdated = new Item(itemEntityUpdated);
        return itemUpdated;
    }
}
