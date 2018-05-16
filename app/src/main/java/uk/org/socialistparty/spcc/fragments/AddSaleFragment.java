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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.Locale;

import uk.org.socialistparty.spcc.R;
import uk.org.socialistparty.spcc.util.CurrencyFilter;


public class AddSaleFragment extends Fragment {
    private static final String PAPERS_SOLD = "papers_sold";
    private static final String FIGHTING_FUND_RAISED = "fighting_fund_raised";

    private int papersSold = 0;
    private float fundRaised = 0;
    private int day = 0;
    private int month = 0;
    private int year = 0;
    private String notes;
    private boolean isPaid = false;

    private EditText paperTextView;
    private EditText fundTextView;
    private EditText dayTextView;
    private EditText monthTextView;
    private EditText yearTextView;
    private EditText notesTextView;
    private CheckBox paidCheck;
    private Button addSaleButton;


    public AddSaleFragment() {
    }

    public static AddSaleFragment newInstance(
            int papersSold,
            float fundRaised) {
        AddSaleFragment fragment = new AddSaleFragment();
        Bundle args = new Bundle();
        args.putInt(PAPERS_SOLD, papersSold);
        args.putFloat(FIGHTING_FUND_RAISED, fundRaised);
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
        setInputFields(containerView);
        initInputFields();
        return containerView;
    }

    public void setInputFields(View container) {
        paperTextView = container.findViewById(R.id.paper_sale_container_input);

        fundTextView = container.findViewById(R.id.fighting_fund_sale_container_input);
        fundTextView.setFilters(new InputFilter[] {new CurrencyFilter()});
        fundTextView.setOnFocusChangeListener(new FightingFundFocusListener());

        dayTextView = container.findViewById(R.id.day_input);
        monthTextView = container.findViewById(R.id.month_input);
        yearTextView = container.findViewById(R.id.year_input);

        notesTextView = container.findViewById(R.id.notes_input);

        paidCheck = container.findViewById(R.id.paid_checkbox_input);

        addSaleButton = container.findViewById(R.id.paper_sale_confirm_button);
        addSaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddSalePressed(view);
            }
        });
    }

    public void initInputFields() {
        if (papersSold > 0) { paperTextView.setText(String.valueOf(papersSold)); }

        if (fundRaised > 0) {
            fundTextView.setText(String.valueOf(fundRaised));
            convertFundToCurrency();
        }

        if (day > 0) { dayTextView.setText(String.valueOf(day)); }
        if (month > 0) { monthTextView.setText(String.valueOf(month)); }
        if (year > 0) { yearTextView.setText(String.valueOf(year)); }

        if (notes != null) { notesTextView.setText(notes); }

        paidCheck.setChecked(isPaid);
    }

    public void onAddSalePressed(View button) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void convertFundToCurrency() {
        Float enteredValue = Float.parseFloat(fundTextView.getText().toString());
        String formattedValue = String.format(Locale.getDefault(), "%.2f", enteredValue);
        fundTextView.setText(formattedValue);
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
