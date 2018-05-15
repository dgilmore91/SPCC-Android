package uk.org.socialistparty.spcc.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import uk.org.socialistparty.spcc.R;


public class AddSaleFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String PAPERS_SOLD = "papers_sold";
    private static final String FIGHTING_FUND_RAISED = "fighting_fund_raised";

    // TODO: Rename and change types of parameters
    private int papersSold;
    private float fundRaised;

    private OnValueChangedListener mListener;

    public AddSaleFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AddSaleFragment newInstance(String papersSold, String fundRaised) {
        AddSaleFragment fragment = new AddSaleFragment();
        Bundle args = new Bundle();
        args.putString(PAPERS_SOLD, papersSold);
        args.putString(PAPERS_SOLD, fundRaised);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            papersSold = getArguments().getInt(PAPERS_SOLD, 0);
            fundRaised = getArguments().getFloat(FIGHTING_FUND_RAISED, 0.0f);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View containerView = inflater.inflate(
                R.layout.fragment_add_sale, container, false
        );
        EditText paperSaleTextView = containerView.findViewById(R.id.paper_sale_container_input);
        System.out.println(papersSold);
        paperSaleTextView.setText(String.valueOf(papersSold));
        return containerView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        Pair valueChangePair = new Pair<>("value", 12);

        if (mListener != null) {
            mListener.onAddSaleValueChange(valueChangePair);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnValueChangedListener) {
            mListener = (OnValueChangedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnValueChangedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnValueChangedListener {
        void onAddSaleValueChange(Pair valueChangePair);
    }
}
