package com.ejemplo.servicios;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import java.net.URI;

public class Main {
    // Dirección base del servicio web
    public static final String BASE_URI = "http://localhost:8080/api/";

    public static HttpServer startServer() {
        // Escanea el paquete para encontrar recursos JAX-RS (ajusta el paquete si es necesario)
        final ResourceConfig rc = new ResourceConfig().packages("com.ejemplo.servicios");
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    public static void main(String[] args) {
        final HttpServer server = startServer();
        System.out.println("Servidor iniciado en " + BASE_URI);
        System.out.println("Presiona Enter para detener el servidor...");
        try {
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
        server.shutdownNow();
    }
}
