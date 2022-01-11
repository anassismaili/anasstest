class Accounts {
    var customerId = 0
    var accountTypeId = 0
    var accountNumber = 0
    var balance = 0
    

    def this(customerId:Int, accountTypeId:Int, accountNumber:Int, balance:Int){
        this()
        this.customerId = customerId
        this.accountTypeId = accountTypeId
        this.accountNumber = accountNumber
        this.balance = balance
    }
    override def toString():String ={
        return "'" + this.customerId + "', '" + this.accountTypeId + "', '" + this.accountNumber +  "', '" + this.balance + "');"
        
    }
  
}
