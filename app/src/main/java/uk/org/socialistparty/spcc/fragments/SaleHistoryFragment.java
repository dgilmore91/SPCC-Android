package uk.org.socialistparty.spcc.fragments;

import android.content.Context;
import android.net.Uri;
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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SaleHistoryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SaleHistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SaleHistoryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private List<Sale> sales;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter recyclerAdapter;

    private OnFragmentInteractionListener mListener;

    public SaleHistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SaleHistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SaleHistoryFragment newInstance(String param1, String param2) {
        SaleHistoryFragment fragment = new SaleHistoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
                "gneiang",
                true));
        sales.add(new Sale(
                1526985691631L,
                5,
                0,
                1044099691633L,
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
        recyclerView = (RecyclerView) containerView.findViewById(R.id.sales_history_recycler_view);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerAdapter = new SalesRecyclerAdapter(sales);
        recyclerView.setAdapter(recyclerAdapter);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
