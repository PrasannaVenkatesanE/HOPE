import java.util.*;
public class HashMapImplementation{
    public static void main(String[] args){


        // Map<Integer,Integer> map = new HashMap<>();
        // map.put(1,10);
        // map.put(2,20); 
        // System.out.println(map.get(3));


        Map<Character,Integer> charCount = new HashMap<>();


        charCount.put('z', 5);
        charCount.put('b', 3);
        charCount.put('c', 8);
        charCount.put('z',10);


        for(Map.Entry<Character,Integer> entry : charCount.entrySet()){
            System.out.println(entry.getKey()+" "+entry.getValue());
        }


        System.out.println(charCount.keySet());
        System.out.println(charCount.values());
        System.out.println(charCount);
        System.out.println(charCount.containsKey('a'));


        HashMap<Character,Integer> charCount2 = new HashMap<>();


        charCount2.put('d', 5);
        charCount2.put('e', 6);
        charCount2.put('f', 7);


        charCount.putAll(charCount2);


        System.out.println(charCount);

    
    }    
}