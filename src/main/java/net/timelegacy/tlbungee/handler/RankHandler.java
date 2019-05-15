package net.timelegacy.tlbungee.handler;

import net.timelegacy.tlbungee.TLBungee;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RankHandler {
	public List<Rank> rankList = new ArrayList<>();

    public HashMap<String, String> playerCache = new HashMap<>();
	private TLBungee bungee = TLBungee.getInstance();

	public void loadRanks() {

		try {
			MongoCursor<Document> cursor = bungee.mongoDB.ranks.find().iterator();
			while (cursor.hasNext()) {
				Document doc = cursor.next();

				String name = doc.getString("name");
				int priority = doc.getInteger("priority");
				String chat = doc.getString("chat");
				String maincolor = doc.getString("maincolor");
				String cocolor = doc.getString("cocolor");
				String tab = doc.getString("tab");

				rankList.add(new Rank(name, priority, chat, maincolor, cocolor, tab));

			}

			cursor.close();

		} catch (Exception e) {
		}
	}

	/**
	 * Get the rank of a player
	 *
	 * @param p
	 * @return
	 */

	public Rank getRank(String p) {
		Rank rank = null;

		if (bungee.playerHandler.playerExistsName(p)) {
			FindIterable<Document> doc = bungee.mongoDB.players.find(Filters.eq("username", p));
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
