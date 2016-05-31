package am.wedo.top10downloader;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Davit: class for parsing each application from XML.
 */
public class ParseApplications {

    private final String TAG = ParseApplications.class.getSimpleName();

    private String xmlData;
    private List<Application> applications;

    public List<Application> getApplications() {
        return applications;
    }

    public ParseApplications(String xmlData) {
        this.xmlData = xmlData;
        applications = new ArrayList<>();

    }


    public boolean process() {
        boolean status = true;
        Application currentRecord = null;
        boolean inEntry = false;
        String textValue = "";

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(xmlData));
            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT){
                String tagName = xpp.getName();
                switch (eventType){
                    case XmlPullParser.START_TAG:
//                        Log.d(TAG, "Starting tag: " + tagName);
                        if ( tagName.equalsIgnoreCase("entry")){
                            inEntry = true;
                            currentRecord = new Application();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        textValue = xpp.getText();
                        break;

                    case XmlPullParser.END_TAG:
//                        Log.d(TAG, "Ending tag: " + tagName);
                        if(inEntry){
                            if(tagName.equalsIgnoreCase("entry")){
                                applications.add(currentRecord);
                                inEntry = false;
                            }else if(tagName.equalsIgnoreCase("name")){
                                currentRecord.setName(textValue);
                            }else if(tagName.equalsIgnoreCase("artist")){
                                currentRecord.setArtist(textValue);
                            }else if(tagName.equalsIgnoreCase("releaseDate")){
                                currentRecord.setReleaseDate(textValue);
                            }
                        }
                        break;
                    default:
                        // nothing else to do
                }
                eventType = xpp.next();
            }

        } catch (Exception e) {
            status = false;
            e.printStackTrace();
        }

        for (Application app : applications){
            Log.d(TAG, "*****************");
            Log.d(TAG, "Name: " + app.getName());
            Log.d(TAG, "Artist: " + app.getArtist());
            Log.d(TAG, "Release Date: " + app.getReleaseDate());
        }
        return true;
    }
}
