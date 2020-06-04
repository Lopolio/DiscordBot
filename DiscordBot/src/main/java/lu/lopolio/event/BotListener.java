package lu.lopolio.event;

import lu.lopolio.command.CommandMap;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;

import javax.annotation.Nonnull;

/**
 *
 * @author Lopolio
 */
public class BotListener implements EventListener{

    private final CommandMap commandMap;

    public BotListener(CommandMap commandMap) {
        this.commandMap = commandMap;
    }
    
    
    @Override
    public void onEvent(GenericEvent event) {
        if(event instanceof MessageReceivedEvent){
            onMessage((MessageReceivedEvent)event);
        }
    }
    
    private void onMessage(MessageReceivedEvent event){
        if(event.getAuthor().equals(event.getJDA().getSelfUser())) return;
        String message = event.getMessage().getContentRaw();
        if(message.startsWith(commandMap.getTag())){
            
            message = message.substring(1);
            if(message.charAt(0) == ' ') message = message.substring(1);
            commandMap.commandUser(event.getAuthor(), message, event.getMessage());
        }
    }
}
