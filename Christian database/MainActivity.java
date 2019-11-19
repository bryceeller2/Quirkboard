package com.example.ckraft.myapplication;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {


    //CHOCOLATE CHIP MUFFIN

    TextView hello;
    ImageButton homebtn;
    ImageButton postbtn;
    ImageButton settings;
    double FRICTION=1;
    double BACKGROUNDSPEED=1;

    float xspeed=0, yspeed=0, startX=0, startY=0;
    boolean notTouching = true;

    ArrayList<ImageView> posts = new ArrayList<ImageView>();
    ArrayList<TextView> postTexts = new ArrayList<TextView>();

    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) { //This runs first, automatically

        GPS a = new GPS("ASDF");
        a.Fun();

        /*
        // Get a handler that can be used to post to the main thread
        Handler mainHandler = new Handler(Looper.getMainLooper());

        Runnable myRunnable = new Runnable()
        {
            @Override
            public void run() {System.out.println("I Am Running");} // This is your code
        };
        mainHandler.post(myRunnable);
        */

        new Thread() {
            public void run() {
                while (true) {
                    try {
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                if (notTouching)
                                    movePosts(0, 0);
                            }
                        });
                        Thread.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();


        super.onCreate(savedInstanceState);
        //Title off
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //this would set the view to be the xml
        setContentView(R.layout.activity_main);

//BUTTONS
        homebtn = (ImageButton) findViewById(R.id.homie);
        settings = (ImageButton) findViewById(R.id.settingbtn);
        hello = (TextView) findViewById(R.id.hello);
        postbtn = (ImageButton) findViewById(R.id.postbtn);


        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Get Android ID
                String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                        Settings.Secure.ANDROID_ID);

                //store "android_id" to database

                hello.setText("Hello " + android_id + "!");
                System.out.println("hello world");
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                String url = "http://quirkboard.net/android/addPost.php";
//
//
//                JSONObject json = new JSONObject();
//                try {
//                    json.put("postid", "12");
//                    json.put("userid", "bob");
//                    json.put("text", "this is a post. look at my post");
//                    json.put("latitude", "1");
//                    json.put("longitude", "1");
//                    json.put("stickiness", "45");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
                getPosts();
            }
        });

        postbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hello.setVisibility(View.GONE);
                startActivityForResult(new Intent(MainActivity.this, Pop.class), 0);
            }

        });
    }


    public void getPosts(){
        String URL = AppContext.getBASE_URL() + "/android/getPosts.php";

        new GetRequestTask(new GetRequestTask.TaskListener() {

            @Override
            public void onStarted() {
            }
            @Override
            public void onFinished(String response) {


                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();


            }
        }).execute(URL);
    }


































    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode)
        {
            case (0) :
            {
                if (resultCode == Activity.RESULT_OK)
                {
                    Bundle extras = data.getExtras();
                    System.out.println("This is the data: " + data);

                    String userText = extras.getString("userText");

                    addPost(userText);

                    hello.setText(userText);
                }
                break;
            }
        }
    }

    public void addPost(String textToAdd)
    {
        ImageView stickyImage = new ImageView(this);
        TextView postText = new TextView(this);

        RelativeLayout myLayout = (RelativeLayout) findViewById(R.id.mainboard);

        myLayout.addView(stickyImage);
        myLayout.addView(postText);
        postText.setText(textToAdd);
        postText.setTextColor(Color.BLACK);

        BmpProcess processor = new BmpProcess();
        //TODO set image scale relative to screen size
        stickyImage.setImageBitmap(processor.autoScale(getResources(), R.drawable.sticky, 600, 600));

        //Get screen size
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        //posttext.setPadding(200,400,0,0);
        postText.setX(width/2-textToAdd.length());
        postText.setY(height / 2 - 10);
        stickyImage.setX(width / 2 - 400);
        stickyImage.setY(height / 2 - 300);

        posts.add(stickyImage);
        postTexts.add(postText);

    }


    public boolean dispatchTouchEvent(MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            notTouching=false;
            xspeed = 0;
            yspeed = 0;

            startX = event.getX();
            startY = event.getY();

            return super.dispatchTouchEvent(event);
        }

        if(event.getAction() == MotionEvent.ACTION_MOVE)
        {
            xspeed = (float)((startX-event.getX()) * -BACKGROUNDSPEED);
            yspeed = (float)((startY-event.getY()) * -BACKGROUNDSPEED);
            startX = event.getX();
            startY = event.getY();

            movePosts(xspeed, yspeed);

            return super.dispatchTouchEvent(event);
        }

        if(event.getAction() == MotionEvent.ACTION_UP)
        {
            notTouching = true;
            return super.dispatchTouchEvent(event);
        }

        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void movePosts(float xChange, float yChange)
    {
        if (!posts.isEmpty() && !postTexts.isEmpty())
        {
            if(notTouching)
                slowDown(FRICTION);

            for (ImageView pic : posts)
            {
                pic.setX(pic.getX()+xspeed);
                pic.setY(pic.getY()+yspeed);
            }

            for (TextView txt : postTexts)
            {
                txt.setX(txt.getX()+xspeed);
                txt.setY(txt.getY()+yspeed);
            }
        }
    }

    public void slowDown(double FRICTION)
    {

        if (Math.abs(xspeed)-FRICTION <0)
            xspeed=0;
        if (Math.abs(yspeed)-FRICTION <0)
            yspeed=0;
        if (Math.abs(xspeed) > 0 )
        {
            if (xspeed>0)
                xspeed-=FRICTION;
            else if (xspeed<0)
                xspeed+=FRICTION;
        }
        if (Math.abs(yspeed) > 0 )
        {
            if (yspeed>0)
                yspeed-=FRICTION;
            else if (yspeed<0)
                yspeed+=FRICTION;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public class GetUser extends AsyncTask<String, String, JSONObject> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }
        @Override
        protected JSONObject doInBackground(String... params){

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();

                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line ="";
                while ((line = reader.readLine()) != null){
                    buffer.append(line);
                }

                String finalJson = buffer.toString();

                JSONObject finalObject = new JSONObject(finalJson);

                stream.close();
                connection.disconnect();


                Gson gson = new Gson();

                return finalObject;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
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
            return null;
        }
        @Override
        protected void onPostExecute(final JSONObject response) {
            super.onPostExecute(response);

            int locationID;
            if(response != null){

                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();

            }
            else {

            }
        }
    }


    public class GetPosts extends AsyncTask<String, String, JSONObject> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }
        @Override
        protected JSONObject doInBackground(String... params){

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();

                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line ="";
                while ((line = reader.readLine()) != null){
                    buffer.append(line);
                }

                String finalJson = buffer.toString();

                JSONObject finalObject = new JSONObject(finalJson);

                stream.close();
                connection.disconnect();


                Gson gson = new Gson();

                return finalObject;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
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
            return null;
        }
        @Override
        protected void onPostExecute(final JSONObject response) {
            super.onPostExecute(response);

            int locationID;
            if(response != null){

                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();

            }
            else {

            }
        }
    }

    public class AddUser extends AsyncTask<String, String, JSONObject> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;




            try {
                URL url = new URL(params[0]);

                String json = params[1];

                JSONObject jsonObject = new JSONObject(json);
                HashMap<String, String> postDataParams = new HashMap<>();

                Iterator<?> keys = jsonObject.keys();

                while( keys.hasNext() ){
                    String key = (String)keys.next();
                    String value = jsonObject.getString(key);
                    postDataParams.put(key, value);

                }

                connection = (HttpURLConnection) url.openConnection();


                connection.setReadTimeout(15000);
                connection.setConnectTimeout(15000);
                //connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setRequestMethod("POST");


                OutputStream os = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();
                int responseCode=connection.getResponseCode();

                String endString = "";

                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    String line;
                    BufferedReader br=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    while ((line=br.readLine()) != null) {
                        endString+=line;
                    }
                }
                else {
                    endString="";

                }

                connection.connect();

                /*
                InputStream in = new BufferedInputStream(connection.getInputStream());
                reader = new BufferedReader(new InputStreamReader(in));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }*/

                //String finalJson = buffer.toString();

                //JSONObject parentObject = new JSONObject();

                //in.close();

                return null;


                //ADD TO OTHERS

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(final JSONObject response) {
            super.onPostExecute(response);

            if (response != null){
                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
            }

        }
    }

    public class AddPost extends AsyncTask<String, String, JSONObject> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;




            try {
                URL url = new URL(params[0]);

                String json = params[1];

                JSONObject jsonObject = new JSONObject(json);
                HashMap<String, String> postDataParams = new HashMap<>();

                Iterator<?> keys = jsonObject.keys();

                while( keys.hasNext() ){
                    String key = (String)keys.next();
                    String value = jsonObject.getString(key);
                    postDataParams.put(key, value);

                }

                connection = (HttpURLConnection) url.openConnection();


                connection.setReadTimeout(15000);
                connection.setConnectTimeout(15000);
                //connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setRequestMethod("POST");


                OutputStream os = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();
                int responseCode=connection.getResponseCode();

                String endString = "";

                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    String line;
                    BufferedReader br=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    while ((line=br.readLine()) != null) {
                        endString+=line;
                    }
                }
                else {
                    endString="";

                }

                connection.connect();

                /*
                InputStream in = new BufferedInputStream(connection.getInputStream());
                reader = new BufferedReader(new InputStreamReader(in));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }*/

                //String finalJson = buffer.toString();

                //JSONObject parentObject = new JSONObject();

                //in.close();

                return null;


                //ADD TO OTHERS

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(final JSONObject response) {
            super.onPostExecute(response);

            if (response != null){
                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
            }

        }
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException{
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }



}
