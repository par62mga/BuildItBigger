/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.pkrobertson.jokeserver;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import javax.inject.Named;

import com.pkrobertson.javajokes.FetchJavaJoke;

/** An endpoint class we are exposing */
@Api(
  name = "myApi",
  version = "v1",
  namespace = @ApiNamespace(
    ownerDomain = "jokeserver.pkrobertson.com",
    ownerName = "jokeserver.pkrobertson.com",
    packagePath=""
  )
)
public class MyEndpoint {

    /** A simple endpoint method that takes a request and send back random joke text */
    @ApiMethod(name = "fetchJoke")
    public MyBean fetchJoke(@Named("requestName") String requestName) {
        MyBean response = new MyBean();
        response.setrequestName(requestName);
        String randomJoke = FetchJavaJoke.fetchRandomJoke();
        response.setResponseData(randomJoke);

        return response;
    }

}
