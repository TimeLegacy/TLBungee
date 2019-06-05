package net.timelegacy.tlbungee.handler;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import java.util.UUID;
import net.timelegacy.tlbungee.TLBungee;
import net.timelegacy.tlbungee.mongodb.MongoDB;
import org.bson.Document;

public class ServerHandler {
  private static TLBungee core = TLBungee.getPlugin();
  private static MongoCollection<Document> servers = MongoDB.mongoDatabase.getCollection("servers");

  /**
   * Get the type of the server
   *
   * @param uuid server's uuid
   */
  public static String getType(UUID uuid) {
    FindIterable<Document> doc = servers.find(Filters.eq("uuid", uuid.toString()));
    String state = doc.first().getString("type");

    return state;
  }

  /**
   * Get the max count of players on the server
   *
   * @param uuid server's uuid
   */
  public static Integer getMaxPlayers(UUID uuid) {
    FindIterable<Document> doc = servers.find(Filters.eq("uuid", uuid.toString()));
    Integer maxPlayers = doc.first().getInteger("max_players");

    return maxPlayers;
  }

  /**
   * Get the online players
   *
   * @param uuid server's uuid
   */
  public static Integer getOnlinePlayers(UUID uuid) {
    FindIterable<Document> doc = servers.find(Filters.eq("uuid", uuid.toString()));
    Integer onlinePlayers = doc.first().getInteger("online_players");

    return onlinePlayers;
  }

  /**
   * Get the status of the server
   *
   * @param uuid server's uuid
   */
  public static boolean isOnline(UUID uuid) {
    FindIterable<Document> doc = servers.find(Filters.eq("uuid", uuid.toString()));
    boolean online = doc.first().getBoolean("online");

    return online;
  }
}
