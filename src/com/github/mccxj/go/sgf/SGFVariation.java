package com.github.mccxj.go.sgf;

import com.github.mccxj.go.GameConstants;
import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;

import java.util.ArrayList;
import java.util.List;

public class SGFVariation {
    private TIntList stones = new TIntArrayList();
    private List<SGFVariation> variations = new ArrayList<SGFVariation>();
	private SGFVariation parent = null;
    
    public void addBlack(String val) {
        addStone(stones, GameConstants.BLACK, val);
    }

    public void addWhite(String val) {
        addStone(stones, GameConstants.WHITE, val);
    }

    protected void addStone(TIntList stones, int color, String val) {
        int p = GameConstants.WHITE_PASS;
        int os = GameConstants.WHITE_OFFSET;
        if(color == GameConstants.BLACK){
            p = GameConstants.BLACK_PASS;
            os = GameConstants.BLACK_OFFSET;
        }
        
        if (val.isEmpty()) {
            stones.add(p);
            return;
        }
        if (val.length() == 2) {
            if ("tt".equals(val)) {
                stones.add(p);
                return;
            }
            int posX = val.charAt(0) - 'a';
            int posY = val.charAt(1) - 'a';
            stones.add(os + posX * GameConstants.SIZE + posY);
        }
    }

	public void addVariation(SGFVariation var) {
		var.setParent(this);
		variations.add(var);
	}
	
	public void setParent(SGFVariation parent) {
		this.parent = parent;
	}

    public SGFChecker checker() {
        return new SGFAutoChecker();
    }
    
    class SGFAutoChecker implements SGFChecker {
        private SGFVariation var = SGFVariation.this;
        private int step = 0;
        private int result = GameConstants.SUCC;

        @Override
        public int checkNext(int posX, int posY) {
            // 走当前分支
            if(var.stones.size() > step){
                int i = var.stones.get(step);
                if(i/GameConstants.SIZE == posX && i%GameConstants.SIZE == posY){
                    step++;
                    if(var.stones.size() > step){
                        return var.stones.get(step++);
                    }
                    else {
                        return result;
                    }
                }
                else {
                    return GameConstants.INVALID;
                }
            }
            else {
                if(var.variations.isEmpty())
                    return result;
                step = 0;
                for(int i=0;i<var.variations.size();i++){
                    SGFVariation tmpvar = var.variations.get(i);
                    int iv = tmpvar.stones.get(0);
                    if(iv/GameConstants.SIZE == posX && iv%GameConstants.SIZE == posY){
                        var = tmpvar;
                        if(i != 0)
                            result = GameConstants.FAIL;
                        return checkNext(posX, posY);
                    }
                }
                return GameConstants.INVALID;
            }
        }
    }
}
