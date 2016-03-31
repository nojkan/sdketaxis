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
import android.net.Uri;

import com.alpha.anna.webpay.sdk.exception.OrangeAPIException;
import com.android.volley.Request.Method;
import com.android.volley.Response;

import org.json.JSONObject;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Location of the Orange Cloud API functions.
 *
 * The class is parameterized with the type of session it uses. This will be
 * the same as the type of session you pass into the constructor.
 */
public final class OrangeStreetPixAPI {

    /**
     * The version of this Orange SDK.
     */
    public static final String SDK_VERSION = "0.0.1";

    //URL for Images
    //private static String URL_IMG = "https://api.orange.com/et/upload";
    private static String URL_IMG = "http://hawaii.orangeadd.com/etaxis/upload";

    private static String API_VERSION = "v1";

    private RestUtils myRestUtil;



    /**
     * Create an instance of Orange Cloud Api
     */
    public OrangeStreetPixAPI(Context context) {

        myRestUtil = new RestUtils(context);

    }


    /**
     * Upload a photo to the Server
     *
     * @param fileUri  Uri of photo to upload
     * @param file name of photo to upload
     * @param success  callback if upload is ok
     * @param progress callback to notify upload progress
     * @param failure  callback to notify error
     */
    public void upload(final Uri fileUri, final File file, final OrangeListener.Success<JSONObject> success,
                       final OrangeListener.Progress progress, final OrangeListener.Error failure) {


        URL url;
        try {
            //TODO prepare URL
            //TODO set photo name
            url = new URL(URL_IMG);
//                    + "/files/content?name=" + filename);

            this.myRestUtil.uploadRequest(url, file, getHeadersImg(), success,progress,
                    new OrangeListener.Error() {
                        //TODO check session
                        @Override
                        public void onErrorResponse(OrangeAPIException error) {
                            failure.onErrorResponse(error);
                        }
                    });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }



    private Map<String, String> getHeadersImg() {
        HashMap<String, String> headersImg = new HashMap<String, String>();
        //TODO Authent Apigee
        //headers.put("Authorization", "Bearer " + getSession().getAccessToken());
        //headersImg.put("Content-Type", "multipart/form-data;Boundary=Boundary");

        return headersImg;
    }

}
