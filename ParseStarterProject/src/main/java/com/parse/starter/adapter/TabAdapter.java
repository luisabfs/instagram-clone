package com.parse.starter.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.ViewGroup;

import com.parse.starter.R;
import com.parse.starter.fragment.HomeFragment;
import com.parse.starter.fragment.UsersFragment;

import java.util.HashMap;

public class TabAdapter extends FragmentStatePagerAdapter {
    private Context context;
    private int[] icons = new int[]{R.drawable.ic_action_home, R.drawable.ic_people};
    private int iconSize;
    private HashMap<Integer, Fragment> fragments;

    public TabAdapter(FragmentManager fm, Context c) {
        super(fm);
        this.context = c;
        double scale = this.context.getResources().getDisplayMetrics().density;
        iconSize = (int) (36 * scale);
        this.fragments = new HashMap<>();
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position){
            case 0:
                fragment = new HomeFragment();
                fragments.put(position, fragment);
                break;
            case 1:
                fragment = new UsersFragment();
                break;
        }

        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        fragments.remove(position);
    }

    public Fragment getFragment(Integer id){
        return fragments.get(id);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Drawable drawable = ContextCompat.getDrawable(this.context, icons[position]);
        drawable.setBounds(0, 0, iconSize, iconSize);

        ImageSpan imageSpan = new ImageSpan(drawable);

        SpannableString spannableString = new SpannableString(" ");
        spannableString.setSpan(imageSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    @Override
    public int getCount() {
        return icons.length;
    }
}
