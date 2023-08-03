package com.github.supercoding.web.controller;

import com.github.supercoding.service.ElectronicStoreItemService;
import com.github.supercoding.web.dto.BuyOrder;
import com.github.supercoding.web.dto.Item;
import com.github.supercoding.web.dto.ItemBody;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ElectronicStoreController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ElectronicStoreItemService electronicStoreItemService; //@RequiredArgsConstructor로 생성자 따로 코드 작성 없이 Bean 주입 가능

//    public ElectronicStoreController(ElectronicStoreItemService electronicStoreItemService) {
//        this.electronicStoreItemService = electronicStoreItemService;
//    }

    @ApiOperation("모든 item을 검색하는 API")
    @GetMapping("/items")
    public List<Item> findAllItem(){
        logger.info("GET /items 요청이 들어왔습니다.");
        List<Item> items = electronicStoreItemService.findAllItems();
        logger.info("GET /items 응답: "+items);
        return items;
    }

    @ApiOperation("item id로 검색 - 경로 변수")
    @GetMapping("/items/{id}")
    public Item findItemByPathId(
            @ApiParam(name = "id", value = "item ID", example = "1")
            @PathVariable String id){//items/1
//        Item itemFounded = items.stream()
//                .filter((item->item.getId().equals(id)))
//                .findFirst()
//                .orElseThrow(()-> new RuntimeException());
        return electronicStoreItemService.findItemById(id);
    }

    @ApiOperation("item id로 검색 - 쿼리 스트링")
    @GetMapping("/items-query")
    public Item findItemByQuery(
            @ApiParam(name = "id", value = "item ID", example = "1")
            @RequestParam("id") String id){ //items-query?id=1
//        Item itemFounded = items.stream()
//                .filter((item->item.getId().equals(id)))
//                .findFirst()
//                .orElseThrow(()-> new RuntimeException());
        return electronicStoreItemService.findItemById(id);
    }

    @ApiOperation("여러 개의 item id로 검색 - 쿼리 스트링")
    @GetMapping("/items-queries")
    public List<Item> findItemByQueryIds(
            @ApiParam(name = "ids", value = "item IDs", example = "[1,2,3]")
            @RequestParam("id") List<String> ids){ //items-queries?id=1&id=2&id=3


//        Set<String> idSet = ids.stream().collect(Collectors.toSet());
//
//        List<Item> itemsFound = items.stream()
//                .filter(item ->idSet.contains(item.getId()))
//                .collect(Collectors.toList());

        return electronicStoreItemService.findItemsByIds(ids);
    }

    @ApiOperation("item 등록")
    @PostMapping("/items")
    public String registerItem(@RequestBody ItemBody itemBody) {
//        Item newItem = new Item(serialItemId++, itemBody);
//        items.add(newItem);
        Integer itemId = electronicStoreItemService.saveItem(itemBody);

        return "ID: "+itemId;
    }

    @ApiOperation("item id로 삭제")
    @DeleteMapping("/items/{id}")
    public String deleteItemById(
            @ApiParam(name = "id", value = "item ID", example = "1")
            @PathVariable String id){

        electronicStoreItemService.deleteItem(id);
        return "Object with id = "+id+" has been deleted";
    }

    @ApiOperation("item id로 찾아서 내용 수정")
    @PutMapping("/items/{id}")
    public Item updateItem(
            @ApiParam(name = "id", value = "item ID", example = "1")
            @PathVariable String id, @RequestBody ItemBody itemBody){

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

    @ApiOperation("item 구매")
    @PostMapping("/items/buy")
    public String buyItem(@RequestBody BuyOrder buyOrder){
        Integer orderItemNums = electronicStoreItemService.buyItems(buyOrder);
        return "요청하신 Item 중 "+orderItemNums+"개를 구매하였습니다.";
    }
}
