package com.example.moneylist;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

public class MoneyActivity extends SingleFragmentActivity{
    private static final String EXTRA_MONEY_ID="com.example.moneylist.money_id";

    public static Intent newIntent(Context packageContext, UUID moneyId){
        Intent intent=new Intent(packageContext,MoneyActivity.class);
        intent.putExtra(EXTRA_MONEY_ID,moneyId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        UUID moneyId = (UUID)getIntent().getSerializableExtra(EXTRA_MONEY_ID);
        //传入Fragment
        return MoneyFragment.newInstance(moneyId);
    }
}
