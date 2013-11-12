package com.github.mccxj.go;

import com.github.mccxj.go.sgf.SGFChecker;
import com.github.mccxj.go.sgf.SGFGame;
import gnu.trove.iterator.TIntIterator;
import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;
import gnu.trove.list.linked.TIntLinkedList;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

import java.util.Arrays;
import java.util.BitSet;

import static com.github.mccxj.go.GameConstants.*;

public class GoGame {
    private int curr = BLACK;
    
    private int[][] stones = new int[SIZE][SIZE];
    private TIntList steps = new TIntArrayList();
    private TIntObjectMap<TIntList> kills = new TIntObjectHashMap<TIntList>();
    private static final int[][] POSES = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};//上下左右
    private SGFGame sgfgame;
    private SGFChecker checker;

    public boolean addStone(int posX, int posY) {
        //越界,无法落子
        if (posX < 0 || posX >= SIZE || posY < 0 || posY >= SIZE)
            return false;
        //已经被占用,无法落子
        if (stones[posX][posY] != EMPTY)
            return false;
        
        stones[posX][posY] = curr;
        if (tryStone(this.curr, posX, posY)) {
            steps.add(posX * SIZE + posY);
            if (this.curr == BLACK) {
                this.curr = WHITE;
            } else {
                this.curr = BLACK;
            }
            return true;
        } else {
            // 恢复原样
            stones[posX][posY] = EMPTY;
            return false;
        }
    }
    
    public int next(int posX, int posY){
        if(checker != null){
            return checker.checkNext(posX, posY);
        }
        return GameConstants.FAIL;
    }

    private boolean tryStone(int curr, int posX, int posY) {
        boolean ok = false;
        BitSet killmarks = new BitSet(SIZE * SIZE);
        for (int[] pos : POSES) {
            int newPosX = posX + pos[0];
            int newPosY = posY + pos[1];

            if (newPosX >= 0 && newPosX < SIZE && newPosY >= 0 && newPosY < SIZE)
                if (stones[newPosX][newPosY] != EMPTY && stones[newPosX][newPosY] != curr) {
                    BitSet marks = new BitSet(SIZE * SIZE);
                    if (!checkValidQi(stones[newPosX][newPosY], marks, newPosX, newPosY)) {
                        killmarks.or(marks);
                        ok = true;
                    }
                }
        }

        if (ok) {
            if (isDaJie(killmarks, posX, posY)) {
                return false;
            } else {
                kill(killmarks);
                return true;
            }
        }

        BitSet marks = new BitSet(SIZE * SIZE);
        if (checkValidQi(curr, marks, posX, posY)) {
            return true;
        }
        return false;
    }

    private boolean isDaJie(BitSet killmarks, int posX, int posY) {
        if (steps.isEmpty())
            return false;

        TIntList lastkill = kills.get(steps.size() - 1);
        // 上一步吃子只有一个
        if (lastkill == null || lastkill.size() != 1)
            return false;

        int i = lastkill.get(0);
        // 上一步吃子就是当前落子位置
        if (i != posX * SIZE + posY)
            return false;

        int laststep = steps.get(steps.size() - 1);
        // 上一步位置在吃子列表中
        if (!killmarks.get(laststep))
            return false;

        // 只需判断上一步落子的四周,看看是否全属于越界或敌方子力情况,如果是,则属于打劫情况
        for (int[] pos : POSES) {
            int newPosX = laststep / SIZE + pos[0];
            int newPosY = laststep % SIZE + pos[1];

            if (newPosX >= 0 && newPosX < SIZE && newPosY >= 0 && newPosY < SIZE)
                if (stones[newPosX][newPosY] == EMPTY || stones[newPosX][newPosY] != curr)
                    return false;
        }
        return true;
    }

    /**
     * 把标记过的位置全部清除
     */
    private void kill(BitSet marks) {
        TIntList ks = kills.get(steps.size());
        if (ks == null) {
            ks = new TIntLinkedList();
            kills.put(steps.size(), ks);
        }
        for (int i = 0; i < SIZE * SIZE; i++)
            if (marks.get(i)) {
                stones[i / SIZE][i % SIZE] = EMPTY;
                ks.add(i);
            }
    }

    /**
     * 检查某个位置的棋子是否有气,没气的话返回false并在marks数组中的该位置置为1
     */
    private boolean checkValidQi(int expected, BitSet marks, int posX, int posY) {
        // 添加到已经检查过的列表中
        marks.set(posX * SIZE + posY);
        for (int[] pos : POSES) {
            int newPosX = posX + pos[0];
            int newPosY = posY + pos[1];

            if (newPosX >= 0 && newPosX < SIZE && newPosY >= 0 && newPosY < SIZE) {
                // 四周有空的，当然有气
                if (stones[newPosX][newPosY] == EMPTY)
                    return true;
                // 如果是同样颜色的, 就把有气的希望寄托给它了
                if (stones[newPosX][newPosY] == expected) {
                    // 还没检查过
                    if (!marks.get(newPosX * SIZE + newPosY))
                        if (checkValidQi(expected, marks, newPosX, newPosY))
                            return true;
                }
            }
        }
        // 四周都检查不到气
        return false;
    }

    public int[][] getStones() {
        return stones;
    }
    
    public int get(int posX, int posY) {
        return this.stones[posX][posY];
    }

    public void setSGF(SGFGame sgfgame) {
        this.sgfgame = sgfgame;
        this.checker = sgfgame.checker();
    }

    public void reset() {
        for(int i=0;i<stones.length;i++){
            Arrays.fill(stones[i], GameConstants.EMPTY);
        }
        TIntList initStones = sgfgame.getInitStones();
        TIntIterator it = initStones.iterator();
        while(it.hasNext()){
            int step = it.next();
            if(step >=1000)
            {
                step = step-1000;
                stones[GameConstants.ARR.charAt(step/19)-'a'][GameConstants.ARR.charAt(step%19)-'a'] = GameConstants.WHITE;
            }
            else {
                stones[GameConstants.ARR.charAt(step/19)-'a'][GameConstants.ARR.charAt(step%19)-'a'] = GameConstants.BLACK;
            }
        }
        this.steps.clear();
        this.kills.clear();
        this.curr = GameConstants.BLACK;
    }
}
