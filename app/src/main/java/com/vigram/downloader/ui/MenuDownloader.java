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
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.vigram.downloader.BuildConfig;
import com.vigram.downloader.R;
import com.vigram.downloader.component.gallery.PermissionsHelper;
import com.vigram.downloader.component.library.ClipboardHelper;
import com.vigram.downloader.component.library.HashtagsHelper;
import com.vigram.downloader.component.mobileads.AdmobHelper;
import com.vigram.downloader.component.model.MainModels;
import com.vigram.downloader.component.network.MediaDownloader;
import com.vigram.downloader.component.network.NetworkCheck;
import com.vigram.downloader.component.fragment.VigramHelper;

import org.json.JSONException;

public class MenuDownloader extends Fragment implements LifecycleOwner {
    // TODO: Rename parameter arguments, choose names that match
    private LifecycleRegistry lifecycleRegistry;
    private Button btn_preview, btn_save, btn_next;
    private CheckBox checkbox_amateur, checkbox_random;
    private static String mHashTag;
    private static ImageView imageFetched;
    private WebView webView;
    private VigramHelper vigramHelper;
    private static ProgressBar vigram_progress;
    private MediaDownloader mediaDownloader;
    private static String tempJson;
    private static String media_url;
    private static String postDesription, postUsername;
    private static EditText media_url_input;
    private static boolean vigramVideo;
    private NestedScrollView scrollView;
    private Handler handler;
    private ImageView btn_clear_text;
    private LinearLayoutCompat previewImageVideoLayout;
    private Context context;
    private LinearLayoutCompat tagsCheckboxLayout;
    private int counterSave = 0;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    public MenuDownloader() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MenuDownloader.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuDownloader newInstance() {
        MenuDownloader fragment = new MenuDownloader();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycleRegistry = new LifecycleRegistry(this);
        lifecycleRegistry.setCurrentState(Lifecycle.State.CREATED);
        vigramHelper = VigramHelper.newInstance(getContext());
        handler = new Handler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_menu_downloader, container, false);
        context = getContext();
        btn_preview = root.findViewById(R.id.id_button_preview);
        btn_save = root.findViewById(R.id.id_button_save_image);
        imageFetched = root.findViewById(R.id.image_fetched);
        media_url_input = root.findViewById(R.id.id_media_server_input);
        vigram_progress = root.findViewById(R.id.id_progress_downloading);
        scrollView = root.findViewById(R.id.id_scroll_view);
        btn_clear_text = root.findViewById(R.id.id_btn_clear_text);
        previewImageVideoLayout = root.findViewById(R.id.id_preview_layout);
        webView = root.findViewById(R.id.webview_layout);
        checkbox_random = root.findViewById(R.id.id_random);
        tagsCheckboxLayout = root.findViewById(R.id.id_tags_type);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        lifecycleRegistry.setCurrentState(Lifecycle.State.STARTED);
        btn_preview.setOnClickListener(onClickListener);
        btn_save.setOnClickListener(onClickListener);
        media_url_input.addTextChangedListener(textWatcher);
        media_url_input.setOnFocusChangeListener(changeListener);
        media_url_input.setOnClickListener(onClickListener);
        btn_clear_text.setOnClickListener(onClickListener);
        Glide.with(getContext()).load(R.drawable.preview_image).into(imageFetched);

    }



    @Override
    public void onResume() {
        super.onResume();
        lifecycleRegistry.setCurrentState(Lifecycle.State.RESUMED);
        MainModels.setVigramProcess(vigram_progress);
        MainModels.setBntClearText(btn_clear_text);
        MainModels.setNestedScrollView(scrollView);
        MainModels.setPreviewImageVideoLayout(previewImageVideoLayout);
        MainModels.setWebView(webView);
        MainModels.getNestedScrollView().setVisibility(View.VISIBLE);
        MainModels.setTagsCheckBoxLayout(tagsCheckboxLayout);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        lifecycleRegistry.setCurrentState(Lifecycle.State.DESTROYED);
    }


    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return super.getLifecycle();
    }

    private View.OnClickListener onClickListener = v -> {
        switch (v.getId()) {
            case R.id.id_button_preview:
                if(PermissionsHelper.newInstance().checkAndRequestPermissions(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                    if(checkbox_random.isChecked()) {
                        mHashTag = HashtagsHelper.getRandomTags().get(HashtagsHelper.rotateTags(0, HashtagsHelper.getRandomTags().size() - 1));
                    }
                    preview();
                }


                break;

            case R.id.id_button_save_image:

                if(!BuildConfig.BUILD_TYPE.contentEquals("developer")) {
                    // counter save
                    if (counterSave == 2) {
                        // Load Interstitial ads when user save two times
                        AdmobHelper admobHelper = AdmobHelper.newInstance();
                        admobHelper.loadInterstitial(context, getString(R.string.mobileads_interstitial), getString(R.string.mobileads_test_device));
                        counterSave = 0;
                    }
                    // counter saver increase
                    else {
                        if (PermissionsHelper.newInstance().checkAndRequestPermissions(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                            if (checkbox_random.isChecked()) {
                                mHashTag = HashtagsHelper.getRandomTags().get(HashtagsHelper.rotateTags(0, HashtagsHelper.getRandomTags().size() - 1));
                            }
                            saveMedia();
                            counterSave++;
                        }
                    }

                }

                else {
                    if (PermissionsHelper.newInstance().checkAndRequestPermissions(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        if (checkbox_amateur.isChecked()) {
                            checkbox_random.setEnabled(false);
                            mHashTag = HashtagsHelper.getAmateursTags().get(HashtagsHelper.rotateTags(0, HashtagsHelper.getAmateursTags().size() - 1));
                        }
                        if (checkbox_random.isChecked()) {
                            checkbox_amateur.setEnabled(false);
                            mHashTag = HashtagsHelper.getRandomTags().get(HashtagsHelper.rotateTags(0, HashtagsHelper.getRandomTags().size() - 1));
                        }
                        saveMedia();
                    }
                }


                break;

            case R.id.url_input_layout:
                Log.d(MainModels.getMenuDownloaderTag(), "keyboard show");
                break;

            case R.id.id_btn_clear_text:
                if(media_url_input.getText().toString() != null) {
                    media_url_input.getText().clear();
                    btn_clear_text.setVisibility(View.GONE);
                    media_url_input.setEms(15);
                    previewImageVideoLayout.setVisibility(View.VISIBLE);
                    Glide.with(getContext()).load(R.drawable.preview_image).into(imageFetched);
                }
                break;


        }
    };

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
            media_url_input.setEms(14);
            btn_clear_text.setVisibility(View.VISIBLE);
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.d(MainModels.getMenuDownloaderTag(), "onTextChanged");
            media_url_input.setEms(14);
            scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
            btn_clear_text.setVisibility(View.VISIBLE);

        }

        @Override
        public void afterTextChanged(Editable s) {
            media_url_input.setEms(14);
            scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
            if(media_url_input.getText().toString().length() > 0) {
                btn_clear_text.setVisibility(View.VISIBLE);
                MainModels.setUrlInstagram(true);
            }

            else {
                btn_clear_text.setVisibility(View.GONE);
                MainModels.setUrlInstagram(false);
            }

        }

    };

    private View.OnFocusChangeListener changeListener = (v, hasFocus) -> {

    };

    public static void setTempJson(String tempJson) {
        MenuDownloader.tempJson = tempJson;
    }

    public static void setMedia_url(String media_url) {
        MenuDownloader.media_url = media_url;
    }

    public static ImageView getImageFetched() {
        return imageFetched;
    }

    public static EditText getMedia_url_input() {
        return media_url_input;
    }

    private void previewImageVideo(String mediaUrl) {
        // check if url is instagram
        if(mediaUrl.startsWith("https://www.instagram.com")) {
            MediaDownloader.setMediaPageId(mediaUrl);
            VigramHelper.fetchImageVideos(mediaUrl);
            handler.postDelayed(() -> {
                try {
                    if(tempJson != null) {
                        VigramHelper.glideMedia(tempJson);
                    }
                    else {
                        // data from server null
                        media_url_input.getText().clear();
                        vigram_progress.setVisibility(View.GONE);
                        media_url_input.setEms(15);
                        previewImageVideoLayout.setVisibility(View.VISIBLE);
                        Glide.with(getContext()).load(R.drawable.preview_image).into(imageFetched);
                        Toast.makeText(getContext(), "Network Error or Page is Private :(", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, 4000);
        }
    }

    private void saveImageVideo() {
        MainModels.getVigramProcess().setVisibility(View.VISIBLE);
        if(media_url != null) {
            Log.d(MainModels.getMenuDownloaderTag(), "media_url available");
            mediaDownloader = MediaDownloader.newInstance(getContext(), getActivity());
            mediaDownloader.setVigramVideo(vigramVideo);
            mediaDownloader.execute(media_url);
            // copie post description and username
            new Handler().postDelayed(() -> {
                String desc = "";
                if(postDesription != null && postUsername != null) {
                    if(!postDesription.contains("#")) {
                        desc = "Caption: "+ HashtagsHelper.getCaption()[HashtagsHelper.rotateTags(0, HashtagsHelper.getCaption().length - 1)] + "\n\n" + postDesription + "\n\n" + mHashTag + "\n\n" +"Source: "+postUsername;
                    }
                    else {
                        desc = "Caption: "+ HashtagsHelper.getCaption()[HashtagsHelper.rotateTags(0, HashtagsHelper.getCaption().length - 1)] + "\n\n" + postDesription + "\n\n" +"Source: "+postUsername;
                    }
                    ClipboardHelper.setClipboard(getContext(), desc);
                }
            }, 5000);



        }
        else {
            // data from server null
            media_url_input.getText().clear();
            btn_clear_text.setVisibility(View.GONE);
            vigram_progress.setVisibility(View.GONE);
            media_url_input.setEms(15);
            previewImageVideoLayout.setVisibility(View.VISIBLE);
            Glide.with(getContext()).load(R.drawable.preview_image).into(imageFetched);
            Toast.makeText(getContext(), "Network Error or Page is Private :(", Toast.LENGTH_LONG).show();


        }

    }

    public static void setVigramVideo(boolean vigramVideo) {
        MenuDownloader.vigramVideo = vigramVideo;
    }

    // preview image and photo

    private void preview() {
        if(NetworkCheck.Online(getContext())) {
            String mediaUrl = media_url_input.getText().toString();

            if(!mediaUrl.equals("")) {
                vigram_progress.setVisibility(View.VISIBLE);
                previewImageVideo(mediaUrl);
            }
            else {
                Toast.makeText(getContext(), "Please copy and paste correct link first!", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(getContext(), "You are offline :(", Toast.LENGTH_SHORT).show();
        }
    }

    // save image

    private void saveMedia() {
        if(NetworkCheck.Online(getContext())) {
            if(!media_url_input.getText().toString().equals("")) {
                // if urlinput not null
                saveImageVideo();

            } else {
                Toast.makeText(getContext(), "Please copy and paste correct link first!", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(getContext(), "You are offline :(", Toast.LENGTH_SHORT).show();
        }
    }

    public static void setPostDesription(String postDesription) {
        MenuDownloader.postDesription = postDesription;
    }

    public static void setPostUsername(String postUsername) {
        MenuDownloader.postUsername = postUsername;
    }

    public static String getmHashTag() {
        return mHashTag;
    }
}
