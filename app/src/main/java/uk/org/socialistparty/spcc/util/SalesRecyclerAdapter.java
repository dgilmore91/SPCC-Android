package uk.org.socialistparty.spcc.util;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import uk.org.socialistparty.spcc.R;
import uk.org.socialistparty.spcc.data.Sale;

public class SalesRecyclerAdapter extends RecyclerView.Adapter<SalesRecyclerAdapter.ViewHolder> {
    private List<Sale> sales;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout itemBaseLayout;
        ViewHolder(ConstraintLayout v) {
            super(v);
            itemBaseLayout = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public SalesRecyclerAdapter(List<Sale> sales) {
        this.sales = sales;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SalesRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        ConstraintLayout v = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sale_history_list_item, parent, false);

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Sale sale = sales.get(position);
        ConstraintLayout baseLayout = holder.itemBaseLayout;
        TextView dateView = baseLayout.findViewById(R.id.sale_history_list_item_date_view);
        TextView paperView = baseLayout.findViewById(R.id.sale_history_list_item_paper_view);
        TextView fundView = baseLayout.findViewById(R.id.sale_history_list_item_fund_view);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(sale.getSaleDate());
        DateFormat formatter = java.text.DateFormat.getDateInstance(DateFormat.FULL);
        String formatted = formatter.format(calendar.getTime());

        dateView.setText(formatted);
        paperView.setText(String.valueOf(sale.getPapersSold()));
        fundView.setText(String.valueOf(sale.getFundRaised()));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return sales.size();
    }
}
