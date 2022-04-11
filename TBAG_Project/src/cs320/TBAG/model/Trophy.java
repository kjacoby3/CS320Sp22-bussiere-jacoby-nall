package cs320.TBAG.model;

public class Trophy extends Item{
	public Trophy(String name, int price) {
		this.name = name;
		this.price = price;
		type = "Trophy";
	}
	
	public int getPrice() {
		return (int) (price*.7);
	}
}
