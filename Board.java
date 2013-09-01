
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class Board extends JPanel implements ActionListener{
 int boardWidth = 20;
 int boardHeight = 30;
 //int time = (int)((1/level)*100+350-5*level);
 boolean isStarted = false;
 boolean isPaused = false;
 int numTurns = 0;
 JLabel statusbar;
 Timer timer;
 int[][] board = new int[boardHeight][boardWidth];
 int[][] piece1 = new int[boardHeight][boardWidth];
 int[][] piece2 = new int[boardHeight][boardWidth];
 int positionX1;
 int positionY1;
 int positionX2;
 int positionY2;
 boolean player1, player2;
 int score1, score2;
 int mode; //0 for freeplay. 1 for campaign. 2 for multiplayer.
 int level = 1;
 int levelPositionX;
 //int levelPositionY;
 BlockCascade blah;
 ArrayList<Chaser> chaser = new ArrayList<Chaser>();
 //Chaser chaser = new Chaser(new int[]{(int)(boardWidth/2),boardHeight-2},this);
 int maxLevel = 1;
 
 public Board(BlockCascade parent){
  setFocusable(true);
  timer = new Timer(time(), this);
  timer.start();
  statusbar = parent.getStatusBar();
  addKeyListener(new TAdapter());
  clearBoard();  
  piece1[boardHeight-7][(int)(boardWidth/2)]=1;
  positionX1 = (int)(boardWidth/2);
  positionY1 = boardHeight-7;
  mode = 0;
  blah = parent;
  start();
  restart(0);
  pause();
 }

 public void actionPerformed(ActionEvent e){
  if (numTurns%5==0){
   timer.setDelay(timer.getDelay()-1);
   if (mode==0 && numTurns==0){
    timer.setDelay(500);
   }
   for (int i=0; i<chaser.size(); i++){
    if (mode!=1){
     chaser.get(i).timer.setDelay((int)(timer.getDelay()/1.78));
    } else {
     chaser.get(i).timer.setDelay((int)(timer.getDelay()/1.6));
     if (numTurns==5){
      chaser.get(i).timer.start();
     } else if (numTurns<5){
      chaser.get(i).timer.stop();
     }
    }
   }
  }
  nextTurn();
  numTurns++;
 }

 public void start(){
  if (isPaused){
   return;
  }
  isStarted = true;
  numTurns = 0;
  player1 = true;
  timer.setDelay(time());
  clearBoard();
  timer.start();
 }

 public void pause(){
  if (!isStarted){
   return;
  }
  isPaused = !isPaused;
  if (isPaused){
   timer.stop();
   for (int i=0; i<chaser.size(); i++){
    chaser.get(i).timer.stop();
   }
   statusbar.setText("Press space to unpause");
  } else {
   timer.start();
   for (int i=0; i<chaser.size(); i++){
    chaser.get(i).timer.start();
   }
   statusbar.setText(String.valueOf(numTurns));
  }
  repaint();
 }

 public void restart(int modee, int levelo){
  piece1[positionY1][positionX1]=0;
  piece1[boardHeight-2][(int)(boardWidth/2)]=1;
  positionY1=boardHeight-2;
  positionX1=(int)(boardWidth/2);
  levelPositionX = (int)(boardWidth*Math.random());
  clearBoard();
  for (int i=0; i<(int)(boardHeight/2.5); i++){
   nextTurn();
  }
  numTurns=0;
  level = levelo;
  mode = 1;
  player1 = true;
  player2 = false;
  isStarted = true;
  timer.setDelay(time());
  timer.start();
  isPaused=false;
  pause();
 }

 public void restart(int modee){
  chaser = new ArrayList<Chaser>();
  if (modee==0){   //freeplay
   piece1[positionY1][positionX1]=0;
   piece1[boardHeight-7][(int)(boardWidth/2)]=1;
   positionY1=boardHeight-7;
   positionX1=(int)(boardWidth/2);
   clearBoard();
   player1=true;
   player2=false;
   level=1;
   isPaused=false;
   start();
   for (int i=0; i<blah.numaiint; i++){
    chaser.add(new Chaser(new int[]{(int)(boardWidth/2),boardHeight-2},this));
   }
   mode = 0;
  } else if (modee==1){ //classic
   piece1[positionY1][positionX1]=0;
   piece1[boardHeight-4][(int)(boardWidth/2)]=1;
   positionY1=boardHeight-4;
   positionX1=(int)(boardWidth/2);
   levelPositionX = (int)(boardWidth*Math.random());
   clearBoard();
   for (int i=0; i<(int)(boardHeight/2.5); i++){
    nextTurn();
   }
   numTurns=0;
   level = 1;
   mode = 1;
   player1 = true;
   player2 = false;
   isStarted = true;
   for (int i=0; i<blah.numaiint; i++){
    chaser.add(new Chaser(new int[]{(int)(boardWidth/2),boardHeight-2},this));
    chaser.get(i).timer.stop();
   }
   timer.setDelay(time());
   timer.start();
  } else if (modee==2){ //multiplayer race
   piece1[positionY1][positionX1]=0;
   piece1[boardHeight-2][(int)(2*boardWidth/3)]=1;
   positionY1=boardHeight-2;
   positionX1=(int)(2*boardWidth/3);
   piece2[positionY2][positionX2]=0;
   piece2[boardHeight-2][(int)(boardWidth/3)]=1;
   positionY2=boardHeight-2;
   positionX2=(int)(boardWidth/3);
   player1 = true;
   player2 = true;
   score1 = 0;
   score2 = 0;
   levelPositionX = (int)(boardWidth*Math.random());
   clearBoard();
   isStarted = true;
   for (int i=0; i<(int)(boardHeight/2.5); i++){
    nextTurn();
   }
   numTurns=0;
   level = 1;
   mode = 2;
   timer.setDelay(time());
   timer.start();
  } else if (modee==3){  //multiplayer freeplay
   piece1[positionY1][positionX1]=0;
   piece1[boardHeight-2][(int)(2*boardWidth/3)]=1;
   positionY1=boardHeight-2;
   positionX1=(int)(2*boardWidth/3);
   piece2[positionY2][positionX2]=0;
   piece2[boardHeight-2][(int)(boardWidth/3)]=1;
   positionY2=boardHeight-2;
   positionX2=(int)(boardWidth/3);
   player1 = true;
   player2 = true;
   clearBoard();
   isStarted = true;
   numTurns=0;
   level = 1;
   mode = 3;
   timer.setDelay(550);
   timer.start();
  } else if (modee==4){ //currently nothing
   piece1[positionY1][positionX1]=0;
   piece1[boardHeight-2][(int)(boardWidth/2)]=1;
   positionY1=boardHeight-2;
   positionX1=(int)(boardWidth/2);
   clearBoard();
   player1=true;
   player2=false;
   level=1;
   start();
   mode = 0;////////////////////////////
  }
  isPaused = false;
   for (int i=0; i<chaser.size(); i++){
    chaser.get(i).timer.start();
   }
 }

 public int time(){
  if ((int)((1/level)*200+350-5*level)>50){
   return (int)((1/level)*100+350-5*level);
  } else {
   return 50;
  }
 }

 public boolean levelup(){
  if (mode==1 || mode==2){
   if ((positionX1==levelPositionX && positionY1==1) || (positionX2==levelPositionX && positionY2==1)){
    if (mode==2){
     if (positionX1==levelPositionX){
      score1++;
     } else if (positionX2==levelPositionX){
      score2++;
     }
     piece2[positionY2][positionX2]=0;
     piece2[boardHeight-2][(int)(boardWidth/3)]=1;
     positionY2=boardHeight-2;
     positionX2=(int)(boardWidth/3);
     piece1[positionY1][positionX1]=0;
     piece1[boardHeight-2][(int)(2*boardWidth/3)]=1;
     positionY1=boardHeight-2;
     positionX1=(int)(2*boardWidth/3);
     player1 = true;
     player2 = true;
    } else {
     piece1[positionY1][positionX1]=0;
     piece1[boardHeight-2][(int)(boardWidth/2)]=1;
     positionY1=boardHeight-2;
     positionX1=(int)(boardWidth/2);
     player1 = true;
    }
    level++;
    levelPositionX = (int)(boardWidth*Math.random());
    clearBoard();
    for (int i=0; i<(int)(boardHeight/2.5); i++){
     nextTurn();
    }
    numTurns=0;
    maxLevel=level;
    return true;
   }
   return false;
  } else {
   level=1;
   return false;
  }
 }

 public void nextTurn(){
  for (int i=boardHeight-1; i>=0; i--){
   for (int j=boardWidth-1; j>=0; j--){
    if (i==boardHeight-1){
     board[i][j]=0;
    } else {
     if (board[i][j]==1 && board[i+1][j]!=1){
      if (piece1[i+1][j]==1 && i!=(boardHeight-2) && player1){
       if (player2){
        if (piece2[i+2][j]==1 && i!=(boardHeight-3)){
         /*piece2[i+3][j]=1;
         positionX2=j;
         positionY2=i+3;
         piece2[i+2][j]=0;*/
        } else if (piece2[i+2][j]==1){
         
        } else {
         piece1[i+2][j]=1;
         positionX1=j;
         positionY1=i+2;
         piece1[i+1][j]=0;
        }
       } else {
        piece1[i+2][j]=1;
        positionX1=j;
        positionY1=i+2;
        piece1[i+1][j]=0;
       }
      }
      if (piece2[i+1][j]==1 && i!=(boardHeight-2) && player2){
       if (player1){
        if (piece1[i+2][j]==1 && i!=(boardHeight-3)){
         /*piece1[i+3][j]=1;
         positionX1=j;
         positionY1=i+3;
         piece1[i+2][j]=0;*/
        } else if (piece1[i+2][j]==1){
   
        } else {
         piece2[i+2][j]=1;
         positionX2=j;
         positionY2=i+2;
         piece2[i+1][j]=0;
        }
       } else {
        piece2[i+2][j]=1;
        positionX2=j;
        positionY2=i+2;
        piece2[i+1][j]=0;
       }
      }
      for (int m=0; m<chaser.size(); m++){
       if (chaser.get(m).position[1]==(i+1) && chaser.get(m).position[0]==j && (i+2)<boardHeight){
        chaser.get(m).position[1]++;
       }
      }
      board[i+1][j]=1;
      board[i][j]=0;
     }
    }
   }
  }
  for (int i=0; i<(int)(boardWidth/4); i++){
   double random = Math.random();
   int blah=(int)(random*boardWidth);
   board[0][blah]=1;
  }
  if (mode==0 || mode==3){
   statusbar.setText(numTurns+"");
  } else if (mode==1){
   statusbar.setText(numTurns+" Level "+level);
  } else if (mode==2){
   statusbar.setText(score2+"   Level "+level+"   "+score1);
  }
  if (mode==0 || mode==1){
   for (int j=0; j<boardWidth; j++){
    if (board[boardHeight-1][j]==1 && piece1[boardHeight-1][j]==1){
     timer.stop();
     board[boardHeight-1][j]=2;
     statusbar.setText(statusbar.getText()+" game over!");
     isStarted=false;
    }
   }
  } else if (mode==2 || mode==3){
   for (int i=0; i<boardHeight; i++){
    for (int j=0; j<boardWidth; j++){
     if (board[i][j]==1 && piece1[i][j]==1 && player1){
      board[i][j]=2;
      player1=false;
      if (!player2 || mode==3){
       timer.stop();
       statusbar.setText(statusbar.getText()+" game over!");
       isStarted=false;
      }
     } else if (board[i][j]==1 && piece2[i][j]==1 && player2){
      board[i][j]=2;
      player2=false;
      if (!player1 || mode==3){
       timer.stop();
       statusbar.setText(statusbar.getText()+" game over!");
       isStarted=false;
      }
     }
    }
   }
  }
  repaint();
 }

 public void clearBoard(){
  board = new int[boardHeight][boardWidth];
 }

 public void aiGotcha(){
  if (mode!=1 || numTurns>8){
   for (int i=0; i<chaser.size(); i++){
    if (positionX1==chaser.get(i).position[0] && positionY1==chaser.get(i).position[1]){
     timer.stop();
     board[positionY1][positionX1]=2;
     if (player1){
      statusbar.setText(statusbar.getText()+" game over!");
     }
     player1=false;
     isStarted=false;
     chaser.get(i).timer.stop();
    }
   }
  }
 }

 int squareWidth(){return (int) getSize().getWidth() / boardWidth;}
 int squareHeight(){return (int) getSize().getHeight() / boardHeight;}

 public void paint(Graphics g){
  super.paint(g);
  Dimension size = getSize();
  int boardTop = (int) size.getHeight() - boardHeight * squareHeight();
  if (mode==1 || mode==2){
   drawSquare(g,0+levelPositionX*squareWidth(),boardTop+(1)*squareHeight(),4);
  }
  for (int i=0; i<boardHeight; ++i){
   for (int j=0; j<boardWidth; ++j){
    if (board[i][j]==1){
     drawSquare(g,0+j*squareWidth(),boardTop+i*squareHeight(),1);
    }
    if (board[i][j]==2){
     drawSquare(g,0+j*squareWidth(),boardTop+i*squareHeight(),2);
    }
    for (int m=0; m<chaser.size(); m++){
     drawSquare(g,0+chaser.get(m).position[0]*squareWidth(),boardTop+chaser.get(m).position[1]*squareHeight(),6);
    }
    if (piece1[i][j]==1 && player1==true){
     drawSquare(g,0+j*squareWidth(),boardTop+i*squareHeight(),3);
    }
    if (piece2[i][j]==1 && player2==true){
     drawSquare(g,0+j*squareWidth(),boardTop+i*squareHeight(),5);
    }
   }
  }
  aiGotcha();
  for (int i=0; i<boardHeight; i++){
   for (int j=0; j<boardWidth; j++){
    if (board[i][j]==2){
     drawSquare(g,0+j*squareWidth(),boardTop+i*squareHeight(),2);
    }
   }
  }
 }

 private void drawSquare(Graphics g, int x, int y, int type){
  /*Color colors[] = {new Color(0,0,0), new Color(204,102,102),
   new Color(102,204,102),new Color(102,102,204),
   new Color(204,204,102),new Color(204,102,204),
   new Color(102,204,204),new Color(218,170,0)
  };
  Color color = colors[shape.ordinal()];*/
  Color color;
  color = new Color(0,0,0);
  if (type==1){ //falling blocks
   color = new Color(82,204,82);//102,204,102);
  } else if (type==2){ //dead
   color = new Color(204,51,51);
  } else if (type==3){ //player1
   color = new Color(102,51,204);
  } else if (type==4){ //portal
   color = new Color(204,204,204);
  } else if (type==5){ //player2
   color = new Color(255,165,0);
  } else if (type==6){ //chaser
   color = new Color(0,0,0);
  }
  g.setColor(color);
  g.fillRect(x+1,y+1,squareWidth()-2,squareHeight()-2);
  g.setColor(color.brighter());
  g.drawLine(x,y+squareHeight()-1,x,y);
  g.drawLine(x,y,x+squareWidth()-1,y);
  g.setColor(color.darker());
  g.drawLine(x+1,y+squareHeight()-1,x+squareWidth()-1,y+squareHeight()-1);
  g.drawLine(x+squareWidth()-1,y+squareHeight()-1,x+squareWidth()-1,y+1);
 }
 
 private void tryMove(int direction, int player){
  if (player==1){
   if (direction==0){ //left
    if ((positionX1-1)<0){
     return;
    }
    if (board[positionY1][positionX1-1]==1){
     return;
    }
    if (piece2[positionY1][positionX1-1]==1 && player2){
     if (blah.pushing.getState() && (positionX1-2)>=0 && board[positionY1][positionX1-2]!=1){
      piece2[positionY1][positionX1-2]=1;
      piece2[positionY1][positionX1-1]=0;
      positionX2=positionX1-2;
     } else {
      return;
     }
    }
    piece1[positionY1][positionX1-1]=1;
    piece1[positionY1][positionX1]=0;
    positionX1=positionX1-1;
   } else if (direction==1){ //right
    if ((positionX1+1)>boardWidth-1){
     return;
    }
    if (board[positionY1][positionX1+1]==1){
     return;
    }
    if (piece2[positionY1][positionX1+1]==1 && player2){
     if (blah.pushing.getState() && positionX1+2<=boardWidth-1 && board[positionY1][positionX1+2]!=1){
      piece2[positionY1][positionX1+2]=1;
      piece2[positionY1][positionX1+1]=0;
      positionX2=positionX1+2;
     } else {
      return;
     }
    }
    piece1[positionY1][positionX1+1]=1;
    piece1[positionY1][positionX1]=0;
    positionX1=positionX1+1;
   } else if (direction==2){ //up
    if ((positionY1-2)<0){
     return;
    }
    if (board[positionY1-1][positionX1]==1){
     return;
    }
    if (piece2[positionY1-1][positionX1]==1 && player2){
     if (blah.pushing.getState() && positionY1-2>=1 && board[positionY1-2][positionX1]!=1){
      piece2[positionY1-2][positionX1]=1;
      piece2[positionY1-1][positionX1]=0;
      positionY2=positionY1-2;
     } else {
      return;
     }
    }
    piece1[positionY1-1][positionX1]=1;
    piece1[positionY1][positionX1]=0;
    positionY1=positionY1-1;
   } else if (direction==3){ //down
    if ((positionY1+1)>boardHeight-1){
     return;
    }
    if (board[positionY1+1][positionX1]==1){
     return;
    }
    if (piece2[positionY1+1][positionX1]==1 && player2){
     if (blah.pushing.getState() && positionY1+2<=boardHeight-1 && board[positionY1+2][positionX1]!=1){
      piece2[positionY1+2][positionX1]=1;
      piece2[positionY1+1][positionX1]=0;
      positionY2=positionY1+2;
     } else {
      return;
     }
    }
    piece1[positionY1+1][positionX1]=1;
    piece1[positionY1][positionX1]=0;
    positionY1=positionY1+1;
   }
  } else if (player==2){
   if (direction==0){ //left
    if ((positionX2-1)<0){
     return;
    }
    if (board[positionY2][positionX2-1]==1){
     return;
    }
    if (piece1[positionY2][positionX2-1]==1 && player1){
     if (blah.pushing.getState() && (positionX2-2)>=0 && board[positionY2][positionX2-2]!=1){
      piece1[positionY2][positionX2-2]=1;
      piece1[positionY2][positionX2-1]=0;
      positionX1=positionX2-2;
     } else {
      return;
     }
    }
    piece2[positionY2][positionX2-1]=1;
    piece2[positionY2][positionX2]=0;
    positionX2=positionX2-1;
   } else if (direction==1){ //right
    if ((positionX2+1)>boardWidth-1){
     return;
    }
    if (board[positionY2][positionX2+1]==1){
     return;
    }
    if (piece1[positionY2][positionX2+1]==1 && player1){
     if (blah.pushing.getState() && positionX2+2<=boardWidth-1 && board[positionY2][positionX2+2]!=1){
      piece1[positionY2][positionX2+2]=1;
      piece1[positionY2][positionX2+1]=0;
      positionX1=positionX2+2;
     } else {
      return;
     }
    }
    piece2[positionY2][positionX2+1]=1;
    piece2[positionY2][positionX2]=0;
    positionX2=positionX2+1;
   } else if (direction==2){ //up
    if ((positionY2-2)<0){
     return;
    }
    if (board[positionY2-1][positionX2]==1){
     return;
    }
    if (piece1[positionY2-1][positionX2]==1 && player1){
     if (blah.pushing.getState() && positionY2-2>=1 && board[positionY2-2][positionX2]!=1){
      piece1[positionY2-2][positionX2]=1;
      piece1[positionY2-1][positionX2]=0;
      positionY1=positionY2-2;
     } else {
      return;
     }
    }
    piece2[positionY2-1][positionX2]=1;
    piece2[positionY2][positionX2]=0;
    positionY2=positionY2-1;
   } else if (direction==3){ //down
    if ((positionY2+1)>boardHeight-1){
     return;
    }
    if (board[positionY2+1][positionX2]==1){
     return;
    }
    if (piece1[positionY2+1][positionX2]==1 && player1){
     if (blah.pushing.getState() && positionY2+2<=boardHeight-1 && board[positionY2+2][positionX2]!=1){
      piece1[positionY2+2][positionX2]=1;
      piece1[positionY2+1][positionX2]=0;
      positionY1=positionY2+2;
     } else {
      return;
     }
    }
    piece2[positionY2+1][positionX2]=1;
    piece2[positionY2][positionX2]=0;
    positionY2=positionY2+1;
   }
  }
  repaint();
  levelup();
  repaint();
 }
 
 class TAdapter extends KeyAdapter{
  public void keyPressed(KeyEvent e){
   if (!isStarted){
    return;
   }
   int keycode = e.getKeyCode();
   if (keycode=='P' || keycode=='p' || keycode==' '){
    pause();
   }
   if (isPaused){
    return;
   }
   switch (keycode){
    case KeyEvent.VK_LEFT:
     tryMove(0,1);
     break;
    case KeyEvent.VK_RIGHT:
     tryMove(1,1);
     break;
    case KeyEvent.VK_UP:
     tryMove(2,1);
     break;
    case KeyEvent.VK_DOWN:
     tryMove(3,1);
     break;
   }
   if (mode==2 || mode==3){
    if (keycode=='A' || keycode=='a'){
     tryMove(0,2);
    } else if (keycode=='D' || keycode=='d'){
     tryMove(1,2);
    } else if (keycode=='W' || keycode=='w'){
     tryMove(2,2);
    } else if (keycode=='S' || keycode=='s'){
     tryMove(3,2);
    }
   }
  }
 }
}

















