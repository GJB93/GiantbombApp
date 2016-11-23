package ie.dit.giantbombapp.view;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ie.dit.giantbombapp.R;
import ie.dit.giantbombapp.controller.MainController;

/**
 * Created by graha on 23/11/2016.
 */

public class ReviewActivity extends AppCompatActivity
{
    private static final String TAG = "ReviewActivity";
    private MainController controller;
    private Cursor result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_activity);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        controller = new MainController(getBaseContext(), getString(R.string.api_key), getString(R.string.format), getString(R.string.sort));
        Intent i = getIntent();
        result = controller.fetchReview(i.getIntExtra("GameId", -1));

        TextView title = (TextView) findViewById(R.id.review_name);
        TextView score = (TextView) findViewById(R.id.score);
        TextView deck = (TextView) findViewById(R.id.deck);


        title.setText(result.getString(3));
        score.setText("Score: " + result.getInt(8));
        deck.setText(result.getString(2));
    }
}
