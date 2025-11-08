package game.service;

import game.location.LocationStart;
import game.model.User;
import game.model.enums.GameRole;

public class Session {
    private User user;
    private GameRole gameRole;
    private LocationStart locationStart;

    public User getUser() {
        return user;
    }

    public GameRole getGameRole() {
        return gameRole;
    }

    public LocationStart getLocationStart() {
        return locationStart;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setGameRole(GameRole gameRole) {
        this.gameRole = gameRole;
    }

    public void setLocationStart(LocationStart locationStart) {
        this.locationStart = locationStart;
    }
}