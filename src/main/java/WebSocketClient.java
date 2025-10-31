import io.github.cdimascio.dotenv.Dotenv;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.concurrent.CompletionStage;

public class WebSocketClient {
    Dotenv dotenv = Dotenv.load();
    // ⚠️ Replace this with your actual bot token
    private  String BOT_TOKEN = dotenv.get("KEY_TOKEN");

    public static void main(String[] args) throws InterruptedException {
        String gatewayUrl = "wss://gateway.discord.gg/?v=10&encoding=json";

        HttpClient client = HttpClient.newHttpClient();

        client.newWebSocketBuilder()
                .buildAsync(URI.create(gatewayUrl), new DiscordWebSocketListener());
        Thread.sleep(10000);
    }

    // Simple WebSocket listener implementation
    static class DiscordWebSocketListener implements WebSocket.Listener {

        @Override
        public void onOpen(WebSocket webSocket) {
            System.out.println("✅ Connected to Discord Gateway!");
            WebSocket.Listener.super.onOpen(webSocket);
        }

        @Override
        public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
            System.out.println("📩 Received message: " + data);
            return WebSocket.Listener.super.onText(webSocket, data, last);
        }

        @Override
        public void onError(WebSocket webSocket, Throwable error) {
            System.err.println("❌ WebSocket error: " + error.getMessage());
            WebSocket.Listener.super.onError(webSocket, error);
        }

        @Override
        public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
            System.out.println("🔌 Disconnected: " + statusCode + " " + reason);
            return WebSocket.Listener.super.onClose(webSocket, statusCode, reason);

        }
    }
}

//Parse the HELLO payload → extract heartbeat_interval.
//
//Send an IDENTIFY payload → authenticate your bot.
//
//Start sending heartbeats every X milliseconds.
//
//Listen for events like "MESSAGE_CREATE". //