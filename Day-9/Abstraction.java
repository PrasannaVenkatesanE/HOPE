abstract class Employee {
    String name;
    int id;
    public Employee(String name,int id){
        this.name = name;
        this.id = id;
    }
    abstract double salary();
}

class FullTimeEmployee extends Employee {
    private double monthlySalary;
    public FullTimeEmployee(String name,int id,double monthlySalary) {
        super(name,id);
        this.monthlySalary = monthlySalary;
    }

    @Override
    double salary() {
        return monthlySalary;
    }
}

class PartTimeEmployee extends Employee {
    private double hourlyRate;
    private int hoursWorked;

    public PartTimeEmployee(String name,int id,double hourlyRate, int hoursWorked) {
        super(name,id);
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
    }

    @Override
    double salary() {
        return hourlyRate * hoursWorked;
    }
}

public class Abstraction {
    public static void main(String[] args) {
        Employee fullTime = new FullTimeEmployee("Prasanna",1,5000.0);
        Employee partTime = new PartTimeEmployee("Prasnna",2,20.0, 40);
        System.out.println("Employee Name: " + fullTime.name + ", ID: " + fullTime.id);
        System.out.println("Employee Name: " + partTime.name + ", ID: " + partTime.id);
        System.out.println("Full-time employee salary: $" + fullTime.salary());
        System.out.println("Part-time employee salary: $" + partTime.salary());
    }
}