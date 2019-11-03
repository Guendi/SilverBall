package com.example.silverball;

import android.content.Context;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.silverball.tools.BaseGameActivity;
import com.example.silverball.tools.SampleAnimationListener;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.InputStream;


public class MainActivity extends BaseGameActivity implements View.OnClickListener, GameEngine.OnBallInHoleListener, GameEngine.OnGameOverListener {

    private final static String FONTNAME="jandamanateesolid";
    private GameView gameView;
    //private GameSurfaceView gameView;
    private ViewGroup container;
    private GameEngine gameEngine;
    private LevelPack levelPack;
    private int level;
    private int totalPoints;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addTypeface(FONTNAME);
        setTypeface((TextView) findViewById(R.id.title), FONTNAME);
        setTypeface((TextView) findViewById(R.id.score), FONTNAME);
        findViewById(R.id.title).setOnClickListener(this);
        container = (ViewGroup) findViewById(R.id.container);
        gameView = new GameView(this);
        //gameView = new GameSurfaceView(this);
        gameView.setTypeface(getTypeface(FONTNAME));
        container.addView(gameView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        gameOver();
        //read levels
        try {
            InputStream source = getAssets().open("levels.xml");
            Serializer serializer = new Persister();
            levelPack = serializer.read(LevelPack.class, source);
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "loading levels threw exception", e);
        }
    }


    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.title) {
            Animation a = AnimationUtils.loadAnimation(this,R.anim.fade_out);
            a.setAnimationListener(new SampleAnimationListener() {
                @Override
                public void onAnimationEnd(Animation animation) {
                    //start game after anim
                    startGame();
                }
            });
            view.startAnimation(a);
        }
    }
    //hide text
    // set game visible
    // start level
    private void startGame() {
        hideView(R.id.title);
        hideView(R.id.score);
        gameView.setVisibility(View.VISIBLE);
        level=0;
        totalPoints =0;
        gameView.setTotalPoints(totalPoints);
        startLevel();
    }

    private void startLevel() {
        Level l = levelPack.getLevels().get(level);
        gameEngine = new GameEngine((SensorManager)getSystemService(Context.SENSOR_SERVICE),gameView,this,this);
        float bd = gameView.getBaseDimension();
        //Log.d("dimension","bd is: "+bd);
        //set Playground
        gameEngine.setRegion(bd /2, bd /2,container.getWidth()- bd /2,container.getHeight()- bd /2);
        float horDim = container.getWidth()/16;
        float verDim = container.getHeight()/9;
        /**
         * set ball position
         * set hole position
         * set time
         * set walls
         */
        gameEngine.setBallPosition(l.getBall().getStartx() * horDim, l.getBall().getStarty() * verDim);
        gameEngine.setHolePosition(l.getHole().getX()*horDim, l.getHole().getY()*verDim, bd /2);
        gameEngine.setTime(l.getTime());
        gameEngine.setPointsStart(l.getPoints());
        for(Obstacle o : l.getObstacles()) {
            gameEngine.addObstacle(o.getType(), o.getX()*horDim, o.getY()*verDim, o.getW()*horDim, o.getH()*verDim);
        }
        gameEngine.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(gameEngine!=null) gameEngine.stop();
    }

    @Override
    public void onBallInHole(int score) {
        totalPoints += score;
        gameView.setTotalPoints(totalPoints);
        gameView.invalidate();
        level++;
        if(levelPack.getLevels().size() > level) {
            startLevel();
        } else {
            gameOver();
        }
    }

    private void gameOver() {
        gameView.setVisibility(View.GONE);
        showView(R.id.title);
        setText(R.id.score, getString(R.string.score) + " " + Integer.toString(totalPoints));
        showView(R.id.score);
    }


    @Override
    public void onGameOver() {
        gameOver();
    }
}
