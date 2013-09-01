
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BlockCascade extends JFrame{
 JMenuBar menubar;
 JMenu file, modes, multiplayer, options, extra;
 JMenuItem newgame, exit, campaign, race, msurvival, freeplay, chase, chooselevel, numai;
 JCheckBoxMenuItem pushing;
 JLabel statusbar;
 MenuHandler menuHandler;
 Board board;
 int numaiint = 3;

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
  chase = new JMenuItem("Chase");
  chase.addActionListener(menuHandler);
  freeplay = new JMenuItem("FreePlay");
  freeplay.addActionListener(menuHandler);
  race = new JMenuItem("Race");
  race.addActionListener(menuHandler);
  msurvival = new JMenuItem("Freeplay");
  msurvival.addActionListener(menuHandler);
  modes.add(campaign);
  //modes.add(chase);
  modes.add(freeplay);
  multiplayer.add(race);
  multiplayer.add(msurvival);
  modes.add(multiplayer);
  menubar.add(modes);
  options = new JMenu("Options");
  pushing = new JCheckBoxMenuItem("Pushing",false);
  numai = new JMenuItem("Number of AI: 3");
  numai.addActionListener(menuHandler);
  options.add(pushing);
  options.add(numai);
  menubar.add(options);
  extra = new JMenu("Extra");
  chooselevel = new JMenuItem("Choose Level");
  chooselevel.addActionListener(menuHandler);
  extra.add(chooselevel);
  menubar.add(extra);
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
   } else if (source==msurvival){
    board.restart(3);
   } else if (source==chase){
    board.restart(4);
   } else if (source==chooselevel){
    Object[] list = new Object[board.maxLevel];
    for (int i=0; i<list.length; i++){
     list[i]=i+1;
    }
    try{
     board.restart(1,(int)JOptionPane.showInputDialog(null,"Select the level in Classic that you'd like to jump to:","Level Selector",JOptionPane.PLAIN_MESSAGE,null,list,list[0]));
    } catch (Exception exception){
    }
   } else if (source==numai){
    Object[] list = new Object[10];
    for (int i=0; i<10; i++){
     list[i]=i;
    }
    try{
     numaiint = (int)JOptionPane.showInputDialog(null,"How many AI would you like to chase you?\nWill take effect when restarted.","Number of AI",JOptionPane.PLAIN_MESSAGE,null,list,list[numaiint]);
     numai.setText("Number of AI: "+numaiint);
    } catch (Exception exception){
    }
   }
  }
 }
}