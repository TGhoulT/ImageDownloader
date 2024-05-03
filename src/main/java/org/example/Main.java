package org.example;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        String url = "https://skillbox.ru/";
        try {
            ImageUrlExtractor imageUrlExtractor = new ImageUrlExtractor(url);
            Set<String> imageLinks = imageUrlExtractor.getImageUrls();

            ImageDownloader imageDownloader = new ImageDownloader();
            createImagesFolder(); // Создание папки "images", если ее нет
            int number = 1;
            for (String link : imageLinks) {
                try {
                    String extension = link.replaceAll("^.+\\.", "").replace("?.+$", "");
                    String filePath = "images/" + number++ + "." + extension;
                    imageDownloader.downloadImage(link, filePath);
                } catch (IOException e) {
                    System.out.println("Ошибка при скачивании изображения: " + e.getMessage());
                }
            }

            System.out.println("Ссылки на изображения:");
            for (String link : imageLinks) {
                System.out.println(link);
            }
        } catch (IOException e) {
            e.getMessage();
        }
    }

    //если не существует папки images то создаём её
    private static void createImagesFolder() {
        File folder = new File("images");
        if (!folder.exists()) {
            if (folder.mkdir()) {
                System.out.println("Папка 'images' успешно создана.");
            } else {
                System.out.println("Не удалось создать папку 'images'.");
            }
        }
    }
}
