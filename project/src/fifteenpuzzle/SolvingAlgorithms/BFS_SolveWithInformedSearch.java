package fifteenpuzzle.SolvingAlgorithms;
import fifteenpuzzle.puzzle.PuzzleBoard;
import java.util.Queue;
import java.util.PriorityQueue;
import java.time.temporal.TemporalAdjuster;
import java.util.ArrayList;
import java.util.Iterator;

public class BFS_SolveWithInformedSearch extends PuzzleBoard implements BFS{
    static final char UP = 'U';
    static final char DOWN = 'D';
    static final char LEFT = 'L';
    static final char RIGHT = 'R';
    static int howGoodTheSolutionIndex = 3;
    static int levelBFSBeforeSubGraph = 11;
    //static int timeCalledCycle = 0;

    class NoSubGraphException extends Exception{
        private static final long serialVersionUID = 1L;

        public NoSubGraphException(String message) {
            super(message);
        }
    }

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

    // private boolean isAGoodMove(char moveDirection, final PuzzleBoard boardCurrent, final ArrayList<Character> listMovements){
    //     //return true;
    //     PuzzleBoard temp = new PuzzleBoard(boardCurrent);
    //     Integer[] atFirst = boardCurrent.valueDisplacement();
    //     temp.move(moveDirection);
    //     Integer[] atLater = temp.valueDisplacement();

    //     if ((atFirst[0] > atLater[0]) ||
    //         (-atFirst[1] > -(atLater[1])) ||
    //         (atFirst[2] > (atLater[2])) ||
    //         (-atFirst[3] > -(atLater[3]))){
    //             return true;
    //     }
    //     else{
    //         int countBadMove = 0;
    //         // check if there is a bad move once before, if so, it should return false
    //         temp = new PuzzleBoard(this);
    //         atFirst = boardCurrent.valueDisplacement();
    //         for (int i = 0; i < listMovements.size(); i++){
    //             temp.move(listMovements.get(i));
    //             atLater = temp.valueDisplacement();
    //             if (!((atFirst[0]) >  atLater[0] ||
    //                 (-(atFirst[1]) > -(atLater[1])) ||
    //                 ((atFirst[2]) > (atLater[2])) ||
    //                 (-(atFirst[3]) > -(atLater[3])))){
    //                     countBadMove++;
    //             }
    //             atFirst[0] = atLater[0];
    //             atFirst[1] = atLater[1];
    //             atFirst[2] = atLater[2];
    //             atFirst[3] = atLater[3];
    //             if (countBadMove >= howGoodTheSolutionIndex){
    //                 return false;
    //             } 
    //         }
    //         return true;
    //     }
    // }

    private boolean isAGoodMoveForLevel(char moveDirection, final PuzzleBoard boardCurrent, final ArrayList<Character> listMovements, int level){
        PuzzleBoard temp = new PuzzleBoard(boardCurrent);
        Integer[] atFirst = boardCurrent.valueDisplacement();
        temp.move(moveDirection);
        Integer[] atLater = temp.valueDisplacement();

        if ((atFirst[0] > atLater[0]) ||
            (-atFirst[1] > -(atLater[1])) ||
            (atFirst[2] > (atLater[2])) ||
            (-atFirst[3] > -(atLater[3]))){
                return true;
        }
        else{
            int countBadMove = 0;
            // check if there is a bad move once before, if so, it should return false
            temp = new PuzzleBoard(this);
            atFirst = boardCurrent.valueDisplacementLevel(level);
            for (int i = 0; i < listMovements.size(); i++){
                temp.move(listMovements.get(i));
                atLater = temp.valueDisplacementLevel(level);
                if (!((atFirst[0]) > atLater[0] ||
                    (-(atFirst[1]) > -(atLater[1])) ||
                    ((atFirst[2]) > (atLater[2])) ||
                    (-(atFirst[3]) > -(atLater[3])))){
                        countBadMove++;
                }
                atFirst[0] = atLater[0];
                atFirst[1] = atLater[1];
                atFirst[2] = atLater[2];
                atFirst[3] = atLater[3];
                
                if (countBadMove > howGoodTheSolutionIndex){
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

    public void solution(){
        PuzzleBoard initBoard = new PuzzleBoard(this);
        ArrayList<BFS_SolveWithInformedSearch> listTemp = new ArrayList<BFS_SolveWithInformedSearch>(); 
        BFS_SolveWithInformedSearch solvingBoard = new BFS_SolveWithInformedSearch(this);
        
        solvingBoard = solvingBoard.rootGraphSolution('N', 0, listTemp);
        while (solvingBoard == null){
            howGoodTheSolutionIndex++;
            solvingBoard = new BFS_SolveWithInformedSearch(this);
            solvingBoard = solvingBoard.rootGraphSolution('N', 0, listTemp);
        }
        listTemp.add(solvingBoard);

        for (int level = 0; level < this.getSize() - 1; level++){
            howGoodTheSolutionIndex = 3;
            while(!solvingBoard.isSolvedLevel(level)){
                //System.out.println(solvingBoard.toMatrix());
                //System.out.println(solvingBoard.toListMoveString());
                BFS_SolveWithInformedSearch tempSolvingBoard = new BFS_SolveWithInformedSearch(solvingBoard);
                BFS_SolveWithInformedSearch temp = tempSolvingBoard.rootGraphSolution(tempSolvingBoard.getLastMovement(), level, listTemp);
                if (temp != null){
                    solvingBoard = temp;
                    listTemp.add(solvingBoard);
                }else{
                    howGoodTheSolutionIndex++;
                }
                
                
                //System.out.println("-------------------");
            }
            
             
            //System.out.println("Done Level " + level + " -------------------------------------");
        }
        //System.out.println(solvingBoard.isSolved());
       // System.out.println(solvingBoard.toMatrix());
        //System.out.println(solvingBoard.toListMoveString());
        
        initBoard.outputSolutionFile(solvingBoard.getListMovements());

        //return "Done";
    }
    
    private boolean isSameSubGraphOnThatLevel(BFS_SolveWithInformedSearch subGraph, int level){
        for (int i = 0; i < this.getSize(); i++){
            for (int j = 0; j < this.getSize(); j++){
                if (this.getPuzzle()[i][j] != subGraph.getPuzzle()[i][j]){
                    return false;
                }
            }
        }

        return true;
    }

    private boolean hasCircleMove(final BFS_SolveWithInformedSearch subGraph){
        {
            if (subGraph.getListMovements().size() < 12) return false;
            ArrayList<Character> list = new ArrayList<Character>(); 

            list.add(subGraph.getListMovements().get(subGraph.getListMovements().size() - 1));
            list.add(subGraph.getListMovements().get(subGraph.getListMovements().size()  - 2));
            list.add(subGraph.getListMovements().get(subGraph.getListMovements().size()  - 3));
            list.add(subGraph.getListMovements().get(subGraph.getListMovements().size()  - 4));
            int sumVertical = 0;
            int sumHorizontal = 0;
            
            for (int listIndex = 0; listIndex < list.size(); listIndex++){
                if (list.get(listIndex) == 'U'){
                    sumVertical--;
                }
                if (list.get(listIndex) == 'D'){
                    sumVertical++;
                }
                if (list.get(listIndex) == 'L'){
                    sumHorizontal--;
                }
                if (list.get(listIndex) == 'R'){
                    sumHorizontal++;
                }
            }

            if (sumHorizontal == 0 && sumVertical == 0){
                return true;
            }


            list = new ArrayList<Character>(); 
            list.add(subGraph.getListMovements().get(subGraph.getListMovements().size()  - 5));
            list.add(subGraph.getListMovements().get(subGraph.getListMovements().size()  - 6));
            list.add(subGraph.getListMovements().get(subGraph.getListMovements().size()  - 7));
            list.add(subGraph.getListMovements().get(subGraph.getListMovements().size()  - 8));
            sumVertical = 0;
            sumHorizontal = 0;
            
            for (int listIndex = 0; listIndex < list.size(); listIndex++){
                if (list.get(listIndex) == 'U'){
                    sumVertical--;
                }
                if (list.get(listIndex) == 'D'){
                    sumVertical++;
                }
                if (list.get(listIndex) == 'L'){
                    sumHorizontal--;
                }
                if (list.get(listIndex) == 'R'){
                    sumHorizontal++;
                }
            }

            if (sumHorizontal == 0 && sumVertical == 0){
                return true;
            }


            list = new ArrayList<Character>(); 
            list.add(subGraph.getListMovements().get(subGraph.getListMovements().size()  - 9));
            list.add(subGraph.getListMovements().get(subGraph.getListMovements().size()  - 10));
            list.add(subGraph.getListMovements().get(subGraph.getListMovements().size() - 11));
            list.add(subGraph.getListMovements().get(subGraph.getListMovements().size()  - 12));
            sumVertical = 0;
            sumHorizontal = 0;
            
            for (int listIndex = 0; listIndex < list.size(); listIndex++){
                if (list.get(listIndex) == 'U'){
                    sumVertical--;
                }
                if (list.get(listIndex) == 'D'){
                    sumVertical++;
                }
                if (list.get(listIndex) == 'L'){
                    sumHorizontal--;
                }
                if (list.get(listIndex) == 'R'){
                    sumHorizontal++;
                }
            }

            if (sumHorizontal == 0 && sumVertical == 0){
                return true;
            }

                
            return false;
        }
    }

    private boolean hasCycle(final BFS_SolveWithInformedSearch subGraph, final ArrayList<BFS_SolveWithInformedSearch> listSubGraphBefore, int level){
        for (int i = listSubGraphBefore.size() - 1; i >= 0; i--){
            if (subGraph.isSameSubGraphOnThatLevel(listSubGraphBefore.get(i), level)){
                return true;
            }
        }
        return false;
    }

    public BFS_SolveWithInformedSearch rootGraphSolution(char lastMoveDirection, int level, final ArrayList<BFS_SolveWithInformedSearch> listSubGraphBefore){
        Queue<Long> queue = new PriorityQueue<Long>();
        //this.finalize();;
        //long count = 0;
        // the order is Up Down Left Right // N is for skipping but still add into queue 
        final long UP = 0;
        final long DOWN = 1;
        final long LEFT = 2;
        final long RIGHT = 3;
        //int MAX_STEP = maxLevelThatVariableCanHandle();
        // init the 1st-level of tree
        if (super.hasUp(level) && lastMoveDirection != BFS_SolveWithInformedSearch.DOWN){
            queue.add(UP);
        }
        if (super.hasDown(level) && lastMoveDirection != BFS_SolveWithInformedSearch.UP){
            queue.add(DOWN);
        }
        if (super.hasLeft(level) && lastMoveDirection != BFS_SolveWithInformedSearch.RIGHT){
            queue.add(LEFT);
        }
        if (super.hasRight(level) && lastMoveDirection != BFS_SolveWithInformedSearch.LEFT){
            queue.add(RIGHT);
        }
        
        ArrayList<BFS_SolveWithInformedSearch> subGraph = new ArrayList<BFS_SolveWithInformedSearch>();

        while(!queue.isEmpty()){ //
            long popQueue = queue.poll();
            ArrayList<Character> listMovements = new ArrayList<Character>();
            
            listMovements = getPath(popQueue);
            PuzzleBoard tempPuzzleBoard = new PuzzleBoard(this);
            //System.out.println("" + listMovements);
            if (listMovements.size() == levelBFSBeforeSubGraph + 1) break;
            if (isSolution(listMovements, tempPuzzleBoard, level)){
                //System.out.println("Solved: " + listMovements);
                BFS_SolveWithInformedSearch temp = bfs_SolveWithInformedSearch(listMovements);
                return temp;
            }
            if (listMovements.size() == levelBFSBeforeSubGraph){
                BFS_SolveWithInformedSearch temp = bfs_SolveWithInformedSearch(listMovements);
                subGraph.add(temp);
            }
            else{
                Character lastMovements = listMovements.get(listMovements.size() - 1);
                long orderOfFirstChild = orderOfFirstChildOf(popQueue);
                //System.out.println("The order: " + popQueue + " " );
                //detect cycle
                if (lastMovements == 'U'){
                    if(tempPuzzleBoard.hasUp(level) && isAGoodMoveForLevel(this.UP, tempPuzzleBoard, listMovements, level)){
                        queue.add(orderOfFirstChild + 0);
                    }
                    if(tempPuzzleBoard.hasLeft(level) && isAGoodMoveForLevel(this.LEFT, tempPuzzleBoard, listMovements, level)){
                        queue.add(orderOfFirstChild + 1);
                    }
                    if(tempPuzzleBoard.hasRight(level) && isAGoodMoveForLevel(this.RIGHT, tempPuzzleBoard, listMovements, level)){
                        queue.add(orderOfFirstChild + 2);
                    }
                    continue;
                }
                if (lastMovements == 'D'){
                    if(tempPuzzleBoard.hasDown(level) && isAGoodMoveForLevel(this.DOWN, tempPuzzleBoard, listMovements, level)){
                        queue.add(orderOfFirstChild + 0);
                    }
                    if(tempPuzzleBoard.hasLeft(level) && isAGoodMoveForLevel(this.LEFT, tempPuzzleBoard, listMovements, level)){
                        queue.add(orderOfFirstChild + 1);
                    }
                    if(tempPuzzleBoard.hasRight(level) && isAGoodMoveForLevel(this.RIGHT, tempPuzzleBoard, listMovements, level)){
                        queue.add(orderOfFirstChild + 2);
                    }
                    continue;
                }
                if (lastMovements == 'L'){
                    if(tempPuzzleBoard.hasUp(level) && isAGoodMoveForLevel(this.UP, tempPuzzleBoard, listMovements, level)){
                        queue.add(orderOfFirstChild + 0);
                    }
                    if(tempPuzzleBoard.hasDown(level) && isAGoodMoveForLevel(this.DOWN, tempPuzzleBoard, listMovements, level)){
                        queue.add(orderOfFirstChild + 1);
                    }
                    if(tempPuzzleBoard.hasLeft(level) && isAGoodMoveForLevel(this.LEFT, tempPuzzleBoard, listMovements, level)){
                        queue.add(orderOfFirstChild + 2);
                    }
                    continue;
                }
                if (lastMovements == 'R'){
                    if(tempPuzzleBoard.hasUp(level) && isAGoodMoveForLevel(this.UP, tempPuzzleBoard, listMovements, level)){
                        queue.add(orderOfFirstChild + 0);
                    }
                    if(tempPuzzleBoard.hasDown(level) && isAGoodMoveForLevel(this.DOWN, tempPuzzleBoard, listMovements, level)){
                        queue.add(orderOfFirstChild + 1);
                    }
                    if(tempPuzzleBoard.hasRight(level) && isAGoodMoveForLevel(this.RIGHT, tempPuzzleBoard, listMovements, level)){
                        queue.add(orderOfFirstChild + 2);
                    }
                    continue;
                }     
            }
        }

        //System.out.println("There is " + subGraph.size() + " subgraphs");
        
        try{
            BFS_SolveWithInformedSearch bestSubGraph;
            if (subGraph.isEmpty()){throw new NoSubGraphException("No Subgraph");}
            bestSubGraph = new BFS_SolveWithInformedSearch(subGraph.get(0));
            //System.out.println("----------------------------------");
            for (int i = 1; i < subGraph.size(); i++){
                /// choose
                if (!bestSubGraph.isBetterThanForTheLevel(subGraph.get(i), level)){
                    if (!hasCycle(subGraph.get(i), listSubGraphBefore, level) && !hasCircleMove(subGraph.get(i))) /// make sure there is no duplicate  && hasCircleMove(subGraph.get(i))
                    {   
                        bestSubGraph = new BFS_SolveWithInformedSearch(subGraph.get(i));
                    }
                }
            }
            //hasCycle(bestSubGraph, initState);
            return bestSubGraph;
            //System.out.println(bestSubGraph.toMatrix());
            //System.out.println(bestSubGraph.listMoveString());
            //return bestSubGraph.subGraphSolution(bestSubGraph.getLastMovement());
        }catch(NoSubGraphException e){
            //System.out.println("subGraph is 0");
            return null;

        }
    }
}