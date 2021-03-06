package com.jbhunt.jbhuntrecruiter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Jobs extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private ListView mDynamicListView;
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    ArrayList<String> JobList;
    ArrayList<String> hiddenArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v("MattWasHere", "Bleh");
        super.onCreate(savedInstanceState);
        //addDrawerItems();
        mDrawerList = (ListView)findViewById(R.id.dynamiclistview);
        setContentView(R.layout.activity_jobs);
        mDynamicListView = (ListView )findViewById(R.id.dynamiclistview);
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

      getJobData();
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    public void getJobData()
    {
        final com.gc.materialdesign.widgets.ProgressDialog progress = new com.gc.materialdesign.widgets.ProgressDialog(this, "Loading");
        progress.show();
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(JBHuntRecruiterUtils.URL_POSITION, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                // called when response HTTP status is "200 OK"
                progress.dismiss();
                Log.v("Nope", "Fdsafdsafsda");
                try {
                    JobList = new ArrayList<String>();
                    hiddenArray = new ArrayList<String>();

                    JSONArray positions = response;
                    Log.v("position size", positions.length() + "" );
                    for (int i = 0; i < positions.length(); i++) {
                        JSONObject c = positions.getJSONObject(i);
                        if(c != null) {
//                        // Storing each json item in variable
                            String position = c.getString("category");
                            String subPosition = position.split("\t")[0].trim();
                            String test = getIntent().getStringExtra("subPosition");
                            if(test.equals(subPosition))
                            {
                                JobList.add(c.getString("title")+ "\n" + c.getString("siteID"));
                                hiddenArray.add(c.getString("id"));
                            }
                            position = position.split("\t")[0].trim();
                            //FUCK IT WE'LL DO IT LIVE!!!!

                        }
                    }
                    ArrayAdapter<String> positionAdapter = new ArrayAdapter<String>(Jobs.this, android.R.layout.simple_list_item_1, JobList);
                    ListView titles = (ListView)findViewById(R.id.dynamiclistview);
                    titles.setAdapter(positionAdapter);


                    titles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> adapterView, View view, int index, long id){
                            Intent it = new Intent(getApplicationContext(),Details.class);

                            it.putExtra("id",(hiddenArray.get(index)));
                            startActivity(it);
                        }
                    });
                }
                catch(Exception e){
                    Log.e("Error", e.toString());
                }
            }


            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }

        });
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
            getMenuInflater().inflate(R.menu.jobs, menu);
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

    private void addDrawerItems() {
        ArrayList<String> choices = new ArrayList<String>();
        choices.add("Jobs");
        choices.add("News");
        choices.add("Settings");
        mAdapter = new ArrayAdapter<String>(Jobs.this, android.R.layout.simple_list_item_1, choices);
        mDrawerList.setAdapter(mAdapter);
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
            View rootView = inflater.inflate(R.layout.fragment_jobs, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((Jobs) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }


}
