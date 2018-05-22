package uk.org.socialistparty.spcc.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import uk.org.socialistparty.spcc.R;
import uk.org.socialistparty.spcc.data.Sale;
import uk.org.socialistparty.spcc.util.SalesRecyclerAdapter;

public class SaleHistoryFragment extends Fragment {
    private List<Sale> sales;

    public SaleHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sales = new ArrayList<>();
        sales.add(new Sale(
                1526985691631L,
                3,
                0,
                1044099691633L,
                "afaefae",
                false));
        sales.add(new Sale(
                1526985691631L,
                3,
                0,
                1044099691633L,
                "",
                true));
        sales.add(new Sale(
                1526985691631L,
                5,
                0,
                1044899691633L,
                "wegauorg",
                false));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View containerView = inflater.inflate(
                R.layout.fragment_sale_history, container, false
        );
        initRecyclerView(containerView);
        return containerView;
    }

    private void initRecyclerView(View containerView){
        RecyclerView recyclerView = containerView.findViewById(R.id.sales_history_recycler_view);

        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.Adapter recyclerAdapter = new SalesRecyclerAdapter(sales);
        recyclerView.setAdapter(recyclerAdapter);
    }

}
