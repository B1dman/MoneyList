package com.example.moneylist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

public class MoneyPagerActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private List<Amoney> mMoneys;

    private static final String EXTRA_MONEY_ID="com.example.moneylist.money_id";

    //接收数据
    public static Intent newIntent(Context packageContext, UUID moneyId){
        Intent intent=new Intent(packageContext,MoneyPagerActivity.class);
        intent.putExtra(EXTRA_MONEY_ID,moneyId);
        return intent;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_pager);

        //被点击的money 的id信息
        UUID Moneyid = (UUID) getIntent().getSerializableExtra(EXTRA_MONEY_ID);


        mViewPager = (ViewPager) findViewById(R.id.activity_money_pager_view_pager);
        mMoneys = MoneyLab.get(this).getMoneys();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

            //获取并显示money数据中指定的详情信息,初始化pager
            @Override
            public Fragment getItem(int position) {
                Amoney amoney = mMoneys.get(position);
                return MoneyFragment.newInstance(amoney.getId());
            }

            //获取数组列表大小
            @Override
            public int getCount() {
                return mMoneys.size();
            }
        });

        //跳转点击的money页面
        for (int i = 0; i < mMoneys.size(); i++) {
            if (mMoneys.get(i).getId().equals(Moneyid)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
