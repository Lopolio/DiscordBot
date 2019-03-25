package lu.lopolio.main;

import java.util.logging.Logger;
import javax.security.auth.login.LoginException;
import lu.lopolio.event.BotListener;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

/**
 *
 * @author Lopolio
 * 
 * The Main Class Initializing the JDa Discord Bot
 */
public class Main {
    
    private final static Logger LOG = Logger.getLogger(Main.class.getName());
    public static void main(String[] args){
        try{
            JDA jda = new JDABuilder(AccountType.BOT).setToken("MzY4MDgwNTcxNTYzMzc2NjQy.D3qSzg.L5luEIrVn6-ywFeoAbjT7YF5xR8").build();
            LOG.info("Bot Connected");
            jda.addEventListener(new BotListener());
        }catch(LoginException | IllegalArgumentException e){
            e.printStackTrace();
        }
    }
}
