package com.github.supercoding.web.controller;

import com.github.supercoding.web.dto.Item;
import com.github.supercoding.web.dto.ItemBody;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ElectronicStoreController {

    private static int serialItemId = 1;
    private List<Item> items = new ArrayList<>();

    @GetMapping("/items")
    public List<Item> findAllItem(){
        return items;
    }

    @GetMapping("/items/{id}")
    public Item findItemByPathId(@PathVariable String id){//items/1
        Item itemFounded = items.stream()
                .filter((item->item.getId().equals(id)))
                .findFirst()
                .orElseThrow(()-> new RuntimeException());

        return itemFounded;
    }

    @GetMapping("/items-query")
    public Item findItemByQuery(@RequestParam("id") String id){ //items-query?id=1
        Item itemFounded = items.stream()
                .filter((item->item.getId().equals(id)))
                .findFirst()
                .orElseThrow(()-> new RuntimeException());

        return itemFounded;
    }

    @GetMapping("/items-queries")
    public List<Item> findItemByQueryIds(@RequestParam("id") List<String> ids){ //items-queries?id=1&id=2&id=3

        Set<String> idSet = ids.stream().collect(Collectors.toSet());

        List<Item> itemsFound = items.stream()
                .filter(item ->idSet.contains(item.getId()))
                .collect(Collectors.toList());

        return itemsFound;
    }

    @PostMapping("/items")
    public String registerItem(@RequestBody ItemBody itemBody) {
        Item newItem = new Item(serialItemId++, itemBody);
        items.add(newItem);
        return "ID: "+newItem.getId();
    }

    @DeleteMapping("/items/{id}")
    public String deleteItemById(@PathVariable String id){
        Item itemFounded = items.stream()
                .filter((item->item.getId().equals(id)))
                .findFirst()
                .orElseThrow(()-> new RuntimeException());

        items.remove(itemFounded);
        return "Object with id = "+itemFounded.getId()+" has been deleted";
    }

    @PutMapping("/items/{id}")
    public Item updateItem(@PathVariable String id, @RequestBody ItemBody itemBody){

        //자바 내부에서 수정 -> 없애고 다시 넣는 게 나음.
        //일반적으로는 해당하는 것 찾아서 수정하고 저장하기기
       Item itemFounded = items.stream()
                .filter((item->item.getId().equals(id)))
                .findFirst()
                .orElseThrow(()-> new RuntimeException());

        items.remove(itemFounded);

        Item itemUpdated = new Item(Integer.valueOf(id), itemBody);
        items.add(itemUpdated);

        return itemUpdated;
    }
}
