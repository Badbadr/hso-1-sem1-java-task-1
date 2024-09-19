package my.project.model;

import lombok.Getter;

import java.util.List;

@Getter
public class Payment {

    private final long paymentUniqueID;
    private final String contractId;
    private final long clientID;
    private final String paymentType;
    private final String paymentMonth;
    private final String beneficiaryIBAN;
    private final String beneficiaryBIC;
    private final String engagementNumber;
    private final float paymentAmount;
    private final String paymentStatus;
    private final boolean paidOnBlockedAccount;

    public Payment(String paymentUniqueID, String contractId, String clientID, String paymentType, String paymentMonth,
                   String beneficiaryIBAN, String beneficiaryBIC, String engagementNumber, String paymentAmount,
                   String paymentStatus, String paidOnBlockedAccount) {
        this.paymentUniqueID = Long.parseLong(paymentUniqueID);
        this.contractId = contractId;
        this.clientID = Long.parseLong(clientID);
        this.paymentType = paymentType;
        this.paymentMonth = paymentMonth;
        this.beneficiaryIBAN = beneficiaryIBAN;
        this.beneficiaryBIC = beneficiaryBIC;
        this.engagementNumber = engagementNumber;
        this.paymentAmount = Float.parseFloat(paymentAmount);
        this.paymentStatus = paymentStatus;
        this.paidOnBlockedAccount = Boolean.parseBoolean(paidOnBlockedAccount);
    }

    public Payment(List<String> args) {
       this(args.get(0), args.get(1), args.get(2), args.get(3), args.get(4), args.get(5), args.get(6), args.get(7),
               args.get(8), args.get(9), args.get(10));
    }

}
