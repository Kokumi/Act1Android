package com.debruyckere.florian.act1android.Model;

import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;

import com.debruyckere.florian.act1android.Controller.MainActivity;
import com.oc.rss.fake.FakeNews;
import com.oc.rss.fake.FakeNewsList;

import org.w3c.dom.Document;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Debruyckère Florian on 12/12/2017.
 */

public class DownloadTask extends AsyncTask<URL, Void, List<News>> {
    public DownloadResponse delegate =null;

    private URL mURL;
    private MyAdapter mAdapter;

    /**
     * constructeur pour reprendre l'adapter
     * @param pAda
     *          MyAdapter déclarant cette class
     */
    public DownloadTask(MyAdapter pAda){
        this.mAdapter = pAda;
    }
    @Override
    protected List<News> doInBackground(URL... urls) {

        List<News> lNews = new ArrayList<>();
        for(URL unUrl : urls){
            Log.i("ETAT","Thread de URL" + unUrl.toString());
            try {   //Recup des news
                InputStream stream = unUrl.openConnection().getInputStream();
                Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(stream);
                InputStream mIs = unUrl.openConnection().getInputStream();
                lNews = Xmlparser(doc,mIs);
            }catch (Exception e){
                Log.i("CONNECTION ERROR","Erreur de la connexion ", e);
                this.cancel(true);
            }
        }

        return lNews;
    }

    @Override
    protected void onPostExecute(List<News> pNews){
        Log.i("POST","Process finish called");
        mAdapter.processFinish(pNews);
    }

    /**
     * convertie un document XML en list de News
     * @param pDoc
     * @return liste de news
     */
    private List<News> Xmlparser(Document pDoc , InputStream pIs){
        List<News> lNews=new ArrayList<News>();
        Log.i("XML","Parse XML");
        boolean isNews = false;

        try {           //traduction du XML en objet News
            XmlPullParser mParser = XmlPullParserFactory.newInstance().newPullParser();
            mParser.setInput(pIs,null);

            int event = mParser.getEventType();
            while (event!=XmlPullParser.END_DOCUMENT){
                News newNews = new News();
                Log.i("XML", "Event: "+event);

                String parserName;

                if(mParser.getName() == null) continue;
                else parserName = mParser.getName();

                if(event == XmlPullParser.START_TAG){
                    if(parserName.equalsIgnoreCase("item")) isNews = true;
                    continue;
                }

                if(event == XmlPullParser.END_TAG) {
                    if(parserName.equalsIgnoreCase("item")) isNews =false;
                    continue;
                }

                String result ="";
                if(mParser.next() == XmlPullParser.TEXT){
                    result = mParser.getText();
                    mParser.nextTag();
                }

                    Log.i("XML","traitement article");

                    switch (parserName){
                        case "title":
                            //newNews.setTitle(mParser.getAttributeValue("item","title"));
                            newNews.setTitle(result);
                            break;
                        case "link":
                            try {
                                //newNews.setLink(new URL(mParser.getAttributeValue("item", "link")));
                                newNews.setLink(new URL(result));
                            }catch (MalformedURLException e){
                            }
                            break;
                        case "description":
                            //newNews.setDescription(mParser.getAttributeValue("item" , "link"));
                            newNews.setDescription(result);
                            break;
                        case "pubDate":
                            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                            try {
                                //newNews.setPubDate(format.parse(mParser.getAttributeValue("item", "pubDate")));
                                newNews.setPubDate(format.parse(result));
                            }catch (ParseException e){
                                newNews.setPubDate(new Date());
                            }
                            break;
                            default:
                                skip(mParser);
                                break;
                    }
                    Log.i("XML","traitement de l'article "+newNews.getTitle());
                    lNews.add(newNews);
                }


            Log.i("Taille news","Taille: "+lNews.size());
        }catch (XmlPullParserException e){
            Log.i("XMLERROR","Erreur dans le parsing des XML", e);
            this.cancel(true);
        }catch (IOException e){
            Log.i("XMLERROR", ""+e);
        }

        return lNews;
    }
    private void skip(XmlPullParser pPerser) throws XmlPullParserException,IOException{
        Log.i("SKIP","dans le Skip");
        int depth = 1;
        //if(pPerser.getEventType()!= XmlPullParser.START_TAG) throw new IllegalStateException();

        while(depth !=0){
            Log.i("DEPTH",""+depth);
            switch(pPerser.next()){
                case XmlPullParser.END_TAG: depth--;
                break;
                case XmlPullParser.START_TAG: depth++;
                break;
            }
        }
    }
}
// tag = title, link, description, pubDate