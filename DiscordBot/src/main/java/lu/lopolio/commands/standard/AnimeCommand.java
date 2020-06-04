package lu.lopolio.commands.standard;

import java.awt.Color;
import java.util.Date;
import lu.lopolio.command.Command;
import lu.lopolio.command.CommandMap;
import lu.lopolio.main.BotDiscord;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

/**
 *
 * @author Lopolio
 */
public class AnimeCommand {

    private Date date;
    private final BotDiscord botDiscord;
    private final CommandMap commandmap;

    public AnimeCommand(BotDiscord botDiscord, CommandMap commandMap) {
        this.botDiscord = botDiscord;
        this.commandmap = commandMap;
    }

    @Command(name = "wallpapers", description = "Get Links to Cloud for anime Wallpapers", type = Command.ExecutorType.USER)
    private void wallpapers(User user, MessageChannel channel) {
        date = new Date();
        System.out.println(date.toString() + " User: " + user.getName() + " executed the wallpapers command");

        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Wallpaper Cloud Links");
        builder.setColor(Color.CYAN);

        builder.addField("PC Wallpapers", "https://mega.nz/#F!tPY0wITC!6_qMz9N2xg-2D2zE3r6_lg", false);
        builder.addField("Phone Wallpapers", "https://mega.nz/#F!wOQw1IDK!jK06cstG1NaUEkUlzf014A", false);
        builder.addField("Information", "Pc Wallpapers Resolution 1920x1080 and Phone 720x1280, each is HD quality", false);

        user.openPrivateChannel().queue((privateChannel) -> privateChannel.sendMessage(builder.build()).queue());
    }
}
