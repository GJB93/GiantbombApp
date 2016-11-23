package ie.dit.giantbombapp.view;

import android.content.Intent;
import android.database.Cursor;
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

public class PromoListActivity extends AppCompatActivity {

    private static final String TAG = "PromoListActivity";
    private MainController controller;
    private Cursor result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promo_list_activity);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        controller = new MainController(getBaseContext(), getString(R.string.api_key), getString(R.string.format), getString(R.string.sort));
        result = controller.fetchAllPromos();
        ListView promo_list = (ListView) findViewById(R.id.promo_list);
        Log.d(TAG, "Set the list view");
        MyCustomAdapter customAdapter = new MyCustomAdapter(getBaseContext(), result);
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

        //controller.fetchPromo(12229);
        //controller.fetchAllPromos();
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
}
