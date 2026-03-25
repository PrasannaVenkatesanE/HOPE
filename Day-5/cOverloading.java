public class cOverloading{
    cOverloading(){
        System.out.println("Constructor 1");

    }
    cOverloading(int a){
        System.out.println("Constructor 2");

    }
    cOverloading(String s){
        System.out.println("Constructor 3");

    }
}
public class Main{
    public static void main(String[] args){
        cOverloading c1 = new cOverloading();
        cOverloading c2 = new cOverloading(5);
        cOverloading c3 = new cOverloading("Hello");
    }
}