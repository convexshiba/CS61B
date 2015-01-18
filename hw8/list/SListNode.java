package hw8.list;/* hw8.list.SListNode.java */

/**
 * hw8.list.SListNode is a class used internally by the SList class.  An SList object
 * is a singly-linked list, and an hw8.list.SListNode is a node of a singly-linked
 * list.  Each hw8.list.SListNode has two references:  one to an object, and one to
 * the next node in the list.
 */

class SListNode {
    Object item;
    SListNode next;

    /**
     * hw8.list.SListNode() (with one parameter) constructs a list node referencing the
     * item "obj".
     */

    SListNode(Object obj) {
        item = obj;
        next = null;
    }

    /**
     * hw8.list.SListNode() (with two parameters) constructs a list node referencing the
     * item "obj", whose next list node is to be "next".
     */

    SListNode(Object obj, SListNode next) {
        item = obj;
        this.next = next;
    }

}
