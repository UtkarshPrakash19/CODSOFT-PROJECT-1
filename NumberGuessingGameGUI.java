import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class NumberGuessingGameGUI extends JFrame {
    private JTextField guessField;
    private JButton guessButton;
    private JLabel messageLabel;
    private JLabel attemptsLabel;
    private int targetNumber;
    private int maxAttempts = 10;
    private int attempts = 0;

    public NumberGuessingGameGUI() {
        setTitle("Number Guessing Game");
        setIconImage(new ImageIcon("icon.png").getImage()); // Add a custom title bar icon (icon.png should be in your project folder)
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 240, 240)); // Set a custom background color

        targetNumber = generateRandomNumber(1, 100);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        messageLabel = new JLabel("<html><div style='text-align: center;'>I'm thinking of a number between 1 and 100.<br>Try to guess it!</div></html>");
        messageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Add a border around the message label
        attemptsLabel = new JLabel("Attempts left: " + (maxAttempts - attempts));
        topPanel.add(messageLabel);
        topPanel.add(attemptsLabel);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        guessField = new JTextField(10);
        guessButton = new JButton("Guess");
        guessButton.addActionListener(new GuessButtonListener());
        centerPanel.add(guessField);
        centerPanel.add(guessButton);
        add(centerPanel, BorderLayout.CENTER);

        pack();
    }

    private int generateRandomNumber(int minRange, int maxRange) {
        Random random = new Random();
        return random.nextInt(maxRange - minRange + 1) + minRange;
    }

    private class GuessButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (attempts >= maxAttempts) {
                messageLabel.setText("<html><div style='text-align: center;'>Sorry, you've used all your attempts.<br>The correct number was " + targetNumber + ".</div></html>");
                guessField.setEnabled(false);
                guessButton.setEnabled(false);
                return;
            }

            try {
                int userGuess = Integer.parseInt(guessField.getText());
                attempts++;

                if (userGuess == targetNumber) {
                    messageLabel.setText("<html><div style='text-align: center;'>Congratulations! You guessed the number " + targetNumber + " in " + attempts + " attempts.</div></html>");
                    guessField.setEnabled(false);
                    guessButton.setEnabled(false);
                } else if (userGuess < targetNumber) {
                    messageLabel.setText("<html><div style='text-align: center;'>Too low. Try again.</div></html>");
                } else {
                    messageLabel.setText("<html><div style='text-align: center;'>Too high. Try again.</div></html>");
                }

                attemptsLabel.setText("Attempts left: " + (maxAttempts - attempts));
            } catch (NumberFormatException ex) {
                messageLabel.setText("<html><div style='text-align: center;'>Please enter a valid number.</div></html>");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            NumberGuessingGameGUI game = new NumberGuessingGameGUI();
            game.setVisible(true);
        });
    }
}
