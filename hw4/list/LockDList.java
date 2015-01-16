package hw4.list;

/**
 * Created by Meth on 1/8/15.
 */
public class LockDList extends DList {
    public LockDList() {
        head = newNode(null, null, null);
        head.next = head;
        head.prev = head;
    }

    public void lockNode(DListNode node) {
        ((LockDListNode) node).lockStatus = true;
    }

    @Override
    public void remove(DListNode node) {
        if (((LockDListNode) node).lockStatus) {
            System.out.println("locked");
            return;
        }
        super.remove(node);
    }

    @Override
    protected LockDListNode newNode(Object item, DListNode prev, DListNode next) {
        return new LockDListNode(item, prev, next);
    }

    @Override
    public LockDListNode front() {
        return (LockDListNode) super.front();
    }

    @Override
    public LockDListNode back() {
        return (LockDListNode) super.back();
    }

    @Override
    public LockDListNode next(DListNode node) {
        return (LockDListNode) super.next(node);
    }

    @Override
    public LockDListNode prev(DListNode node) {
        return (LockDListNode) super.prev(node);
    }


}
