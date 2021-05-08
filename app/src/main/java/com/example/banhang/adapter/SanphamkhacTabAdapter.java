package com.example.banhang.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.banhang.fragment.Fragment_A;
import com.example.banhang.fragment.Fragment_B;
import com.example.banhang.fragment.Fragment_C;
import com.example.banhang.fragment.Fragment_D;
import com.example.banhang.fragment.Fragment_E;
import com.example.banhang.fragment.Fragment_F;
import com.example.banhang.fragment.Fragment_G;

public class SanphamkhacTabAdapter extends FragmentPagerAdapter {
    private String[] listTab;
    private Fragment_D fragment_d;
    private Fragment_E fragment_e;
    private Fragment_F fragment_f;
    private Fragment_G fragment_g;

    public SanphamkhacTabAdapter(@NonNull FragmentManager fm, int p1, int p2, int p3, int p4, String[] listTab, String localServer) {
        super(fm);
        this.listTab = listTab;
        fragment_d = new Fragment_D(p1, localServer);
        fragment_e = new Fragment_E(p2, localServer);
        fragment_f = new Fragment_F(p3,localServer);
        fragment_g = new Fragment_G(p4,localServer);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return fragment_d;
        } else if (position == 1) {
            return fragment_e;
        }else if (position == 2) {
            return fragment_f;
        }else if (position == 3) {
            return fragment_g;
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
