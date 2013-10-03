
public class Tile {
	
	private STATE state;
	
	public static enum STATE 
	{
		IN_MAZE,
		NOT_IN_MAZE,
	}
	
	public Tile()
	{
		state = STATE.NOT_IN_MAZE;
	}
	
	public void set_State(STATE state) 
	{
		this.state = state;
	}
	
	public STATE get_State()
	{
		return this.state;
	}
}
