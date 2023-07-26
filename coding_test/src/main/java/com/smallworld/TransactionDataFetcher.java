package com.smallworld;

import com.smallworld.data.Transaction;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class TransactionDataFetcher {

    /**
     * Returns the sum of the amounts of all transactions
     */
    public double getTotalTransactionAmount() {
        List<Transaction> txList = Transaction.from(loadData());
        double totalAmount = 0;
        for (int i = 0; i < txList.size(); i++) {
            totalAmount += txList.get(i).getAmount();
        }
        return totalAmount;
    }

    /**
     * Returns the sum of the amounts of all transactions sent by the specified client
     */
    public double getTotalTransactionAmountSentBy(String senderFullName) {
        List<Transaction> txList = Transaction.from(loadData());
        double totalAmount = 0;
        for (int i = 0; i < txList.size(); i++) {
            if(txList.get(i).getSenderFullName().equalsIgnoreCase( senderFullName))
            totalAmount += txList.get(i).getAmount();
        }
        return totalAmount;
    }

    /**
     * Returns the highest transaction amount
     */
    public double getMaxTransactionAmount() {

        List<Transaction> txList = Transaction.from(loadData());
        double maxAmount = 0;
        for (int i = 0; i < txList.size(); i++) {
            if(txList.get(i).getAmount()>maxAmount)
                maxAmount=txList.get(i).getAmount();
        }
        return maxAmount;
    }

    /**
     * Counts the number of unique clients that sent or received a transaction
     */
    public long countUniqueClients() {
        List<Transaction> txList = Transaction.from(loadData());
        Set<String> clients = new HashSet<>();
        for (Transaction transaction : txList) {
            clients.add(transaction.getSenderFullName());
            clients.add(transaction.getBeneficiaryFullName());
        }
        return clients.size();
    }

    /**
     * Returns whether a client (sender or beneficiary) has at least one transaction with a compliance
     * issue that has not been solved
     */
    public boolean hasOpenComplianceIssues(String clientFullName) {
        boolean hasOpenComplianceIssue=false;
        List<Transaction> txList = Transaction.from(loadData());
        for (int i = 0; i < txList.size(); i++) {
            if(txList.get(i).getSenderFullName().equalsIgnoreCase(clientFullName)&&!txList.get(i).isIssueSolved())
             hasOpenComplianceIssue=true;
            break;
        }
        return hasOpenComplianceIssue;
    }

    /**
     * Returns all transactions indexed by beneficiary name
     */
    public Map<String, Transaction> getTransactionsByBeneficiaryName() {
        List<Transaction> txList = Transaction.from(loadData());

        Map<String, Transaction> txMap=new HashMap<>();
        for (Transaction transaction : txList) {
            txMap.put(transaction.getBeneficiaryFullName(),transaction);
        }
        Map<String, Transaction> sortedMap = new TreeMap<>(txMap) ;

        return sortedMap;
    }
    /**
     * Returns the identifiers of all open compliance issues
     */
    public Set<Integer> getUnsolvedIssueIds() {
        List<Transaction> txList = Transaction.from(loadData());
        Set<Integer> ids=new HashSet<>();
        for (int i = 0; i < txList.size(); i++) {
            if(!txList.get(i).isIssueSolved()){
                ids.add(txList.get(i).getIssueId());
            }
        }
        return ids;

    }

    /**
     * Returns a list of all solved issue messages
     */
    public List<String> getAllSolvedIssueMessages() {
        List<Transaction> txList = Transaction.from(loadData());
        List<String> ids=new ArrayList<>();
        for (int i = 0; i < txList.size(); i++) {
            if(txList.get(i).isIssueSolved()&&!txList.get(i).getIssueMessage().equalsIgnoreCase("")){
               ids.add(txList.get(i).getIssueMessage());
            }
        }
        return ids;
    }

    /**
     * Returns the 3 transactions with the highest amount sorted by amount descending
     */
    public List<Transaction> getTop3TransactionsByAmount() {
        List<Transaction> txList = Transaction.from(loadData());
        Collections.sort(txList, Comparator.comparingDouble(Transaction::getAmount).reversed());
        int topCount = Math.min(3, txList.size());
        return txList.subList(0, topCount);
    }

    /**
     * Returns the senderFullName of the sender with the most total sent amount
     */
    public Optional<String> getTopSender() {
        List<Transaction> txList = Transaction.from(loadData());
        Map<String, Double> senderTotalAmounts = new HashMap<>();
        for (Transaction transaction : txList) {
            String senderFullName = transaction.getSenderFullName();
            double amount = transaction.getAmount();

            senderTotalAmounts.put(senderFullName, senderTotalAmounts.getOrDefault(senderFullName, 0.0) + amount);
        }
        Optional<String> senderWithMostTotalAmount = null;
        double maxTotalAmount = Double.MIN_VALUE;

        for (Map.Entry<String, Double> entry : senderTotalAmounts.entrySet()) {
            if (entry.getValue() > maxTotalAmount) {
                maxTotalAmount = entry.getValue();
                senderWithMostTotalAmount =Optional.ofNullable( entry.getKey());
            }
        }
        return senderWithMostTotalAmount;

    }

    JSONArray loadData() {
        JSONArray jsonData;
        try {
            jsonData = (JSONArray) new JSONParser().parse(new FileReader("./transactions.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return jsonData;
    }


    public static void main(String[] args) {

        TransactionDataFetcher tData = new TransactionDataFetcher();
        System.out.println("Get Total Transaction Amount: "+tData.getTotalTransactionAmount());
        System.out.println("Get Total Transaction Amount Sent By : "+tData.getTotalTransactionAmountSentBy("Billy Kimber"));
        System.out.println("Get Max Transaction Amount: "+tData.getMaxTransactionAmount());
        System.out.println("Get Unique Client Count: "+tData.countUniqueClients());
        System.out.println("Has Open Compliance Issues: "+tData.hasOpenComplianceIssues("Grace Burgess"));
        System.out.println("Get Transactions By Beneficiary Name: "+tData.getTransactionsByBeneficiaryName());
        System.out.println("Get Unsolved Issue Ids: "+tData.getUnsolvedIssueIds());
        System.out.println("Get All Solved Issue Messages: "+tData.getAllSolvedIssueMessages());
        System.out.println("Get Top3 Transactions By Amount: "+tData.getTop3TransactionsByAmount());
        System.out.println("Get Top Sender"+tData.getTopSender().get());

    }

}
