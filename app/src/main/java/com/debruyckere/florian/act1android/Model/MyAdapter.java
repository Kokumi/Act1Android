package com.debruyckere.florian.act1android.Model;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.debruyckere.florian.act1android.Controller.ArticleActivity;
import com.debruyckere.florian.act1android.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Created by Debruyckère Florian on 01/12/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>
        implements DownloadTask.DocumentConsumer{

    private Context mContext;
    private Document mDocument = null;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_cell,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Element item =(Element)mDocument.getElementsByTagName("item").item(position);
        holder.setElement(item);
    }

    @Override
    public void setXMLDocument(Document document){
        Log.i("SETTER","SET DOCUMENT");
        mDocument = document;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mDocument != null){
            Log.i("COUNT",""+mDocument.getElementsByTagName("item").getLength());
            return mDocument.getElementsByTagName("item").getLength();
        }else{
            Log.i("COUNT","Document=null");
            return 0;
        }
    }




    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView mTitle;
        private Element mElement;


        public MyViewHolder(final View itemView){
            super(itemView);
            mTitle = itemView.findViewById(R.id.Article_Title);
            mContext = itemView.getContext();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext,ArticleActivity.class);
                    intent.putExtra("ETRA_HTML",mElement.getElementsByTagName("link").item(0).getTextContent());
                    mContext.startActivity(intent);
                }
            });
        }

        public void setElement(Element element){
            Log.i("RECYCLER","affichage des titres");
            mElement = element;
            mTitle.setText(element.getElementsByTagName("title").item(0).getTextContent());
        }
    }
}
