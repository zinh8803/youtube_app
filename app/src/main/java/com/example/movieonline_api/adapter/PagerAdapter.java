package com.example.movieonline_api.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.annotation.NonNull;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.movieonline_api.Fragment.HomeFragment;
import com.example.movieonline_api.Fragment.UserFragment;
import com.example.movieonline_api.Fragment.hisFragment;
import com.example.movieonline_api.Fragment.SearchFragment;

public class PagerAdapter extends FragmentStateAdapter {
    public PagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
//            case 0:
//                HomeFragment homeFragment = new HomeFragment();
//                return homeFragment;
//            case 1:
//                hisFragment hisFragment = new hisFragment();
//                return hisFragment;
//            case 2:
//                UserFragment userFragment = new UserFragment();
//                return userFragment;
//            case 3:
//                SearchFragment SearchFragment = new SearchFragment();
//                return  SearchFragment; // Tìm kiếm
//            default:
//                return null;
            case 0:
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
                SearchFragment SearchFragment = new SearchFragment();
                return  SearchFragment; // Tìm kiếm
            case 2:
                hisFragment hisFragment = new hisFragment();
                return hisFragment;
            case 3:
                UserFragment userFragment = new UserFragment();
                return userFragment;

            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
