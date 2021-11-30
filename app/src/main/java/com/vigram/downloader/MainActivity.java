/**
 * Class Name: com.vigram.downloader;
 * App Name: Vigram
 * Package: com.vigram.downloader
 * Created: 10:18 PM GMT+7 1/6/2020
 * Developer: Vigram.cc
 * By: Zuhrul Anam (zuhrul@vigram.cc)
 * Edited: 10:19 PM GMT+7 1/6/2020
 */

package com.vigram.downloader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.viewpager.widget.ViewPager;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;
import com.vigram.downloader.component.fragment.FragmentHelper;
import com.vigram.downloader.component.fragment.VigramHelper;
import com.vigram.downloader.component.gallery.GalleryHelper;
import com.vigram.downloader.component.mobileads.AdmobHelper;
import com.vigram.downloader.component.model.MainModels;
import com.vigram.downloader.component.fragment.ViewPagerFragment;
import com.vigram.downloader.component.network.VersionHelper;
import com.vigram.downloader.component.vigrambrowser.BrowserHelper;
import com.vigram.downloader.ui.MenuGallery;

public class MainActivity extends AppCompatActivity implements LifecycleOwner {
    private LifecycleRegistry lifecycleRegistry;
    private TabLayout mTabLayout;
    private static ViewPager viewPager;
    private LinearLayoutCompat menuSheet, menuSheet1, menuSheet2, menuSheet3;
    private ImageView imageMenuSheet, imageMenuSheet1,imageMenuSheet2, imageMenuSheet3;
    private ImageView closeButtonSheet;
    private BottomSheetDialog mBottomSheetDialog;
    private ImageView menuVigram;
    private Handler handler;
    private ProgressBar updateAppProggress;
    private boolean doubleExit = false;
    private int countExitBrowser = 0;
    // Admob Variable
    private AdView adView;
    private InterstitialAd interstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this, getString(R.string.mobileads_app_id));
        handler = new Handler();
        lifecycleRegistry = new LifecycleRegistry(this);
        lifecycleRegistry.setCurrentState(Lifecycle.State.CREATED);
        mTabLayout = findViewById(R.id.id_tab_layout);
        viewPager = findViewById(R.id.id_viewpager);
        menuVigram = findViewById(R.id.id_menu_vigram);
        adView = findViewById(R.id.banner_ads);
        updateAppProggress = findViewById(R.id.id_app_update_progress);
        ViewPagerFragment.setTabLayout(mTabLayout);
        ViewPagerFragment.newInstance(getSupportFragmentManager(), 0);
        if(!MainModels.isTabAvailable()) {
            Log.d(MainModels.getMainActivityTag(), "tab not available");
            ViewPagerFragment.setupViewPager(viewPager, FragmentHelper.getFragments(), FragmentHelper.getFragmentsTitle());
        }
        else {
            ViewPagerFragment.setupViewPager(viewPager, null, null);
        }

        if(!BuildConfig.BUILD_TYPE.contentEquals("developer")) {
            // If build variant is not developer this is default like debug or release
            AdmobHelper admobHelper = AdmobHelper.newInstance();
            admobHelper.setComponent(getString(R.string.mobileads_test_device), this);
            admobHelper.loadAdView(adView);
            // parameter loadInterstitial (Context context, String adUnit, String testDevice)
            admobHelper.loadInterstitial(this, getString(R.string.mobileads_interstitial), getString(R.string.mobileads_test_device));

        }
        createVigramMenu();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onStart() {
        super.onStart();
        lifecycleRegistry.setCurrentState(Lifecycle.State.STARTED);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case 100: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission read granted
                    Log.d(MainModels.getMainActivityTag(), "permission read allowed")  ;
                    if(MainModels.isTabAvailable()) {
                        Log.d(MainModels.getMenuDownloaderTag(), "handler menudownloader");
                        viewPager.setAdapter(ViewPagerFragment.newInstance(getSupportFragmentManager(), 0));
                        mTabLayout.getTabAt(0).setIcon(FragmentHelper.getFragmentsIcon().get(0));
                        mTabLayout.getTabAt(1).setIcon(FragmentHelper.getFragmentsIcon().get(1));
                    }


                } else {
                    Log.d("MainActivity", "not granted");

                }
                return;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        lifecycleRegistry.setCurrentState(Lifecycle.State.RESUMED);
        // Request read and write permission

    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.d(MainModels.getMainActivityTag(), "OnPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        lifecycleRegistry.setCurrentState(Lifecycle.State.DESTROYED);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(MainModels.getMainActivityTag(), "onRestart");
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return super.getLifecycle();
    }

    @Override
    public void onBackPressed() {
        if(MainModels.getWebView().canGoBack()) {
            BrowserHelper.goBack();
        }
        else {
            mTabLayout.getTabAt(0).setText(FragmentHelper.getFragmentsTitle().get(0));
            mTabLayout.getTabAt(0).setIcon(FragmentHelper.getFragmentsIcon().get(0));
            MainModels.getWebView().setVisibility(View.GONE);
            MainModels.getNestedScrollView().setVisibility(View.VISIBLE);
            MainModels.getTagsCheckBoxLayout().setVisibility(View.VISIBLE);
            if(doubleExit) {
                super.onBackPressed();
            }
            doubleExit = true;
            Toast.makeText(this, "Double tap to exit :)", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(() -> doubleExit = false, 2000);
        }
    }

    public static ViewPager getViewPager() {
        return viewPager;
    }

    private void createVigramMenu() {
        Log.d(MainModels.getMainActivityTag(), "createVigramMenu");
        View mView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_drawer, null);
        menuSheet = mView.findViewById(R.id.id_menu_item_1);
        menuSheet1 = mView.findViewById(R.id.id_menu_item_2);
        menuSheet2 = mView.findViewById(R.id.id_menu_item_3);
        menuSheet3 = mView.findViewById(R.id.id_menu_item_4);
        imageMenuSheet = mView.findViewById(R.id.image_menu_sheet);
        imageMenuSheet1 = mView.findViewById(R.id.image_menu_sheet_1);
        imageMenuSheet2 = mView.findViewById(R.id.image_menu_sheet_2);
        imageMenuSheet3 = mView.findViewById(R.id.image_menu_sheet_3);
        Glide.with(this).load(R.drawable.baseline_info_24).into(imageMenuSheet);
        Glide.with(this).load(R.drawable.baseline_sentiment_satisfied_alt_24).into(imageMenuSheet1);
        Glide.with(this).load(R.drawable.baseline_cloud_done_24).into(imageMenuSheet2);
        Glide.with(this).load(R.drawable.baseline_exit_to_app_24).into(imageMenuSheet3);
        closeButtonSheet = mView.findViewById(R.id.id_close_button);
        menuSheet.setOnClickListener(bottomSheetListener);
        menuSheet1.setOnClickListener(bottomSheetListener);
        menuSheet2.setOnClickListener(bottomSheetListener);
        menuSheet3.setOnClickListener(bottomSheetListener);
        // set rounded bottom sheet with transparent drawable
        closeButtonSheet.setOnClickListener(bottomSheetListener);
        if (mBottomSheetDialog == null) {

            mBottomSheetDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
            mBottomSheetDialog.setContentView(mView);
            menuVigram.setOnClickListener(v -> {
                mBottomSheetDialog.show();
            });

        }
    }

    private View.OnClickListener bottomSheetListener = v -> {
        switch (v.getId()) {
            case R.id.id_menu_item_1:
               loadAbout();
                break;
            case R.id.id_menu_item_2:
                loadPrivacyPolicy();
                break;
            case R.id.id_menu_item_3:
                // Test latest version from your server
                // upload vigram.json to your server then put it here
                VersionHelper.newInstance().execute("https://auth.toxbooster.co/vigram.json");
                updateAppProggress.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        updateAppProggress.setVisibility(View.GONE);
                        if(MainModels.getVersionFromServer() != null) {
                            String currentVersion = BuildConfig.VERSION_NAME;
                            String versionServer = VersionHelper.getVersionServer(MainModels.getVersionFromServer());
                            if(currentVersion.equals(versionServer)) {
                                Toast.makeText(getApplicationContext(), "Yay, your app is up to date :)", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Update Available", Toast.LENGTH_SHORT).show();
                                rateVigram();
                            }
                        }
                    }
                }, 4000);

                break;
            case R.id.id_menu_item_4:
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mBottomSheetDialog.dismiss();
                    finishAndRemoveTask();
                }
                else {
                    mBottomSheetDialog.dismiss();
                    finish();
                }
                break;
            case R.id.id_close_button:
                mBottomSheetDialog.dismiss();
                break;
        }
    };

    // privacy policy

    private void loadPrivacyPolicy() {
        if(MainModels.getWebView() != null) {
            MainModels.getNestedScrollView().setVisibility(View.GONE);
            MainModels.getTagsCheckBoxLayout().setVisibility(View.GONE);
            MainModels.getWebView().setVisibility(View.VISIBLE);
            BrowserHelper.loadUrl(MainModels.getWebView(), "file:///android_asset/privacy-policy.html");
            mTabLayout.getTabAt(0).setText("PRIVACY POLICY");
            mTabLayout.getTabAt(0).setIcon(R.drawable.baseline_sentiment_satisfied_alt_24);
            mBottomSheetDialog.dismiss();

        }
    }

    // load about us

    private void loadAbout() {
        if(MainModels.getWebView() != null) {
            MainModels.getNestedScrollView().setVisibility(View.GONE);
            MainModels.getTagsCheckBoxLayout().setVisibility(View.GONE);
            MainModels.getWebView().setVisibility(View.VISIBLE);
            BrowserHelper.loadUrl(MainModels.getWebView(), "file:///android_asset/privacy-policy.html");
            mTabLayout.getTabAt(0).setText("ABOUT US");
            mTabLayout.getTabAt(0).setIcon(R.drawable.baseline_info_24);
            mBottomSheetDialog.dismiss();

        }

    }

    // rate vigram

    private void rateVigram() {
        VigramHelper.rateVigram(this);
    }


}
