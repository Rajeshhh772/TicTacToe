import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.*;

public class StakeMinesGame extends JFrame implements ActionListener {

    int gridSize = 5;
    int numMines = 5;
    int wallet = 100;
    int stake = 10;
    double multiplier = 1.0;
    boolean[][] mineMap;
    JButton[][] gridButtons;
    boolean[][] revealed;
    boolean gameStarted = false;

    // Stake app colors
    Color darkBackground = new Color(17, 23, 35);
    Color panelBackground = new Color(24, 32, 48);
    Color buttonColor = new Color(32, 41, 58);
    Color accentGreen = new Color(46, 214, 163);
    Color accentRed = new Color(239, 65, 70);
    Color textColor = new Color(255, 255, 255);
    Color secondaryText = new Color(153, 164, 187);
    Color gemColor = new Color(92, 219, 148);
    Color borderColor = new Color(40, 50, 70);
    Color hoverColor = new Color(48, 60, 85);

    JLabel walletLabel = new JLabel("₹100");
    JLabel multiplierLabel = new JLabel("1.00x");
    JTextField stakeField = new JTextField("10", 5);
    JTextField minesField = new JTextField("5", 5);
    JTextField sizeField = new JTextField("5", 5);
    JButton startButton = new JButton("START GAME");
    JButton cashOutButton = new JButton("CASH OUT");

    JPanel mainPanel = new JPanel();
    JPanel leftPanel = new JPanel();
    JPanel rightPanel = new JPanel();
    JPanel gamePanel = new JPanel();
    JPanel infoPanel = new JPanel();

    public StakeMinesGame() {
        setTitle("Stake Mines Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Set app-wide styling
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mainPanel.setLayout(new BorderLayout(15, 15));
        mainPanel.setBackground(darkBackground);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Left Panel (Settings)
        leftPanel.setLayout(new GridBagLayout());
        leftPanel.setBackground(panelBackground);
        leftPanel.setBorder(BorderFactory.createLineBorder(borderColor, 1));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Info panel for wallet and multiplier
        infoPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        infoPanel.setBackground(panelBackground);
        
        JPanel walletPanel = createInfoPanel("WALLET", walletLabel);
        JPanel multiplierPanel = createInfoPanel("MULTIPLIER", multiplierLabel);
        
        infoPanel.add(walletPanel);
        infoPanel.add(multiplierPanel);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        leftPanel.add(infoPanel, gbc);
        
        // Grid size input styling
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel sizeLabel = new JLabel("GRID SIZE");
        sizeLabel.setForeground(secondaryText);
        leftPanel.add(sizeLabel, gbc);
        
        gbc.gridx = 1;
        styleTextField(sizeField);
        leftPanel.add(sizeField, gbc);

        // Mines input styling
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel minesLabel = new JLabel("MINES");
        minesLabel.setForeground(secondaryText);
        leftPanel.add(minesLabel, gbc);
        
        gbc.gridx = 1;
        styleTextField(minesField);
        leftPanel.add(minesField, gbc);

        // Stake input styling
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel stakeLabel = new JLabel("BET AMOUNT ₹");
        stakeLabel.setForeground(secondaryText);
        leftPanel.add(stakeLabel, gbc);
        
        gbc.gridx = 1;
        styleTextField(stakeField);
        leftPanel.add(stakeField, gbc);

        // Start button styling
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        styleButton(startButton, accentGreen);
        leftPanel.add(startButton, gbc);
        startButton.addActionListener(this);

        // Cash Out button styling
        gbc.gridx = 0;
        gbc.gridy = 5;
        styleButton(cashOutButton, accentRed);
        leftPanel.add(cashOutButton, gbc);
        cashOutButton.addActionListener(this);
        cashOutButton.setEnabled(false);

        // Right Panel (Game Area)
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setBackground(panelBackground);
        rightPanel.setBorder(BorderFactory.createLineBorder(borderColor, 1));
        
        gamePanel.setLayout(new GridLayout(gridSize, gridSize, 8, 8));
        gamePanel.setBackground(panelBackground);
        gamePanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        rightPanel.add(gamePanel, BorderLayout.CENTER);

        // Add panels to main panel
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);
        
        // Add main panel to frame
        add(mainPanel);

        setSize(900, 650);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }
    
    private JPanel createInfoPanel(String title, JLabel valueLabel) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(buttonColor);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(secondaryText);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 12));
        
        valueLabel.setForeground(textColor);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(valueLabel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void styleTextField(JTextField field) {
        field.setBackground(buttonColor);
        field.setForeground(textColor);
        field.setCaretColor(textColor);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(borderColor),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)));
        field.setFont(new Font("Arial", Font.PLAIN, 14));
    }
    
    private void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(200, 40));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if(button.isEnabled()) {
                    button.setBackground(color.brighter());
                }
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                if(button.isEnabled()) {
                    button.setBackground(color);
                }
            }
        });
    }

    void initializeGame() {
        try {
            gridSize = Integer.parseInt(sizeField.getText());
            numMines = Integer.parseInt(minesField.getText());
            stake = Integer.parseInt(stakeField.getText());

            if (gridSize < 2 || gridSize > 8) {
                showDialog("Grid size must be between 2 and 8!");
                return;
            }
            
            if (numMines <= 0 || numMines >= gridSize * gridSize) {
                showDialog("Invalid number of mines!");
                return;
            }
            
            if (stake > wallet || stake <= 0) {
                showDialog("Invalid stake amount!");
                return;
            }
        } catch (NumberFormatException e) {
            showDialog("Please enter valid numbers!");
            return;
        }

        wallet -= stake;
        updateWallet();

        gamePanel.removeAll();
        gamePanel.setLayout(new GridLayout(gridSize, gridSize, 8, 8));
        gridButtons = new JButton[gridSize][gridSize];
        revealed = new boolean[gridSize][gridSize];
        mineMap = new boolean[gridSize][gridSize];
        gameStarted = true;
        multiplier = 1.0;
        updateMultiplier();
        generateMines();

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                final int row = i;
                final int col = j;
                JButton btn = new JButton();
                btn.setBackground(buttonColor);
                btn.setFocusPainted(false);
                btn.setBorderPainted(false);
                btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                
                // Add hover effect to grid buttons
                btn.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        if(gameStarted && !revealed[row][col]) {
                            btn.setBackground(hoverColor);
                        }
                    }
                    
                    @Override
                    public void mouseExited(MouseEvent e) {
                        if(gameStarted && !revealed[row][col]) {
                            btn.setBackground(buttonColor);
                        }
                    }
                });
                
                btn.addActionListener(this);
                gridButtons[i][j] = btn;
                gamePanel.add(btn);
            }
        }

        cashOutButton.setEnabled(true);
        validate();
        repaint();
    }

    void generateMines() {
        Random rand = new Random();
        int placed = 0;
        while (placed < numMines) {
            int r = rand.nextInt(gridSize);
            int c = rand.nextInt(gridSize);
            if (!mineMap[r][c]) {
                mineMap[r][c] = true;
                placed++;
            }
        }
    }

    void reveal(int r, int c) {
        if (revealed[r][c]) return;

        revealed[r][c] = true;

        if (mineMap[r][c]) {
            gridButtons[r][c].setText("💣");
            gridButtons[r][c].setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
            gridButtons[r][c].setBackground(accentRed);
            showAllMines();
            showDialog("GAME OVER! You hit a mine and lost ₹" + stake);
            cashOutButton.setEnabled(false);
            gameStarted = false;
        } else {
            gridButtons[r][c].setText("💎");
            gridButtons[r][c].setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
            gridButtons[r][c].setBackground(gemColor);
            multiplier += (double) numMines / (gridSize * gridSize - numMines - getTotalRevealed() + 1);
            updateMultiplier();
        }
    }
    
    private int getTotalRevealed() {
        int count = 0;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (revealed[i][j]) count++;
            }
        }
        return count;
    }
    
    private ImageIcon createMineIcon() {
        JLabel label = new JLabel("💣");
        label.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        label.setForeground(Color.WHITE);
        return null; // Return null to not use ImageIcon
    }
    
    private ImageIcon createGemIcon() {
        JLabel label = new JLabel("💎");
        label.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        label.setForeground(Color.WHITE);
        return null; // Return null to not use ImageIcon
    }

    void showAllMines() {
        for (int i = 0; i < gridSize; i++)
            for (int j = 0; j < gridSize; j++)
                if (mineMap[i][j] && !revealed[i][j]) {
                    gridButtons[i][j].setText("💣");
                    gridButtons[i][j].setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
                    gridButtons[i][j].setBackground(accentRed);
                }
    }

    void cashOut() {
        int earnings = (int) Math.round(stake * multiplier);
        wallet += earnings;
        updateWallet();
        
        // Show cash out animation/effect
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (!revealed[i][j] && !mineMap[i][j]) {
                    gridButtons[i][j].setText("💎");
                    gridButtons[i][j].setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
                    gridButtons[i][j].setBackground(gemColor);
                }
            }
        }
        
        showDialog("WINNER! You cashed out ₹" + earnings + " at " + String.format("%.2f", multiplier) + "x");
        cashOutButton.setEnabled(false);
        gameStarted = false;
        showAllMines();
    }

    void updateWallet() {
        walletLabel.setText("₹" + wallet);
    }
    
    void updateMultiplier() {
        multiplierLabel.setText(String.format("%.2fx", multiplier));
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            initializeGame();
        } else if (e.getSource() == cashOutButton) {
            cashOut();
        } else {
            if (!gameStarted) return;
            for (int i = 0; i < gridSize; i++)
                for (int j = 0; j < gridSize; j++)
                    if (e.getSource() == gridButtons[i][j]) {
                        reveal(i, j);
                    }
        }
    }

    void showDialog(String msg) {
        JDialog dialog = new JDialog(this, "Game Message", true);
        dialog.setLayout(new BorderLayout());
        
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(darkBackground);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel msgLabel = new JLabel(msg, JLabel.CENTER);
        msgLabel.setForeground(textColor);
        msgLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        JButton ok = new JButton("OK");
        styleButton(ok, accentGreen);
        ok.setPreferredSize(new Dimension(100, 40));
        ok.addActionListener(e -> dialog.setVisible(false));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(darkBackground);
        buttonPanel.add(ok);
        
        panel.add(msgLabel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.add(panel);
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StakeMinesGame());
    }
}
