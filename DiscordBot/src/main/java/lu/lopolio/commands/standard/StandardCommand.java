/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lu.lopolio.commands.standard;

import lu.lopolio.command.Command;
import lu.lopolio.main.BotDiscord;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

/**
 *
 * @author Lopolio
 */
public class StandardCommand {
    
    private final BotDiscord botDiscord;

    public StandardCommand(BotDiscord botDiscord) {
        this.botDiscord = botDiscord;
    }
    
    
    
    @Command(name="stop", type = Command.ExecutorType.CONSOLE)
    private void stop(){
        botDiscord.setRunning(false);
    }
    
    @Command(name="info", type = Command.ExecutorType.USER)
    private void info(User user, MessageChannel channel){
        channel.sendMessage(user.getAsMention()+" in the Channel "+channel.getName()).complete();
    }
}
