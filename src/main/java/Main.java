import implementation.ZIPLibrary;
import implementations.SimpleAesLibrary;
import intefaces.IZIPLibrary;
import interfaces.AesLibrary;
import org.eclipse.jetty.http.HttpStatus;

import static spark.Spark.post;

/**
 * Created by mariusz on 03.12.16.
 */

public class Main {

    private static IZIPLibrary zipLibrary = new ZIPLibrary();
    private static AesLibrary aesLibrary = new SimpleAesLibrary();

    public static void main(String[] args) {
        post("/file/ziper/:source/:destination/:zipFileName/:password", (req, res) -> {
            try {
                String source = ":source";
                String destination = ":destination";
                String zipFileName = ":zipFileName";
                String password = ":password";
                zipLibrary.compress(source, destination, zipFileName, password);
                return "OK";
            } catch (Exception ex) {
                res.status(HttpStatus.BAD_REQUEST_400);
                return ex.getMessage();
            }
        }, new JsonTransformer());

        post("/file/unziper/", (req, res) -> {
            return null;
        });

        post("/file/downloader/", (req, res) -> {
            return null;
        });

        post("/file/encryptor/:fileInputName/:fileOutputName/:key", (req, res) -> {
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

        post("/file/descriptor/:fileInputName/:fileOutputName/:key", (req, res) -> {
            try {
                String fileInputNameParam = ":fileInputName";
                String fileOutputNameParam = req.params(":fileOutputName");
                String key = req.params(":key");
                aesLibrary.decrypt(req.params(fileInputNameParam), fileOutputNameParam, key);
                return "Ok";
            } catch (Exception ex) {
                res.status(HttpStatus.BAD_REQUEST_400);
                return ex.getMessage();
            }
        }, new JsonTransformer());

        post("/file/calculator", (req, res) -> {
            return null;
        });
    }
}
