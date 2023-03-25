package com.example.spaceinvaders.managers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.spaceinvaders.R;
import com.example.spaceinvaders.activities.MainActivity;
import com.example.spaceinvaders.database.AppDataBase;
import com.example.spaceinvaders.database.Counter;
import com.example.spaceinvaders.database.HighScore;
import com.example.spaceinvaders.gui.MyButton;
import com.example.spaceinvaders.logic.BulletFactoryImpl;
import com.example.spaceinvaders.logic.GameStateImpl;
import com.example.spaceinvaders.logic.PathFactoryImpl;
import com.example.spaceinvaders.logic.VillainBulletsSupplier;
import com.example.spaceinvaders.logic.VillainFactoryImpl;
import com.example.spaceinvaders.logic.interfaces.Bullet;
import com.example.spaceinvaders.logic.interfaces.BulletsSupplier;
import com.example.spaceinvaders.logic.interfaces.GameState;
import com.example.spaceinvaders.logic.interfaces.Path;
import com.example.spaceinvaders.logic.interfaces.Villain;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread mainThread;
    private final DrawManager drawManager;
    private final GameLogicManager gameLogicManager;
    private final GyroscopeManager gyroscopeManager;
    private final MyButton moveButton;
    private final GameState gameState;
    private int gameOverCooldown = 200;

    public GameView(Context context){
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);

        gameState = new GameStateImpl(this);
        drawManager = new DrawManager(gameState);
        Path.PathFactory pathFactory = new PathFactoryImpl();
        Villain.VillainFactory villainFactory = new VillainFactoryImpl(this);
        Bullet.BulletFactory bulletFactory = new BulletFactoryImpl(this);
        BulletsSupplier bulletsSupplier = new VillainBulletsSupplier(bulletFactory);
        WaveManager waveManager = new WaveManagerImpl(villainFactory, pathFactory, bulletsSupplier);
        gameLogicManager = new GameLogicManager(gameState, waveManager);
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
        if(gameState.getGameOver()){
            if(gameOverCooldown>0){
                gameOverCooldown--;
            }
            else{
                Activity activity = (Activity) getContext();
                activity.finish();
            }
        }
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

        mainThread.setRunning(false);

        while (mainThread!=null){
            try {
                mainThread.join();
                mainThread = null;
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }

    }
}
