package net.timelegacy.tlbungee.commands;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import java.security.SecureRandom;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.timelegacy.tlbungee.TLBungee;
import net.timelegacy.tlbungee.mongodb.MongoDB;
import net.timelegacy.tlbungee.utils.MessageUtils;
import org.bson.Document;

public class HubCommand extends Command {

  private static MongoCollection<Document> servers = MongoDB.mongoDatabase.getCollection("servers");
  private TLBungee plugin = TLBungee.getPlugin();

  public HubCommand() {
    super("hub", "", "exit");
  }

  @Override
  public void execute(CommandSender sender, String[] args) {

    if (sender instanceof ProxiedPlayer) {

      ProxiedPlayer p = (ProxiedPlayer) sender;

      FindIterable<Document> doc =
          servers.find(Filters.eq("uid", p.getServer().getInfo().getName()));
      String state = doc.first().getString("type");

      if (!state.equalsIgnoreCase("LOBBY")) {
        p.connect(
            ProxyServer.getInstance()
                .getServerInfo(
                    plugin.getHubs().get(new SecureRandom().nextInt(plugin.getHubs().size()))));
      } else {
        MessageUtils.sendMessage(
            p, MessageUtils.ERROR_COLOR + "You are already connected to a lobby.", true);
      }
    }
  }
}
