import java.util.ArrayList;
import java.util.Arrays;
//@Author Albin Visteus alvi9625

public class Dog {
    private String name;
    private String breed;
    private User owner;
    private int age;
    private int weight;
    private boolean hasOwner;
    private double tailLength;
    private final double taxTl = 3.70;
    private final double tlCalc = 10;

    public Dog(String name, String breed, int age, int weight) {
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.weight = weight;
    }
    public String getName() {
        return name;
    }
    public void setName(String newName) {
        this.name = newName;
    }
    public User getOwner() {
        return owner;
    }
    public void setOwner(User newName) {
        this.owner = newName;
    }
    public boolean hasOwner() {
        return this.owner != null;
    }
    public String getBreed() {
        return breed;
    }
    public void setBreed(String newBreed) {
        this.breed = newBreed;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int newAge) {
        this.age = newAge;
    }
    public void increaseAge() {
        this.age = age+1;
    }
    public int getWeight() {
        return weight;
    }
    public void setWeight(int newWeight) {
        this.weight = newWeight;
    }
    public double getTailLength() {
        ArrayList<String> taxDog = new ArrayList<>(Arrays.asList("TAX", "TECKEL", "DACHSHUND"));
        double tailLength;
        if(taxDog.contains(breed.toUpperCase())) {
            tailLength = taxTl;
        } else {
            double currentAge = age;
            double currentWeight = weight;
            tailLength = currentAge * (currentWeight/tlCalc);
        }
        return tailLength;
    }
    public boolean dogOwnedByUser(User user) {
        return owner.equals(user);
    }

    public String toStringDog() {
        if (hasOwner()) {
            return "* " +  name + " (" + breed +", " + age + " years, " + weight + " kilo, "
                    + getTailLength() + " cm tail" + ", owned by " + owner.getName() + ") ";
        } else {
            return "* " +  name + " (" + breed +", " + age + " years, " + weight + " kilo, "
                    + getTailLength() + " cm tail)";
        }
    }

}

// DETTA KAN BEHÖVAS FÖR VPL

//    public void setName(String newName) {
//        if(newName == null || newName.isEmpty()) {
//            name = "A. Nonym";
//        } else {
//            name = newName;
//        }
//    }