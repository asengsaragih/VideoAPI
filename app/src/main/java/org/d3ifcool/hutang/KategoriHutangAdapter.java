package org.d3ifcool.hutang;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class KategoriHutangAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public KategoriHutangAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 0) {
            return new DipinjamkanFragment();
        } else {
            return new MeminjamFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    public CharSequence getPageTitle(int position) {
        if (position == 0){
            return mContext.getString(R.string.dipinjamkan);
        } else {
            return mContext.getString(R.string.meminjam);
        }
    }
}
