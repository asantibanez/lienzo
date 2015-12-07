package com.andressantibanez.lienzoviewexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.andressantibanez.lienzo.LienzoView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null ) {
            LienzoView lienzoView = (LienzoView) findViewById(R.id.lienzo);
            lienzoView.addImage("http://rxboilerroom.com/wp-content/uploads/2015/04/The-Avengers.png");
            lienzoView.addImage("http://images.cdn.stuff.tv/sites/stuff.tv/files/brands/avengers_age_of_ultron.jpg");
            lienzoView.addImage("http://screenrant.com/wp-content/uploads/Avengers-2-Age-of-Ultron-Character-Posters-Collage.jpg");

            lienzoView.loadImages();
        }
    }
}
