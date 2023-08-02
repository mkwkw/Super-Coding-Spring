package com.github.supercoding.electronic_store.web.controller;

import com.github.supercoding.electronic_store.service.ElectronicStoreItemService;
import com.github.supercoding.electronic_store.web.dto.BuyOrder;
import com.github.supercoding.electronic_store.web.dto.Item;
import com.github.supercoding.electronic_store.web.dto.ItemBody;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ElectronicStoreController {

    private ElectronicStoreItemService electronicStoreItemService;

    public ElectronicStoreController(ElectronicStoreItemService electronicStoreItemService) {
        this.electronicStoreItemService = electronicStoreItemService;
    }

    @GetMapping("/items")
    public List<Item> findAllItem(){
        return electronicStoreItemService.findAllItems();
    }

    @GetMapping("/items/{id}")
    public Item findItemByPathId(@PathVariable String id){//items/1
//        Item itemFounded = items.stream()
//                .filter((item->item.getId().equals(id)))
//                .findFirst()
//                .orElseThrow(()-> new RuntimeException());
        return electronicStoreItemService.findItemById(id);
    }

    @GetMapping("/items-query")
    public Item findItemByQuery(@RequestParam("id") String id){ //items-query?id=1
//        Item itemFounded = items.stream()
//                .filter((item->item.getId().equals(id)))
//                .findFirst()
//                .orElseThrow(()-> new RuntimeException());
        return electronicStoreItemService.findItemById(id);
    }

    @GetMapping("/items-queries")
    public List<Item> findItemByQueryIds(@RequestParam("id") List<String> ids){ //items-queries?id=1&id=2&id=3


//        Set<String> idSet = ids.stream().collect(Collectors.toSet());
//
//        List<Item> itemsFound = items.stream()
//                .filter(item ->idSet.contains(item.getId()))
//                .collect(Collectors.toList());

        return electronicStoreItemService.findItemsByIds(ids);
    }

    @PostMapping("/items")
    public String registerItem(@RequestBody ItemBody itemBody) {
//        Item newItem = new Item(serialItemId++, itemBody);
//        items.add(newItem);
        Integer itemId = electronicStoreItemService.saveItem(itemBody);

        return "ID: "+itemId;
    }

    @DeleteMapping("/items/{id}")
    public String deleteItemById(@PathVariable String id){

        electronicStoreItemService.deleteItem(id);
        return "Object with id = "+id+" has been deleted";
    }

    @PutMapping("/items/{id}")
    public Item updateItem(@PathVariable String id, @RequestBody ItemBody itemBody){

        //자바 내부에서 수정 -> 없애고 다시 넣는 게 나음.
        //일반적으로는 해당하는 것 찾아서 수정하고 저장하기기
//       Item itemFounded = items.stream()
//                .filter((item->item.getId().equals(id)))
//                .findFirst()
//                .orElseThrow(()-> new RuntimeException());
//
//        items.remove(itemFounded);
//
//        Item itemUpdated = new Item(Integer.valueOf(id), itemBody);
//        items.add(itemUpdated);


        return electronicStoreItemService.updateItem(id, itemBody);
    }

    @PostMapping("/items/buy")
    public String buyItem(@RequestBody BuyOrder buyOrder){
        Integer orderItemNums = electronicStoreItemService.buyItems(buyOrder);
        return "요청하신 Item 중 "+orderItemNums+"개를 구매하였습니다.";
    }
}
