package com.example.codev.assignment1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class OwnProfileActivity extends AppCompatActivity {

    SharedPreferences sharedPreferencesForAuthenticationKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own_profile);

        TextView tvuserName=(TextView)findViewById(R.id.user_name);
        TextView tvuserId=(TextView)findViewById(R.id.user_id);
        TextView tvofficeId=(TextView)findViewById(R.id.office_id);
        TextView tvofficeName=(TextView)findViewById(R.id.office_name);


        sharedPreferencesForAuthenticationKey =getSharedPreferences("AUTHENTICATION_KEY", Context.MODE_PRIVATE);
        String userName=sharedPreferencesForAuthenticationKey.getString("user_name", "Name");

        Log.i("Nameeee", userName);

        String userId=sharedPreferencesForAuthenticationKey.getString("user_id", "112");
        String officeId=sharedPreferencesForAuthenticationKey.getString("office_id", "7AB");
        String officeName=sharedPreferencesForAuthenticationKey.getString("office_name", "Office");

        tvuserName.setText(userName);
        tvuserId.setText("User Id \t:\t"+userId);
        tvofficeId.setText("Office Id \t:\t"+officeId);
        tvofficeName.setText("Office Name \t:\t"+officeName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_own_profile, menu);
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
