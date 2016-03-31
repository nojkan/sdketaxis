package com.alpha.anna.mywebview;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.Toast;

import com.alpha.anna.webpay.sdk.OrangeListener;
import com.alpha.anna.webpay.sdk.OrangeStreetPixAPI;
import com.alpha.anna.webpay.sdk.exception.OrangeAPIException;

import org.json.JSONObject;

import java.io.File;

public class UploadPicture extends AsyncTask<Void, Integer, Boolean> {

    private OrangeStreetPixAPI mApi;
    private Uri mUri;
    private File mFile;

    private boolean mresult;

    private long mFileLen;
    //    private UploadRequest mRequest;
    private Context mContext;
    private final ProgressDialog mDialog;

    private String mErrorMsg;

    public UploadPicture(Context context, OrangeStreetPixAPI api, File file, Uri uri) {
        mContext = context;
        mApi = api;
        mFile = file;
        mUri = uri;

        mDialog = new ProgressDialog(context);
        mDialog.setMax(100);
        mDialog.setMessage(mContext.getResources().getString(R.string.upload_image_title) +
                getFilename());
        mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mDialog.setProgress(0);
        mDialog.show();
    }

    private ContentResolver getContentResolver() {
        return mContext.getContentResolver();
    }


    @Override
    protected Boolean doInBackground(Void... voids) {

        mApi.upload(mUri, mFile, new OrangeListener.Success<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                mresult = true;
            }
        }, new OrangeListener.Progress() {
            @Override
        public void onProgress(float ratio) {
                publishProgress(new Integer((int) (ratio * 100)));
            }
        }, new OrangeListener.Error() {
            @Override
            public void onErrorResponse(OrangeAPIException error) {
                mresult = false;
                showToast(error.description);
            }
        });
        Log.e("Are you doing it?", "Yes, I am");
        return mresult;
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        mDialog.setProgress(progress[0].intValue());
    }

    @Override
    protected void onPostExecute(Boolean result) {
        mDialog.dismiss();
        if (result) {
            showToast(mContext.getResources().getString(R.string.upload_file_success));
            if (mContext != null) {
                ((MainActivity) mContext).refreshData();
            }
        } else {
            showToast(mErrorMsg);
        }
    }

    private void showToast(String msg) {
        Toast error = Toast.makeText(mContext, msg, Toast.LENGTH_LONG);
        error.show();
    }

    /**
     * Try to get the filename of an uri (used to upload)
     *
     * @return the filename or 'default_file' name
     */
    private String getFilename() {
        String result = "default_file";
        if (mUri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(mUri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = mUri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }
}