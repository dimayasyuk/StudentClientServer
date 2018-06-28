package sample.Server;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmitriy on 10.6.18.
 */
public class SessionsManager {
    private List<Session> sessions = new ArrayList<>();

    public List<Session> getSessions() {
        return sessions;
    }

    public synchronized void addSession(Session session){
        sessions.add(session);
    }
    public synchronized void removeSession(Session session){
        sessions.remove(session);
    }
}
