package nau.coursework4.server;

public class ProductsStoreSingleton {
    private static Products instance;
    private ProductsStoreSingleton(){

    }
    public static Products getInstance(){
        if(instance == null)
        {
            instance = new Products();
        }
        return instance;
    }


}