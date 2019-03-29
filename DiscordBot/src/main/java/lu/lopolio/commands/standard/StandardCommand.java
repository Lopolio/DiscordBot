/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lu.lopolio.commands.standard;

import java.awt.Color;
import lu.lopolio.command.Command;
import lu.lopolio.command.CommandMap;
import lu.lopolio.command.SimpleCommand;
import lu.lopolio.main.BotDiscord;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

/**
 *
 * @author Lopolio
 */
public class StandardCommand {

    private final BotDiscord botDiscord;
    private final CommandMap commandmap;

    public StandardCommand(BotDiscord botDiscord, CommandMap commandMap) {
        this.botDiscord = botDiscord;
        this.commandmap = commandMap;
    }

    @Command(name = "test", description = "A Simple test to see if the bot is working", type = Command.ExecutorType.USER)
    private void info(User user, MessageChannel channel) {
        System.out.println("User: " + user.getName() + " executed the test command");
        channel.sendMessage(user.getAsMention() + " in the Channel " + channel.getName()).complete();
    }

    @Command(name = "help", description = "A Simple Help Displaying all the commands", type = Command.ExecutorType.USER)
    private void help(User user, MessageChannel channel) {
        System.out.println("User: " + user.getName() + " executed the help command");

        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Command List");
        builder.setColor(Color.CYAN);

        for (SimpleCommand command : commandmap.getCommands()) {
            if (command.getExecutorType() == Command.ExecutorType.CONSOLE) {
                continue;
            }

            builder.addField("$"+command.getName(), command.getDescription(), false);

        }
        
        user.openPrivateChannel().queue((privateChannel) -> privateChannel.sendMessage(builder.build()).queue());
    }
}
