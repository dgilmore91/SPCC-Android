package uk.org.socialistparty.spcc.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "sales")
public class Sale {
    @PrimaryKey
    private int sale_id;

    @ColumnInfo(name = "date_created")
    private int dateCreated;

    @ColumnInfo(name = "papers_sold")
    private int papersSold;

    @ColumnInfo(name = "fund_raised")
    private float fundRaised;

    @ColumnInfo(name = "sale_date")
    private int saleDate;

    @ColumnInfo(name = "notes")
    private String notes;

    @ColumnInfo(name = "is_paid")
    private boolean isPaid;

    public Sale() {

    }

    public Sale(
            int sale_id,
            int dateCreated,
            int papersSold,
            float fundRaised,
            int saleDate,
            String notes,
            boolean isPaid){
        this.sale_id = sale_id;
        this.dateCreated = dateCreated;
        this.papersSold = papersSold;
        this.fundRaised = fundRaised;
        this.saleDate = saleDate;
        this.notes = notes;
        this.isPaid = isPaid;
    }

    public int getSale_id() {
        return sale_id;
    }

    public void setSale_id(int sale_id) {
        this.sale_id = sale_id;
    }

    public int getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(int dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getPapersSold() {
        return papersSold;
    }

    public void setPapersSold(int papersSold) {
        this.papersSold = papersSold;
    }

    public float getFundRaised() {
        return fundRaised;
    }

    public void setFundRaised(float fundRaised) {
        this.fundRaised = fundRaised;
    }

    public float getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(int saleDate) {
        this.saleDate = saleDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }
}

