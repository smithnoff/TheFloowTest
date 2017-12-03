package com.cesarsmith.thefloowtest.ui.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cesarsmith.thefloowtest.R;
import com.cesarsmith.thefloowtest.background.database.implementations.DBManager;
/*This class make a delete of database journeys and places tables*/
public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Button restoreAppBt=(Button)findViewById(R.id.restore_bt);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }
        final DBManager dbManager=new DBManager(this);
        restoreAppBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // method call database manager delete method

                dbManager.deleteDB();
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
