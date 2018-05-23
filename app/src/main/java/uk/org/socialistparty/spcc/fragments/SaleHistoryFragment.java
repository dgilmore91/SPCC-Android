package uk.org.socialistparty.spcc.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import uk.org.socialistparty.spcc.R;
import uk.org.socialistparty.spcc.activities.HomeActivity;
import uk.org.socialistparty.spcc.data.Sale;
import uk.org.socialistparty.spcc.util.SalesRecyclerAdapter;

public class SaleHistoryFragment extends Fragment {
    private RecyclerView recyclerView;

    public SaleHistoryFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View containerView = inflater.inflate(
                R.layout.fragment_sale_history, container, false
        );
        initRecyclerView(containerView);
        Observable<List<Sale>> saleObservable = Observable.fromCallable(new Callable<List<Sale>>() {
            @Override
            public List<Sale> call() throws Exception {
                return ((HomeActivity)getActivity()).getSales();
            }
        });
        saleObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Sale>>() {
                    @Override
                    public void onSubscribe(Disposable d) {}

                    @Override
                    public void onNext(List<Sale> sales) {
                        saveSales(sales);
                    }

                    @Override
                    public void onError(Throwable e) {}

                    @Override
                    public void onComplete() {}
                });
        return containerView;
    }

    private void saveSales(List<Sale> sales){
        SalesRecyclerAdapter recyclerAdapter = new SalesRecyclerAdapter(sales);
        recyclerView.setAdapter(recyclerAdapter);
    }

    private void initRecyclerView(View containerView){
        recyclerView = containerView.findViewById(R.id.sales_history_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
    }

}
