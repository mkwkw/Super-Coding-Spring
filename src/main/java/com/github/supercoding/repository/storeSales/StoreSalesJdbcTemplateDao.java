//package com.github.supercoding.repository.storeSales;
//
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public class StoreSalesJdbcTemplateDao implements StoreSalesRepository{
//
//    private JdbcTemplate jdbcTemplate;
//
//    public StoreSalesJdbcTemplateDao(@Qualifier("jdbcTemplate1") JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    //테이블 컬럼 이름과 연결
//    static RowMapper<StoreSalesEntity> storeSalesEntityRowMapper = ((rs, rowNum) ->
//            new StoreSalesEntity.StoreSalesEntityBuilder()
//                    .id(rs.getInt("id"))
//                    .storeName(rs.getNString("store_name"))
//                    .amount(rs.getInt("amount"))
//                    .build()
//            );
//
//    @Override
//    public StoreSalesEntity findStoreSalesById(Integer storeId) {
//
//        return jdbcTemplate.queryForObject("SELECT * FROM store_sales WHERE id = ?", storeSalesEntityRowMapper, storeId);
//    }
//
//    @Override
//    public void updateSalesAmount(Integer storeId, Integer newSalesAmount) {
//        jdbcTemplate.update("UPDATE store_sales SET amount = ? WHERE id = ?", newSalesAmount, storeId);
//    }
//}
