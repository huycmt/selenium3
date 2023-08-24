package org.example.utils;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import lombok.SneakyThrows;
import org.openqa.selenium.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Set;
import java.util.stream.Collectors;

import static org.example.driver.DriverManager.driver;


public class FileUtils {

    @SneakyThrows
    public static String getMD5(String path) {
        HashCode md5 = Files.asByteSource(new File(path)).hash(Hashing.md5());
        byte[] md5Bytes = md5.asBytes();
        return md5Bytes.toString();
    }

    @SneakyThrows
    public static void deleteFile(String path) {
        File file = new File(path);
        file.delete();
    }

    public static void download(String urlString, String destinationFile, boolean isUseCookie) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        if (isUseCookie) {
            Set<Cookie> cookies = driver().getDriver().getWebDriver().manage().getCookies();
            String cookieString = cookies.stream().map(cookie -> String.format("%s=%s", cookie.getName(), cookie.getValue())).collect(Collectors.joining("; "));
            connection.setRequestProperty("Cookie", cookieString);
        }

        InputStream inputStream = connection.getInputStream();
        org.apache.commons.io.FileUtils.copyInputStreamToFile(inputStream, new File(destinationFile));
    }

    private static final Logger log = LoggerFactory.getLogger(FileUtils.class);
}
