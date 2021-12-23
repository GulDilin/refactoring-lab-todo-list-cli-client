package guldilin;

import guldilin.error.ErrorMessage;

import java.net.MalformedURLException;
import java.net.URL;

public class Main {

    public static void main(String[] args) {
        final MainHandler client;
        if (args.length < 1) {
            System.err.println(ErrorMessage.URL_PARAMETER_REQUIRED);
        }
        URL baseUrl;
        try {
            baseUrl = new URL(args[0]);
        } catch (MalformedURLException e) {
            System.err.println(ErrorMessage.URL_INCORRECT);
            return;
        }


        client = new MainHandler(baseUrl);
        boolean isServerAvailable = false;
        try {
            isServerAvailable = client.isServerAvailable();
        } catch (Exception ex) { /*ignore*/ }
        if (!isServerAvailable) {
            System.err.println(ErrorMessage.SERVER_UNAVAILABLE);
            return;
        }

        client.start();
    }
}
