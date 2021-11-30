/**
 * Class Name: com.vigram.downloader.component.gallery;
 * App Name: Vigram
 * Package: com.vigram.downloader
 * Created: 10:18 PM GMT+7 1/6/2020
 * Developer: Vigram.cc
 * By: Zuhrul Anam (zuhrul@vigram.cc)
 * Edited: 10:19 PM GMT+7 1/6/2020
 */


package com.vigram.downloader.component.gallery;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.vigram.downloader.R;
import com.vigram.downloader.component.model.MainModels;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GalleryHelper extends BaseAdapter {

    private static List<String> galleryImageSource = new ArrayList<>();
    private static List<String> galleryImagesVideos = new ArrayList<>();
    private static GalleryHelper galleryHelper;
    private LayoutInflater layoutInflater;
    private ImageView roundedImageView;
    private vHolder vHolder;
    private View cView;
    private Context mContext;

    public GalleryHelper (Context context) {
        mContext = context;
    }

    public static GalleryHelper newInstance(Context context) {
        galleryHelper = new GalleryHelper(context);
        return galleryHelper;
    }

    @Override
    public int getCount() {
        return galleryImageSource.size();
    }

    @Override
    public Object getItem(int position) {
        return galleryImageSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.gallery_layout, null);
            roundedImageView = convertView.findViewById(R.id.gallery_img);
            roundedImageView.getLayoutParams().height = PixelHelper.getPx(180, mContext);
            roundedImageView.getLayoutParams().width = PixelHelper.getPx(180, mContext);
            vHolder = new vHolder(roundedImageView);
            convertView.setTag(vHolder);
        }

        //Glide.with(context).load(storyImageSource.get(position)).into(vHolder.storyImg);
        vHolder viewHolder = (vHolder) convertView.getTag();
        Glide.with(mContext).load(galleryImageSource.get(position)).into(viewHolder.storyImg);

        return convertView;
    }

    public static void addSource(String source) {
        galleryImageSource.add(source);
    }

    public static void clear() {
        galleryImageSource.clear();
    }

    public static void notifyData() {
        getGalleryHelper().notifyDataSetChanged();
    }




    private class vHolder {
        private ImageView storyImg;

        public vHolder(ImageView roundedImageView) {
            this.storyImg = roundedImageView;
        }

    }

    private static Comparator<File> filterDate = (o1, o2) -> Long.compare(o1.lastModified(), o2.lastModified());


    public static void fetchGallery() {
        Log.d("GalleryHelper", "fetch gallery");
        File file = new File("/storage/emulated/0/com.vigram.downloader");
        File[] files = file.listFiles();
        if(files != null) {
            GalleryHelper.clear();
            Log.d(MainModels.getGalleryHelperTag(), "image size:"+galleryImageSource.size());
            Arrays.sort(files, filterDate);
            for (File theFile : files) {
                Log.d("GalleryHelper", theFile.getAbsolutePath());
                if (theFile.getAbsolutePath().endsWith(".mp4") || theFile.getAbsolutePath().endsWith(".jpg")) {
                    if(!galleryImageSource.contains(theFile.getAbsolutePath())) {
                        GalleryHelper.addSource(theFile.getAbsolutePath());
                    }
                }
            }

            Collections.reverse(galleryImageSource);


        }
    }


    public static GalleryHelper getGalleryHelper() {
        return galleryHelper;
    }



}
