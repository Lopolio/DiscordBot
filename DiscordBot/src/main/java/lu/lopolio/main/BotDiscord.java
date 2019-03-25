package lu.lopolio.main;

import javax.security.auth.login.LoginException;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

/**
 *
 * @author Lopolio
 */
public class BotDiscord {
    public static JDA jda;
    
    private static void main(String[] args){
        
        try {
            jda = new JDABuilder(AccountType.BOT).setToken("").build();
        } catch (LoginException ex) {
            ex.printStackTrace();
        }
    }
}
