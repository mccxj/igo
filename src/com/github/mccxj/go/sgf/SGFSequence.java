package com.github.mccxj.go.sgf;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SGFSequence implements Iterable<SGFNode> {
    private List<SGFNode> nodes = new ArrayList<SGFNode>();

    public void addNode(SGFNode node) {
        this.nodes.add(node);
    }

    @Override
    public Iterator<SGFNode> iterator() {
        return nodes.iterator();
    }
}
