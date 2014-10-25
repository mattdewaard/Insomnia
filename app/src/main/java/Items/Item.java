package Items;
import model.Attributes;

public class Item {
	private String id;
	private String name;
	private String type;
	private int price;
	
	public Item(String ID, String NAME, String TYPE, int PRICE){
		this.id = ID;
		this.name = NAME;
		this.type = TYPE;
		this.price = PRICE;
	}
	
	public String getName() {return name;}
	public String getType() {return type;}
	public int getPrice() {return price;}
	public String getId() {return id;}
}