# Design Parking Lot (v2)

1. **Requirements Gathering**
#### Functional Requirements:
- Vehicle Types: Cars, Motorcycles, Trucks
- Parking Space Types: Compact, Regular, Large
- System assign parking spaces based on vehicle type and availability
- Ticket issue on Entry and calculate parking fee on Exit
- Fees based on duration and vehicle type with rates varying by time of day
#### Non-Functional Requirements:
- Scalability to handle peak hours
- Consistency in ticketing and fee calculation
- High availability and fault tolerance

2. Identify Entities and Relationships
- Vehicle (VehicleID, Type, LicensePlate)
- ParkingSpace (SpaceID, Type, IsOccupied)
- Ticket (TicketID, VehicleID, SpaceID, EntryTime, ExitTime, Fee)
- ParkingManager (manages parking spaces and tickets)
- ParkingLot (contains parking spaces and manages overall operations)

3. Class Design
- Vehicle Interface: Represents a vehicle with attributes like size and license plate. All vehicle types implement this interface.
- ParkingSpot Interface: Represents a parking spot with attributes like size and occupancy status. All parking spot types implement this interface.
- ParkingManager Class: Primary function is to identifying available parking spaces, assigning most suitable spot for each vehicle, maintaining a record of occupied spaces, and calculating parking fees based on duration and vehicle type.
- Ticket Class: Represents a parking ticket with attributes like entry time, exit time, and fee calculation logic.
- FareStrategy Interface: Defines a strategy for calculating parking fees based on vehicle type and time of day. Different implementations can be created for different fee structures.
- FareCalculator Class: Implements the FareStrategy interface and contains logic to calculate fees based on the defined rates.
- ParkingLot Class: Represents the parking lot itself, containing a collection of parking spaces and managing overall operations.