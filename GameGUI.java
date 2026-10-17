import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class GameGUI {

    JFrame window;
    Container container;
    JPanel titlePanel;
    JPanel startPanel;
    JLabel titleText;
    JButton startButton;
    int width = 800;
    int height = 600;

    JPanel gameplayTextPanel;
    JTextArea gameplayTextArea;
    TitleScreenButtonAction tsbHandler = new TitleScreenButtonAction();
    
    
    public static void main(String[] args)
    {
        new GameGUI(); 
    }

    public GameGUI(){

        Color titleScreenColour = new Color(97, 150, 96);
        
        window = new JFrame();
        window.setSize(width, height);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(titleScreenColour);
        window.setLayout(null);
        window.setVisible(true);
        window.setTitle("Text Quest");

        container = window.getContentPane();

        titlePanel = new JPanel(); 
        int titleW = width;
        int titleH = 200;
        titlePanel.setBounds((width-titleW)/2, 50, titleW, titleH);
        titlePanel.setBackground(titleScreenColour);

        titleText = new JLabel("TEXT QUEST"); 
        titleText.setForeground(Color.WHITE); //colour of the text
        titleText.setFont(new Font("Cantarell Extra Bold", Font.BOLD, 100));
        titlePanel.add(titleText);

        container.add(titlePanel);

        startPanel = new JPanel();
        int startW = 200;
        int startH = 100;
        startPanel.setBounds((width-startW)/2, 300, startW, startH);
        startPanel.setBackground(titleScreenColour);
        container.add(startPanel);
        startButton = new JButton("START");
        startButton.setBackground(titleScreenColour);
        startButton.setForeground(Color.WHITE);
        startButton.setFont(new Font("Cantarell Extra Bold", Font.PLAIN, 60));
        startButton.addActionListener(tsbHandler);
        startPanel.add(startButton);

    }

    public void createGameScreen()
    {
        //erasing title screen
        titlePanel.setVisible(false);
        startPanel.setVisible(false);

        //creating game screen
        gameplayTextPanel = new JPanel(); 
        int gtpHeight = 200;
        int gtpWidth = 700;
        gameplayTextPanel.setBounds((width-gtpWidth)/2, 50, gtpWidth, gtpHeight);
        Color mainScreenColour = new Color(139, 193, 139); 
        window.getContentPane().setBackground(mainScreenColour);
        gameplayTextPanel.setBackground(mainScreenColour);
        container.add(gameplayTextPanel);

        gameplayTextArea = new JTextArea("Welcome! You are on a quest... to find adventure! (And a magic ring...) Explore the map to find puzzles to solve and heroic deeds to complete. Type help to learn what different commands there are. You are standing in a forest.");
        gameplayTextArea.setBounds((width-gtpWidth)/2, 50, gtpWidth, gtpHeight);
        gameplayTextArea.setBackground(mainScreenColour);
        gameplayTextArea.setForeground(Color.WHITE);
        gameplayTextArea.setFont(new Font("Monospace", Font.PLAIN, 20)); 
        gameplayTextArea.setLineWrap(true);
        gameplayTextPanel.add(gameplayTextArea);

    }

    public class TitleScreenButtonAction implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            createGameScreen();
        } 
    }
    
}
