package DAO;
import model.User;
import javafx.collections.FXCollections;
import helper.JDBC;
import java.sql.SQLException;

import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * User Interaction class
 * This is used to communicate data between the database and the application
 * @author Alekandra Laskowska
 */
public class userInteraction{

    /**
     * Method that checks if the entered userName and passWord match to the ones in the database
     * @param userName the userName that is entered at login
     * @param passWord the passWord that is entered at login
     * @return If the username and password match then a user_ID is returned
     * If the username and password do not match then a -1 is returned
     */
    public static int checkUser(String userName, String passWord) {
        try{
            String sql = "SELECT * FROM USERS WHERE User_Name= '" + userName + "' AND Password= '" + passWord + "'";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                if(rs.getString("User_Name").equals(userName)){
                    if(rs.getString("Password").equals(passWord)){
                        return rs.getInt("User_ID");
                    }
                }
            }

            }catch (SQLException e){
            e.printStackTrace();
        }
        return -1;
                }

    /**
     * Method that makes a list of all the users from the database
     * @return observable list of users
     */
        public static ObservableList<User> getAllUsers () {
            ObservableList<User> usersObservableList = FXCollections.observableArrayList();
            try {

                String sql = "SELECT * from USERS";
                PreparedStatement ps = JDBC.connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int user_ID = rs.getInt("User_ID");
                    String user_Name = rs.getString("User_Name");
                    String password = rs.getString("Password");
                    User user = new User(user_ID, user_Name, password);
                    usersObservableList.add(user);
                }
            }
            catch (SQLException e){
                e.printStackTrace();
            }
            return usersObservableList;

        }
    }