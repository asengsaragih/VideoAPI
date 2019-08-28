package org.d3ifcool.hutang;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // untuk pemanggilan slider nya atau view pager
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager_Hutang);
        KategoriHutangAdapter hutangAdapter = new KategoriHutangAdapter(MainActivity.this, getSupportFragmentManager());
        viewPager.setAdapter(hutangAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs_hutang);
        tabLayout.setupWithViewPager(viewPager);
    }
}
