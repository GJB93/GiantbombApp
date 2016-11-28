package ie.dit.giantbombapp.view.adapters;

import android.content.Context;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import ie.dit.giantbombapp.R;

/**
 * Author: Graham Byrne
 *
 * Created: 23/11/2016
 * Modified: 25/11/2016
 */

public class PromoCursorAdapter extends CursorAdapter {

    private static final String TAG = "PromoCursorAdapter";
    public PromoCursorAdapter(Context ctx, Cursor c)
    {
        super(ctx, c, 0);
    }

    @Override
    public View newView(Context ctx, Cursor c, ViewGroup g)
    {
        return LayoutInflater.from(ctx).inflate(R.layout.promo_row, g, false);
    }

    @Override
    public void bindView(View v, Context ctx, Cursor c)
    {
        TextView promoTitle = (TextView) v.findViewById(R.id.promo_title);
        TextView promoAuthor = (TextView) v.findViewById(R.id.promo_author);
        TextView promoDate = (TextView) v.findViewById(R.id.promo_date);
        ImageView promoType = (ImageView) v.findViewById(R.id.promo_type);

        String title = "" + c.getString(c.getColumnIndexOrThrow("promo_title"));
        String author = c.getString(c.getColumnIndexOrThrow("author"));
        String date = "" + c.getString(c.getColumnIndexOrThrow("date_added"));
        String type = "" + c.getString(c.getColumnIndexOrThrow("resource_type"));

        promoTitle.setText(title);
        if (author != null)
            promoAuthor.setText(author);
        else
            promoAuthor.setVisibility(View.GONE);
        promoDate.setText(date);

        if("video".equals(type))
        {
            promoType.setImageResource(R.drawable.video);
        }
        else if ("podcast".equals(type))
        {
            promoType.setImageResource(R.drawable.sound);
        }
        else
        {
            promoType.setImageResource(R.drawable.other_type);
        }
    }
}

