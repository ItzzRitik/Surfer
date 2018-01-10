package xtremecreations.surfer;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Handler;
import android.os.Vibrator;
import android.speech.RecognizerIntent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions;
import android.webkit.SslErrorHandler;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.skyfishjy.library.RippleBackground;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Home extends AppCompatActivity {
    SharedPreferences ad_preferences,settings;
    SharedPreferences.Editor ad_editor,settings_editor;
    RippleBackground rippleBackground_small;
    xtremecreations.surfer.NestedWebView surf;
    SurfaceView cameraView;
    BarcodeDetector barcode;
    Button allow_camera;
    xtremecreations.surfer.CameraSource cameraSource;
    xtremecreations.surfer.CameraSource.Builder cameraBuild;
    SurfaceHolder holder;
    EditText addbar;
    ProgressBar cloader, loader;
    RelativeLayout addpane, voicepane, menupaneback, backoverlay, tabpane, listpane, menuback, fabCover;
    RelativeLayout fade_away, scanner,scan_results,permission_camera,adblock_small;
    private android.widget.RelativeLayout.LayoutParams fabParams;
    LinearLayout basepane, menuhold;
    Animation animation;
    TextView tittle,scan_text,ads_total_small;
    ImageView fevicon, voicecommandin, voicecommandout, back, forward, tabs, home, mainTab,flash,share_scan,copy_scan,camera_rotate;
    ImageView backfloat, option41, option43,ad_shield_small;
    android.support.design.widget.FloatingActionButton fab, option11, option12, option13, option14, option21, option22, option23, option24;
    android.support.design.widget.FloatingActionButton option31, option32, option33, option34, option42,ad_settings,ad_pause,ad_power;
    android.support.v7.widget.CardView mainPanel, mainTabH, menupane, addhover, addhoverTop, search_engine, engine, search_type, type_search, search_no, suggestion;
    android.support.design.widget.AppBarLayout appBar;
    io.codetail.widget.RevealLinearLayout addpanefloat;
    ListView searchlist;
    Rect menu_rect,menu_reload,menu_back,menu_forward;
    int REQUEST_CODE = 1,qr=0,flash_on=0, addhoveractive = 0, menuLog = 0, cpro = 0, reReady = 0, menuUp = 0, justfabUp = 0, tabOpen = 0,tab_act=0;
    int on_engine = 0, on_type = 0, on_suggestion = 0,fullscreen=0,adblock=0,ad_paused=0,speedmode=0,vibrate=0,ad_bl_small=0,ad__button=0;
    int me_back=0,me_back_over=0,me_reload_over=0,me_for=0,me_for_over=0;
    int its_loading=0,its_out =0;
    boolean isLongPress = true;
    String Homepage = "https://www.google.com/";
    String[] Null = {},whitelist;
    Toolbar toolbar;
    PackageInfo pinfo;
    View divider1,decorView;
    @Override
    public void onBackPressed()
    {
        if (addhoveractive == 1){hideAddHover();}
        else if(qr==1){qr=0;
            int cx = (option33.getLeft() + option33.getRight()) / 2;
            int cy = option13.getHeight()/4+(menuback.getTop()+menuback.getBottom())/2+(option33.getTop()+option33.getBottom())/2;
            Animator anim = io.codetail.animation.ViewAnimationUtils.createCircularReveal(scanner,cx, cy,fabCover.getWidth() * 4/5, option33.getWidth() / 2);
            anim.setInterpolator(new DecelerateInterpolator());
            anim.setDuration(200);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {scanner.setVisibility(View.GONE);}
            });
            anim.start();cameraView.setVisibility(View.GONE);scan_results.setVisibility(View.GONE);
        }
        else if(ad_bl_small==1){
            int cx = (option13.getLeft() + option13.getRight()) / 2;ad_bl_small=0;ad__button=0;
            int cy = ((menuback.getTop()+menuback.getBottom())/2)-((option13.getTop()+option13.getBottom())/2)-(option13.getHeight());
            Animator anim = io.codetail.animation.ViewAnimationUtils.createCircularReveal(adblock_small,cx+13, cy-5, fabCover.getWidth() * 4/5,option13.getWidth() / 2);
            anim.setInterpolator(new AccelerateInterpolator());
            anim.setDuration(200);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {rippleBackground_small.stopRippleAnimation();
                    adblock_small.setVisibility(View.GONE);}});
            anim.start();
        }
        else if (menuUp == 1) {option42.performClick();}
        else if (tabOpen == 1) {closeTabs();}
        else {if (surf.canGoBack()) {surf.goBack();}else{exit();}}
    }
    @Override
    public void onResume()
    {
        super.onResume();resume();
    }
    public void resume(){
        adblock=ad_preferences.getInt("status",0);fullscreen=settings.getInt("fullscreen",0);
        vibrate=settings.getInt("vibrate",0);ads_total_small.setText(ad_preferences.getInt("total",0)+"");
        if (fullscreen==1)
        {
            option12.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.fullscreen)));
            option12.setImageDrawable(ContextCompat.getDrawable(Home.this, R.drawable.fullscreen_toggle));
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
        else
        {
            option12.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.menucolor)));
            option12.setImageDrawable(ContextCompat.getDrawable(Home.this, R.drawable.fullscreen));
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }

        if(adblock==1){
            option13.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.adblock)));
            ad_settings.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#43a3ed")));
            ad_pause.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#43a3ed")));
            ad_power.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#43a3ed")));

            option13.setImageDrawable(ContextCompat.getDrawable(Home.this, R.drawable.ad_block_toggle));
            ad_shield_small.setImageDrawable(ContextCompat.getDrawable(Home.this, R.drawable.shield));
        }
        else{
            option13.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.menucolor)));
            ad_settings.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#8e8e8e")));
            ad_pause.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#8e8e8e")));
            ad_power.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#8e8e8e")));

            option13.setImageDrawable(ContextCompat.getDrawable(Home.this, R.drawable.ad_block));
            ad_shield_small.setImageDrawable(ContextCompat.getDrawable(Home.this, R.drawable.shield_disabled));
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AdBlocker.init(Home.this);
        ad_preferences = getApplicationContext().getSharedPreferences("adblock", 0);ad_editor = ad_preferences.edit();
        settings = getApplicationContext().getSharedPreferences("settings", 0);settings_editor = settings.edit();
        settings_editor.putInt("vibrate",1);settings_editor.commit();
        decorView = getWindow().getDecorView();
        try{resume();}catch(Exception e){e.printStackTrace();}

        if (Intent.ACTION_VIEW.equals(getIntent().getAction())) {
            Homepage = getIntent().getData().toString();
        }
        setContentView(R.layout.activity_home);
        try {
            pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (Exception e) {
        }
        toolbar = (Toolbar) findViewById(R.id.addressbar);
        setSupportActionBar(toolbar);
        appBar = (android.support.design.widget.AppBarLayout) findViewById(R.id.appBar);
        voicecommandout = (ImageView) findViewById(R.id.voicecommandout);
        menuhold = (LinearLayout) findViewById(R.id.menuhold);
        basepane = (LinearLayout) findViewById(R.id.basepane);
        adblock_small= (RelativeLayout) findViewById(R.id.adblock_small);
        listpane = (RelativeLayout) findViewById(R.id.listpane);
        searchlist = (ListView) findViewById(R.id.searchlist);
        tittle = (TextView) findViewById(R.id.Tittle);
        cloader = (ProgressBar) findViewById(R.id.cloader);
        mainPanel = (android.support.v7.widget.CardView) findViewById(R.id.mainPanel);
        divider1 = findViewById(R.id.divider1);
        divider1.setVisibility(View.GONE);
        backoverlay = (RelativeLayout) findViewById(R.id.backoverlay);
        backoverlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAddHover();
            }
        });
        backfloat = (ImageView) findViewById(R.id.backfloat);
        backfloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAddHover();
            }
        });

        addbar = (EditText) findViewById(R.id.addbar);
        addbar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    final String url = addbar.getText().toString();
                    hideAddHover();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (!addbar.getText().toString().equals("")) {
                                surf.loadUrl(getURL(url));
                            }
                        }
                    }, 250);
                    return true;
                }
                return false;
            }
        });
        addbar.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (addbar.getText().toString().matches("")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            searchlist.setAdapter(new CustomAdapter(Home.this, Null));
                                        } catch (Exception e) {
                                        }
                                    }
                                });
                            } else {
                                String srctxt = addbar.getText().toString();
                                if (!(srctxt.startsWith("https") || srctxt.startsWith("http")) && !Patterns.WEB_URL.matcher(srctxt).matches()) {
                                    URL u = new URL("http://suggestqueries.google.com/complete/search?client=firefox&q=" + (addbar.getText()).toString());
                                    HttpURLConnection c = (HttpURLConnection) u.openConnection();
                                    c.setRequestMethod("GET");
                                    c.connect();
                                    InputStream in = c.getInputStream();
                                    final ByteArrayOutputStream bo = new ByteArrayOutputStream();
                                    byte[] buffer = new byte[1024];
                                    in.read(buffer);
                                    bo.write(buffer);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                String token = bo.toString();
                                                bo.close();
                                                token = token.substring(token.indexOf("\"", token.indexOf("\"", token.indexOf("\"") + 1) + 1) + 1, token.lastIndexOf("\""));
                                                int size = 5;
                                                String[] results = new String[size];
                                                if (token.split("\",\"").length >= size) {
                                                    for (int i = 0; i < size; i++) {
                                                        results[i] = token.split("\",\"")[i];
                                                    }
                                                } else {
                                                    for (int i = 0; i < token.split("\",\"").length; i++) {
                                                        results[i] = token.split("\",\"")[i];
                                                    }
                                                }
                                                if (results[0].startsWith("[\"")) {
                                                    searchlist.setAdapter(new CustomAdapter(Home.this, Null));
                                                } else {
                                                    searchlist.setAdapter(new CustomAdapter(Home.this, results));
                                                }
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            } catch (StringIndexOutOfBoundsException e) {
                                                searchlist.setAdapter(new CustomAdapter(Home.this, Null));
                                            }
                                        }
                                    });
                                }
                            }
                        } catch (MalformedURLException e) {
                        } catch (IOException e) {
                        }
                    }
                }).start();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        addpane = (RelativeLayout) findViewById(R.id.addbarpane);
        addpane.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        tab_act=(int)event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if((int)event.getY()>tab_act+10) {openTabs();}
                        break;
                    case MotionEvent.ACTION_UP:
                        showAddHover();break;
                }
                return true;
            }
        });
        fevicon = (ImageView) findViewById(R.id.fevicon);
        voicepane = (RelativeLayout) findViewById(R.id.voicepane);
        voicepane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak Now");
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, REQUEST_CODE);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        voicecommandin = (ImageView) findViewById(R.id.voicecommandin);
        voicecommandin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak Now");
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, REQUEST_CODE);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        fabCover = (RelativeLayout) findViewById(R.id.fabCover);

        fab = (FloatingActionButton) findViewById(R.id.fab);fab.setStateListAnimator(null);
        fab.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        menu_rect = new Rect(v.getLeft(), v.getTop(),v.getRight(), v.getBottom());
                        menu_reload = new Rect(v.getLeft(), v.getTop()-v.getHeight(),v.getRight(), v.getTop());
                        menu_back = new Rect(v.getLeft()-v.getWidth(), v.getTop(),v.getLeft(), v.getBottom());
                        menu_forward = new Rect(v.getRight(), v.getTop(),v.getRight()+v.getWidth(), v.getBottom());
                        menuLog = 1;isLongPress=true;its_out=0;me_back_over=0;me_for_over=0;its_loading=0;
                        new Handler().postDelayed(new Runnable() {@Override
                            public void run() {if (isLongPress) {surf.loadUrl(Homepage);justfabUp=1;}}
                        }, 700);break;
                    case MotionEvent.ACTION_MOVE:
                        if(!menu_rect.contains(v.getLeft() + (int) event.getX(), v.getTop() + (int) event.getY())){its_out=1;}
                        else{its_out=0;}
                        tittle.setText( reReady+" , "+me_back+" , "+me_for+" , "+me_back_over+" , "+its_loading+" , "+me_back_over+" , "+me_for_over);
                        if(menu_reload.contains(v.getLeft() + (int) event.getX(), v.getTop() + (int) event.getY())&& its_loading==0 && reReady == 0 && me_back==0 && me_for==0 && me_back_over==0&& me_for_over==0)
                        {
                            animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.float_show);
                            isLongPress=false;fab.startAnimation(animation);
                            new Handler().postDelayed(new Runnable() {@Override public void run()
                            {fabCover.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.float_reload));}},1);
                            justfabUp = 1;reReady = 1;cloader.setVisibility(View.GONE);fab.setImageResource(R.drawable.reload);
                        }
                        if(menu_rect.contains(v.getLeft() + (int) event.getX(), v.getTop() + (int) event.getY())&&its_loading == 0 && reReady==1 && me_back==0 &&me_for==0&& me_back_over==0&& me_for_over==0)
                        {
                            reReady = 0;me_reload_over=1;
                            fabCover.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.float_reload_over));
                            new Handler().postDelayed(new Runnable() {@Override public void run(){
                                animation =AnimationUtils.loadAnimation(getApplicationContext(), R.anim.float_hide);
                                fab.startAnimation(animation);
                                animation.setAnimationListener(new Animation.AnimationListener() {
                                    @Override public void onAnimationStart(Animation animation) {}
                                    @Override public void onAnimationEnd(Animation animation) {me_reload_over=0;}
                                    @Override public void onAnimationRepeat(Animation animation) {}
                                });
                                cloader.setVisibility(View.GONE);fab.setImageResource(R.drawable.menu);
                            }},40);
                        }

                        if(menu_back.contains(v.getLeft() + (int) event.getX(), v.getTop() + (int) event.getY()) && me_back==0 && reReady==0 && me_for==0&& me_reload_over==0&& me_for_over==0)
                        {
                            animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.float_show);
                            justfabUp=1;isLongPress=false;fab.startAnimation(animation);
                            new Handler().postDelayed(new Runnable() {@Override public void run()
                            {fabCover.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.float_back));}},1);
                            me_back=1;cloader.setVisibility(View.GONE);
                            if(surf.canGoBack()){fab.setImageResource(R.drawable.back_nav);}
                            else{fab.setImageResource(R.drawable.back_nav_disabled);}
                        }
                        if(menu_rect.contains(v.getLeft() + (int) event.getX(), v.getTop() + (int) event.getY()) && me_back==1 && reReady==0 &&me_for==0&& me_reload_over==0 && me_for_over==0)
                        {
                            me_back=0;me_back_over=1;
                            fabCover.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.float_back_over));
                            new Handler().postDelayed(new Runnable() {@Override public void run(){
                                animation =AnimationUtils.loadAnimation(getApplicationContext(), R.anim.float_hide);
                                fab.startAnimation(animation);
                                animation.setAnimationListener(new Animation.AnimationListener() {
                                    @Override public void onAnimationStart(Animation animation) {}
                                    @Override public void onAnimationEnd(Animation animation) {me_back_over=0;}
                                    @Override public void onAnimationRepeat(Animation animation) {}
                                });
                                cloader.setVisibility(View.GONE);fab.setImageResource(R.drawable.menu);
                            }},40);
                        }

                        if(menu_forward.contains(v.getLeft() + (int) event.getX(), v.getTop() + (int) event.getY()) &&me_for==0 && me_back==0 && reReady==0 && me_reload_over==0 && me_for_over==0)
                        {
                            animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.float_show);
                            justfabUp=1;isLongPress=false;fab.startAnimation(animation);
                            new Handler().postDelayed(new Runnable() {@Override public void run()
                            {fabCover.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.float_for));}},1);
                            me_for=1;cloader.setVisibility(View.GONE);
                            if(surf.canGoForward()){fab.setImageResource(R.drawable.forward_nav);}
                            else{fab.setImageResource(R.drawable.forward_nav_disabled);}
                        }
                        if(menu_rect.contains(v.getLeft() + (int) event.getX(), v.getTop() + (int) event.getY())&& me_for==1 && me_back==0 && reReady==0 && me_reload_over==0&& me_for_over==0)
                        {
                            me_for=0;me_for_over=1;
                            fabCover.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.float_for_over));
                            new Handler().postDelayed(new Runnable() {@Override public void run(){
                                animation =AnimationUtils.loadAnimation(getApplicationContext(), R.anim.float_hide);
                                fab.startAnimation(animation);
                                animation.setAnimationListener(new Animation.AnimationListener() {
                                    @Override public void onAnimationStart(Animation animation) {}
                                    @Override public void onAnimationEnd(Animation animation) {me_for_over=0;}
                                    @Override public void onAnimationRepeat(Animation animation) {}
                                });
                                cloader.setVisibility(View.GONE);fab.setImageResource(R.drawable.menu);
                            }},40);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        fab.setImageResource(R.drawable.menu);isLongPress = false;
                        if (reReady == 1&&justfabUp==1 && me_back==0 && me_for==0)
                        {
                            cloader.setVisibility(View.VISIBLE);menuLog = 0;surf.loadUrl(surf.getUrl());justfabUp = 0;its_loading=1;
                            fabCover.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.float_reload_over));
                        }
                        if (reReady == 0&&justfabUp==1 && me_back==1 && me_for==0)
                        {
                            me_back=0;if(surf.canGoBack()){surf.goBack();}
                            fabCover.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.float_back_over));
                            new Handler().postDelayed(new Runnable() {@Override public void run(){
                                animation =AnimationUtils.loadAnimation(getApplicationContext(), R.anim.float_hide);
                                fab.startAnimation(animation);
                            }},40);
                        }
                        if (reReady == 0&&justfabUp==1 && me_back==0 && me_for==1)
                        {
                            me_for=0;if(surf.canGoForward()){surf.goForward();}
                            fabCover.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.float_for_over));
                            new Handler().postDelayed(new Runnable() {@Override public void run(){
                                animation =AnimationUtils.loadAnimation(getApplicationContext(), R.anim.float_hide);
                                fab.startAnimation(animation);
                            }},40);
                        }
                        if (menuLog == 1)
                        {
                            if (justfabUp == 1) {justfabUp = 0;}
                            else if (menuUp == 0 && its_out==0) {showMenu();}
                        }
                        break;
                }
                return false;
            }
        });

        loader = (ProgressBar) findViewById(R.id.loader);
        loader.setScaleY(0.5f);
        loader.setMax(100);
        loader.setProgress(0);

        addScanner();
        addAddressBar();
        addWebView();
        addbasepane();
        addmenu();
        add_search();
    }
    public void fabRestore() {
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.float_hide);reReady = 0;justfabUp=0;its_loading=0;
        fab.startAnimation(animation);cloader.setVisibility(View.GONE);fab.setImageResource(R.drawable.menu);
    }

    public void showMenu() {
        menuUp = 1;
        menupane.setVisibility(View.VISIBLE);
        menupaneback.setVisibility(View.VISIBLE);
        menupaneback.setEnabled(true);
        menupaneback.setClickable(true);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.menuback_in);
        menupaneback.startAnimation(animation);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.menuback_out);
        animation.setDuration(100);
        fade_away.startAnimation(animation);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.menu_close_show);
        option42.startAnimation(animation);
        int cy = (fabCover.getTop() + fabCover.getBottom())/ 2;
        Animator anim = io.codetail.animation.ViewAnimationUtils.createCircularReveal(menupane, (fabCover.getWidth() / 2) - 11, cy + 8, fab.getWidth() / 2, fabCover.getWidth() * 4 / 5);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.setDuration(250);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                option42.setEnabled(true);
            }
        });
        anim.start();
        fabCover.setVisibility(View.INVISIBLE);
        ViewCompat.animate(option42).rotation(180f).withLayer().setDuration(350).setInterpolator(new OvershootInterpolator()).start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.menu_icons_anim);
                option32.startAnimation(animation);
                option33.startAnimation(animation);
            }
        }, 30);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.menu_icons_anim);
                option31.startAnimation(animation);
                option22.startAnimation(animation);
                option23.startAnimation(animation);
                option34.startAnimation(animation);
            }
        }, 80);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.menu_icons_anim);
                option21.startAnimation(animation);
                option12.startAnimation(animation);
                option13.startAnimation(animation);
                option24.startAnimation(animation);
            }
        }, 100);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.menu_icons_anim);
                option11.startAnimation(animation);
                option14.startAnimation(animation);
            }
        }, 130);
    }

    public void hideMenu() {
        menuUp = 0;
        option42.setEnabled(false);
        menupaneback.setEnabled(false);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.menuback_out);
        menupaneback.startAnimation(animation);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.menuback_in);
        animation.setDuration(300);
        fade_away.startAnimation(animation);
        ViewCompat.animate(option42).rotation(0f).withLayer().setDuration(300).setInterpolator(new AccelerateDecelerateInterpolator()).start();
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.menu_close_hide);
        option42.startAnimation(animation);
        int cy =  (fabCover.getTop() + fabCover.getBottom())/2;
        Animator anim = io.codetail.animation.ViewAnimationUtils.createCircularReveal(menupane, (fabCover.getWidth() / 2) - 11, cy + 8, fabCover.getWidth() * 3 / 4, fab.getWidth() / 2);
        anim.setInterpolator(new LinearOutSlowInInterpolator());
        anim.setDuration(300);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                menupane.setVisibility(View.GONE);
                fabCover.setVisibility(View.VISIBLE);
                menupaneback.setEnabled(false);
                menupaneback.setVisibility(View.INVISIBLE);
                menupaneback.setClickable(false);
            }
        });
        anim.start();

    }

    @Override
    public File getCacheDir() {
        return getApplicationContext().getCacheDir();
    }

    public void addAddressBar() {
        addhover = (CardView) findViewById(R.id.addhover);
        addhover.setVisibility(View.GONE);
        addpanefloat = (io.codetail.widget.RevealLinearLayout) findViewById(R.id.addpanefloat);
        addpanefloat.setVisibility(View.INVISIBLE);
        addpanefloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideAddHover();
            }
        });
        addhoverTop = (CardView) findViewById(R.id.addhoverTop);
        addhoverTop.setVisibility(View.INVISIBLE);
    }

    public void addbasepane() {
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (surf.canGoBack()) {
                    surf.goBack();
                }
            }
        });
        forward = (ImageView) findViewById(R.id.forward);
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (surf.canGoForward()) {
                    surf.goForward();
                }
            }
        });


        home = (ImageView) findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(surf.getUrl()).equals(Homepage)) {
                    surf.loadUrl(Homepage);
                    //tittle.setText(surf.getScrollHeight()+" , "+surf.getHeight());
                }
            }
        });
        home.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                exit();
                return false;
            }
        });
        prepareTabs();
    }

    public void prepareTabs() {
        tabs = (ImageView) findViewById(R.id.tabs);
        tabpane = (RelativeLayout) findViewById(R.id.tabpane);
        mainTabH = (CardView) findViewById(R.id.mainTabH);
        mainTabH.setVisibility(View.INVISIBLE);
        mainTab = (ImageView) findViewById(R.id.mainTab);
        tabs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTabs();
            }
        });
    }
    public Bitmap mergeBitmap(Bitmap bitmap1, Bitmap bitmap2) {
        Bitmap mergedBitmap = null;
        int w, h = 0;
        h = bitmap1.getHeight() + bitmap2.getHeight();
        if (bitmap1.getWidth() > bitmap2.getWidth()) {w = bitmap1.getWidth();}
        else{w = bitmap2.getWidth();}
        mergedBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mergedBitmap);
        canvas.drawBitmap(bitmap1, 0f, 0f, null);
        canvas.drawBitmap(bitmap2, 0f, bitmap1.getHeight(), null);
        return mergedBitmap;
    }
    public void openTabs()
    {
        surf.setVisibility(View.INVISIBLE);tabOpen = 1;tabs.setEnabled(false);
        mainTabH.setLayoutParams(new RelativeLayout.LayoutParams(tabpane.getWidth() * 5 / 9, tabpane.getHeight() / 2));
        surf.destroyDrawingCache();surf.buildDrawingCache();
        mainPanel.destroyDrawingCache();mainPanel.buildDrawingCache();
        Bitmap bmp = mergeBitmap(mainPanel.getDrawingCache(),surf.getDrawingCache());
        //bmp = Bitmap.createBitmap(bmp, 0, 0, surf.getWidth(), surf.getHeight() - getSupportActionBar().getHeight() - 45, null, false);
        mainTab.setImageBitmap(bmp);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.tab_show);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mainTabH.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.menu_hide);
                        anim.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation)
                            {appBar.animate().translationY(-toolbar.getBottom()).setInterpolator(new AccelerateInterpolator()).start();}
                            @Override public void onAnimationEnd(Animation animation) {menuhold.setVisibility(View.GONE);}
                            @Override public void onAnimationRepeat(Animation animation) {}
                        });
                        menuhold.startAnimation(anim);
                    }
                }, 120);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        mainTabH.startAnimation(animation);
    }
    public void closeTabs() {
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.tab_hide);
        tabOpen = 0;
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.menu_show);
                anim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        menuhold.setVisibility(View.VISIBLE);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                appBar.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();
                            }
                        }, 50);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                menuhold.startAnimation(anim);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                tabs.setEnabled(true);
                surf.setVisibility(View.VISIBLE);
                mainTabH.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        mainTabH.startAnimation(animation);
    }

    public void addScanner() {
        scanner = (RelativeLayout) findViewById(R.id.scanner);
        cameraView = (SurfaceView) findViewById(R.id.cameraView);
        holder = cameraView.getHolder();
        barcode = new BarcodeDetector.Builder(Home.this).setBarcodeFormats(Barcode.QR_CODE).build();
        if (!barcode.isOperational()) {
            Toast.makeText(Home.this, "Feature not Supported by device !", Toast.LENGTH_SHORT).show();
        }
        cameraBuild=new xtremecreations.surfer.CameraSource.Builder(Home.this, barcode);
        cameraBuild.setFacing(CameraSource.CAMERA_FACING_BACK);
        cameraBuild.setRequestedPreviewSize(1080, 1920);
        cameraBuild.setRequestedFps(60.0f);
        cameraBuild = cameraBuild.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        cameraSource = cameraBuild.build();
        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ContextCompat.checkSelfPermission(Home.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(cameraView.getHolder());}
                    else{permission_camera.setVisibility(View.VISIBLE);flash.setVisibility(View.GONE);camera_rotate.setVisibility(View.GONE);}
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {cameraSource.stop();}
        });
        barcode.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {}
            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                if (barcodes.size() != 0) {
                    surf.post(new Runnable() {
                        public void run() {
                            String url = barcodes.valueAt(0).displayValue;
                            if (Patterns.WEB_URL.matcher(url).matches()) {
                                if(vibrate==1){((Vibrator) Home.this.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(100);}
                                scan_results.setVisibility(View.GONE);
                                option42.performClick();
                                surf.loadUrl(getURL(url));
                            }
                            else
                            {
                                scan_results.setVisibility(View.VISIBLE);scan_text.setText("");
                                scan_text.setText(barcodes.valueAt(0).displayValue);
                            }
                        }
                    });
                }
            }
        });

        flash=(ImageView)findViewById(R.id.flash);
        flash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(vibrate==1){((Vibrator) Home.this.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(20);}
                try
                {
                    if(flash_on==0)
                    {
                        flash.setImageResource(R.drawable.flash_glow);flash_on=1;
                        cameraSource.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);

                    }
                    else
                    {
                        flash.setImageResource(R.drawable.flash);flash_on=0;
                        cameraSource.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    }
                }
                catch(Exception e){e.printStackTrace();}
            }
        });
        camera_rotate=(ImageView)findViewById(R.id.camera_rotate);
        camera_rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(vibrate==1){((Vibrator) Home.this.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(20);}
                try
                {
                    if(cameraSource.getCameraFacing()== CameraSource.CAMERA_FACING_BACK)
                    {
                        flash.setVisibility(View.GONE);
                        cameraBuild.setFacing(CameraSource.CAMERA_FACING_FRONT);
                        cameraBuild.setRequestedPreviewSize(1080, 1920);
                        cameraBuild.setRequestedFps(60.0f);
                        cameraBuild = cameraBuild.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
                        cameraView.setVisibility(View.GONE);cameraView.setVisibility(View.VISIBLE);
                    }
                    else if(cameraSource.getCameraFacing()== CameraSource.CAMERA_FACING_FRONT)
                    {
                        flash.setVisibility(View.VISIBLE);
                        cameraBuild.setFacing(CameraSource.CAMERA_FACING_BACK);
                        cameraBuild.setRequestedPreviewSize(1080, 1920);
                        cameraBuild.setRequestedFps(60.0f);
                        cameraBuild = cameraBuild.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
                        cameraView.setVisibility(View.GONE);cameraView.setVisibility(View.VISIBLE);
                        if(flash_on==1){cameraSource.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);}
                    }
                }
                catch(Exception e){e.printStackTrace();}
            }
        });

        permission_camera=(RelativeLayout)findViewById(R.id.permission_camera);
        scan_results=(RelativeLayout)findViewById(R.id.scan_results);
        scan_text=(TextView)findViewById(R.id.scan_text);

        copy_scan=(ImageView)findViewById(R.id.copy_scan);
        copy_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("XtremeSurfer Scan",scan_text.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(Home.this, "Copied to Clipboard", Toast.LENGTH_SHORT).show();
            }
        });
        share_scan=(ImageView)findViewById(R.id.share_scan);
        share_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,scan_text.getText().toString());
                startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_using)));
            }
        });

        allow_camera =(Button)findViewById(R.id.allow_camera);
        allow_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(Home.this, new String[]{Manifest.permission.CAMERA}, 1);
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {cameraView.setVisibility(View.GONE);cameraView.setVisibility(View.VISIBLE);
                    permission_camera.setVisibility(View.GONE);flash.setVisibility(View.VISIBLE);camera_rotate.setVisibility(View.VISIBLE);}
                else{}
                return;
            }
        }
    }

    public void addmenu() {
        menupane = (android.support.v7.widget.CardView) findViewById(R.id.menupane);
        menuback = (RelativeLayout) findViewById(R.id.menuback);
        //menupane.setBackgroundColor(getLightColor(Color.parseColor("#"+Integer.toHexString(getResources().getColor(R.color.menucolor))),1f));
        menupaneback = (RelativeLayout) findViewById(R.id.menupaneback);
        fade_away = (RelativeLayout) findViewById(R.id.fade_away);
        menupaneback.setBackgroundColor(getLightColor(Color.parseColor("#000000"), 0.7f));
        menupane.setVisibility(View.GONE);
        menupaneback.setVisibility(View.GONE);
        menupaneback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option42.performClick();
            }
        });

        option11 = (android.support.design.widget.FloatingActionButton) findViewById(R.id.option11);option11.setStateListAnimator(null);
        option11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(vibrate==1){((Vibrator) Home.this.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(20);}
                WindowManager.LayoutParams attrs = getWindow().getAttributes();
                if (speedmode==0)
                {
                    option11.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.speed_mode)));
                    option11.setImageDrawable(ContextCompat.getDrawable(Home.this, R.drawable.speed_mode_toggle));speedmode=1;
                }
                else
                {
                    option11.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.menucolor)));
                    option11.setImageDrawable(ContextCompat.getDrawable(Home.this, R.drawable.speed_mode));speedmode=0;
                }
            }
        });

        option12 = (android.support.design.widget.FloatingActionButton) findViewById(R.id.option12);option12.setStateListAnimator(null);
        option12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(vibrate==1){((Vibrator) Home.this.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(20);}
                WindowManager.LayoutParams attrs = getWindow().getAttributes();
                if (fullscreen==0)
                {
                    option12.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.fullscreen)));
                    option12.setImageDrawable(ContextCompat.getDrawable(Home.this, R.drawable.fullscreen_toggle));fullscreen=1;
                    decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                }
                else
                {
                    option12.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.menucolor)));
                    option12.setImageDrawable(ContextCompat.getDrawable(Home.this, R.drawable.fullscreen));fullscreen=0;
                    decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                }settings_editor.putInt("fullscreen",fullscreen);settings_editor.commit();
            }
        });

        option13 = (android.support.design.widget.FloatingActionButton) findViewById(R.id.option13);option13.setStateListAnimator(null);
        option13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ad__button==0)
                {
                    adButton_Listener();
                }
            }
        });
        rippleBackground_small=(RippleBackground)findViewById(R.id.ad_ripple_small);
        ads_total_small=(TextView)findViewById(R.id.ads_total_small);
        ad_shield_small=(ImageView)findViewById(R.id.ad_shield_small);
        ad_settings=(FloatingActionButton) findViewById(R.id.ad_settings);
        ad_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(vibrate==1){((Vibrator) Home.this.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(20);}option42.performClick();
                Intent intent=new Intent(Home.this,AdBlocking_Activity.class);
                if(fullscreen==1){intent.putExtra("fullscreen",1);}else{intent.putExtra("fullscreen",0);}
                startActivity(intent);overridePendingTransition(R.anim.splash_in,0);
            }
        });
        ad_pause=(FloatingActionButton)findViewById(R.id.ad_pause);
        ad_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    if(ad_paused==0)
                    {
                        whitelist=getArrayData("whitelist",Home.this);
                        String[] temp=new String[whitelist.length+1];
                        for(int i=0;i<whitelist.length;i++){temp[i]=whitelist[i];}
                        temp[whitelist.length]= ((new URL(surf.getUrl())).getHost()).toLowerCase();
                        setArrayData(temp,"whitelist",Home.this);
                        whitelist=getArrayData("whitelist",Home.this);
                        ad_pause.setImageDrawable(ContextCompat.getDrawable(Home.this, R.drawable.play));
                        ad_paused=1;
                    }
                    else
                    {
                        whitelist=getArrayData("whitelist",Home.this);
                        if(whitelist.length>0){
                            String[] temp=new String[whitelist.length-1];
                            for(int i=0;i<whitelist.length-1;i++)
                            {
                                if(! whitelist[i].equals(((new URL(surf.getUrl())).getHost()).toLowerCase()))
                                {temp[i]=whitelist[i];}
                            }
                            setArrayData(temp,"whitelist",Home.this);
                            whitelist=getArrayData("whitelist",Home.this);}
                        ad_pause.setImageDrawable(ContextCompat.getDrawable(Home.this, R.drawable.play));
                        ad_paused=0;
                    }
                }
                catch (MalformedURLException e){}
                if(whitelist.length>0)Toast.makeText(Home.this,whitelist[0], Toast.LENGTH_SHORT).show();
            }
        });
        ad_power=(FloatingActionButton)findViewById(R.id.ad_power);
        ad_power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adblock==0) {rippleBackground_small.startRippleAnimation();}
                else{rippleBackground_small.stopRippleAnimation();}
                adButton_Listener();
            }
        });
        option13.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(vibrate==1){((Vibrator) Home.this.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(35);}
                int cx = (option13.getLeft() + option13.getRight()) / 2;ad__button=1;
                int cy = ((menuback.getTop()+menuback.getBottom())/2)-((option13.getTop()+option13.getBottom())/2)-(option13.getHeight());
                Animator anim = io.codetail.animation.ViewAnimationUtils.createCircularReveal(adblock_small,cx+13, cy-5, option13.getWidth() / 2, fabCover.getWidth() * 4/5);
                anim.setInterpolator(new AccelerateInterpolator());
                anim.setDuration(200);
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {if (adblock==1){rippleBackground_small.startRippleAnimation();}}});
                anim.start();adblock_small.setVisibility(View.VISIBLE);ad_bl_small=1;
                return false;
            }
        });

        option14 = (android.support.design.widget.FloatingActionButton) findViewById(R.id.option14);option14.setStateListAnimator(null);
        option14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(vibrate==1){((Vibrator) Home.this.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(20);}
            }
        });

        option21 = (android.support.design.widget.FloatingActionButton) findViewById(R.id.option21);option21.setStateListAnimator(null);
        option22 = (android.support.design.widget.FloatingActionButton) findViewById(R.id.option22);option22.setStateListAnimator(null);
        option23 = (android.support.design.widget.FloatingActionButton) findViewById(R.id.option23);option23.setStateListAnimator(null);
        option24 = (android.support.design.widget.FloatingActionButton) findViewById(R.id.option24);option24.setStateListAnimator(null);
        option31 = (android.support.design.widget.FloatingActionButton) findViewById(R.id.option31);option31.setStateListAnimator(null);
        option32 = (android.support.design.widget.FloatingActionButton) findViewById(R.id.option32);option32.setStateListAnimator(null);

        option33 = (android.support.design.widget.FloatingActionButton) findViewById(R.id.option33);option33.setStateListAnimator(null);
        option33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(vibrate==1){((Vibrator) Home.this.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(20);}
                scanner.setVisibility(View.VISIBLE);qr=1;
                int cx = (option33.getLeft() + option33.getRight()) / 2;
                int cy = option13.getHeight()/4+(menuback.getTop()+menuback.getBottom())/2+(option33.getTop()+option33.getBottom())/2;
                Animator anim = io.codetail.animation.ViewAnimationUtils.createCircularReveal(scanner,cx, cy, option33.getWidth() / 2, fabCover.getWidth() * 4/5);
                anim.setInterpolator(new AccelerateInterpolator());
                anim.setDuration(200);
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                    }});
                anim.start();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {cameraView.setVisibility(View.VISIBLE);}}, 150);
            }
        });

        option34=(android.support.design.widget.FloatingActionButton)findViewById(R.id.option34);option34.setStateListAnimator(null);

        option41 =(ImageView) findViewById(R.id.option41);
        option41.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {}
        });

        option42=(android.support.design.widget.FloatingActionButton)findViewById(R.id.option42);option42.setEnabled(false);
        option42.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {hideMenu();cameraView.setVisibility(View.GONE);scan_results.setVisibility(View.GONE);
                scanner.setVisibility(View.GONE);qr=0;
                rippleBackground_small.stopRippleAnimation();adblock_small.setVisibility(View.GONE);ad_bl_small=0;ad__button=0;}
        });option42.setStateListAnimator(null);

        option43 =(ImageView) findViewById(R.id.option43);
        option43.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {exit();}
        });
    }
    public boolean setArrayData(String[] array, String arrayName, Context mContext) {
        ad_editor.putInt(arrayName +"_size ", array.length);
        for(int i=0 ; i<array.length ; i++){
            ad_editor.putString(arrayName + "_" + i, array[i]);
        }
        return ad_editor.commit();
    }
    public String[] getArrayData(String arrayName, Context mContext) {
        int size = ad_preferences.getInt(arrayName + "_size", 0);
        String array[]=new String[size];
        for (int i = 0; i < size; i++) {
            array[i]=ad_preferences.getString(arrayName + "_" + i,"");
        }
        return array;
    }
    public void adButton_Listener()
    {
        if(vibrate==1){((Vibrator) Home.this.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(20);}
        if (adblock==0)
        {
            option13.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.adblock)));
            ad_settings.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#43a3ed")));
            ad_pause.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#43a3ed")));
            ad_power.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#43a3ed")));

            option13.setImageDrawable(ContextCompat.getDrawable(Home.this, R.drawable.ad_block_toggle));
            ad_shield_small.setImageDrawable(ContextCompat.getDrawable(Home.this, R.drawable.shield));adblock=1;
        }
        else
        {
            option13.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.menucolor)));
            ad_settings.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#8e8e8e")));
            ad_pause.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#8e8e8e")));
            ad_power.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#8e8e8e")));

            option13.setImageDrawable(ContextCompat.getDrawable(Home.this, R.drawable.ad_block));
            ad_shield_small.setImageDrawable(ContextCompat.getDrawable(Home.this, R.drawable.shield_disabled));adblock=0;
        }
        ad_editor.putInt("status",adblock);ad_editor.commit();
    }

    public int getLightColor(int col,float factor)
    {return Color.argb(Math.round(Color.alpha(col)*factor),Color.red(col),Color.green(col),Color.blue(col));}

    public void addWebView(){
        surf=(xtremecreations.surfer.NestedWebView) findViewById(R.id.surfView);
        WebSettings settings = surf.getSettings();
        settings.setDefaultTextEncodingName("utf-8");
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setLoadsImagesAutomatically(true);
        settings.setAppCacheEnabled(true);
        settings.setAppCachePath(Home.this.getCacheDir().getPath());
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setGeolocationEnabled(true);
        settings.setSaveFormData(true);
        settings.setDomStorageEnabled(true);
        settings.setUserAgentString("Mozilla/5.0 (Linux; Android "+Build.VERSION.RELEASE+"; "+Build.MODEL+" Build/"+Build.ID+") AppleWebKit/537.36 (KHTML, like Gecko) Chrome/ Mobile Safari/537.36");

        surf.setWebChromeClient(new MyWebChromeClient());
        surf.setScrollbarFadingEnabled(true);
        surf.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String url,String userAgent,String contentDisposition,String mimetype,long contentLength) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }});
        surf.setWebViewClient(new WebViewClient(){
            private Map<String, Boolean> loadedUrls = new HashMap<>();
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                if(adblock==1 && ad_paused==1)
                {
                    boolean ad;
                    if (!loadedUrls.containsKey(request.getUrl().toString())) {
                        ad = AdBlocker.isAd(request.getUrl().toString());
                        loadedUrls.put(request.getUrl().toString(), ad);
                    } else {
                        ad = loadedUrls.get(request.getUrl().toString());
                    }
                    if(ad){
                        ad_editor.putInt("total", ad_preferences.getInt("total", 0)+1);ad_editor.commit();
                    }
                    return ad ? AdBlocker.createEmptyResource() : super.shouldInterceptRequest(view, request);
                }
                return super.shouldInterceptRequest(view, request);
            }

            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl){
                if(errorCode==-6){view.loadData(errorpage("Something Went Wrong","The Page You Requested, Can't Be Reached"), "text/html", null);}
                else if(errorCode==-2){view.loadData(errorpage("No Internet Connection","Wifi or Mobile Data Should Be Active"), "text/html", null);}
                else if(errorCode==-10){view.loadData(errorpage("You are Lost!","Webpage Not Available"), "text/html", null);}
                else if(errorCode==-8){view.loadData(errorpage("Error Occured!","Connection Timed Out"), "text/html", null);}
                fevicon.setBackgroundResource(R.drawable.error);
                Toast.makeText(Home.this, "" + errorCode , Toast.LENGTH_LONG).show();
            }
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error){handler.proceed();}
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                fevicon.setBackgroundResource(R.drawable.flash_glow);loader.setProgress(0);}
            @Override
            public void onPageFinished(WebView view, String url){
                ObjectAnimator anim = ObjectAnimator.ofInt(loader, "progress",loader.getProgress(),100);
                anim.setDuration(500);anim.setInterpolator(new DecelerateInterpolator());anim.start();
                anim.addListener(new Animator.AnimatorListener() {
                    public void onAnimationStart(Animator animation) {}
                    public void onAnimationRepeat(Animator animation) {}
                    public void onAnimationCancel(Animator animation) {}
                    public void onAnimationEnd(Animator animation) {loader.setProgress(0);}
                });
                if(surf.getScrollHeight()>surf.getHeight())
                {((AppBarLayout.LayoutParams) toolbar.getLayoutParams()).setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL|
                        AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS|AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP);
                    //((CoordinatorLayout.LayoutParams)menuhold.getLayoutParams()).setBehavior(null);menuhold.requestLayout();
                }
                if(reReady==1){fabRestore();}
            }
            @Override
            public void onLoadResource(WebView view, String url)
            {if(url.toLowerCase().startsWith("http") && addhoveractive==0){addbar.setText(view.getUrl());}}
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (Uri.parse(url).getHost().equals("play.google.com") || url.toLowerCase().startsWith("market://") ||
                        url.toLowerCase().startsWith("magnet:")){
                    view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));return true;
                }
                loader.setProgress(0);
                return false;
            }

        });
        surf.loadUrl(Homepage);
        surf.setOnScrollChangedCallback(new NestedWebView.OnScrollChangedCallback(){
            public void onScroll(int l, int t){

                if(surf.getScrollY()==0)
                {
                    //((ViewGroup.MarginLayoutParams)surf.getLayoutParams()).bottomMargin = getSupportActionBar().getHeight()+menuhold.getHeight();
                }
                else if(surf.getScrollY()>0){
                }
            }
        });
    }
    public class MyWebChromeClient extends WebChromeClient {

        @Override
        public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
            callback.invoke(origin, true, false);
        }
        @Override
        public void onReceivedTitle(WebView view, String title) {
            tittle.setText(title);
            super.onReceivedTitle(view, title);
        }
        @Override
        public void onProgressChanged(WebView view, int progress)
        {
            if(progress<cpro){loader.setProgress(0);}cpro=loader.getProgress();
            ObjectAnimator anim = ObjectAnimator.ofInt(loader, "progress",loader.getProgress(),progress);
            anim.setDuration(500);anim.setInterpolator(new DecelerateInterpolator());anim.start();

            if(surf.canGoBack()){back.setEnabled(true);back.setImageDrawable(ContextCompat.getDrawable(Home.this, R.drawable.back_nav));}
            else{back.setEnabled(false);back.setImageDrawable(ContextCompat.getDrawable(Home.this, R.drawable.back_nav_disabled));}
            if(surf.canGoForward()){forward.setEnabled(true);forward.setImageDrawable(ContextCompat.getDrawable(Home.this, R.drawable.forward_nav));}
            else{forward.setEnabled(false);forward.setImageDrawable(ContextCompat.getDrawable(Home.this, R.drawable.forward_nav_disabled));}
        }
        @Override
        public void onReceivedIcon(WebView view, Bitmap b) {
            super.onReceivedIcon(view, b);
            if(view.getUrl().contains("google.com") || view.getUrl().contains("google.co.")){fevicon.setBackgroundResource(R.drawable.google);}
            else if(view.getUrl().contains("facebook.com")){fevicon.setBackgroundResource(R.drawable.facebook);}
            else{fevicon.setBackgroundDrawable(new BitmapDrawable(getResources(),b));}
            ObjectAnimator anim = ObjectAnimator.ofInt(loader, "progress",loader.getProgress(),100);
                anim.setDuration(500);anim.setInterpolator(new DecelerateInterpolator());anim.start();
                anim.addListener(new Animator.AnimatorListener() {
                public void onAnimationStart(Animator animation) {}
                public void onAnimationRepeat(Animator animation) {loader.setProgress(0);}
                public void onAnimationCancel(Animator animation) {loader.setProgress(0);}
                public void onAnimationEnd(Animator animation) {loader.setProgress(0);}
            });
            if(view.getUrl().startsWith("file:///")) {fevicon.setBackgroundResource(R.drawable.error);}
        }
    }
    public void showAddHover(){
        if(addhoveractive==0){
            final int cx = (mainPanel.getLeft() + mainPanel.getRight())/2;int cy = (mainPanel.getTop() + mainPanel.getBottom())/2;
            float finalRadius = (float)Math.hypot(Math.max(cx,mainPanel.getWidth()-cx),Math.max(cy,mainPanel.getHeight()-cy));
            Animator anim=io.codetail.animation.ViewAnimationUtils.createCircularReveal(mainPanel,cx-25,mainPanel.getTop(),finalRadius,0);
            anim.setInterpolator(new DecelerateInterpolator());anim.setDuration(150);
            mainPanel.animate().translationY(-mainPanel.getBottom()/10).setInterpolator(new DecelerateInterpolator()).setDuration(90).start();
            new Handler().postDelayed(new Runnable() {@Override public void run(){
                mainPanel.animate().translationY(mainPanel.getTop()*11/3).setInterpolator(new DecelerateInterpolator()).setDuration(60).start();}},80);
            Animation fadeIn = new AlphaAnimation(0,1);fadeIn.setInterpolator(new DecelerateInterpolator());fadeIn.setDuration(350);
            addpanefloat.startAnimation(fadeIn);addpanefloat.setVisibility(View.VISIBLE);
            anim.addListener(new AnimatorListenerAdapter()
            {
                @Override
                public void onAnimationEnd(Animator animation) {
                    int cy = getSupportActionBar().getHeight()*20/11;
                    Animator animate=io.codetail.animation.ViewAnimationUtils.createCircularReveal(addhover,
                            cx-25,cy,0,cy*3);
                    animate.setInterpolator(new DecelerateInterpolator());animate.setDuration(180);animate.start();
                    addhover.setVisibility(View.VISIBLE);addbar.setVisibility(View.VISIBLE);listpane.setVisibility(View.VISIBLE);
                    addhover.animate().translationY(cy/2).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(120).start();
                    new Handler().postDelayed(new Runnable() {@Override public void run(){
                        addhoverTop.animate().translationY(addhoverTop.getHeight()).setInterpolator(new DecelerateInterpolator()).setDuration(0).start();
                        addhoverTop.setVisibility(View.VISIBLE);addbar.requestFocus();addhoveractive=1;
                        addhoverTop.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).setDuration(100).start();
                        addhover.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).setDuration(100).start();
                        ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(addbar, InputMethodManager.SHOW_IMPLICIT);
                    }},60);
                }
            });anim.start();
        }
    }
    public void hideAddHover()
    {
        if(addhoveractive==1){
            Animation fadeIn = new AlphaAnimation(1,0);fadeIn.setInterpolator(new DecelerateInterpolator());fadeIn.setDuration(150);
            addpanefloat.startAnimation(fadeIn);addhover.startAnimation(fadeIn);
            new Handler().postDelayed(new Runnable() {@Override public void run(){addhover.setVisibility(View.GONE);
                addbar.setVisibility(View.GONE);listpane.setVisibility(View.GONE);addpanefloat.setVisibility(View.GONE);}},150);
            addhoverTop.animate().translationY(addhoverTop.getHeight()).setInterpolator(new DecelerateInterpolator()).setDuration(80).start();
            new Handler().postDelayed(new Runnable() {@Override public void run(){addhoverTop.setVisibility(View.GONE);
                mainPanel.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).setDuration(80).start();
                addhoverTop.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).setDuration(0).start();}},80);
            new Handler().postDelayed(new Runnable() {@Override public void run(){
                ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(addbar.getWindowToken(),0);
            }},10);addhoveractive=0;addbar.setText(surf.getUrl());searchlist.setAdapter(new CustomAdapter(Home.this,Null));
            engine.setVisibility(View.INVISIBLE);type_search.setVisibility(View.INVISIBLE);suggestion.setVisibility(View.INVISIBLE);
            on_engine=0;on_type=0;on_suggestion=0;
        }
    }
    public void add_search()
    {
        search_engine=(CardView)findViewById(R.id.search_engine);
        engine=(CardView)findViewById(R.id.engine);engine.setVisibility(View.INVISIBLE);
        search_engine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float radius=(float)Math.sqrt(Math.pow(engine.getWidth(),2)+Math.pow(engine.getHeight(),2));
                if(on_engine==0)
                {
                    engine.setVisibility(View.VISIBLE);type_search.setVisibility(View.INVISIBLE);suggestion.setVisibility(View.INVISIBLE);
                    Animator anim=io.codetail.animation.ViewAnimationUtils.createCircularReveal(engine,search_engine.getWidth()/2,0,0,radius);
                    anim.setInterpolator(new AccelerateInterpolator());anim.setDuration(250);anim.start();on_engine=2;on_type=0;on_suggestion=0;
                    anim.addListener(new AnimatorListenerAdapter() {@Override public void onAnimationEnd(Animator animation) {on_engine=1;}});
                }
                else if(on_engine==1)
                {
                    Animator anim=io.codetail.animation.ViewAnimationUtils.createCircularReveal(engine,search_engine.getWidth()/2,0,radius,0);
                    anim.setInterpolator(new DecelerateInterpolator());anim.setDuration(250);anim.start();on_engine=2;
                    anim.addListener(new AnimatorListenerAdapter() {@Override public void onAnimationEnd(Animator animation) {on_engine=0;
                        engine.setVisibility(View.INVISIBLE);}});
                }
            }
        });
        search_type=(CardView)findViewById(R.id.search_type);
        type_search=(CardView)findViewById(R.id.type_search);type_search.setVisibility(View.INVISIBLE);
        search_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float radius=(float)Math.sqrt(Math.pow(type_search.getWidth(),2)+Math.pow(type_search.getHeight(),2));
                if(on_type==0)
                {
                    engine.setVisibility(View.INVISIBLE);type_search.setVisibility(View.VISIBLE);suggestion.setVisibility(View.INVISIBLE);
                    Animator anim=io.codetail.animation.ViewAnimationUtils.createCircularReveal(type_search,type_search.getWidth()/2,0,0,radius);
                    anim.setInterpolator(new AccelerateInterpolator());anim.setDuration(250);anim.start();on_engine=0;on_type=2;on_suggestion=0;
                    anim.addListener(new AnimatorListenerAdapter() {@Override public void onAnimationEnd(Animator animation) {on_type=1;}});
                }
                else if(on_type==1)
                {
                    Animator anim=io.codetail.animation.ViewAnimationUtils.createCircularReveal(type_search,type_search.getWidth()/2,0,radius,0);
                    anim.setInterpolator(new DecelerateInterpolator());anim.setDuration(250);anim.start();on_type=2;
                    anim.addListener(new AnimatorListenerAdapter() {@Override public void onAnimationEnd(Animator animation) {on_type=0;
                        type_search.setVisibility(View.INVISIBLE);}});
                }
            }
        });
        search_no=(CardView)findViewById(R.id.search_no);
        suggestion=(CardView)findViewById(R.id.suggestion);suggestion.setVisibility(View.INVISIBLE);
        search_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float radius=(float)Math.sqrt(Math.pow(suggestion.getWidth(),2)+Math.pow(suggestion.getHeight(),2));
                if(on_suggestion==0)
                {
                    engine.setVisibility(View.INVISIBLE);type_search.setVisibility(View.INVISIBLE);suggestion.setVisibility(View.VISIBLE);
                    Animator anim=io.codetail.animation.ViewAnimationUtils.createCircularReveal(suggestion,suggestion.getWidth()-search_no.getWidth()/2,0,0,radius);
                    anim.setInterpolator(new AccelerateInterpolator());anim.setDuration(250);anim.start();on_engine=0;on_type=0;on_suggestion=2;
                    anim.addListener(new AnimatorListenerAdapter() {@Override public void onAnimationEnd(Animator animation) {on_suggestion=1;}});
                }
                else if(on_suggestion==1)
                {
                    Animator anim=io.codetail.animation.ViewAnimationUtils.createCircularReveal(suggestion,suggestion.getWidth()-search_no.getWidth()/2,0,radius,0);
                    anim.setInterpolator(new DecelerateInterpolator());anim.setDuration(250);anim.start();on_suggestion=2;
                    anim.addListener(new AnimatorListenerAdapter() {@Override public void onAnimationEnd(Animator animation) {on_suggestion=0;
                        suggestion.setVisibility(View.INVISIBLE);}});
                }
            }
        });
    }
    public String getURL(String URL){
        String url=(URL.trim()).replace(" ","%20");
        if (! (url.startsWith("https") || url.startsWith("http")) && Patterns.WEB_URL.matcher(url).matches()){url="http://"+url;}
        if(! Patterns.WEB_URL.matcher(url).matches()){url="https://www.google.co.in/m?q="+url;}addbar.setText(url);
        return url;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultcode, Intent intent) {
        super.onActivityResult(requestCode, resultcode, intent);
        ArrayList<String> speech;
        if (resultcode == RESULT_OK && requestCode == REQUEST_CODE) {
            speech = intent.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String sp=speech.get(0);
            if(sp.toLowerCase().startsWith("search"))
            {
                if(addhoveractive==1){hideAddHover();}
                if(sp.toLowerCase().equals("search"))
                {Toast.makeText(getApplicationContext(), "Search Command Should Be Followed By a Search Value.", Toast.LENGTH_LONG).show();}
                else{sp=sp.substring(7,sp.length());surf.loadUrl(getURL(sp));}
            }
            else {if(addhoveractive==0){showAddHover();}addbar.setText(sp);addbar.setSelection(0,sp.length());}
        }
    }
    public void exit()
    {
        new AlertDialog.Builder(new ContextThemeWrapper(Home.this, R.style.DarkDialog))
                .setTitle("Exit surfer?").setMessage("Are you sure you want to exit surfer?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), Splash.class);
                        intent.putExtra("exit",1);startActivity(intent);
                        overridePendingTransition(R.anim.exitsplash,R.anim.exitmain);
                        new Handler().postDelayed(new Runnable() {@Override public void run(){Home.this.finish();}},1000);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {}
                })
                .setIcon(R.mipmap.logo)
                .show();
    }

    public String errorpage(String errorhead,String errormsg){
        return "<!doctype html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "  <meta charset=\"utf-8\">\n" +
                "  <meta name=\"apple-mobile-web-app-capable\" content=\"yes\">\n" +
                "  <meta name=\"mobile-web-app-capable\" content=\"yes\">\n" +
                "  <title>Error!</title>  \n" +
                "  <link rel=\"shortcut icon\" type=\"image/png\" href=\"favicon.png\"/>\n" +
                " <style type=\"text/css\">\n" +
                " @import url('https://fonts.googleapis.com/css?family=Quicksand');\n" +
                " body{background: #141414;}\n" +
                "#cloud{\n" +
                "  width:300px;height:120px;\n" +
                "  background: #676767;\n" +
                "  background: -webkit-linear-gradient(-90deg,#676767 5%, #676767 100%);\n" +
                "  -webkit-border-radius: 100px;\n" +
                "  -moz-border-radius: 100px;\n" +
                "  border-radius:100px;position:relative;\n" +
                "  margin: 150px auto 0;opacity: .5;\n" +
                "}\n" +
                "#cloud:before, #cloud:after{\n" +
                "  content: '';\n" +
                "  position:absolute;background: #676767;z-index: -1;\n" +
                "}\n" +
                "#cloud:after{\n" +
                "  width: 100px;height: 100px;top: -50px;left:50px;\n" +
                "-webkit-border-radius: 100px;\n" +
                "-moz-border-radius: 100px;\n" +
                "border-radius: 100px;\n" +
                "}\n" +
                "#cloud:before{\n" +
                "  width: 120px;height: 120px;top: -70px;right: 50px;\n" +
                "  -webkit-border-radius: 200px;\n" +
                "  -moz-border-radius: 200px;\n" +
                "  border-radius: 200px;\n" +
                "}\n" +
                ".shadow {\n" +
                "  width: 300px;position: absolute;bottom: -10px;background: black;z-index: -1;\n" +
                "  -webkit-box-shadow: 0 0 25px 8px rgba(0,0,0,0.4);\n" +
                "  -moz-box-shadow: 0 0 25px 8px rgba(0,0,0,0.4);\n" +
                "  box-shadow: 0 0 25px 8px rgba(0,0,0,0.4);\n" +
                "  -webkit-border-radius: 50%;\n" +
                "  -moz-border-radius: 50%;\n" +
                "  border-radius: 50%;\n" +
                "}\n" +
                "h2{color: #fff;font-size: 20px;font-family: 'Quicksand';padding-top: 25px;text-align: center;margin: 5px auto;}\n" +
                "h4 {color: #fff;font-size: 12px;font-family: 'Quicksand';margin: 0 auto;padding: 0;text-align: center;}\n" +
                " </style>\n" +
                "<body>    \n" +
                "<br></br>\n" +
                "<div id=\"cloud\"> <h2>"+errorhead+"</h2>\n" +
                "<h4>"+errormsg+"</h4>\n" +
                "<span class=\"shadow\"></span></div>\n" +
                "</body>\n" +
                "</html>\n";
    }

}
