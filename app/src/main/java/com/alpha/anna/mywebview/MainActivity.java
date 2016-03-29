package com.alpha.anna.mywebview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.RatingBar;


public class MainActivity extends AppCompatActivity implements FragmentListener {

    private WebView webPage;
    private RatingBar taxiRating;
    private ImageButton paiement;
    private int rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        loadFragment(new com.alpha.anna.mywebview.MyFragments.MyFourthFragment(), false);

    }

    @Override
    public void loadFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_main, fragment);
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }

    /**
     * Refresh the data of folder displayed
     * needed for UploadPicture
     */
    public void refreshData() {

    }


}
