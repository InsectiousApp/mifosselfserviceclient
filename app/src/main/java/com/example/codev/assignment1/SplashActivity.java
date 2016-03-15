package com.example.codev.assignment1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.vstechlab.easyfonts.EasyFonts;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import mehdi.sakout.fancybuttons.FancyButton;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener{
    private static int SPLASH_TIME_OUT = 1500;
    TextView appName;
    TextView tv_hello;
    ImageView iv_icon, iv_settings;
    LinearLayout llmain, lllogin;
    FancyButton blogin;
    EditText etusername, etpassword;
    ProgressDialog pDialog;
    SharedPreferences sharedPreferencesForUrl;
    SharedPreferences sharedPreferencesForAuthenticationKey;
    String baseUrl;

    String officeid="1";

            String firstname="cyriss";
    String lastname="devv";
    String fathername="jsmm";
    String externalId="786YYJ5";
    String dateformat="dd MMMM yyyy";
    String locale="en";
    boolean active=false;
    String submitteddate="04 March 2009";

    String boddy="{\n" +
            "\"officeId\": 1,\n" +
            "\"firstname\": \"cyris\",\n" +
            "\"lastname\": \"dev\",\n" +
            "\"fathername\": \"jsm\",\n" +
            "\"externalId\": \"786YYK6\",\n" +
            "\"dateFormat\": \"dd MMMM yyyy\",\n" +
            "\"locale\": \"en\",\n" +
            "\"active\": false,\n" +
            "    \"submittedOnDate\":\"04 March 2009\"\n" +
            "  \n" +
            "}";


    JSONObject jsonBody;
    String mRequestBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        tv_hello = (TextView) findViewById(R.id.tv_upscale);
        tv_hello.setTypeface(EasyFonts.tangerineRegular(this));
        iv_icon=(ImageView)findViewById(R.id.iv_icon);
        iv_settings=(ImageView)findViewById(R.id.iv_settings);
      //  llmain=(LinearLayout)findViewById(R.id.ll_splashscreen);
        lllogin=(LinearLayout)findViewById(R.id.ll_login);

        etusername=(EditText)findViewById(R.id.et_username);
        etpassword=(EditText)findViewById(R.id.et_password);

        blogin=(FancyButton)findViewById(R.id.btn_login);
        iv_settings.setOnClickListener(this);
        blogin.setOnClickListener(this);

        lllogin.setVisibility(View.GONE);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Processing Login ...");
        pDialog.setCancelable(false);

        sharedPreferencesForUrl =getSharedPreferences("BASE_URL", Context.MODE_PRIVATE);
        baseUrl=sharedPreferencesForUrl.getString("base_url", "https://control.decimus.in:8443/mifosng-provider/");

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, MainActivity.class);

                showLogin();



                //startActivity(i);
               // finish();
            }
        }, SPLASH_TIME_OUT);
    }

    public void showLogin()
    {

       // lllogin.setVisibility(View.VISIBLE);
      //    iv_icon.animate().translationY(-50);
       // tv_hello.animate().translationY(50);
//       lllogin.animate()
//                .translationY(lllogin.getHeight())
//                .alpha(1.0f)
//                .setDuration(700)
//                .setListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        super.onAnimationEnd(animation);
//                        lllogin.setVisibility(View.VISIBLE);
//                    }
//                });

       // ImageView imageView = (ImageView) findViewById(R.id.imageView);
//        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.your_fade_in_anim);
//// Now Set your animation
//        lllogin.startAnimation(fadeInAnimation);
        Animation animTranslate  = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.translate);
        animTranslate.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation arg0) { }

            @Override
            public void onAnimationRepeat(Animation arg0) { }

            @Override
            public void onAnimationEnd(Animation arg0) {
                lllogin.setVisibility(View.VISIBLE);
                Animation animFade  = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.fade);
                lllogin.startAnimation(animFade);
                Animation animFade2  = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.fade);
                iv_settings.setVisibility(View.VISIBLE);
                iv_settings.startAnimation(animFade2);

            }
        });

        Animation animTranslate2  = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.translate2);
        animTranslate2.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation arg0) {
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationEnd(Animation arg0) {

            }
        });


        iv_icon.startAnimation(animTranslate);

        tv_hello.startAnimation(animTranslate2);

    }

    public void hideLogin()
    {

    }

    @Override
    public void onClick(View v)
    {

        switch (v.getId()) {

            case R.id.iv_settings:

                Intent ii=new Intent();
                ii.setClass(SplashActivity.this, URLSettingsActivity.class);
                startActivity(ii);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);


                break;

            case R.id.btn_login:

//                Intent i=new Intent();
//                i.setClass(SplashActivity.this, MainActivity.class);
//                startActivity(i);
//                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

            if (etpassword.equals("") || etusername.equals("")) {
                Toast.makeText(getApplicationContext(), "Enter all fields", Toast.LENGTH_LONG).show();
            } else {
                pDialog.show();

                MyVolley.init(getApplicationContext());
                RequestQueue queue = MyVolley.getRequestQueue();

                String username = etusername.getText().toString();
                String password = etpassword.getText().toString();

                String url2 = baseUrl+"api/v1/authentication?username=" + username + "&password=" + password + "&tenantIdentifier=default";
                String url3="https://control.decimus.in:8443/mifosng-provider/api/v1/clients/178?tenantIdentifier=default&pretty=true";

                String url="https://control.decimus.in:8443/mifosng-provider/api/v1/clients?tenantIdentifier=default&pretty=true";

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

               JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.POST, url2
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

//                        headers.put("Authorization", "Basic bWlmb3M6cGFzc3dvcmQ\u003d");
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
                break;

        }
    }




    private com.android.volley.Response.Listener<JSONObject> reqSuccessListener() {
        return new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject serverResponse) {
                //Log.d(TAG,"in volley success");
                //Log.d(TAG, "Response" + response);
                pDialog.hide();
                Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();


                Log.i("resultt", "success " + serverResponse.toString());

                try {
                    String authenticationKey=serverResponse.getString("base64EncodedAuthenticationKey");
                    String userName=serverResponse.getString("username");
                    String userId=serverResponse.getString("userId");
                    String officeId=serverResponse.getString("officeId");
                    String officeName=serverResponse.getString("officeName");


                    sharedPreferencesForAuthenticationKey =getSharedPreferences("AUTHENTICATION_KEY", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferencesForAuthenticationKey.edit();

                    editor.putString("authentication_key", authenticationKey);
                    editor.putString("user_name", userName);
                    editor.putString("user_id", userId);
                    editor.putString("office_id", officeId);
                    editor.putString("office_name", officeName);

                    editor.commit();



                } catch (JSONException e) {
                    e.printStackTrace();
                }



                Intent i=new Intent();
                i.setClass(SplashActivity.this, MainActivity.class);
                startActivity(i);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);



              //  finish();

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
                Toast.makeText(getApplicationContext(), "Can't Login", Toast.LENGTH_LONG).show();
            }
        };
    }







}
