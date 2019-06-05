package net.timelegacy.tlbungee.handler;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import net.timelegacy.tlbungee.mongodb.MongoDB;
import org.bson.Document;

public class MultiplierHandler {

  private static MongoCollection<Document> settings =
      MongoDB.mongoDatabase.getCollection("settings");

  /** Get the multiplier amount */
  public static Integer getMultiplier() {
    FindIterable<Document> doc = settings.find(Filters.eq("name", "multiplier"));
    int multiplier = doc.first().getInteger("amount");

    return multiplier;
  }

  /** Check if multiplier is on */
  public static Boolean isMultiplierEnabled() {
    FindIterable<Document> doc = settings.find(Filters.eq("name", "multiplier"));
    return doc.first().getBoolean("enabled");
  }
}
