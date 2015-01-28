package pj3.graph;

import hw5.list.SList;
import hw6.dict.Entry;

/**
 * This file is created by @Muffin_C on 1/28/15 15:46.
 * This file is part of Project CS61B.
 */
public class HashTableAuto extends hw6.dict.HashTableChained {

    private double loadFactor() {
        return (double) entryNumber / hashSize;
    }

    private void doubleSize() {
        hashSize *= 2;
        SList[] oldList = hashList;
        arrayInit();

        for (int i = 0; i < oldList.length; i++) {
            hashList[i] = oldList[i];
        }
    }

    @Override
    public Entry insert(Object key, Object value) {
        Entry item = super.insert(key, value);

        if (loadFactor() > 1) {
            doubleSize();
        }

        return item;
    }

    public static void main(String[] args) {
        System.out.println("test");
    }
}
