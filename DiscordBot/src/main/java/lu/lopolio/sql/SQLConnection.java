package lu.lopolio.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;

/**
 *
 * @author Lopolio
 */
public class SQLConnection {

    private Date date;
    private static final int XPGAINED = 1;
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

    public void addXPToUsers(ArrayList<Member> users) {
        try {
            int level = 1;
            boolean gainedNewRole = false;
            getConnection();
            for (Member user : users) {
                if (user.getUser().isBot() || user.getOnlineStatus() == OnlineStatus.INVISIBLE || user.getOnlineStatus() == OnlineStatus.OFFLINE
                        || user.getOnlineStatus() == OnlineStatus.UNKNOWN) {
                    continue;
                }
                ResultSet result = connection.createStatement().executeQuery("Select * from users where id = " + user.getUser().getId());
                if (result.next()) {
                    //a user already exists in the db
                    String id = result.getString(1);
                    int xp = result.getInt(2);
                    level = result.getInt(3);

                    xp += XPGAINED;
                    if (xp > 500) {
                        level++;
                        xp -= 500;
                        date = new Date();
                        System.out.println(date.toString() + " " + user.getUser().getName() + " has leveled up to: " + level);

                        ResultSet rolesresult = connection.createStatement().executeQuery("select role from roles where level = '" + level + "';");
                        if (rolesresult.next()) {
                            gainedNewRole = true;
                            String role = rolesresult.getString(1);
                            user.getGuild().getController().addRolesToMember(user, user.getGuild().getRolesByName(role, true).get(0)).complete();
                            sendLevelUpMessage(gainedNewRole, user.getGuild().getDefaultChannel(), user, level, role);
                        }
                        if (!gainedNewRole) {
                            sendLevelUpMessage(gainedNewRole, user.getGuild().getDefaultChannel(), user, level, "");
                        }

                    }
                    connection.createStatement().execute("UPDATE users SET xp = "
                            + xp + ", level = "
                            + level + " WHERE id = '"
                            + id + "'");

                } else {
                    //no user like this already exists so we need to create a new
                    date = new Date();
                    System.out.println(date.toString() + " User: " + user.getUser().getName() + " did not exist, adding to DB");
                    connection.createStatement().execute("INSERT INTO users (id, xp, level) VALUES ('"
                            + user.getUser().getId() + "', "
                            + 0 + ", "
                            + 1 + ")");

                    ResultSet rolesresult = connection.createStatement().executeQuery("select role from roles where level = '" + level + "';");
                    if (rolesresult.next()) {
                        gainedNewRole = true;
                        String role = rolesresult.getString(1);
                        user.getGuild().getController().addRolesToMember(user, user.getGuild().getRolesByName(role, true).get(0)).complete();
                        sendLevelUpMessage(gainedNewRole, user.getGuild().getDefaultChannel(), user, level, role);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void sendLevelUpMessage(boolean gainedNewRole, MessageChannel channel, Member member, int level, String role) {
        String message = "";

        message += "Congratulations: "+member.getAsMention()+"\nYou reached Level "+ level;
        if(gainedNewRole){
            message+= "\nYou received a new Role: "+role;
        }
        channel.sendMessage(message).queue();
    }
}
