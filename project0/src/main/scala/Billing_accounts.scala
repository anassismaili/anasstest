class Billing_accounts {
  var customerId = 0
  var accountId = 0
  var billAmount = 0
  var comments = ""

  def this(customerId:Int, accountId:Int, billAmount:Int, comments:String) {
      this()

      this customerId = customerId
      this.accountId = accountId
      this.billAmount = billAmount
      this.comments = comments
  }
  override def toString():String = {
    return this.customerId + " ," + this.accountId + " ,'" + this.billAmount + "' ,'" + this.comments + "');"
  }
  
}
