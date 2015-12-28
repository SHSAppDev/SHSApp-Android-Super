package appdev.shs.shsapp3;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;

public class ViewGradesFragment extends Fragment {

    private View view;
    private WebView gradesWebView;

    public ViewGradesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_view_grades, container, false);

        gradesWebView = (WebView)view.findViewById(R.id.grades_web_view);

        new Thread(new Runnable() {
            @Override
            public void run() {
                String html = getHtmlFromUrl("https://lgsuhsd.instructure.com/grades");
                gradesWebView.loadData(html, "text/html", null);


            }
        }).start();

        // Inflate the layout for this fragment
        return view;
    }


    public String getHtmlFromUrl(String url) {
        String html = "Empty html";

        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);

            InputStream in = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder str = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                str.append(line);
            }
            in.close();
            html = str.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return html;


        /*String xml = "";

        try {

            //URL url1 = new URL(url);
            URL url1 = new URL(url);

            HttpURLConnection urlConn = (HttpURLConnection) url1
                    .openConnection();

            urlConn.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 6.1;zh-tw; MSIE 6.0)");
            if (Integer.parseInt(Build.VERSION.SDK) < Build.VERSION_CODES.FROYO) {
                System.setProperty("http.keepAlive", "false");
            }*/
        //urlConn.setReadTimeout(10000 /* milliseconds */);
        //urlConn.setConnectTimeout(15000 /* milliseconds */);
            /*urlConn.setDoOutput(true);
            urlConn.setDoInput(true);
            urlConn.setRequestMethod("GET");
            urlConn.setUseCaches(false);


            InputStreamReader in = new InputStreamReader(
                    urlConn.getInputStream());
            BufferedReader buffer = new BufferedReader(in, 100000);

            StringBuilder builder = new StringBuilder();
            String aux = "";


            while ((aux = buffer.readLine()) != null)
                builder.append(aux);

            xml = builder.toString();

            in.close();
            urlConn.disconnect();

        } catch (SocketTimeoutException e) {
            return "time out";
        } catch (IOException e) {
            e.printStackTrace();
        }
        // return XML
        return xml;


    }*/
    }
}
