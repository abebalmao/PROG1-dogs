import java.util.ArrayList;
//@Author Albin Visteus alvi9625
public class Auction {

    private Dog dog;
    private int aucNumber;
    private final int maxDisplayForBidders = 3;
    private ArrayList<Bid> bids = new ArrayList<>();

    public Auction(Dog dog, int aucNumber) {
        this.dog = dog;
        //counter ++;
        this.aucNumber = aucNumber;
    }
    public Dog getDog() {
        return dog;
    }
    public Bid getBidByBidder(User user) {
        for (Bid currBid : bids) {
            if(currBid.getBidder().equals(user)) {
                return currBid;
            }
        }
        return null;
    }
    public int getAucNumber() {
        return this.aucNumber;
    }

    public void addBid(User user, int bidAmount) {
        removeBidByBidder(user);
        if(!bids.isEmpty()) {
            if (userExistsInBid(user)) {
                Bid bid = getBidByBidder(user);
                bids.add(bid);
            }
        }
        registerBid(user, bidAmount);
    }
    public void registerBid(User bidderName, int bidPrice) {
        bids.add(new Bid(bidderName, bidPrice));
    }
    public void removeBidByBidder(User user) {
        Bid currBid = getBidByBidder(user);
        bids.remove(currBid);
    }
    public boolean userExistsInBid(User user) {
        for (Bid bid : bids) {
            if(bid.getBidder().equals(user)) {
                return true;
            }
        }
        return false;
    }
    public boolean hasBids() {
        return bids.size() > 0;
    }
    public int getHighestBid() {
        if (bids.isEmpty()) {
            return 0;
        } else {
            Bid bid = bids.get(bids.size()-1);
            int highestBid = bid.getBidPrice();
            return highestBid;
        }
    }
    public Bid getHighestBidder() {
        sortBids();
        if (this.hasBids()) {
            return bids.get(0);
        }
        return new Bid(new User("a", null), 1);
    }
    private String getSortedBidsAsString(){
        ArrayList<Bid> sortedBids = sortBids();
        String topBids = "";
        for (int i = 0; i < sortedBids.size() && i < maxDisplayForBidders; i++) {
            topBids = topBids + sortedBids.get(i).toString();
            if (i!= (sortedBids.size() - 1) && i != 2) {
                topBids = topBids + ", ";
            }
        }
        return  topBids;
    }
    protected ArrayList<Bid> getBids() {
        sortBids();
        return bids;
    }
    protected ArrayList<Bid> sortBids(){
        int listLength = bids.size();
        for (int i = 1; i < listLength; ++i) {
            Bid currentBid = bids.get(i);
            int j = i - 1;
            while (j >= 0 && bids.get(j).getBidPrice() < currentBid.getBidPrice()) {
                bids.set(j + 1, bids.get(j));
                j--;
            }
            bids.set(j + 1, currentBid);
        }
        return bids;
    }

    @Override
    public String toString() {
        if (this.getHighestBid() > 0) {
            return "Auction #" + this.aucNumber + ": " + this.dog.getName() + ". Top bids: [" + getSortedBidsAsString() + " ]\n";
        } else {
            return "Auction #" + this.aucNumber + ": " + this.dog.getName() + ". Top bids: []\n";
        }

    }

}
