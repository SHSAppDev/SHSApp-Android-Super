package appdev.shs.shsapp3;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BellsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BellsFragment extends Fragment {

    private static final String ARG_POS = "position";

    private int mPosition;


    public static Fragment newInstance(int position) {
        BellsFragment fragment = new BellsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POS,position);
        fragment.setArguments(args);
        return fragment;
    }

    public BellsFragment() {
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
        return inflater.inflate(R.layout.fragment_bells, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d("MainActivity", "Bells fragment attatched.");
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_POS));
    }

}
