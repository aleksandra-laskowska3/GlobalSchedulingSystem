package helper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class databaseQuery {

    private static PreparedStatement statement;

    public static void setPreparedStatement (Connection databaseConnection, String sql) {
        try {
            statement = databaseConnection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static PreparedStatement getPreparedStatement(){
        return statement;
    }
}
