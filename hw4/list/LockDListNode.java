package hw4.list;

/**
 * Created by Meth on 1/8/15.
 */
public class LockDListNode extends DListNode {
    boolean lockStatus;

    LockDListNode(Object item, DListNode prev, DListNode next) {
        super(item, prev, next);
        lockStatus = false;
    }
}
