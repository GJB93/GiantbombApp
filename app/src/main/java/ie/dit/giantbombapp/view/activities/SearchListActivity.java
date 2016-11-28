package ie.dit.giantbombapp.view.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import ie.dit.giantbombapp.R;
import ie.dit.giantbombapp.controller.MainController;
import ie.dit.giantbombapp.model.pojos.Search;
import ie.dit.giantbombapp.view.adapters.SearchArrayAdapter;

/**
 * Author: Graham Byrne
 *
 * Created: 24/11/2016
 * Modified: 25/11/2016
 *
 * When this activity first appears, it displays a search bar and a spinner containing
 * the categories that they can search within. The search panel disappears when the
 * user presses the search button, and a list is shown displaying the data found for the
 * given search result. The user can click the search icon in the action bar to bring up
 * search panel again.
 */

public class SearchListActivity extends AppCompatActivity
{

    private static final String TAG = "SearchListActivity";
    private MainController controller;
    private List<Search> mData;
    private ListView search_list;
    private EditText searchQuery;
    private Spinner resourceTypes;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_list_activity);

        //Create a custom app bar and set it as the action bar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //Create the controller to be used in this activity
        controller = new MainController(getBaseContext(), getString(R.string.api_key), getString(R.string.format), getString(R.string.sort));

        searchQuery = (EditText) findViewById(R.id.search_bar);
        resourceTypes = (Spinner) findViewById(R.id.resource_spinner);
        searchButton = (Button) findViewById(R.id.search_button);

        //Set the adapter that contains the data to populate the spinner with
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.resource_types, android.R.layout.simple_spinner_dropdown_item);
        resourceTypes.setAdapter(adapter);

        //Set an onClickListener for the search button
        searchButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(!searchQuery.getText().toString().isEmpty())
                {
                    //Hide error messages if trying to search again
                    findViewById(R.id.no_search_data).setVisibility(View.GONE);
                    findViewById(R.id.data_error).setVisibility(View.GONE);

                    //Hide the search panel and show the progress indicator
                    findViewById(R.id.search_details).setVisibility(View.GONE);
                    findViewById(R.id.progress_indicator).setVisibility(View.VISIBLE);

                    //Call the AsyncTask to get data for the given search query and resource
                    new SearchForQueryTask().execute(searchQuery.getText().toString(), resourceTypes.getSelectedItem().toString());
                }
            }
        });


    }

    @Override
    protected void onResume()
    {
        super.onResume();
        controller.openDatabase();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        controller.closeDatabase();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.search_action_menu, menu);
        return true;
    }

    /**
     * Handles what happens when each action bar menu item is clicked. Includes a search
     * action, which hides and displays the search panel.
     *
     * @param item: The menu item that was selected
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        switch (id)
        {
            //Hide or show the search panel
            case R.id.search:
            {
                if(findViewById(R.id.search_details).getVisibility() == View.VISIBLE)
                {
                    findViewById(R.id.search_details).setVisibility(View.GONE);
                }
                else
                {
                    findViewById(R.id.search_details).setVisibility(View.VISIBLE);
                }
                return true;
            }

            case R.id.action_settings:
            {
                Toast.makeText(getBaseContext(), "There are no settings", Toast.LENGTH_SHORT).show();
                return true;
            }

            //Switches to PromoListActivity
            case R.id.to_promos:
            {
                Intent i = new Intent(getBaseContext(), PromoListActivity.class);
                finish();
                startActivity(i);
                return true;
            }

            //Switches to ReviewListActivity
            case R.id.to_reviews:
            {
                Intent i = new Intent(getBaseContext(), ReviewListActivity.class);
                finish();
                startActivity(i);
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * This AsyncTask calls the controller to make a search on the API using
     * a given search query and search resource
     */
    private class SearchForQueryTask extends AsyncTask<String, Void, String>
    {
        protected String doInBackground(String... params)
        {
            mData = controller.searchForData(params[0], params[1]);

            //If data was found, return null, else return a non-null string
            if(mData != null)
            {
                return null;
            }
            else
            {
                return "No data";
            }
        }

        protected void onPostExecute(String result)
        {
            // Hide the progress indicator
            findViewById(R.id.progress_indicator).setVisibility(View.GONE);

            //If data was retrieved successfully
            if(result == null)
            {
                if(mData.size() > 0)
                {
                    ArrayAdapter<Search> customAdapter = new SearchArrayAdapter(getBaseContext(), R.layout.search_row, mData);
                    search_list = (ListView) findViewById(R.id.search_list);
                    search_list.setVisibility(View.VISIBLE);
                    search_list.setAdapter(customAdapter);
                    search_list.setOnItemClickListener(new AdapterView.OnItemClickListener()
                    {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                        {
                            Adapter listAdapter = parent.getAdapter();
                            Search data = (Search) listAdapter.getItem(position);
                            Uri uri = Uri.parse(data.getSiteUrl());
                            Intent i = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(i);
                    /*
                    Intent i = new Intent(getBaseContext(), ReviewActivity.class);
                    i.putExtra("GameId", data.getInt(data.getColumnIndexOrThrow("game_id")));
                    startActivity(i);
                    */
                        }
                    });
                }
                else
                {
                    search_list = (ListView) findViewById(R.id.search_list);
                    search_list.setVisibility(View.GONE);
                    findViewById(R.id.no_search_data).setVisibility(View.VISIBLE);
                }
            }
            else
            {
                //Show an error message if no data was entered to the cursor
                findViewById(R.id.data_error).setVisibility(View.VISIBLE);
            }
        }
    }
}
