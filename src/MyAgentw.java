import java.util.Random;

public  class MyAgentw extends Agent{
	
	Random rnd = new Random();
	int myColor;
	
	public MyAgentw(World world){
		super(world);
		myColor = World.WHITE_PLAYER;
	}
	
	
	// skore daneho stavu je rovne poctu policok danej farby
	public int heuristics(int[][] plocha, int playerColor){
		int ret = 0;
		for (int r = 0; r < plocha.length; r++){
			for (int c = 0; c < plocha[r].length; c++){
				if (plocha[r][c] == playerColor){
					ret++;
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
			return heuristics(plocha, playerColor);
		}
		
		if (this.myColor == playerColor){
			for (int i = 0;i < possibleMoves.length; i++){
				alpha = Math.max(
							alpha, 
							alphabeta(
									world.getResultingState(
											plocha,
											possibleMoves[i], 
											playerColor
									),
									depth - 1,
									alpha, 
									beta,
									getOppositePlayerColor(playerColor)
							)
						);
				if (beta <= alpha) break;
			}
			return alpha;
		}
		else{
			for (int i = 0;i < possibleMoves.length; i++){
				beta = Math.min(
							beta, 
							alphabeta(
									world.getResultingState(
											plocha,
											possibleMoves[i], 
											playerColor
									),
									depth - 1,
									alpha, 
									beta,
									getOppositePlayerColor(playerColor)
							)
						);
				if (beta <= alpha) break;
			}
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
							4, 
							-Constants.INFTY, 
							Constants.INFTY, 
							myColor
						);
			if (score > maxScore){
				maxScore = score;
				maxTah = i;
			}
		}
		
		return maxTah;
		
		/* ZACIATOK MIESTA PRE VAS KOD */
		
		
		// mozne tahy bieleho hraca v stave hry "plocha"
		// int[] tahyBieleho = world.getPossibleMoves(plocha, World.WHITE_PLAYER);

		// mozne tahy cierneho hraca v stave hry "plocha"
		// int[] tahyCierneho = world.getPossibleMoves(plocha, World.BLACK_PLAYER);
		
		// stav hry po zahrati tahu "tah" hracom "player" v stave "plocha"		
		// int[][] novaPlocha = world.getResultingState(plocha, tah, player);
		
		//result=rnd.nextInt(tahy.length);	 
		
		/* KONIEC MIESTA PRE VAS KOD */
		//return result;
	}	
}