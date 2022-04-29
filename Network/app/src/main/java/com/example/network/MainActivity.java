package com.example.network;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {

    TextView text1;
    int data_type = 1;
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text1 = (TextView) findViewById(R.id.text1);
    }

    class NetworkThread extends Thread {
        @Override
        public void run() {
            try{
                String site = "http://172.20.10.9:8080/AndroidDataTransaction";

                if(data_type == 1) {
                    site += "/home";
                } else {
                    site += "/json-data";
                }

                URL url = new URL(site);
                URLConnection conn = url.openConnection();

                InputStream is = conn.getInputStream();
                InputStreamReader isr = new InputStreamReader(is, "UTF-8");
                BufferedReader br = new BufferedReader(isr);

                String str = null;
                StringBuffer buf = new StringBuffer();
                do {
                    str = br.readLine();
                    if(str != null) {
                        buf.append(str);
                    }
                } while(str != null);

                String data = buf.toString();

                if(data_type == 1) {
                    result = "JSP Page\n\n";
                    result += data;
                } else {
                    result = "JSON Data\n\n";
                    result += readJSON(data);
                    result += data;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        text1.setText(result);
                    }
                });

            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String readJSON(String data) {
        StringBuffer result = new StringBuffer();
        try{
            JSONArray root = new JSONArray(data);
            for(int i=0; i<root.length(); i++) {
                JSONObject obj = root.getJSONObject(i);
                final String name = obj.getString("name");
                final int price = obj.getInt("price");

                result.append("name : " + name + "\n");
                result.append("price : " + price + "\n\n");
            }
            Log.d("huan", result.toString());
        } catch(Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    class XMLNetworkThread extends Thread {
        @Override
        public void run() {
            try{
                String site = "http://172.20.10.9:8080/AndroidDataTransaction/xml-data";
                URL url = new URL(site);
                URLConnection conn = url.openConnection();

                InputStream is = conn.getInputStream();
                // DOM 방식으로 XML 문서를 분석할 수 있는 파서 객체를 생성한다
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(is);

                Element data_tag = doc.getDocumentElement();
                NodeList item_tags = data_tag.getElementsByTagName("item");

                //text1.setText("XML Data\n\n");
                for(int i=0; i < item_tags.getLength(); i++) {
                    Element item_tag = (Element) item_tags.item(i);

                    NodeList name_list = item_tag.getElementsByTagName("name");
                    NodeList price_list = item_tag.getElementsByTagName("price");

                    Element name_tag = (Element) name_list.item(0);
                    Element price_tag = (Element) price_list.item(0);

                    String name1 = name_tag.getTextContent();
                    String price_str = price_tag.getTextContent();
                    int price1 = Integer.parseInt(price_str);

                    int finalI = i;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(finalI == 0) {
                                text1.setText("XML Data\n\n");
                            }
                            text1.append("name : " + name1 + "\n");
                            text1.append("price : " + price1 + "\n\n");
                        }
                    });
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void networkButton(View view) {
        data_type = 1;
        NetworkThread thread = new NetworkThread();
        thread.start();
    }

    public void jsonButton(View view) {
        data_type = 2;
        NetworkThread thread = new NetworkThread();
        thread.start();
    }

    public void xmlButton(View view) {
        XMLNetworkThread thread = new XMLNetworkThread();
        thread.start();
    }


}