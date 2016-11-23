package ie.dit.giantbombapp.view;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import ie.dit.giantbombapp.R;
import ie.dit.giantbombapp.controller.MainController;

/**
 * Created by graha on 23/11/2016.
 */

public class PromoActivity extends AppCompatActivity
{
    private static final String TAG = "PromoActivity";
    private MainController controller;
    private Cursor result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promo_activity);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        controller = new MainController(getBaseContext(), getString(R.string.api_key), getString(R.string.format), getString(R.string.sort));
        Intent i = getIntent();
        result = controller.fetchPromo(i.getIntExtra("PromoId", -1));

        ImageView header = (ImageView) findViewById(R.id.header_image);
        TextView title = (TextView) findViewById(R.id.title);
        TextView deck = (TextView) findViewById(R.id.promo_deck);


        Picasso.with(this).load(result.getString(4)).into(header);
        title.setText(result.getString(7));
        deck.setText(result.getString(3));


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
