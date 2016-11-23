package ie.dit.giantbombapp.view;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import ie.dit.giantbombapp.R;
import ie.dit.giantbombapp.controller.MainController;

public class ReviewListActivity extends AppCompatActivity {

    private static final String TAG = "ReviewListActivity";
    private MainController controller;
    private Cursor mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_list_activity);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        controller = new MainController(getBaseContext(), getString(R.string.api_key), getString(R.string.format), getString(R.string.sort));
        new GetReviewsTask().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.action_menu, menu);
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
        }

        return super.onOptionsItemSelected(item);
    }

    private class GetReviewsTask extends AsyncTask<Void, Void, String>
    {
        protected String doInBackground(Void... params)
        {
            controller.getReviewData();
            return null;
        }

        protected void onPostExecute(String result)
        {
            mData = controller.fetchAllReviews();
            ListView review_list = (ListView) findViewById(R.id.review_list);
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
                    Intent i = new Intent(getBaseContext(), ReviewActivity.class);
                    i.putExtra("GameId", data.getInt(4));
                    startActivity(i);
                }
            });
        }
    }
}
