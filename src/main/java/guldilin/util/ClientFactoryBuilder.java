package guldilin.util;


import lombok.SneakyThrows;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

public class ClientFactoryBuilder {
    private static Client client;

    @SneakyThrows
    public static Client getClient() {
        if (client != null) return client;
        client = ClientBuilder.newBuilder().build();
        return client;
    }

    public static String getApiUrl() {
        return System.getenv("SERVICE_URL");
    }
}
