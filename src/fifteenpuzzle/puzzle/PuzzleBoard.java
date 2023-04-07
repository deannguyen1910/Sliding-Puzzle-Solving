package fifteenpuzzle.puzzle;

import java.io.File;
import java.io.FileNotFoundException;
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
                System.out.println("Size is: " + size);
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
                        System.out.println("current_x: " + current_x + ", current_y: " + current_y);
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
    public void outputSolutionFile(){

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

    protected boolean isSolution(ArrayList<Character> listMovements, PuzzleBoard tempPuzzleBoard){
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

        return tempPuzzleBoard.isSolved();
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

    private String availableMove(){
        String moves = "";
        if (hasRight()){
            moves += "R";
        }
        if (hasUp()){
            moves += "U";
        }
        if (hasLeft()){
            moves += "L";
        }
        if (hasDown()){
            moves += "D";
        }
        return moves; 
    }

    private boolean isSolved(){
        for(int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                if (puzzle[i][j] != (j + 1) + size * i){
                        return false;
                }
            }
        }
        return true;
    }

    public String listMoveString(){
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