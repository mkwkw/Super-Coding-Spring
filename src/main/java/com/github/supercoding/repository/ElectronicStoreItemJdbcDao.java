package com.github.supercoding.repository;

import com.github.supercoding.web.dto.Item;
import com.github.supercoding.web.dto.ItemBody;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ElectronicStoreItemJdbcDao implements ElectronicStoreItemRepository{

    private JdbcTemplate jdbcTemplate;
    static RowMapper<ItemEntity> itemEntityRowMapper = ((rs, rowNum) ->
            new ItemEntity(
                    rs.getInt("id"),
                    rs.getNString("name"),
                    rs.getNString("type"),
                    rs.getInt("price"),
                    rs.getNString("cpu"),
                    rs.getNString("capacity")
            )
    );

    public ElectronicStoreItemJdbcDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Integer saveItem(ItemEntity itemEntity) {
        jdbcTemplate.update("INSERT INTO item(name, type, price, cpu, capacity) VALUES (?,?,?,?,?)",
                itemEntity.getName(), itemEntity.getType(), itemEntity.getPrice(),
                itemEntity.getCpu(), itemEntity.getCapacity()
                );

        ItemEntity itemEntityFounded = jdbcTemplate.queryForObject("SELECT * FROM item WHERE name = ?", itemEntityRowMapper, itemEntity.getName());
        return itemEntityFounded.getId();
    }

    @Override
    public List<ItemEntity> findAllItems() {
        List<ItemEntity> itemEntities = jdbcTemplate.query("SELECT * FROM item", itemEntityRowMapper);

        //return itemEntities.stream().map(Item::new).collect(Collectors.toList()); //Item 생성자에 itemEntity 넣어서 new Item 생성
        return itemEntities;
    }

    @Override
    public ItemEntity updateItemEntity(Integer idInt, ItemEntity itemEntity) {
        jdbcTemplate.update("UPDATE item SET name = ?, type=?, price=?, cpu=?, capacity=? WHERE id = ?",
                itemEntity.getName(), itemEntity.getType(), itemEntity.getPrice(), itemEntity.getCapacity(), itemEntity.getCapacity(), idInt
                );
        return jdbcTemplate.queryForObject("SELECT * FROM item WHERE id=?", itemEntityRowMapper, idInt);
    }

    @Override
    public Item findItemById(String id) {
        ItemEntity itemEntity = jdbcTemplate.queryForObject("SELECT * FROM item WHERE id = ?", itemEntityRowMapper, id);
        Item item = new Item(itemEntity);
        return item;

    }
}
