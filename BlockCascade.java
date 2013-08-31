
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BlockCascade extends JFrame{
 JMenuBar menubar;
 JMenu file, modes, multiplayer, options;
 JMenuItem newgame, exit, campaign, race, survival, freeplay;
 JCheckBoxMenuItem pushing;
 JLabel statusbar;
 MenuHandler menuHandler;
 Board board;

 public BlockCascade(){
  menubar = new JMenuBar();
  menuHandler = new MenuHandler();
  file = new JMenu("File");
  newgame = new JMenuItem("New Game");
  newgame.addActionListener(menuHandler);
  exit = new JMenuItem("Exit");
  exit.addActionListener(menuHandler);
  file.add(newgame);
  file.add(exit);
  menubar.add(file);
  modes = new JMenu("Modes");
  multiplayer = new JMenu("Multiplayer");
  campaign = new JMenuItem("Classic");
  campaign.addActionListener(menuHandler);
  freeplay = new JMenuItem("FreePlay");
  freeplay.addActionListener(menuHandler);
  race = new JMenuItem("Race");
  race.addActionListener(menuHandler);
  survival = new JMenuItem("Survival");
  survival.addActionListener(menuHandler);
  modes.add(campaign);
  modes.add(freeplay);
  multiplayer.add(race);
  multiplayer.add(survival);
  modes.add(multiplayer);
  menubar.add(modes);
  options = new JMenu("Options");
  pushing = new JCheckBoxMenuItem("Pushing",false);
  options.add(pushing);
  menubar.add(options);
  statusbar = new JLabel("Hi, this is a statusbar.", SwingConstants.CENTER);
  add(statusbar, BorderLayout.SOUTH);
  board = new Board(this);
  add(board);
  setJMenuBar(menubar);
  //board.start();
  //setSize(400,400);
  setSize(board.boardWidth*20,board.boardHeight*20);
  setTitle("BlockCascade");
  setDefaultCloseOperation(EXIT_ON_CLOSE);
 }
 public JLabel getStatusBar(){
  return statusbar;
 }
 public static void main(String[] args){
  BlockCascade game = new BlockCascade();
  game.setLocationRelativeTo(null);
  game.setVisible(true);
  game.board.start();
 }
 private class MenuHandler implements ActionListener{
  public void actionPerformed(ActionEvent e){
   Object source = e.getSource();
   if (source==newgame){
    board.restart(board.mode);
   } else if (source==exit){
    System.exit(0);
   } else if (source==campaign){
    board.restart(1);
   } else if (source==freeplay){
    board.restart(0);
   } else if (source==race){
    board.restart(2);
   } else if (source==survival){
    board.restart(3);
   }
  }
 }
}