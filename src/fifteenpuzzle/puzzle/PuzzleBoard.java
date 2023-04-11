package fifteenpuzzle.puzzle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class PuzzleBoard {
    static final char UP = 'U';
    static final char DOWN = 'D';
    static final char LEFT = 'L';
    static final char RIGHT = 'R';
    
    private int [][] puzzle;
    private int size;
    private String inputFileName;
    private String outputFileName;
    private int current_x;
    private int current_y;      
    private ArrayList<Character> listMovements;

    public PuzzleBoard puzzleBoard(ArrayList<Character> newListMovements){
        PuzzleBoard newBoard = new PuzzleBoard(this);
        for (int i = 0; i < newListMovements.size(); i++){
            newBoard.listMovements.add(newListMovements.get(i));
            newBoard.move(newListMovements.get(i));
        }
        return newBoard;
    }

    public PuzzleBoard(String newInputFileName, String newOutputFileName){
        //System.out.println("Initialize the Puzzle board");
        inputFile(newInputFileName, newOutputFileName);
    }

    public PuzzleBoard(PuzzleBoard item){ //deep copy
        this.size = item.size;
        this.current_x = item.current_x;
        this.current_y = item.current_y;
        this.puzzle = new int[this.size][this.size];
        this.inputFileName = item.inputFileName;
        this.outputFileName = item.outputFileName;
        this.listMovements = new ArrayList<Character>();
        if (item.listMovements != null){
            for (int i = 0; i < item.listMovements.size(); i++){
                this.listMovements.add(item.listMovements.get(i));
            }
        }
        for (int i = 0; i < item.size; i++){
            for (int j = 0; j < item.size; j++){
                this.puzzle[i][j] = item.puzzle[i][j];
            }
        }
    }
    
    private static Integer parseIntContainSpace(String line){
        String ret;
        ret = new String(line);
        ret = ret.replace(" " ,"");
        if (ret == "") return 0;
        return Integer.parseInt(ret);
    }

    public void inputFile(String newInputFileName, String newOutputFileName){
        this.inputFileName = new String(newInputFileName);
        this.outputFileName = new String(newOutputFileName);
        File inputFile = new File(inputFileName);
        Scanner myReader;

        try{
            myReader = new Scanner(inputFile);

            if (myReader.hasNextLine()) { // get first line and parse it into int
                size = parseIntContainSpace(myReader.nextLine());
                //System.out.println("Size is: " + size);
                puzzle = new int[size][size];
            }
            else{
                myReader.close();
                throw new IllegalArgumentException();
            }

            for (int i = 0; i < size; i++){
                String tempLine;
                if (myReader.hasNextLine()){
                    tempLine = myReader.nextLine();
                }
                else{
                    myReader.close();
                    throw new IllegalArgumentException();
                }

                if (tempLine.length() != size * 3 - 1) {
                    myReader.close();
                    throw new IllegalArgumentException();
                }

                for (int j = 0; j < size; j++){
                    String num = new String(tempLine.substring(3 * j, 3 * j + 2));
                    int tempNum = parseIntContainSpace(num);
                    if (tempNum == 0) {
                        tempNum = size * size;
                        current_x = j;
                        current_y = i;
                        // System.out.println("current_x: " + current_x + ", current_y: " + current_y);
                    }
                    puzzle[i][j] = tempNum;
                }
            }

            myReader.close();
        }
        catch (FileNotFoundException e){
            System.out.println();
        }
        catch(Exception e){
            throw e;
        }
    }
    public void outputSolutionFile(ArrayList<Character> listMove){
        try{
            FileWriter outFile = new FileWriter(outputFileName);
            for (int i = 0; i < listMove.size(); i++){
                String temp = "";
                if (listMove.get(i) == 'U'){
                    int tempX = current_x;
                    int tempY = current_y;
                    this.up();
                    temp += puzzle[tempY][tempX];
                    temp += " ";
                    temp += 'U';
                    temp += '\n';
                    outFile.write(temp);
                    continue;

                }
                if (listMove.get(i) == 'D'){
                    int tempX = current_x;
                    int tempY = current_y;
                    this.down();
                    temp += puzzle[tempY][tempX];
                    temp += " ";
                    temp += 'D';
                    temp += '\n';
                    outFile.write(temp);
                    continue;
                }
                if (listMove.get(i) == 'L'){
                    int tempX = current_x;
                    int tempY = current_y;
                    this.left();
                    temp += puzzle[tempY][tempX];
                    temp += " ";
                    temp += 'L';
                    temp += '\n';
                    outFile.write(temp);
                    continue;

                }
                if (listMove.get(i) == 'R'){
                    int tempX = current_x;
                    int tempY = current_y;
                    this.right();
                    temp += puzzle[tempY][tempX];
                    temp += " ";
                    temp += 'R';
                    temp += '\n';
                    outFile.write(temp);
                    continue;

                }
            }
            outFile.close();
        }catch(IOException e){
            return;
        }
        
    }

    public boolean hasDown(){
        return current_y >= 1;
    }
    public boolean hasUp(){
        return (current_y < size - 1);
    }
    public boolean hasLeft(){
        return (current_x < size - 1);
    }
    public boolean hasRight(){
        return (current_x >= 1);
    }

    public boolean hasDown(int level){
        return current_y >= level + 1;
    }
    public boolean hasUp(int level){
        return (current_y < size - 1);
    }
    public boolean hasLeft(int level){
        return (current_x < size - 1);
    }
    public boolean hasRight(int level){
        return (current_x >= level + 1);
    }


    public void right(){
        if (!hasRight()){
            throw new IllegalArgumentException("Cannot move Right");
        }
        else{
            puzzle[current_y][current_x] = puzzle[current_y][current_x - 1];
            //System.out.println(puzzle[current_x][current_y] + " D");
            puzzle[current_y][current_x - 1] = (size) * size;
            current_x = current_x - 1;
            //System.out.println(this.toString());
            //this.valueDisplacement();
        }
    }
    public void left(){
        if (!hasLeft()){
            throw new IllegalArgumentException("Cannot move Left");
        }
        else{
            //System.out.println(predictMovements());
            puzzle[current_y][current_x] = puzzle[current_y][current_x + 1];
            //System.out.println(puzzle[current_x][current_y] + " U");
            puzzle[current_y][current_x + 1] = size * size;
            current_x = current_x + 1;
            //System.out.println(this.toString());
            //this.valueDisplacement();
        }
    }
    public void down(){
        if (!hasDown()){
            throw new IllegalArgumentException("Cannot move Down");
        }
        else{
            //System.out.println(predictMovements());
            puzzle[current_y][current_x] = puzzle[current_y - 1][current_x];
            //System.out.println(puzzle[current_y][current_x] + " R");
            puzzle[current_y - 1][current_x] = size * size;
            current_y = current_y - 1;
            //System.out.println(this.toString());
            //this.valueDisplacement();
        }
    }
    public void up(){
        if (!hasUp()){
            throw new IllegalArgumentException("Cannot move Up");
        }
        else{
            //System.out.println(predictMovements());
            puzzle[current_y][current_x] = puzzle[current_y + 1][current_x];
            //System.out.println(puzzle[current_y][current_x] + " L");
            puzzle[current_y + 1][current_x] = size * size;
            current_y = current_y + 1;
            //System.out.println(this.toString());
            //this.valueDisplacement();
        }
    }

    public void move(char direction){
        if (direction == UP){
            up();
        }
        if (direction == DOWN){
            down();
        }
        if (direction == LEFT){
            left();
        }
        if (direction == RIGHT){
            right();
        }
    }

    public void move(char direction, int level){
        if (direction == UP){
            up();
        }
        if (direction == DOWN){
            down();
        }
        if (direction == LEFT){
            left();
        }
        if (direction == RIGHT){
            right();
        }
    }

    protected boolean isSolution(ArrayList<Character> listMovements, PuzzleBoard tempPuzzleBoard, int level){
        for (int i = 0; i < listMovements.size(); i++){
            if (listMovements.get(i) == 'U' && tempPuzzleBoard.hasUp()){
                tempPuzzleBoard.up();
                continue;
            }
            if (listMovements.get(i) == 'D' && tempPuzzleBoard.hasDown()){
                tempPuzzleBoard.down();
                continue;
            }
            if (listMovements.get(i) == 'L' && tempPuzzleBoard.hasLeft()){
                tempPuzzleBoard.left();
                continue;
            }
            if(listMovements.get(i) == 'R' && tempPuzzleBoard.hasRight()){
                tempPuzzleBoard.right();
                continue;
            }
        }

        return tempPuzzleBoard.isSolvedLevel(level);
    }

    public byte numberOfAvailableDirection(){
        byte temp = 0;
        if (this.hasUp()){
            temp++;
        }
        if (this.hasDown()){
            temp++;
        }
        if (this.hasLeft()){
            temp++;
        }
        if (this.hasRight()){
            temp++;
        }

        return temp;
    }

    public Integer[] valueDisplacement(){
        int sumHorizontalPositive = 0;
        int sumHorizontalNegative = 0;
        int sumVerticalPositive = 0;
        int sumVerticalNegative = 0;

        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                //puzzle[i][j] != (j + 1) + size * i
                if (puzzle[i][j] == size * size) continue;
                int x = j;
                int y = i;
                int horizontalDisplacement = (puzzle[i][j] - 1) % size - x;
                if (horizontalDisplacement > 0){
                    sumHorizontalPositive += horizontalDisplacement;
                }
                else{
                    sumHorizontalNegative += horizontalDisplacement;
                }
                
                int verticalDisplacement = (puzzle[i][j] - 1) / size - y;
                if (verticalDisplacement > 0){
                    sumVerticalPositive += verticalDisplacement;
                }
                else{
                    sumVerticalNegative += verticalDisplacement;
                }

                //System.out.println("Value: " + puzzle[i][j] + " is at (" + x + ";" + y + ") ; Displacements: " + horizontalDisplacement + " " + verticalDisplacement);
            }
        }
        //System.out.println(sumHorizontalPositive + " " + sumHorizontalNegative + " " + sumVerticalPositive + " " + sumVerticalNegative);
        Integer[] displacementList = new Integer[4];
        displacementList[0] = sumHorizontalPositive;
        displacementList[1] = sumHorizontalNegative;
        displacementList[2] = sumVerticalPositive;
        displacementList[3] = sumVerticalNegative;
        return displacementList;
    }

    public Integer[] valueDisplacementLevel(int level){
        int sumHorizontalPositive = 0;
        int sumHorizontalNegative = 0;
        int sumVerticalPositive = 0;
        int sumVerticalNegative = 0;

        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                if (puzzle[i][j] == size * size) continue;
                int x = j;
                int y = i;
                
                if ((size * level + level < puzzle[i][j] && puzzle[i][j] <= level * size + size) 
                || (puzzle[i][j] % size == level + 1 && puzzle[i][j] > level * size)){
                    int horizontalDisplacement = (puzzle[i][j] - 1) % size - x;
                    //System.out.print("Number " + puzzle[i][j] + " :");
                    //System.out.print(horizontalDisplacement);
                    if (horizontalDisplacement > 0){
                        sumHorizontalPositive += horizontalDisplacement;
                    }
                    else{
                        sumHorizontalNegative += horizontalDisplacement;
                    }
                    
                    int verticalDisplacement = (puzzle[i][j] - 1) / size - y;
                    //System.out.println(verticalDisplacement);
                    if (verticalDisplacement > 0){
                        sumVerticalPositive += verticalDisplacement;
                    }
                    else{
                        sumVerticalNegative += verticalDisplacement;
                    }
                }
            }
        }

        Integer[] displacementList = new Integer[4];
        displacementList[0] = sumHorizontalPositive;
        displacementList[1] = sumHorizontalNegative;
        displacementList[2] = sumVerticalPositive;
        displacementList[3] = sumVerticalNegative;
        // for (int i = 0 ; i < 4; i++){
        //     System.out.print(displacementList[i] + " ");
        // }
        return displacementList;
    }

    public int countCorrectOnThatLevel(int level){
        int count = 0;
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                if (puzzle[i][j] == size * size) continue;
                int x = j;
                int y = i;
                
                if ((size * level + level < puzzle[i][j] && puzzle[i][j] <= level * size + size) 
                || (puzzle[i][j] % size == level + 1 && puzzle[i][j] > level * size)){
                    int horizontalDisplacement = (puzzle[i][j] - 1) % size - x;
                    //System.out.print("Number " + puzzle[i][j] + " :");
                    //System.out.print(horizontalDisplacement);
                    
                    
                    int verticalDisplacement = (puzzle[i][j] - 1) / size - y;
                    //System.out.println(verticalDisplacement);
                    if (horizontalDisplacement == 0 && verticalDisplacement == 0){
                        count++;
                    }
                }
            }
        }

        return count;
    }

    public boolean isBetterThan(PuzzleBoard item){
        Integer[] tempValueDisplacementThis = this.valueDisplacement();
        Integer[] tempValueDisplacementItem = item.valueDisplacement();
        int sumValueDisplacementItem = tempValueDisplacementItem[0] - tempValueDisplacementItem[1] + 
        tempValueDisplacementItem[2] - tempValueDisplacementItem[3]; 
        int sumValueDisplacementThis = tempValueDisplacementThis[0] - tempValueDisplacementThis[1] + 
        tempValueDisplacementThis[2] - tempValueDisplacementThis[3];
        if (sumValueDisplacementThis < sumValueDisplacementItem){
            return true;
        }else{
            if (sumValueDisplacementItem == sumValueDisplacementThis){
                double avgDisItem = sumValueDisplacementItem /4;
                double avgDisThis = sumValueDisplacementThis /4;
                if (
                Math.abs((double)tempValueDisplacementThis[0] - avgDisThis) + 
                Math.abs((double)-tempValueDisplacementThis[1] - avgDisThis) + 
                Math.abs((double)tempValueDisplacementThis[2] - avgDisThis) + 
                Math.abs((double)-tempValueDisplacementThis[3] - avgDisThis) 
                <=
                Math.abs((double)(tempValueDisplacementItem[0]) - avgDisItem) +
                Math.abs((double)-tempValueDisplacementItem[1] - avgDisItem) +
                Math.abs((double)tempValueDisplacementItem[2] - avgDisItem) +
                Math.abs((double)-tempValueDisplacementItem[3] - avgDisItem))
                {
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }
    }

    public boolean isBetterThanForTheLevel(PuzzleBoard item, int level){
        Integer[] tempValueDisplacementThis = this.valueDisplacementLevel(level);
        Integer[] tempValueDisplacementItem = item.valueDisplacementLevel(level);
        int sumValueDisplacementItem = tempValueDisplacementItem[0] - tempValueDisplacementItem[1] + 
        tempValueDisplacementItem[2] - tempValueDisplacementItem[3]; 
        int sumValueDisplacementThis = tempValueDisplacementThis[0] - tempValueDisplacementThis[1] + 
        tempValueDisplacementThis[2] - tempValueDisplacementThis[3];
        if (sumValueDisplacementThis < sumValueDisplacementItem){
            return true;
        }else{
            if (sumValueDisplacementItem == sumValueDisplacementThis){
                double avgDisItem = sumValueDisplacementItem /4;
                double avgDisThis = sumValueDisplacementThis /4;
                double stdeviationThis = Math.abs((double)tempValueDisplacementThis[0] - avgDisThis) + 
                Math.abs((double)-tempValueDisplacementThis[1] - avgDisThis) + 
                Math.abs((double)tempValueDisplacementThis[2] - avgDisThis) + 
                Math.abs((double)-tempValueDisplacementThis[3] - avgDisThis);

                double stdeviationItem = Math.abs((double)(tempValueDisplacementItem[0]) - avgDisItem) +
                Math.abs((double)-tempValueDisplacementItem[1] - avgDisItem) +
                Math.abs((double)tempValueDisplacementItem[2] - avgDisItem) +
                Math.abs((double)-tempValueDisplacementItem[3] - avgDisItem);
                
                if (stdeviationThis < stdeviationItem)
                {
                    return true;
                }else{
                    if (stdeviationItem == stdeviationThis){
                        if (this.countCorrectOnThatLevel(level) > item.countCorrectOnThatLevel(level)){
                            return true;
                        }else{
                            return false;
                        }
                    }else{
                        return false;
                    }
                }
            }else{
                return false;
            }
        }
    }

    // private String availableMove(){
    //     String moves = "";
    //     if (hasRight()){
    //         moves += "R";
    //     }
    //     if (hasUp()){
    //         moves += "U";
    //     }
    //     if (hasLeft()){
    //         moves += "L";
    //     }
    //     if (hasDown()){
    //         moves += "D";
    //     }
    //     return moves; 
    // }

    protected boolean isSolved(){
        for(int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                if (puzzle[i][j] != (j + 1) + size * i){
                        return false;
                }
            }
        }
        return true;
    }

    protected boolean isSolvedLevel(int level){
        for(int i = level; i < size; i++){
            if (puzzle[i][level] != (level + 1) + size * i){
                return false;
            }
            if (puzzle[level][i] != (i + 1) + size * level){
                return false;
            }
        }
        return true;
    }

    protected boolean isSame(PuzzleBoard item){
        for (int i = size - 1; i >= 0; i--){
            for (int j = size - 1; j >= 0; j--){
                if (this.puzzle[j][i] != item.puzzle[j][i]){
                    return false;
                }
            }
        }
        return true;
    }

    protected char getLastMovement(){
        return this.listMovements.get(this.listMovements.size() - 1);
    }

    protected ArrayList<Character> getListMovements(){
        return this.listMovements;
    }

    public int getSize(){
        return size;
    }

    protected PuzzleBoard getPuzzleBoard(){
        return this;
    }

    protected int[][] getPuzzle(){
        return this.puzzle;
    }

    public String toListMoveString(){
        String temp = "";
        for (int i = 0; i < listMovements.size(); i++){
            temp += listMovements.get(i) + " ";
        }

        return temp;
    }

    public String toMatrix(){
        String temp =""; // "The size is: " +  size + "\n";
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                if (puzzle[i][j] == size * size){
                    temp += "   "; 
                    continue;
                }
                if (puzzle[i][j] < 10){
                    temp += " " + puzzle[i][j] + " ";
                }
                else{
                    temp += puzzle[i][j] + " ";
                }
            }
            temp = temp.substring(0, temp.length());
            temp += "\n";
        }
        return temp;
    }
}