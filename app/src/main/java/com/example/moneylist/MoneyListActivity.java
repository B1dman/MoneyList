package com.example.moneylist;

import android.support.v4.app.Fragment;

public class MoneyListActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        return new MoneyListFragment();
    }
}
