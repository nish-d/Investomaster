package com.nishitadutta.investomaster.stocks;

import android.content.Context;
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
import com.nishitadutta.investomaster.widgets.DividerItemDecoration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;


public class StockFragment extends Fragment {

    private static final String TAG = "StockFragment";
    @BindView(R.id.rvStockItems)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;
    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.ticker)
    TextView ticker;
    RecyclerAdapterStocks recyclerAdapterStocks;
    LinearLayoutManager linearLayoutManager;
    ArrayList<Stock> stockObjects;
    Stock stock;
    String tickerString;
    String[] symbols = new String[]{"INTC", "BABA","TANH","TEDU","TTM","VDTH","INFY", "IBN","JD", "JKS", "TSLA", "AIR.PA", "YHOO", "AZRE", "ZNH", "WIT", "SIFY"};
    Map<String, Stock> stocks;
    Context context;

    public StockFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_stock, container, false);
        ButterKnife.bind(this, rootView);
        context = getContext();
        stockObjects = new ArrayList<>();
        new StockJob().execute();
        ticker.setSelected(true);
        linearLayoutManager = new LinearLayoutManager(getContext());
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new StockJob().execute();
            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

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

    private class StockJob extends AsyncTask<String, Void, Void> {


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
            tickerString=new String();
            if (stocks != null) {
                stockObjects=new ArrayList<>();
                for (Map.Entry<String, Stock> entry : stocks.entrySet()) {

                    stockObjects.add(entry.getValue());
                    tickerString+=entry.getValue().getSymbol()+ " " + entry.getValue().getQuote().getChange()+ " | ";
                    Log.e(TAG, "onPostExecute: " + entry.getValue().getName().toString() + entry.getValue().getQuote().toString());
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
