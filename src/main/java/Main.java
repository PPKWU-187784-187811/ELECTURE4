import implementation.ZIPLibrary;
import implementations.SimpleAesLibrary;
import implementations.SimpleChecksumCalculator;
import intefaces.IZIPLibrary;
import interfaces.AesLibrary;
import interfaces.ChecksumCalculator;
import org.eclipse.jetty.http.HttpStatus;

import static spark.Spark.post;

/**
 * Created by mariusz on 03.12.16.
 */

public class Main {

    private static IZIPLibrary zipLibrary = new ZIPLibrary();
    private static AesLibrary aesLibrary = new SimpleAesLibrary();
    private static ChecksumCalculator checksumCalculator = new SimpleChecksumCalculator();

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

        post("/file/unziper/:source/:destination/:password", (req, res) -> {
            try {
                String source = ":source";
                String destination = ":destination";
                String password = ":password";
                zipLibrary.decompress(source, destination, password);
                return "OK";
            } catch (Exception ex) {
                res.status(HttpStatus.BAD_REQUEST_400);
                return ex.getMessage();
            }
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

        post("/file/calculator/:fileInputName/:algorithm", (req, res) -> {
            try {
                String fileInputNameParam = ":fileInputName";
                String algorithmParam = ":algorithm";
                String sum = checksumCalculator.calculate(fileInputNameParam, algorithmParam);
                return new ChecksumDto(sum);
            } catch (Exception ex) {
                res.status(HttpStatus.BAD_REQUEST_400);
                return ex.getMessage();
            }
        }, new JsonTransformer());
    }
}
