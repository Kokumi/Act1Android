package com.debruyckere.florian.act1android.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.debruyckere.florian.act1android.Model.DownloadResponse;
import com.debruyckere.florian.act1android.Model.MyAdapter;
import com.debruyckere.florian.act1android.R;
import com.oc.rss.fake.FakeNews;
import com.oc.rss.fake.FakeNewsList;
import java.util.List;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity{

    private List<FakeNews> mFakeNews= FakeNewsList.all;
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter = new MyAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView=findViewById(R.id.ListViews);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        mAdapter.Destroy();         //Stop la tâche DownloadTask
    }

}
