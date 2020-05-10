package Home.Controllers;

public class Book {
    //private variables
    private String bookID;
    private String bookTitle;
    private String bookCat;
    private String bookAuthor;
    private String bookPublisher;
    private String bookEdition;
    private int bookTotalAmt;
    private int bookAvlbAmt;
    private float bookPrice;
    private String bookPosition;


    //Constructor

    public Book( String ID, String Title,String Author, String Cat, String Pub,String Edi,  int TotalAmt ,int AvlbAmt, float Price)
    {
        bookID = ID;
        bookTitle = Title;
        bookCat = Cat;
        bookAuthor = Author;
        bookEdition = Edi;
        bookPublisher = Pub;
        bookTotalAmt = TotalAmt;
        bookAvlbAmt = AvlbAmt;
        bookPrice = Price;

    }
    public Book(){
        ID="";
        Title="";
        Author="";
        Category="";
        Publisher="";
        Edition="";
        Total_quantity=0;
        Available_quantity=0;
        Price=0;
        Position="";
    }

    public Book(String a, int b){
        if (b == 0) bookID = a;
        else bookTitle = a;

    }
    public Book(Book temp){
        if ( temp == null) return;
        bookID = temp.bookID;
        bookTitle = temp.bookTitle;
        bookCat = temp.bookCat;
        bookAuthor = temp.bookAuthor;
        bookEdition = temp.bookEdition;
        bookPublisher = temp.bookPublisher;
        bookTotalAmt = temp.bookTotalAmt;
        bookAvlbAmt = temp.bookAvlbAmt;
        bookPrice = temp.bookPrice;
    }
    public float getBookPrice() {
        return bookPrice;
    }

    public String getBookTitle() {
        return bookTitle;
    }
    public boolean CompareCat(String cat){
        return this.getBookCat().equals(cat);
    }
    public boolean CompareBook(Book book){
        return this.getBookID().equals(book.getBookID())
            && this.getBookTitle().equals(book.getBookTitle())
            && this.CompareCat(book.getBookCat())
            && this.getBookAuthor().equals(book.getBookAuthor())
            && this.getBookPublisher().equals(book.getBookPublisher())
            && this.getBookEdition().equals(book.getBookEdition())
            && this.getBookPrice()== book.getBookPrice()
            && this.getBookAvlbAmt() == book.getBookAvlbAmt();}
    public String getBookAuthor() {
        return bookAuthor;
    }

    public int getBookAvlbAmt() {
        return bookAvlbAmt;
    }

    public String getBookPosition() {
        return bookPosition;
    }

    public int getBookTotalAmt() {
        return bookTotalAmt;
    }

    public String getBookCat() {
        return bookCat;
    }

    public String getBookID() {
        return bookID;
    }

    public String getBookEdition() {
        return bookEdition;
    }

    public String getBookPublisher() {
        return bookPublisher;
    }

    public void setBookAvlbAmt(int bookAvlbAmt) {
        this.bookAvlbAmt = bookAvlbAmt;
    }

    public void setBookCat(String bookCat) {
        this.bookCat = bookCat;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public void setBookPrice(float bookPrice) {
        this.bookPrice = bookPrice;
    }

    public void setBookPublisher(String bookPublisher) {
        this.bookPublisher = bookPublisher;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public void setBookTotalAmt(int bookTotalAmt) {
        this.bookTotalAmt = bookTotalAmt;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }


    public void setBookPosition(String bookPosition) {
        this.bookPosition = bookPosition;
    }

    public void setBookEdition(String bookEdition) {
        this.bookEdition = bookEdition;
    }
    String ID;
    String Title;
    String Author;
    String Category;
    String Publisher;
    String Edition;
    int Total_quantity;
    int Available_quantity;
    int Price;
    String Position;
}
