import java.util.StringJoiner;

//@Author Albin Visteus alvi9625
public class User {
    private int indexStart = 1;
    private String name;
    private Dog[] ownedDogs;

    public User(String name, Dog[] ownedDogs) {
        this.name = name;
        this.ownedDogs = new Dog[0];
    }
    public void setDog(Dog[] dog) {
        this.ownedDogs = dog;
    }
    public String getName() {
        return name;
    }
    public void setName(String newName) {
        this.name = newName;
    }
    public void addDog(Dog dog) {
        Dog[] tempArr = new Dog[ownedDogs.length+1];
        for(int i = 0; i < ownedDogs.length; i++) {
            tempArr[i] = ownedDogs[i];
        }
        ownedDogs = tempArr;
        ownedDogs[ownedDogs.length-1] = dog;
    }
    public void removeThisDog(Dog dog) {
        Dog[] tempArr = new Dog[ownedDogs.length-1];
        for(int i = 0; i < ownedDogs.length; i++) {
            tempArr[i] = ownedDogs[i];
        }
        ownedDogs = tempArr;
        ownedDogs[ownedDogs.length-1] = dog;
    }
    public boolean hasDogs() {
        return ownedDogs.length > 0;
    }
    @Override
    public String toString() {
        StringJoiner string = new StringJoiner(", ", name + "[", "]");
        for (Dog dogs : ownedDogs) {
            string.add(dogs.getName());
        }
        return string.toString();
    }
}

