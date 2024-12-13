package me.eren.bbserver.connection;

public enum ConnectionStatus {
    LOGIN, // waiting for the player to send their name and other login info.
    PLAY,  // player is ready to play
}
