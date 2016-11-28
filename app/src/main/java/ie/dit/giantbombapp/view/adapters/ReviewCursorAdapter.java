package ie.dit.giantbombapp.view.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;


import ie.dit.giantbombapp.R;

/**
 * Author: Graham Byrne
 *
 * Created: 23/11/2016
 * Modified: 25/11/2016
 */

public class ReviewCursorAdapter extends CursorAdapter
{

    private static final String TAG = "ReviewCursorAdapter";
    public ReviewCursorAdapter(Context ctx, Cursor c)
    {
        super(ctx, c, 0);
    }

    @Override
    public View newView(Context ctx, Cursor c, ViewGroup g)
    {
        return LayoutInflater.from(ctx).inflate(R.layout.review_row, g, false);
    }

    @Override
    public void bindView(View v, Context ctx, Cursor c)
    {
        TextView reviewGame = (TextView) v.findViewById(R.id.game_name);
        TextView reviewScore = (TextView) v.findViewById(R.id.review_score);
        TextView reviewDeck = (TextView) v.findViewById(R.id.review_deck);
        TextView reviewAuthor = (TextView) v.findViewById(R.id.review_author);

        String game = "" + c.getString(c.getColumnIndexOrThrow("game_name"));
        int score = c.getInt(c.getColumnIndexOrThrow("score"));
        String deck = "" + c.getString(c.getColumnIndexOrThrow("deck"));
        String author = "" + c.getString(c.getColumnIndexOrThrow("author"));
        String scoreString = "Score: " + score + "/5";

        reviewGame.setText(game);
        reviewScore.setText(scoreString);
        reviewDeck.setText(deck);
        reviewAuthor.setText(author);

    }
}
