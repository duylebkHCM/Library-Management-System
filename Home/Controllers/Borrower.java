package Home.Controllers;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Date;

public class Borrower {
    String BookID;
    String Name;
    String ID;
    String Phone;
    String Email;
    String Address;
    Date IssueDate;
    Date DueDate;
    String Status;
    String IssueCode;
    Date ReturnDate;

    public Borrower() {

        this.Name = new String();
        this.ID = new String();
        this.Phone = new String();
        this.Email = new String();
        this.IssueDate = new Date();
        this.DueDate = new Date();
        this.Status = new String();
        this.IssueCode = new String();
        this.ReturnDate = new Date();
    }

    public Borrower(String BookID, String Name, String ID, String Phone, String Email, String Address, Date IssueDate, Date DueDate, String Status, String IssueCode, Date ReturnDate){
        this.BookID = BookID;
        this.Name = Name;
        this.ID = ID;
        this.Phone = Phone;
        this.Email = Email;
        this.IssueDate = IssueDate;
        this.DueDate = DueDate;
        this.Status = Status;
        this.IssueCode = IssueCode;
        this.ReturnDate = ReturnDate;
    }

    public String getBookID() {
        return BookID;
    }

    public void setBookID(String bId) {
        this.BookID = bId;
    }

    public String getBName() {
        return Name;
    }

    public void setBName(String bId) {
        this.Name = (bId);
    }

    public String getBID() {
        return ID;
    }

    public void setBID(String bId) {
        this.ID = new String(bId);
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String bId) {
        this.Phone = new String(bId);
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String bId) {
        this.Email = new String(bId);
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String bId) {
        this.Address = new String(bId);
    }

    public Date getIssueDate() {
        return IssueDate;
    }

    public void setIssueDate(Date date1) {
        this.IssueDate = date1;
    }

    public Date getDueDate() {
        return DueDate;
    }

    public void setDueDate(Date date1) {
        this.DueDate = date1;
    }

    public Date getReturnDate() {
        return ReturnDate;
    }

    public void setReturnDate(Date date1) {
        this.ReturnDate = date1;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String bId) {
        this.Status = new String(bId);
    }

    public String getIssueCode() {
        return IssueCode;
    }

    public void setIssueCode(String bId) {
        this.IssueCode = new String(bId);
    }

}
