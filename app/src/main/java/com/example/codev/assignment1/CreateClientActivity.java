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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import mehdi.sakout.fancybuttons.FancyButton;

public class CreateClientActivity extends AppCompatActivity implements View.OnClickListener{

    EditText etname, etfathername, etmobilenumber;
    FancyButton bregister;
    CheckBox cb;
    ProgressDialog progressDialog;

    String name;
    String fathername;
    String mobileno;
    String mRequestBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_client);

        bregister=(FancyButton)findViewById(R.id.btn_register_new_client);
        etname=(EditText)findViewById(R.id.et_client_new_name);
        etfathername=(EditText)findViewById(R.id.et_client_new_fathername);
        etmobilenumber=(EditText)findViewById(R.id.et_client_new_mobileno);
        cb=(CheckBox)findViewById(R.id.chb_agree);

        bregister.setOnClickListener(this);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Registering....");
        progressDialog.setCancelable(false);
        progressDialog.hide();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_client, menu);
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

      if(cb.isChecked())
      {

         name=etname.getText().toString();
         fathername=etfathername.getText().toString();
           mobileno=etmobilenumber.getText().toString();

          if (name != null && !name.isEmpty()&&fathername != null && !fathername.isEmpty()&&mobileno != null && !mobileno.isEmpty())
          {

              makeserverrequest();


          }
           else
          {
              Toast.makeText(this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
          }

      }
        else
      {
          Toast.makeText(this, "Please agree the terms", Toast.LENGTH_SHORT).show();
      }

    }




    private void makeserverrequest()

    {




        MyVolley.init(getApplicationContext());
        RequestQueue queue = MyVolley.getRequestQueue();

       // String idd=etClientId.getText().toString();



            progressDialog.show();
            String url="https://control.decimus.in:8443/mifosng-provider/api/v1/clients?tenantIdentifier=default&pretty=true";

                JSONObject jsonBody = new JSONObject();

                try {
                    jsonBody.put("officeId", "1");
                    jsonBody.put("firstname", name);
                    jsonBody.put("lastname", mobileno);
                    jsonBody.put("fathername", fathername);


                    Random crazy=new Random();
                    String randomnumber1=String.valueOf(crazy.nextInt(978)+1);
                    String randomnumber2=String.valueOf(crazy.nextInt(7)+1);

                    String externalIdd=randomnumber1+"YJJ"+randomnumber2;

                    jsonBody.put("externalId", externalIdd);

                    Log.i("resultt", "external id " + externalIdd);
                    Log.i("resultt", "name "+name);
                    Log.i("resultt", "mobile no "+mobileno);
                    Log.i("resultt", "father name "+fathername);

                    jsonBody.put("dateFormat", "dd MMMM yyyy");
                    jsonBody.put("locale", "en");
                    jsonBody.put("active", false);

                    Calendar c = Calendar.getInstance();

                    SimpleDateFormat df = new SimpleDateFormat("dd MMMM yyyy");
                    String formattedDate = df.format(c.getTime());

                    jsonBody.put("submittedOnDate", formattedDate);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                 mRequestBody = jsonBody.toString();
                Log.i("resultt", "bodyy "+mRequestBody.toString());

            JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.POST, url
                    , reqSuccessListener(), reqErrorListener()) {



                    @Override
                    public byte[] getBody() {
                        //String str = "{\"officeId\":\""+officeid+"\",\"firstname\":\""+firstname+"\",\"lastname\":\"\""+lastname+"\"\",\"fathername\":\"\""+fathername+"\"\"}";

                        return mRequestBody.getBytes();
                    };

                    public String getBodyContentType()
                    {
                        return "application/json; charset=utf-8";
                    }



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


    private com.android.volley.Response.Listener<JSONObject> reqSuccessListener() {
        return new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject serverResponse) {
                //Log.d(TAG,"in volley success");
                //Log.d(TAG, "Response" + response);
                progressDialog.hide();
                //Toast.makeText(getApplicationContext(), "Successfully Registered", Toast.LENGTH_LONG).show();

                try {
                    String clientId=serverResponse.getString("clientId");
                    Toast.makeText(getApplicationContext(), "Registered successfully with client Id :"+clientId, Toast.LENGTH_LONG).show();
                etname.setText("");
                    etfathername.setText("");
                    etmobilenumber.setText("");
                    cb.setChecked(false);

                } catch (JSONException e) {
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
                progressDialog.hide();
                Log.i("resultt", "error "+error.toString());
                Toast.makeText(getApplicationContext(), "Can't Register", Toast.LENGTH_LONG).show();
            }
        };
    }





}
