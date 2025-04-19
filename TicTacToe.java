import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class TicTacToe extends JFrame implements ActionListener {
private JButton[] buttons = new JButton[9];
private boolean playerXTurn = true;
private JLabel statusLabel;
private JLabel scoreLabel; // Reference for the score label
private int xWins = 0, oWins = 0;
public TicTacToe() {
setTitle("Tic Tac Toe");
setSize(400, 500);
setLayout(new BorderLayout());
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
setLocationRelativeTo(null); // Center window
// Status label for player turn and scores
statusLabel = new JLabel("Player X's Turn", JLabel.CENTER);
statusLabel.setFont(new Font("Arial", Font.BOLD, 20));
add(statusLabel, BorderLayout.NORTH);
// Panel for Tic-Tac-Toe grid
JPanel gridPanel = new JPanel();
gridPanel.setLayout(new GridLayout(3, 3));
add(gridPanel, BorderLayout.CENTER);
initButtons(gridPanel);
// Bottom panel for restart button and scores
JPanel bottomPanel = new JPanel();
bottomPanel.setLayout(new BorderLayout());
JButton restartButton = new JButton("Restart Game");
restartButton.setFont(new Font("Arial", Font.BOLD, 14));
restartButton.addActionListener(e -> resetBoard());
bottomPanel.add(restartButton, BorderLayout.CENTER);
scoreLabel = new JLabel("X: " + xWins + " | O: " + oWins, JLabel.CENTER);
scoreLabel.setFont(new Font("Arial", Font.PLAIN, 16));
bottomPanel.add(scoreLabel, BorderLayout.SOUTH);
add(bottomPanel, BorderLayout.SOUTH);
setVisible(true);
}
private void initButtons(JPanel gridPanel) {
for (int i = 0; i < 9; i++) {
buttons[i] = new JButton("");
buttons[i].setFont(new Font("Arial", Font.BOLD, 60));
buttons[i].setFocusPainted(false);
buttons[i].setBackground(Color.WHITE);
buttons[i].addActionListener(this);
gridPanel.add(buttons[i]);
}
}
public void actionPerformed(ActionEvent e) {
JButton clicked = (JButton) e.getSource();
if (!clicked.getText().equals("")) {
return; // Already clicked
}
clicked.setText(playerXTurn ? "X" : "O");
if (checkWin()) {
JOptionPane.showMessageDialog(this, "Player " + (playerXTurn ? "X" : "O") + " wins!");
if (playerXTurn) {
xWins++;
} else {
oWins++;
}
updateStatus();
updateScore();
resetBoard();
} else if (isDraw()) {
JOptionPane.showMessageDialog(this, "It's a draw!");
resetBoard();
} else {
playerXTurn = !playerXTurn;
updateStatus();
}
}
private void updateStatus() {
statusLabel.setText("Player " + (playerXTurn ? "X" : "O") + "'s Turn");
}
private void updateScore() {
scoreLabel.setText("X: " + xWins + " | O: " + oWins); // Update score display
}
private boolean checkWin() {
String[][] combos = new String[3][3];
for (int i = 0; i < 9; i++) {
combos[i / 3][i % 3] = buttons[i].getText();
}
// Check rows, columns, and diagonals
for (int i = 0; i < 3; i++) {
if (!combos[i][0].equals("") &&
combos[i][0].equals(combos[i][1]) &&
combos[i][1].equals(combos[i][2])) {
return true;
}
if (!combos[0][i].equals("") &&
combos[0][i].equals(combos[1][i]) &&
combos[1][i].equals(combos[2][i])) {
return true;
}
}
if (!combos[0][0].equals("") &&
combos[0][0].equals(combos[1][1]) &&
combos[1][1].equals(combos[2][2])) {
return true;
}
if (!combos[0][2].equals("") &&
combos[0][2].equals(combos[1][1]) &&
combos[1][1].equals(combos[2][0])) {
return true;
}
return false;
}
private boolean isDraw() {
for (JButton btn : buttons) {
if (btn.getText().equals("")) {
return false;
}
}
return true;
}
private void resetBoard() {
for (JButton btn : buttons) {
btn.setText("");
}
playerXTurn = true;
updateStatus();
}
public static void main(String[] args) {
new TicTacToe();
}
}