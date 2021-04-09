package model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Blob;
import java.util.Date;

/**
 * Model for Reimbursement Object.  Includes attributes describing a given reimbursement along
 * with getters and setters for each.  Overidden toString is available.
 */
public class Reimbursement {
    private int reimbursementId;
    private double amount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
    private Date submitDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
    private Date resolveDate;
    private String description;
    private Blob receipt;
    private int author;
    private Integer resolver;
    private int status;
    private int type;

    /**
     * All args constructor for reimbursement
     * @param reimbursementId
     * @param amount
     * @param submitDate
     * @param resolveDate
     * @param description
     * @param receipt
     * @param author
     * @param resolver
     * @param status
     * @param type
     */
    public Reimbursement(int reimbursementId, double amount, Date submitDate, Date resolveDate, String description, Blob receipt, int author, Integer resolver, int status, int type) {
        this.reimbursementId = reimbursementId;
        this.amount = amount;
        this.submitDate = submitDate;
        this.resolveDate = resolveDate;
        this.description = description;
        this.receipt = receipt;
        this.author = author;
        this.resolver = resolver;
        this.status = status;
        this.type = type;
    }

    /**
     * Additional constructor used in DAO layer to add a reimbursement to DB.
     * @param amount
     * @param description
     * @param author
     * @param status
     * @param type
     */
    public Reimbursement(double amount, String description, int author, int status, int type) {
        this.amount = amount;
        this.description = description;
        this.author = author;
        this.status = status;
        this.type = type;
    }

    public int getReimbursementId() {
        return reimbursementId;
    }

    public void setReimbursementId(int reimbursementId) {
        this.reimbursementId = reimbursementId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    public Date getResolveDate() {
        return resolveDate;
    }

    public void setResolveDate(Date resolveDate) {
        this.resolveDate = resolveDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Blob getReceipt() {
        return receipt;
    }

    public void setReceipt(Blob receipt) {
        this.receipt = receipt;
    }

    public int getAuthor() {
        return author;
    }

    public void setAuthor(int author) {
        this.author = author;
    }

    public Integer getResolver() {
        return resolver;
    }

    public void setResolver(Integer resolver) {
        this.resolver = resolver;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Reimbursement{" +
                "reimbursementId=" + reimbursementId +
                ", amount=" + amount +
                ", submitDate=" + submitDate +
                ", resolveDate=" + resolveDate +
                ", description='" + description + '\'' +
                ", receipt=" + receipt +
                ", author=" + author +
                ", resolver=" + resolver +
                ", status=" + status +
                ", type=" + type +
                '}';
    }
}
