package Home.Controllers;

public class Category {
    private String catName;
    private int catNumofBook;


    public Category(String Name, int num){
        catName = Name;
        catNumofBook = num;
    }

    public int getCatNumofBook() {
        return catNumofBook;
    }


    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public void setCatNumofBook(int catNumofBook) {
        this.catNumofBook = catNumofBook;
    }

    public String catID(){
        String bookID ="";
        switch(this.catName) {
            case "Business": bookID = "BU";break;
            case "Comic": bookID = "CO";break;
            case "Computer & Tech" : bookID = "CT";break;
            case "Edu & Reference": bookID = "ED";break;
            case "Literature & Fiction" :bookID = "LF";break;
            case "Music & Art" :bookID = "MU";break;
            case "Magazine": bookID = "MA";break;
            case "Science & Math" : bookID = "SM";break;
            case "Social Science" : bookID = "SS" ;break;
        }
        return bookID;
    }
    public String initBookID()
    {
        String bookID ;
        bookID = catID();
        int numOfZero = 5 - String.valueOf(this.catNumofBook).length();
        for (int i = 0; i < numOfZero;i++)
        {
            bookID += "0";
        }
        bookID += String.valueOf(this.catNumofBook);
        return bookID;
    }
}
