import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.WebSocket;
import java.util.Map;
import java.util.concurrent.CompletionStage;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        Dotenv dotenv = Dotenv.load();

        String BOT_TOKEN= dotenv.get("KEY_TOKEN");;
        String CHANNEL_GENERAL_ID="1428809861742989456";
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("https://discord.com/api/v10/channels/%s/messages", CHANNEL_GENERAL_ID)))
                .header("Authorization", "Bot " + BOT_TOKEN)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{\"content\":\"HALO BOS 2!\"}"))
                .build();

        //HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
       // System.out.println(response.body());



        HttpClient client1 = HttpClient.newHttpClient();
        WebSocket.Builder builder = client1.newWebSocketBuilder();

        builder.buildAsync(URI.create("wss://gateway.discord.gg/?v=10&encoding=json"),
                new WebSocket.Listener() {
                    @Override
                    public void onOpen(WebSocket webSocket) {
                        System.out.println("Connected to Discord Gateway!");
                        WebSocket.Listener.super.onOpen(webSocket);
                    }

                    @Override
                    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
                        System.out.println("Received: " + data);
                        return WebSocket.Listener.super.onText(webSocket, data, last);
                    }
                });





    }





}
