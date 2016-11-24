package ie.dit.giantbombapp.view;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        search_list = (ListView) findViewById(R.id.search_list);
        controller = new MainController(getBaseContext(), getString(R.string.api_key), getString(R.string.format), getString(R.string.sort));

        searchQuery = (EditText) findViewById(R.id.search_bar);
        resourceTypes = (Spinner) findViewById(R.id.resource_spinner);
        searchButton = (Button) findViewById(R.id.search_button);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.resource_types, android.R.layout.simple_spinner_dropdown_item);
        resourceTypes.setAdapter(adapter);

        searchButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(!searchQuery.getText().toString().isEmpty())
                {
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
        getMenuInflater().inflate(R.menu.wiki_menu, menu);
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
                finish();
                startActivity(i);
                return true;
            }

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

    private class SearchForQueryTask extends AsyncTask<String, Void, String>
    {
        protected String doInBackground(String... params)
        {
            mData = controller.searchForData(params[0], params[1]);

            return null;
        }

        protected void onPostExecute(String result)
        {
            Log.d(TAG, "Set the list view");
            ArrayAdapter<Search> customAdapter = new SearchArrayAdapter(getBaseContext(), R.layout.search_row, mData);
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
                    finish();
                    startActivity(i);
                    /*
                    Intent i = new Intent(getBaseContext(), ReviewActivity.class);
                    i.putExtra("GameId", data.getInt(data.getColumnIndexOrThrow("game_id")));
                    startActivity(i);
                    */
                }
            });
        }
    }
}
