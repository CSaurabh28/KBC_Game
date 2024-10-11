package application;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application {
    private QRCodeGenerator qrCodeGenerator = new QRCodeGenerator();
    private ImageView imageView = qrCodeGenerator.getImageView();
    private static Label questionLabel = new Label(); // Label for the question
    private static Label resultLabel = new Label();
    private static Label optionA;
    private static Label optionB;
    private static Label optionC;
    private static Label optionD;

    private static String[] questions = {
            "What is the capital of France?", 
            "What is 2 + 2?", 
            "What is the color of the sky?",
            "What is the largest planet in our solar system?",
            "What is the Capital of India?"
    };

    private static String[][] options = {
            {"Paris", "London", "Berlin", "Rome"},
            {"3", "4", "5", "6"},
            {"Red", "Blue", "Green", "Yellow"},
            {"Earth", "Jupiter", "Mars", "Saturn"},
            {"Mumbai", "Jaipur", "Delhi", "Nashik"}
    };

    private static String[] answers = {"Paris", "4", "Blue", "Jupiter", "Avocado"};
    private static int currentQuestionIndex = 0;

    // Instance of Main to call instance methods
    private static Main instance;

    public static void main(String[] args) {
        instance = new Main(); // Initialize instance

        // Start the HTTP server in a separate thread
        new Thread(() -> {
            try {
                HTTPServer.main(new String[] {});
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        // Launch the JavaFX application
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("KBC Game");

        // Initialize labels for answer options
        optionA = createOptionLabel();
        optionB = createOptionLabel();
        optionC = createOptionLabel();
        optionD = createOptionLabel();

        // Create VBox for multiple choice labels
        VBox vboxOptions = new VBox(10, optionA, optionB, optionC, optionD);
        vboxOptions.setAlignment(Pos.CENTER_LEFT);
        vboxOptions.setPadding(new Insets(20));

        // Add the QR code and result label
        VBox vboxQR = new VBox(20, imageView, resultLabel);
        vboxQR.setAlignment(Pos.CENTER);
        vboxQR.setPadding(new Insets(20));

        // Create a main HBox to combine both the question, options, and QR code sections
        HBox mainLayout = new HBox(30, vboxOptions, vboxQR);

        // Create a VBox for the question and the main layout
        VBox mainVBox = new VBox(20, questionLabel, mainLayout);
        mainVBox.setPadding(new Insets(20));

        // Create and set the scene
        Scene scene = new Scene(mainVBox, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Generate the initial QR code for the first question
        Platform.runLater(() -> {
            generateQRCodeForCurrentQuestion();
            updateQuestionLabel(); // Ensure the question label is updated
        });
    }

    // Method to create option labels with uniform size
    private Label createOptionLabel() {
        Label label = new Label();
        label.setPrefWidth(300); // Set preferred width for uniformity
        label.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-border-color: transparent;");
        return label;
    }

    private void generateQRCodeForCurrentQuestion() {
        String ipAddress = getLocalIpAddress();
        if (ipAddress != null) {
            System.out.println("Local IP Address: " + ipAddress); // Display IP address in console
            String link = "http://" + ipAddress + ":8080/submit.html"; // Use the local IP address
            qrCodeGenerator.generateQRCode(link, 300, 300); // Generate QR code
        } else {
            updateGUI("Unable to fetch IP address.");
        }
    }

    // Method to get the local IP address
    private String getLocalIpAddress() {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            return ip.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null; // Return null if unable to fetch IP
        }
    }

    private void updateQuestionLabel() {
        Platform.runLater(() -> {
            questionLabel.setText("Question: " + questions[currentQuestionIndex]);
            questionLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
            updateOptionLabels(); // Update the options when the question is updated
        });
    }

    // Method to update the labels with the current question's options
    private void updateOptionLabels() {
        Platform.runLater(() -> {
            optionA.setText("A: " + options[currentQuestionIndex][0]);
            optionB.setText("B: " + options[currentQuestionIndex][1]);
            optionC.setText("C: " + options[currentQuestionIndex][2]);
            optionD.setText("D: " + options[currentQuestionIndex][3]);
        });
    }

    // Method to check the player's answer
    private void checkAnswer(String selectedAnswer) {
        if (selectedAnswer.equalsIgnoreCase(getCurrentAnswer())) {
            showCongratulationsPopup("Player"); // Pass the player's name or a static name for now
            updateGUI("Congratulations! Your answer is correct!");
            moveToNextQuestion();
        } else {
            updateGUI("Sorry, your answer is incorrect.");
        }
    }

    // Method to display a popup alert with a congratulatory message
    public static void showCongratulationsPopup(String playerName) {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Correct Answer!");
            alert.setHeaderText(null);
            alert.setContentText("Congratulations " + playerName + ", your answer is correct!");
            alert.showAndWait();
        });
    }

    // Method to update the GUI with a congratulatory message or next question
    public static void updateGUI(String message) {
        Platform.runLater(() -> resultLabel.setText(message));
    }

    // Method to move to the next question
    public static void moveToNextQuestion() {
        currentQuestionIndex++;
        if (currentQuestionIndex < questions.length) {
            instance.generateQRCodeForCurrentQuestion();
            instance.updateQuestionLabel(); // Update question and options
        } else {
            showGameOverPopup(); // Show Game Over popup
        }
    }

    // Method to display a Game Over popup
    private static void showGameOverPopup() {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Game Over!");
            alert.setHeaderText(null);
            alert.setContentText("Game over! Thanks for playing.");
            alert.showAndWait();
        });
    }

    public static String getCurrentAnswer() {
        return answers[currentQuestionIndex];
    }
}
