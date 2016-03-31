package com.alpha.anna.mywebview.MyFragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;

//import com.firebase.client.Firebase;
//import com.firebase.client.FirebaseError;
//import com.orange.labs.etaxis.ETaxisActivity;
//import com.orange.labs.etaxis.FragmentListener;


import com.alpha.anna.mywebview.FragmentListener;
import com.alpha.anna.mywebview.MainActivity;
import com.alpha.anna.mywebview.R;
import com.alpha.anna.mywebview.UploadPicture;
import com.alpha.anna.webpay.sdk.OrangePaymentAPI;
import com.alpha.anna.webpay.sdk.OrangeStreetPixAPI;
import com.alpha.anna.webpay.sdk.exception.OrangeAPIException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Map;

/**
 * Created by Aziliz on 11/02/2016.
 */
public class MyFourthFragment extends Fragment {

    //private Map<String, Object> infoclients;
    //private Map<String, Object> infobags;
    private Map<String, Object> finalRequest;
    private NumberPicker nbclients;
    private NumberPicker nbbags;
    private int guys;
    private int bags;
    private Button test;
    private ImageButton sendRequest;
    private ImageButton makeShot;
    private FragmentListener mFragmentListener;
//    public Firebase dataBase, pushID;
    private static final int CAMERA_PIC_REQUEST = 1337;
    private OrangeStreetPixAPI mApi;


    private boolean general;


    private ProgressDialog bar;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.commande_2, container, false);

    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final PackageManager pm = getContext().getPackageManager();
        this.mApi = new OrangeStreetPixAPI(getContext());


        makeShot = (ImageButton) view.findViewById(R.id.photo);
        makeShot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                    Intent cameraIntent = new Intent("android.media.action.IMAGE_CAPTURE");
                    startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);

                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_PIC_REQUEST) {
                //getting photo
                Bitmap image = (Bitmap) data.getExtras().get("data");
                //saving photo to an image view
                ImageView imageview = (ImageView) getView().findViewById(R.id.imageView);
                imageview.setImageBitmap(image);

                Uri selectedImageUri = data.getData();
                String uri = getRealPathFromURI(selectedImageUri);
                if (uri == null) {
                    uri = selectedImageUri.getPath();
                }
                if (uri != null) {
                    File file = new File(uri);
                    UploadPicture upload = new UploadPicture(getContext(), mApi, file, selectedImageUri);
                    upload.execute();
                }
            }
//        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        Log.e("lala", String.valueOf(Uri.parse(path)));
        Uri myUri = Uri.parse(path);
        Log.e("uri", "uriiii");
        return myUri;
    }
    public String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = this.getContext().getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }


//    public void upload(){
//
//        OrangeListener.Success<JSONObject> defaultListener = new OrangeListener.Success<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                // Add new result in stack and display data in the listView
//                Log.v("response create payment", response.toString());
//
//            }
//        };
//
//        OrangeListener.Error defaultErrorListener = new OrangeListener.Error() {
//            @Override
//            public void onErrorResponse(OrangeAPIException error) {
//                failure(error);
//            }
//        };
//
//        mApi.upload(, "filename", defaultListener, defaultErrorListener);
//
//    }

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
