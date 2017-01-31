/*
package com.nishitadutta.investomaster.stocks;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nishitadutta.investomaster.R;
import com.nishitadutta.investomaster.utils.SharedPreferenceUtils;
import com.nishitadutta.investomaster.widgets.DividerItemDecoration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;


public class FavoritesFragment extends Fragment {
    public static final String TAG="FavoritesFragment";
    @BindView(R.id.rvStockFItems)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_fcontainer)
    SwipeRefreshLayout swipeContainer;
    @BindView(R.id.fprogress)
    ProgressBar progressBar;
    @BindView(R.id.fticker)
    TextView ticker;
    String[] symbols = new String[60];
    RecyclerAdapterStocks recyclerAdapterStocks;
    LinearLayoutManager linearLayoutManager;
    ArrayList<Stock> stockObjects;
    String tickerString;
    SharedPreferenceUtils sharedPreferenceUtils;
    Context context;
    Map<String, Stock> stocks;
    public FavoritesFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorites, container, false);
        ButterKnife.bind(this, rootView);
        context = getContext();
        sharedPreferenceUtils=SharedPreferenceUtils.getInstance(context);
        stockObjects=sharedPreferenceUtils.getFavorites();
        if(stockObjects==null)
            stockObjects=new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerAdapterStocks=new RecyclerAdapterStocks(context, stockObjects);
        recyclerView.setAdapter(recyclerAdapterStocks);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(context,null));
        */
/*for(int i=0;i<objects.length;i++){
            symbols[i]=objects[i].toString();
            Log.e(TAG, "onCreateView: " + symbols[i]);
        }
        stockObjects = new ArrayList<>();
        if(symbols.length>0){
            new StockJob().execute();
        }else
        {
            Toast.makeText(context, "No favorites added. Long press on star icon to create a favourite", Toast.LENGTH_LONG).show();
        }
        ticker.setSelected(true);*//*


       */
/* swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(symbols.length>0){
                    new StockJob().execute();
                }else
                {
                    Toast.makeText(context, "No favorites added. Long press on star icon to create a favourite", Toast.LENGTH_LONG).show();
                }
            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
*//*

        return rootView;
    }
    public void setProgressVisible(final int v) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(v);
            }
        });
    }
   */
/* private class StockJob extends AsyncTask<String, Void, Void> {


        @Override
        protected Void doInBackground(String... params) {
            setProgressVisible(View.VISIBLE);

            try {
                stocks = YahooFinance.get(symbols);
            } catch (IOException e) {
                System.out.println(e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            setProgressVisible(View.GONE);
            tickerString=new String("");
            if (stocks != null) {
                stockObjects=new ArrayList<>();
                for (Map.Entry<String, Stock> entry : stocks.entrySet()) {

                    stockObjects.add(entry.getValue());
                   // tickerString+=entry.getValue().getSymbol()+ " " + entry.getValue().getQuote().getChange()+ " | ";
                    //Log.e(TAG, "onPostExecute: " + entry.getValue().getName());
                }
                if (recyclerAdapterStocks == null) {
                    recyclerAdapterStocks = new RecyclerAdapterStocks(context, stockObjects);
                    recyclerView.setAdapter(recyclerAdapterStocks);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.addItemDecoration(new DividerItemDecoration(context, null));
                } else {
                    Log.e(TAG, "onPostExecute: " + "swiped" );
                    recyclerAdapterStocks.clear();
                    recyclerAdapterStocks.addAll(stockObjects);
                    swipeContainer.setRefreshing(false);
                }
                setTickerString();
            }
            else{
                Toast.makeText(context, "Check your internet connection and try again.", Toast.LENGTH_SHORT).show();
            }
        }
    }
*//*

    private void setTickerString() {
        if(tickerString!=null)
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ticker.setText(tickerString);
                }
            });
    }
}
*/
