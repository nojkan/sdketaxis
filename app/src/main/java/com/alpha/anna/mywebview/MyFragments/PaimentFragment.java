package com.alpha.anna.mywebview.MyFragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;

import com.alpha.anna.mywebview.R;
import com.alpha.anna.mywebview.FragmentListener;
import com.alpha.anna.webpay.sdk.OrangePaymentAPI;
import com.alpha.anna.webpay.sdk.OrangeListener;
import com.alpha.anna.webpay.sdk.exception.OrangeAPIException;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by Anna on 08.03.2016.
 */
public class PaimentFragment extends Fragment {

//    private Firebase dataBase;
    private RatingBar taxiRating;
    private ImageButton payement;
    private Map<String, Object> inforates;
    private int rating;
    private FragmentListener mFragmentListener;
    private OrangePaymentAPI mApi;
    private Gson gson;
    private JSONObject mjsonOM = new JSONObject();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.paiement, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        Firebase.setAndroidContext(this.getContext());
//        dataBase = new Firebase(getResources().getString(R.string.firebase_url));

        taxiRating = (RatingBar)view.findViewById(R.id.taxiRating);
//        inforates = new HashMap<String, Object>();

        taxiRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float stars, boolean fromUser) {

                rating = (int) Math.round(stars);
                Log.e("Rating", String.valueOf(rating));
//                inforates.put("users/taxis/1/rating", rating);
//                dataBase.updateChildren(inforates);


            }
        });

        payement = (ImageButton)view.findViewById(R.id.payement);

        payement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFragmentListener != null) {
                    // true to add fragment on backstack
                    mApi = new OrangePaymentAPI(getContext());

                    postPayments();
                    //try {
                    //    Log.v("myrespOM", mjsonOM.getString("payment_url"));
                    //} catch (JSONException e) {
                    //    e.printStackTrace();
                    //}


                }
            }
        });

    }
/*
    public void postPayments() {

//            String jsonStr =  "{\"merchant_key\":\"" + obj.getMerchant_key() + "\"," +
//                    "\"currency\":\"" + obj.getCurrency() + "\"," +
//                    "\"order_id\":\"" + obj.getOrder_id() + "\"," +
//                    "\"amount\":" + obj.getAmount() + "," +
//                    "\"return_url\":\"" + obj.getReturn_url() + "\"," +
//                    "\"cancel_url\":\"" + obj.getCancel_url() + "\"," +
//                    "\"notif_url\":\"" + obj.getNotif_url() + "\"," +
//                    "\"lang\":\"" + obj.getLang() + "\"}";


//        JsonParser jsonParser = new JsonParser();
        JSONObject jo = new JSONObject();
        try {
            jo.putOpt("name", "Anna");
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        try {
//            jo = new JSONObject();
//            jo.putOpt("merchant_key", obj.getMerchant_key());
//            jo.putOpt("currency", obj.getCurrency());
//            jo.putOpt("order_id", obj.getOrder_id());
//            jo.putOpt("amount", obj.getAmount());
//            jo.putOpt("return_url", "http://hawaii.orangeadd.com/etaxis/webpay/return");
//
////                    obj.getReturn_url());
//            jo.putOpt("cancel_url", "http://hawaii.orangeadd.com/etaxis/webpay/cancel");
////                    obj.getCancel_url());
//            jo.putOpt("notif_url", "http://hawaii.orangeadd.com/etaxis/webpay/notif");
////                    .getNotif_url());
//            jo.putOpt("lang", obj.getLang());
//            Log.e("obj", String.valueOf(jo));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        Assert.assertNotNull(jo);


        final OrangeListener.Success<JSONObject> defaultListener = new OrangeListener.Success<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // Add new result in stack and display data in the listView
                Log.v("response create payment", String.valueOf(response));
                mjsonOM = (JSONObject) response;


            }
        };

        OrangeListener.Error defaultErrorListener = new OrangeListener.Error() {
            @Override
            public void onErrorResponse(OrangeAPIException error) {
                failure(error);
            }
        };
        mApi.postPayment(jo, defaultListener, defaultErrorListener);

    }

*/


    public void postPayments() {
        JSONObject PaymentOjb = new JSONObject();


        final OrangeListener.Success<JSONObject> defaultListener = new OrangeListener.Success<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // Add new result in stack and display data in the listView
                Log.v("response crea pay", response.toString());
                //mTextView.setText(response.toString());
                String url = response.optString("payment_url");
                Log.v("url", url);
                if (url != null) {
                    com.alpha.anna.mywebview.MyFragments.WebPageFragment webPageFragment = new com.alpha.anna.mywebview.MyFragments.WebPageFragment();
                    Bundle args = new Bundle();
                    args.putString("URL", url);
                    webPageFragment.setArguments(args);
                    mFragmentListener.loadFragment(webPageFragment, true);
                } else {
                    // popup erreur
                }
            }
        };

        OrangeListener.Error defaultErrorListener = new OrangeListener.Error() {
            @Override
            public void onErrorResponse(OrangeAPIException error) {
                failure(error);
            }
        };
        mApi.postPayment(PaymentOjb, defaultListener, defaultErrorListener);
    }


    /**
     * Method called when an API error occurred
     *
     * @param error               an OrangeAPIException
     */
    public void failure(OrangeAPIException error) {
        // Display popup alert and retry connect !

        AlertDialog.Builder alert = new AlertDialog.Builder(this.getContext());
        alert.setTitle(R.string.error);
        alert.setMessage(error.description);
        alert.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            /*if (forceAuthentication) {
                // For sample retry authentication if error
                mApi.getSession().startAuthentication();
            }*/
            }
        });
        alert.show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mFragmentListener = (FragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement FragmentListener");
        }
    }
}
