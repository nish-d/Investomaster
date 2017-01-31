package com.nishitadutta.investomaster.stocks;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nishitadutta.investomaster.utils.Navigator;
import com.nishitadutta.investomaster.R;
import com.nishitadutta.investomaster.utils.AppConstants;
import com.nishitadutta.investomaster.utils.SharedPreferenceUtils;

import java.math.BigDecimal;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import yahoofinance.Stock;

/**
 * Created by Nishita on 07-01-2017.
 */

public class RecyclerAdapterStocks extends
        RecyclerView.Adapter<RecyclerAdapterStocks.NewsViewHolder> {

    private static final String TAG = "RecyclerAdapterStocks";
    Context context;
    SharedPreferenceUtils sharedPreferenceUtils;//=SharedPreferenceUtils.getInstance(context);
    private ArrayList<Stock> stockList;
    private ArrayList<String> favourites;

    public RecyclerAdapterStocks(Context context, ArrayList<Stock> stockList) {
        this.context = context;
        this.stockList = stockList;
        sharedPreferenceUtils = SharedPreferenceUtils.getInstance(context);
        favourites = sharedPreferenceUtils.getFavorites();
        if (favourites == null) {
            favourites = new ArrayList<>();
        }
    }

    public void clear() {
        stockList.clear();
        Log.e(TAG, "clear: ");
        notifyDataSetChanged();
    }

    // Add a list of items
    public void addAll(ArrayList<Stock> list) {
        stockList.addAll(list);
        for (Stock stock : stockList) {
            Log.e(TAG, "addAll: " + stock.getName());
        }
        notifyDataSetChanged();
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //creates a RecyclerViewHolderList that holds a view of R.layout.recycler_list_item type
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView;
        itemView = inflater.inflate(R.layout.stock_list_item, parent, false);
        return new NewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {

        Log.e(TAG, "RecyclerAdapterStocks: " + stockList.get(position).getName());
        if (stockList.get(position)
                .getQuote()
                .getChange()
                .compareTo(BigDecimal.ZERO) == -1) {
            holder.tvChangeInPrice.setTextColor(Color.RED);
            holder.tvPercentagePrice.setTextColor(Color.RED);
            holder.imgInc.setImageResource(R.drawable.ic_action_down);

        }
        String sym = stockList.get(position).getSymbol();
        holder.tvSymbol.setText(sym);
        holder.tvName.setText(stockList.get(position).getName());
        holder.tvChangeInPrice.setText(stockList.get(position).getQuote().getChange() + "");
        holder.tvPrice.setText(stockList.get(position).getQuote().getPrice() + "");
        holder.tvPercentagePrice.setText("("+stockList.get(position).getQuote().getChangeInPercent() + "%)");
        /*for(Stock fav:favourites){
            if(fav.getSymbol().equals(sym.getSymbol()))
            holder.imgFav.setImageResource(R.drawable.ic_action_name);
        }*/
        if(favourites.indexOf(sym)>=0)
            holder.imgFav.setImageResource(R.drawable.ic_action_name);
    }

    @Override
    public int getItemCount() {
        return stockList.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {
        public
        @BindView(R.id.tv_name)
        TextView tvName;

        public
        @BindView(R.id.tv_symbol)
        TextView tvSymbol;

        public
        @BindView(R.id.tv_change_in_price)
        TextView tvChangeInPrice;

        public
        @BindView(R.id.tv_percentage_price)
        TextView tvPercentagePrice;

        public
        @BindView(R.id.img_inc)
        ImageView imgInc;

        public
        @BindView(R.id.tv_price)
        TextView tvPrice;
        public
        @BindView(R.id.img_fav)
        ImageView imgFav;
        StockObject stockObject;

        public NewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // stockObject = new StockObject(stockList.get(getPosition()));
                    Bundle bundle = new Bundle();
                    bundle.putString(AppConstants.STOCK_OBJECT_KEY, stockList.get(getPosition()).getSymbol());
                    Navigator navigator = new Navigator();
                    navigator.openNewActivityWithExtras(context, new StockDetailActivity(), bundle);
                }
            });

            imgFav.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    String sym = stockList.get(getPosition()).getSymbol();
                    if (favourites.indexOf(sym) >= 0) {
                        imgFav.setImageResource(R.drawable.ic_action_fav);
                        sharedPreferenceUtils.removeFavorite(sym);
                        favourites.remove(sym);
                    } else {
                        imgFav.setImageResource(R.drawable.ic_action_name);
                        sharedPreferenceUtils.addFavorite(sym);
                        favourites.add(sym);
                        new StockObject(stockList.get(getPosition())).save(context);
                    }
                    return false;
                }
            });
        }

    }
}
