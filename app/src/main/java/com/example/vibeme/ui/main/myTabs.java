package com.example.vibeme.ui.main;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.vibeme.vibes.vibeFragment;
import com.example.vibeme.vibers_frag.vibersFragment;
import com.example.vibeme.request_frag.Request;

public class myTabs extends FragmentPagerAdapter {
    public myTabs(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 0:
                vibeFragment chatFragment = new vibeFragment();
                return chatFragment;

            case 1:
                vibersFragment contactFragment = new vibersFragment();
                return contactFragment;


            case 2:
                Request RequestFragment = new Request();
                return RequestFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {

            case 0:
                return "vibes";
            case 1:
                return "vibers";

            case 2:
                return "Requests";
            default:
                return null;
        }
    }
}
