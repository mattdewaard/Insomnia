package Items;

public class UpgradeStone extends Item {
    private int count;
    private String rarity;

    public UpgradeStone(String id, String n, String t, int p) {
        super(id, n, t, p);
        this.count = 1;
        switch(id.charAt(0)){
            case ('u'):
                rarity = "Uncommon";
                break;
            case ('r'):
                rarity = "Rare";
                break;
            case ('l'):
                rarity = "Legendary";
                break;
            case ('a'):
                rarity = "Ancient";
                break;
            default:
                rarity = "Common";
                break;
        }
    }

    public String getRarity() {
        return this.rarity;
    }

    public int getCount() { return count;}

    public void setCount(int c) {
        count = c;
    }

    public void increaseCount() {
        count++;
    }

    public boolean decreaseCount() {
        count--;
        if (count == 0) return false;
        else return true;
    }
}