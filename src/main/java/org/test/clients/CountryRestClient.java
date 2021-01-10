package org.test.clients;

import io.reactivex.Single;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.test.model.Country;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RegisterRestClient(configKey = "country-client")
@Path("/v2")
public interface CountryRestClient {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/all")
    Single<List<Country>> getAllCountries();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/name/{name}")
    Single<List<Country>> getCountryByName(@PathParam("name") String name);

}
