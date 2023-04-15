package com.example.spaceinvaders.managers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.spaceinvaders.R;
import com.example.spaceinvaders.bluetooth.TransferService;
import com.example.spaceinvaders.database.Counter;
import com.example.spaceinvaders.gui.MyButton;
import com.example.spaceinvaders.logic.BTGyroscopeMoveProvider;
import com.example.spaceinvaders.logic.BluetoothMoveProvider;
import com.example.spaceinvaders.logic.BoxFactoryImpl;
import com.example.spaceinvaders.logic.BoxSpawnerImpl;
import com.example.spaceinvaders.logic.BulletFactoryImpl;
import com.example.spaceinvaders.logic.GameStateImpl;
import com.example.spaceinvaders.logic.PathFactoryImpl;
import com.example.spaceinvaders.logic.ShipBulletSupplierImpl;
import com.example.spaceinvaders.logic.ShipFactoryImpl;
import com.example.spaceinvaders.logic.VillainBulletsSupplier;
import com.example.spaceinvaders.logic.VillainFactoryImpl;
import com.example.spaceinvaders.logic.interfaces.Box;
import com.example.spaceinvaders.logic.interfaces.Bullet;
import com.example.spaceinvaders.logic.interfaces.BulletsSupplier;
import com.example.spaceinvaders.logic.interfaces.GameState;
import com.example.spaceinvaders.logic.GyroscopeMoveProvider;
import com.example.spaceinvaders.logic.interfaces.Ship;
import com.example.spaceinvaders.logic.interfaces.Villain;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@SuppressLint("ViewConstructor")
public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    public enum GameType{
        NORMAL, SURVIVAL, HOST, CLIENT
    }

    private MainThread mainThread;
    private final DrawManager drawManager;
    private final GameLogicManager gameLogicManager;
    private final MyButton moveButton;
    private final GameState gameState;
    private final GyroscopeManager gyroscopeManager;
    private int gameEndCooldown = 200;

    public GameView(Context context, GameType type){
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);

        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

        List<Ship> ships = new ArrayList<>();
        Bullet.BulletFactory bulletFactory = new BulletFactoryImpl(this);
        BulletsSupplier shipBulletsSupplier = new ShipBulletSupplierImpl(bulletFactory);
        Ship.ShipFactory shipFactory = new ShipFactoryImpl();

        gyroscopeManager = new GyroscopeManager(context);
        Ship ship;
        boolean singlePlayer = type.equals(GameType.NORMAL) || type.equals(GameType.SURVIVAL);
        if(singlePlayer){
            ship = shipFactory.produce(context, new Point(screenWidth / 2, screenHeight), shipBulletsSupplier, new GyroscopeMoveProvider(gyroscopeManager));
        }
        else{
            ship = shipFactory.produce(context, new Point(screenWidth / 3, screenHeight), shipBulletsSupplier, new BTGyroscopeMoveProvider(gyroscopeManager, context));
            ships.add(ship);
            ship = shipFactory.produce(context, new Point(2*screenWidth /3, screenHeight), shipBulletsSupplier, new BluetoothMoveProvider(context));
        }
        ships.add(ship);

        int seed;
        if(type.equals(GameType.CLIENT)){
            seed = TransferService.read(context);
        }
        else{
            seed = new Random().nextInt();
            if(type.equals(GameType.HOST)){
                TransferService.write(context, ByteBuffer.allocate(4).putInt(seed).array());
            }
        }


        gameState = new GameStateImpl(this, ships);
        drawManager = new DrawManager(gameState);
        BulletsSupplier bulletsSupplier = new VillainBulletsSupplier(bulletFactory);
        int finalSeed = seed;
        Villain.ShootFunction shootFunction = new Villain.ShootFunction() {
            private int shootCooldown = 30;
            private final Random random = new Random(finalSeed);

            @Override
            public List<Bullet> shoot(Point pos) {
                List<Bullet> bullets = new ArrayList<>();
                if(shootCooldown <= 0 && random.nextInt(250) == 0){
                    shootCooldown = 30;
                    return bulletsSupplier.produce(null, pos.x, pos.y);
                }
                shootCooldown--;
                return bullets;
            }
        };

        LevelManager levelManager;
        WaveCreator waveCreator = new WaveCreatorImpl(new PathFactoryImpl());
        Villain.VillainFactory villainFactory = new VillainFactoryImpl(this);


        if(type.equals(GameType.NORMAL)){
            levelManager = new NormalLevelManager(context, waveCreator, new NormalVillainsCreator(context, villainFactory, shootFunction));
        }
        else{
            levelManager = new SurvivalLevelManager(waveCreator, new SurvivalVillainsCreator(villainFactory, shootFunction, seed), seed);
        }


        Map<Box.BoxType, Box.OpenBoxFunction> openBoxMap = new HashMap<>();
        openBoxMap.put(Box.BoxType.UPGRADE, (s)->{
            Counter.increase(Counter.AchievementType.UPGRADES, 1);
            s.upgrade();
        });
        openBoxMap.put(Box.BoxType.HEAL, (s)->{
            Counter.increase(Counter.AchievementType.HEAL, 1);
            s.changeHp(1);});

        Box.BoxSpawner boxSpawner = new BoxSpawnerImpl(new BoxFactoryImpl(this, openBoxMap), seed);

        gameLogicManager = new GameLogicManager(gameState, levelManager, boxSpawner);
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
        if(gameState.getGameOver() || gameState.getYouWon()){
            if(gameEndCooldown>0){
                gameEndCooldown--;
            }
            else{
                Activity activity = (Activity) getContext();
                activity.finish();
            }
        }
        gameLogicManager.update();
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
