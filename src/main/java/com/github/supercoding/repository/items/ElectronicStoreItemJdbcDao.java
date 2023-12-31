//package com.github.supercoding.repository.items;
//
//import lombok.Builder;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public class ElectronicStoreItemJdbcDao implements ElectronicStoreItemRepository{
//
//    private final JdbcTemplate jdbcTemplate;
//
//    //RowMapper로 구현하면 필드 순서와 DAO에서의 순서를 일일이 비교해야함.
//    //Builder로 해결
//    static RowMapper<ItemEntity> itemEntityRowMapper = ((rs, rowNum) ->
////            new ItemEntity(
////                    rs.getInt("id"),
////                    rs.getNString("name"),
////                    rs.getNString("type"),
////                    rs.getInt("price"),
////                    rs.getInt("store_id"),
////                    rs.getInt("stock"),
////                    rs.getNString("cpu"),
////                    rs.getNString("capacity")
////            )
//            new ItemEntity.ItemEntityBuilder()
//                    .id(rs.getInt("id"))
//                    .name( rs.getNString("name"))
//                    .type(rs.getNString("type"))
//                    .stock(rs.getInt("stock"))
//                    .capacity(rs.getNString("capacity"))
//                    .cpu(rs.getNString("cpu"))
//                    .price( rs.getInt("price"))
//                    .storeId(rs.getInt("store_id"))
//                    .build()
//
//    );
//
//    //템플릿 지정
//    public ElectronicStoreItemJdbcDao(@Qualifier("jdbcTemplate1") JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//
//    @Override
//    public Integer saveItem(ItemEntity itemEntity) {
//        jdbcTemplate.update("INSERT INTO item(name, type, price, cpu, capacity) VALUES (?,?,?,?,?)",
//                itemEntity.getName(), itemEntity.getType(), itemEntity.getPrice(),
//                itemEntity.getCpu(), itemEntity.getCapacity()
//                );
//
//        ItemEntity itemEntityFounded = jdbcTemplate.queryForObject("SELECT * FROM item WHERE name = ?", itemEntityRowMapper, itemEntity.getName());
//        return itemEntityFounded.getId();
//    }
//
//    @Override
//    public List<ItemEntity> findAllItems() {
//        List<ItemEntity> itemEntities = jdbcTemplate.query("SELECT * FROM item", itemEntityRowMapper);
//
//        //return itemEntities.stream().map(Item::new).collect(Collectors.toList()); //Item 생성자에 itemEntity 넣어서 new Item 생성
//        return itemEntities;
//    }
//
//    @Override
//    public ItemEntity updateItemEntity(Integer idInt, ItemEntity itemEntity) {
//        jdbcTemplate.update("UPDATE item SET name = ?, type=?, price=?, cpu=?, capacity=? WHERE id = ?",
//                itemEntity.getName(), itemEntity.getType(), itemEntity.getPrice(), itemEntity.getCapacity(), itemEntity.getCapacity(), idInt
//                );
//        return jdbcTemplate.queryForObject("SELECT * FROM item WHERE id=?", itemEntityRowMapper, idInt);
//    }
//
//    @Override
//    public ItemEntity findItemById(String id) {
//        ItemEntity itemEntity = jdbcTemplate.queryForObject("SELECT * FROM item WHERE id = ?", itemEntityRowMapper, id);
//        return itemEntity;
//    }
//
//    @Override
//    public void deleteItem(Integer id) {
//        jdbcTemplate.update("DELETE FROM item WHERE id=?", itemEntityRowMapper, id);
//    }
//
//    @Override
//    public void updateItemStock(Integer itemId, Integer newStock) {
//        jdbcTemplate.update("UPDATE item SET stock = ? WHERE id = ?", newStock, itemId);
//    }
//}
