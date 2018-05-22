package uk.org.socialistparty.spcc.util;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

import uk.org.socialistparty.spcc.R;
import uk.org.socialistparty.spcc.data.Sale;

public class SalesRecyclerAdapter extends RecyclerView.Adapter<SalesRecyclerAdapter.ViewHolder> {
    private List<Sale> sales;
    private Calendar calendar = Calendar.getInstance();
    private DateFormat formatter = java.text.DateFormat.getDateInstance(DateFormat.FULL);

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
        TextView dateView = baseLayout.findViewById(R.id.sale_history_list_item_date_view);
        TextView paperView = baseLayout.findViewById(R.id.sale_history_list_item_paper_view);
        TextView fundView = baseLayout.findViewById(R.id.sale_history_list_item_fund_view);

        calendar.setTimeInMillis(sale.getSaleDate());
        String formatted = formatter.format(calendar.getTime());

        dateView.setText(formatted);
        paperView.setText(String.valueOf(sale.getPapersSold()));
        fundView.setText(String.valueOf(sale.getFundRaised()));

    }

    @Override
    public int getItemCount() {
        return sales.size();
    }
}
