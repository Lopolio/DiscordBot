/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lu.lopolio.commands.standard;

import java.awt.Color;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import lu.lopolio.command.Command;
import lu.lopolio.command.CommandMap;
import lu.lopolio.command.SimpleCommand;
import lu.lopolio.main.BotDiscord;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

/**
 *
 * @author Lopolio
 */
public class StandardCommand {

    private Date date;
    private final BotDiscord botDiscord;
    private final CommandMap commandmap;

    public StandardCommand(BotDiscord botDiscord, CommandMap commandMap) {
        this.botDiscord = botDiscord;
        this.commandmap = commandMap;
    }

    @Command(name = "test", description = "A Simple test to see if the bot is working", type = Command.ExecutorType.USER)
    private void info(User user, MessageChannel channel) {
        date = new Date();
        System.out.println(date.toString()+" User: " + user.getName() + " executed the test command");
        channel.sendMessage(user.getAsMention() + " in the Channel " + channel.getName()).complete();
    }

    @Command(name = "help", description = "A Simple Help Displaying all the commands", type = Command.ExecutorType.USER)
    private void help(User user, MessageChannel channel) {
        date = new Date();
        System.out.println(date.toString()+" User: " + user.getName() + " executed the help command");

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
    
    @Command(name = "info", description = "Get a Description for the Bot and helpfull Information", type = Command.ExecutorType.USER)
    private void botInfo(User user, MessageChannel channel) {
        date = new Date();
        System.out.println(date.toString()+" User: " + user.getName() + " executed the help command");
        
        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model;
        String version = "";
        try {
            model = reader.read(new FileReader("pom.xml"));
            version = model.getVersion();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (XmlPullParserException ex) {
            ex.printStackTrace();
        }
        
        
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Bot Info");
        builder.setColor(Color.CYAN);
        
        builder.addField("Current Build", version, false); //version
        builder.addField("Description", "I am the Bot for the Disctrict Server, I have several commands at my disposal,"
                + " for more details on my commands execute the $help", false); //version
        builder.addField("Developer", "My Developer is known as either Sensei, Lopi or simply Joe", false); //Developer
        builder.addField("GitHub Repository", "https://github.com/Lopolio/DiscordBot", false); //GitHub
        builder.addField("Zenkits Tasks", "https://zenkit.com/collections/hwsW867X1/views/V27KKWyZO6", false); //Zenkit
        builder.addField("General Information", "If you have a feature Request or anything, do not hesitate to contact "
                + "Lopi-Sensei, or if you wish to contribute in any way, ask my Developer for access to GitHub/Zenkit. "
                + "Whereas GitHub is the Repository for the Current Code, and Zenkit a ToDo List for work!", false); //General
        user.openPrivateChannel().queue((privateChannel) -> privateChannel.sendMessage(builder.build()).queue());
    }

    @Command(name = "spam", description="Spam someone a lot of messages", type = Command.ExecutorType.USER)
    private void spamUser(User user, MessageChannel channel, Message message){
        date = new Date();
        System.out.println(date.toString()+" User: " + user.getName() + " executed the spam command");

        if(user.getName().contains("Lopolio")){
            for(Member m :message.getMentionedMembers()){
                int counter = 0;
                while(counter < 25){
                    counter++;
                    m.getUser().openPrivateChannel().queue((privateChannel) -> privateChannel.sendMessage("I am Spamming you son Sensei's Orders").queue());
                }
            }
        }else{
            channel.sendMessage("I only listen to Sensei").complete();
        }
    }
}
