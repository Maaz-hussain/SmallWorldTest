package com.smallworld.data;

import com.smallworld.TransactionDataFetcher;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Transaction {
    // Represent your transaction data here.
    private Integer mtn;
    private double amount;
    private String senderFullName;
    private Integer senderAge;
    private String beneficiaryFullName;
    private Integer beneficiaryAge;
    private Integer issueId;
    private boolean issueSolved;
    private String issueMessage;


    public Integer getMtn() {
        return mtn;
    }

    public void setMtn(Integer mtn) {
        this.mtn = mtn;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getSenderFullName() {
        return senderFullName;
    }

    public void setSenderFullName(String senderFullName) {
        this.senderFullName = senderFullName;
    }

    public Integer getSenderAge() {
        return senderAge;
    }

    public void setSenderAge(Integer senderAge) {
        this.senderAge = senderAge;
    }

    public String getBeneficiaryFullName() {
        return beneficiaryFullName;
    }

    public void setBeneficiaryFullName(String beneficiaryFullName) {
        this.beneficiaryFullName = beneficiaryFullName;
    }

    public Integer getBeneficiaryAge() {
        return beneficiaryAge;
    }

    public void setBeneficiaryAge(Integer beneficiaryAge) {
        this.beneficiaryAge = beneficiaryAge;
    }

    public Integer getIssueId() {
        return issueId;
    }

    public void setIssueId(Integer issueId) {
        this.issueId = issueId;
    }

    public boolean isIssueSolved() {
        return issueSolved;
    }

    public void setIssueSolved(boolean issueSolved) {
        this.issueSolved = issueSolved;
    }

    public String getIssueMessage() {
        return issueMessage;
    }

    public void setIssueMessage(String issueMessage) {
        this.issueMessage = issueMessage;
    }

    public static List<Transaction> from(JSONArray jsonArray) {
        List<Transaction> transactionList = new ArrayList<>();
        TransactionDataFetcher transactionDataFetcher = new TransactionDataFetcher();

        for (Object temp : jsonArray) {
            JSONObject jsonObject = (JSONObject) temp;
            Transaction transaction = new Transaction();
            transaction.setMtn(Integer.parseInt(jsonObject.get("mtn").toString()));
            transaction.setAmount(Double.parseDouble(jsonObject.get("amount").toString()));
            transaction.setSenderFullName(jsonObject.get("senderFullName").toString());
            transaction.setSenderAge(Integer.parseInt(jsonObject.get("senderAge").toString()));
            transaction.setBeneficiaryFullName(jsonObject.get("beneficiaryFullName").toString());
            transaction.setBeneficiaryAge(Integer.parseInt(jsonObject.get("beneficiaryAge").toString()));
            transaction.setIssueId(Integer.parseInt(jsonObject.get("issueId") != null ? jsonObject.get("issueId").toString() : "0"));
            transaction.setIssueSolved(Boolean.parseBoolean(jsonObject.get("issueSolved").toString()));
            transaction.setIssueMessage((jsonObject.get("issueMessage") != null ? jsonObject.get("issueMessage").toString() : ""));

            transactionList.add(transaction);
        }
        return transactionList;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "mtn=" + mtn +
                ", amount=" + amount +
                ", senderFullName='" + senderFullName + '\'' +
                ", senderAge=" + senderAge +
                ", beneficiaryFullName='" + beneficiaryFullName + '\'' +
                ", beneficiaryAge=" + beneficiaryAge +
                ", issueId=" + issueId +
                ", issueSolved=" + issueSolved +
                ", issueMessage='" + issueMessage + '\'' +
                '}';
    }
}



