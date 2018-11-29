package com.parse.starter.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.parse.ParseObject;
import com.parse.starter.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostAdapter extends ArrayAdapter<ParseObject> {
    private Context context;
    private ArrayList<ParseObject> posts;

    public PostAdapter(@NonNull Context c, @NonNull ArrayList<ParseObject> objects) {
        super(c, 0, objects);
        this.context = c;
        this.posts = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if(view == null){
            //initializing object
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.post_list, parent, false);
        }

        if(posts.size() > 0){
            ImageView photoPost = view.findViewById(R.id.photo_post_list);

            ParseObject parseObject = posts.get(position);

            //parseObject.getParseFile("photo");
            Picasso.get()
                    .load(parseObject.getParseFile("photo").getUrl())
                    .fit()
                    .into(photoPost);
        }

        return view;
    }
}
