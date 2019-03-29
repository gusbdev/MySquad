package com.projects.gus.mysquad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class SplashScreen extends Activity {
  private static int SPLASH_TIME_OUT = 5000;
  ImageView img;
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(R.layout.activity_splash);
    //this.img = ((ImageView)findViewById(2131427444));
    new Handler().postDelayed(new Runnable()
    {
      public void run()
      {
        Intent localIntent = new Intent(SplashScreen.this, MainActivity.class);
        startActivity(localIntent);
        finish();
      }
    }, SPLASH_TIME_OUT);
  }
}
