package com.jbhunt.jbhuntrecruiter;

import android.app.Activity;
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
import android.widget.Button;
import android.widget.ListView;

import com.gc.materialdesign.widgets.ProgressDialog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class positionType extends ActionBarActivity  {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    Button test;
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    JSONParser jParser = new JSONParser();
    String positionTypeFlag;
    JSONArray positionTypes = null;
    ArrayList<String> positionSubTypes = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final ProgressDialog progress = new ProgressDialog(this, "Loading");
        progress.show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position_type);

        mTitle = getTitle();
        positionTypeFlag = this.getIntent().getStringExtra("positionTypeFlag");
        // Set up the drawer.

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(JBHuntRecruiterUtils.URL_POSITION, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                // called when response HTTP status is "200 OK"
                try {
                    progress.dismiss();
                    Log.v("test", "Test");
                    JSONArray positions = response;
                    Log.v("position size", positions.length() + "" );
                    for (int i = 0; i < positions.length(); i++) {
                        JSONObject c = positions.getJSONObject(i);
                        if(c != null) {
//                        // Storing each json item in variable
                            String position = c.getString("category");
                            position = position.split("\t")[0].trim();
                            //FUCK IT WE'LL DO IT LIVE!!!!
                            if((position.toUpperCase().contains("MAINTENANCE") && "maintenance".equals(positionTypeFlag))
                                    || (!position.toUpperCase().contains("MAINTENANCE")  &&
                                                !position.toUpperCase().contains("DRIVER") && "office".equals(positionTypeFlag))
                                    || (position.toUpperCase().contains("DRIVER") && "driver".equals(positionTypeFlag)
                                    && !position.toUpperCase().contains("DRIVER RECRUITING"))
                                    )
                            {

                                if (position != null && !positionSubTypes.contains(position))
                                    positionSubTypes.add(position);
//                            }
                            }
                        }
                    }
                    ArrayAdapter<String> positionAdapter = new ArrayAdapter<String>(positionType.this, android.R.layout.simple_list_item_1, positionSubTypes);
                    ListView titles = (ListView)findViewById(R.id.lstvPositionTypes);
                    titles.setAdapter(positionAdapter);
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
        final ListView lv = (ListView)findViewById(R.id.lstvPositionTypes);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long id){
                Intent it = new Intent(getApplicationContext(),Jobs.class);

                it.putExtra("subPosition",(positionSubTypes.get(index)));
                startActivity(it);
            }
        });
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
            View rootView = inflater.inflate(R.layout.fragment_position_type, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((positionType) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }
}
