import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Parser {
    private static String baseBookUrl = "http://loveread.ec/view_global.php?id=";
    private static String baseImgUrl = "http://loveread.ec/img/photo_books/%s.jpg";

    public static Book process(String bookNumber) throws IOException {
        String bookUrl = baseBookUrl + bookNumber;
        String imgUrl = String.format(baseImgUrl, bookNumber);
        String name = "";
        String genre = "";
        String author = "";
        int date = 0;
        String publisher = "";
        String isbn = "";
        int pages = 0;

        Document document = Jsoup.connect(bookUrl).get();


        //GENRE searching
        Elements genreRow = document.getElementsByClass("td_top_color");
        Element genreElement = genreRow.first().getElementsByTag("p").first();
        genre = genreElement.text().split(" ")[1];


        //OTHER INFO searching
        Map<String, String> map = getInfoMap(document);
        if (map.containsKey("Автор:")) author = map.get("Автор:");
        if (map.containsKey("Название:")) name = map.get("Название:");
        if (map.containsKey("Год:")) date = Integer.parseInt(map.get("Год:"));
        if (map.containsKey("Издательство:")) publisher = map.get("Издательство:");
        if (map.containsKey("Страниц:")) pages = Integer.parseInt(map.get("Страниц:"));
        if (map.containsKey("ISBN:")) isbn = map.get("ISBN:");

        Book book = new Book(name, genre, author, pages);
        book.setDate(date);
        book.setPublisher(publisher);
        book.setIsbn(isbn);

        if (ImgSaver.saveImg(bookNumber, imgUrl)) book.setImgNumber(Integer.parseInt(bookNumber));  //saving img and it's number

        return book;
    }

    private static Map getInfoMap(Document document) {
        Map<String, String> map = new HashMap<>();
        Element context = document.getElementsByClass("td_center_color").first();
        Element infoContainer = context.getElementsByTag("p").first();
        String[] info = infoContainer.text().split(" ");

        for (int i = 0; i < info.length; i++) {
            String key = "";
            String value = "";
            String tempKey = info[i];
            if (tempKey.equalsIgnoreCase("перевод")) continue;
            if (tempKey.endsWith(":")) {
                key = tempKey;
                for (int j = ++i; j < info.length; j++) {
                    String tempValue = info[j];
                    if (tempValue.endsWith(":") || tempValue.equalsIgnoreCase("перевод")) {
                        i = --j;
                        break;
                    }
                    value = value + " " + tempValue;
                    i = j;
                }
            }
            map.put(key.trim(), value.trim());
        }
        return map;
    }
}
