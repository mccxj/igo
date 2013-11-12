package com.github.mccxj.go.sgf;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SGFProperty implements Iterable<String> {
    private String identifier;
    private List<String> values = new ArrayList<String>();

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String uniqValue() {
        return values.get(0);
    }

    public void addValue(String value) {
        this.values.add(value.substring(1, value.length() - 1));
    }

    @Override
    public Iterator<String> iterator() {
        return values.iterator();
    }
}
