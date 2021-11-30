/**
 * Class Name: com.vigram.downloader.component.fragment;
 * App Name: Vigram
 * Package: com.vigram.downloader
 * Created: 10:18 PM GMT+7 1/6/2020
 * Developer: Vigram.cc
 * By: Zuhrul Anam (zuhrul@vigram.cc)
 * Edited: 10:19 PM GMT+7 1/6/2020
 */
package com.vigram.downloader.component.fragment;

import androidx.fragment.app.Fragment;

import com.vigram.downloader.R;
import com.vigram.downloader.ui.MenuDownloader;
import com.vigram.downloader.ui.MenuGallery;

import java.util.ArrayList;
import java.util.List;

public class FragmentHelper {
    private static List<Fragment> fragments;
    private static List<String> fragmentsTitle;
    private static List<Integer> fragmentsIcon;

    public static List<Fragment> getFragments() {
        fragments = new ArrayList<>();
        fragments.add(MenuDownloader.newInstance());
        fragments.add(MenuGallery.newInstance());
        return fragments;
    }

    public static List<String> getFragmentsTitle() {
        fragmentsTitle = new ArrayList<>();
        fragmentsTitle.add("DOWNLOADER");
        fragmentsTitle.add("GALLERY");
        return fragmentsTitle;
    }

    public static List<Integer> getFragmentsIcon() {
        fragmentsIcon = new ArrayList<>();
        fragmentsIcon.add(R.drawable.baseline_cloud_download_24);
        fragmentsIcon.add(R.drawable.baseline_grid_on_24);
        return fragmentsIcon;
    }
}
