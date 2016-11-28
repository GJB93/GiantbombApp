package ie.dit.giantbombapp.view.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ie.dit.giantbombapp.R;
import ie.dit.giantbombapp.model.pojos.Search;

/**
 * Author: Graham Byrne
 *
 * Created: 24/11/2016
 * Modified: 25/11/2016
 */

public class SearchArrayAdapter extends ArrayAdapter<Search>
{
    private static final String TAG = "SearchArrayAdapter";
    private final Context ctx;
    private List<Search> mData;

    public SearchArrayAdapter(Context context, int resource, List<Search> objects)
    {
        super(context, resource, objects);
        mData = new ArrayList<>();
        mData = objects;
        this.ctx = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View row = convertView;


        if(row == null)
        {
            row = LayoutInflater.from(ctx).inflate(R.layout.search_row, parent, false);
        }

        ImageView thumbnail = (ImageView) row.findViewById(R.id.search_thumbnail);
        TextView name = (TextView) row.findViewById(R.id.search_name);
        TextView deck = (TextView) row.findViewById(R.id.search_deck);

        String searchName = mData.get(position).getName();
        String searchDeck = mData.get(position).getDeck();
        String searchThumb = null;

        if(mData.get(position).getImage() != null)
            searchThumb = mData.get(position).getImage().getThumbUrl();


        if(searchThumb != null)
            Picasso.with(ctx).load(searchThumb).into(thumbnail);
        name.setText(searchName);
        deck.setText(searchDeck);
        return row;

    }
}
