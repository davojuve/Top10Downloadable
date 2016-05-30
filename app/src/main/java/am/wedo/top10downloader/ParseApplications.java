package am.wedo.top10downloader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Davit: class for parsing each application from XML.
 */
public class ParseApplications {
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
}
