package net.timelegacy.tlbungee.handler;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.timelegacy.tlbungee.TLBungee;
import net.timelegacy.tlbungee.datatype.Rank;
import net.timelegacy.tlbungee.mongodb.MongoDB;
import org.bson.Document;

public class RankHandler {

  public static List<Rank> rankList = new ArrayList<>();

  private static TLBungee plugin = TLBungee.getPlugin();

  private static MongoCollection<Document> ranks = MongoDB.mongoDatabase.getCollection("ranks");
  private static MongoCollection<Document> players = MongoDB.mongoDatabase.getCollection("players");

  /** Load the ranks from the database */
  public static void loadRanks() {

    try {
      MongoCursor<Document> cursor = ranks.find().iterator();
      while (cursor.hasNext()) {
        Document doc = cursor.next();

        String name = doc.getString("name");
        int priority = doc.getInteger("priority");
        String chat = doc.getString("chat_format");
        String color = doc.getString("color");
        String tab = doc.getString("tab_format");

        rankList.add(new Rank(name, priority, chat, color, tab));
      }

      cursor.close();

    } catch (Exception e) {
      System.out.println(e);
    }
  }

  /**
   * Get the rank of a player
   *
   * @param uuid player's uuid
   */
  public static Rank getRank(UUID uuid) {
    if (PlayerHandler.playerExists(uuid)) {
      FindIterable<Document> doc = players.find(Filters.eq("uuid", uuid.toString()));
      String rnk = doc.first().getString("rank");

      return stringToRank(rnk);
    }

    return stringToRank("DEFAULT");
  }

  /**
   * Convert string to a rank if it exists
   *
   * @param rank
   * @return
   */
  public static Rank stringToRank(String rank) {
    for (Rank r : rankList) {
      if (r.getName().equalsIgnoreCase(rank)) {
        return r;
      }
    }
    return null;
  }

  /**
   * Check if the rank is valid
   *
   * @param rank rank name as string
   * @return
   */
  public static boolean isValidRank(String rank) {
    for (Rank r : rankList) {
      if (r.getName().equalsIgnoreCase(rank)) {
        return true;
      }
    }

    return false;
  }
}
