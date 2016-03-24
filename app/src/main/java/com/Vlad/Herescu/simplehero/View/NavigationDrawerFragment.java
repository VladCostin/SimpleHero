package com.Vlad.Herescu.simplehero.View;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.Vlad.Herescu.simplehero.model.ItemModelDrawer;
import com.Vlad.Herescu.simplehero.R;

import java.util.ArrayList;

/**
 * Fragment used for managing interactions for and presentation of a navigation drawer.
 * See the <a href="https://developer.android.com/design/patterns/navigation-drawer.html#Interaction">
 * design guidelines</a> for a complete explanation of the behaviors implemented here.
 */
public class NavigationDrawerFragment extends Fragment implements AdapterView.OnItemClickListener {

    /**
     * Remember the position of the selected item.
     */
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    /**
     * Per the design guidelines, you should show the drawer on launch until the user manually
     * expands it. This shared preference tracks this.
     */
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

    /**
     * A pointer to the current callbacks instance (the Activity).
     */
    private NavigationDrawerCallbacks mCallbacks;

    /**
     * Helper component that ties the action bar to the navigation drawer.
     */
    private ActionBarDrawerToggle mDrawerToggle;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerListView;
    private View mFragmentContainerView;

    private int mCurrentSelectedPosition = 0;
    private boolean mFromSavedInstanceState;
    private boolean mUserLearnedDrawer;

    Context m_context;

    View m_lastItemSelected;

    SharedPreferences sharedPref;
    ArrayList<ItemModelDrawer> items;
    ListAdapterDrawer m_adapter;

    public NavigationDrawerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Read in the flag indicating whether or not the user has demonstrated awareness of the
        // drawer. See PREF_USER_LEARNED_DRAWER for details.
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
            Log.i("drawer", "saved instance state here is" + mCurrentSelectedPosition  + " ");
        }
        else
            Log.i("drawer", "it is not saving the instance");


        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);



        selectItem(sharedPref.getInt("LAST_FRAGMENT",0));
        // Select either the default item (0) or the last selected item.
       // selectItem(mCurrentSelectedPosition);
        m_context = getActivity();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Indicate that this fragment would like to influence the set of actions in the action bar.
        setHasOptionsMenu(true);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDrawerListView = (ListView) inflater.inflate(
                R.layout.fragment_navigation_drawer, container, false);



        ArrayList<String> titles = new ArrayList<String>();
        titles.add(getString(R.string.title_homePage));
        titles.add(getString(R.string.title_section2));
        titles.add(getString(R.string.title_section3));
        titles.add(getString(R.string.title_section4));
        titles.add(getString(R.string.title_section5));


         items = new ArrayList<ItemModelDrawer>();
        int color = m_context.getResources().getColor(R.color.cyan);

        int position = sharedPref.getInt("LAST_FRAGMENT",0);
        Log.i("messageElse","sunt in create : " + position + " ");
        if(position == 0)
            items.add( new ItemModelDrawer(getResources().getDrawable(R.drawable.ic_home_black_24dp), getString(R.string.title_homePage), color, true));
        else
            items.add( new ItemModelDrawer(getResources().getDrawable(R.drawable.ic_home_black_24dp), getString(R.string.title_homePage), color, false));

        if(position == 1)
            items.add( new ItemModelDrawer(getResources().getDrawable(R.drawable.ic_thumb_up_black_24dp),getString(R.string.title_section2), color, true));
        else
            items.add( new ItemModelDrawer(getResources().getDrawable(R.drawable.ic_thumb_up_black_24dp),getString(R.string.title_section2), color, false));
        if(position == 2)
            items.add(new ItemModelDrawer(getResources().getDrawable(R.drawable.ic_assignment_black_24dp), getString(R.string.title_section3), color, true));
        else
            items.add(new ItemModelDrawer(getResources().getDrawable(R.drawable.ic_assignment_black_24dp), getString(R.string.title_section3), color, false));
        if(position == 3)
            items.add(new ItemModelDrawer(getResources().getDrawable(R.drawable.ic_done_black_24dp), getString(R.string.title_section4), color, true));
        else
            items.add(new ItemModelDrawer(getResources().getDrawable(R.drawable.ic_done_black_24dp), getString(R.string.title_section4), color, false));
        if(position == 4)
            items.add(new ItemModelDrawer(getResources().getDrawable(R.drawable.ic_help_black_24dp), getString(R.string.title_section5), color, true));
        else
            items.add(new ItemModelDrawer(getResources().getDrawable(R.drawable.ic_help_black_24dp), getString(R.string.title_section5), color, false));




         m_adapter = new ListAdapterDrawer(m_context, R.layout.item_listview_drawer, titles, items);
        mDrawerListView.setAdapter(m_adapter);
        mDrawerListView.setOnItemClickListener(this);


        //mDrawerListView.setItemChecked(sharedPref.getInt("LAST_FRAGMENT",0), true);
        mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);
        return mDrawerListView;
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    /**
     * Users of this fragment must call this method to set up the navigation drawer interactions.
     *
     * @param fragmentId   The android:id of this fragment in its activity's layout.
     * @param drawerLayout The DrawerLayout containing this fragment's UI.
     */
    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);


        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the action bar app icon.
        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),                    /* host Activity */
                mDrawerLayout,                    /* DrawerLayout object */
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }

                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
                mDrawerToggle.syncState();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                View view = getActivity().getCurrentFocus();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                super.onDrawerOpened(drawerView);




                if (!isAdded()) {
                    return;
                }

                if (!mUserLearnedDrawer) {
                    // The user manually opened the drawer; store this flag to prevent auto-showing
                    // the navigation drawer automatically in the future.
                    mUserLearnedDrawer = true;
                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(getActivity());
                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
                }

                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
                mDrawerToggle.syncState();
            }
        };

        // If the user hasn't 'learned' about the drawer, open it to introduce them to the drawer,
        // per the navigation drawer design guidelines.
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(mFragmentContainerView);
        }

        // Defer code dependent on restoration of previous instance state.
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void selectItem(int position) {
        mCurrentSelectedPosition = position;
       // if (mDrawerListView != null) {
        //    mDrawerListView.setItemChecked(position, true);
       // }
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (mCallbacks != null) {
            mCallbacks.onNavigationDrawerItemSelected(position);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i("drawer", "onSaveInstanceState" + mCurrentSelectedPosition + " ");
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Forward the new configuration the drawer toggle component.
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // If the drawer is open, show the global app actions in the action bar. See also
        // showGlobalContextActionBar, which controls the top-left area of the action bar.
        if (mDrawerLayout != null && isDrawerOpen()) {
            inflater.inflate(R.menu.global, menu);
            showGlobalContextActionBar();
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    /**
     * Per the navigation drawer design guidelines, updates the action bar to show the global app
     * 'context', rather than just what's in the current screen.
     */
    private void showGlobalContextActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle(R.string.app_name);
    }

    private ActionBar getActionBar() {
        return ((ActionBarActivity) getActivity()).getSupportActionBar();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {


        Log.i("messageOnItemCLick", position + " ");

        TextView textView = (TextView) view.findViewById(R.id.textViewDrawer);
        textView.setTextColor(getResources().getColor(R.color.cyan));
        items.get(position).set_selected(true);
        Log.i("message onItemClick",sharedPref.getInt("LAST_FRAGMENT", 0) + "BBBB ");

        if(m_lastItemSelected != null)
        {
            Log.i("message ",sharedPref.getInt("LAST_FRAGMENT", 0) + "CCCC" );
            textView = (TextView) m_lastItemSelected.findViewById(R.id.textViewDrawer);
            textView.setTextColor(getResources().getColor(R.color.black));
            items.get(sharedPref.getInt("LAST_FRAGMENT", 0)).set_selected(false);
        }
        else
        {
            Log.i("messageElse", sharedPref.getInt("LAST_FRAGMENT", 0) + "AAAAA");
            if(position != sharedPref.getInt("LAST_FRAGMENT", 0))
            {
                m_lastItemSelected = mDrawerListView.getChildAt(sharedPref.getInt("LAST_FRAGMENT", 0));
                textView = (TextView) m_lastItemSelected.findViewById(R.id.textViewDrawer);
                textView.setTextColor(getResources().getColor(R.color.black));
                items.get(sharedPref.getInt("LAST_FRAGMENT", 0)).set_selected(false);

            }
        }
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("LAST_FRAGMENT", position);
        editor.commit();
        m_lastItemSelected = view;
        m_adapter.notifyDataSetChanged();
        selectItem(position);

    }

    /**
     * Callbacks interface that all activities using this fragment must implement.
     */
    public static interface NavigationDrawerCallbacks {
        /**
         * Called when an item in the navigation drawer is selected.
         */
        void onNavigationDrawerItemSelected(int position);
    }
}
