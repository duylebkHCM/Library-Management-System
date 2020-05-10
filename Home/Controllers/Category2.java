package Home.Controllers;


public class Category2 {
	String categoryName;
	int noOfBook ;
	Category2()
	{
		this.categoryName = null;
		this.noOfBook =0;
	}
	Category2(String name)
	{
		this.categoryName = name;
		this.noOfBook = 0;
	}
	public void setCategory(String cat) 
	{
		this.categoryName = cat;
	}
	public String getCategory() 
	{
		return this.categoryName;
	}
	void setNum (int num)
	{
		this.noOfBook += num;
	}
	int getNum()
	{
		return this.noOfBook;
	}
	public String initBookID()
	{
		String bookID ="";
		switch(this.categoryName) {
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
		int numOfZero = 5 - String.valueOf(this.noOfBook+1).length();
		for (int i = 0; i < numOfZero;i++)
		{
			bookID += "0";
		}
		bookID += String.valueOf(this.noOfBook + 1);
		return bookID;
	}
	public String initBookLocation()
	{
		String location ="";
		switch(this.categoryName) {
		case "Business": location = "A";break;
		case "Comic": location = "B";break;
		case "Computer & Tech" : location = "C";break;
		case "Edu & Reference": location = "D";break;
		case "Literature & Fiction" :location = "E";break;
		case "Music & Art" :location = "F";break;
		case "Magazine": location = "G";break;
		case "Science & Math" : location = "H";break;
		case "Social Science" : location = "I" ;break;
		}
		
		return location;
	}
}