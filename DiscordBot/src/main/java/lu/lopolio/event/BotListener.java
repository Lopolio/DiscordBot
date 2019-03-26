package lu.lopolio.event;

import lu.lopolio.command.CommandMap;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.EventListener;

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
    public void onEvent(Event event) {
        if(event instanceof MessageReceivedEvent){
            System.out.println("Message Received");
            onMessage((MessageReceivedEvent)event);
        }
    }
    
    private void onMessage(MessageReceivedEvent event){
        if(event.getAuthor().equals(event.getJDA().getSelfUser())) return;
        
        String message = event.getMessage().getContentRaw();
        if(message.startsWith(commandMap.getTag())){
            System.out.println("Message contains Tag");
            message = message.replaceFirst(commandMap.getTag(), "");
            commandMap.commandUser(event.getAuthor(), message, event.getMessage());
        }
    }
    
}
