package lu.lopolio.main;

import java.util.Scanner;
import javax.security.auth.login.LoginException;
import lu.lopolio.command.CommandMap;
import lu.lopolio.event.BotListener;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

/**
 *
 * @author Lopolio
 */
public class BotDiscord implements Runnable {

    private final JDA jda;
    private final CommandMap commandMap = new CommandMap(this);
    private final Scanner scanner = new Scanner(System.in);
    
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
    }
    
    public static void main(String[] args) {
        
        try {
            BotDiscord botDiscord = new BotDiscord();
            new Thread(botDiscord, "bot").start();
        } catch (LoginException ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void run() {
        running = true;
        
        while (running) {
            if (scanner.hasNextLine()) {
                commandMap.commandConsole(scanner.nextLine());
            }
        }
        
        scanner.close();
        jda.shutdown();
        System.exit(0);
    }
}
