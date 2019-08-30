package org.d3ifcool.hutang;

import android.content.Intent;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    BottomAppBar bottomAppBar;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // untuk pemanggilan bottom bar app
        bottomBar();
        // untuk pemanggilan view pager dan tab layout
        viewPagerNtabLayout();
        //untuk pemanggilan floating action button
        floatingButton();
    }

    private void floatingButton(){
        //untuk pemanggilan floating action button
        floatingActionButton = findViewById(R.id.fabIndex);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FormHutang.class);
                startActivity(intent);
            }
        });
    }

    private void bottomBar(){
        //untuk bottom app bar nya
        bottomAppBar = findViewById(R.id.bottom_app_bar);
        bottomAppBar.replaceMenu(R.menu.bottom_menu);
        bottomAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.action_setting:
                        Toast.makeText(MainActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
        bottomAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Berhasil Bray", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void viewPagerNtabLayout(){
        // untuk pemanggilan slider nya atau view pager
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager_Hutang);
        KategoriHutangAdapter hutangAdapter = new KategoriHutangAdapter(MainActivity.this, getSupportFragmentManager());
        viewPager.setAdapter(hutangAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs_hutang);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.history_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_history){
            Toast.makeText(MainActivity.this, "Pembukaan History", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
