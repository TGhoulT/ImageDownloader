package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException {
        String url = "https://skillbox.ru/";
        Document doc = Jsoup.connect(url).get();

        Set<String> imageLinks = new HashSet<>();

        /*System.out.println(doc);*/    //проверяем, получили ли мы исходный код сайта

        Elements elements = doc.select("img");

        /*elements.forEach(System.out::println);*/    //просмотр картинок в исходном коде с тегом img

        for (Element image : elements) { //получаем все элементы с тегом img и добавляем ссылки на эти изобр. в хэшсет
            String imageUrl = image.attr("abs:src");
            imageLinks.add(imageUrl);
        }

        int number = 1;

        for (String link : imageLinks) {
            try {
                String extension = link.replaceAll("^.+\\.", "").replace("?.+$", "");

                //формируем путь для сохранения файла
                String filePath = "images/" + number++ + "." + extension;

                //скачиваем файл и сохраняем его по указанному пути
                downloadImage(link, filePath);
            } catch (IOException e) {
                //пропускаем итерацию цикла при возникновении исключения (кириллица в ссылке)
                System.out.println("Ошибка при скачивании изображения: " + e.getMessage());
            }
        }

        //все ссылки, которые мы привели к нормальному виду через регулярку
        System.out.println("Ссылки на изображения:");
        for (String link : imageLinks) {
            System.out.println(link);
        }


    }

    private static void downloadImage(String link, String filePath) throws IOException {
        URLConnection connection = new URL(link).openConnection();
        try (InputStream inStream = connection.getInputStream();
             OutputStream outStream = new FileOutputStream(filePath)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
        }
    }
}