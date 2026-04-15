public class IteratorImplementation{
    public static void main(String[] args){
        List<Student> s = new ArrayList<>();
        s.add(new Student(10, "prasa"));
        s.add(new Student(20, "ven"));
        s.add(new Student(230, "pv"));
        Collections.sort(s);
        for(Student student : s){
            System.out.println(student);
        }
    }
}