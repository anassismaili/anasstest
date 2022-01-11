class Account_type {

    var accountType = ""
    var customerId = 0

    def this(customerId:Int, accountType:String) {
        this()
        this.customerId = customerId
        this.accountType = accountType
    }
    override def toString():String = {
        return this.customerId + "," + "'" + this.accountType + "');"
    }
  
}
