package com.nishitadutta.investomaster.news;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nishitadutta.investomaster.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nishita on 08-01-2017.
 */

public class RecyclerAdapterNews extends
        RecyclerView.Adapter<RecyclerAdapterNews.NewsViewHolder> {

    private static final String TAG = "RecyclerAdapterNews";
    Context context;
    private ArrayList<News> newsList;

    {
    }


    public RecyclerAdapterNews(Context context, ArrayList<News> stockList) {
        this.context = context;
        this.newsList = stockList;

    }

    @Override
    public RecyclerAdapterNews.NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //creates a RecyclerViewHolderList that holds a view of R.layout.recycler_list_item type
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView;
        itemView = inflater.inflate(R.layout.news_list_item, parent, false);
        return new RecyclerAdapterNews.NewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapterNews.NewsViewHolder holder, int position) {
        holder.tvDesc.setText(newsList.get(position).getDesc());
        holder.tvTitle.setText(newsList.get(position).getTitle());
        Picasso.with(context)
                .load(newsList.get(position).getImg_url())
                .placeholder(R.drawable.ic_action_placeholder)
                .error(R.drawable.ic_action_erorr)
                .resize(150,200)
                .centerCrop()
                .into(holder.imgNews);
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {
        public
        @BindView(R.id.tv_desc)
        TextView tvDesc;

        public
        @BindView(R.id.tv_title)
        TextView tvTitle;
        public
        @BindView(R.id.img_news)
        ImageView imgNews;
        public NewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(newsList.get(getPosition()).getUrl()));
                    context.startActivity(browserIntent);
                }
            });

        }

    }
}
