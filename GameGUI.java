import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameGUI {

    JFrame window;
    Container container;
    JPanel titlePanel;
    JPanel startPanel;
    JLabel titleText;
    JButton startButton;
    int width = 800;
    int height = 600;
    
    
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
        startPanel.add(startButton);


        






    }
    
}
