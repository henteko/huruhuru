package com.henteko07.huruhuru;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.PushService;
import com.squareup.seismic.ShakeDetector;


public class MyActivity extends Activity implements ShakeDetector.Listener {
    private static final String SWITCH_KEY = "switch";

    private static boolean mIsSwitch;
    private static SharedPreferences mPreferences;
    private static ParseUtil mParseUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        mPreferences = this.getSharedPreferences("user_data", Context.MODE_PRIVATE);
        mIsSwitch = mPreferences.getBoolean(SWITCH_KEY, true);

        mParseUtil = new ParseUtil(this, this);
        mParseUtil.updateChannel(mIsSwitch);

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        ShakeDetector sd = new ShakeDetector(this);
        sd.start(sensorManager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void hearShake() {
        if (mIsSwitch) {
            mParseUtil.sendMessage("はろー！！！！！！");
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_my, container, false);
            return rootView;
        }

        @Override
        public void onStart() {
            super.onStart();

            ToggleButton button = (ToggleButton) getActivity().findViewById(R.id.toggleButton);
            button.setChecked(mIsSwitch);
            button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mIsSwitch = isChecked;
                    SharedPreferences.Editor editor = mPreferences.edit();
                    editor.putBoolean(SWITCH_KEY, mIsSwitch);
                    editor.commit();

                    mParseUtil.updateChannel(mIsSwitch);
                }
            });
        }
    }
}
