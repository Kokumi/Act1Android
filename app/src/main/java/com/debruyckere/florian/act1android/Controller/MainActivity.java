package com.debruyckere.florian.act1android.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.debruyckere.florian.act1android.Model.DownloadResponse;
import com.debruyckere.florian.act1android.Model.DownloadTask;
import com.debruyckere.florian.act1android.Model.MyAdapter;
import com.debruyckere.florian.act1android.R;
import com.oc.rss.fake.FakeNews;
import com.oc.rss.fake.FakeNewsList;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

public class MainActivity extends AppCompatActivity{

    private List<FakeNews> mFakeNews= FakeNewsList.all;
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter = new MyAdapter();
    List<URL> mURLList = new ArrayList<>();
    DownloadTask dTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView=findViewById(R.id.ListViews);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        try{
            mURLList.add(new URL("http://www.lemonde.fr/jeux-video/rss_full.xml"));
            mURLList.add(new URL("http://www.lemonde.fr/m-actu/rss_full.xml"));
            mURLList.add(new URL("http://www.lemonde.fr/technologies/rss_full.xml"));
        }catch (MalformedURLException e){
            Log.i("BUG","ERREUR DANS LES URLS");
        }

        dTask = new DownloadTask(mAdapter);
        dTask.execute(mURLList.get(0), mURLList.get(1),mURLList.get(2));

    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(dTask != null){
            dTask.cancel(true);
        }
    }

}
