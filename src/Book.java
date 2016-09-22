public class Book {
    private String name;
    private String genre;
    private String author;
    private int date;
    private String publisher;
    private String isbn;
    private int pages = 500;
    private int imgNumber = -1;

    public Book(String name, String genre, String author, int pages) {
        this.name = name;
        this.genre = genre;
        this.author = author;
        this.pages = pages;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setImgNumber(int imgNumber) {
        this.imgNumber = imgNumber;
    }

    public void printBook() {
        if (name != null && genre != null && author != null) {
            System.out.println("Название: " + name);
            System.out.println("Жанр: " + genre);
            System.out.println("Автор: " + author);
            System.out.println("Страниц: " + pages);
        } else {
            System.out.println("Incorrect parsing");
            return;
        }

        if (publisher != null && publisher != "") System.out.println("Издательство: " + publisher);
        if (date != 0) System.out.println("Год: " + date);
        if (isbn != null && isbn != "") System.out.println("ISBN: " + isbn);
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public String getAuthor() {
        return author;
    }

    public int getDate() {
        return date;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getPages() {
        return pages;
    }

    public int getImgNumber() {
        return imgNumber;
    }
}
