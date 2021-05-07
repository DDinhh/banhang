package com.example.banhang.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.banhang.fragment.Fragment_A;
import com.example.banhang.fragment.Fragment_B;
import com.example.banhang.fragment.Fragment_C;

public class TabAdapter extends FragmentPagerAdapter {
    private String[] listTab;
    private Fragment_A fragment_a;
    private Fragment_B fragment_b;
    private Fragment_C fragment_c;


    public TabAdapter(@NonNull FragmentManager fm, int p1, int p2, int p3, String p4, String[] listTab, String localServer) {
        super(fm);
        this.listTab = listTab;
        fragment_a = new Fragment_A(p1,p4, localServer);
        fragment_b = new Fragment_B(p2,p4, localServer);
        fragment_c = new Fragment_C(p3,p4,localServer);

    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return fragment_a;
        } else if (position == 1) {
            return fragment_b;
        }else if (position == 2) {
            return fragment_c;
        }
        return null;
    }

    @Override
    public int getCount() {
        return listTab.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return listTab[position];
    }
}
