import java.util.Random;

public  class MyAgentw extends Agent{
	
	Random rnd = new Random();
	int myColor;
	
	public MyAgentw(World world){
		super(world);
		myColor = World.WHITE_PLAYER;
	}
	
	
	// skore daneho stavu je rovne rozdielu poctu mojich policok a policok supera
	public int heuristics(int[][] plocha){
		int ret = 0;
		for (int r = 0; r < plocha.length; r++){
			for (int c = 0; c < plocha[r].length; c++){
				if (plocha[r][c] == myColor){
					ret++;
				}
				else if (plocha[r][c] == getOppositePlayerColor(myColor)){
					ret--;
				}
			}
		}
		
		return ret;
	}
	
	
	public int getOppositePlayerColor(int playerColor){
		if (playerColor == World.BLACK_PLAYER) return World.WHITE_PLAYER;
		return World.BLACK_PLAYER;
	}
	
	
	public int alphabeta(int[][] plocha, int depth, int alpha, int beta, int playerColor){
		
		int[] possibleMoves;
		possibleMoves = world.getPossibleMoves(plocha, playerColor);
		if (depth == 0 || possibleMoves.length == 0){
			return heuristics(plocha);
		}
		
		for (int i = 0;i < possibleMoves.length; i++){
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
		int result = 0;
		
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
							5, 
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