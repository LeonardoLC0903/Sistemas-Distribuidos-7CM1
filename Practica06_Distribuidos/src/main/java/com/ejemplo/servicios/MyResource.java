package com.ejemplo.servicios;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("example")
public class MyResource {

    // Endpoint GET: http://localhost:8080/api/example/hello
    @GET
    @Path("hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String getHello() {
        return "Hola, este es un servicio web en Java!";
    }
    
    // Endpoint POST: http://localhost:8080/api/example/echo
    // Envía un mensaje en texto plano y lo devuelve con un prefijo.
    @POST
    @Path("echo")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String echo(String message) {
        return "Recibido: " + message;
    }
}
