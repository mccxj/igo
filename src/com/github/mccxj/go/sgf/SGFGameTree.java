package com.github.mccxj.go.sgf;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SGFGameTree {
    private SGFSequence sequence;
    private List<SGFGameTree> childs = new ArrayList<SGFGameTree>();

    public void setSequence(SGFSequence sequence) {
        this.sequence = sequence;
    }

    public void addGameTree(SGFGameTree child) {
        this.childs.add(child);
    }

    public void init(SGFGame game) {
        Iterator<SGFNode> it = sequence.iterator();
        SGFNode root = it.next();

        Iterator<SGFProperty> itp = root.iterator();
        while (itp.hasNext()) {
            SGFProperty prop = itp.next();
            String id = prop.getIdentifier();
            String val = prop.uniqValue();
            if ("AP".equals(id)) {
                game.setApplication(val);
            } else if ("CA".equals(id)) {
                game.setCharset(val);
            } else if ("FF".equals(id)) {
                game.setFormat(val);
            } else if ("GM".equals(id)) {
                game.setGametype(val);
            } else if ("ST".equals(id)) {
                game.setShowtype(val);
            } else if ("SZ".equals(id)) {
                game.setSize(val);
            } else if ("AB".equals(id)) {
                Iterator<String> pv = prop.iterator();
                while (pv.hasNext()) {
                    String v = pv.next();
                    game.initBlack(v);
                }
            } else if ("AW".equals(id)) {
                Iterator<String> pv = prop.iterator();
                while (pv.hasNext()) {
                    String v = pv.next();
                    game.initWhite(v);
                }
            } else if ("C".equals(id)) {
                System.out.println(val);
            }
        }

        this.toVariation(game);
    }

    private void toVariation(SGFVariation var) {
        Iterator<SGFNode> it = sequence.iterator();
        while (it.hasNext()) {
            SGFNode node = it.next();
            Iterator<SGFProperty> isp = node.iterator();
            while (isp.hasNext()) {
                SGFProperty prop = isp.next();
                String id = prop.getIdentifier();
                String val = prop.uniqValue();
                if ("B".equals(id)) {
                    var.addBlack(val);
                    break;
                } else if ("W".equals(id)) {
                    var.addWhite(val);
                    break;
                }
            }
        }

        for (SGFGameTree child : childs) {
            SGFVariation cc = new SGFVariation();
            var.addVariation(cc);
            child.toVariation(cc);
        }
    }
}
