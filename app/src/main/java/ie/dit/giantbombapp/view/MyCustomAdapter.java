package ie.dit.giantbombapp.view;

import android.content.Context;

import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ie.dit.giantbombapp.R;

/**
 * Created by Graham on 27-Oct-16.
 */

public class MyCustomAdapter extends CursorAdapter {

    private static final String TAG = "MyCustomAdapter";
    MyCustomAdapter(Context ctx, Cursor c)
    {
        super(ctx, c, 0);
        Log.d(TAG, "Creating an adapter");
    }

    @Override
    public View newView(Context ctx, Cursor c, ViewGroup g)
    {
        Log.d(TAG, "Creating an inflated view");
        return LayoutInflater.from(ctx).inflate(R.layout.promo_row, g, false);
    }

    @Override
    public void bindView(View v, Context ctx, Cursor c)
    {
        Log.d(TAG, "Getting views in BindView");
        ImageView promoThumbnail = (ImageView) v.findViewById(R.id.promo_thumbnail);
        TextView promoTitle = (TextView) v.findViewById(R.id.promo_title);
        TextView promoAuthor = (TextView) v.findViewById(R.id.promo_author);
        TextView promoDate = (TextView) v.findViewById(R.id.promo_date);
        ImageView promoType = (ImageView) v.findViewById(R.id.promo_type);

        Log.d(TAG, "Getting cursor strings in BindView");
        String title = c.getString(c.getColumnIndexOrThrow("promo_title"));
        String author = c.getString(c.getColumnIndexOrThrow("author"));
        String date = c.getString(c.getColumnIndexOrThrow("date_added"));
        String type = c.getString(c.getColumnIndexOrThrow("resource_type"));
        String thumbnail = c.getString(c.getColumnIndexOrThrow("thumbnail"));

        if(thumbnail != null)
            Picasso.with(ctx).load(thumbnail).into(promoThumbnail);

        if(title != null)
            promoTitle.setText("" + title);

        if(author != null)
            promoAuthor.setText("" + author);

        if(date != null)
            promoDate.setText("" + date);

        if("video".equals(type))
        {
            Log.d(TAG, "Getting video image");
            promoType.setImageResource(R.drawable.video);
        }
        else if ("podcast".equals(type))
        {
            Log.d(TAG, "Getting sound image");
            promoType.setImageResource(R.drawable.sound);
        }
        else
        {
            Log.d(TAG, "Getting other image");
            promoType.setImageResource(R.drawable.other_type);
        }
        Log.d(TAG, "Ending BindView");
    }
}

