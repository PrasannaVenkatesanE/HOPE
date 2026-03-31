class Encapsulation{
    private int a;
    Encapsulation(int a){
        a = 10;
    }
    static display(){
        System.out.println(a);
    }
}
public class encapsulationimplementation{
    public static void main(String[] args){
        Encapsulation en = new Encapsulation();
        en.display();     
    }
}