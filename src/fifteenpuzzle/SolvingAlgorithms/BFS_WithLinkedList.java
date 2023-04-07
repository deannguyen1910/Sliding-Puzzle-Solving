package fifteenpuzzle.SolvingAlgorithms;
import fifteenpuzzle.puzzle.PuzzleBoard;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.ArrayList;

public class BFS_WithLinkedList extends PuzzleBoard implements BFS{
    public BFS_WithLinkedList(String args0, String args1){
        super(args0, args1);
    }
    
    private class moveDirection{
        public static char UNKNOWN = 'N';
        public static char UP = 'U';
        public static char DOWN = 'D';
        public static char LEFT = 'L';
        public static char RIGHT = 'R';
        private char direction; // the data
        private moveDirection prev;
        private moveDirection[] next;
        private byte size_next;
        
        // public static moveDirection eliminate(moveDirection prev, moveDirection[] next, byte size_next){
        //     return new moveDirection();
        // }
        // public static moveDirection eliminate(char Direction){
        //     return new moveDirection(Direction);
        // }
        public moveDirection(){
            this.direction = UNKNOWN;
            this.prev = null;
            this.next = null;
            this.size_next = 0;
        }
        public moveDirection(char direction, moveDirection prev, moveDirection[] next, byte size_next){
            this.direction = direction;
            this.prev = prev;
            this.next = next;
            this.size_next = size_next;
        }
        public moveDirection root(moveDirection[] next, byte size_next){
            return new moveDirection(UNKNOWN, null, next, size_next);
        }
        public moveDirection up(moveDirection prev, moveDirection[] next, byte size_next){
            return new moveDirection(UP, prev, next, size_next);
        }
        public moveDirection down(moveDirection prev, moveDirection[] next, byte size_next){
            return new moveDirection(DOWN, prev, next, size_next);
        }
        public moveDirection left(moveDirection prev, moveDirection[] next, byte size_next){
            return new moveDirection(LEFT, prev, next, size_next);
        }
        public moveDirection right(moveDirection prev, moveDirection[] next, byte size_next){
            return new moveDirection(RIGHT, prev, next, size_next);
        }
        public moveDirection[] setNext(moveDirection[] next){
            this.next = next;
            return this.next;
        }
        public moveDirection[] getNext(){
            return next;
        }
        public boolean hasPrev(){
            return this.prev != null;
        }
        public moveDirection getPrev(){
            return this.prev;
        }
        public char getDirection(){
            return direction;
        }
        public char getValue(){
            return this.direction;
        }
    }

    public String solution(){
        Queue<moveDirection> queue = new PriorityQueue<moveDirection>();
        // Queue<moveDirection> saveQueue = new PriorityQueue<moveDirection>();
        byte size_next = super.numberOfAvailableDirection();
        byte zero = 0;
        
        moveDirection[] tempNext = new moveDirection[size_next];
        //moveDirection root = new moveDirection().root(tempNext, size_next);
        byte count = 0;
        if (this.hasUp()){
            tempNext[count] = new moveDirection(moveDirection.UP, null, null, zero);
            queue.add(tempNext[count]);
            count++;
        }
        if (this.hasDown()){
            tempNext[count] = new moveDirection(moveDirection.DOWN, null, null, zero);
            queue.add(tempNext[count]);
            count++;
        }
        if (this.hasLeft()){
            tempNext[count] = new moveDirection(moveDirection.LEFT, null, null, zero);
            queue.add(tempNext[count]);
            count++;
        }
        if (this.hasRight()){
            tempNext[count] = new moveDirection(moveDirection.RIGHT, null, null, zero);
            queue.add(tempNext[count]);
            count++;
        }
        //root = new moveDirection(moveDirection.UNKNOWN, null, tempNext, size_next);
        



        boolean hasSolution = false;
        moveDirection currentNode;
        do{
            currentNode = queue.poll();
            moveDirection tempTraverse = currentNode;
            
            ArrayList<Character> listMovements = new ArrayList<>();
            if (tempTraverse != null){
                listMovements.add(0, tempTraverse.direction);
            }
            while(tempTraverse.hasPrev()){
                tempTraverse = tempTraverse.getPrev();
                listMovements.add(0, tempTraverse.direction);   
            }
            
            // check the boolean state that there is any is solve. Meanwhile, the tempPuzzleBoard reach to the current state.
            PuzzleBoard tempPuzzleBoard = new PuzzleBoard(this);
            if (isSolution(listMovements, tempPuzzleBoard)){
                return listMovements.toString();
                
            }
            size_next = tempPuzzleBoard.numberOfAvailableDirection();
            moveDirection[] temp = new moveDirection[size_next];
            count = 0;
            if (currentNode.getValue() != moveDirection.DOWN && tempPuzzleBoard.hasUp()){
                tempNext[count] = new moveDirection(moveDirection.UP, currentNode, null, zero);
                queue.add(tempNext[count]);
                count++;
            }
            if (tempPuzzleBoard.hasDown()){
                tempNext[count] = new moveDirection(moveDirection.UP, currentNode, null, zero);
                queue.add(tempNext[count]);
                count++;
            }
            if (tempPuzzleBoard.hasLeft()){
                tempNext[count] = new moveDirection(moveDirection.UP, currentNode, null, zero);
                queue.add(tempNext[count]);
                count++;
            }
            if (tempPuzzleBoard.hasRight()){
                tempNext[count] = new moveDirection(moveDirection.UP, currentNode, null, zero);
                queue.add(tempNext[count]);
                count++;
            }
        }while(!hasSolution);
        return "Error";
    }
}
