package com.qtsn.fox.test.weatherfox.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.qtsn.fox.test.weatherfox.fragment.CurrentFragment;
import com.qtsn.fox.test.weatherfox.fragment.ForecastFragment;
import com.qtsn.fox.test.weatherfox.R;
import com.qtsn.fox.test.weatherfox.pojo.ModeType;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    /**
     * Degree Type Argument :
     * C : Celcius
     * F: Fahrenheit
     */
    public static final String DEG_TYPE = "C";

    // Bundle Key
    public static final String LOCATION = "LOCATION";

    // Location Request Code
    private static final int LOCATION_CODE = 0;

    /**
     * Widget
     */
    private SearchView mLocation;
    private RadioButton mCurrentMode;
    private RadioButton mForecastMode;
    private Button mSearch;
    private ProgressBar mProgress;


    // FusedLocationClient for Geolocation
    private FusedLocationProviderClient mFusedLocationClient;

    /**
     * Mode Type
     */
    private ModeType mMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLocation = findViewById(R.id.sv_location);
        mCurrentMode = findViewById(R.id.rb_current);
        mForecastMode = findViewById(R.id.rb_forecast);
        mSearch = findViewById(R.id.bt_search);
        mProgress = findViewById(R.id.pb_progress);

        init();
    }

    /**
     * Field Initialisation
     */
    private void init() {
        mLocation.setQueryHint(getResources().getString(R.string.location_hint));
        mCurrentMode.setText(getResources().getString(R.string.current_mode));
        mForecastMode.setText(getResources().getString(R.string.forecast_mode));
        mSearch.setText(getResources().getString(R.string.search_button));

        mCurrentMode.setChecked(true);
        mForecastMode.setChecked(false);
        mMode = ModeType.CURRENT;

        mProgress.setIndeterminate(true);
        mProgress.setVisibility(View.GONE);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_CODE);
        } else {
            // Permission granted
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                mLocation.setQuery(getCityName(location), true);
                            }
                        }
                    });
        }

        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchFragment();
            }
        });

        mLocation.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                launchFragment();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    /**
     * Launch the right fragment : CurrentFragment or ForecastFragment
     * Or nothing if the SearchView is Empty
     */
    private void launchFragment() {
        if (mLocation.getQuery() != null && !(mLocation.getQuery().toString().trim().length() == 0)) {
            mProgress.setVisibility(View.VISIBLE);

            Fragment fragment = mMode.equals(ModeType.FORECAST) ? new ForecastFragment() : new CurrentFragment();

            Bundle bundle = new Bundle();
            bundle.putString(LOCATION, mLocation.getQuery().toString().trim());
            fragment.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.fl_fragment_mode, fragment).addToBackStack(fragment.getClass().getSimpleName()).commit();
        } else {
            Toast.makeText(MainActivity.this, getResources().getString(R.string.empty_location), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Listenner onRadioButtonClicked, setting the mode
     *
     * @param view
     */
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.rb_current:
                if (checked) {
                    mMode = ModeType.CURRENT;
                }
                break;
            case R.id.rb_forecast:
                if (checked) {
                    mMode = ModeType.FORECAST;
                }
                break;
        }
    }

    /**
     * Listenner onRequestPermissionsResult
     * Retrieving weather for current location
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case LOCATION_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mFusedLocationClient.getLastLocation()
                            .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location) {
                                    if (location != null) {
                                        mLocation.setQuery(getCityName(location), true);
                                    }
                                }
                            });
                }
                return;
            }
        }
    }

    /**
     * Utils to translate Location object to city name
     *
     * @param loc
     * @return String cityName
     */
    private String getCityName(Location loc) {
        Geocoder gcd = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;

        try {
            addresses = gcd.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
            if (addresses.size() > 0) {
                return addresses.get(0).getLocality();
            }
        } catch (IOException e) {
            // TODO
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Progress Bar Getter
     *
     * @return progressBar
     */
    public ProgressBar getProgress() {
        return mProgress;
    }

    /**
     * Blocking the backPressed
     */
    @Override
    public void onBackPressed() {
        // Do Nothing
    }


}