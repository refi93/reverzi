import java.util.Arrays;


public class Position {
	
	public int[][] plocha;
	public int color;
	
	public Position(int[][] plocha, int color){
		
		this.plocha = new int[Constants.PLAN_HEIGHT][Constants.PLAN_WIDTH];
		for (int r = 0; r < plocha.length; r++){
			for (int c = 0; c < plocha[r].length; c++){
				this.plocha[r][c] = plocha[r][c];
			}
		}
		
		this.color = color;
	}
	
	 public boolean equals(Object obj) {
        if(obj != null && obj instanceof Position) {
            Position other = (Position)obj;
            return (Arrays.deepEquals(other.plocha,this.plocha) && (other.color == this.color));
        }
        return false;
	}
	
	public int hashCode(){
		int prime = 47;
		int ret = java.util.Arrays.deepHashCode(plocha);
		ret = ret * prime + color;
		return ret;
	}
}
