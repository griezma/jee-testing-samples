package mangri.sample.jaxrs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("check")
public class SampleApi {
    @GET @Produces(MediaType.TEXT_PLAIN)
    public String check(@QueryParam("name") String name) {
        return "it works " + name;
    }
}
