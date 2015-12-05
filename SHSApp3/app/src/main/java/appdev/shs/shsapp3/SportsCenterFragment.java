package appdev.shs.shsapp3;


import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.HashMap;


public class SportsCenterFragment extends Fragment {
    private static final String ARG_POS = "position";

    private int mPosition;
    private Context context;
    private LinearLayout main;
    private Spinner sportSpinner;
    private Spinner leagueSpinner;
    private Button goButton;
    public static ScrollView scroll;
    private TextView scrollText;
    private static ArrayList<String> sports;
    private static HashMap<String, String[]> leagues;


    public static SportsCenterFragment newInstance(int position) {
        SportsCenterFragment fragment = new SportsCenterFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POS, position);
        fragment.setArguments(args);
        return fragment;
    }

    public SportsCenterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sports_center, container, false);


        main = (LinearLayout)view.findViewById(R.id.main_container);

        //initialize gradient
        GradientDrawable backgroundGradient = new GradientDrawable();
        backgroundGradient.setShape(GradientDrawable.RECTANGLE);
        backgroundGradient.setColors(new int[]{0xffffffff, 0xffff3333});
        backgroundGradient.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
        main.setBackground(backgroundGradient);


        sportSpinner = (Spinner)view.findViewById(R.id.sport_spinner);

        ArrayAdapter<String> sportAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, sports);
        sportSpinner.setAdapter(sportAdapter);
        sportSpinner.setSelection(sportAdapter.getPosition("SELECT SPORT..."));
        sportSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> s, View v,
                                       int index, long id) {
                sportSelected(v);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });


        leagueSpinner = (Spinner)view.findViewById(R.id.league_spinner);
        ArrayAdapter<String> leagueAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, leagues.get(sportSpinner.getSelectedItem()));
        leagueSpinner.setAdapter(leagueAdapter);
        leagueSpinner.setSelection(0);

        goButton = (Button)view.findViewById(R.id.goButton);
        goButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                goButtonClicked(v);
            }
        });

        scroll = (ScrollView)view.findViewById(R.id.timeline_text).getParent();
        scrollText = (TextView)view.findViewById(R.id.timeline_text);

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d("MainActivity", "Sports center fragment attatched.");
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_POS));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getActivity();
        if (getArguments() != null) {
            mPosition = getArguments().getInt(ARG_POS);
        }

        //initialize leagues and sports
        leagues = new HashMap<String,String[]>();
        sports = new ArrayList<String>();

        leagues.put("SELECT SPORT...", new String[]{"SELECT LEAGUE..."}); sports.add("SELECT SPORT...");
        leagues.put("Badminton", new String[]{"SELECT LEAGUE...", "V", "JV"}); sports.add("Badminton");
        leagues.put("Baseball", new String[]{"SELECT LEAGUE...", "Boys V", "Boys F-SO", "Boys Freshman"}); sports.add("Baseball");
        leagues.put("Basketball", new String[]{"SELECT LEAGUE...", "Boys V", "Boys F-SO", "Girls V", "Girls JV"}); sports.add("Basketball");
        leagues.put("Cheerleading", new String[]{"N/A"}); sports.add("Cheerleading");
        leagues.put("Cross Country", new String[]{"SELECT LEAGUE...", "Boys V", "Boys F-SO", "Girls V", "Girls JV"}); sports.add("Cross Country");
        leagues.put("Dance", new String[]{"N/A"}); sports.add("Dance");
        leagues.put("Field Hockey", new String[]{"SELECT LEAGUE...", "Girls V", "Girls JV"}); sports.add("Field Hockey");
        leagues.put("Football", new String[]{"SELECT LEAGUE...", "Boys V", "Boys F-SO", "Powderpuff"}); sports.add("Football");
        leagues.put("Golf", new String[]{"SELECT LEAGUE...", "Boys", "Girls"}); sports.add("Golf");
        leagues.put("Lacrosse", new String[]{"SELECT LEAGUE...", "Boys V", "Boys F-SO", "Girls V", "Girls JV"}); sports.add("Lacrosse");
        leagues.put("Soccer", new String[]{"SELECT LEAGUE...", "Boys V", "Boys F-SO", "Girls V", "Girls JV"}); sports.add("Soccer");
        leagues.put("Softball", new String[]{"SELECT LEAGUE...", "Girls V", "Girls JV"}); sports.add("Softball");
        leagues.put("Swimming", new String[]{"SELECT LEAGUE...", "Boys V", "Boys F-SO", "Girls V", "Girls JV"}); sports.add("Swimming");
        leagues.put("Tennis", new String[]{"SELECT LEAGUE...", "Boys V", "Boys F-SO", "Girls V", "Girls JV"}); sports.add("Tennis");
        leagues.put("Track and Field", new String[]{"SELECT LEAGUE...", "Boys V", "Boys F-SO", "Girls V", "Girls JV"}); sports.add("Track and Field");
        leagues.put("Volleyball", new String[]{"SELECT LEAGUE...", "Boys V", "Boys F-SO", "Girls V", "Girls JV", "Girls Freshman"}); sports.add("Volleyball");
        leagues.put("Water Polo", new String[]{"SELECT LEAGUE...", "Boys V", "Boys F-SO", "Girls V", "Girls JV"}); sports.add("Water Polo");
        leagues.put("Wrestling", new String[]{"SELECT LEAGUE...", "Boys V", "Boys JV"}); sports.add("Wrestling");
        

    }

    public void goButtonClicked(View v){

        //checks if network is available. If not, Toast error and return
        if(!isNetworkAvailable(context)) {
            Toast.makeText(getActivity(), "Please connect to the internet.", Toast.LENGTH_SHORT).show();
            return;
        }


        //get info from 2 Spinners/DatePicker
        String sport = ((String) sportSpinner.getSelectedItem()).trim().replaceAll("\\s+","_").toLowerCase();
        String league = ((String) leagueSpinner.getSelectedItem()).trim().replaceAll("\\s+","_").toLowerCase();;

        // if sportSpinner or leagueSpinner still have "select" then make notification saying make all selections first
        if(sport.equals("select_sport...")){
            Toast.makeText(context, "Please select a sport.", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(league.equals("select_league...")){
            Toast.makeText(context, "Please select a league.", Toast.LENGTH_SHORT).show();
            return;
        }

        setInfoOnTextView(scrollText, sport, league);

    }

    private void sportSelected(View v){
        String key = ((TextView)v).getText().toString();
        ArrayAdapter<String> a = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, leagues.get(key));
        leagueSpinner.setAdapter(a);
        leagueSpinner.setSelection(0);
    }

    public static void scrollToBottom(){
        //this is what causes the ScrollView to scroll automatically to the bottom once GO is pressed
        scroll.post(new Runnable() {
            @Override
            public void run() {
                scroll.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
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

    public static void setInfoOnTextView(final TextView v, final String sport, final String league){

        v.setBackgroundColor(0x55ffffff);
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("sport", sport);
        params.put("league", league);
        ParseCloud.callFunctionInBackground("requestSportsCenterSportHistory", params, new FunctionCallback<String>() {
            public void done(String result, ParseException e) {
                if (e == null) {
                    // result is text
                    v.setText(result.replaceAll("%", "\n"));
                    v.setBackgroundColor(0x00ffffff);
                    scrollToBottom();
                }
            }
        });

    }

    public static boolean isNetworkAvailable(Context context) {
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }


}
