package com.lee.flickster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lee.flickster.R;
import com.lee.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;


/**
 * Created by lee on 7/20/16.
 */
public class MovieArrayAdapter extends ArrayAdapter<Movie> {
    //view lookup cache
    static class ViewHolder {
        @BindView(R.id.tvTitle) TextView title;
        @BindView(R.id.tvOverview) TextView overview;
        @BindView(R.id.ivMovieImage) ImageView image;
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public MovieArrayAdapter(Context context, List<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = getItem(position);

        ViewHolder viewHolder; //caches view lookups; avoid repeated findViewById calls
        if (convertView == null) { //check if view is recycled
            // create new view
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_movie, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder); //attaches arbitrary object onto a View object
        } else {
            // use recycled view
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //clear out image from convertView
        viewHolder.image.setImageResource(0);

        // Populate the data into the template view using the data object
        viewHolder.title.setText(movie.getOriginalTitle());
        viewHolder.overview.setText(movie.getOverview());

        Integer orientation = getContext().getResources().getConfiguration().orientation;
        String imagePath;
        if(orientation == Configuration.ORIENTATION_PORTRAIT) {
            imagePath = movie.getPosterPath();
        } else {
            imagePath = movie.getBackdropPath();
        }

        Picasso.with(getContext()).load(imagePath).fit().placeholder(R.drawable.ticket).error(R.drawable.ticket).transform(new RoundedCornersTransformation(10, 10)).centerCrop().into(viewHolder.image);

        return convertView;
    }
}
