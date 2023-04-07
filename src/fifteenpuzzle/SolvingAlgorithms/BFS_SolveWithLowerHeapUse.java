package fifteenpuzzle.SolvingAlgorithms;
import fifteenpuzzle.puzzle.PuzzleBoard;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.ArrayList;

public class BFS_SolveWithLowerHeapUse extends PuzzleBoard implements BFS{
    public BFS_SolveWithLowerHeapUse(String args0, String args1) {
        super(args0, args1);
    }

    public static long orderOfFirstChildOf(long orderOfParent){
        //get the order on that level

        long a0 = 0; // reccurrence function to calculate
        long a1 = 4;
        long countLevel = 1;
        long level;
    
        while(true){
            if (a0 <= orderOfParent && orderOfParent < a1){
                level = countLevel;
                break;
            }else{
                long newa1 = 4*a1 - 3*a0;
                a0 = a1;
                a1 = newa1;
                countLevel++;
            }
        }

        long orderOfParentOnLevel = orderOfParent - 2 * (long)Math.pow(3, level - 1) + 2;
        long orderOfFirstChild = 3 * orderOfParentOnLevel + 2 * (long)Math.pow(3, level) - 2;

        return orderOfFirstChild;
    }

    private static ArrayList<Character> getPath(long count){
        ArrayList<Character> tempPath = new ArrayList<Character>();
        ArrayList<Long> orderOfOperationInQueue = new ArrayList<Long>();

        // determine the level      2*3^(x-1) - 2  <=  count < 2*3^x - 2       -> x is the level
        long a0 = 0; // reccurrence function to calculate
        long a1 = 4;
        long countLevel = 1;
        long level;
    
        while(true){
            if (a0 <= count && count < a1){
                level = countLevel;
                break;
            }else{
                long newa1 = 4*a1 - 3*a0;
                a0 = a1;
                a1 = newa1;
                countLevel++;
            }
        }
        
        //System.out.println(level);
        long orderOnCurrentLevel = count - 2 * (int)Math.pow(3, level - 1) + 2;
        //System.out.println(orderOnCurrentLevel);

        for (long i = level; i >= 1; i--){
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
            long orderInTheNode = orderOfOperationInQueue.get(i) % 3;
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
        Queue<Long> queue = new PriorityQueue<Long>();
        //long count = 0;        
        // the order is Up Down Left Right // N is for skipping but still add into queue
        final long UP = 0;
        final long DOWN = 1;
        final long LEFT = 2;
        final long RIGHT = 3;
        // init the 1st-level of tree
        if (super.hasUp()){
            queue.add(UP);
        }
        if (super.hasDown()){
            queue.add(DOWN);
        }
        if (super.hasLeft()){
            queue.add(LEFT);
        }
        if (super.hasRight()){
            queue.add(RIGHT);
        }
        //////////////////////////////////////////
        
        while(!queue.isEmpty()){
            long popQueue = queue.poll();
            ArrayList<Character> listMovements = new ArrayList<Character>();
            
            listMovements = getPath(popQueue);
            PuzzleBoard tempPuzzleBoard = new PuzzleBoard(this);
            //System.out.println("" + listMovements);

            if (isSolution(listMovements, tempPuzzleBoard)){
                //System.out.println("Solved: " + listMovements);
                PuzzleBoard check = new PuzzleBoard(this);
                for (int i = 0; i < listMovements.size(); i++){
                    check.move(listMovements.get(i));
                    System.out.println(listMovements.get(i) + "   " + check.valueDisplacement());
                }
                return listMovements.toString();
            }

            Character lastMovements = listMovements.get(listMovements.size() - 1);
            long orderOfFirstChild = orderOfFirstChildOf(popQueue);
            //System.out.println("The order: " + popQueue + " " );
            if (lastMovements == 'U'){
                if(tempPuzzleBoard.hasUp()){
                    queue.add(orderOfFirstChild + 0);
                }
                if(tempPuzzleBoard.hasLeft()){
                    queue.add(orderOfFirstChild + 1);
                }
                if(tempPuzzleBoard.hasRight()){
                    queue.add(orderOfFirstChild + 2);
                }
                continue;
            }
            if (lastMovements == 'D'){
                if(tempPuzzleBoard.hasDown()){
                    queue.add(orderOfFirstChild + 0);
                }
                if(tempPuzzleBoard.hasLeft()){
                    queue.add(orderOfFirstChild + 1);
                }
                if(tempPuzzleBoard.hasRight()){
                    queue.add(orderOfFirstChild + 2);
                }
                continue;
            }
            if (lastMovements == 'L'){
                if(tempPuzzleBoard.hasUp()){
                    queue.add(orderOfFirstChild + 0);
                }
                if(tempPuzzleBoard.hasDown()){
                    queue.add(orderOfFirstChild + 1);
                }
                if(tempPuzzleBoard.hasLeft()){
                    queue.add(orderOfFirstChild + 2);
                }
                continue;
            }
            if (lastMovements == 'R'){
                if(tempPuzzleBoard.hasUp()){
                    queue.add(orderOfFirstChild + 0);
                }
                if(tempPuzzleBoard.hasDown()){
                    queue.add(orderOfFirstChild + 1);
                }
                if(tempPuzzleBoard.hasRight()){
                    queue.add(orderOfFirstChild + 2);
                }
                continue;
            }
            
        }
        return "Error";
        
    }
   
}
