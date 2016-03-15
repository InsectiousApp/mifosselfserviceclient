package com.example.codev.assignment1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClientListActivity extends AppCompatActivity {

ProgressDialog pDialog;
    ListView lv;
    LayoutInflater l;
    ArrayList<ListItem> data;
    ListItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_list2);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading Client ...");
        pDialog.setCancelable(false);


        lv=(ListView)findViewById(R.id.lvmain);
        data=new ArrayList<ListItem>();
        l=getLayoutInflater();

//        ListItem l1=new ListItem("Chetan");
//        ListItem l2=new ListItem("Mann");
//
//        data.add(l1);
//        data.add(l2);
//        data.add(l1);
//        data.add(l2);
//        data.add(l1);
//        data.add(l2);
//        data.add(l1);
//        data.add(l2);

        fetchclientlistfromserver();

        adapter=new ListItemAdapter(this, 0, data, l);

        lv.setAdapter(adapter);


    }

    private void fetchclientlistfromserver() {


        MyVolley.init(getApplicationContext());
        RequestQueue queue = MyVolley.getRequestQueue();

        pDialog.show();
        String url="https://control.decimus.in:8443/mifosng-provider/api/v1/clients?tenantIdentifier=default&pretty=true";


        JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.GET, url
                , reqSuccessListener(), reqErrorListener()) {



            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();

                SharedPreferences sharedPreferencesForAuthentication =getSharedPreferences("AUTHENTICATION_KEY", Context.MODE_PRIVATE);
                String baseauthKey=sharedPreferencesForAuthentication.getString("authentication_key", "xx");


                headers.put("Authorization", "Basic "+baseauthKey);
//                        headers.put("Content-Type", "application/json");

                // headers.put("password", "password");

                return headers;
            }

            protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();



                return params;
            }
        };
        myReq.setRetryPolicy(new DefaultRetryPolicy(5000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(myReq);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_client_list, menu);
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


    private com.android.volley.Response.Listener<JSONObject> reqSuccessListener() {
        return new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject serverResponseMain) {
                //Log.d(TAG,"in volley success");
                //Log.d(TAG, "Response" + response);

                Toast.makeText(getApplicationContext(), "Client Detail Loaded", Toast.LENGTH_LONG).show();


                Log.i("resultt", "success " + serverResponseMain.toString());

                try {

                    JSONArray arr=serverResponseMain.getJSONArray("pageItems");
                    int totalClients=Integer.parseInt(serverResponseMain.getString("totalFilteredRecords"));

                   for(int i=0; i<totalClients; i++)
                    {
                        JSONObject serverResponse=arr.getJSONObject(i);
                        String displayname=serverResponse.getString("displayName");
                        String id=serverResponse.getString("id");
                        String accountnumber=serverResponse.getString("accountNo");
                        String externalid=serverResponse.getString("externalId");
                        String officeid=serverResponse.getString("officeId");
                        String officeName=serverResponse.getString("officeName");

                        ListItem l1=new ListItem(displayname, id, accountnumber, externalid, officeid, officeName);
                        data.add(l1);

                    }
                    adapter.notifyDataSetChanged();
                    pDialog.hide();

                } catch (JSONException e) {

                    Log.i("resultt", "catch");

                    e.printStackTrace();
                }


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
                Toast.makeText(getApplicationContext(), "Can't load client", Toast.LENGTH_LONG).show();
            }
        };
    }





}
