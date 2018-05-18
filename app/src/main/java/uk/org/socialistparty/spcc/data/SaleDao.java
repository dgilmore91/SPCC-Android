package uk.org.socialistparty.spcc.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface SaleDao {
    @Query("SELECT * FROM sales")
    List<Sale> getAll();

    @Query("SELECT * FROM sales WHERE sale_id IN (:saleIds)")
    List<Sale> loadAllByIds(int[] saleIds);

    @Insert
    void insertAll(Sale... sales);

    @Delete
    void delete(Sale sale);
}
