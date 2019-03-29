package lu.lopolio.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;
import net.dv8tion.jda.core.entities.User;

/**
 *
 * @author Lopolio
 */
public class SQLConnection {

    private Date date;
    private static final int XPGAINED = 100;
    // init connection object
    private Connection connection = null;

    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/discordbot?serverTimezone=UTC";
    private static final String USERNAME = "BotDiscord";
    private static final String PASSWORD = "BotDiscord1550155";

    public SQLConnection() {
    }
    
    public Connection getConnection() throws Exception {
        if (connection == null) {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }
        return connection;
    }
    
    public void addXPToUsers(List<User> users){
        try {
            getConnection();
            for(User user : users){
                if(user.isBot()) continue;
                ResultSet result = connection.createStatement().executeQuery("Select * from users where id = "+user.getId());
                if(result.next()){
                    //a user already exists in the db
                    String id = result.getString(1);
                    int xp = result.getInt(2);
                    int level = result.getInt(3);
                    
                    xp += XPGAINED;
                    if(xp > 500){
                        level ++;
                        xp -= 500;
                        date = new Date();
                        System.out.println(date.toString()+" "+user.getName()+" has leveled up to: " + level);
                    }
                    connection.createStatement().execute("UPDATE users SET xp = "
                                                                    + xp+", level = "
                                                                    + level +" WHERE id = '"
                                                                    +id+"'");
                }else{
                    //no user like this already exists so we need to create a new
                    date = new Date();
                    System.out.println(date.toString()+" User: "+ user.getName()+ " did not exist, adding to DB");
                    connection.createStatement().execute("INSERT INTO users (id, xp, level) VALUES ('"
                                                                        +user.getId()+ "', "
                                                                        +0+", "
                                                                        +1+")");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
