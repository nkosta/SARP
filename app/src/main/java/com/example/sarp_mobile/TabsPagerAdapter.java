package com.example.sarp_mobile;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                ProcessesDataFragment dat;
                dat =  new ProcessesDataFragment();
                //dat.setRetainInstance(true);
                return dat;
            case 1:
                SimulationFragment sim;
                sim = new SimulationFragment();
                //sim.setRetainInstance(true);
                return sim;
        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 2;
    }

}
