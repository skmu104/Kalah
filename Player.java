package kalah;

public class Player {
    // Class house and store although currently identical created to give possibility
    // of changes in future to either one with least cost.
    private Houses house[];
    private Stores stores;

    Player( int houses,int seeds,int storeSeeds){
        setHouse( new Houses[houses]);
        setStores(new Stores(storeSeeds));
        for (int i = 0; i < houses; i++){
            house[i] = new Houses(seeds);
        }
    }
    public int sumSeeds(){
        int sum = 0;
        for (int i = 0; i < house.length;i++){
            sum += house[i].getSeeds();
        }
        sum += stores.getSeeds();
        return sum;
    }

    public Houses[] getHouse() {
        return house;
    }

    public void setHouse(Houses[] house) {
        this.house = house;
    }

    public Stores getStores() {
        return stores;
    }

    public void setStores(Stores stores) {
        this.stores = stores;
    }
}
