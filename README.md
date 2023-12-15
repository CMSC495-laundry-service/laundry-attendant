# Laundry Attendant

Laundry Attendant is a Java-based application developed for the CMSC 495 final project. This application allows users to manage their laundry services efficiently. Users can access their accounts, register new ones, and purchase laundry tickets, which are displayed on the user dashboard. The purchased tickets are sent to the server and the admin's dashboard for processing.

## Technologies Used

- JUnit
- Java with Maven
- OpenJFX
- MySQL Connector
- JSON Simple

## Quickstart

1. **Switch to the development branch:**
    ```bash
    git clone https://github.com/CMSC495-laundry-service/laundry-attendant.git
    cd laundry-attendant
    ```

2. **Run the application:**
    ```bash
    mvn javafx:run
    ```

3. **To run tests:**
    ```bash
    mvn test
    ```

## Features

### User Functionality

- **Account Management:**
  - Users can access their accounts or register new ones.

- **Laundry Ticket Purchase:**
  - Users can buy one or multiple tickets for laundry services.

- **Dashboard:**
  - Purchased tickets are displayed on the user dashboard.
 
- **Receipt Detail:**
  - User can go to the specific ticket and click on **_show detail_** to view the receipt containing full detail.
 
- **Cancel**
  - If the order status is PENDING, user can cancel their order.
 
- **Notification**
  - Once the clothes are finished, user will receive a notification when logging in. Click accept to accept the completed order.

### Admin Functionality

- **Laundry Status Management:**
  - Admin has the permission to change the status of laundry tickets.

- **Laundry History:**
  - Admin can view the entire laundry history from the dashboard.

- **Receipt Detail:**
  - Admin can click show detail to see client's order receipt.

### Video Demonstration


https://github.com/CMSC495-laundry-service/laundry-attendant/assets/115853113/6decacf2-2695-44c7-b0a6-2fc3967e0fad




### Contributing

Feel free to contribute to the development of Laundry Attendant.

