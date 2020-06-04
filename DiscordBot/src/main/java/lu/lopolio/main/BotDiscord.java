package lu.lopolio.main;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.login.LoginException;
import lu.lopolio.command.CommandMap;
import lu.lopolio.event.BotListener;
import lu.lopolio.sql.SQLConnection;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

/**
 *
 * @author Lopolio
 */
public class BotDiscord implements Runnable {

    private SQLConnection dbconnection = new SQLConnection();
    private final JDA jda;
    private final CommandMap commandMap = new CommandMap(this);
    
    private boolean running;
    
    public void setRunning(boolean running) {
        this.running = running;
    }

    public JDA getJda() {
        return jda;
    }
    
    public BotDiscord() throws LoginException {
        jda = new JDABuilder(AccountType.BOT).setToken("MzY4MDgwNTcxNTYzMzc2NjQy.D3vu0A.te3pUXeeHBkwkPnlL0lBs-A6-64").build();
        jda.addEventListener(new BotListener(commandMap));
        running = true;
        new Thread(this, "bot").start();
    }
    
    @Override
    public void run() {
        setRunning(true);
        
        while (running) {
            try {
                addXPtoUsers();
                Thread.sleep(60000);
            } catch (InterruptedException ex) {
                Logger.getLogger(BotDiscord.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void addXPtoUsers(){
        try {
            ArrayList<Member> members = new ArrayList<>();
            for(TextChannel channel : jda.getTextChannels()){
                for(Member m : channel.getMembers()){
                    if(!members.contains(m)){
                        members.add(m);
                    }
                }
            }
            dbconnection.addXPToUsers(members);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public boolean haltBot(){
        jda.shutdown();
        setRunning(false);
        return true;
    }
}
