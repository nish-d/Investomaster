package com.nishitadutta.investomaster;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.nishitadutta.investomaster.settings.SettingsActivity;
import com.nishitadutta.investomaster.utils.Navigator;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.io.IOException;
import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

public class MainActivity extends AppCompatActivity {
    private static String TAG = "MainActivity";
    Stock stock;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    CustomPagerAdapter customPagerAdapter;
    @BindView(R.id.tab)
    SmartTabLayout smartTabLayout;
    @BindView(R.id.main_toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
       // new StockJob().execute();
        smartTabLayout.setCustomTabView(R.layout.partial_tab, R.id.tab_text);
        customPagerAdapter = new CustomPagerAdapter(getSupportFragmentManager(),this);
        viewPager.setAdapter(customPagerAdapter);
        smartTabLayout.setViewPager(viewPager);
        toolbar.setTitle("Investomaster_13");
        toolbar.setTitleTextColor(Color.WHITE);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_settings) {
            Navigator navigator=new Navigator();
            navigator.openNewActivity(this, new SettingsActivity());
            finish();
            return true;
        }/*else if (id == R.id.menu_logout) {
            FirebaseAuth.getInstance().signOut();
            HealthVaultApp.getInstance(this).reset();
            locationSet = false;
            navigator.openNewActivity(this, new LoginActivity());
            this.finish();
            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }
    private class StockJob extends AsyncTask<String, Void, Void> {


        @Override
        protected Void doInBackground(String... params) {
            try {
                stock = YahooFinance.get("INTC");

            } catch (IOException e) {
                System.out.println(e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(stock!=null) {
                BigDecimal price = stock.getQuote().getPrice();
                BigDecimal change = stock.getQuote().getChangeInPercent();
                BigDecimal peg = stock.getStats().getPeg();
                BigDecimal dividend = stock.getDividend().getAnnualYieldPercent();
                Log.e(TAG, price + " " + change + " " + peg + " " + dividend);
            }
        }
    }
}
