package com.example.codev.assignment1;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import mehdi.sakout.fancybuttons.FancyButton;

public class URLSettingsActivity extends AppCompatActivity {

    FancyButton bseturl;
    EditText eturl;
    SharedPreferences sharedPreferencesForUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urlsettings);


        eturl=(EditText)findViewById(R.id.et_url);
        bseturl=(FancyButton)findViewById(R.id.btn_seturl);

        sharedPreferencesForUrl =getSharedPreferences("BASE_URL", Context.MODE_PRIVATE);
        String urll=sharedPreferencesForUrl.getString("base_url", "https://control.decimus.in:8443/mifosng-provider/");
        eturl.setText(urll);

        bseturl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sharedPreferencesForUrl =getSharedPreferences("BASE_URL", Context.MODE_PRIVATE);


                SharedPreferences.Editor editor = sharedPreferencesForUrl.edit();

               // i=(index+1)*2;

                editor.putString("base_url", eturl.getText().toString());
                editor.commit();

                Toast.makeText(getApplicationContext(), "Url updated successfully", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_urlsettings, menu);
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
}
