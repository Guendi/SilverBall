package com.example.silverball.tools;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hami on 31.10.2019
 */
public class BaseGameActivity extends Activity {

    private Map<String,Typeface> typefaces = new HashMap<String,Typeface>();
    private float density;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        density = getResources().getDisplayMetrics().density;

    }

    protected float scale(float v) {
        return  density*v;
    }
    //change font
    protected void addTypeface(String name) {
        Typeface typeface = Typeface.createFromAsset(getAssets(),name+".ttf");
        typefaces.put(name, typeface);
    }

    protected void setTypeface(TextView v, String typefaceName) {
        Typeface t = getTypeface(typefaceName);
        if(t!=null) {
            v.setTypeface(t);
        }
    }

    protected Typeface getTypeface(String typefaceName) {
        return typefaces.get(typefaceName);
    }

    //hide the view with the id
    protected void hideView(int id) {
        findViewById(id).setVisibility(View.GONE);
    }
    // set the id to visible
    protected void showView(int id) {
        findViewById(id).setVisibility(View.VISIBLE);
    }

    // changed text
    protected  void setText(int id, String text) {
        ((TextView)findViewById(id)).setText(text);
    }

}
