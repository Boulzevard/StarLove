package fr.wcs.starlove;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread myThread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(4000);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        ImageView image = findViewById(R.id.imageView_logo);
        animsimple(image);
        myThread.start();
    }
    public void animsimple(ImageView imagehero){
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(2000); //You can manage the blinking time with this parameter
        imagehero.startAnimation(anim);

    }
}
