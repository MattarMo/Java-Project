public class ServerQueues {

    private Client clientServing;
    private boolean isBusy;
    private int startTime;
    private int timeRemainingWithClient;

    public ServerQueues(){
    }

    public void setClientServing(Client c){
        clientServing = c;
    }

    public Client getClientServed(){
        return clientServing;
    }

    public boolean getIsBusy(){
        return isBusy;
    }

    public void setIsBusy(boolean b){
        isBusy = b;
    }


    public boolean isDoneWithClient(int tick){
        boolean result = false;
        if (clientServing != null){
            //on later rounds, calculate elapsed time and compare with client's server time in order to calculate if we are done
            if(isBusy == true){
                int elapsedTimeWithClient = tick - startTime;
                timeRemainingWithClient = elapsedTimeWithClient;
                if (elapsedTimeWithClient >= clientServing.getServerTime()){
                    result = true;
                }
            }
            //on the first round, assign the tick we are on to a member variable
            else{
                startTime = tick;
                result = false;
            }
        }

        return result;
    }

}
