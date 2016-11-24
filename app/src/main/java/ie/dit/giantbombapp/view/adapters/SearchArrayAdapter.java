package ie.dit.giantbombapp.view.adapters;

import android.content.Context;
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
 * Created by graha on 24/11/2016.
 */

public class SearchArrayAdapter extends ArrayAdapter<Search>
{
    Context ctx;
    List<Search> mData;
    int resource;

    public SearchArrayAdapter(Context context, int resource, List<Search> objects)
    {
        super(context, resource, objects);
        mData = new ArrayList<>();
        mData = objects;
        this.ctx = context;
        this.resource = resource;
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
        String searchThumb = mData.get(position).getImage().getThumbUrl();

        Picasso.with(ctx).load(searchThumb).into(thumbnail);
        name.setText(mData.get(position).getName());
        deck.setText(searchDeck);
        return row;

    }
}
