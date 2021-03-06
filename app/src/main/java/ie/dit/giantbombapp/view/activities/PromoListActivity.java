package ie.dit.giantbombapp.view.activities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import ie.dit.giantbombapp.R;
import ie.dit.giantbombapp.controller.MainController;
import ie.dit.giantbombapp.view.adapters.PromoCursorAdapter;

/**
 * Author: Graham Byrne
 *
 * Created: 15/11/2016
 * Modified: 25/11/2016
 *
 * This activity shows a list of Promo objects that are retrieved
 * from the local database. If a list item is clicked, the user is
 * brought to the browser to display the item found at that selection
 */

public class PromoListActivity extends AppCompatActivity {

    private static final String TAG = "PromoListActivity";
    private MainController controller;
    private ListView promo_list;
    private boolean loadingFlag = false;
    private static int currentOffset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promo_list_activity);

        //Create a custom app bar and set it as the action bar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //Create the controller to be used in this activity
        controller = new MainController(getBaseContext(), getString(R.string.api_key), getString(R.string.format), getString(R.string.sort));

        //AsyncTask used to get the initial promos to enter into the list view
        new GetInitialPromosTask().execute();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        controller.openDatabase();
        currentOffset = 0;
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        controller.closeDatabase();
    }

    /**
     * Inflates the action bar popup menu with given data
     * @param menu: The menu resource to inflate
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.promo_action_menu, menu);
        return true;
    }

    /**
     * Handles what happens when each action bar menu item is clicked
     * @param item: The menu item that was selected
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        switch (id)
        {
            case R.id.action_settings:
            {
                Toast.makeText(getBaseContext(), "There are no settings", Toast.LENGTH_SHORT).show();
                return true;
            }

            // Switches to the ReviewsListActivity
            case R.id.to_reviews:
            {
                Intent i = new Intent(getBaseContext(), ReviewListActivity.class);
                finish();
                startActivity(i);
                return true;
            }

            // Switches to the SearchListActivity
            case R.id.to_search:
            {
                Intent i = new Intent(getBaseContext(), SearchListActivity.class);
                finish();
                startActivity(i);
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * This AsyncTask calls the controller to fetch the initial promo data to be added to the
     * list view in this activity. It takes in no parameters, and the output of doInBackground
     * isn't used
     */
    private class GetInitialPromosTask extends AsyncTask<Void, Void, String>
    {
        protected String doInBackground(Void... params)
        {
            controller.getInitialPromoData();
            return null;
        }

        protected void onPostExecute(String result)
        {
            // Create a cursor to hold the results of the database query
            Cursor mData = controller.fetchAllPromos();

            //Hide the progress indicator
            findViewById(R.id.progress_indicator).setVisibility(View.GONE);

            //If the cursor has data, show and populate the list view
            if(mData != null && mData.getCount() > 0)
            {
                promo_list = (ListView) findViewById(R.id.promo_list);
                promo_list.setVisibility(View.VISIBLE);
                PromoCursorAdapter customAdapter = new PromoCursorAdapter(getBaseContext(), mData);
                promo_list.setAdapter(customAdapter);
                promo_list.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {

                        //OnItemClick sends the user to the browser page for the selected resource
                        Adapter listAdapter = parent.getAdapter();
                        Cursor data = (Cursor) listAdapter.getItem(position);

                        Uri uri = Uri.parse(data.getString(data.getColumnIndexOrThrow("site_url")));
                        Intent i = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(i);

                        /*
                        Intent i = new Intent(getBaseContext(), PromoActivity.class);
                        i.putExtra("PromoId", data.getInt(data.getColumnIndexOrThrow("promo_id")));
                        startActivity(i);
                        */
                    }
                });

                //Scroll listener checks if the user has reached the end of the list
                //Populates the list and database with more promo data if more can be found
                promo_list.setOnScrollListener(new AbsListView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(AbsListView view, int scrollState) {}

                    @Override
                    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                        if(firstVisibleItem+visibleItemCount==totalItemCount && totalItemCount != 0 && totalItemCount < controller.getTotalPromoResults())
                        {
                            //This boolean stops the check from running repeatedly if the user reaches the bottom of the list
                            if(!loadingFlag)
                            {
                                //Show the loading indicator again
                                findViewById(R.id.progress_indicator).setVisibility(View.VISIBLE);
                                loadingFlag = true;
                                Log.d(TAG, "" + totalItemCount);
                                int numberOfPromosLeft = controller.getTotalPromoResults() - currentOffset;
                                Log.d(TAG, "Number of Promos Left: " + numberOfPromosLeft);

                                //If there is more data left to fetch than can be seen, fetch the visible amount
                                //Otherwise only get the amount of data that remains
                                if(visibleItemCount < numberOfPromosLeft)
                                    new GetMorePromosTask().execute(visibleItemCount, currentOffset);
                                else
                                    new GetMorePromosTask().execute(numberOfPromosLeft, currentOffset);
                            }
                        }
                    }
                });
                currentOffset += 10;
            }
            else
            {
                //Show an error message if no data was entered to the cursor
                findViewById(R.id.data_error).setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * This AsyncTask calls the controller to fetch more promo data to be added to the
     * list view in this activity. It is called when the user reaches the bottom of the
     * given list view.
     *
     * It takes in two integer parameters, the first represents the limit to search the API by,
     * and the second represents the offset to search the API from.
     */
    private class GetMorePromosTask extends AsyncTask<Integer, Void, Integer>
    {
        protected Integer doInBackground(Integer... params)
        {
            controller.getMorePromoData(params[0], params[1]);
            return params[0];
        }

        protected void onPostExecute(Integer result)
        {
            //Remove the progress indicator
            findViewById(R.id.progress_indicator).setVisibility(View.GONE);

            //Change the cursor that the adapter is working with, then notify the
            //adapter that its dataset has changed
            PromoCursorAdapter adapter = (PromoCursorAdapter) promo_list.getAdapter();
            adapter.changeCursor(controller.fetchAllPromos());
            adapter.notifyDataSetChanged();

            //Increment the current offset, and set the loadingFlag to false
            currentOffset+=result;
            loadingFlag = false;
        }
    }
}
