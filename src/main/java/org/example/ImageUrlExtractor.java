package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ImageUrlExtractor {
    private String url;

    public ImageUrlExtractor(String url) {
        this.url = url;
    }

    public Set<String> getImageUrls() throws IOException {
        Set<String> imageLinks = new HashSet<>();
        Document doc = Jsoup.connect(url).get();
        Elements elements = doc.select("img");
        for (Element image : elements) {
            String imageUrl = image.attr("abs:src");
            imageLinks.add(imageUrl);
        }
        return imageLinks;
    }
}
