package com.debruyckere.florian.act1android.Model;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.debruyckere.florian.act1android.Controller.ArticleActivity;
import com.debruyckere.florian.act1android.R;
import com.oc.rss.fake.FakeNews;
import com.oc.rss.fake.FakeNewsList;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Debruyckère Florian on 01/12/2017.
 * TODO: demander pour RecyclerView
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements DownloadResponse{

    DownloadTask DDL = new DownloadTask();
    private List<News> mNewsList;
    private List<FakeNews> mFakeNewsList = FakeNewsList.all;
    private Context mContext;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DDL.delegate=this;
        DDL.execute();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_cell,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        News mFN = mNewsList.get(position);
        //FakeNews mFN = mFakeNewsList.get(position);
        holder.display(mFN);
    }

    @Override
    public int getItemCount(){
        return mFakeNewsList.size();
    }

    @Override
    public void processFinish(List<News> result) {
        mNewsList = result;
        Collections.sort(mNewsList, new Comparator<News>() {
            @Override
            public int compare(News o1, News o2) {
                return o1.getPubDate().compareTo(o2.getPubDate());
            }
        });
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView mTitle;
        //private FakeNews mFakeNews;
        private News mNews;

        public MyViewHolder(final View itemView) {
            super(itemView);
            mContext= itemView.getContext();

            mTitle = ((TextView)itemView.findViewById(R.id.Article_Title));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext,ArticleActivity.class);
                    //intent.putExtra("ETRA_HTML",mFakeNews.htmlContent);
                    intent.putExtra("ETRA_HTML",mNews.getLink());
                    mContext.startActivity(intent);
                }
            });
        }
        /*public void display(FakeNews mFN){
            mFakeNews = mFN;
            mTitle.setText(mFN.title);
        }*/
        public void display(News mN){
            mNews = mN;
            mTitle.setText(mN.getTitle());
        }
    }
}
