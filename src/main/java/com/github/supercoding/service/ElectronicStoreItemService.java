package com.github.supercoding.service;

import com.github.supercoding.repository.items.ElectronicStoreItemRepository;
import com.github.supercoding.repository.items.ItemEntity;
import com.github.supercoding.repository.storeSales.StoreSalesEntity;
import com.github.supercoding.repository.storeSales.StoreSalesRepository;
import com.github.supercoding.service.mapper.ItemMapper;
import com.github.supercoding.web.dto.BuyOrder;
import com.github.supercoding.web.dto.Item;
import com.github.supercoding.web.dto.ItemBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ElectronicStoreItemService {

    //@Slf4j로 생성, log.info(), log.error() 등으로 사용
    //private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ElectronicStoreItemRepository electronicStoreItemRepository;
    private final StoreSalesRepository storeSalesRepository;

//    public ElectronicStoreItemService(ElectronicStoreItemRepository electronicStoreItemRepository, StoreSalesRepository storeSalesRepository) {
//        this.electronicStoreItemRepository = electronicStoreItemRepository;
//        this.storeSalesRepository = storeSalesRepository;
//    }

    public List<Item> findAllItems() {
        List<ItemEntity> itemEntities = electronicStoreItemRepository.findAllItems();
        //return itemEntities.stream().map(Item::new).collect(Collectors.toList()); //ItemEntity->Item
        return itemEntities.stream().map(ItemMapper.INSTANCE::itemEntityToItem).collect(Collectors.toList()); //ItemEntity->Item
    }

    public Item findItemById(String id){
        ItemEntity itemFounded = electronicStoreItemRepository.findItemById(id);
        //return new Item(itemFounded);
        Item item = ItemMapper.INSTANCE.itemEntityToItem(itemFounded);
        return item;
    }

    public List<Item> findItemsByIds(List<String> ids){
        List<Item> items = new ArrayList<>();
        for(String id : ids){
            Item item = ItemMapper.INSTANCE.itemEntityToItem(electronicStoreItemRepository.findItemById(id));
            items.add(item);
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
        Item itemUpdated = ItemMapper.INSTANCE.itemEntityToItem(itemEntityUpdated);
        return itemUpdated;
    }

    @Transactional(transactionManager = "tm1")
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
        if(itemEntity.getStock()<=0) {
            log.error("재고가 없습니다.");
            throw new RuntimeException("상품의 재고가 없습니다.");
        }

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
