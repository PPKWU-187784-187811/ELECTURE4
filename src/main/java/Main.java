import implementation.HTTPLibrary;
import implementation.ZIPLibrary;
import implementations.SimpleAesLibrary;
import implementations.SimpleChecksumCalculator;
import intefaces.IHTTPLibrary;
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
    private static IHTTPLibrary httpLibrary = new HTTPLibrary();
    private static AesLibrary aesLibrary = new SimpleAesLibrary();
    private static ChecksumCalculator checksumCalculator = new SimpleChecksumCalculator();

    public static void main(String[] args) {
        post("/file/ziper/:source/:destination/:zipFileName/:password", (req, res) -> {
            try {
                String source = req.params(":source");
                String destination = req.params(":destination");
                String zipFileName = req.params(":zipFileName");
                String password = req.params(":password");
                zipLibrary.compress(source, destination, zipFileName, password);
                return "OK";
            } catch (Exception ex) {
                res.status(HttpStatus.BAD_REQUEST_400);
                return ex.getMessage();
            }
        }, new JsonTransformer());

        post("/file/unziper/:source/:destination/:password", (req, res) -> {
            try {
                String source = req.params(":source");
                String destination = req.params(":destination");
                String password = req.params(":password");
                zipLibrary.decompress(source, destination, password);
                return "OK";
            } catch (Exception ex) {
                res.status(HttpStatus.BAD_REQUEST_400);
                return ex.getMessage();
            }
        }, new JsonTransformer());

        post("/file/downloader/:url/:file", (req, res) -> {
            try {
                String url = req.params(":url");
                String file = req.params(":file");
                httpLibrary.downloadFile(url, file);
                return "OK";
            } catch (Exception ex) {
                res.status(HttpStatus.BAD_REQUEST_400);
                return ex.getMessage();
            }
        }, new JsonTransformer());

        post("/file/encryptor/:fileInputName/:fileOutputName/:key", (req, res) -> {
            try {
                String fileInputNameParam = req.params(":fileInputName");
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
                String fileInputNameParam = req.params(":fileInputName");
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
                String fileInputNameParam = req.params(":fileInputName");
                String algorithmParam = req.params(":algorithm");
                String sum = checksumCalculator.calculate(fileInputNameParam, algorithmParam);
                return new ChecksumDto(sum);
            } catch (Exception ex) {
                res.status(HttpStatus.BAD_REQUEST_400);
                return ex.getMessage();
            }
        }, new JsonTransformer());
    }
}
