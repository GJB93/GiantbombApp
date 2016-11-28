package ie.dit.giantbombapp.view.activities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ie.dit.giantbombapp.R;
import ie.dit.giantbombapp.controller.MainController;

/**
 * Author: Graham Byrne
 *
 * Created: 23/11/2016
 * Modified: 25/11/2016
 *
 * Activity formerly used to show a more detailed view of each object
 * in the ReviewListActivity List. Replaced with a web browser intent call
 */

public class ReviewActivity extends AppCompatActivity
{
    private static final String TAG = "ReviewActivity";
    private MainController controller;
    private Cursor result;
    private TextView title, score, deck;
    private Button connectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_activity);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        try
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException e)
        {
            Log.d(TAG, "Cannot set up home button");
        }
        controller = new MainController(getBaseContext(), getString(R.string.api_key), getString(R.string.format), getString(R.string.sort));
        Intent i = getIntent();
        result = controller.fetchReview(i.getIntExtra("GameId", -1));

        title = (TextView) findViewById(R.id.review_name);
        score = (TextView) findViewById(R.id.score);
        deck = (TextView) findViewById(R.id.deck);
        connectButton = (Button) findViewById(R.id.connect);
        final String reviewScore = "Score: " + result.getInt(result.getColumnIndexOrThrow("score"));


        title.setText(result.getString(result.getColumnIndexOrThrow("game_name")));
        score.setText(reviewScore);
        deck.setText(result.getString(result.getColumnIndexOrThrow("deck")));

        connectButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Uri uri = Uri.parse(result.getString(result.getColumnIndexOrThrow("site_url")));
                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(i);
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

            case android.R.id.home:
            {
                NavUtils.navigateUpFromSameTask(this);
                return true;
            }

            case R.id.to_promos:
            {
                Intent i = new Intent(getBaseContext(), PromoListActivity.class);
                startActivity(i);
                finish();
                return true;
            }

            case R.id.to_search:
            {
                Intent i = new Intent(getBaseContext(), SearchListActivity.class);
                startActivity(i);
                finish();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
