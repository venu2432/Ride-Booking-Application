# Ride Booking Application

Welcome to the Ride Booking Application repository! This Spring Boot application provides a seamless experience for users to book rides and for drivers to accept them. Leveraging advanced technologies such as PostgreSQL with the PostGIS extension, the Open Source Routing Machine (OSRM), and real-time weather data integration, this application ensures efficient handling of geospatial data, precise distance calculations, and dynamic fare adjustments based on current weather conditions. Additionally, Spring Security has been implemented to ensure that the platform is robust, reliable, and secure for all users.

## Table of Contents

- [Introduction](#introduction)
- [Technologies Used](#technologies-used)
- [Features](#features)
- [Installation and Setup](#installation-and-setup)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)

## Introduction

The Ride Booking Application simplifies the process of booking and managing rides. Users can effortlessly book rides, while drivers have the flexibility to view and accept ride requests. The application employs sophisticated algorithms for fare calculation and driver matching, ensuring a fair and efficient system for all users. It integrates real-time weather data to dynamically adjust fares, providing a responsive pricing model based on current weather conditions. The application ensures a high level of usability while prioritizing scalability and maintainability, making it ideal for further expansion and customization in real-world deployments.

## Technologies Used

- **Spring Boot**: A robust framework for building the backend service.
- **Java**: The programming language used for developing the application.
- **PostgreSQL**: A powerful relational database for storing user, ride, and driver information.
- **PostGIS**: An extension for PostgreSQL to handle geospatial data, crucial for storing and querying driver locations.
- **OSRM (Open Source Routing Machine)**: Used for precise distance and route calculations.
- **WeatherAPI**: Provides real-time weather data to dynamically adjust pricing based on current weather conditions.

## Features

- **User Ride Booking**: Intuitive interface for users to book rides.
- **Driver Ride Acceptance**: Easy-to-use platform for drivers to view and accept ride requests.
- **Geospatial Data Handling**: Efficient management of driver locations using PostGIS.
- **Distance Calculation**: Accurate distance computations for rides via OSRM.
- **Fare Calculation**: Multiple strategies for calculating fares, considering distance, time, weather conditions and demand.
- **Driver Matching**: Advanced algorithms to match users with the most suitable drivers.
- **Dynamic Pricing**: Allows riders to adjust fares based on their preferences and current demand.

## Security

Security is a core component of the Ride Booking Application. With **Spring Security**, application ensures that both riders and drivers have secure access to the platform through the following measures:

- **User Authentication**: Every user and driver must sign up and log in using a secure authentication process.
- **Role-Based Authorization**: User roles (rider or driver) are assigned specific permissions, ensuring that only authorized users can perform specific actions.
- **Password Encryption**: All user passwords are securely hashed and encrypted, safeguarding them against breaches.

## Installation and Setup

1. **Clone the Repository**:
   ```sh
   git clone https://github.com/venu2432/Ride-Booking-Application.git
   cd Ride-Booking-Application

   ```

2. **Set Up PostgreSQL with PostGIS**:
   Follow the official [PostGIS installation guide](https://postgis.net/install/) to set up PostGIS on your PostgreSQL instance.

3. **Configure the Application**:
   Update the `application.properties` file with your PostgreSQL and PostGIS configurations.

4. **Build and Run the Application**:
   ```sh
   ./mvnw spring-boot:run
   ```

5. **Access the Application**:
   Open your browser and navigate to `http://localhost:8080`.

## Usage

- **Booking a Ride**: Users can sign in, enter their destination, and book a ride.
- **Accepting a Ride**: Drivers can sign in, view available ride requests, and accept them.
- **Fare Adjustment**: Users can adjust the fare based on their preferences during the booking process.

## Contributing

We welcome contributions! If you'd like to contribute, please fork the repository and create a pull request with your changes. Ensure your code adheres to the project's coding standards and includes appropriate tests.

