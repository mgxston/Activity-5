// Tic Tac Toe is fun!

// Myrtle G. Gaston
// ITCC 11.1 B Event-Driven Programming

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class App extends JFrame implements ActionListener {
    private JButton[][] buttons;
    private JLabel statusLabel;
    private char currentPlayer;
    private boolean gameOver;

    public App() {
        setTitle("Tic Tac Toe");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        getContentPane().setBackground(Color.BLACK);

        JPanel mainPanel = new JPanel(new GridLayout(4, 1));
        mainPanel.setBackground(Color.BLACK);

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel("Tic Tac Toe is Fun!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 50));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        titlePanel.setBackground(Color.BLACK);
        mainPanel.add(titlePanel);

        JPanel newGamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton newGameButton = new JButton("New Game");
        newGameButton.setBackground(Color.BLUE);
        newGameButton.setForeground(Color.WHITE);
        newGameButton.setFont(new Font("Arial", Font.BOLD, 35));
        newGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showTicTacToeGame();
            }
        });
        newGamePanel.add(newGameButton);
        newGamePanel.setBackground(Color.BLACK);
        mainPanel.add(newGamePanel);

        JPanel exitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton exitButton = new JButton("Exit");
        exitButton.setBackground(Color.RED);
        exitButton.setForeground(Color.WHITE);
        exitButton.setFont(new Font("Arial", Font.BOLD, 35));
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        exitPanel.add(exitButton);
        exitPanel.setBackground(Color.BLACK);
        mainPanel.add(exitPanel);

        add(mainPanel);

        setVisible(true);
    }

    private void showTicTacToeGame() {
        getContentPane().removeAll();
        getContentPane().add(createTicTacToePanel());
        revalidate();
    }

    private JPanel createTicTacToePanel() {
        JPanel ticTacToePanel = new JPanel(new BorderLayout());
        ticTacToePanel.setBackground(Color.BLACK);

        JPanel playerTurnPanel = new JPanel(new BorderLayout());
        statusLabel = new JLabel("Player X's Turn", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        statusLabel.setForeground(Color.WHITE);
        playerTurnPanel.add(statusLabel, BorderLayout.NORTH);
        playerTurnPanel.setBackground(Color.BLACK);
        ticTacToePanel.add(playerTurnPanel, BorderLayout.NORTH);

        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(3, 3, 10, 10));
        gamePanel.setBackground(Color.BLACK);

        buttons = new JButton[3][3];
        currentPlayer = 'X';
        gameOver = false;

        Font buttonFont = new Font("Comic Sans MS", Font.BOLD, 30);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(buttonFont);
                buttons[i][j].setPreferredSize(new Dimension(80, 80));
                buttons[i][j].setForeground(Color.BLUE);
                buttons[i][j].setBackground(Color.WHITE);
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].addActionListener(this);
                buttons[i][j].setBackground(Color.BLACK);
                gamePanel.add(buttons[i][j]);
            }
        }

        ticTacToePanel.add(gamePanel, BorderLayout.CENTER);

        return ticTacToePanel;
    }

    private void showWinnerStatus(String message) {
        JFrame winnerFrame = new JFrame("Results");
        winnerFrame.setSize(300, 150);
        winnerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        winnerFrame.setLocationRelativeTo(this);

        JPanel winnerPanel = new JPanel(new GridLayout(2, 1));
        winnerPanel.setBackground(Color.BLACK);

        JLabel winnerLabel = new JLabel(message, SwingConstants.CENTER);
        winnerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        winnerLabel.setForeground(Color.WHITE);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.BLACK);

        JButton playAgainButton = new JButton("Play Again");
        playAgainButton.setBackground(Color.BLUE);
        playAgainButton.setForeground(Color.WHITE);
        playAgainButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                winnerFrame.dispose();
                showTicTacToeGame();
            }
        });

        JButton exitButton = new JButton("Exit");
        exitButton.setBackground(Color.RED);
        exitButton.setForeground(Color.WHITE);
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        buttonPanel.add(playAgainButton);
        buttonPanel.add(exitButton);

        winnerPanel.add(winnerLabel);
        winnerPanel.add(buttonPanel);

        winnerFrame.add(winnerPanel);
        winnerFrame.getContentPane().setBackground(Color.BLACK);
        winnerFrame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            JButton clickedButton = (JButton) e.getSource();
            if (clickedButton.getText().equals("")) {
                clickedButton.setText(Character.toString(currentPlayer));
                if (checkWinner()) {
                    showWinnerStatus("Player " + currentPlayer + " wins!");
                    gameOver = true;
                    statusLabel.setText("");
                } else if (checkDraw()) {
                    showWinnerStatus("It's a draw!");
                    gameOver = true;
                } else {
                    currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                    statusLabel.setText("Player " + currentPlayer + "'s Turn");
                }
            }
        }
    }

    private boolean checkWinner() {
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(buttons[i][1].getText()) &&
                buttons[i][0].getText().equals(buttons[i][2].getText()) &&
                !buttons[i][0].getText().equals("")) {
                return true;
            }
        }
        for (int j = 0; j < 3; j++) {
            if (buttons[0][j].getText().equals(buttons[1][j].getText()) &&
                buttons[0][j].getText().equals(buttons[2][j].getText()) &&
                !buttons[0][j].getText().equals("")) {
                return true;
            }
        }
        if (buttons[0][0].getText().equals(buttons[1][1].getText()) &&
            buttons[0][0].getText().equals(buttons[2][2].getText()) &&
            !buttons[0][0].getText().equals("")) {
            return true;
        }
        if (buttons[0][2].getText().equals(buttons[1][1].getText()) &&
            buttons[0][2].getText().equals(buttons[2][0].getText()) &&
            !buttons[0][2].getText().equals("")) {
            return true;
        }
        return false;
    }

    private boolean checkDraw() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        new App();
    }
}
