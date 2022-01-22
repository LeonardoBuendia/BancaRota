package com.buendiasoftware.bancarota;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
//import android.widget.Toolbar;

public class config extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        Toolbar configToolbar = (Toolbar) findViewById(R.id.config_toolbar);
        setSupportActionBar(configToolbar);

        // Get a support ActionBar corresponding to this toolbar
      //  getActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar configAb = getSupportActionBar();
        // Enable the Up button
        configAb.setDisplayHomeAsUpEnabled(true);

    }

}
