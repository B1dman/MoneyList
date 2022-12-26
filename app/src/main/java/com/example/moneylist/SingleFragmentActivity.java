package com.example.moneylist;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.moneylist.ui.MeFragment;

public abstract class SingleFragmentActivity extends AppCompatActivity {
    protected abstract Fragment createFragment();

    private FrameLayout mNavContainer;
    protected BottomNavigationView mNavView;
    private MoneyListFragment moneyListFragment;
    private MeFragment mMeFragment;

    @SuppressLint("WrongViewCast")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

       moneyListFragment = new MoneyListFragment();
        mMeFragment = new MeFragment();
        mNavView = findViewById(R.id.nav_view);

        mNavContainer = findViewById(R.id.fragment_container);

        //关联
        mNavView.setOnNavigationItemSelectedListener(mNavItemListener);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,moneyListFragment).commitNow();

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mNavItemListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.navigation_me:
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container,mMeFragment).commitNow();
                    break;
                case R.id.navigation_remark:
                    FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                    transaction1.replace(R.id.fragment_container,moneyListFragment).commitNow();
                    break;
            }
            return true;
        }
    };
}
