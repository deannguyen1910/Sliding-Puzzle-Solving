package fifteenpuzzle.SolvingAlgorithms;
import fifteenpuzzle.puzzle.PuzzleBoard;
import java.util.Queue;
import java.util.PriorityQueue;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class BFS_SolveWithInformedSearch extends PuzzleBoard implements BFS{
    static final char UP = 'U';
    static final char DOWN = 'D';
    static final char LEFT = 'L';
    static final char RIGHT = 'R';
    int theGoodIndexOfSolution = 2;

    private BFS_SolveWithInformedSearch(PuzzleBoard item){ // deep copy
        super(item);
    }

    private BFS_SolveWithInformedSearch bfs_SolveWithInformedSearch(ArrayList<Character> listMove){
        return new BFS_SolveWithInformedSearch(puzzleBoard(listMove));
    }

    public BFS_SolveWithInformedSearch(String args0, String args1) {
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

    private boolean isAGoodMove(char moveDirection, final PuzzleBoard boardCurrent, final ArrayList<Character> listMovements){
        PuzzleBoard temp = new PuzzleBoard(boardCurrent);
        Integer[] atFirst = boardCurrent.valueDisplacement();
        temp.move(moveDirection);
        Integer[] atLater = temp.valueDisplacement();
        
        
        if ((Math.abs(atFirst[0]) > Math.abs(atLater[0])) ||
            (Math.abs(atFirst[1]) > Math.abs(atLater[1])) ||
            (Math.abs(atFirst[2]) > Math.abs(atLater[2])) ||
            (Math.abs(atFirst[3]) > Math.abs(atLater[3]))){
                return true;
        }
        else{
            int countBadMove = 0;
            // check if there is a bad move once before, if so, it should return false
            temp = new PuzzleBoard(this);
            atFirst = boardCurrent.valueDisplacement();
            for (int i = 0; i < listMovements.size(); i++){
                temp.move(listMovements.get(i));
                atLater = temp.valueDisplacement();
                if (!((Math.abs(atFirst[0]) > Math.abs(atLater[0])) ||
                    (Math.abs(atFirst[1]) > Math.abs(atLater[1])) ||
                    (Math.abs(atFirst[2]) > Math.abs(atLater[2])) ||
                    (Math.abs(atFirst[3]) > Math.abs(atLater[3])))){
                        countBadMove++;
                }
                atFirst[0] = atLater[0];
                atFirst[1] = atLater[1];
                atFirst[2] = atLater[2];
                atFirst[3] = atLater[3];
                if (countBadMove >= theGoodIndexOfSolution){
                    return false;
                } 
            }
            return true;
        }
    }

    public static int maxLevelThatVariableCanHandle(){
        long a0 = 0; // reccurrence function to calculate
        long a1 = 4;
        int countLevel = 1;
        long MAX = (long)Integer.MAX_VALUE;
        while(a0 <= MAX && a1 <= MAX){
            long newa1 = 4*a1 - 3*a0;
            a0 = a1;
            a1 = newa1;
            countLevel++;
        }

        return countLevel - 3;
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
        
        int MAX_STEP = maxLevelThatVariableCanHandle();
        
        ArrayList<BFS_SolveWithInformedSearch> subGraph = new ArrayList<BFS_SolveWithInformedSearch>();

        while(!queue.isEmpty() && queue.peek() < (2 * (long)Math.pow(3, MAX_STEP) - 2)){
            long popQueue = queue.poll();
            ArrayList<Character> listMovements = new ArrayList<Character>();
            
            listMovements = getPath(popQueue);
            PuzzleBoard tempPuzzleBoard = new PuzzleBoard(this);
            //System.out.println("" + listMovements);

            if (isSolution(listMovements, tempPuzzleBoard)){
                //System.out.println("Solved: " + listMovements);
                return "Solved " + listMovements.toString();
            }
            if (listMovements.size() == 16){
                BFS_SolveWithInformedSearch temp = bfs_SolveWithInformedSearch(listMovements);
                subGraph.add(temp);
            }

            Character lastMovements = listMovements.get(listMovements.size() - 1);
            long orderOfFirstChild = orderOfFirstChildOf(popQueue);
            //System.out.println("The order: " + popQueue + " " );
            

            if (lastMovements == 'U'){
                if(tempPuzzleBoard.hasUp() && isAGoodMove(this.UP, tempPuzzleBoard, listMovements)){
                    queue.add(orderOfFirstChild + 0);
                }
                if(tempPuzzleBoard.hasLeft() && isAGoodMove(this.LEFT, tempPuzzleBoard, listMovements)){
                    queue.add(orderOfFirstChild + 1);
                }
                if(tempPuzzleBoard.hasRight() && isAGoodMove(this.RIGHT, tempPuzzleBoard, listMovements)){
                    queue.add(orderOfFirstChild + 2);
                }
                continue;
            }
            if (lastMovements == 'D'){
                if(tempPuzzleBoard.hasDown() && isAGoodMove(this.DOWN, tempPuzzleBoard, listMovements)){
                    queue.add(orderOfFirstChild + 0);
                }
                if(tempPuzzleBoard.hasLeft() && isAGoodMove(this.LEFT, tempPuzzleBoard, listMovements)){
                    queue.add(orderOfFirstChild + 1);
                }
                if(tempPuzzleBoard.hasRight() && isAGoodMove(this.RIGHT, tempPuzzleBoard, listMovements)){
                    queue.add(orderOfFirstChild + 2);
                }
                continue;
            }
            if (lastMovements == 'L'){
                if(tempPuzzleBoard.hasUp() && isAGoodMove(this.UP, tempPuzzleBoard, listMovements)){
                    queue.add(orderOfFirstChild + 0);
                }
                if(tempPuzzleBoard.hasDown() && isAGoodMove(this.DOWN, tempPuzzleBoard, listMovements)){
                    queue.add(orderOfFirstChild + 1);
                }
                if(tempPuzzleBoard.hasLeft() && isAGoodMove(this.LEFT, tempPuzzleBoard, listMovements)){
                    queue.add(orderOfFirstChild + 2);
                }
                continue;
            }
            if (lastMovements == 'R'){
                if(tempPuzzleBoard.hasUp() && isAGoodMove(this.UP, tempPuzzleBoard, listMovements)){
                    queue.add(orderOfFirstChild + 0);
                }
                if(tempPuzzleBoard.hasDown() && isAGoodMove(this.DOWN, tempPuzzleBoard, listMovements)){
                    queue.add(orderOfFirstChild + 1);
                }
                if(tempPuzzleBoard.hasRight() && isAGoodMove(this.RIGHT, tempPuzzleBoard, listMovements)){
                    queue.add(orderOfFirstChild + 2);
                }
                continue;
            }     
        }

        System.out.println("There is " + subGraph.size() + " subgraphs");
        
      
        //now try to solve each 
        System.out.println("----------------------------------");
        for (int i = 0; i < subGraph.size(); i++){
            System.out.println(subGraph.get(i).listMoveString());   
            System.out.println(subGraph.get(i).toMatrix());
        }
        return "Error";

    }



    
    public String subGraphSolution(){
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
        
        int MAX_STEP = maxLevelThatVariableCanHandle();
        
        ArrayList<BFS_SolveWithInformedSearch> subGraph = new ArrayList<BFS_SolveWithInformedSearch>();

        while(!queue.isEmpty() && queue.peek() < (2 * (long)Math.pow(3, MAX_STEP) - 2)){
            long popQueue = queue.poll();
            ArrayList<Character> listMovements = new ArrayList<Character>();
            
            listMovements = getPath(popQueue);
            PuzzleBoard tempPuzzleBoard = new PuzzleBoard(this);
            //System.out.println("" + listMovements);

            if (isSolution(listMovements, tempPuzzleBoard)){
                //System.out.println("Solved: " + listMovements);
                return "Solved " + listMovements.toString();
            }
            if (listMovements.size() == 17){
                BFS_SolveWithInformedSearch temp = bfs_SolveWithInformedSearch(listMovements);
                subGraph.add(temp);
            }

            Character lastMovements = listMovements.get(listMovements.size() - 1);
            long orderOfFirstChild = orderOfFirstChildOf(popQueue);
            //System.out.println("The order: " + popQueue + " " );
            

            if (lastMovements == 'U'){
                if(tempPuzzleBoard.hasUp() && isAGoodMove(this.UP, tempPuzzleBoard, listMovements)){
                    queue.add(orderOfFirstChild + 0);
                }
                if(tempPuzzleBoard.hasLeft() && isAGoodMove(this.LEFT, tempPuzzleBoard, listMovements)){
                    queue.add(orderOfFirstChild + 1);
                }
                if(tempPuzzleBoard.hasRight() && isAGoodMove(this.RIGHT, tempPuzzleBoard, listMovements)){
                    queue.add(orderOfFirstChild + 2);
                }
                continue;
            }
            if (lastMovements == 'D'){
                if(tempPuzzleBoard.hasDown() && isAGoodMove(this.DOWN, tempPuzzleBoard, listMovements)){
                    queue.add(orderOfFirstChild + 0);
                }
                if(tempPuzzleBoard.hasLeft() && isAGoodMove(this.LEFT, tempPuzzleBoard, listMovements)){
                    queue.add(orderOfFirstChild + 1);
                }
                if(tempPuzzleBoard.hasRight() && isAGoodMove(this.RIGHT, tempPuzzleBoard, listMovements)){
                    queue.add(orderOfFirstChild + 2);
                }
                continue;
            }
            if (lastMovements == 'L'){
                if(tempPuzzleBoard.hasUp() && isAGoodMove(this.UP, tempPuzzleBoard, listMovements)){
                    queue.add(orderOfFirstChild + 0);
                }
                if(tempPuzzleBoard.hasDown() && isAGoodMove(this.DOWN, tempPuzzleBoard, listMovements)){
                    queue.add(orderOfFirstChild + 1);
                }
                if(tempPuzzleBoard.hasLeft() && isAGoodMove(this.LEFT, tempPuzzleBoard, listMovements)){
                    queue.add(orderOfFirstChild + 2);
                }
                continue;
            }
            if (lastMovements == 'R'){
                if(tempPuzzleBoard.hasUp() && isAGoodMove(this.UP, tempPuzzleBoard, listMovements)){
                    queue.add(orderOfFirstChild + 0);
                }
                if(tempPuzzleBoard.hasDown() && isAGoodMove(this.DOWN, tempPuzzleBoard, listMovements)){
                    queue.add(orderOfFirstChild + 1);
                }
                if(tempPuzzleBoard.hasRight() && isAGoodMove(this.RIGHT, tempPuzzleBoard, listMovements)){
                    queue.add(orderOfFirstChild + 2);
                }
                continue;
            }     
        }

        System.out.println("There is " + subGraph.size() + " subgraphs");
        
        
        //now try to solve each 
        System.out.println("----------------------------------");
        for (int i = 0; i < subGraph.size(); i++){
            //System.out.println(subGraphSolution());
        }
        return "Error";
    }
}