package com.example.ckraft.myapplication.Controller.WebRequests;

import android.os.AsyncTask;

import com.example.ckraft.myapplication.Controller.WebRequests.BaseServiceSettings;
import com.example.ckraft.myapplication.Model.Response;
import com.example.ckraft.myapplication.View.AppContext;

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
import java.util.HashMap;

/**
 * Created by ckraft on 8/25/2016.
 */

public class UniversalRequest extends AsyncTask<String, String, Response> {

    // This is the reference to the associated listener
    private final TaskListener taskListener;
    private final BaseServiceSettings settings;


    public interface TaskListener {
        public void onStarted();
        public void onSuccess(Response response);
        public void onFailure(Response response);
    }



    public UniversalRequest(BaseServiceSettings settings, TaskListener listener) {
        // The listener reference is passed in through the constructor
        this.taskListener = listener;
        this.settings = settings;
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
    protected Response doInBackground(String... params){

        Response response = new Response();
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(settings.getAction());

            connection = (HttpURLConnection) url.openConnection();

            //CHECKS SETTINGS METHOD
            String method = settings.getMethod();
            if (method.equalsIgnoreCase("post")){
                connection.setConnectTimeout(5000);
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setRequestMethod("POST");
            }

            connection = AppContext.applyCookie(connection);

            //CHECKS SETTINGS HEADERS
            HashMap<String, String> headers = settings.getHeaders();
            if (!headers.isEmpty()){

                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                OutputStream os = connection.getOutputStream();

                String headersString = "";
                for (int i = 0 ; i < headers.size() ; i+=1){

                    if(i != 0) headersString += "&";
                    String headerKey = (String) headers.keySet().toArray()[i];
                    headersString += headerKey + "=" + headers.get(headerKey);
                }
                os.write((headersString).getBytes("UTF-8"));
                os.close();
            }

            //CHECKS SETTINGS BODY
            String body = settings.getBody();
            if (body != null){

                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

                OutputStream os = connection.getOutputStream();
                os.write(body.getBytes("UTF-8"));
                os.close();
            }

            connection.connect();


            InputStream in = new BufferedInputStream(connection.getInputStream());
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            String finalJson = buffer.toString();

            try{
                JSONObject parentObject = new JSONObject(finalJson);
                response.HyrdateFromJson(parentObject);
            }catch (JSONException e){
                JSONArray parentArray = new JSONArray(finalJson);
                response.setData(parentArray);
                response.setSuccess(true);
            }



            in.close();
            connection.disconnect();
            //ADD TO OTHERS



        } catch (MalformedURLException e) {
            response.setSuccess(false);
            response.getErrors().add(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            response.setSuccess(false);
            response.getErrors().add(e.getMessage());
            e.printStackTrace();
        } catch (JSONException e) {
            response.setSuccess(false);
            response.getErrors().add(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            response.setSuccess(false);
            response.getErrors().add(e.getMessage());
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
    protected void onPostExecute(final Response response) {
        super.onPostExecute(response);
        // In onPostExecute we check if the listener is valid
        if(this.taskListener != null) {

            if (response.getSuccess()){
                // And if it is we call the callback function on it.
                this.taskListener.onSuccess(response);
            }
            else{
                this.taskListener.onFailure(response);
            }
        }
    }
}