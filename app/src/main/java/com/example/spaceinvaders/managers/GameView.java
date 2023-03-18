package com.example.spaceinvaders.managers;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.spaceinvaders.R;
import com.example.spaceinvaders.gui.MyButton;
import com.example.spaceinvaders.logic.GameStateImpl;
import com.example.spaceinvaders.logic.interfaces.GameState;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread mainThread;
    private final DrawManager drawManager;
    private final GameLogicManager gameLogicManager;
    private final GyroscopeManager gyroscopeManager;
    private final MyButton moveButton;

    public GameView(Context context){
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);

        GameState gameState = new GameStateImpl(this);
        drawManager = new DrawManager(gameState);
        gameLogicManager = new GameLogicManager(gameState);
        gyroscopeManager = new GyroscopeManager(context);
        moveButton = new MyButton(Resources.getSystem().getDisplayMetrics().widthPixels, Resources.getSystem().getDisplayMetrics().heightPixels/2,
                gameState::setMovable, BitmapFactory.decodeResource(getResources(), R.drawable.move));
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        drawManager.draw(canvas);
        moveButton.draw(canvas);
    }

    public void update(){
        gameLogicManager.update(gyroscopeManager.getGyroscopeValues());
    }

    public void screenContact(float x, float y, boolean pressed){
        moveButton.press(x, y, pressed);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        gyroscopeManager.register();
        mainThread = new MainThread(getHolder(), this);
        mainThread.setRunning(true);
        mainThread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        gyroscopeManager.unregister();
        boolean again = true;

        while (again){
            try {
                mainThread.setRunning(false);
                mainThread.join();
                again = false;
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }

    }
}
