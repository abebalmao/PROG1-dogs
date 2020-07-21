//@Author Albin Visteus alvi9625
public class Bid {
    private Dog dog;
    private User bidder;
    private int bidPrice;

    public Bid (User bidder, int bidPrice) {
        this.bidder = bidder;
        this.bidPrice = bidPrice;
    }
    public void changeBid(int newBid){
        this.bidPrice = newBid;
    }
    public void setBid(User user, int newBid) {
        this.bidder = user;
        this.bidPrice = newBid;
    }
    public User getBidder() {
        return bidder;
    }
    public int getBidPrice() {
        return bidPrice;
    }
    public Dog getDog() {
        return dog;
    }

    @Override
    public String toString() {
        return bidder.getName() + " " + bidPrice + " kr ";
    }
}
