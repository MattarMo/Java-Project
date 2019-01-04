/*
Mohammad Mattar, HW1129, 12/16/2018
Program Project - Java program to simulate two queues with two servers.
                  A client standing in line may change queues, by leaving
                  its place and going to the end of the other line, question
                  to answer is, does it get served faster?
URL(S):
 */

import java.util.Random;

public class MainSim {

    public static void main(String[] args)  {

        int numSwitched = 0;
        boolean verboseOutput = true;
        double mean = 0.25;
        int rounds = 500;
        Queue aList = new Queue();
        Queue a1List = new Queue();
        ServerQueues aServer = new ServerQueues();
        ServerQueues a1Server = new ServerQueues();
        ServerQueues aVirtualServer = new ServerQueues();
        Queue aListVirtual = new Queue();

        for (int i = 0; i < rounds; i++){
            System.out.println("tick # " + i );
            int numArrive = getPoissonRandom(mean);
            for (int j = 0; j < numArrive; j++){
                Client c = new Client(""+j+i);
                Client d = new Client(""+j+i+"x");

                //this essentially copys over data from c to the new Client c2
                Client c2 = new Client(""+j+i + "y");
                c2.copyClient(c);

                aList.enqueue(c);
                c.setOriginalQueue(1);
                c.setCurrentQueue(1);

                //this essentially copys c2 over to virtual queue
                aListVirtual.enqueue(c2);
                c2.setOriginalQueue(1);
                c2.setCurrentQueue(1);

                a1List.enqueue(d);
                d.setOriginalQueue(2);
                d.setCurrentQueue(2);
            }

            //server for Queue 1 work
            if (aList.getNumberInList() > 0)	{
                if (aServer.getIsBusy() == false)	{
                    aServer.setClientServing(aList.dequeue());
                    System.out.println("tick # " + i + " server 1 start service on customer " + aServer.getClientServed().getId() + " server time " + aServer.getClientServed().getServerTime());
                }
            }

            //give each customer that arrives a 50/50 chance for switching from queue 1 to 2
            Random rr = new Random();
            for (int k = 0; k < numArrive; k++){
                //ranges from(0-9)
                int aNum = rr.nextInt(10);
                boolean isLessThanFive = (aNum < 5);
                if((isLessThanFive == true) && (aList.getNumberInList() > 2)){
                    Client nc = aList.switchQueue();
                    System.out.println("\t switched " + nc.getId() + " and added to queue 2 ");
                    nc.setSwitchedQueues(true);
                    a1List.enqueue(nc);
                    nc.setCurrentQueue(2);

                    String virtualId = nc.getId() + "_V";
                    aListVirtual.setVirtualIsSwitched(virtualId);
                }
            }

            //busy flags work for server 1
            if (aList.getNumberInList() > 0)	{
                if (aServer.isDoneWithClient(i) == true){
                    System.out.println("tick # " + i + " server 1 end service on customer " + aServer.getClientServed().getId());
                    aServer.setIsBusy(false);
                }
                else{
                    aServer.setIsBusy(true);
                }
            }

            //work for server assgining, queue 1(virtual)
            if (aListVirtual.getNumberInList() > 0)	{
                if (aVirtualServer.getIsBusy() == false) {
                    aVirtualServer.setClientServing(aListVirtual.dequeue());
                    System.out.println("tick # " + i + " server 1 Virtual start service on customer " + aVirtualServer.getClientServed().getId() + " server time " + aVirtualServer.getClientServed().getServerTime());

                    if (aVirtualServer.getClientServed().getSwitchedQueues() == true) {
                        String virId = aVirtualServer.getClientServed().getId();
                        int suffixInd = virId.indexOf('_');
                        String actualId = virId.substring(0,suffixInd);
                        System.out.println("Queue 2 is serving " +a1Server.getClientServed().getId());

                        //if the customer ID is still in Q2 then q2 beat q1 in wait time
                        if((a1List.isInQueue(actualId) == true)){
                            System.out.println("id " + actualId + " is still in queue 2, so Q1 beat Q2. set wait time from virtual to Q2");
                            int virtualWaitTime = aVirtualServer.getClientServed().getWaitTime();
                            a1List.WouldHaveWaitedTime(actualId,virtualWaitTime);
                        }
                    }
                }
            }

            //check virtual server for if its busy or not
            if (aListVirtual.getNumberInList() > 0)	{
                if (aVirtualServer.isDoneWithClient(i) == true){
                    System.out.println("tick # " + i + " virtual server 1 end service on customer " + aVirtualServer.getClientServed().getId());
                    aVirtualServer.setIsBusy(false);
                }
                else{
                    aVirtualServer.setIsBusy(true);
                }
            }

            //work for handle server assigning, Queue 2
            if (a1List.getNumberInList() > 0) {
                if (a1Server.getIsBusy() == false) {

                    a1Server.setClientServing(a1List.dequeue());
                    System.out.println("tick # " + i + " server 2 start service on customer " + a1Server.getClientServed().getId() + " server time " + a1Server.getClientServed().getServerTime());
                    if (a1Server.getClientServed().getSwitchedQueues() == true){
                        System.out.println("\t switched client being served " + a1Server.getClientServed().toString());

                        //check to see if Q2 beats Q1 in time:
                        String actualId = a1Server.getClientServed().getId();
                        String virtualId = actualId + "_V";
                        if((aListVirtual.isInQueue(virtualId) == true)){
                            System.out.println("id " + virtualId + " is still in queue 1 virtual, so Q2 beat Q1. set would have waited by adding up prev wts?");
                            //add up all wait times ahead of corresponding virtual added to its current wait time
                            int virtualWaitTime = aListVirtual.addUpWaitTimes(virtualId);
                            a1Server.getClientServed().setWouldHaveWaitedTime(virtualWaitTime);
                            System.out.println("would have waited time on " + a1Server.getClientServed().getId() + " is " + a1Server.getClientServed().getWouldHaveWaitedTime());
                        }
                        numSwitched++;
                    }
                }
            }

            //busy flags work for server 2
            if (a1List.getNumberInList() > 0)	{
                if (a1Server.isDoneWithClient(i) == true){
                    System.out.println("tick # " + i + " server 2 end service on customer " + a1Server.getClientServed().getId());
                    a1Server.setIsBusy(false);
                }
                else{
                    a1Server.setIsBusy(true);
                }
            }

            //update wait time on all queues
            aList.moveNWait();
            a1List.moveNWait();
            aListVirtual.moveNWait();

            //output
            if (verboseOutput == true){

                System.out.println("number arrived " + numArrive);
                System.out.println("queue 1: server busy " + aServer.getIsBusy());
                System.out.println("queue 1: " + aList.getNumberInList());
                System.out.println("queue 1 " + aList.toString());

                System.out.println("queue 1 virtual: server busy " + aVirtualServer.getIsBusy());
                System.out.println("queue 1 virtual: " + aListVirtual.getNumberInList());
                System.out.println("queue 1 virtual " + aListVirtual.toString());

                System.out.println("queue 2: server busy " + a1Server.getIsBusy());
                System.out.println("queue 2: " + a1List.getNumberInList());
                System.out.println("queue 2 " + a1List.toString());
                System.out.println("==========================================");
            }

        }
        System.out.println("Total number switched who were served: " + numSwitched);

    }

    /*
  credit to David Knuth
  https://stackoverflow.com/questions/9832919/generate-poisson-arrival-in-java
  */
    private static int getPoissonRandom(double mean) {
        Random r = new Random();
        double L = Math.exp(-mean);
        int k = 0;
        double p = 1.0;
        do {
            p = p * r.nextDouble();
            k++;
        } while (p > L);
        return k - 1;
    }

}