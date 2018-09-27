package com.ks.musicdownloader.activity;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ks.musicdownloader.ArtistInfo;
import com.ks.musicdownloader.Constants;
import com.ks.musicdownloader.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressWarnings("DanglingJavadoc")
public class ArtistFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = ArtistFragment.class.getSimpleName();

    public RecyclerView recyclerView;
    private View fragmentView;
    private FloatingActionButton downloadButton;
    private LinearLayoutManager linearLayoutManager;

    // Any changes made on this object in this file will also reflect
    // in the parsedArtistInfo object in ListSongsActivity since they both
    // are same references.
    private ArtistInfo artistInfo;

    private FragmentCallback fragmentCallback;
    private ArtistAdapterCallback adapterCallback;

    public ArtistFragment() {
        // Required empty public constructor
    }

    @Override
    public void setArguments(@Nullable Bundle bundle) {
        Log.d(TAG, "setArguments");
        super.setArguments(bundle);
        if (bundle == null) {
            Log.wtf(TAG, "No artist info found! Killing fragment!!");
            removeFragment();
            return;
        }
        artistInfo = bundle.getParcelable(Constants.PARSED_ARTIST_INFO);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_artist, container, false);

        // set listener for download button
        downloadButton = fragmentView.findViewById(R.id.download_button);
        downloadButton.setOnClickListener(this);

        // set callbacks
        fragmentCallback = (FragmentCallback) getActivity();
        createAdapterCallback();

        // set adapter for recycler view
        recyclerView = fragmentView.findViewById(R.id.artist_recycler_view);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        List<String> albumNames = new ArrayList<>(artistInfo.getAlbumInfo().keySet());
        recyclerView.setAdapter(new ArtistAdapter(albumNames, artistInfo.getAlbumCheckedStatus(), adapterCallback));

        return fragmentView;
    }

    @Override
    public void onClick(View view) {
        if (view == null) {
            return;
        }
        switch (view.getId()) {
            case R.id.download_button:
                if (fragmentCallback != null) {
                    fragmentCallback.download();
                } else {
                    Log.d(TAG, "onClick(): fragmentCallback null!");
                }
                break;
        }
    }

    /******************Private************************************/
    /******************Methods************************************/

    private void removeFragment() {
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().remove(this).commit();
    }

    private void createAdapterCallback() {
        adapterCallback = new ArtistAdapterCallback() {

            @Override
            public void setAlbumCheckedStatus(String album, Boolean status) {
                artistInfo.setAlbumCheckedStatus(album, status);
            }
        };
    }
}