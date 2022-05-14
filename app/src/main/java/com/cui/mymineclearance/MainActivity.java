package com.cui.mymineclearance;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,4);
    }

    //按钮点击事件
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.DifficultyButton: // 点击了难度按钮
                startActivity(new Intent(MainActivity.this, ChooseDifficultyActivity.class));
                break;
            case R.id.RankinglistButton: // 点击了排行榜按钮
                startActivity(new Intent(MainActivity.this, RankActivity.class));
                break;
            case R.id.ExitButton: // 点击了退出按钮
                android.os.Process.killProcess(android.os.Process.myPid());
                finish();
                finish();
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
                break;
        }
    }

}