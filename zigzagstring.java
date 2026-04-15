import java.util.*;
public class zigzagstring{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        int n = sc.nextInt();
        boolean down = false;
        if(n == 0){
            System.out.println(str);
        }
        String res = "";
        String[] arr = new String[n];
        for(int i=0;i<n;i++){
            arr[i] = "";
        }
        int row = 0;
        for(int i=0;i<str.length();i++){
            if(row >=0 ){
                arr[row] += str.charAt(i);
            }
            if(row == n-1){
                down = false;
            }
            if(row == 0){
                down = true;
            }
            if(down){
                row++;
            }
            else{
                row--;
            }
        }
        for(int i=0;i<n;i++){
            System.out.println(arr[i]);
        }
    }
}