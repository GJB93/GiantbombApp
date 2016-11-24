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
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import ie.dit.giantbombapp.R;
import ie.dit.giantbombapp.controller.MainController;

public class PromoListActivity extends AppCompatActivity {

    private static final String TAG = "PromoListActivity";
    private MainController controller;
    private Cursor mData;
    private ListView promo_list;
    private boolean loadingFlag = false;
    private static int currentOffset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promo_list_activity);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        controller = new MainController(getBaseContext(), getString(R.string.api_key), getString(R.string.format), getString(R.string.sort));
        promo_list = (ListView) findViewById(R.id.promo_list);
        new GetInitialPromosTask().execute();
        promo_list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem+visibleItemCount==totalItemCount && totalItemCount != 0 && totalItemCount < controller.getTotalPromoResults())
                {
                    if(!loadingFlag)
                    {
                        loadingFlag = true;
                        Log.d(TAG, "" + totalItemCount);
                        int numberOfPromosLeft = controller.getTotalPromoResults() - currentOffset;
                        Log.d(TAG, "Number of Promos Left: " + numberOfPromosLeft);
                        if(visibleItemCount < numberOfPromosLeft)
                            new GetMorePromosTask().execute(visibleItemCount, currentOffset);
                        else
                            new GetMorePromosTask().execute(numberOfPromosLeft, currentOffset);
                    }
                }
            }
        });
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

            case R.id.to_reviews:
            {
                Intent i = new Intent(getBaseContext(), ReviewListActivity.class);
                startActivity(i);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private class GetInitialPromosTask extends AsyncTask<Void, Void, String>
    {
        protected String doInBackground(Void... params)
        {
            controller.getInitialPromoData();
            return null;
        }

        protected void onPostExecute(String result)
        {
            mData = controller.fetchAllPromos();
            currentOffset = 10;
            promo_list = (ListView) findViewById(R.id.promo_list);
            Log.d(TAG, "Set the list view");
            PromoCursorAdapter customAdapter = new PromoCursorAdapter(getBaseContext(), mData);
            promo_list.setAdapter(customAdapter);
            promo_list.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    Adapter listAdapter = parent.getAdapter();
                    Cursor data = (Cursor) listAdapter.getItem(position);
                    Intent i = new Intent(getBaseContext(), PromoActivity.class);
                    i.putExtra("PromoId", data.getInt(4));
                    startActivity(i);
                }
            });
        }
    }

    private class GetMorePromosTask extends AsyncTask<Integer, Void, Integer>
    {
        protected Integer doInBackground(Integer... params)
        {
            controller.getMorePromoData(params[0], params[1]);
            return params[0];
        }

        protected void onPostExecute(Integer result)
        {
            PromoCursorAdapter adapter = (PromoCursorAdapter) promo_list.getAdapter();
            adapter.changeCursor(controller.fetchAllPromos());
            adapter.notifyDataSetChanged();
            currentOffset+=result;
            loadingFlag = false;
        }
    }
}
