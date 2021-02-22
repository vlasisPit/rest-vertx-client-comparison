package org.test;

import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;
import org.test.clients.CountryRestMutinyClient;
import org.test.clients.MutinyCountryVertxClient;
import org.test.model.Country;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/countries")
public class CountryResource {

    private static final Logger LOG = Logger.getLogger(CountryResource.class);

    @Inject
    @RestClient
    CountryRestMutinyClient countryRestMutinyClient;

    @Inject
    MutinyCountryVertxClient mutinyCountryVertxClient;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/uni/restclient")
    public Uni<List<Country>> uniRestClient() {
        LOG.info("First log Uni with Microprofile Rest Client !!!!");

        return this.countryRestMutinyClient.getCountryByName("greece")
                .onItem().invoke(countries ->  LOG.info("Run on thread (Uni) with Microprofile Rest Client ..." + countries.toString()));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/mutiny/vertx")
    public Uni<List<Country>> mutinyVertx() {
        LOG.info("First log Mutiny with Vertx Client !!!!");

        return this.mutinyCountryVertxClient.getCountryByName("greece")
                .onItem().invoke(countries ->  LOG.info("Run on thread with Vertx client..." + countries.toString()));
    }
}