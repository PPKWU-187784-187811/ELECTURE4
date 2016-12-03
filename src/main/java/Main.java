import static spark.Spark.get;

/**
 * Created by mariusz on 03.12.16.
 */
public class Main {

    public static void main(String[] args){
        get("/", (req, res) -> {
            return "Hello world!";
        });

    }
}
