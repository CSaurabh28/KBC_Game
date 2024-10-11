
# KBC-Style Game

## Overview
This is a KBC (Kaun Banega Crorepati) style game developed using JavaFX. The game displays a question and a QR code on a computer screen. Players can participate by scanning the QR code with their mobile devices, entering their names, and answering the question. The application provides feedback based on the player's response.

## Features
- Display a question and a QR code on the computer screen.
- Mobile interface for players to enter their names and answers.
- Real-time feedback for correct and incorrect answers.
- Dynamic transition to the next question upon a correct answer.
- Simple and intuitive user interface.

## Technologies Used
- **JavaFX**: For building the desktop application.
- **ZXing**: For QR Code generation.
- **Java**: For game logic and backend functionality.
- **HttpServer**: To serve the HTML interface for mobile players.
- **Jackson**: For JSON processing.
  
## Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/kbc-style-game.git
   ```
2. Navigate to the project directory:
   ```bash
   cd kbc-style-game
   ```
3. Open the project in your preferred IDE (e.g., Eclipse).
4. Add the required JAR files for JavaFX and other libraries:
   - Ensure you have the JavaFX SDK and relevant dependencies.
   - Include the ZXing library for QR code generation.
   - Include Jackson library if JSON processing is utilized.

## How to Run
1. Start the application.
2. The main game screen will display a question and a QR code.
3. Scan the QR code with your mobile device to access the answer submission interface.
4. Enter your name and answer the question on your mobile UI.
5. Upon submitting the answer, the computer screen will display either a "Congratulations" message for correct answers or indicate a wrong answer with feedback.

## QR Code Functionality
- The QR code directs players to a web interface where they can input their names and answers.
- The web interface is served via a simple HTTP server running in the background.

## Screenshots
Here are some screenshots of the KBC-Style Game in action:

### Main Game Screen
<img src="https://github.com/user-attachments/assets/9259a89b-ede0-41a2-8275-66913f79049e" alt="Main Game Screen" width="600" height="400">

### Mobile Interface
<img src="https://github.com/user-attachments/assets/596ae613-16fc-4da3-a72a-cc9602dc0d50" alt="Mobile Interface" width="400" height="600">

### Feedback Screen
<img src="https://github.com/user-attachments/assets/2c6e807c-32f4-4f19-8c5d-5c7d044517ea" alt="Feedback Screen" width="600" height="400">

## Troubleshooting
- Ensure that all required libraries are correctly added to the project classpath.
- If you encounter issues with JavaFX, ensure that the VM arguments are correctly set to include JavaFX modules.
- If the server does not start, check for port conflicts or permission issues.

## Contributing
Contributions are welcome! If you have suggestions for improvements or new features, please fork the repository and submit a pull request.

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments
- Thanks to [ZXing](https://github.com/zxing/zxing) for the QR Code generation library.
- Inspiration from the KBC game format.
- Special thanks to my academic mentors and peers for their support.

## Contact
For any inquiries, please contact:
- **Saurabh Chavan**  
- **Email**: [csaurabh465@gmail.com]
