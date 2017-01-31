package com.nishitadutta.investomaster.news;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.nishitadutta.investomaster.R;
import com.nishitadutta.investomaster.widgets.DividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class NewsFragment extends Fragment {
    private static final String TAG = "NewsFragment";
    private static final String ARTICLES="articles";
    private static final String TITLE="title";
    private static final String DESCRIPTION="description";
    private static final String URLTOIMAGE="urlToImage";
    private static final String URL="url";
    ArrayList<News> arrayListNews;
    OkHttpClient client;
    Request request;
    LinearLayoutManager linearLayoutManager;
    @BindView(R.id.rvNews)
    RecyclerView newsRecyclerView;
    @BindView(R.id.progressn)
    ProgressBar progressBar;
    RecyclerAdapterNews recyclerAdapterNews;


    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void parseJSON(String string) {
        JSONObject jsonObject;
        JSONArray articles;

        try {
            jsonObject = new JSONObject(string);
        }catch (JSONException e){
            Log.e(TAG, "parseJSON: malformed request" );
            return;
        }

        try {
            articles=jsonObject.getJSONArray(ARTICLES);

        }catch(JSONException e){
            Log.e(TAG, "parseJSON: malformed request" );
            return;
        }

        for (int i=0; i < articles.length(); i++)
        {

            try {
                JSONObject oneObject = articles.getJSONObject(i);
                // Pulling items from the array
                String title = oneObject.getString(TITLE);
                String desc = oneObject.getString(DESCRIPTION);
                String url=oneObject.getString(URLTOIMAGE);
                String url_d=oneObject.getString(URL);
                News news=new News(title,desc,url,url_d);
                Log.e(TAG, "parseJSON: "+ title );
                arrayListNews.add(news);
                setAdapter();
            } catch (JSONException e) {
                // Oops
                return;
            }
        }

    }

    private void setAdapter() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                linearLayoutManager=new LinearLayoutManager(getContext());
                Log.e(TAG, "setAdapter: " );
                recyclerAdapterNews=new RecyclerAdapterNews(getContext(), arrayListNews);
                newsRecyclerView.setLayoutManager(linearLayoutManager);
                newsRecyclerView.setAdapter(recyclerAdapterNews);
                newsRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), null));

            }
        });
         }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, rootView);
        client = new OkHttpClient();
        arrayListNews=new ArrayList<>();
        Request request = new Request.Builder()
                .url("https://newsapi.org/v1/articles?source=financial-times&sortBy=top&apiKey=808166918a134aa2b2ade4591a567529")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onResponse: " + "failure" );
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Log.e(TAG, "onResponse: " + "failure" );
                    // throw new IOException("Unexpected code " + response);
                } else {
                    // Log.e(TAG, "onResponse: " + response.body().string());
                    parseJSON(response.body().string());
                }
            }

        });
        return rootView;
    }
        /*String run (String url)throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response;
            try {
                response = okHttpClient.newCall(request).execute();
            } catch (IOException e) {
                return "Nothing found";
            }
            return response.body().string();
        }*/
}


