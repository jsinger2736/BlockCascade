import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class Chaser implements ActionListener{
 int[] position = new int[2];
 Board parent;
 Timer timer;

 public Chaser(int[] positiono, Board parento){
  position=positiono;
  parent = parento;
  timer = new Timer(250,this);
  timer.start();
 }

 public void actionPerformed(ActionEvent e){
  int distance1, distance2;
  distance1 = Math.abs(position[0]-parent.positionX1);
  distance1 = distance1+Math.abs(position[1]-parent.positionY1);
  distance2 = Math.abs(position[0]-parent.positionX2);
  distance2 = distance2+Math.abs(position[1]-parent.positionY2);
  if (parent.mode==2 || parent.mode==3){
   if (!parent.player1 && !parent.player2){
   } else if (!parent.player1){
    distance1=200;
   } else if (!parent.player2){
    distance2=200;
   }
  }
  if (distance1<distance2){
   tryMove(new int[]{parent.positionX1,parent.positionY1});
  } else if (distance2<distance1){
   tryMove(new int[]{parent.positionX2,parent.positionY2});
  } else {
   if (Math.random()>.5){
    tryMove(new int[]{parent.positionX1,parent.positionY1});
   } else {
    tryMove(new int[]{parent.positionX2,parent.positionY2});
   }
  }
  //tryMove(new int[]{parent.positionX1,parent.positionY1});
 }

 public void tryMove(int[] target){
  int[] direction = {1,2,3,4};
  int xChange = target[0]-position[0];
  int yChange = target[1]-position[1];
  if (xChange>0 && yChange<0){
   if (Math.random()>.4){
    direction = new int[]{1,2,4,3};
   } else {
    direction = new int[]{2,1,4,3};
   }
  } else if (xChange>0 && yChange>0){
   direction = new int[]{2,3,1,4};
  } else if (xChange<0 && yChange<0){
   if (Math.random()>.4){
    direction = new int[]{1,4,2,3};
   } else {
    direction = new int[]{4,1,2,3};
   }
  } else if (xChange<0 && yChange>0){
   direction = new int[]{4,3,1,2};
  } else if (xChange>0){
   direction = new int[]{2,1,3,4};
  } else if (xChange<0){
   direction = new int[]{4,1,3,2};
  } else if (yChange<0){
   direction = new int[]{1,2,4,3};
  } else if (yChange>0){
   direction = new int[]{3,2,4,1};
  }
  int temp;
  double random = Math.random();
  if (random<.7 && random>.35){//RANDOMIZE THIS HOLY CRAP I SHOULDVE THOUGHT O THAT EARLIER
   temp = direction[1];
   direction[1]=direction[2];
   direction[2]=temp;
  } else if (random>.7 && random<.76){
   temp = direction[2];
   direction[2]=direction[3];
   direction[3]=temp;
  } else if (random>.76 && random<.82){
   temp = direction[1];
   direction[1]=direction[3];
   direction[3]=temp;
  } else if (random>.82 && random<.88){
   temp = direction[1];
   direction[1]=direction[0];
   direction[0]=temp;
  } else if (random>.88 && random<.94){
   temp=direction[2];
   direction[2]=direction[0];
   direction[0]=temp;
  } else if (random>.94){
   temp=direction[3];
   direction[3]=direction[0];
   direction[0]=temp;
  }
  for (int i=0; i<4; i++){
   if (attempt(direction[i])){
    break;
   }
  }
  parent.repaint();
 }

 public boolean attempt(int direction){
  int[][] board = parent.board;
  if (direction==1){
   if ((position[1]-1)>=1){
    if (board[position[1]-1][position[0]]!=1){
     position[1]--;
     return true;
    }
   }
  } else if (direction==2){
   if ((position[0]+1)<parent.boardWidth){
    if (board[position[1]][position[0]+1]!=1){
     position[0]++;
     return true;
    }
   }
  } else if (direction==3){
   if ((position[1]+1)<parent.boardHeight){
    if (board[position[1]+1][position[0]]!=1){
     position[1]++;
     return true;
    }
   }
  } else if (direction==4){
   if ((position[0]-1)>=0){
    if (board[position[1]][position[0]-1]!=1){
     position[0]--;
     return true;
    }
   }
  }
  return false;
 }

}