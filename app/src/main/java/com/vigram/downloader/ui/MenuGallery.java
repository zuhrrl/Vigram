/**
 * Class Name: com.vigram.downloader.ui;
 * App Name: Vigram
 * Package: com.vigram.downloader
 * Created: 10:18 PM GMT+7 1/6/2020
 * Developer: Vigram.cc
 * By: Zuhrul Anam (zuhrul@vigram.cc)
 * Edited: 10:19 PM GMT+7 1/6/2020
 */

package com.vigram.downloader.ui;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.vigram.downloader.R;
import com.vigram.downloader.component.gallery.GalleryHelper;
import com.vigram.downloader.component.gallery.PermissionsHelper;
import com.vigram.downloader.component.model.MainModels;

import java.security.Permissions;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenuGallery#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuGallery extends Fragment implements LifecycleOwner {
    private LifecycleRegistry lifecycleRegistry;
    private static GridView gridView;
    private int countFetchGallery = 0;
    private String tag_menu_gallery = "MenuGallery";

    public MenuGallery() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment MenuGallery.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuGallery newInstance() {
        MenuGallery fragment = new MenuGallery();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycleRegistry = new LifecycleRegistry(this);
        lifecycleRegistry.setCurrentState(Lifecycle.State.CREATED);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_menu_gallery, container, false);
        gridView = fragmentView.findViewById(R.id.gallery_grid);

        return fragmentView;
    }

    @Override
    public void onStart() {
        super.onStart();
        lifecycleRegistry.setCurrentState(Lifecycle.State.STARTED);
        MainModels.setGridView(gridView);
        if(!PermissionsHelper.newInstance().checkAndRequestPermissions(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

        }
        else {
            Log.d("MenuGallery", "permission read and write allowed");
            // Instance Gallery Helper
            GalleryHelper.newInstance(getContext());
            // Fetch item on director com.vigram.downloader
            GalleryHelper.fetchGallery();
            if (GalleryHelper.getGalleryHelper() != null) {
                gridView.setAdapter(GalleryHelper.getGalleryHelper());
            }
        }
        gridView.setOnItemClickListener((parent, view, position, id) -> {
            Toast.makeText(getContext(), "Please Check on Gallery :)", Toast.LENGTH_SHORT).show();

        });


    }

    @Override
    public void onResume() {
        super.onResume();
        lifecycleRegistry.setCurrentState(Lifecycle.State.RESUMED);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return super.getLifecycle();
    }

    public static void refreshGallery(Activity activity, Context context) {
        if(!PermissionsHelper.newInstance().checkAndRequestPermissions(activity, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Log.d("MenuGallery", "permission not granted menu gallery ");
        }
        else {
            Log.d("MenuGallery", "refreshGallery()");
            // Instance Gallery Helper
            // Fetch item on director com.vigram.downloader
            // setup gridview with this viewpager
            gridView.invalidateViews();
            GalleryHelper.fetchGallery();
            gridView.setAdapter(GalleryHelper.getGalleryHelper());
            GalleryHelper.newInstance(context).notifyDataSetChanged();
            MainModels.getVigramProcess().setVisibility(View.GONE);


        }
    }
}
