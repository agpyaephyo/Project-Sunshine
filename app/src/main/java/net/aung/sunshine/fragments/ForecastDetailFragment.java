package net.aung.sunshine.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.aung.sunshine.R;

/**
 * Use the {@link ForecastDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForecastDetailFragment extends BaseFragment {

    private static final String ARG_DT = "ARG_DT";

    // TODO: Rename and change types of parameters
    private long dt;

    public ForecastDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param dt unique dateTime value for daily weather data.
     * @return A new instance of fragment ForecastDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ForecastDetailFragment newInstance(long dt) {
        ForecastDetailFragment fragment = new ForecastDetailFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_DT, dt);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void readArguments(Bundle bundle) {
        super.readArguments(bundle);
        dt = bundle.getLong(ARG_DT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forecast_detail, container, false);
    }
}
