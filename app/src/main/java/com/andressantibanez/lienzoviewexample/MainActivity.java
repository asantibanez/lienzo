package com.andressantibanez.lienzoviewexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.andressantibanez.lienzo.LienzoView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Horizontal
        setContentView(R.layout.activity_main_scrollable);
        //Vertical
        //setContentView(R.layout.activity_main);

        if (savedInstanceState == null ) {
            LienzoView lienzoView = (LienzoView) findViewById(R.id.lienzo);
            lienzoView.addImage("http://img.sparknotes.com/content/sparklife/sparktalk/REDlaser_LargeWide.jpg");
            lienzoView.addImage("http://static.tvtropes.org/pmwiki/pub/images/marvel-universe.jpg");
            lienzoView.addImage("http://i.annihil.us/u/prod/marvel//universe3zx/images/8/8f/UltimateMarvelUniverse.jpg");
            lienzoView.addImage("http://ak-hdl.buzzfed.com/static/2015-01/20/17/enhanced/webdr04/enhanced-17383-1421792979-8.jpg");

            lienzoView.loadImages();
        }
    }
}
