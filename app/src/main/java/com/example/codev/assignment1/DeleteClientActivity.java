package com.example.codev.assignment1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import mehdi.sakout.fancybuttons.FancyButton;

public class DeleteClientActivity extends AppCompatActivity implements View.OnClickListener{

    FancyButton bdeleteClient;
    EditText etClientId;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_client);

        bdeleteClient=(FancyButton)findViewById(R.id.btn_deleteclientwithid);
        etClientId=(EditText)findViewById(R.id.et_client_delete);

        bdeleteClient.setOnClickListener(this);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading Client ...");
        pDialog.setCancelable(false);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_delete_client, menu);
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

        MyVolley.init(getApplicationContext());
        RequestQueue queue = MyVolley.getRequestQueue();

        String idd=etClientId.getText().toString();

        if (idd != null && !idd.isEmpty())
        {


            pDialog.show();
            String url="https://control.decimus.in:8443/mifosng-provider/api/v1/clients/"+idd+"?tenantIdentifier=default&pretty=true";


            JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.DELETE, url
                    , reqSuccessListener(), reqErrorListener()) {



                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<String, String>();

                    SharedPreferences sharedPreferencesForAuthentication =getSharedPreferences("AUTHENTICATION_KEY", Context.MODE_PRIVATE);
                    String baseauthKey=sharedPreferencesForAuthentication.getString("authentication_key", "xx");


                    headers.put("Authorization", "Basic "+baseauthKey);
                        headers.put("Content-Type", "application/json");

                    // headers.put("password", "password");

                    return headers;
                }

                protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();



                    // params.put("key", table2key);
                    //     params.put("username", "mifos");
                    //   params.put("password", "password");
                    // params.put("tenantIdentifier", "default");

                    return params;
                }
            };
            myReq.setRetryPolicy(new DefaultRetryPolicy(5000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(myReq);

        }
        else
        {
            Toast.makeText(this, "Enter a id", Toast.LENGTH_SHORT).show();
        }


    }


    private com.android.volley.Response.Listener<JSONObject> reqSuccessListener() {
        return new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject serverResponse) {
                //Log.d(TAG,"in volley success");
                //Log.d(TAG, "Response" + response);
                pDialog.hide();
                Toast.makeText(getApplicationContext(), "Client Deleted Successfully", Toast.LENGTH_LONG).show();
                etClientId.setText("");

            }
        };
    }

    private com.android.volley.Response.ErrorListener reqErrorListener() {
        return new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Log.d(TAG,"in volley error");
                // Log.d(TAG, error.toString());
                pDialog.hide();
                Log.i("resultt", "error "+error.toString());
                Toast.makeText(getApplicationContext(), "Can't Delete Client", Toast.LENGTH_LONG).show();
            }
        };
    }



}
