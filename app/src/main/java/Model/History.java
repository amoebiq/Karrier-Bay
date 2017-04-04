package Model;

/**
 * Created by nirmal.ku on 3/23/2017.
 */

public class History {

    private String from;
    private String to;
    private String amount;
    private String details;
    private String status;

    public History(String from, String to, String amount, String details, String status) {
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.details = details;
        this.status = status;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



}
