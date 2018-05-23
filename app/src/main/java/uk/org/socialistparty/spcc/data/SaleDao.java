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

    @Query("SELECT * FROM sales ORDER BY sale_date")
    List<Sale> getAllOrderedByDate();

    @Query("SELECT * FROM sales ORDER BY sale_date DESC")
    List<Sale> getAllOrderedByDateDesc();

    @Query("SELECT * FROM sales ORDER BY fund_raised")
    List<Sale> getAllOrderedByFund();

    @Query("SELECT * FROM sales ORDER BY fund_raised DESC")
    List<Sale> getAllOrderedByFundDesc();

    @Query("SELECT * FROM sales ORDER BY papers_sold")
    List<Sale> getAllOrderedByPapers();

    @Query("SELECT * FROM sales ORDER BY papers_sold DESC")
    List<Sale> getAllOrderedByPapersDesc();

    @Query("SELECT * FROM sales ORDER BY is_paid ")
    List<Sale> getAllOrderedByPaid();

    @Query("SELECT * FROM sales ORDER BY is_paid DESC")
    List<Sale> getAllOrderedByPaidDesc();

    @Query("SELECT * FROM sales WHERE is_paid")
    List<Sale> getAllPaidSales();

    @Query("SELECT * FROM sales WHERE NOT is_paid")
    List<Sale> getAllUnpaidSales();

    @Query("SELECT * FROM sales WHERE sale_id IN (:saleIds)")
    List<Sale> loadAllByIds(int[] saleIds);

    @Insert
    void insertAll(Sale... sales);

    @Delete
    void delete(Sale sale);
}
