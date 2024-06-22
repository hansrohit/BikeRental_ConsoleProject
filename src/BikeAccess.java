import java.sql.*;
import java.util.Date;

public class BikeAccess {

    public void displayinfo() throws SQLException {
        String query = "SELECT * FROM Bikes WHERE IsAvailable = 1";
        try (Connection con = Jdbc.getConnections();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                System.out.println("Bike Id: " + rs.getString("BikeId") +
                        " Brand: " + rs.getString("Brand") +
                        " Model: " + rs.getString("Model") +
                        " BasePricePerDay: " + rs.getInt("BasePricePerDay"));
            }
        }
    }

    public void addCustomer(String id, String name) throws SQLException {
        String query = "INSERT INTO Customers VALUES (?, ?)";
        try (Connection con = Jdbc.getConnections();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setString(1, id);
            pst.setString(2, name);
            pst.executeUpdate();
        }
    }

    public int returnAvail(String bikeId) throws SQLException {
        String query = "SELECT IsAvailable FROM Bikes WHERE BikeId = ?";
        int availability = 0;

        try (Connection con = Jdbc.getConnections();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, bikeId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    availability = rs.getInt("IsAvailable");
                }
            }
        }

        return availability;
    }

    public String checkBikeAvailability(String bikeId) throws SQLException {
        String query = "SELECT BikeId FROM Bikes WHERE BikeId = ? AND IsAvailable = 1";
        try (Connection con = Jdbc.getConnections();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setString(1, bikeId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("BikeId");
                }
            }
        }
        return null;
    }

    public int calculatePrice(int rentalDays, String bikeId) throws SQLException {
        String query = "SELECT BasePricePerDay FROM Bikes WHERE BikeId = ?";
        try (Connection con = Jdbc.getConnections();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setString(1, bikeId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("BasePricePerDay") * rentalDays;
                }
            }
        }
        return 0;
    }

    public void rentBike(String customerId, String customerName, String bikeId, String brand, String model, int days, int amount, Timestamp rentalDate) throws SQLException {
        String insertQuery = "INSERT INTO Rental(CustomerId, CustomerName, BikeId, Brand, Model, Days, Amount, Rent_Date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String updateQuery = "UPDATE Bikes SET IsAvailable = 0 WHERE BikeId = ?";

        try (Connection con = Jdbc.getConnections();
             PreparedStatement insertPst = con.prepareStatement(insertQuery);
             PreparedStatement updatePst = con.prepareStatement(updateQuery)) {

            insertPst.setString(1, customerId);
            insertPst.setString(2, customerName);
            insertPst.setString(3, bikeId);
            insertPst.setString(4, brand);
            insertPst.setString(5, model);
            insertPst.setInt(6, days);
            insertPst.setInt(7, amount);
            insertPst.setTimestamp(8, rentalDate);
            insertPst.executeUpdate();

            updatePst.setString(1, bikeId);
            updatePst.executeUpdate();
        }
    }

    public String returnBike(String bikeId) throws SQLException {
        String customerName = null;
        String selectQuery = "SELECT * FROM Rental WHERE BikeId = ?";
        String updateQuery = "UPDATE Bikes SET IsAvailable = 1 WHERE BikeId = ?";
        String query = "SELECT IsAvailable FROM Bikes WHERE BikeId = ?";

        try (Connection con = Jdbc.getConnections();
             PreparedStatement selectStmt = con.prepareStatement(selectQuery);
             PreparedStatement updateStmt = con.prepareStatement(updateQuery);
             PreparedStatement St = con.prepareStatement(query)){

            St.setString(1,bikeId);
            ResultSet set = St.executeQuery();


            selectStmt.setString(1, bikeId);
            ResultSet rs = selectStmt.executeQuery();

            while(rs.next()) {
//                if (set.getInt(5)==1) {
                    customerName = rs.getString(3);
                    updateStmt.setString(1, bikeId);
                    updateStmt.executeUpdate();

//                }
            }
        }

        return customerName;
    }


    public int getCustomerCount() throws SQLException {
        String query = "SELECT COUNT(*) FROM Customers";
        try (Connection con = Jdbc.getConnections();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public String getBikeId(String bikeId) throws SQLException {
        String query = "SELECT BikeId FROM Bikes WHERE BikeId = ?";
        try (Connection con = Jdbc.getConnections();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setString(1, bikeId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("BikeId");
                }
            }
        }
        return null;
    }

    public String getBikeBrand(String bikeId) throws SQLException {
        String query = "SELECT Brand FROM Bikes WHERE BikeId = ?";
        try (Connection con = Jdbc.getConnections();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setString(1, bikeId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("Brand");
                }
            }
        }
        return null;
    }

    public String getBikeModel(String bikeId) throws SQLException {
        String query = "SELECT Model FROM Bikes WHERE BikeId = ?";
        try (Connection con = Jdbc.getConnections();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setString(1, bikeId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("Model");
                }
            }
        }
        return null;
    }

    public int getBikePrice(String bikeId) throws SQLException {
        String query = "SELECT BasePricePerDay FROM Bikes WHERE BikeId = ?";
        try (Connection con = Jdbc.getConnections();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setString(1, bikeId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("BasePricePerDay");
                }
            }
        }
        return 0;
    }

//    public void updateReturn(Timestamp date,String bike) throws SQLException{
//        String query="update rental set Return_Date = ? where BikeId = ?";
//        try(Connection con = Jdbc.getConnections();
//        PreparedStatement st= con.prepareStatement(query)){
//            st.setTimestamp(1,date);
//            st.setString(2,bike);
//            st.executeUpdate();
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//    }

}

