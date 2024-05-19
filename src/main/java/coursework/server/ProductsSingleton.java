package coursework.server;

public class ProductsSingleton {
    private static Products instance;
    private ProductsSingleton(){

    }
    public static Products getInstance(){
        if(instance == null)
        {
            instance = new Products();
        }
        return instance;
    }


}