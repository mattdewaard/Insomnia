package Items;

public class Potion extends Item {
    int count;
    int amount;

    public Potion(String id, String n, String t, int p, int HP, int AP) {
        super(id, n, t, p);
        count = 1;
        amount = HP + AP;
    }

    public int getCount() { return count;}

    public int getAmount() {
        return amount;
    }

    public void increaseCount() {
        count++;
    }

    public boolean decreaseCount(){
        count--;
        if(count == 0) return false;
        else return true;
    }

    public void setCount(int count) {
        this.count = count;
    }
}