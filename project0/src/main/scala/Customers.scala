class Customers {

    var customerName = ""
    var customerInfo = ""
    var branchId = 0
    def this(customerName:String, customerInfo:String, branchId:Int) {
        this()
        this.customerName = customerName
        this.customerInfo = customerInfo
        this.branchId = branchId
    }

    override def toString():String ={
        return "'" + this.customerName + "', '" + this.customerInfo +"'," +this.branchId +");" 
        
    }
  
}
