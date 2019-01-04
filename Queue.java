public class Queue {

    private static class Link {
        //data(public);
        public Client data;
        public Link next;

        public Link(Client a) {
            data = a;
        }

    }
    private Link head;
    private Link tail;
    private int size;

    public void enqueue(Client c){
        Link newLink = new Link(c);
        if (size == 0) {
            head = newLink;
        }
        else{
            tail.next = newLink;
        }
        tail = newLink;
        size++;
    }

    public Client dequeue(){
        if (head == null) {
            return null;
        }
        Client headDatum = head.data;
        head = head.next;
        size--;
        return headDatum;

    }
    public int getNumberInList(){
        return size;
    }

    //borrowed from https://stackoverflow.com/questions/36396132/how-does-remove-tail-from-singly-linked-list-in-java-work
    //for the concept on removing from the tail
    public Client switchQueue(){

        if (head == null || head.next == null){
            return null;
        }
        Link node = head;
        Link oldNode = null;
        while(node.next.next != null) {
            node = node.next;
            oldNode = node;
        }

        //Client/customer that will be switching
        Client penult = oldNode.data;
        Client stash = node.next.data;

        node.next = null;
        tail = node;
        size--;
        return stash;
    }

    public void moveNWait(){
        Link pointer = head;
        while (pointer != null){
            int oldTime = pointer.data.getWaitTime();
            pointer.data.setWaitTime(oldTime + 1);
            pointer = pointer.next;
        }
    }

    public void setVirtualIsSwitched(String id){
        Link pointer = head;
        while (pointer != null){
            String retId =  pointer.data.getId() + "";
            if (retId.equals(id)){
                pointer.data.setSwitchedQueues(true);
            }

            pointer = pointer.next;
        }
    }

    public boolean isInQueue(String id){
        boolean result = false;
        Link pointer = head;
        while (pointer != null){
            if (pointer.data.getId().equals(id)){
                result = true;
            }

            pointer = pointer.next;
        }
        return result;
    }

    public int addUpWaitTimes(String stopId){
        int totalWaitTime = 0;
        Link pointer = head;
        while (pointer != null){
            //search each node in turn for the id passed in
            System.out.println("traverseAndAddUpWaitTimes " + pointer.data.getId() );
            boolean hasMyId = pointer.data.getId().equals(stopId) ? true : false;
            if (hasMyId == false){
                totalWaitTime += pointer.data.getServerTime();
                System.out.println("added preceding server time " + pointer.data.getServerTime());
            }
            else{
                totalWaitTime += pointer.data.getWaitTime();
                System.out.println("added its own wait time " + pointer.data.getWaitTime());
                break;
            }

            pointer = pointer.next;
        }
        System.out.println("total up until is " + totalWaitTime);
        return totalWaitTime;
    }

    public void WouldHaveWaitedTime(String idParam, int wouldHaveWaitedTime){
        Link pointer = head;
        while (pointer != null){
            //search each node in turn for the id passed in
            boolean hasMyId = pointer.data.getId().equals(idParam) ? true : false;
            if (hasMyId == true){
                pointer.data.setWouldHaveWaitedTime(wouldHaveWaitedTime);
                System.out.println("after setting wouldHaveWaitedTime to " + wouldHaveWaitedTime + " on " + pointer.data.getId());
                return;
            }
            pointer = pointer.next;
        }

    }
    public String toString() {
        String str = "";
        for (Link p = head; p != null; p = p.next) {
            str += p.data + ", ";
        }
        return str;
    }


}