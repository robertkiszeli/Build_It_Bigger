package com.udacity.gradle.builditbigger.free;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.robertkiszelirk.jokecreator.ShowJokeActivity;
import com.udacity.gradle.builditbigger.R;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    ProgressBar progressBar = null;

    public String loadedJoke = null;

    public boolean testFlag = false;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        progressBar = root.findViewById(R.id.tell_joke_progress_bar);
        progressBar.setVisibility(View.GONE);

        Button buttonTellJoke = root.findViewById(R.id.tell_joke_button);
        buttonTellJoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                getJoke();
            }
        });


        mAdView.loadAd(adRequest);
        return root;
    }

    private void getJoke() {

        new EndpointAsyncTask().execute(this);
    }

    public void startShowJokeActivity() {
        if(!testFlag) {
            Context context = getActivity();
            Intent intent = new Intent(context, ShowJokeActivity.class);
            intent.putExtra(context.getString(R.string.intent_key_to_pass_joke), loadedJoke);
            context.startActivity(intent);
            progressBar.setVisibility(View.GONE);
        }
    }
}
