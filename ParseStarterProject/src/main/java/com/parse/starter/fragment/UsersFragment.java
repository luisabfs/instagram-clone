package com.parse.starter.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.starter.R;
import com.parse.starter.activity.LoginActivity;
import com.parse.starter.activity.UsersFeedActivity;
import com.parse.starter.adapter.UsersAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsersFragment extends Fragment {
    //layout
    private ListView listView;

    //lists
    private ArrayList<ParseUser> users;
    private ArrayAdapter<ParseUser> adapter;
    private ParseQuery<ParseUser> query;

    //contants
    private static final String TAG = "UsersFragment";
    public UsersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_users, container, false);

        users = new ArrayList<>();
        listView = view.findViewById(R.id.lv_users);
        adapter = new UsersAdapter(getActivity(), users);
        listView.setAdapter(adapter);

        getUsers();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ParseUser parseUser = users.get(position);
                Intent intent = new Intent(getActivity(), UsersFeedActivity.class);
                intent.putExtra("username", parseUser.getUsername());
                startActivity(intent);
            }
        });

        return view;
    }

    private void getUsers(){
        query = ParseUser.getQuery();
        query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query.orderByAscending("username");

        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if(e == null){
                    if(objects.size() > 0){
                        users.clear();
                        for(ParseUser parseUser: objects){
                            users.add(parseUser);
                        }

                        adapter.notifyDataSetChanged();
                    }
                }else{
                    Log.i(TAG, "query: " + e.getMessage());
                }
            }
        });
    }

}
