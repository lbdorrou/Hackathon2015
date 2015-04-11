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

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class News extends ActionBarActivity {
    ArrayList<String> hiddenArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        hiddenArray = new ArrayList<String>();
        getJobData();
    }
    public void getJobData()
    {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://ajax.googleapis.com/ajax/services/search/news?v=1.0&q=jb%20hunt%27", new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // called when response HTTP status is "200 OK"
                Log.v("Nope", "Fdsafdsafsda");
                try {
                    hiddenArray = new ArrayList<String>();

                    JSONObject positions = response;
                    Log.v("position size", positions.length() + "");
                    for (int i = 0; i < positions.length(); i++) {
                        JSONArray c = positions.getJSONArray("responseData");
                        if (c != null) {
//                        // Storing each json item in variable
                            for (int j = 0; j < positions.names().length(); j++) {
                                if (positions.get(positions.names().getString(j)) != null)
                                    hiddenArray.add(positions.names().getString(j) + ": " + (positions.get(positions.names().getString(j))));
                            }
                            //FUCK IT WE'LL DO IT LIVE!!!!

                        }
                    }
                    ArrayAdapter<String> positionAdapter = new ArrayAdapter<String>(News.this, android.R.layout.simple_list_item_1, hiddenArray);
                    ListView titles = (ListView) findViewById(R.id.dynamiclistview);
                    titles.setAdapter(positionAdapter);



                } catch (Exception e) {
                    Log.e("Error", e.toString());
                }
            }
        });
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news, menu);
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
            View rootView = inflater.inflate(R.layout.fragment_news, container, false);
            return rootView;
        }
    }
}
