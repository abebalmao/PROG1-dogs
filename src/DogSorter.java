import java.util.ArrayList;
//@Author Albin Visteus alvi9625

public class DogSorter {
    public static void sort(ArrayList<Dog> dogs) {
        int size = dogs.size();
        for (int i = 0; i < size - 1; i++) {
            int minValue = i;
            for (int j = i+1; j < size; j++) {
                Dog dog = dogs.get(j);
                Dog doog = dogs.get(minValue);
                int comparison = Double.valueOf(dog.getTailLength()).compareTo(doog.getTailLength());
                if (comparison == 0) {
                    comparison = String.valueOf(dog.getName()).compareTo(doog.getName());
                }
                if (comparison < 0) {
                    minValue = j;
                }
            }
            Dog temp = dogs.get(minValue);
            dogs.set(minValue, dogs.get(i));
            dogs.set(i, temp);
        }
    }
}
