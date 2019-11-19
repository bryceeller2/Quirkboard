package com.example.ckraft.myapplication;

import android.os.AsyncTask;
import android.widget.Toast;

import com.example.ckraft.myapplication.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ckraft on 8/25/2016.
 */

public class GetRequestTask extends AsyncTask<String, String, String> {

    public interface TaskListener {
        public void onStarted();
        public void onFinished(String response);
    }

    // This is the reference to the associated listener
    private final TaskListener taskListener;

    public GetRequestTask(TaskListener listener) {
        // The listener reference is passed in through the constructor
        this.taskListener = listener;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();

        // In onPreExecute we check if the listener is valid
        if(this.taskListener != null) {

            // And if it is we call the callback function on it.
            this.taskListener.onStarted();
        }
    }
    @Override
    protected String doInBackground(String... params){

        String response = new String();
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();

            AppContext.applyCookie(connection);

            connection.connect();
            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();
            String line ="";
            while ((line = reader.readLine()) != null){
                buffer.append(line);
            }

            String finalJson = buffer.toString();

            stream.close();
            connection.disconnect();

            response = finalJson;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(connection != null) {
                connection.disconnect();
            }
            try {
                if(reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return response;
    }
    @Override
    protected void onPostExecute(final String response) {
        super.onPostExecute(response);

        // In onPostExecute we check if the listener is valid
        if(this.taskListener != null) {

            // And if it is we call the callback function on it.
            this.taskListener.onFinished(response);
        }
    }
}