package net.timelegacy.tlbungee;

public class ToggleOptions {

  private boolean allowPrivateMessages = true;
  private boolean allowServerLookup = true;

  public ToggleOptions() {}

  // TODO make this use the DB along with statuses and shit.

  public boolean isAllowPrivateMessages() {
    return allowPrivateMessages;
  }

  public void setAllowPrivateMessages(boolean allowPrivateMessages) {
    this.allowPrivateMessages = allowPrivateMessages;
  }

  public boolean isAllowServerLookup() {
    return allowServerLookup;
  }

  public void setAllowServerLookup(boolean allowServerLookup) {
    this.allowServerLookup = allowServerLookup;
  }
}
