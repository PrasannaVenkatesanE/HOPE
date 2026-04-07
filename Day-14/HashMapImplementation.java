import java.util.*;
public class HashMapImplementation{
    public static void main(String[] args){
        Map<Integer,Integer> map = new HashMap<>();
        map.put(1,10);
        map.put(2,20);  
        System.out.println(map.get(2));
    }    
}