package net.timelegacy.tlbungee.handler;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import java.util.ArrayList;
import java.util.List;
import net.timelegacy.tlbungee.mongodb.MongoDB;
import org.bson.Document;

public class RankHandler {

	public static List<Rank> rankList = new ArrayList<>();
	public static MongoCollection<Document> ranks = MongoDB.mongoDatabase.getCollection("ranks");
	public static MongoCollection<Document> players = MongoDB.mongoDatabase.getCollection("players");

	public static void loadRanks() {

		try {
			MongoCursor<Document> cursor = ranks.find().iterator();
			while (cursor.hasNext()) {
				Document doc = cursor.next();

				String name = doc.getString("name");
				int priority = doc.getInteger("priority");
				String chat = doc.getString("chat_format");
				String primary_color = doc.getString("primary_color");
				String secondary_color = doc.getString("secondary_color");
				String tab = doc.getString("tab_format");

				rankList.add(new Rank(name, priority, chat, primary_color, secondary_color, tab));

			}

			cursor.close();

		} catch (Exception e) {
		}
	}

	public static Rank getRank(String playerName) {
		Rank rank = null;

		if (PlayerHandler.playerExistsName(playerName)) {
			FindIterable<Document> doc = players.find(Filters.eq("username", playerName));
			String rnk = doc.first().getString("rank");

			for (Rank r : rankList) {
				if (r.getName().equalsIgnoreCase(rnk)) {
					rank = r;
				}
			}
		}
		return rank;
	}

}
