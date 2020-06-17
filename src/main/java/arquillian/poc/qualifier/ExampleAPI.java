package arquillian.poc.qualifier;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/example")
public class ExampleAPI {

    @EJB
    private ExampleServiceFactory exampleServiceFactory;

    @GET
    @Path("/{exampleType}")
    public Response printMessage(@PathParam("exampleType") String exampleType) {
        try {
            this.exampleServiceFactory.getServiceImplementation(exampleType).printOrderNumber();
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Not found type " + exampleType).build();
        }
    }

}
