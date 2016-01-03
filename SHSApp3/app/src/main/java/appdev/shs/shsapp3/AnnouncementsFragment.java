package appdev.shs.shsapp3;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


public class AnnouncementsFragment extends Fragment {

    private static final String ARG_POS = "position";

    private int mPosition;
    private Context context;
    private LinearLayout masterContainer;


    public static AnnouncementsFragment newInstance(int position) {
        AnnouncementsFragment fragment = new AnnouncementsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POS, position);
        fragment.setArguments(args);
        return fragment;
    }

    public AnnouncementsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        if (getArguments() != null) {
            mPosition = getArguments().getInt(ARG_POS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_announcements, container, false);

        masterContainer = (LinearLayout)view.findViewById(R.id.master_announcements_container);
        addTheAnnouncements(masterContainer);

        return view;
    }

    //Method to add all announcements, each in the form of inflated announcement_list_item_layout.
    private void addTheAnnouncements(LinearLayout masterContainer) {
        LayoutInflater inflater = (LayoutInflater)   getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        //Example of how to add an announcement (Now let's change it to get stuff from the Parse server)
        //Enough are added in this filler so that you can scroll through the fake announcements
        for(int i=0; i < 5; i++) {
            View announcement = inflater.inflate(R.layout.announcement_list_item_layout, null);
            TextView title = (TextView)announcement.findViewById(R.id.title);
            TextView sender = (TextView)announcement.findViewById(R.id.sender);
            TextView createdAt = (TextView)announcement.findViewById(R.id.createdAt);
            TextView bodyText = (TextView)announcement.findViewById(R.id.body_text);
            title.setText("It's great to be a falcon");
            sender.setText("ASB");
            createdAt.setText("1/2/2015");
            bodyText.setText("It's the truth, so be proud! Life is good!!!");
            masterContainer.addView(announcement);
        }






    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d("MainActivity", "Announcements fragment attatched.");
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_POS));
    }


}
