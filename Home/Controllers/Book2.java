package Home.Controllers;

public class Book2 {
	String bookID = ""; 
	String bookAuthor;
	String bookTitle;
	String bookCategory;
	String bookPublisher;
	int totalAmount;
	int avaiAmount;
	int price;
	String bookEdition;
	String bookLocation;
	Book2()
	{
		this.bookID = 
		this.bookAuthor = 
		this.bookTitle = 
		this.bookCategory = 
		this.bookPublisher = "";
		this.totalAmount = this.avaiAmount = 
		this.price = 0;
		this.bookEdition = "";
		bookLocation = "";
	}
	Book2(String author,String title,String category, String publisher, int total,int price,String edition)
	{
		this.bookID = "";
		this.bookAuthor = author;
		this.bookTitle = title;
		this.bookCategory = category;
		this.bookPublisher = publisher;
		this.totalAmount = this.avaiAmount = total;
		this.price = price;
		this.bookEdition = edition;
		this.bookLocation ="";
	}
	public void setAvaiAmount(int amount) //get Available Book Number
	{
		this.avaiAmount += amount;
	}
	public void setTotalAmount(int amount) // set Total Amount 
	{
		this.totalAmount += amount;
	}	
	public int getAvaiAmount() //get Available Book Number
	{
		return this.avaiAmount ;
	}
	public int getTotalAmount() //get Total Amount 
	{
		return this.totalAmount ;
	}
	public void setCategory(String cat) // set Category
	{
		this.bookCategory = cat;
	}
	public String getCategory() // get Category
	{
		return this.bookCategory;
	}
	public void setAuthor(String auth) // set Author
	{
		this.bookAuthor = auth;
	}
	public String getAuthor() // get Author
	{
		return this.bookAuthor;
	}
	public void setTitle(String tit) // set Title
	{
		this.bookTitle = tit;
	}
	public String getTitle() // get Title
	{
		return this.bookTitle;
	}
	public void setPublisher(String pub)
	{
		this.bookPublisher = pub;
	}
	public String getPublisher()
	{
		return this.bookPublisher;
	}
	public void setEdition(String edi)
	{
		this.bookEdition = edi;
	}
	public String getEdition()
	{
		return this.bookEdition;
	}
	public void setPrice(int pri)
	{
		this.price = pri;
	}
	public int getPrice()
	{
		return this.price;
	}
	public void setID(String ID)
	{
		this.bookID = ID;
	}
	public String getID ()
	{
		return this.bookID;
	}
	/*public void setID(String cat, int num) 
	{
		switch(cat) {
		case "Business": this.bookID = "BU";break;
		case "Comic": this.bookID = "CO";break;
		case "Computer & Tech" : this.bookID = "CT";break;
		case "Edu & Reference": this.bookID = "ED";break;
		case "Literature & Fiction" :this.bookID = "LF";break;
		case "Music & Art" :this.bookID = "MU";break;
		case "Magazine": this.bookID = "MA";break;
		case "Science & Math" : this.bookID = "SM";break;
		case "Social Science" : this.bookID = "SS" ;break;
		}
		int numOfZero = 5 - String.valueOf(num).length();
		for (int i = 0; i < numOfZero;i++)
		{
			this.bookID += "0";
		}
		this.bookID += String.valueOf(num);
	}*/
	
}
