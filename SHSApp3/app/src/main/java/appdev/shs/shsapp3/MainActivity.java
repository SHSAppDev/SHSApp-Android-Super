package appdev.shs.shsapp3;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, AdapterView.OnItemClickListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private GridView gridMenu;
    private ArrayList<GridItem> gridItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        gridMenu = (GridView)findViewById(R.id.grid_menu);

        //Initialize the buttons (grid items)
        gridItems = new ArrayList<GridItem>();

        //gridItems.add(new GridItem("Schedule", getResources().getDrawable(R.drawable.schedule),
        //        new Intent(this, ScheduleActivity.class)));

        Intent aeries0 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://aeries.lgsuhsd.org/aeries.net/loginparent.aspx"));
        gridItems.add(new GridItem("Aeries", getResources().getDrawable(R.drawable.addphoto), aeries0));

        Intent canvas = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://lgsuhsd.instructure.com/login"));
        gridItems.add(new GridItem("Canvas", getResources().getDrawable(R.drawable.canvas),
                canvas));


        //gridItems.add(new GridItem("Falcon Newspaper", getResources().getDrawable(R.drawable.falconpaper),
        //        new Intent(this, NewsActivity.class)));

        gridItems.add(new GridItem("Directory", getResources().getDrawable(R.drawable.contacts),
                new Intent(this, DirectoryActivity.class)));

        Intent naviance = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://connection.naviance.com/family-connection/auth/login/?hsid=saratogahigh"));
        gridItems.add(new GridItem("Naviance", getResources().getDrawable(R.drawable.naviance),
                naviance));

        //gridItems.add(new GridItem("Announcements", getResources().getDrawable(R.drawable.annoucement),
        //  new Intent(this, Annoucements.class)));

        //gridItems.add(new GridItem("Sports Center", getResources().getDrawable(R.drawable.sportscenter),
        //        new Intent(this, SportsCenterActivity.class)));

        gridMenu.setAdapter(new GridMenuAdapter());
        gridMenu.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
        GridItem item = gridItems.get(position);
        startActivity(item.intent);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    public class GridMenuAdapter extends BaseAdapter {

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View item = getLayoutInflater().inflate(R.layout.grid_item_layout, null);
            item.setPadding(0, 50, 0, 40);
            GridItem current = gridItems.get(position);
            TextView label = (TextView)item.findViewById(R.id.item_label);
            label.setText(current.text);
            label.setCompoundDrawables(null, current.image, null, null);
            label.setCompoundDrawablePadding(32);
            item.setMinimumHeight(300);
            return item;
        }

        @Override
        public final int getCount() {
            return gridItems.size();
        }

        @Override
        public Object getItem(int position) {
            return gridItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }

    public static class GridItem {
        public String text;
        public Drawable image;
        public Intent intent;
        public GridItem(String text, Drawable image, Intent intent) {
            this.text = text;
            this.image = image;
            this.image.setBounds(0, 0, 160, 160);
            this.intent = intent;
        }
    }


}
