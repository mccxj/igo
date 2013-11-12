package com.github.mccxj.go.sgf;

import com.github.mccxj.go.GameConstants;
import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;

public class SGFGame extends SGFVariation {
    private TIntList initstones = new TIntArrayList();
    private String application = "";
    private String charset = "ISO-8859-1";
    private String format = "4";
    private String gametype = "1";
    private String showtype = "0";
    private String size = "19";

    public SGFGame(SGFGameTree gameTree) {
        gameTree.init(this);
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getGametype() {
        return gametype;
    }

    public void setGametype(String gametype) {
        this.gametype = gametype;
    }

    public String getShowtype() {
        return showtype;
    }

    public void setShowtype(String showtype) {
        this.showtype = showtype;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void initBlack(String val) {
        addStone(initstones, GameConstants.BLACK, val);
    }

    public void initWhite(String val) {
        addStone(initstones, GameConstants.WHITE, val);
    }
    
    public TIntList getInitStones(){
        return initstones;
    }
}
