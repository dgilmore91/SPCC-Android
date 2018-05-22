package uk.org.socialistparty.spcc.util;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import uk.org.socialistparty.spcc.R;
import uk.org.socialistparty.spcc.data.Sale;

public class SalesRecyclerAdapter extends RecyclerView.Adapter<SalesRecyclerAdapter.ViewHolder> {
    private List<Sale> sales;
    private Calendar calendar = Calendar.getInstance();
    private SimpleDateFormat dayNameFormat = new SimpleDateFormat(
            "EEEE", Locale.getDefault());
    private SimpleDateFormat dayNumberFormat = new SimpleDateFormat(
            "d", Locale.getDefault());
    private SimpleDateFormat monthAndYearFormat = new SimpleDateFormat(
            "MMMM yy", Locale.getDefault());

    static class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout itemBaseLayout;
        ViewHolder(ConstraintLayout v) {
            super(v);
            itemBaseLayout = v;
        }
    }

    public SalesRecyclerAdapter(List<Sale> sales) {
        this.sales = sales;
    }

    @Override
    public SalesRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ConstraintLayout v = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sale_history_list_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Sale sale = sales.get(position);
        ConstraintLayout baseLayout = holder.itemBaseLayout;
        TextView dayNameView = baseLayout.findViewById(R.id.sale_history_list_item_day_name_view);
        TextView dayNumberView = baseLayout.findViewById(R.id.sale_history_list_item_day_number_view);
        TextView monthAndYearView = baseLayout.findViewById(R.id.sale_history_list_item_month_and_year_name_view);
        TextView paperView = baseLayout.findViewById(R.id.sale_history_list_item_paper_view);
        TextView fundView = baseLayout.findViewById(R.id.sale_history_list_item_fund_view);

        calendar.setTimeInMillis(sale.getSaleDate());
        Date saleDate = calendar.getTime();
        dayNameView.setText(dayNameFormat.format(saleDate));
        dayNumberView.setText(dayNumberFormat.format(saleDate));
        monthAndYearView.setText(monthAndYearFormat.format(saleDate));

        paperView.setText(String.valueOf(sale.getPapersSold()));
        fundView.setText(CurrencyTools.convertToCurrency(sale.getFundRaised()));

        ImageView notesView = baseLayout.findViewById(R.id.sale_history_list_item_notes_view);
        if(sale.getNotes().length() == 0) notesView.setVisibility(View.INVISIBLE);

        ImageView paidView = baseLayout.findViewById(R.id.sale_history_list_item_paid_view);
        if(!sale.getIsPaid()) paidView.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return sales.size();
    }
}
