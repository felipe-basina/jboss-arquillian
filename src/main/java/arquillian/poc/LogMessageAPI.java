package arquillian.poc;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/log")
public class LogMessageAPI {

	@EJB
	private LogMessageService logMessageService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<LogMessage> getAll() {
		return this.logMessageService.recuperarLogs();
	}

}
