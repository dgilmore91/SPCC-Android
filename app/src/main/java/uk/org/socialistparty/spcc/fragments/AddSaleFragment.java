package uk.org.socialistparty.spcc.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.Locale;

import uk.org.socialistparty.spcc.R;
import uk.org.socialistparty.spcc.util.CurrencyFilter;


public class AddSaleFragment extends Fragment {
    private static final String PAPERS_SOLD = "papers_sold";
    private static final String FIGHTING_FUND_RAISED = "fighting_fund_raised";

    private int papersSold = 0;
    private float fundRaised = 0;

    private EditText paperTextView;
    private EditText fundTextView;

    private OnValueChangedListener mListener;

    public AddSaleFragment() {
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
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View containerView = inflater.inflate(
                R.layout.fragment_add_sale, container, false
        );
        initTextFields(containerView);
        return containerView;
    }

    public void initTextFields(View container) {
        paperTextView = container.findViewById(R.id.paper_sale_container_input);
        paperTextView.setText(String.valueOf(papersSold));

        fundTextView = container.findViewById(R.id.fighting_fund_sale_container_input);
        fundTextView.setFilters(new InputFilter[] {new CurrencyFilter()});
        fundTextView.setText(String.valueOf(fundRaised));
        fundTextView.setOnFocusChangeListener(new FightingFundFocusListener());
        convertFundToCurrency();
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

    public void convertFundToCurrency() {
        Float enteredValue = Float.parseFloat(fundTextView.getText().toString());
        String formattedValue = String.format(Locale.getDefault(), "%.2f", enteredValue);
        fundTextView.setText(formattedValue);
    }

    public interface OnValueChangedListener {
        void onAddSaleValueChange(Pair valueChangePair);
    }

    public class FightingFundFocusListener implements View.OnFocusChangeListener {

        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            if(!hasFocus) {
                convertFundToCurrency();
            }
        }
    }
}
