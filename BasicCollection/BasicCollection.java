import java.util.*;

public class BasicCollection {

    public static void main(String[] args){

        int[] nums = {10,20,30,40,50};

        for(int i:nums){
            System.out.println("Number: "+i);
        }

        List<String> cityName= new ArrayList<>();

        cityName.add("abc");
        cityName.add("efg");
        cityName.add("hij");
        cityName.add("hij");


        for(String name:cityName){
            System.out.println("City: " + name);
        }

        Set<String> uniqueCity = new HashSet<>(cityName);
        for(String name:uniqueCity){
            System.out.println("City: " + name);
        }

        Map<Integer, String> studentMap = new HashMap<>();
        studentMap.put(101, "Alice");
        studentMap.put(102, "Bob");
        studentMap.put(103, "Charlie");
        for (Map.Entry<Integer, String> entry : studentMap.entrySet()) {
            System.out.println("Roll No: " + entry.getKey() + " => Name: " + entry.getValue());
        }

        System.out.println("\n🔹 Accessing a student with Roll No 102:");
        System.out.println("Student: " + studentMap.get(102));

    }
}