package com.debruyckere.florian.act1android.Model;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.debruyckere.florian.act1android.Controller.ArticleActivity;
import com.debruyckere.florian.act1android.R;
import com.oc.rss.fake.FakeNews;
import com.oc.rss.fake.FakeNewsList;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Debruyckère Florian on 01/12/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements DownloadResponse{

    DownloadTask DDL = new DownloadTask(this);
    private List<News> mNewsList;
    private List<FakeNews> mFakeNewsList = FakeNewsList.all;
    private Context mContext;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DDL.delegate=this;
        //Verifie si la tâche est en cours
        if(DDL.getStatus() != AsyncTask.Status.RUNNING){
            DDL.execute(URLListing().get(0), URLListing().get(1),URLListing().get(2));
        }

        Log.i("INFO LANCEMENT","thread DDL lancé");
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_cell,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        try {
            News mFN = mNewsList.get(position);
            //FakeNews mFN = mFakeNewsList.get(position);
            holder.display(mFN);
        }catch (Exception e){
            News ErreurNews = new News();
            ErreurNews.setTitle("Error");
            Destroy();
        }
    }

    @Override
    public int getItemCount(){
        return mFakeNewsList.size();
    }

    private List<URL> URLListing(){
        List<URL> mURLList = new ArrayList<>();
        try{
            mURLList.add(new URL("http://www.lemonde.fr/jeux-video/rss_full.xml"));
            mURLList.add(new URL("http://www.lemonde.fr/m-actu/rss_full.xml"));
            mURLList.add(new URL("http://www.lemonde.fr/technologies/rss_full.xml"));
        }catch (MalformedURLException e){
            Log.i("BUG","ERREUR DANS LES URLS");
        }

        return mURLList;
    }

    @Override
    public void processFinish(List<News> result) {      //récupération des résultat de DownloadTask
        mNewsList = result;
        //trie des news selon leur récenteté
        Collections.sort(mNewsList, new Comparator<News>() {
            @Override
            public int compare(News o1, News o2) {
                return o1.getPubDate().compareTo(o2.getPubDate());
            }
        });
    }

    /**
     * Stop l'asyncTask DownloadTask
     */
    public void Destroy(){
        DDL.cancel(true);           //Stop la tâche DownloadTask
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
