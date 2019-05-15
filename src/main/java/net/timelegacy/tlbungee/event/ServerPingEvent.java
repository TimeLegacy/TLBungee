package net.timelegacy.tlbungee.event;

import net.timelegacy.tlbungee.TLBungee;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.Favicon;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.ServerPing.Players;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class ServerPingEvent implements Listener {

	private static TLBungee bungee = TLBungee.getInstance();
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPing(ProxyPingEvent event) {
		ServerPing sp = event.getResponse();
		Players p = sp.getPlayers();

		p.setMax(9999);
		sp.setFavicon(Favicon.create(getImage("server-icon.png")));
		String ver = "1.8-1.13";
		
		if (bungee.multiplierHandler.isMultiplierEnabled()) {
			sp.setDescription(bungee.messageUtils.c("&b&lMineAqua &f&l|| &b&oALPHA &f&l|| &b&l"
					+ ver
					+ "\n" + "&6&l" + bungee.multiplierHandler.getMultiplier()
					+ "x&r &e&lMultiplier &8- &f&lPLAY NOW!"));

		} else {

			if (!bungee.whitelist) {
				sp.setDescription((bungee.messageUtils.c(
						"&b&lMineAqua &f&l|| &b&oALPHA &f&l|| &b&l"
								+ ver
								+ "\n" + "&b&oBedWars & more! &f&lPLAY NOW!")));

			} else if (bungee.whitelist) {

				ServerPing.Protocol version = sp.getVersion();
				version.setProtocol(999);

				sp.setDescription((bungee.messageUtils.c(
						"&b&lMineAqua &f&l|| &b&oALPHA &f&l|| &b&l"
								+ ver
								+ "\n" + "&c&oNetwork under maintenance! Check back later...")));

				//version.setName("&5&nMaintenance");
				version.setName(bungee.messageUtils.c("&7?/?"));

			}
		}

		sp.setPlayers(p);

		event.setResponse(sp);

		/*List<String> str = new ArrayList<String>();
		StringBuilder builder = new StringBuilder();
		File f = new File("ServerInfo.txt");
		if (!f.exists()) {
			try {
				f.createNewFile();
				System.out.println(
						"[Bungee] File " + f.getAbsolutePath() + " created!");
				return;
			} catch (IOException e1) {
				System.out.println("[Bungee] FILE " + f.getAbsolutePath()
						+ " NOT CREATED!");
				e1.printStackTrace();
				System.out.println("[Bungee] FILE " + f.getAbsolutePath()
						+ " NOT CREATED!");
			}
		}
		if (count >= 20) {
			read = readTextFile(f.getAbsolutePath());
			count = 0;
		}
		count += 1;
		for (int i = 0; i < read.length(); i++) {
			String at = String.valueOf(read.charAt(i));
			if (String.valueOf(at).endsWith("~")) {
				str.add(colorize(builder.toString()));
				builder.delete(0, builder.length());
			} else {
				builder.append(at);
			}
		}
		handleServerPing(event.getResponse(), str);*/
	}

	public BufferedImage getImage(String s) {
		BufferedImage img = null;

		try {
			img = ImageIO.read(new File(s));
		}

		catch (IOException e) {
			e.printStackTrace();
		}

		return img;
	}

	String read;

	public ServerPing handleServerPing(ServerPing ping,
			List<String> lines) {
		if (lines.size() == 0) {
			ping.getPlayers().setSample(null);
			return ping;
		}
		ServerPing.PlayerInfo[] players = new ServerPing.PlayerInfo[lines
				.size()];
		for (int i = 0; i < players.length; i++) {
			players[i] = new ServerPing.PlayerInfo(lines.get(i), "");
		}
		ping.getPlayers().setSample(players);
		return ping;
	}
	
	@SuppressWarnings("resource")
	public String readTextFile(String fileName) {
		String returnValue = "";
		FileReader file = null;
		try {
			file = new FileReader(fileName);
			BufferedReader reader = new BufferedReader(file);
			String line = "";
			while ((line = reader.readLine()) != null) {
				if (!line.endsWith("~")) {
					line = line + "~";
				}
				if ((line == "") || (line.startsWith("~"))
						|| (line.length() <= 1)) {
					line = ChatColor.GREEN + " ~";
				}
				returnValue = returnValue + line;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (file != null) {
				try {
					file.close();
				} catch (IOException localIOException) {
				}
			}
		}
		return returnValue;
	}

}
