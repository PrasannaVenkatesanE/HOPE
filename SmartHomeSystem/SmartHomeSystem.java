import java.util.Scanner;
import java.util.Collection;

// Custom Exception for Error Handling
class DeviceNotFoundException extends Exception {
    public DeviceNotFoundException(String message) {
        super(message);
    }
}

// Model for tracking people in the house
class Person {
    private String name;
    private String currentRoom;

    public Person(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Person Name cannot be null or empty.");
        }
        this.name = name;
        this.currentRoom = "Outside"; // default starting location
    }

    public String getName() { return name; }
    public String getCurrentRoom() { return currentRoom; }
    
    public void setCurrentRoom(String currentRoom) { 
        this.currentRoom = currentRoom; 
    }
    
    @Override
    public String toString() {
        return "Person: " + name + " | Location: " + currentRoom;
    }
}

// 1. Abstraction: Abstract class defining the template for any smart device
abstract class Device {
    // 2. Encapsulation: Private fields to protect data
    private String id;
    private String name;
    private String location; 
    private boolean isOn;

    public Device(String id, String name, String location) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Device ID cannot be null or empty.");
        }
        if (location == null || location.trim().isEmpty()) {
            throw new IllegalArgumentException("Location cannot be null or empty.");
        }
        this.id = id;
        this.name = name;
        this.location = location;
        this.isOn = false;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getLocation() { return location; }
    public boolean isOn() { return isOn; }

    public void turnOn() {
        this.isOn = true;
        System.out.println("  >>> " + name + " turned ON.");
    }

    public void turnOff() {
        this.isOn = false;
        System.out.println("  >>> " + name + " turned OFF.");
    }

    // Abstract method to enforce specific behavior in all subclasses
    public abstract void performSpecificAction();
    
    @Override
    public String toString() {
        return "[" + id + "] " + name + " (Room: " + location + ") - Status: " + (isOn ? "ON" : "OFF");
    }
}

// 3. Inheritance: Light inherits from Device
class Light extends Device {
    private int brightness;

    public Light(String id, String name, String location) {
        super(id, name, location);
        this.brightness = 100; // Default brightness
    }

    public void setBrightness(int brightness) {
        if (brightness < 0 || brightness > 100) {
            throw new IllegalArgumentException("Brightness must be between 0 and 100.");
        }
        this.brightness = brightness;
        System.out.println("  >>> " + getName() + " brightness set to " + brightness + "%.");
    }

    // 4. Polymorphism: Overriding the abstract method
    @Override
    public void performSpecificAction() {
        System.out.println("  --> " + getName() + " is glowing cheerfully at " + brightness + "%.");
    }

    @Override
    public String toString() {
        return super.toString() + " | Brightness: " + brightness + "%";
    }
}

// 3. Inheritance: Thermostat inherits from Device
class Thermostat extends Device {
    private double targetTemperature;

    public Thermostat(String id, String name, String location) {
        super(id, name, location);
        this.targetTemperature = 22.0; // Default temperature
    }

    public void setTemperature(double temp) {
        if (temp < 10.0 || temp > 35.0) {
            throw new IllegalArgumentException("Temperature must be between 10.0C and 35.0C.");
        }
        this.targetTemperature = temp;
        System.out.println("  >>> " + getName() + " temperature set to " + temp + "C.");
    }

    // 4. Polymorphism
    @Override
    public void performSpecificAction() {
        System.out.println("  --> " + getName() + " is currently maintaining climate at " + targetTemperature + "C.");
    }

    @Override
    public String toString() {
        return super.toString() + " | Target Temp: " + targetTemperature + "C";
    }
}

// Core System Class aggregates Devices and People
class SmartHome {
    private java.util.Map<String, Device> devices;
    private java.util.Map<String, Person> people;

    public SmartHome() {
        this.devices = new java.util.LinkedHashMap<>(); 
        this.people = new java.util.LinkedHashMap<>();
    }

    public void addDevice(Device device) {
        if (devices.containsKey(device.getId())) {
            throw new IllegalArgumentException("Device with ID '" + device.getId() + "' already exists.");
        }
        devices.put(device.getId(), device);
        System.out.println(">>> Registered Device: " + device.getName() + " in " + device.getLocation());
    }

    public void addPerson(Person p) {
        if (people.containsKey(p.getName().toLowerCase())) {
            throw new IllegalArgumentException("Person " + p.getName() + " is already in the system.");
        }
        people.put(p.getName().toLowerCase(), p);
        System.out.println(">>> Added Person: " + p.getName() + " (Currently: " + p.getCurrentRoom() + ")");
    }

    public Device getDevice(String id) throws DeviceNotFoundException {
        if (!devices.containsKey(id)) {
            throw new DeviceNotFoundException("Device lookup failed: ID '" + id + "' not found.");
        }
        return devices.get(id);
    }

    // Advanced Position-based Tracking Logic
    public void movePerson(String personName, String newRoom) {
        Person p = people.get(personName.toLowerCase());
        if (p == null) {
            throw new IllegalArgumentException("Person '" + personName + "' not found in the house.");
        }

        String oldRoom = p.getCurrentRoom();
        if (oldRoom.equalsIgnoreCase(newRoom)) {
            System.out.println(">>> " + p.getName() + " is already in the " + newRoom + ".");
            return;
        }

        p.setCurrentRoom(newRoom);
        System.out.println("\n[MOTION DETECTED] " + p.getName() + " moved from " + oldRoom + " to " + newRoom);

        evaluateLocationBasedAutomation(oldRoom, newRoom);
    }
    
    // Core engine for executing contextual automated behavior based on room presence
    private void evaluateLocationBasedAutomation(String oldRoom, String newRoom) {
        // 1. Is the old room now completely empty?
        boolean isOldRoomEmpty = true;
        for (Person person : people.values()) {
            if (person.getCurrentRoom().equalsIgnoreCase(oldRoom)) {
                isOldRoomEmpty = false;
                break;
            }
        }
        
        System.out.println("--- Triggering Smart Context Automations ---");
        
        for (Device d : devices.values()) {
            // Turn off devices in the old room ONLY IF the room is now empty (and it's not "Outside")
            if (!oldRoom.equalsIgnoreCase("Outside") && d.getLocation().equalsIgnoreCase(oldRoom)) {
                if (isOldRoomEmpty && d.isOn()) {
                    System.out.print("[Energy Save] ");
                    d.turnOff();
                }
            }
            // Turn on devices in the new room (excluding Outside)
            if (!newRoom.equalsIgnoreCase("Outside") && d.getLocation().equalsIgnoreCase(newRoom)) {
                if (!d.isOn()) {
                    System.out.print("[Welcome Event] ");
                    d.turnOn();
                    d.performSpecificAction(); // Demonstrates Polymorphism triggered via motion
                }
            }
        }
    }

    public void listDevices() {
        if (devices.isEmpty()) {
            System.out.println(">>> No devices registered.");
            return;
        }
        System.out.println("\n--- Registered Devices ---");
        for (Device device : devices.values()) {
            System.out.println(device.toString());
        }
        System.out.println("--------------------------\n");
    }

    public void listPeople() {
        if (people.isEmpty()) {
            System.out.println(">>> No one is registered in the house.");
            return;
        }
        System.out.println("\n--- People Present ---");
        for (Person p : people.values()) {
            System.out.println(p.toString());
        }
        System.out.println("----------------------\n");
    }

    public void executeMorningRoutine() {
        if (devices.isEmpty()) {
            System.out.println(">>> No devices to run the routine on.");
            return;
        }
        System.out.println("\n--- Executing Entire House Morning Routine ---");
        for (Device device : devices.values()) {
            if (!device.isOn()) device.turnOn();
            device.performSpecificAction();
        }
        System.out.println("Morning Routine Complete.\n");
    }
}

// Main class CLI
public class SmartHomeSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SmartHome myHome = new SmartHome();
        boolean running = true;

        System.out.println("=========================================");
        System.out.println("   Welcome to the Smart Home System V2!  ");
        System.out.println("   (Now with Real-time Person Tracking)  ");
        System.out.println("=========================================");

        while (running) {
            System.out.println("\n===== MAIN MENU =====");
            System.out.println("1. Add a Light");
            System.out.println("2. Add a Thermostat");
            System.out.println("3. Register a Person");
            System.out.println("4. Move Person to a new Room");
            System.out.println("5. List All Devices");
            System.out.println("6. List People");
            System.out.println("7. Turn Device ON Manually");
            System.out.println("8. Turn Device OFF Manually");
            System.out.println("9. Adjust Environment Variables"); // Brightness/Temp
            System.out.println("0. Exit System");
            System.out.print("Enter your choice: ");

            String choiceStr = scanner.nextLine();
            int choice = -1;
            try {
                choice = Integer.parseInt(choiceStr.trim());
            } catch (NumberFormatException e) {
                System.out.println(">>> Invalid input. Please enter a number.");
                continue;
            }

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter Light ID (e.g., L1): ");
                        String lId = scanner.nextLine().trim();
                        System.out.print("Enter Light Name (e.g., Main Light): ");
                        String lName = scanner.nextLine().trim();
                        System.out.print("Enter Room Location (e.g., Living Room): ");
                        String lLoc = scanner.nextLine().trim();
                        myHome.addDevice(new Light(lId, lName, lLoc));
                        break;
                    case 2:
                        System.out.print("Enter Thermostat ID (e.g., T1): ");
                        String tId = scanner.nextLine().trim();
                        System.out.print("Enter Thermostat Name (e.g., AC Unit): ");
                        String tName = scanner.nextLine().trim();
                        System.out.print("Enter Room Location (e.g., Living Room): ");
                        String tLoc = scanner.nextLine().trim();
                        myHome.addDevice(new Thermostat(tId, tName, tLoc));
                        break;
                    case 3:
                        System.out.print("Enter Person's Name: ");
                        String pName = scanner.nextLine().trim();
                        myHome.addPerson(new Person(pName));
                        break;
                    case 4:
                        System.out.print("Enter Person's Name: ");
                        String movingPersonName = scanner.nextLine().trim();
                        System.out.print("Enter Destination Room (e.g. Living Room, Bedroom, Outside): ");
                        String destRoom = scanner.nextLine().trim();
                        myHome.movePerson(movingPersonName, destRoom);
                        break;
                    case 5:
                        myHome.listDevices();
                        break;
                    case 6:
                        myHome.listPeople();
                        break;
                    case 7:
                        System.out.print("Enter Device ID to turn ON: ");
                        String onId = scanner.nextLine().trim();
                        myHome.getDevice(onId).turnOn();
                        break;
                    case 8:
                        System.out.print("Enter Device ID to turn OFF: ");
                        String offId = scanner.nextLine().trim();
                        myHome.getDevice(offId).turnOff();
                        break;
                    case 9:
                        System.out.print("Enter Device ID to adjust: ");
                        String adjId = scanner.nextLine().trim();
                        Device dev = myHome.getDevice(adjId);
                        if (dev instanceof Light) {
                            System.out.print("Current is Light. Enter new brightness (0-100): ");
                            int b = Integer.parseInt(scanner.nextLine().trim());
                            ((Light) dev).setBrightness(b);
                        } else if (dev instanceof Thermostat) {
                            System.out.print("Current is Thermostat. Enter new temperature (10.0-35.0): ");
                            double temp = Double.parseDouble(scanner.nextLine().trim());
                            ((Thermostat) dev).setTemperature(temp);
                        }
                        break;
                    case 0:
                        System.out.println(">>> Exiting Smart Home System V2. Goodbye!");
                        running = false;
                        break;
                    default:
                        System.out.println(">>> Invalid choice. Please select a valid number from the menu.");
                }
            } catch (DeviceNotFoundException e) {
                System.out.println(">>> System Error: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println(">>> Validation Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println(">>> An unexpected error occurred: " + e.getMessage());
            }
        }
        scanner.close();
    }
}
