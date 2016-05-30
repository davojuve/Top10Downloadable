package am.wedo.top10downloader;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private class DownloadData extends AsyncTask<String, Void, String>{

        private final String TAG = DownloadData.class.getSimpleName();

        private String mFileContents;

        @Override
        protected String doInBackground(String... params) {
            mFileContents = downloadXMLFile(params[0]);

            if( null == mFileContents ){
                Log.d(TAG, "Error downloading");
            }

            return mFileContents;
        }

        private String downloadXMLFile(String urlPath) {
            StringBuilder tempBuffer = new StringBuilder();

            try{
                URL url = new URL(urlPath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                int responseCode = connection.getResponseCode();
                Log.d(TAG, "The response code was: " + responseCode);

                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);

                int charRead;
                char[] inputBuffer = new char[500]; // 500 bites at a time
                while (true){
                    charRead = isr.read(inputBuffer); // number of characters read
                    if(charRead <= 0){
                        break;
                    }
                    tempBuffer.append( String.copyValueOf(inputBuffer, 0, charRead) );
                }

                return tempBuffer.toString();

            }catch (IOException e){
                Log.e(TAG, "Error downloading data: " + e.getMessage());
            }

            return null;
        }
    }
}
