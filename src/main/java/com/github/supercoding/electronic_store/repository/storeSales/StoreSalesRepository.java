package com.github.supercoding.electronic_store.repository.storeSales;

public interface StoreSalesRepository {
    StoreSalesEntity findStoreSalesById(Integer storeId);

    void updateSalesAmount(Integer storeId, Integer newSalesAmount);
}
