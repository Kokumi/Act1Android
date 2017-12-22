package com.debruyckere.florian.act1android.Model;

import android.os.AsyncTask;
import android.util.Log;

import org.w3c.dom.Document;
import java.io.InputStream;
import java.net.URL;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Debruyckère Florian on 12/12/2017.
 */

public class DownloadTask extends AsyncTask<URL, Void, Document> {

    interface DocumentConsumer{
        void setXMLDocument(Document mDocument);
    }
    private DocumentConsumer docConsumer ;

    public DownloadTask(DocumentConsumer consumer){docConsumer = consumer;}


    @Override
    protected Document doInBackground(URL... urls){
        Document doc = null;

        try{
            for(URL unUrl : urls){
                Log.i("TASK","Downloading this"+unUrl);
                InputStream stream = unUrl.openConnection().getInputStream();
                try{
                    Log.i("TASK","Retour doc");
                    doc= DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(stream);
                }
                catch (Exception ex){
                    Log.e("TASK","Erreur Download"+ex);
                }finally {
                    Log.i("TASK","fin du téléchargement");
                    stream.close();
                }
                if(isCancelled())break;
            }
        }catch (Exception ex){
            Log.e("CONNECTION ERROR","erreur durand le Téléchargement", ex);
            throw new RuntimeException(ex);

        }

        return doc;
    }
    @Override
    protected void onPostExecute(Document document) {
        Log.i("TASK","Post execute");
        docConsumer.setXMLDocument(document);
    }
}