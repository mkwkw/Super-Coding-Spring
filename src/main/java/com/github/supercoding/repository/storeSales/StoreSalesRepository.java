package com.github.supercoding.repository.storeSales;

public interface StoreSalesRepository {
    StoreSalesEntity findStoreSalesById(Integer storeId);

    void updateSalesAmount(Integer storeId, Integer newSalesAmount);
}
