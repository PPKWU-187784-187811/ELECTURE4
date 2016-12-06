import static spark.Spark.post;

/**
 * Created by mariusz on 03.12.16.
 */

public class Main {

    public static void main(String[] args){
        post("/file/ziper/", (req, res) -> {
            return null;
        });

        post("/file/unziper/", (req, res) -> {
            return null;
        });

        post("/file/downloader/", (req, res) -> {
            return null;
        });

        post("/file/encryptor/", (req, res) -> {
            return null;
        });

        post("/file/descriptor/", (req, res) -> {
            return null;
        });

        post("/file/calculator", (req, res) -> {
            return null;
        });
    }
}
