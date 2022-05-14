package com.cui.mymineclearance;

import static java.lang.Thread.sleep;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GameSceneActivity extends AppCompatActivity {
    private ImageButton[][] imgButtons;
    private ImageView[][] imgViews;
    private TextView timerTv;
    private TextView remainderTv;
    private ImageView difficultyIv;
    private GridLayout mineLayout;
    private boolean gamePause;
    int gameTime = 0;
    private ThreadSafe thead;
    private int[] imageRes;
    private int mineHeigh;
    private int mineWidth;
    private MineZone mineZone;
    private int mineNums;
    private int difficultyNum;

    boolean first = true;
    boolean winOr = false;


    private Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    timerTv.setText("时间: " + gameTime);
                    break;
                case 2:

                    if (!winOr) {
                        tiy.setText("很遗憾,踩雷了!!!");
                    }
                    // 后面是开启失败界面
                    tyjm.setVisibility(View.VISIBLE);
                    tiy.setVisibility(View.VISIBLE);
                    fanhuicaidan.setVisibility(View.VISIBLE);
                    zlyc.setVisibility(View.VISIBLE);
                    pause.setVisibility(View.GONE);
                    returnPage.setVisibility(View.GONE);
                    break;
            }
        }
    };
    private Object lock = new Object();
    private ImageButton pause, returnGame;
    private ImageButton returnPage;
    private int leftoverMine;
    private boolean gameOver;
    private boolean[][] isFlag;
    private TextView tiy;
    private ImageView tyjm, fanhuicaidan, zlyc;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_scene);


        Intent intent = getIntent();
        if (intent != null) {
            mineWidth = intent.getIntExtra("Column", 9);
            mineHeigh = intent.getIntExtra("Row", 11);
            mineNums = intent.getIntExtra("MineCount", 10);
            difficultyNum = intent.getIntExtra("Difficult", 0);
            Log.d("sadsa", "onCreate: " + difficultyNum);
        } else {
            Log.d("sadsa", "onCreate:intent " + difficultyNum);
            mineWidth = 9;
            mineHeigh = 11;
            mineNums = 10;
            difficultyNum = 0;
        }
        initView(); // 实例化,找到控件
        startTimer(); // 开启计时器
        setDifficulty(); // 设置难易等级布局
        initImage(); // 实例化图片
        onCreateGame(); // 加载整个雷的布局
    }

    private void onCreateGame() {  // 加载整个雷的布局
        leftoverMine = mineNums;
        upDateMineNum();
        mineLayout.setRowCount(mineHeigh);
        mineLayout.setColumnCount(mineWidth);
        WindowManager wm = this.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        Log.d("qwer", "onCreateGame: width" + width); //1080
        Log.d("qwer", "onCreateGame: height" + height); //2264
        int length;
        if ((width - mineWidth * 2) / mineWidth > (height - 200) / mineHeigh) {
            length = (height - 200) / mineHeigh;
        } else {
            length = (width - mineWidth * 2) / mineWidth;
        }
        // 初级 118 9 X 9   中级 65 16 x 16   高级 65 16 x 30
        Log.d("qwer", "onCreateGame: length" + length);
        for (int i = 0; i < mineLayout.getRowCount(); i++) {
            for (int j = 0; j < mineLayout.getColumnCount(); j++) {
                GridLayout.LayoutParams glParam = new GridLayout.LayoutParams(
                        GridLayout.spec(i), GridLayout.spec(j));
                glParam.width = length; // 每一个item的宽
                glParam.height = length;
                glParam.setMargins(1, 1, 1, 1); //设置边距
                final ImageButton btn = new ImageButton(GameSceneActivity.this);
                btn.setBackgroundResource(R.drawable.button_0); // 盖在上面的
                btn.setScaleType(ImageView.ScaleType.FIT_XY); // 拉伸
                btn.setLayoutParams(glParam); //
                final ImageView img = new ImageView(GameSceneActivity.this);
                img.setBackgroundResource(R.drawable.empty_0);
                img.setScaleType(ImageView.ScaleType.FIT_XY); // 拉伸
                img.setLayoutParams(glParam); //
                img.setVisibility(View.GONE);
                imgButtons[i][j] = btn;
                imgViews[i][j] = img;
                int tempH = i;
                int tmepW = j;
                first = true;

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (first) {
                            mineZone.firRow = tempH;
                            mineZone.firCol = tmepW;
                            first = false;
                            mineZone.InitMineZone();
                        }


                        if (!mineZone.aOpen[tempH][tmepW] && !isFlag[tempH][tmepW]) {
                            mineZone.OpenZone(tempH, tmepW);
                            refreshZone();

                            if (mineZone.aZone[tempH][tmepW] == -1) {

//                                thead.interrupt();
                                gameOver = true;
                                gamePause = true;
                                for (int i = 0; i < mineHeigh; i++) {
                                    for (int j = 0; j < mineWidth; j++) {
                                        if (mineZone.aZone[i][j] == -1) {
                                            imgViews[i][j].setVisibility(View.VISIBLE);
                                            imgViews[i][j].setImageDrawable(getResources().getDrawable(imageRes[9]));
                                            imgButtons[i][j].setVisibility(View.GONE);
                                        }
                                    }
                                }
                                imgViews[tempH][tmepW].setImageDrawable(getResources().getDrawable(imageRes[10]));

                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            RankBean abcdef = new RankBean(difficultyNum + "", "abcdef", gameTime + "");
                                            DBManager.insertItemToAccounttb(abcdef);
                                            sleep(1500);
                                            Message message = new Message();
                                            message.what = 2;
                                            handler.sendMessage(message);

                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();

                            } else {
                                int com = 0;
                                for (int i = 0; i < mineHeigh; i++) {
                                    for (int j = 0; j < mineWidth; j++) {
                                        if (!mineZone.aOpen[i][j]) {
                                            com++;
                                        }
                                    }
                                }
                                if (com == mineNums) {


                                    tiy.setText("恭喜你,赢了!!!");
                                    int i = gameTime;

                                    tyjm.setVisibility(View.VISIBLE);
                                    tiy.setVisibility(View.VISIBLE);
                                    fanhuicaidan.setVisibility(View.VISIBLE);
                                    zlyc.setVisibility(View.VISIBLE);
                                    pause.setVisibility(View.GONE);
                                    returnPage.setVisibility(View.GONE);
                                    RankBean abcdef = new RankBean(difficultyNum + "", "abcdef", gameTime + "");
                                    DBManager.insertItemToAccounttb(abcdef);
                                }
                            }

                        }


                    }
                });


                btn.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        if (!mineZone.aOpen[tempH][tmepW]) {
                            if (isFlag[tempH][tmepW]) { // 是旗子就取消
                                isFlag[tempH][tmepW] = false;
                                btn.setBackgroundResource(imageRes[12]);
                                leftoverMine++; // 剩余雷数加1
                            } else {
                                isFlag[tempH][tmepW] = true; // 不是旗子就标旗子
                                btn.setBackgroundResource(imageRes[11]);
                                leftoverMine--;
                            }
                            upDateMineNum(); // 更新雷数
                        }
                        return true;
                    }
                });
                mineLayout.addView(btn);
                mineLayout.addView(img);

            }
        }


    }

    private void refreshZone() {
        for (int i = 0; i < mineHeigh; i++) {
            for (int j = 0; j < mineWidth; j++) {
                if (mineZone.aOpen[i][j] && !isFlag[i][j]) {
                    imgViews[i][j].setVisibility(View.VISIBLE);
                    imgViews[i][j].setImageDrawable(getResources().getDrawable(getImage(i, j)));
                    imgButtons[i][j].setVisibility(View.GONE);
                }
            }
        }
    }


    public void initImage() {
        imageRes = new int[15];
        //0~8
        imageRes[0] = R.drawable.empty_0;
        imageRes[1] = R.drawable.n1_0;
        imageRes[2] = R.drawable.n2_0;
        imageRes[3] = R.drawable.n3_0;
        imageRes[4] = R.drawable.n4_0;
        imageRes[5] = R.drawable.n5_0;
        imageRes[6] = R.drawable.n6_0;
        imageRes[7] = R.drawable.n7_0;
        imageRes[8] = R.drawable.n8_0;
        //地雷
        imageRes[9] = R.drawable.gmine_0;
        imageRes[10] = R.drawable.rmine_0;
        //旗子
        imageRes[11] = R.drawable.flag_0;
        //区块
        imageRes[12] = R.drawable.button_0;
        //背景
        imageRes[13] = R.drawable.background_0;
        //错误旗子
        imageRes[14] = R.drawable.flagerr_0;


    }

    private int getImage(int height, int width) {
        int imgName = imageRes[0];
        switch (mineZone.aZone[height][width]) {
            case -1:
                imgName = imageRes[9];
                break;
            case 0:
                imgName = imageRes[0];
                break;
            case 1:
                imgName = imageRes[1];
                break;
            case 2:
                imgName = imageRes[2];
                break;
            case 3:
                imgName = imageRes[3];
                break;
            case 4:
                imgName = imageRes[4];
                break;
            case 5:
                imgName = imageRes[5];
                break;
            case 6:
                imgName = imageRes[6];
                break;
            case 7:
                imgName = imageRes[7];
                break;
            default:
                imgName = imageRes[8];
                break;
        }
        return imgName;
    }


    private void upDateMineNum() {
        remainderTv.setText("剩余雷数: " + leftoverMine);
    }

    private void setDifficulty() { // 设置难易等级布局
        switch (difficultyNum) {
            case 0:
                difficultyIv.setImageDrawable(getResources().getDrawable(R.drawable.primary));
                break;
            case 1:
                difficultyIv.setImageDrawable(getResources().getDrawable(R.drawable.intermediate_title));
                break;
            case 2:
                difficultyIv.setImageDrawable(getResources().getDrawable(R.drawable.senior));
                break;
            default:
                difficultyIv.setImageDrawable(getResources().getDrawable(R.drawable.primary));
                break;
        }
    }

    private void startTimer() {  // 开启计时器
        thead = new ThreadSafe();
        thead.start();
    }


    public class ThreadSafe extends Thread {

        @Override
        public void run() {
            super.run();
            while (!isInterrupted()) {

                while (gamePause) { // true 表示暂停
                    synchronized (lock) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }
                Message obtain = Message.obtain();
                obtain.what = 1;
                handler.sendMessage(obtain);
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                gameTime++;
            }
        }

    }

    private void initView() {
        timerTv = findViewById(R.id.timer);
        remainderTv = findViewById(R.id.mineNum);
        difficultyIv = findViewById(R.id.difficulty);
        mineLayout = findViewById(R.id.mineLayout);
        returnGame = findViewById(R.id.returnGame);
        pause = findViewById(R.id.pause);
        returnPage = findViewById(R.id.returnPage);
        isFlag = new boolean[mineHeigh][mineWidth];
        imgButtons = new ImageButton[mineHeigh][mineWidth];
        mineZone = new MineZone(mineHeigh, mineWidth, mineNums);
        imgViews = new ImageView[mineHeigh][mineWidth];
        tiy = findViewById(R.id.tiy);
        tyjm = findViewById(R.id.tyjm);
        zlyc = findViewById(R.id.zlyc);
        fanhuicaidan = findViewById(R.id.fanhuicaidan);


    }

    public void returnGame() {
        gameTime = 0;
        timerTv.setText("时间: " + gameTime);
        gamePause = false;
        synchronized (lock) {
            lock.notifyAll();
        }
        returnPage.setVisibility(View.VISIBLE);
        returnGame.setVisibility(View.GONE);
        pause.setVisibility(View.VISIBLE);
        mineZone = new MineZone(mineHeigh, mineWidth, mineNums);
        isFlag = new boolean[mineHeigh][mineWidth];
        leftoverMine = mineNums;
        upDateMineNum();  // 更新雷数
        tyjm.setVisibility(View.GONE);
        tiy.setVisibility(View.GONE);
        fanhuicaidan.setVisibility(View.GONE);
        zlyc.setVisibility(View.GONE);
        onCreateGame();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.restart:
                returnGame();
                break;
            case R.id.pause: // 点击了暂停
                gamePause = true;
                returnGame.setVisibility(View.VISIBLE);
                pause.setVisibility(View.GONE);
                Toast.makeText(GameSceneActivity.this, "游戏暂停了", Toast.LENGTH_SHORT).show();
                break;
            case R.id.returnGame: // 点击继续游戏
                gamePause = false;
                returnGame.setVisibility(View.GONE);
                pause.setVisibility(View.VISIBLE);
                Toast.makeText(GameSceneActivity.this, "游戏继续", Toast.LENGTH_SHORT).show();
                synchronized (lock) {
                    lock.notifyAll();
                }
                break;
            case R.id.returnPage: // 返回主菜单
                //返回主菜单
                Intent intent = new Intent(GameSceneActivity.this, MainActivity.class);
                intent.putExtra("ReturnPage", true);
                setResult(0x718, intent);
                finish();//返回主菜单

                break;
            case R.id.fanhuicaidan: // 返回主菜单
                //返回主菜单
                Intent intent1 = new Intent(GameSceneActivity.this, MainActivity.class);
                intent1.putExtra("ReturnPage", true);
                setResult(0x718, intent1);
                finish();//返回主菜单
                break;
            case R.id.zlyc: // 再来一次
                returnGame();
                break;
        }
    }
}