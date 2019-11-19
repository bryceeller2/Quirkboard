package com.quirkboard.imagebutton;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Tushar on 4/24/16.
 */
public class Statspage extends Activity {

    TextView stats;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.statspagewindow);
        stats = (TextView) findViewById(R.id.statstext);

        // set the text of statstext object to change the text on the stats page!.
    }
}
