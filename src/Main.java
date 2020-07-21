import java.util.ArrayList;
import java.util.Scanner;
//@Author Albin Visteus alvi9625

public class Main {
    private Scanner input = new Scanner(System.in);
    private ArrayList<Auction> auctions = new ArrayList<Auction>();
    private ArrayList<Bid> bids = new ArrayList<Bid>();
    private ArrayList<Dog> dogs = new ArrayList<Dog>();
    private ArrayList<User> users = new ArrayList<User>();
    private int indexStart = 1;
    private int auctionNumber = 0;
    private Dog[] ownedDogs = new Dog[indexStart];

    public static void main(String[] args) {
        Main m = new Main();
        m.run();
    }
    private void run() {
        String command;
        boolean running = true;
        while (running) {
            System.out.println("Command?>");
            command = input.nextLine();
            handleCommand(command);
        }
    }
    private boolean handleCommand(String command) {
        switch (command) {
            case "remove dog":
                removeDog();
                break;
            case "register new dog":
                registerNewDog();
                break;
            case "increase age":
                incAgeDog();
                break;
            case "register new user":
                registerNewUser();
                break;
            case "give dog":
                giveDog();
                break;
            case "remove user":
                removeUser();
                break;
            case "start auction":
                startAuction();
                break;
            case "close auction":
                closeAuction();
                break;
            case "make bid":
                makeBid();
                break;
            case "list dogs":
                DogSorter.sort(dogs);
                listDogs(input);
                break;
            case "list users":
                listUsers();
                break;
            case "list auctions":
                listAuctions();
                break;
            case "list bids":
                listBids();
                break;
            case "exit":
                System.out.println("Goodbye!");
                System.exit(0);
                break;
            default:
                System.out.println("Error: not a command");
                break;
        }
        return false;
    }
    private boolean dogInAuction(String dogInput) {
        for (Auction auc : auctions) {
            if (auc.getDog().getName().equalsIgnoreCase(dogInput)) {
                return true;
            }
        }
        return false;
    }
    private void makeBid() {
        String userName = userNameInput();
        if (userInSystem(userName)) { //currUser != null
            User currUser = getUserByName(userName);
            String dogName = getDogNameInput();
            if (dogInAuction(dogName)) {
                Auction currAuc = getAuctionByDogNameString(dogName);
                boolean bidMin = true;
                while (bidMin) {
                    int bidPrice = currAuc.getHighestBid() + 1;
                    System.out.println("Amount to bid ( min " + bidPrice + ") ?>");
                    int bidInput = input.nextInt();
                    String s = input.nextLine();
                    if (bidInput >= bidPrice) {
                        currAuc.addBid(currUser, bidInput);
                        System.out.println("Bid made");
                        bidMin = false;
                    } else {
                        System.out.println("Error: too low bid");
                    }
                }
            } else {
                System.out.println("Error: this dog is not up for auction");
            }
        }
    }
    private void startAuction() {
        String dogInput = getDogNameInput();
        boolean found = dogInAuction(dogInput);
        for (Dog currDog : dogs) {
            if (found) {
                System.out.println("Error: this dog is already up for auction");
                break;
            } else if (currDog.hasOwner()){
                System.out.println("Error: this dog already has an owner");
            } else {
                if (currDog.getName().equalsIgnoreCase(dogInput)) {
                    auctionNumber++;
                    Auction currAuc = new Auction(currDog, auctionNumber);
                    auctions.add(currAuc);
                    System.out.println(currDog.getName() + " has been put up for auction at #" + currAuc.getAucNumber());
                }
            }
        }
    }
    private void closeAuction() {
        String dogInput = getDogNameInput();
        if (dogInAuction(dogInput)) {
            Auction currAuc = getAuctionByDogNameString(dogInput);
            if (currAuc.getBids().isEmpty() && currAuc.getDog().getName().equalsIgnoreCase(dogInput)) {
                Dog currDog = currAuc.getDog();
                System.out.println("The auction is closed. No bids were made for " + currDog.getName());
                auctions.remove(currAuc);
                //dogs.remove(currDog); // Kanske inte ska finnas med?
            } else if (currAuc.getDog().getName().equalsIgnoreCase(dogInput)) {
                Dog currDog = currAuc.getDog();
                String user = currAuc.getHighestBidder().getBidder().getName(); // Få denna att bli till en sträng
                for (User currUser : users) {
                    if (currUser.getName().equalsIgnoreCase(user)) {
                        currUser.addDog(currDog);
                        currDog.setOwner(currUser);
                        auctions.remove(currAuc);
                        //dogs.remove(currDog); Ska inte finnas med?
                        System.out.println("The auction is closed. The winning bid was " + currAuc.getHighestBid() + " kr and was made by " + currUser.getName());
                    }
                }
            } else if (!currAuc.getDog().getName().equalsIgnoreCase(dogInput)) {
                System.out.println("Error: this dog is not up for auction");
            }
        } else {
            System.out.println("Error: this dog is not up for auction");
        }

    }
    private void registerNewDog() {
        while (true) {
            System.out.println("Name?>");
            String name = input.nextLine();
            if (name == null || name.trim().isEmpty()) {
                System.out.println("Error: the name can't be empty");
            } else {
                System.out.println("Breed?>");
                String breed = input.nextLine();

                System.out.println("Age?>");
                int age = input.nextInt();

                System.out.println("Weight?>");
                int weight = input.nextInt();
                String s = input.nextLine();
                Dog dog = new Dog(name.trim(), breed, age, weight);
                dogs.add(dog);
                System.out.println(dog.getName().trim() + " added to the register");
                break;
            }
        }
    }
    private void registerNewUser() {
        while (true) {
            System.out.println("Name?>");
            String name = input.nextLine();
            if (name == null || name.trim().isEmpty()) {
                System.out.println("Error: the name can't be empty");
            } else {
                User user = new User(name, null);
                users.add(user);
                System.out.println(name + " added to the register");
                break;

            }
        }
    }
    private void removeDog() {
        if (dogs.size() != 0) {
            String dogName = getDogNameInput();
            if (dogInSystem(dogName)) {
                Dog currDog = getDogByName(dogName);
                Auction currAuc = getAuctionByDogNameString(dogName);
                dogs.remove(currDog);
                auctions.remove(currAuc);
                System.out.println(currDog.getName() + " is removed from the register");
            }
        } else {
            System.out.println("Error: no dogs in the register");
        }
    }
    private void removeUser() {
        String userName = userNameInput();
        if (userInSystem(userName)) {
            User currUser = getUserByName(userName);
            for (Auction currAuc : cloneAuctions()) {
                if (currAuc.userExistsInBid(currUser)) {
                    currAuc.removeBidByBidder(currUser);
                }
            }
            for (Dog currDog : cloneDogs()) {
                if (currDog.hasOwner()) {
                    if (currDog.dogOwnedByUser(currUser)) {
                        removeDogByUser(currUser);
                    }
                }
            }
            users.remove(currUser);
            System.out.println(userName+ " is removed from the register");
        }
    }
    private ArrayList<Dog> cloneDogs() {
        return (ArrayList<Dog>) dogs.clone();
    }
    private ArrayList<Auction> cloneAuctions() {
        return (ArrayList<Auction>) auctions.clone();
    }
    private void removeDogByUser(User user) {
        if (user != null) {
            Dog currDog = getDogByUser(user);
            dogs.remove(currDog);
        }
    }
    private void incAgeDog(){
        System.out.println("Enter the name of the dog?>");
        String incAgeName = input.nextLine();
        int i = 0;
        for (Dog currDog : dogs){
            if (currDog.getName().equalsIgnoreCase(incAgeName.trim())){
                currDog.increaseAge();
                System.out.println(currDog.getName()+ " is now one year older");
                return;
            }
            else {
                i++;
            }
        }
        if (i == dogs.size()) {
            System.out.println("Error: no such dog");
        }
    }
    private void giveDog() {
        String dogInput = getDogNameInput();
        if (dogInSystem(dogInput)){
            Dog currDog = getDogByName(dogInput);
            if (!currDog.hasOwner()) {
                if (currDog.getName().equalsIgnoreCase(dogInput)) {
                    String userInput = userNameInput();
                    if (userInSystem(userInput)) { //currUser.getName().equalsIgnoreCase(userInput)
                        User currUser = getUserByName(userInput);
                        currUser.addDog(currDog);
                        currDog.setOwner(currUser);
                        System.out.println(currUser.getName() + " now owns " + currDog.getName());
                    }
                }
            } else if (currDog.hasOwner()){
                System.out.println("Error: this dog already has an owner");
            }
        }

    }
    private String getDogNameInput() {
        int i = 0;
        System.out.println("Enter the name of your dog?> ");
        String dogInput = input.nextLine();
        for (Dog currDog : dogs) {
            if (currDog.getName().equalsIgnoreCase(dogInput.trim())) {
                return dogInput;
            }else {
                i++;
            }
        }
        if (i == dogs.size()) {
            System.out.println("Error: dog not in system");
        }
        return null;
    }
    private String userNameInput() {
        int i = 0;
        System.out.println("Enter the name of the user?> ");
        String userName = input.nextLine();
        for (User currUser : users) {
            if (currUser.getName().trim().equalsIgnoreCase(userName.trim()))  {
                return userName;
            } else {
                i++;
            }
        }
        if (i == users.size()) {
            System.out.println("Error: user not in system");

        }
        return null;
    }
    private User getUserByName(String name) {
        for (User currUser : users) {
            if (currUser.getName().equalsIgnoreCase(name)){
                return currUser;
            }
        }
        return null;
    }
    private Dog getDogByName(String name) {
        for (Dog currDog : dogs) {
            if (currDog.getName().equalsIgnoreCase(name)) {
                return currDog;
            }
        }
        return null;
    }
    private Dog getDogByUser(User user) {
        for (Dog currDog : dogs) {
            if (currDog.getOwner().equals(user)) {
                return currDog;
            }
        }
        return null;
    }
    private boolean dogInSystem(String name) {
        for (Dog currDog : dogs) {
            if (currDog.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
    private boolean userInSystem(String name) {
        for (User currUser : users) {
            if (currUser.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
    private Auction getAuctionByDogNameString(String name) {
        for (Auction currAuc : auctions) {
            if (currAuc.getDog().getName().equalsIgnoreCase(name)) {
                return currAuc;
            }
        }
        return null;
    }
    private void listDogs(Scanner input) {
        if (dogs.size() == 0) {
            System.out.println("Error: no dogs in register");
        } else {
            System.out.println("Smallest tail length to display?>");
            double tailInput = input.nextDouble();
            String s = input.nextLine();
            for (Dog dog : dogs) {
                if (dog.getTailLength() >= tailInput) {
                    System.out.println(dog.toStringDog());

                }
            }
        }
    }
    private void listUsers() {
        if (users.size() == 0) {
            System.out.println("Error: no users in register");
        } else {
            for (User user : users) {
                System.out.println(user.toString());
                //System.out.println(user.deepToString(ownedDogs).replace("null", ""));

            }
        }
    }
    private void listAuctions() { //Skapa en ny stringbuilder som fungerar (med [] och ,)
        if (auctions.isEmpty()) {
            System.out.println("Error: no auctions in progress");
        } else {
            System.out.print(auctions.toString()
                    .replace("[", "")
                    .replace("]", "")
                    .replace(", ", ""));
        }
    }
    private void listBids() {
        String dogInput = getDogNameInput();
        Auction currAuc = getAuctionByDogNameString(dogInput);
        if (dogInSystem(dogInput)) {
            if (currAuc != null) {
                currAuc.sortBids();
                ArrayList<Bid> bid = currAuc.getBids();
                System.out.print(bid.toString()
                        .replace("[", "")
                        .replace("]", "")
                        .replace(", ", "") + "\n");
            } else if (dogInAuction(dogInput) && !currAuc.hasBids()) {
                System.out.println("Error: no bids registered for this auction");
            } else {
                System.out.println("Error: no bids registered for this auction");
            }
        }
    }

}
