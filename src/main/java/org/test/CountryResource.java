package org.test;

import io.reactivex.Single;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;
import org.test.clients.CountryRestClient;
import org.test.clients.CountryRestMutinyClient;
import org.test.clients.CountryVertxClient;
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
    CountryRestClient countryRestClient;

    @Inject
    @RestClient
    CountryRestMutinyClient countryRestMutinyClient;

    @Inject
    CountryVertxClient countryVertxClient;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/uni/restclient")
    public Uni<List<Country>> uniRestClient() {
        LOG.info("First log Uni with Microprofile Rest Client !!!!");

        return this.countryRestMutinyClient.getCountryByName("Greece")
                .onItem().invoke(() ->  LOG.info("Run on thread (Uni) with Microprofile Rest Client ..."))
                .onItem().invoke(() ->  LOG.info("-----------------------"));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/rxjava/restclient")
    public Single<List<Country>> rxjavaRestClient() {
        LOG.info("First log RxJava with Microprofile Rest Client !!!!");

        return this.countryRestClient.getCountryByName("Greece")
                .doOnEvent((countries, throwable) ->  LOG.info("Run on thread (RxJava) with Microprofile Rest Client ..."))
                .doOnEvent((countries, throwable) ->  LOG.info("-----------------------"));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/rxjava/vertx")
    public Single<List<Country>> rxjavaVertx() {
        LOG.info("First log RxJava with Vertx Client !!!!");

        return this.countryVertxClient.getCountryByName("Greece")
                .doOnEvent((countries, throwable) ->  LOG.info("Run on thread with Vertx client..."))
                .doOnEvent((countries, throwable) ->  LOG.info("-----------------------"));
    }
}