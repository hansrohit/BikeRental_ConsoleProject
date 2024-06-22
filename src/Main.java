import java.util.*;
import java.sql.*;

import java.util.*;
import java.util.Date;

class Bike {
    private String bikeId;
    private String brand;
    private String model;
    private int basePricePerDay;

    public Bike(String bikeId, String brand, String model, int basePricePerDay) {
        this.bikeId = bikeId;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
    }

    public String getBikeId() {
        return bikeId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public int getBasePricePerDay() {
        return basePricePerDay;
    }
}

class Customer {
    private String customerId;
    private String name;

    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }
}

class Rental {
    private Bike bike;
    private Customer customer;
    private int days;

    public Rental(Bike bike, Customer customer, int days) {
        this.bike = bike;
        this.customer = customer;
        this.days = days;
    }

    public Bike getBike() {
        return bike;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }
}


class BikeRentalSystem {
    private BikeAccess bikeAccess = new BikeAccess();

    public void menu() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("===== Bike Rental System =====");
            System.out.println("1. Rent a Bike");
            System.out.println("2. Return a Bike");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    rentBike(scanner);
                    break;
                case 2:
                    returnBike(scanner);
                    break;
                case 3:
                    System.out.println("\nThank you for using the Bike Rental System!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private void rentBike(Scanner scanner) throws SQLException {
        System.out.println("\n== Rent a Bike ==\n");
        System.out.print("Enter your name: ");
        String customerName = scanner.nextLine();

        System.out.println("\nAvailable Bikes:");
        bikeAccess.displayinfo();

        System.out.print("\nEnter the Bike ID you want to rent: ");
        String bikeId = scanner.nextLine();

        String bikeBrand = bikeAccess.getBikeBrand(bikeId);
        String bikeModel = bikeAccess.getBikeModel(bikeId);
        int bikePrice = bikeAccess.getBikePrice(bikeId);

        if (bikeBrand == null || bikeModel == null || bikePrice == 0) {
            System.out.println("Invalid Bike ID or Bike not available.");
            return;
        }

        Bike bike = new Bike(bikeId, bikeBrand, bikeModel, bikePrice);

        System.out.print("Enter the number of days for rental: ");
        int rentalDays = scanner.nextInt();
        scanner.nextLine();

        String newCustomerId = "CUS" + (bikeAccess.getCustomerCount() + 1);
        Customer newCustomer = new Customer(newCustomerId, customerName);

        String selectedBike = bikeAccess.checkBikeAvailability(bikeId);

        if (selectedBike != null) {
            int totalPrice = bikeAccess.calculatePrice(rentalDays, selectedBike);
            System.out.println("\n== Rental Information ==\n");
            System.out.println("Customer ID: " + newCustomer.getCustomerId());
            System.out.println("Customer Name: " + newCustomer.getName());
            System.out.println("Bike: " + bike.getBrand() + " " + bike.getModel());
            System.out.println("Rental Days: " + rentalDays);
            System.out.println("Total Price: Rs." + totalPrice);

            System.out.print("\nConfirm rental (y/n): ");
            String confirm = scanner.nextLine();

            if (confirm.equalsIgnoreCase("Y")) {
                Rental rental = new Rental(bike, newCustomer, rentalDays);
                Timestamp rentalDate = new Timestamp(new Date().getTime());
                bikeAccess.addCustomer(newCustomer.getCustomerId(), newCustomer.getName());
                bikeAccess.rentBike(newCustomer.getCustomerId(), newCustomer.getName(), bike.getBikeId(), bike.getBrand(), bike.getModel(), rentalDays, totalPrice, rentalDate);
                System.out.println("\nBike rented successfully.");
            } else {
                System.out.println("\nRental canceled.");
            }
        } else {
            System.out.println("\nInvalid Bike selection or Bike not available for rent.");
        }
    }

    private void returnBike(Scanner scanner) throws SQLException {
        System.out.println("\n== Return a Bike ==\n");
        System.out.print("Enter the Bike ID you want to return: ");
        String bikeId = scanner.nextLine();

        int availability = bikeAccess.returnAvail(bikeId);

        if (availability == 0) {
            String customerName = bikeAccess.returnBike(bikeId);
            if (customerName != null) {
                Timestamp returnTime = new Timestamp(new Date().getTime());
//                bikeAccess.updateReturn(returnTime,bikeId);

                System.out.println("Bike was returned successfully by " + customerName);
            } else {
                System.out.println("Bike was not rented.");
            }
        } else {
            System.out.println("Bike was not rented.");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        BikeRentalSystem rentalSystem = new BikeRentalSystem();
        try {
            rentalSystem.menu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

