package com.github.mccxj.go.sgf;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SGFNode implements Iterable<SGFProperty> {
    private List<SGFProperty> properties = new ArrayList<SGFProperty>();

    public void addProperty(SGFProperty property) {
        this.properties.add(property);
    }

    @Override
    public Iterator<SGFProperty> iterator() {
        return properties.iterator();
    }
}
