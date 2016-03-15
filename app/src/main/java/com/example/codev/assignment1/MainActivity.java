package com.example.codev.assignment1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    LinearLayout llCreateClient, llClientDetails, llOwnProfile, llAboutUpscale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        llCreateClient=(LinearLayout)findViewById(R.id.ll_createclient);
        llClientDetails=(LinearLayout)findViewById(R.id.ll_viewclient);
        llOwnProfile=(LinearLayout)findViewById(R.id.ll_viewownprofile);
        llAboutUpscale=(LinearLayout)findViewById(R.id.ll_aboutupscale);

        llCreateClient.setOnClickListener(this);
        llClientDetails.setOnClickListener(this);
        llOwnProfile.setOnClickListener(this);
        llAboutUpscale.setOnClickListener(this);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

    @Override
    public void onClick(View v) {

        Intent i=new Intent();


        switch (v.getId())
        {
            case R.id.ll_createclient:

                i.setClass(this, CreateClientActivity.class);

                break;

            case R.id.ll_viewclient:

                i.setClass(this, ClientActivity.class);

                break;

            case R.id.ll_viewownprofile:

                i.setClass(this, OwnProfileActivity.class);

                break;

            case R.id.ll_aboutupscale:

                i.setClass(this, AboutUpscale.class);

                break;

        }

        startActivity(i);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

    }
}
