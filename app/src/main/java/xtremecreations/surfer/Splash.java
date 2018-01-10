package xtremecreations.surfer;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ProgressBar;

public class Splash extends Activity {
    int exit=0;
    ProgressBar load;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {getWindow().setStatusBarColor(ContextCompat.getColor(Splash.this, R.color.maincolor));}
        setContentView(R.layout.splash);
        load=(ProgressBar)findViewById(R.id.load);
        try{exit=getIntent().getExtras().getInt("exit");}catch(Exception e){}
        if(exit==0){new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {load.setVisibility(View.GONE);
                if(exit==0){startActivity(new Intent(Splash.this, Home.class));
                finish();overridePendingTransition(R.anim.splash_in,R.anim.splash_out);}
            }
        },800);}
        else if(exit==1){load.setVisibility(View.GONE);
            new Handler().postDelayed(new Runnable() {@Override public void run()
            {Splash.this.finish();Splash.this.overridePendingTransition(R.anim.exitsplash,R.anim.exitmain);}},1000);}
    }
}