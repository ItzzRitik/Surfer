package xtremecreations.surfer;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.liuguangqiang.swipeback.SwipeBackLayout;
import com.skyfishjy.library.RippleBackground;

import java.io.ByteArrayOutputStream;

public class AdBlocking_Activity extends SwipeBackActivity {
    RippleBackground rippleBackground;
    TextView ads_total;
    ImageView share_ad,clean_ad,ad_switch,ad_shield;
    RelativeLayout adblocker,ad_details;
    SharedPreferences ad_preferences;
    SharedPreferences.Editor ad_editor;
    int dur=0,adBlocker_on=0;
    @Override
    public void onBackPressed() {
        finish();overridePendingTransition(0,R.anim.exitmain);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_blocking);
        setDragEdge(SwipeBackLayout.DragEdge.TOP);
        if (getIntent().getExtras().getInt("fullscreen")==1)
        {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
        ad_preferences = getApplicationContext().getSharedPreferences("adblock", 0);ad_editor=ad_preferences.edit();
        adBlocker_on=ad_preferences.getInt("status",0);

        rippleBackground=(RippleBackground)findViewById(R.id.ad_ripple);

        ads_total=(TextView)findViewById(R.id.ads_total);
        ads_total.setText(ad_preferences.getInt("total",0)+"");
        dur=Integer.parseInt(ads_total.getText().toString());
        if(dur>=0&&dur<=100){dur=50;}
        else if(dur>100&&dur<=10000){dur=200;}
        else if(dur>10000&&dur<=100000){dur=500;}
        else if(dur>100000&&dur<=1000000){dur=1000;}
        else if(dur>1000000&&dur<=10000000){dur=1500;}
        clean_ad=(ImageView)findViewById(R.id.clean_ad);
        clean_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor ad_editor = ad_preferences.edit();
                ad_editor.putInt("total",0);ad_editor.commit();
                ValueAnimator animator = ValueAnimator.ofInt(Integer.parseInt(ads_total.getText().toString()),0);
                animator.setInterpolator(new DecelerateInterpolator());animator.setDuration(dur);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator animation) {
                        ads_total.setText(animation.getAnimatedValue().toString());
                        if((int)animation.getAnimatedValue()<=0){ads_total.setText("0");}
                    }
                });
                animator.start();
            }
        });

        ad_details=(RelativeLayout)findViewById(R.id.ad_details);
        share_ad=(ImageView)findViewById(R.id.share_ad);
        share_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ad_details.setDrawingCacheEnabled(true);ad_details.destroyDrawingCache();ad_details.buildDrawingCache();
                Bitmap bitmap = Bitmap.createBitmap(ad_details.getDrawingCache());
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "AdBlocker", null);
                Uri imageUri =  Uri.parse(path);
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/png");
                intent.putExtra(Intent.EXTRA_STREAM, imageUri);
                intent.putExtra(android.content.Intent.EXTRA_TEXT,"Powerfull AdBlocker in Surfer Browser makes browsing cleaner and faster, Try It !");
                startActivity(Intent.createChooser(intent , "Share"));
            }
        });

        ad_switch=(ImageView)findViewById(R.id.ad_switch);
        ad_shield=(ImageView)findViewById(R.id.ad_shield);
        adblocker=(RelativeLayout)findViewById(R.id.adblocker);
        if(adBlocker_on==1){
            ad_switch.setImageDrawable(getResources().getDrawable(R.drawable.on, getApplicationContext().getTheme()));
            ad_shield.setImageDrawable(getResources().getDrawable(R.drawable.shield, getApplicationContext().getTheme()));
            share_ad.setVisibility(View.VISIBLE);clean_ad.setVisibility(View.VISIBLE);rippleBackground.startRippleAnimation();
        }
        adblocker.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        adblocker.setBackgroundColor(Color.parseColor("#D6D6D6"));setDragEdge(null);break;
                    case MotionEvent.ACTION_UP:
                        adblocker.setBackgroundColor(Color.parseColor("#f9f9f9"));setDragEdge(SwipeBackLayout.DragEdge.TOP);
                        if(adBlocker_on==0)
                        {
                            ad_switch.setImageDrawable(getResources().getDrawable(R.drawable.on, getApplicationContext().getTheme()));
                            ad_shield.setImageDrawable(getResources().getDrawable(R.drawable.shield, getApplicationContext().getTheme()));
                            adBlocker_on=1;share_ad.setVisibility(View.VISIBLE);clean_ad.setVisibility(View.VISIBLE);
                            rippleBackground.startRippleAnimation();
                        }
                        else
                        {
                            ad_switch.setImageDrawable(getResources().getDrawable(R.drawable.off, getApplicationContext().getTheme()));
                            ad_shield.setImageDrawable(getResources().getDrawable(R.drawable.shield_disabled, getApplicationContext().getTheme()));
                            adBlocker_on=0;share_ad.setVisibility(View.GONE);clean_ad.setVisibility(View.GONE);
                            rippleBackground.stopRippleAnimation();
                        }
                        ad_editor.putInt("status",adBlocker_on);ad_editor.commit();
                        break;
                }
                return true;
            }
        });
    }
}
