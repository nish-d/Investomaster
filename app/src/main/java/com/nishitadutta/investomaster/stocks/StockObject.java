package com.nishitadutta.investomaster.stocks;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import com.nishitadutta.investomaster.R;
import com.nishitadutta.investomaster.utils.SharedPreferenceUtils;

import java.io.Serializable;
import java.math.BigDecimal;

import yahoofinance.Stock;

/**
 * Created by Nishita on 07-01-2017.
 */

public class StockObject implements Serializable{
    Stock stock;
    public StockObject(Stock stock){
        this.stock=stock;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public void save(Context context) {
        SharedPreferenceUtils sharedPreferenceUtils= SharedPreferenceUtils.getInstance(context);

    }
}
