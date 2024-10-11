package application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HTTPServer {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // Serve the HTML file (submit.html)
        server.createContext("/submit.html", new HtmlHandler());

        // Handle form submissions
        server.createContext("/submit", new AnswerHandler());

        server.setExecutor(null); // Default executor
        server.start();
        System.out.println("Server is listening on port 8080...");
    }

    static class HtmlHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            byte[] response = Files.readAllBytes(Paths.get("C:/Program Files/Apache Software Foundation/Tomcat 9.0/KBC_Game/src/application/submit.html"));
            exchange.sendResponseHeaders(200, response.length);
            OutputStream os = exchange.getResponseBody();
            os.write(response);
            os.close();
        }
    }

    static class AnswerHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                // Parse the incoming request
                String requestBody = new String(exchange.getRequestBody().readAllBytes());
                PlayerSubmission submission = objectMapper.readValue(requestBody, PlayerSubmission.class);

                // Debugging: Log the received answer and name
                System.out.println("Received submission from: " + submission.getName() + " with answer: " + submission.getAnswer());

                // Check if the answer is correct
                String response;
                if (submission.getAnswer().equalsIgnoreCase(Main.getCurrentAnswer())) {
                    response = "Congratulations " + submission.getName() + ", your answer is correct!";
                    
                    // Debugging: Log the correct answer response
                    System.out.println("Answer correct. Sending update to GUI: " + response);
                    
                    // Show popup with congratulations message
                    Main.showCongratulationsPopup(submission.getName());
                    
                    // Update the GUI with the congratulatory message in the label
                    Main.updateGUI("Congratulations " + submission.getName() + "!");
                    
                    // Move to the next question
                    Main.moveToNextQuestion();
                } else {
                    response = "Sorry " + submission.getName() + ", your answer is incorrect.";
                }

                // Send response back to the client
                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }
    }

    static class PlayerSubmission {
        private String name;
        private String answer;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getAnswer() { return answer; }
        public void setAnswer(String answer) { this.answer = answer; }
    }
}
