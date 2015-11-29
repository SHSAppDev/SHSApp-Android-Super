package appdev.shs.shsapp3;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class SportsCenterFragment extends Fragment {
    private static final String ARG_POS = "position";

    private int mPosition;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPosition = getArguments().getInt(ARG_POS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sports_center, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d("MainActivity", "Sports center fragment attatched.");
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_POS));
    }


}
