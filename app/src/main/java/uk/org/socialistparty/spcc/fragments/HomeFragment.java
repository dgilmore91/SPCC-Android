package uk.org.socialistparty.spcc.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import uk.org.socialistparty.spcc.R;
import uk.org.socialistparty.spcc.activities.HomeActivity;

public class HomeFragment extends Fragment {


    public HomeFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parentView =  inflater.inflate(R.layout.fragment_home, container, false);

        View addSaleButton = parentView.findViewById(R.id.home_fragment_add_sale_button);
        View viewHistoryButton = parentView.findViewById(R.id.home_fragment_view_history_button);
        View latestNewsButton = parentView.findViewById(R.id.home_fragment_latest_news_button);

        View.OnClickListener buttonClickListener = new HomeFragmentButtonClickListener();

        addSaleButton.setOnClickListener(buttonClickListener);
        viewHistoryButton.setOnClickListener(buttonClickListener);
        latestNewsButton.setOnClickListener(buttonClickListener);

        return parentView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public class HomeFragmentButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View selectedButton) {
            int fragmentId = 0;

            switch (selectedButton.getId()){
                case R.id.home_fragment_add_sale_button:
                    fragmentId = R.id.nav_add_sale;
                    break;
                case R.id.home_fragment_view_history_button:
                    fragmentId = R.id.nav_sale_history;
                    break;
                case R.id.home_fragment_latest_news_button:
                    fragmentId = R.id.nav_news;
                    break;
            }

            if (fragmentId != 0) { ((HomeActivity)getActivity()).moveToFragment(fragmentId); }
        }
    }
}
