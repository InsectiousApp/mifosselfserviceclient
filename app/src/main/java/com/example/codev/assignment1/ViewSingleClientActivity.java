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
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import mehdi.sakout.fancybuttons.FancyButton;

public class ViewSingleClientActivity extends AppCompatActivity implements View.OnClickListener{

    EditText etClientId;
    TextView tvofficeId, tvdisplayName, tvfirstName, tvlastName, tvaccountNo, tvexternalId, tvofficeName, tvsubmitBy;
    FancyButton bsearch;

    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_single_client);

        bsearch=(FancyButton)findViewById(R.id.btn_search_single_client);
        etClientId=(EditText)findViewById(R.id.et_client_id);

        tvdisplayName=(TextView)findViewById(R.id.client_displayname);
        tvfirstName=(TextView)findViewById(R.id.client_firstname);
        tvlastName=(TextView)findViewById(R.id.client_lastname);
        tvaccountNo=(TextView)findViewById(R.id.client_accountnumber);
        tvexternalId=(TextView)findViewById(R.id.client_externalid);
        tvofficeName=(TextView)findViewById(R.id.client_officename);
        tvsubmitBy=(TextView)findViewById(R.id.client_submittedby);
        tvofficeId=(TextView)findViewById(R.id.client_officeid);

        bsearch.setOnClickListener(this);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading Client ...");
        pDialog.setCancelable(false);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_single_client, menu);
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

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }


        MyVolley.init(getApplicationContext());
        RequestQueue queue = MyVolley.getRequestQueue();

        String idd=etClientId.getText().toString();

        if (idd != null && !idd.isEmpty())
        {


        pDialog.show();
        String url="https://control.decimus.in:8443/mifosng-provider/api/v1/clients/"+idd+"?tenantIdentifier=default&pretty=true";

            //     /"+idd+"

//                JSONObject jsonBody = new JSONObject();
//
//                try {
//                    jsonBody.put("officeId", "1");
//                    jsonBody.put("firstname", "cmm");
//                    jsonBody.put("lastname", "mnn");
//                    jsonBody.put("fathername", "jss");
//                    jsonBody.put("externalId", "786YJJ4");
//                    jsonBody.put("dateFormat", "dd MMMM yyyy");
//                    jsonBody.put("locale", "en");
//                    jsonBody.put("active", false);
//                    jsonBody.put("submittedOnDate", "04 March 2009");
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                 mRequestBody = jsonBody.toString();
//                Log.i("resultt", "bodyy "+mRequestBody.toString());

        JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.GET, url
                , reqSuccessListener(), reqErrorListener()) {

//
//
//                    @Override
//                    public byte[] getBody() throws com.android.volley.AuthFailureError {
//                        //String str = "{\"officeId\":\""+officeid+"\",\"firstname\":\""+firstname+"\",\"lastname\":\"\""+lastname+"\"\",\"fathername\":\"\""+fathername+"\"\"}";
//
//                        return mRequestBody.getBytes();
//                    };
//
//                    public String getBodyContentType()
//                    {
//                        return "application/json; charset=utf-8";
//                    }



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
                Toast.makeText(getApplicationContext(), "Client Detail Loaded", Toast.LENGTH_LONG).show();


                Log.i("resultt", "success " + serverResponse.toString());

                try {

//                    Log.i("resultt", "try inside");
//
//                    JSONArray arr=serverResponse.getJSONArray("pageItems");
//
//                    JSONObject singleClientObject=arr.getJSONObject(5);
//                    String accntn=singleClientObject.getString("accountNo");
//
//                    Log.i("resultt", "array : "+singleClientObject.toString());
//                    Log.i("resultt", "array : "+accntn);

                    String displayname=serverResponse.getString("displayName");
                    String firstname=serverResponse.getString("firstname");
                    String lastname=serverResponse.getString("lastname");
                    String accountnumber=serverResponse.getString("accountNo");
                    String externalid=serverResponse.getString("externalId");

                    String officeid=serverResponse.getString("officeId");
                    String officeName=serverResponse.getString("officeName");
                //    String submittedby=serverResponse.getString("submittedByUsername");

                    Log.i("resultt", "reached inside " + displayname);

                    Log.i("resultt", " "+displayname+ " "+firstname+" "+ lastname+" "+accountnumber+" "+externalid+" "+officeid+" "+officeName);

                    tvdisplayName.setText(displayname);
                    tvfirstName.setText(firstname);
                    tvlastName.setText(lastname);
                    tvaccountNo.setText(accountnumber);
                    tvexternalId.setText(externalid);

                    tvofficeId.setText(officeid);
                    tvofficeName.setText(officeName);
                   // tvsubmitBy.setText(submittedby);

                    Log.i("resultt", "reached inside2 "+displayname);

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
