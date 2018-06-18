package com.example.basit009.vaccs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SectionPagerAdapterVaccines extends FragmentPagerAdapter {


    public SectionPagerAdapterVaccines(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0:
                BrandsFragment brandsFragment=new BrandsFragment();
                return brandsFragment;

            case 1:
                DosesFragment dosesFragment=new DosesFragment();
                return dosesFragment;
        }

        return null;
    }
}
