package com.debruyckere.florian.act1android.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.debruyckere.florian.act1android.Model.DownloadTask;
import com.debruyckere.florian.act1android.Model.MyAdapter;
import com.debruyckere.florian.act1android.R;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity{

    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter = new MyAdapter();
    List<URL> mURLList = new ArrayList<>();
    DownloadTask dTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("news Feeder");

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
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_refresh:
                dTask.cancel(true);
                new DownloadTask(mAdapter).execute(mURLList.get(0), mURLList.get(1),mURLList.get(2));
                Log.i("MENU","Refresh");
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
