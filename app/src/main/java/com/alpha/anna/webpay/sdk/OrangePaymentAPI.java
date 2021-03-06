/*
 * Copyright (c) 2014 Orange.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Created by Erwan Morvillez on 07/10/14.
 */
package com.alpha.anna.webpay.sdk;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.alpha.anna.webpay.sdk.exception.OrangeAPIException;
import com.alpha.anna.webpay.sdk.OrangeListener;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.graphics.Bitmap;

/**
 * Location of the Orange Cloud API functions.
 *
 * The class is parameterized with the type of session it uses. This will be
 * the same as the type of session you pass into the constructor.
 */
public final class OrangePaymentAPI {

    /**
     * The version of this Orange SDK.
     */
    public static final String SDK_VERSION = "0.0.1";
    // Payment server information
    private static String API_URL = "https://api.orange.com/orange-money-webpay/dev/v1/webpayment";
    //private static String API_URL = "https://api.orange.com/t";
//    private static String API_URL = "https://hawaii.orangeadd.com/etaxis/headers";

    private static String API_VERSION = "v1";

    private com.alpha.anna.webpay.sdk.RestUtils myRestUtil;
    private GsonRequest myGsonRequest;
    private JSONObject mResponse;

    /**
     * Create an instance of Orange Cloud Api
     */
    public OrangePaymentAPI(Context context) {

        myRestUtil = new RestUtils(context);
        //myGsonRequest = new GsonRequest(context);

    }

    /**
     * Create a new payment.
     *
     * @param myJsonPayment   the channel to create
     * @param success callback returning the new created Entry
     * @param failure callback when error occurred
     */
    public void postPayment(final JSONObject myJsonPayment,
                            final OrangeListener.Success<JSONObject> success,
                            final OrangeListener.Error failure) {



        // Create Tag used to cancel the request
        final String tag = "OM/webpay/";


        // Prepare URL
//        final String url = API_URL + API_VERSION + "/webpayment";

        final String url = API_URL;
        this.myRestUtil.jsonRequest(tag, Method.POST, url, myJsonPayment, getHeaders(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.v("tagresp", response.toString());
                success.onResponse(response);

            }
        }, new OrangeListener.Error() {
            @Override
            public void onErrorResponse(OrangeAPIException error) {
                //checkSession(error, new OrangeListener.Success<String>() {
                //    @Override
                //    public void onResponse(String response) {
                //        postChannel(myChannel, success, failure);
                //    }
                //}, failure);
                failure.onErrorResponse(error);
            }
        });

    }



    /**
     * Generate a Map containing the HTTP headers needed to Cloud Api.
     *
     * @return a Map containing minimal headers needed to Cloud Api
     */
   private Map<String, String> getHeaders() {
        HashMap<String, String> headers = new HashMap<String, String>();
        //TODO Authent Apigee
        //headers.put("Authorization", "Bearer " + getSession().getAccessToken());

       //headers.put("Content-Type", "application/json");
       headers.put("Accept", "application/json");
       headers.put("Authorization", "Bearer h49PeiSj63FPMLZqDcdGt96juvtv");
        return headers;
    }


}
