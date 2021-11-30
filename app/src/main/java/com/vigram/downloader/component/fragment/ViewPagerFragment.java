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

import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.vigram.downloader.component.model.MainModels;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerFragment extends FragmentStatePagerAdapter {
    private static final List<Fragment> mFragmentList = new ArrayList<>();
    private static final List<String> mDashboardTitle = new ArrayList<>();
    private static ViewPagerFragment viewPagerFragment;
    private static TabLayout tabLayout;

    public ViewPagerFragment (FragmentManager fragmentManager, int behavior) {
        super(fragmentManager, behavior);
    }

    public static ViewPagerFragment newInstance(FragmentManager fragmentManager, int behavior) {
        viewPagerFragment = new ViewPagerFragment(fragmentManager, behavior);
        return viewPagerFragment;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mDashboardTitle.get(position);
    }

    @Override
    public int getCount() {
        return mDashboardTitle.size();
    }

    public static void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mDashboardTitle.add(title);

    }

    public static void setupViewPager(ViewPager viewPager, List<Fragment> fragments, List<String> tabTitles) {
        Log.d("ViewPagerFragment", "size: "+mFragmentList.size());
        if(mFragmentList.size() != 2) {
            addFragment(fragments.get(0), tabTitles.get(0));
            addFragment(fragments.get(1), tabTitles.get(1));
            viewPager.setAdapter(getViewPagerFragment());
            getTabLayout().setupWithViewPager(viewPager);
            getTabLayout().getTabAt(0).setIcon(FragmentHelper.getFragmentsIcon().get(0));
            getTabLayout().getTabAt(1).setIcon(FragmentHelper.getFragmentsIcon().get(1));
            MainModels.setTabAvailable(true);
        } else {
            viewPager.setAdapter(getViewPagerFragment());
            getTabLayout().setupWithViewPager(viewPager);
            getTabLayout().getTabAt(0).setIcon(FragmentHelper.getFragmentsIcon().get(0));
            getTabLayout().getTabAt(1).setIcon(FragmentHelper.getFragmentsIcon().get(1));
            MainModels.setTabAvailable(true);
        }


    }

    public static void refreshViewPager(ViewPager viewPager) {
        viewPager.setAdapter(getViewPagerFragment());
        getTabLayout().getTabAt(0).setIcon(FragmentHelper.getFragmentsIcon().get(0));
        getTabLayout().getTabAt(1).setIcon(FragmentHelper.getFragmentsIcon().get(1));
        MainModels.setTabAvailable(true);
    }

    public static ViewPagerFragment getViewPagerFragment() {
        return viewPagerFragment;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }

    public static TabLayout getTabLayout() {
        return tabLayout;
    }

    public static void setTabLayout(TabLayout tabLayout) {
        ViewPagerFragment.tabLayout = tabLayout;
    }
}
