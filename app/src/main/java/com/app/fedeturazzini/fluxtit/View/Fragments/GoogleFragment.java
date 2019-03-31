package com.app.fedeturazzini.fluxtit.View.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.app.fedeturazzini.fluxtit.Controller.Events.PetByIdEvent;
import com.app.fedeturazzini.fluxtit.Model.Pet;
import com.app.fedeturazzini.fluxtit.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * A simple {@link Fragment} subclass.
 */
public class GoogleFragment extends Fragment {

    /** private **/
    private Pet pet;
    private WebView webView;

    public GoogleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_google, container, false);

        /** Link UI **/
        webView = view.findViewById(R.id.googleWebView);

        return view;
    }

    /** Event bus **/
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPetByIdEvents(PetByIdEvent petByid) {
        pet = petByid.getPet();

        // Cargo el URL en la web view
        webView.setWebViewClient(new WebViewClient());
        try {
            webView.loadUrl("https://www.google.com/search?safe=active&source=hp&ei=h9WgXLq5D4zM5OUPocO1yAE&q=" + pet.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    };

}
