package com.example.basit009.vaccs;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).

        switch (position)
        {
            case 0:
                DoctorsFragment doctorsFragment=new DoctorsFragment();
                return doctorsFragment;

            case 1:
                ChildrenFragment childrenFragment=new ChildrenFragment();
                return childrenFragment;

            case 2:
                VaccinesFragment vaccinesFragment=new VaccinesFragment();
                return vaccinesFragment;

        }

        return null;
        //return PlaceholderFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }
}
