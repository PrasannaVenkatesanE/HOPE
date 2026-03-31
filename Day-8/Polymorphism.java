class Animal{
    void sound(){
        System.out.println("All animals have differene sound");
    }
}
class Dog extends Animal{
    void sound(){
        super.sound();
        System.out.println("Dog Barksssssssss!!!!!!!");
    }
}

class Cat extends Animal{
    
    void sound(){
        super.sound();
        System.out.println("Cat meowwwwwssssssss!!!!!");
    }
}
public class Polymorphism{
    public static void main(String[] args){
        Animal animal;
        animal = new Cat();//Down Casting 
        animal.sound();
        animal = new Dog();
        animal.sound();
    }
}