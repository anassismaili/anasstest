class Bank_locations {
    var address = ""


    def this(address:String) {
        this()
        this.address = address
    }

    override def toString():String ={
        return "'" + this.address + "');"
        
    }
  
}
