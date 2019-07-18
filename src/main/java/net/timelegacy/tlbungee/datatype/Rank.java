package net.timelegacy.tlbungee.datatype;

public class Rank {

  private String name;
  private int priority;
  private String chat;
  private String color;
  private String tab;

  public Rank(
      String name, int priority, String chat, String color,
      String tab) {
    this.name = name;
    this.priority = priority;
    this.chat = chat;
    this.color = color;
    this.tab = tab;
  }

  public String getName() {
    return name;
  }

  public String getChat() {
    return chat;
  }

  public int getPriority() {
    return priority;
  }

  public String getTab() {
    return tab;
  }

  public String getColor() {
    return color;
  }
}
