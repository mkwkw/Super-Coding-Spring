package com.github.supercoding.repository.storeSales;

import org.springframework.data.relational.core.sql.In;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class StoreSalesJdbcTemplateDao implements StoreSalesRepository{

    private JdbcTemplate jdbcTemplate;

    public StoreSalesJdbcTemplateDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //테이블 컬럼 이름과 연결
    static RowMapper<StoreSalesEntity> storeSalesEntityRowMapper = ((rs, rowNum) ->
            new StoreSalesEntity(
                    rs.getInt("id"),
                    rs.getNString("store_name"),
                    rs.getInt("amount")
            ));

    @Override
    public StoreSalesEntity findStoreSalesById(Integer storeId) {

        return jdbcTemplate.queryForObject("SELECT * FROM store_sales WHERE id = ?", storeSalesEntityRowMapper, storeId);
    }

    @Override
    public void updateSalesAmount(Integer storeId, Integer newSalesAmount) {
        jdbcTemplate.update("UPDATE store_sales SET amount = ? WHERE id = ?", newSalesAmount, storeId);
    }
}
