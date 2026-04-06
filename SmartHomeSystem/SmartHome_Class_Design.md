# Smart Home System Design (V2 - Context Aware)

## 1. Core Entities and Relationships
- **Device (Abstract)**: The foundational class defining common structure.
- **Light**: A concrete smart home device (`Inherits` from `Device`).
- **Thermostat**: A concrete smart home device (`Inherits` from `Device`).
- **Person**: Represents an individual inside the smart home environment, tracking their current contextual room assignment.
- **SmartHome**: Manages registered devices AND dynamically tracks the movements of 'Person' entities (`Aggregation` of `Device` and `Person`). Handles cross-entity location events.
- **DeviceNotFoundException**: A system exception to handle failed lookups safely.

## 2. Object-Oriented Principles Applied
- **Encapsulation**: 
  - All attributes (e.g., `id`, `location`, `currentRoom`) are kept strictly private inside their class borders.
  - States are safely mutated through protective setter methods, preventing any unexpected system overrides.

- **Inheritance**: 
  - `Light` and `Thermostat` inherit core identifiers (`id`, `name`, `location`) and basic functions (`turnOn()`, `turnOff()`) from the parent `Device` class, heavily reducing redundant code constraints.

- **Polymorphism**: 
  - The `Device` class sets the rule that every device must formally implement `performSpecificAction()`.
  - When a user moves into a room, the overarching controller scans as generic `Device` elements, but intelligently knows which action to trigger per active device (the Light glows, the Thermostat regulates climate) automatically relying entirely on Polymorphism bounds without nested `instanceof` blocks.

- **Abstraction**: 
  - `Device` remains strictly `abstract`. A raw device concept cannot perform specific commands directly since it lacks inherent hardware definitions.

## 3. Class Attributes and Methods Updates
### `Device` (Abstract)
- **Attributes**: `String id`, `String name`, `String location`, `boolean isOn`
- **Methods**: `turnOn()`, `turnOff()`, `getLocation()`, `abstract void performSpecificAction()`

### `Person`
- **Attributes**: `String name`, `String currentRoom`
- **Methods**: `setCurrentRoom(String)`, `getCurrentRoom()`, `getName()`

### `SmartHome`
- **Attributes**: `Map<String, Device> devices`, `Map<String, Person> people`
- **Methods**: `addDevice()`, `addPerson()`, `movePerson()`, `evaluateLocationBasedAutomation()`

## 4. Advanced Requirements Implemented
- **Data Integrity & Validation**: Strict constraints ensure blanks or nulls are never saved to entity profiles. Realistic environment variables heavily protect the operational state of lights and HVAC equivalents.
- **Location-Based Environment Automation (Context Subsystem Pivot)**: The system registers location changes natively. Whenever a formally registered `Person` is pushed between states (i.e. 'Living Room' to 'Bedroom'), a global contextual check processes:
  1. The target room triggers a "Welcome Event"; identifying devices registered uniquely to that state space and commanding an autonomous Startup event.
  2. The origin room verifies the presence check - safely determining if the remaining room carries an absolute net-zero count for presence. If zero, it initiates a global "Energy Save" procedure across the abandoned cluster.
