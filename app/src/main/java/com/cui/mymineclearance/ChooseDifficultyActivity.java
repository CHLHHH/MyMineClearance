package com.cui.mymineclearance;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class ChooseDifficultyActivity extends AppCompatActivity {

    private int iColumn, iRow, iMineCount;
    private Intent oIntent;
    public final int CODE = 0x717;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_difficulty);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.BasicButton: // 点击了初级按钮
                oIntent = new Intent(ChooseDifficultyActivity.this, GameSceneActivity.class);
                iColumn = 9;
                iRow = 9;
                iMineCount = 10;
                oIntent.putExtra("Column", iColumn);
                oIntent.putExtra("Row", iRow);
                oIntent.putExtra("MineCount", iMineCount);
                oIntent.putExtra("Difficult", 0);
                startActivityForResult(oIntent, CODE);
                break;
            case R.id.IntermediateButton:// 点击了中级按钮
                oIntent = new Intent(ChooseDifficultyActivity.this, GameSceneActivity.class);
                iColumn = 16;
                iRow = 16;
                iMineCount = 40;
                oIntent.putExtra("Column", iColumn);
                oIntent.putExtra("Row", iRow);
                oIntent.putExtra("MineCount", iMineCount);
                oIntent.putExtra("Difficult", 1);
                startActivityForResult(oIntent, CODE);
                break;
            case R.id.AdvancedButton:// 点击了高级按钮
                oIntent = new Intent(ChooseDifficultyActivity.this, GameSceneActivity.class);
                iColumn = 16;
                iRow = 25;
                iMineCount = 90;
                oIntent.putExtra("Column", iColumn);
                oIntent.putExtra("Row", iRow);
                oIntent.putExtra("MineCount", iMineCount);
                oIntent.putExtra("Difficult", 2);
                //开启activity
                startActivityForResult(oIntent, CODE);
            case R.id.ReturnButton:// 点击了返回按钮
                finish();
        }
    }
}