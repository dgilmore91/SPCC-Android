package uk.org.socialistparty.spcc.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import uk.org.socialistparty.spcc.R;
import uk.org.socialistparty.spcc.activities.HomeActivity;
import uk.org.socialistparty.spcc.data.Sale;
import uk.org.socialistparty.spcc.util.ConversionTools;
import uk.org.socialistparty.spcc.util.CurrencyFilter;

public class AddSaleFragment extends Fragment {
    public static final String INCOMING_SALE_ID = "incoming_sale_id";

    private int saleId = -1;
    private int papersSold = 0;
    private float fundRaised = 0;
    private int day, month, year = 0;
    private String notes;
    private boolean isPaid = false;

    private EditText paperTextView, fundTextView,
            dayTextView, monthTextView, yearTextView,
            notesTextView;
    private CheckBox paidCheck;

    private HomeActivity activity;

    public AddSaleFragment() { }

    public static AddSaleFragment newInstance(
            int incomingSaleId) {
        AddSaleFragment fragment = new AddSaleFragment();
        Bundle args = new Bundle();
        args.putInt(INCOMING_SALE_ID, incomingSaleId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            getExistingSale(getArguments().getInt(INCOMING_SALE_ID));
        }
        if(activity == null){
            activity = (HomeActivity) getActivity();
        }
    }

    private void getExistingSale(final int saleId){
        Observable<List<Sale>> saleObservable = Observable.fromCallable(new Callable<List<Sale>>() {
            @Override
            public List<Sale> call() throws Exception {
                return ((HomeActivity)getActivity()).getSaleForID(saleId);
            }
        });
        saleObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Sale>>() {
                    @Override
                    public void onSubscribe(Disposable d) {}

                    @Override
                    public void onNext(List<Sale> sales) {
                        populateFieldsFromSale(sales.get(0));
                        initInputFields();
                    }

                    @Override
                    public void onError(Throwable e) {}

                    @Override
                    public void onComplete() {}
                });
    }

    private void populateFieldsFromSale(Sale sale){
        saleId = sale.getSale_id();
        papersSold = sale.getPapersSold();
        fundRaised = sale.getFundRaised();
        notes = sale.getNotes();
        isPaid = sale.getIsPaid();
        day = ConversionTools.getDayIntFromTimeStamp(sale.getSaleDate());
        month = ConversionTools.getMonthIntFromTimeStamp(sale.getSaleDate());
        year = ConversionTools.getYearIntFromTimeStamp(sale.getSaleDate());
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

        Button addSaleButton = container.findViewById(R.id.paper_sale_confirm_button);
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

    public void getValues() {
        String papersSoldText = paperTextView.getText().toString();
        String fundRaisedText = fundTextView.getText().toString();
        String dayText = dayTextView.getText().toString();
        String monthText = monthTextView.getText().toString();
        String yearText = yearTextView.getText().toString();

        if (papersSoldText.length() > 0) papersSold = Integer.parseInt(papersSoldText);
        if (fundRaisedText.length() > 0) fundRaised = Float.parseFloat(fundRaisedText);
        if (dayText.length() > 0) day = Integer.parseInt(dayText);
        if (monthText.length() > 0) month = Integer.parseInt(monthText) - 1;
        if (yearText.length() > 0) year = Integer.parseInt(yearText) + 2000;

        notes = notesTextView.getText().toString();
        isPaid = paidCheck.isChecked();
    }

    private String validateValues() {
        boolean arePapersOrFundPresent = (papersSold > 0 || fundRaised > 0);
        boolean isDayCorrectlyFormatted = (day > 0 && day <= 31);
        boolean isMonthCorrectlyFormatted = (month >= 0 && month < 12);
        boolean isYearCorrectlyFormatted = (year > 2000);

        if(!arePapersOrFundPresent){ return "Please enter papers or fighting fund." ;}
        if(!isDayCorrectlyFormatted){ return "Day must be between 1 and 31."; }
        if(!isMonthCorrectlyFormatted){ return "Month must be between 1 and 12."; }
        if(!isYearCorrectlyFormatted){ return "Please enter a year."; }

        return "";
    }

    public void onAddSalePressed(View button) {
        getValues();
        String errorMessage = validateValues();
        if(errorMessage.length() > 0){
            activity.sendMessageToUser(errorMessage);
        }else{
            confirmSale();
        }
    }

    public void confirmSale(){
        Date now = new Date();
        long nowStamp = now.getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        long saleDateStamp = calendar.getTime().getTime();

        final Sale saleToAdd = new Sale(nowStamp, papersSold, fundRaised, saleDateStamp, notes, isPaid);
        if(saleId >= 0) saleToAdd.setSale_id(saleId);
        Observable<Void> confirmObservable = Observable.fromCallable(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                return ((HomeActivity)getActivity()).addSale(saleToAdd);
            }
        });
        confirmObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Void>() {
                    @Override
                    public void onSubscribe(Disposable d) {}
                    @Override
                    public void onNext(Void sales) { }
                    @Override
                    public void onError(Throwable e) {}
                    @Override
                    public void onComplete() {}
                });
    }

    public void convertFundToCurrency() {
        String enteredValue = fundTextView.getText().toString();

        if(!enteredValue.isEmpty()){
            Float floatValue = Float.parseFloat(enteredValue);
            String formattedValue = String.format(Locale.getDefault(), "%.2f", floatValue);
            fundTextView.setText(formattedValue);
        }
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
