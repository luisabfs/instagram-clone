package com.parse.starter.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.starter.R;
import com.parse.starter.adapter.PostAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    //layout
    private ListView listView;

    //lists
    private ArrayList<ParseObject> posts;
    private ArrayAdapter<ParseObject> adapter;
    private ParseQuery<ParseObject> query;

    //constants
    private static final String TAG = "HomeFragment";

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        /*
         setting listview and adapter
         */
        posts = new ArrayList<>();
        listView = view.findViewById(R.id.lv_home);
        adapter = new PostAdapter(getActivity(), posts);
        listView.setAdapter(adapter);

        getPosts();

        return view;
    }

    private void getPosts(){
        //retrieving photos
        query = ParseQuery.getQuery("Photo");
        query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query.orderByDescending("createdAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null){
                    Log.i(TAG, "query: sucess");
                    if(objects.size() > 0){
                        posts.clear();

                        for(ParseObject parseObject: objects){
                            posts.add(parseObject);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }else{
                    Log.i(TAG, "query: " + e.getMessage());
                }
            }
        });
    }

    public void updatePosts(){
        getPosts();
    }
}
