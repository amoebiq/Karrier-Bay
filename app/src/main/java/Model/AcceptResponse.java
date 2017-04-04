package Model;

/**
 * Created by vel on 26/2/17.
 */

public class AcceptResponse {
  private  String status;
  private String order_id;
  private String total_amount;

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getOrder_id() {
    return order_id;
  }

  public void setOrder_id(String order_id) {
    this.order_id = order_id;
  }

  public String getTotal_amount() {
    return total_amount;
  }

  public void setTotal_amount(String total_amount) {
    this.total_amount = total_amount;
  }
}
