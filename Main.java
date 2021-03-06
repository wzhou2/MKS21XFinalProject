import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javafx.stage.*;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

public class Main extends JFrame implements ActionListener{
    
    private Container pane;
    private JPanel numberBar, playerBar, topBar;
    private JMenuBar menuBar;
    private JMenu Options, Help;
    private JMenuItem newGame, saver, loader, helper;
    private String file = "Default.txt";
    private Board board;
    public static int barWidth = 32;
    public static int timerWidth = 256;
    public static Font fontStyle = new Font("sans-serif", Font.BOLD, barWidth);
    private JTextField fileSave, fileLoad;
    
    public Main() {
        

        // Window Dimension
        this.setTitle("Chess");
        this.setSize(Board.width+barWidth+timerWidth,Board.height+barWidth);
        this.setLocation(0,0);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        
        pane = this.getContentPane();
        
        // JPanel initialize
        board = new Board();
        topBar = makeTopBar();
        numberBar = makeNumberBar();
        playerBar = new PlayerBar();
        menuBar = makeMenuBar();
        
        // Adding sections
        pane.add(board, BorderLayout.CENTER);
        pane.add(topBar, BorderLayout.PAGE_START);
        pane.add(numberBar, BorderLayout.LINE_START);
        pane.add(playerBar, BorderLayout.LINE_END);
        this.setJMenuBar(menuBar);
        
        pack(); 
    }
    
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("New Game")){
            newGame();
        }
        if (e.getActionCommand().equals("Save")){
            file = FileChooser.main();
            if (!file.equals("")) {
                SaveNLoad.save(file, board.compressBoard());
            }
        }
        if (e.getActionCommand().equals("Load")){
            file = FileChooser.main();
            if (!file.equals("")) {
                newGame(file);
            }
        }
        if (e.getActionCommand().equals("Help")){
            JOptionPane.showMessageDialog(null,
            "The basic goal of Chess is to capture the enemy's king. Each piece has a different set of possible \n moves that allow players to play strategically. It's no wonder Chess has become a popular worldwide \n strategy game. For a more in-depth guide, please visit www.chesscorner.com/tutorial/learn.htm .");
        }
    }
    public void newGame(String fileName) {
        pane.remove(board);
        board = new Board(fileName);
        pane.add(board, BorderLayout.CENTER);
        
        pane.remove(playerBar);
        playerBar = new PlayerBar();
        pane.add(playerBar, BorderLayout.LINE_END);
    }
    
    public void newGame() {
        newGame("Default.txt");
    }
    
    private JPanel makeLetterBar() {
        JPanel letterBar = new JPanel(new GridLayout(1,8));
        for (int x = 0; x < 8; x++) {
            JLabel token;
            
            char letter = (char) (65+x);
            token = new JLabel(""+letter, JLabel.CENTER);
            token.setPreferredSize(new Dimension(Board.width/8,barWidth));
            token.setFont(fontStyle);
            
            token.setBorder(Board.standard);
            letterBar.add(token);
        }
        return letterBar;
    }

    private JPanel makeTopBar() {
        JPanel top = new JPanel(new BorderLayout());
        
        // letterBar
        JPanel letterBar = makeLetterBar();
        
        // Left Side Box
        JLabel leftBox = new JLabel();
        leftBox.setPreferredSize(new Dimension(barWidth,barWidth));
        
        // Right Side Box
        JLabel rightBox = new JLabel();
        rightBox.setPreferredSize(new Dimension(timerWidth,barWidth));
        
        
        top.add(leftBox, BorderLayout.LINE_START);
        top.add(rightBox, BorderLayout.LINE_END);
        top.add(letterBar, BorderLayout.CENTER);
        
        return top;
    }
    
    private JPanel makeNumberBar() {
        numberBar = new JPanel(new GridLayout(8,1));
        numberBar.setPreferredSize(new Dimension(barWidth,Board.width/8));
        
        for (int x = 8; x > 0; x--) {
            JLabel token;
            token = new JLabel(""+x, JLabel.CENTER);
            token.setPreferredSize(new Dimension(barWidth,Board.width/8));
            token.setFont(fontStyle);
            
            token.setBorder(Board.standard);
            numberBar.add(token);
        }
        
        return numberBar;
    }
    
    private JMenuBar makeMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        JMenu Options = new JMenu("Options");
        JMenu Help = new JMenu("Help");
    
        //Initializing Options JMenuItems
        newGame = new JMenuItem("New Game");
        saver = new JMenuItem("Save");
        loader = new JMenuItem("Load");
        
        newGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK));
        saver.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK));
        loader.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Event.CTRL_MASK));
    
        Options.add(newGame);
        Options.add(saver);
        Options.add(loader);
    
    
        //Initializing Help JMenuItems
        helper = new JMenuItem("Help");
        
        helper.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, Event.CTRL_MASK));
        
        Help.add(helper);
    
        //Adding MenuListeners
        newGame.addActionListener(this);
        saver.addActionListener(this);
        loader.addActionListener(this);
    
        helper.addActionListener(this);

        //Adding Menus to MenuBar
        menuBar.add(Options);
        menuBar.add(Help);
        
        //Resizing and Recoloring
        Font f = new Font("sans-serif", Font.BOLD, 20);
        
        menuBar.setFont(f);
        Options.setFont(f);
        Help.setFont(f);
        
        newGame.setFont(f);
        saver.setFont(f);
        loader.setFont(f);
        helper.setFont(f);
        
        return menuBar;
    }
    
    
    public static void main(String[] args) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        int screenValue = Math.min(screenHeight,screenWidth);
        Board.setDimension(screenValue-300, screenValue-300);
        
        try {
            if (args.length == 5) {
                PlayerBar.setPlayerNames(args[0],args[1]);
                Board.setDimension(Integer.parseInt(args[2]), Integer.parseInt(args[3]));
                PlayerBar.changeTimerStartTime(Integer.parseInt(args[4]));
            }
            if (args.length == 3) {
                Board.setDimension(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
                PlayerBar.changeTimerStartTime(Integer.parseInt(args[2]));
            }
            if (args.length == 2) {
                PlayerBar.setPlayerNames(args[0],args[1]);
            }
            if (args.length == 1) {
                PlayerBar.changeTimerStartTime(Integer.parseInt(args[0]));
            }        
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter only integer values");
            System.exit(1);
        }
        Main test = new Main();
        test.setVisible(true);
    }
}