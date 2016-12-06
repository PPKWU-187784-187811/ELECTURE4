import implementations.SimpleAesLibrary;
import interfaces.AesLibrary;
import org.eclipse.jetty.http.HttpStatus;

import static spark.Spark.post;

/**
 * Created by mariusz on 03.12.16.
 */

public class Main {

    private static AesLibrary aesLibrary = new SimpleAesLibrary();

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

        post("/file/encrypter/:fileInputName/:fileOutputName/:key", (req, res) -> {
            try {
                String fileInputNameParam = ":fileInputName";
                String fileOutputNameParam = req.params(":fileOutputName");
                String key = req.params(":key");
                aesLibrary.encrypt(req.params(fileInputNameParam), fileOutputNameParam, key);
                return "Ok";
            } catch (Exception ex) {
                res.status(HttpStatus.BAD_REQUEST_400);
                return ex.getMessage();
            }
        }, new JsonTransformer());

        post("/file/descriptor/", (req, res) -> {
            return null;
        });

        post("/file/calculator", (req, res) -> {
            return null;
        });
    }
}
