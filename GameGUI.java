import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

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
    JPanel textInputPanel;
    JTextField textInputBox;
    JPanel enterPanel;
    JButton enterButton;
    EnterButtonAction ebHandler = new EnterButtonAction();
    
    
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

        //area where text is displayed to the user
        gameplayTextArea = new JTextArea("Welcome! You are on a quest... to find adventure! (And a magic ring...) \nExplore the map to find puzzles to solve and heroic deeds to complete. \nType help to learn what different commands there are. \n \nYou are standing in a forest.");
        gameplayTextArea.setBounds((width-gtpWidth)/2, 50, gtpWidth, gtpHeight);
        gameplayTextArea.setBackground(mainScreenColour);
        gameplayTextArea.setForeground(Color.WHITE);
        gameplayTextArea.setFont(new Font("Monospace", Font.PLAIN, 20)); 
        gameplayTextArea.setLineWrap(true);
        gameplayTextPanel.add(gameplayTextArea);

        //area where the user inputs text
        textInputPanel = new JPanel(); 
        int tipHeight = 350;
        int tipWidth = 500;
        textInputPanel.setBounds((width-tipWidth)/2, 300, tipWidth, tipHeight);
        textInputPanel.setBackground(mainScreenColour);
        container.add(textInputPanel);

        textInputBox = new JTextField("<Type commands here>", 10);
        int tibWidth = 500;
        int tibHeight = 350;
        textInputBox.setBounds((width-tibWidth)/2, 0, tibWidth, tibHeight);
        textInputBox.setBackground(Color.WHITE);
        textInputBox.setForeground(Color.BLACK);
        textInputBox.setVisible(true);
        
        textInputPanel.setLayout(new BorderLayout());

        textInputBox.setFont(new Font("Monospaced", Font.PLAIN, 20));
        textInputBox.setPreferredSize(new Dimension(500, 50));
        textInputPanel.add(textInputBox, BorderLayout.NORTH);

        //button to allow the user to sumbit text
        enterPanel = new JPanel();
        int epWidth = 100;
        int epHeight = 50;

        enterPanel.setBounds((width-epWidth)/2, 400, epWidth, epHeight);
        enterPanel.setBackground(mainScreenColour);
        
        
        enterButton = new JButton("ENTER");
        enterButton.setBackground(Color.WHITE);
        enterButton.setForeground(mainScreenColour);
        enterButton.setFont(new Font("Cantarell Extra Bold", Font.PLAIN, 25));
        enterButton.addActionListener(ebHandler);

        enterButton.setVisible(true);
        enterPanel.setVisible(true);
        enterPanel.add(enterButton);
        container.add(enterPanel);



        //textInputPanel.add(textInputBox);


    }

    public void respondToTextInput()
    {
        String inputtedText = textInputBox.getText();

        String outputString = "";
        gameplayTextArea.setText(outputString);
    }

    public class TitleScreenButtonAction implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            createGameScreen();
        } 
    }

    public class EnterButtonAction implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            respondToTextInput();
        }
    }
    
}
