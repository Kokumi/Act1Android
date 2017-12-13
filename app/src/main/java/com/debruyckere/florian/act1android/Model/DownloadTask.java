package com.debruyckere.florian.act1android.Model;

import android.os.AsyncTask;
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
    @Override
    protected List<News> doInBackground(URL... urls) {
        List<News> lNews = new ArrayList<>();
        for(URL unUrl : urls){
            try {
                InputStream stream = unUrl.openConnection().getInputStream();
                Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(stream);
                lNews = Xmlparser(doc);
            }catch (Exception e){

            }
        }

        return lNews;
    }

    @Override
    protected void onPostExecute(List<News> pNews){

    }
    private List<News> Xmlparser(Document pDoc){
        List<News> lNews=new ArrayList<News>();

        try {
            XmlPullParser mParser = XmlPullParserFactory.newInstance().newPullParser();

            int event = mParser.getEventType();
            while (event!=XmlPullParser.END_DOCUMENT){
                News newNews = new News();
                if(event == XmlPullParser.END_TAG){
                    switch (mParser.getName()){
                        case "title":
                            newNews.setTitle(mParser.getAttributeValue("item","title"));
                            break;
                        case "link":
                            try {
                                newNews.setLink(new URL(mParser.getAttributeValue("item", "link")));
                            }catch (MalformedURLException e){
                            }
                            break;
                        case "description":
                            newNews.setDescription(mParser.getAttributeValue("item" , "link"));
                            break;
                        case "pubDate":
                            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                            try {
                                newNews.setPubDate(format.parse(mParser.getAttributeValue("item", "pubDate")));
                            }catch (ParseException e){
                                newNews.setPubDate(new Date());
                            }
                            break;
                    }
                    lNews.add(newNews);
                }

            }
        }catch (XmlPullParserException e){

        }

        return lNews;
    }
}
// tag = title, link, description, pubDate