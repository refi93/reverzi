import java.util.HashMap;
import java.util.Random;

public  class MyAgentb extends Agent{
	
	Random rnd = new Random();
	int myColor, cutoff, depthLimit;
	
	int[][] vyhodnost = {
			{15,1,10,10,10,10,1,15},
			{1,1,2,2,2,2,1,1},
			{10,2,4,4,4,4,2,10},
			{10,2,4,4,4,4,2,10},
			{10,2,4,4,4,4,2,10},
			{10,2,4,4,4,4,2,10},
			{1,1,2,2,2,2,1,1},
			{15,1,10,10,10,10,1,15}
	};
	
	
	HashMap<Position, int[]> nextMoveCache = new HashMap<Position, int[]>();
	
	public MyAgentb(World world){
		super(world);
		myColor = World.BLACK_PLAYER;
	}
	
	
	// skore daneho stavu je rovne rozdielu poctu mojich policok a policok supera
	public int heuristics(int[][] plocha){
		int ret = 0;
		for (int r = 0; r < plocha.length; r++){
			for (int c = 0; c < plocha[r].length; c++){
				if (plocha[r][c] == myColor){
					ret += vyhodnost[r][c];
				}
				else if (plocha[r][c] == getOppositePlayerColor(myColor)){
					ret -= vyhodnost[r][c];
				}
			}
		}
		
		return ret;
	}
	
	
	public int getOppositePlayerColor(int playerColor){
		if (playerColor == World.BLACK_PLAYER) return World.WHITE_PLAYER;
		return World.BLACK_PLAYER;
	}
	
	public int[] getPossibleMoves(int[][] plocha, int playerColor){
		Position pos = new Position(plocha, playerColor);
		int[] ret = nextMoveCache.get(pos);
		// if value is in cache, return it without asking the world
		if (ret != null){
			return ret;
		}
		
		// cache the new value
		ret = world.getPossibleMoves(plocha, playerColor);
		nextMoveCache.put(pos, ret);
		
		return ret;
	}
	
	
	public void sortPossibleMoves(int[] possibleMoves){
		for(int i = 0; i < possibleMoves.length; i++){
			for (int j = 0; j < i; j++){
				int r_i = possibleMoves[i] % 10;
				int c_i = possibleMoves[i] / 10;
				
				int r_j = possibleMoves[j] % 10;
				int c_j = possibleMoves[j] / 10;
				
				
				if (vyhodnost[r_i][c_i] > vyhodnost[r_j][c_j]){
					int pom = possibleMoves[i];
					possibleMoves[i] = possibleMoves[j];
					possibleMoves[j] = pom;
				}
			}
		}
	}
	
	public int alphabeta(int[][] plocha, int depth, int alpha, int beta, int playerColor){
		
		int[] possibleMoves;
		possibleMoves = world.getPossibleMoves(plocha, playerColor);
		
		sortPossibleMoves(possibleMoves);
		
		if (depth == 0 || possibleMoves.length == 0){
			return heuristics(plocha);
		}
		
		for (int i = 0;i < Math.min(possibleMoves.length, cutoff); i++){
			int alphabeta = alphabeta(
					world.getResultingState(
							plocha,
							possibleMoves[i], 
							playerColor
					),
					depth - 1,
					alpha, 
					beta,
					getOppositePlayerColor(playerColor)
			);
			
			if (playerColor == myColor){
				alpha = Math.max(alpha, alphabeta);
			}
			else{
				beta = Math.min(beta, alphabeta);
			}
			
			if (beta <= alpha) break;
		}
		
		if (playerColor == myColor) {
			return alpha;
		}
		else{
			return beta;
		}
	}
	
	public int act(int plocha[][], int[] tahy, int timeLimit){
		if (timeLimit <= 40) {
			cutoff = 2;
			depthLimit = 3;
		}
		else if (timeLimit < 100) {
			cutoff = 5;
			depthLimit = 3;
		}
		else if (timeLimit < 300) {
			cutoff = 8;
			depthLimit = 4;
		}
		else if (timeLimit < 700) {
			cutoff = 100;
			depthLimit = 4;
		}
		else {
			cutoff = 100;
			depthLimit = 5;
		}
		
		int maxScore = -Constants.INFTY;
		int maxTah = 0;
		
		// pokusime sa zahrat jednotlive tahy a vratime ten tah, pre ktory nam heuristika vrati najlepsie skore
		for (int i = 0;i < tahy.length; i++){
			int score = alphabeta(
							world.getResultingState(
									plocha, 
									tahy[i], 
									this.myColor
							), 
							depthLimit, 
							-Constants.INFTY, 
							Constants.INFTY, 
							getOppositePlayerColor(myColor)
						);
			if (score > maxScore){
				maxScore = score;
				maxTah = i;
			}
		}
		
		return maxTah;
	}	
}