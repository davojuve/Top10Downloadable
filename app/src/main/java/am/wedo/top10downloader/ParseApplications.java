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
    private List<Applicaton> applicatons;

    public ParseApplications(String xmlData) {
        this.xmlData = xmlData;
        applicatons = new ArrayList<>();

    }

    // getter for applications
    public List<Applicaton> getApplicatons() {
        return applicatons;
    }

    public boolean process() {
        boolean status = true;
        Applicaton currentRecord;
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
                        Log.d(TAG, "Starting tag: " + tagName);
                        if ( tagName.equalsIgnoreCase("entry")){
                            inEntry = true;
                            currentRecord = new Applicaton();
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        Log.d(TAG, "Ending tag: " + tagName);
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
        return true;
    }
}
