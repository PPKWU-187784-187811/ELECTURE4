import static spark.Spark.post;

/**
 * Created by mariusz on 03.12.16.
 */

public class Main {

    public static void main(String[] args){
        post("/file/zip/", (req, res) -> {
            return null;
        });

        post("/file/unzip/", (req, res) -> {
            return null;
        });

        post("/file/download", (req, res) -> {
            return null;
        });

        post("/file/encrypt/", (req, res) -> {
            return null;
        });

        post("/file/decrypt/", (req, res) -> {
            return null;
        });

        post("/file/calculate", (req, res) -> {
            return null;
        });
    }
}
