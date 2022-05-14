package com.cui.mymineclearance;

import android.util.Log;

import java.util.Random;

public class MineZone {

    public final static int EMPTY = 0;
    public final static int MINE = -1;

    public int firRow;
    public int firCol;

    public int iColumn; //列数，x值
    public int iRow; //行数，y值
    public int iMineCount; //雷的总数


    public int[][] aZone;
    public boolean[][] aOpen;

    /**
     * @param row       行数
     * @param column    列数
     * @param mineCount 雷的数目
     */
    public MineZone(int row, int column, int mineCount) {
        iColumn = column;
        iRow = row;
        iMineCount = mineCount;
        aZone = new int[iRow][iColumn];
        aOpen = new boolean[iRow][iColumn];
//        InitMineZone();
    }

    /**
     * 递归调用开启一片区域
     */
    public void OpenZone(int y, int x) {
        //已经翻开的区域就直接返回
        if (aOpen[y][x] == true || aZone[y][x] == MINE)
            return;
//
//        //处理待翻开区域是雷的情况
//        if (aZone[y][x] == MINE)
//            return;

        //不是空白就标记一下
        if (aZone[y][x] != EMPTY) {
            aOpen[y][x] = true;
        }

        //处理待翻开区域是空白的情况
        if (aZone[y][x] == EMPTY) {
            //翻开，然后递归包括自己在内的周围的9个位置
            aOpen[y][x] = true;
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    if (y + i >= 0 && y + i < iRow && x + j >= 0 && x + j < iColumn) {
                        //自己递归自己已经在前面处理过（已经翻开）
                        OpenZone(y + i, x + j);
                    }
                }
            }
        }


    }
    public void InitMineZone() {
        int randX, randY = 0;
        //创建随机器
        Random rand = new Random();
        for (int i = 0; i < iMineCount; i++) {
            randY = rand.nextInt(iRow); // 11
            randX = rand.nextInt(iColumn); //生成的随机数不包括上界[0, iColumn) 9
            while (aZone[randY][randX] == MINE || (randX == firCol && randY == firRow)) {
                randX = rand.nextInt(iColumn); //生成的随机数不包括上界[0, iColumn)
                randY = rand.nextInt(iRow);
            }
            aZone[randY][randX] = MINE;
        }
        //对每个方块都进行四周8个块的查询 下面是填充数字
        for (int y = 0; y < iRow; y++) {
            for (int x = 0; x < iColumn; x++) {
                //雷区就不进行填充
                if (aZone[y][x] != EMPTY)
                    continue;
                //对周围包括自己在内的9格区域进行检索
                for (int i = -1; i < 2; i++) {
                    for (int j = -1; j < 2; j++) {
                        if (y + i >= 0 && y + i < iRow && x + j >= 0 && x + j < iColumn) {
                            if (aZone[y + i][x + j] == MINE)
                                aZone[y][x]++;
                        }
                    }
                }
            }
        }

    }

}
