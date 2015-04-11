package com.jbhunt.jbhuntrecruiter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.gc.materialdesign.widgets.ProgressDialog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;


public class Details extends ActionBarActivity {

    ArrayList<Object> JobDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
            JobDetails = new ArrayList<Object>();
            getJobData();
        }
    }
    public void getJobData()
    {
        final ProgressDialog progress = new ProgressDialog(this, "Loading");
        progress.show();
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://lbdorrou.ddns.net:3000/jobs/" + getIntent().getStringExtra("id") + "?format=json", new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                progress.dismiss();
                // called when response HTTP status is "200 OK"
                Log.v("Nope", "Fdsafdsafsda");
                try {

                    /**/JSONObject positions = response;
                    if(positions!=null)
                    {
                        for(int i = 0; i<positions.names().length(); i++){
                            if(positions.get(positions.names().getString(i))!= null) {
                                String title = positions.names().getString(i);
                                if(positions.get(positions.names().getString(i)) != null) {
                                    JobDetails.add(titleize(title) + ": " + positions.get(positions.names().getString(i)));
                                }
                            }
                        }
                    }
                    ArrayAdapter<Object> positionAdapter = new ArrayAdapter<Object>(Details.this, android.R.layout.simple_list_item_1, JobDetails);
                    ListView titles = (ListView)findViewById(R.id.details);
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
    }

    private String titleize(String input)
    {
        if(input != null && input.length() > 1)
        {
            input = input.substring(0,1).toUpperCase() + input.substring(1).replace('_', ' ');
        }
        return input;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
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

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_details, container, false);
            return rootView;
        }
    }
}
