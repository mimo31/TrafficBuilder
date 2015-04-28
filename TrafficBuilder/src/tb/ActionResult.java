package tb;

public class ActionResult {
	
	public boolean close;
	public int closeId;
	public boolean repaint;
	
	public ActionResult(boolean close, int closeId, boolean repaint){
		this.close = close;
		this.closeId = closeId;
		this.repaint = repaint;
	}
	
}
