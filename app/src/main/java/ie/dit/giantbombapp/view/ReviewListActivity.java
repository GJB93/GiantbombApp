package ie.dit.giantbombapp.view;

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
import ie.dit.giantbombapp.view.adapters.ReviewCursorAdapter;

public class ReviewListActivity extends AppCompatActivity {

    private static final String TAG = "ReviewListActivity";
    private MainController controller;
    private Cursor mData;
    private ListView review_list;
    private boolean loadingFlag = false;
    private static int currentOffset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_list_activity);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        review_list = (ListView) findViewById(R.id.review_list);
        controller = new MainController(getBaseContext(), getString(R.string.api_key), getString(R.string.format), getString(R.string.sort));
        new GetInitialReviewsTask().execute();
        review_list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem+visibleItemCount==totalItemCount && totalItemCount != 0 && totalItemCount < 40)
                {
                    if(!loadingFlag)
                    {
                        loadingFlag = true;
                        Log.d(TAG, "" + totalItemCount);
                        int numberOfReviewsLeft = controller.getTotalReviewResults() - currentOffset;
                        Log.d(TAG, "Number of Reviews Left: " + numberOfReviewsLeft);
                        if(visibleItemCount < numberOfReviewsLeft)
                            new GetMoreReviewsTask().execute(visibleItemCount, currentOffset);
                        else
                            new GetMoreReviewsTask().execute(numberOfReviewsLeft, currentOffset);
                    }
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
        getMenuInflater().inflate(R.menu.review_action_menu, menu);
        return true;
    }

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

            case R.id.to_promos:
            {
                Intent i = new Intent(getBaseContext(), PromoListActivity.class);
                startActivity(i);
                finish();
                return true;
            }

            case R.id.to_wiki:
            {
                Intent i = new Intent(getBaseContext(), SearchListActivity.class);
                startActivity(i);
                finish();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private class GetInitialReviewsTask extends AsyncTask<Void, Void, String>
    {
        protected String doInBackground(Void... params)
        {
            controller.getInitialReviewData();
            return null;
        }

        protected void onPostExecute(String result)
        {
            mData = controller.fetchAllReviews();
            Log.d(TAG, "Set the list view");
            ReviewCursorAdapter customAdapter = new ReviewCursorAdapter(getBaseContext(), mData);
            review_list.setAdapter(customAdapter);
            review_list.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    Adapter listAdapter = parent.getAdapter();
                    Cursor data = (Cursor) listAdapter.getItem(position);

                    /*
                    Uri uri = Uri.parse(data.getString(data.getColumnIndexOrThrow("site_url")));
                    Intent i = new Intent(Intent.ACTION_VIEW, uri);
                    finish();
                    startActivity(i);
                    */

                    Intent i = new Intent(getBaseContext(), ReviewActivity.class);
                    i.putExtra("GameId", data.getInt(data.getColumnIndexOrThrow("game_id")));
                    startActivity(i);

                }
            });
            currentOffset = 10;
        }
    }

    private class GetMoreReviewsTask extends AsyncTask<Integer, Void, Integer>
    {
        protected Integer doInBackground(Integer... params)
        {
            controller.getMoreReviewData(params[0], params[1]);
            return params[0];
        }

        protected void onPostExecute(Integer result)
        {
            ReviewCursorAdapter adapter = (ReviewCursorAdapter) review_list.getAdapter();
            adapter.changeCursor(controller.fetchAllReviews());
            adapter.notifyDataSetChanged();
            currentOffset+=result;
            loadingFlag = false;
        }
    }
}
