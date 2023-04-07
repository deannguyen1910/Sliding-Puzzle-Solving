package fifteenpuzzle.SolvingAlgorithms;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.ArrayList;
import fifteenpuzzle.puzzle.PuzzleBoard;

public class BadBFS extends PuzzleBoard implements BFS{
    public BadBFS(String args0, String args1){
        super(args0, args1);
    }
    
    private static ArrayList<Character> getPath(int count){
        ArrayList<Character> tempPath = new ArrayList<Character>();
        ArrayList<Integer> orderOfOperationInQueue = new ArrayList<Integer>();

        // determine the level      2*3^(x-1) - 2  <=  count < 2*3^x - 2       -> x is the level
        int a0 = 0; // reccurrence function to calculate
        int a1 = 4;
        int countLevel = 1;
        int level;
        while(true){
            if (a0 <= count && count < a1){
                level = countLevel;
                break;
            }else{
                int newa1 = 4*a1 - 3*a0;
                a0 = a1;
                a1 = newa1;
                countLevel++;
            }
        }
        //System.out.println(level);
        int orderOnCurrentLevel = count - 2 * (int)Math.pow(3, level - 1) + 2;
        //System.out.println(orderOnCurrentLevel);

        for (int i = level; i >= 1; i--){
            orderOfOperationInQueue.add(0, orderOnCurrentLevel);
            orderOnCurrentLevel /= 3;
        }
        
        if (orderOfOperationInQueue.isEmpty()) throw new IllegalArgumentException();
        
        
        if (orderOfOperationInQueue.get(0) == 0){
            tempPath.add('U');
        }
        if (orderOfOperationInQueue.get(0) == 1){
            tempPath.add('D');
        }
        if (orderOfOperationInQueue.get(0) == 2){
            tempPath.add('L');
        }
        if (orderOfOperationInQueue.get(0) == 3){
            tempPath.add('R');
        }

        if (orderOfOperationInQueue.size() < 2) return tempPath;

        for (int i = 1; i < orderOfOperationInQueue.size(); i++){
            int orderInTheNode = orderOfOperationInQueue.get(i) % 3;
            if (tempPath.get(i - 1) == 'U'){
                if (orderInTheNode == 0){
                    tempPath.add('U');
                }
                if (orderInTheNode == 1){
                    tempPath.add('L');
                }
                if (orderInTheNode == 2){
                    tempPath.add('R');
                }
            }
            if (tempPath.get(i - 1) == 'D'){
                if (orderInTheNode == 0){
                    tempPath.add('D');
                }
                if (orderInTheNode == 1){
                    tempPath.add('L');
                }
                if (orderInTheNode == 2){
                    tempPath.add('R');
                }
            }
            if (tempPath.get(i - 1) == 'L'){
                if (orderInTheNode == 0){
                    tempPath.add('U');
                }
                if (orderInTheNode == 1){
                    tempPath.add('D');
                }
                if (orderInTheNode == 2){
                    tempPath.add('L');
                }
            }
            if (tempPath.get(i - 1) == 'R'){
                if (orderInTheNode == 0){
                    tempPath.add('U');
                }
                if (orderInTheNode == 1){
                    tempPath.add('D');
                }
                if (orderInTheNode == 2){
                    tempPath.add('R');
                }
            }
        }
    
        return tempPath;
    }

    public String solution(){
        Queue<Character> queue = new PriorityQueue<Character>();
        int count = 0;        
        // the order is Up Down Left Right // N is for skipping but still add into queue
        
        // init the 1st-level of tree
        if (this.hasUp()){
            queue.add('U');
        }
        else{
            queue.add('N');
        }
        if (this.hasDown()){
            queue.add('D');
        }
        else{
            queue.add('N');
        }
        if (this.hasLeft()){
            queue.add('L');
        }
        else{
            queue.add('N');
        }
        if (this.hasRight()){
            queue.add('R');
        }
        else{
            queue.add('N');
        }
        //////////////////////////////////////////
        
        while(!queue.isEmpty()){
            Character temp = queue.poll();
            count++;
            if (temp == 'N'){
                queue.add('N');
                queue.add('N');
                queue.add('N');
                continue;
            }
            
            ArrayList<Character> listMovements = new ArrayList<Character>();
            listMovements = getPath(count);
            PuzzleBoard tempPuzzleBoard = new PuzzleBoard(this);
            System.out.println("" + listMovements);

            if (isSolution(listMovements, tempPuzzleBoard)){
                System.out.println("Solved: " + listMovements);
                return listMovements.toString();
            }
            
            if (temp == 'U'){
                if(tempPuzzleBoard.hasUp()){
                    queue.add('U');
                }
                else{
                    queue.add('N');
                }
                if(tempPuzzleBoard.hasLeft()){
                    queue.add('L');
                }
                else{
                    queue.add('N');
                }
                if(tempPuzzleBoard.hasRight()){
                    queue.add('R');
                }
                else{
                    queue.add('N');
                }
            }

            if (temp == 'D'){
                if(tempPuzzleBoard.hasUp()){
                    queue.add('D');
                }
                else{
                    queue.add('N');
                }
                if(tempPuzzleBoard.hasLeft()){
                    queue.add('L');
                }
                else{
                    queue.add('N');
                }
                if(tempPuzzleBoard.hasRight()){
                    queue.add('R');
                }
                else{
                    queue.add('N');
                }
            }
            
            if (temp == 'L'){
                if(tempPuzzleBoard.hasUp()){
                    queue.add('U');
                }
                else{
                    queue.add('N');
                }
                if(tempPuzzleBoard.hasLeft()){
                    queue.add('D');
                }
                else{
                    queue.add('N');
                }
                if(tempPuzzleBoard.hasRight()){
                    queue.add('L');
                }
                else{
                    queue.add('N');
                }
            }

            if (temp == 'R'){
                if(tempPuzzleBoard.hasUp()){
                    queue.add('U');
                }
                else{
                    queue.add('N');
                }
                if(tempPuzzleBoard.hasLeft()){
                    queue.add('D');
                }
                else{
                    queue.add('N');
                }
                if(tempPuzzleBoard.hasRight()){
                    queue.add('R');
                }
                else{
                    queue.add('N');
                }
            }
        }

        return "Error";
    }
}
