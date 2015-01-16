/* Entry.java */

package hw6.dict;

/**
 * A class for dictionary entries.
 * <p/>
 * DO NOT CHANGE THIS FILE.  It is part of the interface of the
 * Dictionary ADT.
 */

public class Entry {

    protected Object key;
    protected Object value;

    public Object key() {
        return key;
    }

    public Object value() {
        return value;
    }

    @Override
    public String toString() {
        return key + ": " + value;
    }
}
