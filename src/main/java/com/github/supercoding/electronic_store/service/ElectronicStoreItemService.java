package com.github.supercoding.electronic_store.service;

import com.github.supercoding.electronic_store.repository.items.ElectronicStoreItemRepository;
import com.github.supercoding.electronic_store.repository.items.ItemEntity;
import com.github.supercoding.electronic_store.repository.storeSales.StoreSalesEntity;
import com.github.supercoding.electronic_store.repository.storeSales.StoreSalesRepository;
import com.github.supercoding.electronic_store.web.dto.BuyOrder;
import com.github.supercoding.electronic_store.web.dto.Item;
import com.github.supercoding.electronic_store.web.dto.ItemBody;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ElectronicStoreItemService {

    private ElectronicStoreItemRepository electronicStoreItemRepository;
    private StoreSalesRepository storeSalesRepository;

    public ElectronicStoreItemService(ElectronicStoreItemRepository electronicStoreItemRepository, StoreSalesRepository storeSalesRepository) {
        this.electronicStoreItemRepository = electronicStoreItemRepository;
        this.storeSalesRepository = storeSalesRepository;
    }

    public List<Item> findAllItems() {
        List<ItemEntity> itemEntities = electronicStoreItemRepository.findAllItems();
        return itemEntities.stream().map(Item::new).collect(Collectors.toList());
    }

    public Item findItemById(String id){
        ItemEntity itemFounded = electronicStoreItemRepository.findItemById(id);
        return new Item(itemFounded);
    }

    public List<Item> findItemsByIds(List<String> ids){
        List<Item> items = new ArrayList<>();
        for(String id : ids){
            items.add(new Item(electronicStoreItemRepository.findItemById(id)));
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

    @Transactional
    public Integer buyItems(BuyOrder buyOrder){
        // 1. BuyOrder 에서 상품 ID와 수량을 얻어낸다.
        // 2. 상품을 조회하여 수량이 얼마나 있는 지 확인한다.
        // 3. 상품의 수량과 가격을 가지고 계산하여 총 가격을 구한다.
        // 4. 상품의 재고에 기존 계산한 재고를 구매하는 수량을 뺸다.
        // 5. 상품 구매하는 수량 * 가격 만큼 가계 매상으로 올린다.
        // (단, 재고가 아예 없거나 매장을 찾을 수 없으면 살 수 없다. )

        Integer itemId = buyOrder.getItemId();
        Integer itemNums = buyOrder.getItemNums();

        ItemEntity itemEntity = electronicStoreItemRepository.findItemById(String.valueOf(itemId));
        if(itemEntity.getStoreId()==null) throw new RuntimeException("매장을 찾을 수 없습니다.");
        if(itemEntity.getStock()<=0) throw new RuntimeException("상품의 재고가 없습니다.");

        Integer possibleBuyItemNums;
        //살 수 있는 수량 - 재고가 충분한가 아닌가
        if(itemNums>=itemEntity.getStock()) possibleBuyItemNums = itemEntity.getStock();
        else possibleBuyItemNums = itemNums;

        Integer totalPrice = possibleBuyItemNums*itemEntity.getPrice();

        //Item 재고 감소
        electronicStoreItemRepository.updateItemStock(itemId, itemEntity.getStock() - possibleBuyItemNums);

        //매장 매상 추가
        StoreSalesEntity storeSales = storeSalesRepository.findStoreSalesById(itemEntity.getStoreId());
        storeSalesRepository.updateSalesAmount(itemEntity.getStoreId(), storeSales.getAmount()+totalPrice);

        return possibleBuyItemNums;
    }
}
