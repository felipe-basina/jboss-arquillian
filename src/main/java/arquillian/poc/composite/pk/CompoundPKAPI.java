package arquillian.poc.composite.pk;

import java.util.List;

import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/compound")
public class CompoundPKAPI {

	@EJB
	private CompoundPKDao compoundPKDao;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ClasseCompoundPK> getAll() {
		return this.compoundPKDao.getAllData();
	}
	
	@GET
	@Path(value = "/create")
	@Produces(MediaType.APPLICATION_JSON)
	public Response create() {
		try {
			this.compoundPKDao.createData();
			return Response.status(Status.NO_CONTENT).build();
		} catch (Exception e) {
			JsonObject json = Json.createObjectBuilder().add("error", e.getMessage()).build();
			return Response.status(Status.BAD_REQUEST).entity(json).build();
		}
	}
	
	@GET
	@Path(value = "/update")
	@Produces(MediaType.APPLICATION_JSON)
	public Response update() {
		try {
			this.compoundPKDao.updateData();
			return Response.status(Status.NO_CONTENT).build();
		} catch (Exception e) {
			JsonObject json = Json.createObjectBuilder().add("error", e.getMessage()).build();
			return Response.status(Status.BAD_REQUEST).entity(json).build();
		}
	}
	
	@GET
	@Path(value = "/batch/update")
	@Produces(MediaType.APPLICATION_JSON)
	public Response batchUpdate() {
		try {
			this.compoundPKDao.batchUpdateData();
			return Response.status(Status.NO_CONTENT).build();
		} catch (Exception e) {
			JsonObject json = Json.createObjectBuilder().add("error", e.getMessage()).build();
			return Response.status(Status.BAD_REQUEST).entity(json).build();
		}
	}
	
	@GET
	@Path(value = "/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete() {
		try {
			this.compoundPKDao.deleteAllData();
			return Response.status(Status.OK).build();
		} catch (Exception e) {
			JsonObject json = Json.createObjectBuilder().add("error", e.getMessage()).build();
			return Response.status(Status.BAD_REQUEST).entity(json).build();
		}
	}
	
}
