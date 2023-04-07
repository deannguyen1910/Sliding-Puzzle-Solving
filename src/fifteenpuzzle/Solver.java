package fifteenpuzzle;
import fifteenpuzzle.SolvingAlgorithms.BFS_SolveWithLowerHeapUse;
import fifteenpuzzle.puzzle.PuzzleBoard;
import java.lang.invoke.MethodHandles;
import fifteenpuzzle.SolvingAlgorithms.*;

public class Solver extends BFS_SolveWithInformedSearch{
	public Solver(String args0, String args1) {
		super(args0, args1);
	}
	void Solve(){
		super.solution();
	}
	public static void main(String[] args) {
//		System.out.println("number of arguments: " + args.length);
//		for (int i = 0; i < args.length; i++) {
//			System.out.println(args[i]);
//		}

		if (args.length < 2) {
			System.out.println("File names are not specified");
			System.out.println("usage: java " + MethodHandles.lookup().lookupClass().getName() + " input_file output_file");
			return;
		}

		Solver a = new Solver(args[0], args[1]);

		//a.toString();
		System.out.println(a.solution());
		//a.BFS_SolveWithLowerHeapUse();
		//System.out.println(Solver.orderOfFirstChildOf(7));
		//System.out.println(maxLevelThatVariableCanHandle());
		// TODO
		//PuzzleBoard puzzle = new PuzzleBoard(args[0], args[1]);
		//System.out.println(puzzle.toString());
		//puzzle.solvePuzzle();
		// System.out.println("After move: \n" + puzzle.toString());
		//System.out.println(PuzzleBoard.getPath(19));
		
		//File input = new File(args[0]);
		// solve...
		//File output = new File(args[1]);

	}
}