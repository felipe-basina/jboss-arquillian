package arquillian.poc;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/clock")
public class WebSocketClock {

	static ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor();

	private static int TOTAL = 10;
	
	private static int COUNT = 0;
	
	private static Set<Session> allSessions;

	DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

	@OnOpen
	public void showTime(Session session) {
		allSessions = session.getOpenSessions();

		// start the scheduler on the very first connection
		// to call sendTimeToAll every second
		if (allSessions.size() == 1) {
			timer.scheduleAtFixedRate(() -> sendTimeToAll(session), 0, 1, TimeUnit.SECONDS);
		}
	}

	private void sendTimeToAll(Session session) {
		String msg = "";
		
		COUNT += 1;
		
		if (COUNT % TOTAL == 0) {
			msg = "Essa é uma mensagem de push notification - Felipe";
			COUNT = 0;
		}
		
		allSessions = session.getOpenSessions();
		for (Session sess : allSessions) {
			try {
				sess.getBasicRemote().sendText("Local time: " + LocalTime.now().format(timeFormatter) + " : " + msg);
			} catch (IOException ioe) {
				System.out.println(ioe.getMessage());
			}
		}
	}
}
