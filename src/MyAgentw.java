import java.util.Random;

public  class MyAgentw extends Agent{
	
	Random rnd = new Random();
	
	public MyAgentw(World world){
		super(world);
	}
		
	public int act(int plocha[][], int[] tahy, int timeLimit){
		int result = 0;
		/* ZACIATOK MIESTA PRE VAS KOD */
		
		
		// mozne tahy bieleho hraca v stave hry "plocha"
		// int[] tahyBieleho = world.getPossibleMoves(plocha, World.WHITE_PLAYER);

		// mozne tahy cierneho hraca v stave hry "plocha"
		// int[] tahyCierneho = world.getPossibleMoves(plocha, World.BLACK_PLAYER);
		
		// stav hry po zahrati tahu "tah" hracom "player" v stave "plocha"		
		// int[][] novaPlocha = world.getResultingState(plocha, tah, player);
		
		result=rnd.nextInt(tahy.length);	 
		
		/* KONIEC MIESTA PRE VAS KOD */
		return result;
	}	
}