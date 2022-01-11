import java.util.Scanner
import java.sql.DriverManager
import java.sql.Connection
import scala.util.Random
import java.io.File
import java.util.Calendar
import java.io.PrintWriter

object Banking_app {

  def main(args: Array[String]):Unit = {
    newCustomer()

  }


  def newCustomer(): Unit = {

    // connect to the database named "mysql" on the localhost
    val driver = "com.mysql.jdbc.Driver"
    val url = "jdbc:mysql://localhost:3306/bankapp" // Modify for whatever port you are running your DB on
    val username = "root"
    val password = "monalisa.123" // Update to include your password
    val log = new PrintWriter(new File("query.log"))

    var connection:Connection = null
    try {
      // make the connection
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)
      var scanner = new Scanner(System.in)
      var accountTypes = Set("Personal", "Busniss")
      // create the statement, and run the select query
      val statement = connection.createStatement()
      //if user enters a departement that does not match our set of departements
      var badData = true
      var accountType = ""
      while(badData){
        badData = false
        println("-------------------------------------Bank App-----------------------------------------------------")
        //Types of account
        println("Enter Account type (Personal or Busniss): ")
        try{
          accountType = scanner.nextLine()
          //Personal account
          if(accountType == "Personal"){
            println("-----------------------------------------------------------------------------------------------")
            println("--------------------------Information about bank location--------------------------------------")
            println("-----------------------------------------------------------------------------------------------")
            var statement = connection.createStatement()
            //Enter information about a new location of the bank
            println("Do you want to enter a new bank location (Y/N) : ")
            var bankL = scanner.nextLine()
            if(bankL == "Y"){
              var badData = true
              var bankAddress = ""
              while(badData){
                badData = false
                println("Enter bank location : ")
                try{
                  var banckAddress = scanner.nextLine()
                  if(banckAddress == "") {
                    badData = true
                    throw new BadDataEntryException
                  }

                  if(banckAddress != ""){
                    // Enter data into table BankLocation in MySQL Database
                    var query2 = "INSERT INTO BankLocation (Address) Values (" + new Bank_locations(banckAddress)
                    log.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'INSERT INTO BankLocation (Address) Values (" + new Bank_locations(banckAddress) + "\n")
                    

                    println(query2)
                    statement.executeUpdate(query2)
                    val resultSet2 = statement.executeQuery("SELECT * FROM BankLocation;")
                    log.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'SELECT * FROM BankLocation;" + "\n")
                    while ( resultSet2.next() ) {
                      print(resultSet2.getString(1) + " " + resultSet2.getString(2))
                      println()
                    }
                  }
                }catch{
                  case bde: BadDataEntryException => println("Enter a valid bank location!")
                  case e: Exception => println("Exception was thrown, trying again..") 
                }
 
              }
            }
            println("---------------------------End of Bank location ----------------------------------------------")
            println("-------------------------Information about accounts ------------------------------------------")

            println("-----------------------------------------------------------------------------------------------")
            println("---------------------------------banklocation table--------------------------------------------")
            var query5 = "SELECT * FROM BankLocation "
            log.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'SELECT * FROM BankLocation;" + "\n")
            var resultSet20 = statement.executeQuery(query5) // Change query to your table
            while ( resultSet20.next() ) {
                print(resultSet20.getString(1) + " " + resultSet20.getString(2))
                println()
            }

            println("----------------------------------------------------------------------------------------------")
            println("------------------------Informations about new customer---------------------------------------")
            println("Do you want make new customer press (Y/N)")
            var newCustomer = scanner.nextLine()
            if(newCustomer == "Y"){
              var badData = true
              var customerName = ""
              var customerInfo = ""
              var branchId = -1
              var customerId = -1
              var accountTypeId = -1
              while(badData){
                badData = false
                // Personal account customer information
                println("Enter name of the new customer (First and Last Name): ")
                try{
                  customerName = scanner.nextLine()
                  if(customerName == ""){
                    badData= true
                    throw new BadDataEntryException
                  }
                }catch {
                  case bde: BadDataEntryException => println("Enter a valid name!")
                  case e: Exception => println("Exception was thrown, trying again..")
                }
                //Enter Customer information
                println("Enter customer information (Phone or Email): ")
                try {
                  customerInfo = scanner.nextLine()
                  if(customerInfo == ""){
                    badData = true
                    throw new BadDataEntryException
                  }
                }catch{
                  case bde: BadDataEntryException => println("Enter a valid name!")
                  case e: Exception => println("Exception was thrown, trying again..")
                }
                //Enter branch Id to create new customer
                println("Enter branch id of this customer: ")
                try{
                  branchId = scanner.nextInt()
                  scanner.nextLine()
                  if(branchId <= 0){
                    badData = true
                    throw new BadDataEntryException
                  }
                }catch {
                  case bde: BadDataEntryException => println("Please enter a valid branchId")
                  case e: Exception => println("Exception was thrown, trying again..")
                }
                // showing the customer table
                println("----------------------------------------------------------------------------------------------")
                println("------------------------------Customers table-------------------------------------------------")
                var query6 = "SELECT * FROM Customers "
                log.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'SELECT * FROM Customers;" + "\n")
                var resultSet21 = statement.executeQuery(query6) // Change query to your table
                while ( resultSet21.next() ) {
                  print(resultSet21.getString(1) + " " + resultSet21.getString(2) + " " + resultSet21.getString(3) + " " + resultSet21.getString(4))
                  println()
                }
                println("----------------------------------------------------------------------------------------------")
                println("--------------------------------Account Type table--------------------------------------------")
                println("Enter the customer id of this account type : ")
                try{
                  customerId = scanner.nextInt()
                  scanner.nextLine()
                  if(customerId <= 0){
                    badData = true
                    throw new BadDataEntryException
                  }
                }catch {
                    case bde: BadDataEntryException => println("Please enter a valid CustomerId ")
                    case e: Exception => println("Exception was thrown, trying again..")
                }
                val resultSet1 = statement.executeQuery("SELECT * FROM AccountType;")
                log.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'SELECT * FROM AccountType;" + "\n")
                while ( resultSet1.next() ) {
                  print(resultSet1.getString(1) + " " + resultSet1.getString(2) + " " + resultSet1.getString(3))
                  println()
                }
                println("-------------------------------------------------------------------------------------------")
                println("---------------------------------------Update new Account----------------------------------")
                println("Enter account type id of this account: ")
                try{
                  accountTypeId = scanner.nextInt()
                  scanner.nextLine()
                  if(accountTypeId <= 0){
                    badData = true
                    throw new BadDataEntryException
                  }
                }catch{
                  case bde: BadDataEntryException => println("Please enter a valid CustomerId ")
                  case e: Exception => println("Exception was thrown, trying again..")
                }

                
                if(customerInfo != "" || customerInfo != "" || branchId >= 0 || customerId >=0 || accountTypeId >= 0){
                  // Enter data into table customers in MySQL DataBase
                  var query = "INSERT INTO Customers (CustomerName, ContactInfo, branchId) Values (" + new Customers(customerName, customerInfo, branchId)//, branchId
                  log.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'INSERT INTO Customers (CustomerName, ContactInfo, branchId) Values (" + new Customers(customerName, customerInfo, branchId)) + "\n"
                  println(query)
                  statement.executeUpdate(query) // Change query to your table
                  val resultSet = statement.executeQuery("SELECT * FROM Customers;")
                  log.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'SELECT * FROM Customers;" + "\n")
                  while (resultSet.next()) {
                    print(resultSet.getString(1) + " " + resultSet.getString(2) + " " + resultSet.getString(3) + " " + resultSet.getString(4))
                    println()
                  }
                  //Enter data into table AccountType in MySQL Database
                  var query1 = "INSERT INTO AccountType (CustomerId, AccountType) Values (" + new Account_type(customerId, accountType)
                  log.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'INSERT INTO AccountType (CustomerId, AccountType) Values (" + new Account_type(customerId, accountType) + "\n")
                  println(query1)
                  statement.executeUpdate(query1)
                  val resultSet1 = statement.executeQuery("SELECT * FROM AccountType;")
                  log.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'SELECT * FROM AccountType;" + "\n")
                  while ( resultSet1.next() ) {
                    print(resultSet1.getString(1) + " " + resultSet1.getString(2) + " " + resultSet1.getString(3))
                    println()
                  }
                  var balance = 0
                  // Enter account number
                  var rand = new Random()
                  var accountNumber = rand.nextInt(100000000)
                  // Enter data into table Accounts in MySQL Database
                  var query3 = "INSERT INTO Accounts (CustomerId, AccountTypeId, AccountNumber, Balance) Values (" + new Accounts(customerId, accountTypeId, accountNumber, balance)// CustomerId, AccountTypeId,
                  log.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'INSERT INTO Accounts (CustomerId, AccountTypeId, AccountNumber, Balance) Values (" + new Accounts(customerId, accountTypeId, accountNumber, balance) + "\n")
                  println(query3)
                  statement.executeUpdate(query3)
                  val resultSet3 = statement.executeQuery("SELECT * FROM Accounts;")
                  log.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'SELECT * FROM Accounts;" + "\n")
                  while ( resultSet3.next() ) {
                    print(resultSet3.getString(1) + " " + resultSet3.getString(2) + " " + resultSet3.getString(3) + " " + resultSet3.getString(4) + " " + resultSet3.getString(5))
                    println()
                  }
              }
            }
          }

            //Add a new transaction
            println("Do you want make transaction (Y/N): ")
            var trn = scanner.nextLine()
            if(trn == "Y"){
              var badData = true
              var transactions = Set("billing", "deposit", "nothing")
              var transaction = ""
              var customerId = -1
              var accountId = -1
              while(badData){
                badData = false
                // Enter account type id of this customer
                println("------------------------------Customers table-------------------------------------------------")
                var query6 = "SELECT * FROM Customers "
                log.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'SELECT * FROM Customers;" + "\n")
                var resultSet21 = statement.executeQuery(query6) // Change query to your table
                while ( resultSet21.next() ) {
                    print(resultSet21.getString(1) + " " + resultSet21.getString(2) + " " + resultSet21.getString(3) + " " + resultSet21.getString(4))
                    println()
                }
                // Enter account customer Id
                println("Enter customer id for this account: ")
                try{
                  customerId = scanner.nextInt()
                  scanner.nextLine()
                  if(customerId <= 0){
                    badData = true
                    throw new BadDataEntryException
                  }

                }catch {
                  case bde: BadDataEntryException => println("Please enter a valid customer id")
                  case e: Exception => println("Exception was thrown, trying again..")
                }

          
                println("---------------------------------------------------------------------------------------------")
                println("-------------------------------Accounts table------------------------------------------------")
                var query8 = "SELECT * FROM Accounts"
                log.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'SELECT * FROM Accounts;" + "\n")
                var resultSet23 = statement.executeQuery(query8) // Change query to your table
                while ( resultSet23.next() ) {
                    print(resultSet23.getString(1) + " " + resultSet23.getString(2) + " " + resultSet23.getString(3)+ " " + resultSet23.getString(4) + " " + resultSet23.getString(5))
                    println()
                }
                //Enter account id
                println("Enter account id of this account: ")
                try{
                  accountId = scanner.nextInt()
                  scanner.nextLine()
                  if(accountId <= 0){
                    badData = true
                    throw new BadDataEntryException
                  }
                }catch{
                  case bde: BadDataEntryException => println("Please enter a valid account id")
                  case e: Exception => println("Exception was thrown, trying again..")
                }
                println("---------------------------------------------------------------------------------------------")
                println("-------------------------------------Transaction---------------------------------------------")

                // calculate balance
                println("Enter type of transaction (billing or deposit or nothing): ")
                try{
                  transaction = scanner.nextLine()
                  if(!transactions.contains(transaction)){
                    badData = true
                    throw new BadDataEntryException
                  }
                }catch {
                  case bde: BadDataEntryException => println(" this transaction is invalide! Enter a valid transaction")
                  case e: Exception => println("Exception thrown. Trying again..")

                }
                if(transactions.contains(transaction) || customerId >=0 || accountId >= 0){
                  var balance = 0
                  var billingAmount = 0
                  if(transaction == "billing"){
                    println("----------------------Information about billing--------------------------")
                    //Enter the amount of billing
                    println("Enter the amount of billing : ")
                    var billingAmount = scanner.nextInt()
                    scanner.nextLine()
                    //Update the balance in Account table
                    var query102 = "SELECT * FROM Accounts"
                    log.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'SELECT * FROM Accounts;" + "\n")
                    var resultSet24 = statement.executeQuery(query102) // Change query to your table
                    while ( resultSet24.next() ) {
                      balance = (resultSet24.getInt(5) - billingAmount)
                    }   
                    var query100 = "UPDATE Accounts SET Balance = " + balance + " WHERE CustomerId = " +  customerId + ";"
                    log.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'UPDATE Accounts SET Balance = " + balance + " WHERE CustomerId = " +  customerId + ";'\n")
                    println(query100)
                    statement.executeUpdate(query100)
                    println("-------------------------------Account table-----------------------------------------------")
                    var resultSet23 = statement.executeQuery(query102) // Change query to your table
                    while ( resultSet23.next() ) {
                      print(resultSet23.getString(1) + " " + resultSet23.getString(2) + " " + resultSet23.getString(3)+ " " + resultSet23.getString(4) + " " + resultSet23.getString(5))
                      println()
                    }
                    //Enter comments for billing
                    println("Enter comments for billing")
                    var comments = scanner.nextLine()
                    //Update table in BillingAccounts
                    var query4 = "INSERT INTO BillAccounts (CustomerId, AccountId, BillAmount, comments) Values (" + new Billing_accounts(customerId, accountId, billingAmount, comments)//CustomerId, AccountId,
                    log.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'INSERT INTO BillAccounts (CustomerId, AccountId, BillAmount, comments) Values (" + new Billing_accounts(customerId, accountId, billingAmount, comments) + "\n") 
                    println(query4)
                    statement.executeUpdate(query4)
                    val resultSet4 = statement.executeQuery("SELECT * FROM BillAccounts;")
                    log.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'SELECT * FROM BillAccounts;" + "\n")
                    while ( resultSet4.next() ) {
                      print(resultSet4.getString(1) + " " + resultSet4.getString(2) + " " + resultSet4.getString(3) + " " + resultSet4.getString(4) + " " + resultSet4.getString(5))
                      println()
                    }
                    println("---------------------End of billing information-------------------------")   
                  }else if (transaction == "deposit"){
                    //Enter the amount of deposit
                    println("Enter the amount of deposit : ")
                    var deposit = scanner.nextInt()
                    scanner.nextLine()
                    var query102 = "SELECT * FROM Accounts"
                    log.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'SELECT * FROM Accounts;" + "\n")
                    var resultSet24 = statement.executeQuery(query102) // Change query to your table
                    while ( resultSet24.next() ) {
                      balance = (resultSet24.getInt(5) + deposit)
                    }
              
                    //Update the balance in Account table
                    var query100 = "UPDATE Accounts SET Balance = " + balance + " WHERE CustomerId = " +  customerId + ";"
                    log.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'UPDATE Accounts SET Balance = " + balance + " WHERE CustomerId = " +  customerId + ";'\n")
                    println(query100)
                    statement.executeUpdate(query100)
                    println("-------------------------------Account table-----------------------------------------------")

                    var resultSet23 = statement.executeQuery(query102) // Change query to your table
                    while ( resultSet23.next() ) {
                      print(resultSet23.getString(1) + " " + resultSet23.getString(2) + " " + resultSet23.getString(3)+ " " + resultSet23.getString(4) + " " + resultSet23.getString(5))
                      println()
                    }
                    //
                  }else if(transaction == "nothing"){
                    //Update the balance in Account table
                    var query102 = "SELECT * FROM Accounts"
                    var resultSet24 = statement.executeQuery(query102) // Change query to your table
                    while ( resultSet24.next() ) {
                      balance = resultSet24.getInt(5)
                    } 
                    //Update the balance in Account table
                    var query100 = "UPDATE Accounts SET Balance = " + balance + " WHERE CustomerId = " +  customerId + ";"
                    println(query100)
                    statement.executeUpdate(query100)
                    println("-------------------------------Account table-----------------------------------------------")
                    var query101 = "SELECT * FROM Accounts"
                    log.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'SELECT * FROM Accounts;" + "\n")
                    var resultSet23 = statement.executeQuery(query101) // Change query to your table
                    while ( resultSet23.next() ) {
                      print(resultSet23.getString(1) + " " + resultSet23.getString(2) + " " + resultSet23.getString(3)+ " " + resultSet23.getString(4) + " " + resultSet23.getString(5))
                      println()
                    }
                  }
                  println("balance: " + balance)
                  println("----------------------End of Accounts information--------------------------")
                }
              }
            }
          // Busniss Account
          }else if (accountType == "Busniss") {
            println("-----------------------------------------------------------------------------------------------")
            println("-----------------------------------------------------------------------------------------------")
            println("--------------------------Information about bank location--------------------------------------")
            println("-----------------------------------------------------------------------------------------------")
            var statement = connection.createStatement()
            //Enter information about a new location of the bank
            println("Do you want to enter a new bank location (Y/N) : ")
            var bankL = scanner.nextLine()
            if(bankL == "Y"){
              var badData = true
              var bankAddress = ""
              while(badData){
                badData = false
                println("Enter bank location : ")
                try{
                  var banckAddress = scanner.nextLine()
                  if(banckAddress == "") {
                    badData = true
                    throw new BadDataEntryException
                  }

                  if(banckAddress != ""){
                    // Enter data into table BankLocation in MySQL Database
                    var query2 = "INSERT INTO BankLocation (Address) Values (" + new Bank_locations(banckAddress)
                    log.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'INSERT INTO BankLocation (Address) Values (" + new Bank_locations(banckAddress) + "\n") 
                    println(query2)
                    statement.executeUpdate(query2)
                    val resultSet2 = statement.executeQuery("SELECT * FROM BankLocation;")
                    log.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'SELECT * FROM BankLocation;" + "\n")
                    while ( resultSet2.next() ) {
                      print(resultSet2.getString(1) + " " + resultSet2.getString(2))
                      println()
                    }
                  }
                }catch{
                  case bde: BadDataEntryException => println("Enter a valid bank location!")
                  case e: Exception => println("Exception was thrown, trying again..") 
                }
 
              }
            }
            println("---------------------------End of Bank location ----------------------------------------------")
            println("-------------------------Information about accounts ------------------------------------------")

            println("-----------------------------------------------------------------------------------------------")
            println("---------------------------------banklocation table--------------------------------------------")
            var query5 = "SELECT * FROM BankLocation "
            log.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'SELECT * FROM BankLocation;" + "\n")
            var resultSet20 = statement.executeQuery(query5) // Change query to your table
            while ( resultSet20.next() ) {
                print(resultSet20.getString(1) + " " + resultSet20.getString(2))
                println()
            }

            println("----------------------------------------------------------------------------------------------")
            println("------------------------Informations about new customer---------------------------------------")
            println("Do you want make new customer press (Y/N)")
            var newCustomer = scanner.nextLine()
            if(newCustomer == "Y"){
              var badData = true
              var customerName = ""
              var customerInfo = ""
              var branchId = -1
              var customerId = -1
              var accountTypeId = -1
              while(badData){
                badData = false
                // Personal account customer information
                println("Enter name of the new customer (First and Last Name): ")
                try{
                  customerName = scanner.nextLine()
                  if(customerName == ""){
                    badData= true
                    throw new BadDataEntryException
                  }
                }catch {
                  case bde: BadDataEntryException => println("Enter a valid name!")
                  case e: Exception => println("Exception was thrown, trying again..")
                }
                //Enter Customer information
                println("Enter customer information (Phone or Email): ")
                try {
                  customerInfo = scanner.nextLine()
                  if(customerInfo == ""){
                    badData = true
                    throw new BadDataEntryException
                  }
                }catch{
                  case bde: BadDataEntryException => println("Enter a valid name!")
                  case e: Exception => println("Exception was thrown, trying again..")
                }
                //Enter branch Id to create new customer
                println("Enter branch id of this customer: ")
                try{
                  branchId = scanner.nextInt()
                  scanner.nextLine()
                  if(branchId <= 0){
                    badData = true
                    throw new BadDataEntryException
                  }
                }catch {
                  case bde: BadDataEntryException => println("Please enter a valid branchId")
                  case e: Exception => println("Exception was thrown, trying again..")
                }
                // showing the customer table
                println("----------------------------------------------------------------------------------------------")
                println("------------------------------Customers table-------------------------------------------------")
                var query6 = "SELECT * FROM Customers "
                log.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'SELECT * FROM Customers;" + "\n")
                var resultSet21 = statement.executeQuery(query6) // Change query to your table
                while ( resultSet21.next() ) {
                  print(resultSet21.getString(1) + " " + resultSet21.getString(2) + " " + resultSet21.getString(3) + " " + resultSet21.getString(4))
                  println()
                }
                println("----------------------------------------------------------------------------------------------")
                println("--------------------------------Account Type table--------------------------------------------")
                println("Enter the customer id of this account type : ")
                try{
                  customerId = scanner.nextInt()
                  scanner.nextLine()
                  if(customerId <= 0){
                    badData = true
                    throw new BadDataEntryException
                  }
                }catch {
                    case bde: BadDataEntryException => println("Please enter a valid CustomerId ")
                    case e: Exception => println("Exception was thrown, trying again..")
                }
                val resultSet1 = statement.executeQuery("SELECT * FROM AccountType;")
                log.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'SELECT * FROM AccountType;" + "\n")
                while ( resultSet1.next() ) {
                  print(resultSet1.getString(1) + " " + resultSet1.getString(2) + " " + resultSet1.getString(3))
                  println()
                }
                println("-------------------------------------------------------------------------------------------")
                println("---------------------------------------Update new Account----------------------------------")
                println("Enter account type id of this account: ")
                try{
                  accountTypeId = scanner.nextInt()
                  scanner.nextLine()
                  if(accountTypeId <= 0){
                    badData = true
                    throw new BadDataEntryException
                  }
                }catch{
                  case bde: BadDataEntryException => println("Please enter a valid CustomerId ")
                  case e: Exception => println("Exception was thrown, trying again..")
                }

                
                if(customerInfo != "" || customerInfo != "" || branchId >= 0 || customerId >=0 || accountTypeId >= 0){
                  // Enter data into table customers in MySQL DataBase
                  var query = "INSERT INTO Customers (CustomerName, ContactInfo, branchId) Values (" + new Customers(customerName, customerInfo, branchId)//, branchId
                  log.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'INSERT INTO Customers (CustomerName, ContactInfo, branchId) Values (" + new Customers(customerName, customerInfo, branchId) + "\n")
                  println(query)
                  statement.executeUpdate(query) // Change query to your table
                  val resultSet = statement.executeQuery("SELECT * FROM Customers;")
                  log.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'SELECT * FROM Customers;" + "\n")
                  while (resultSet.next()) {
                    print(resultSet.getString(1) + " " + resultSet.getString(2) + " " + resultSet.getString(3) + " " + resultSet.getString(4))
                    println()
                  }
                  //Enter data into table AccountType in MySQL Database
                  var query1 = "INSERT INTO AccountType (CustomerId, AccountType) Values (" + new Account_type(customerId, accountType)
                  log.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'INSERT INTO AccountType (CustomerId, AccountType) Values (" + new Account_type(customerId, accountType) + "\n")
                  println(query1)
                  statement.executeUpdate(query1)
                  val resultSet1 = statement.executeQuery("SELECT * FROM AccountType;")
                  log.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'SELECT * FROM AccountType;" + "\n")
                  while ( resultSet1.next() ) {
                    print(resultSet1.getString(1) + " " + resultSet1.getString(2) + " " + resultSet1.getString(3))
                    println()
                  }
                  var balance = 0
                  // Enter account number
                  var rand = new Random()
                  var accountNumber = rand.nextInt(100000000)
                  // Enter data into table Accounts in MySQL Database
                  var query3 = "INSERT INTO Accounts (CustomerId, AccountTypeId, AccountNumber, Balance) Values (" + new Accounts(customerId, accountTypeId, accountNumber, balance)// CustomerId, AccountTypeId,
                  log.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'INSERT INTO Accounts (CustomerId, AccountTypeId, AccountNumber, Balance) Values (" + new Accounts(customerId, accountTypeId, accountNumber, balance) + "\n")
                  println(query3)
                  statement.executeUpdate(query3)
                  val resultSet3 = statement.executeQuery("SELECT * FROM Accounts;")
                  log.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'SELECT * FROM Accounts;" + "\n")
                  while ( resultSet3.next() ) {
                    print(resultSet3.getString(1) + " " + resultSet3.getString(2) + " " + resultSet3.getString(3) + " " + resultSet3.getString(4) + " " + resultSet3.getString(5))
                    println()
                  }
              }
            }
          }

            //Add a new transaction
            println("Do you want make transaction (Y/N): ")
            var trn = scanner.nextLine()
            if(trn == "Y"){
              var badData = true
              var transactions = Set("billing", "deposit", "nothing")
              var transaction = ""
              var customerId = -1
              var accountId = -1
              while(badData){
                badData = false
                // Enter account type id of this customer
                println("------------------------------Customers table-------------------------------------------------")
                var query6 = "SELECT * FROM Customers"
                log.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'SELECT * FROM Customers;" + "\n")
                var resultSet21 = statement.executeQuery(query6) // Change query to your table
                while ( resultSet21.next() ) {
                    print(resultSet21.getString(1) + " " + resultSet21.getString(2) + " " + resultSet21.getString(3) + " " + resultSet21.getString(4))
                    println()
                }
                // Enter account customer Id
                println("Enter customer id for this account: ")
                try{
                  customerId = scanner.nextInt()
                  scanner.nextLine()
                  if(customerId <= 0){
                    badData = true
                    throw new BadDataEntryException
                  }

                }catch {
                  case bde: BadDataEntryException => println("Please enter a valid customer id")
                  case e: Exception => println("Exception was thrown, trying again..")
                }

          
                println("---------------------------------------------------------------------------------------------")
                println("-------------------------------Accounts table------------------------------------------------")
                var query8 = "SELECT * FROM Accounts"
                log.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'SELECT * FROM Accounts;" + "\n")
                var resultSet23 = statement.executeQuery(query8) // Change query to your table
                while ( resultSet23.next() ) {
                    print(resultSet23.getString(1) + " " + resultSet23.getString(2) + " " + resultSet23.getString(3)+ " " + resultSet23.getString(4) + " " + resultSet23.getString(5))
                    println()
                }
                //Enter account id
                println("Enter account id of this account: ")
                try{
                  accountId = scanner.nextInt()
                  scanner.nextLine()
                  if(accountId <= 0){
                    badData = true
                    throw new BadDataEntryException
                  }
                }catch{
                  case bde: BadDataEntryException => println("Please enter a valid account id")
                  case e: Exception => println("Exception was thrown, trying again..")
                }
                println("---------------------------------------------------------------------------------------------")
                println("-------------------------------------Transaction---------------------------------------------")

                // calculate balance
                println("Enter type of transaction (billing or deposit or nothing): ")
                try{
                  transaction = scanner.nextLine()
                  if(!transactions.contains(transaction)){
                    badData = true
                    throw new BadDataEntryException
                  }
                }catch {
                  case bde: BadDataEntryException => println("this transaction is invalide! Enter a valid transaction")
                  case e: Exception => println("Exception thrown. Trying again..")

                }
                if(transactions.contains(transaction) || customerId >=0 || accountId >= 0){
                  var balance = 0
                  var billingAmount = 0
                  if(transaction == "billing"){
                      println("----------------------Information about billing--------------------------")
                      //Enter the amount of billing
                      println("Enter the amount of billing : ")
                      var billingAmount = scanner.nextInt()
                      scanner.nextLine()
                      //Update the balance in Account table
                      var query102 = "SELECT * FROM Accounts"
                      log.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'SELECT * FROM Accounts;" + "\n")
                      var resultSet24 = statement.executeQuery(query102) // Change query to your table
                      while ( resultSet24.next() ) {
                        balance = (resultSet24.getInt(5) - billingAmount)
                      }   
                      var query100 = "UPDATE Accounts SET Balance = " + balance + " WHERE CustomerId = " +  customerId + ";"
                      log.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'UPDATE Accounts SET Balance = " + balance + " WHERE CustomerId = " +  customerId + ";'\n")
                      println(query100)
                      statement.executeUpdate(query100)
                      println("-------------------------------Account table-----------------------------------------------")
                      var resultSet23 = statement.executeQuery(query102) // Change query to your table
                      while ( resultSet23.next() ) {
                        print(resultSet23.getString(1) + " " + resultSet23.getString(2) + " " + resultSet23.getString(3)+ " " + resultSet23.getString(4) + " " + resultSet23.getString(5))
                        println()
                      }
                      //Enter comments for billing
                      println("Enter comments for billing")
                      var comments = scanner.nextLine()
                      //Update table in BillingAccounts
                      var query4 = "INSERT INTO BillAccounts (CustomerId, AccountId, BillAmount, comments) Values (" + new Billing_accounts(customerId, accountId, billingAmount, comments)//CustomerId, AccountId, 
                      log.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'INSERT INTO BillAccounts (CustomerId, AccountId, BillAmount, comments) Values (" + new Billing_accounts(customerId, accountId, billingAmount, comments) + "\n")
                      println(query4)
                      statement.executeUpdate(query4)
                      val resultSet4 = statement.executeQuery("SELECT * FROM BillAccounts;")
                      while ( resultSet4.next() ) {
                        print(resultSet4.getString(1) + " " + resultSet4.getString(2) + " " + resultSet4.getString(3) + " " + resultSet4.getString(4) + " " + resultSet4.getString(5))
                        println()
                      }
                      println("---------------------End of billing information-------------------------")   
                    }else if (transaction == "deposit"){
                      //Enter the amount of deposit
                      println("Enter the amount of deposit : ")
                      var deposit = scanner.nextInt()
                      scanner.nextLine()
                      var query102 = "SELECT * FROM Accounts"
                      log.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'SELECT * FROM Accounts;" + "\n")
                      var resultSet24 = statement.executeQuery(query102) // Change query to your table
                      while ( resultSet24.next() ) {
                        balance = (resultSet24.getInt(5) + deposit)
                      }
                
                      //Update the balance in Account table
                      var query100 = "UPDATE Accounts SET Balance = " + balance + " WHERE CustomerId = " +  customerId + ";"
                      log.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'UPDATE Accounts SET Balance = " + balance + " WHERE CustomerId = " +  customerId + ";'\n")
                      println(query100)
                      statement.executeUpdate(query100)
                      println("-------------------------------Account table-----------------------------------------------")

                      var resultSet23 = statement.executeQuery(query102) // Change query to your table
                      while ( resultSet23.next() ) {
                        print(resultSet23.getString(1) + " " + resultSet23.getString(2) + " " + resultSet23.getString(3)+ " " + resultSet23.getString(4) + " " + resultSet23.getString(5))
                        println()
                      }
                      //
                    }else if(transaction == "nothing") {
                      //Update the balance in Account table
                      var query102 = "SELECT * FROM Accounts"
                      log.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'SELECT * FROM Accounts;" + "\n")
                      var resultSet24 = statement.executeQuery(query102) // Change query to your table
                      while ( resultSet24.next() ) {
                        balance = resultSet24.getInt(5)
                      } 
                      //Update the balance in Account table
                      var query100 = "UPDATE Accounts SET Balance = " + balance + " WHERE CustomerId = " +  customerId + ";"
                      log.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'UPDATE Accounts SET Balance = " + balance + " WHERE CustomerId = " +  customerId + ";'\n")
                      println(query100)
                      statement.executeUpdate(query100)
                      println("-------------------------------Account table-----------------------------------------------")
                      var query101 = "SELECT * FROM Accounts"
                      log.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'SELECT * FROM Accounts;" + "\n")
                      var resultSet23 = statement.executeQuery(query101) // Change query to your table
                      while ( resultSet23.next() ) {
                        print(resultSet23.getString(1) + " " + resultSet23.getString(2) + " " + resultSet23.getString(3)+ " " + resultSet23.getString(4) + " " + resultSet23.getString(5))
                        println()
                      }
                    }
                  println("balance: " + balance)

                  println("----------------------End of Accounts information--------------------------")

                }
              }
            }

          }
          if(!accountTypes.contains(accountType)){
            badData = true
            throw new BadDataEntryException
          }
        }catch {
          case bde : BadDataEntryException => println(accountType + "is not a type of accounts ! Enter a valid departement")
          case e : Exception => println("Exception thrown. Trying again.....")
        }
        
      }
      //Delete row from bank location table
      println("Do you want to delete data from bank location (Y/N)")
      var deleb = scanner.nextLine()
      if(deleb == "Y") {
        println("------------------------------------bank location table-------------------------------------")
        var query50 = "SELECT * FROM BankLocation "
        log.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'SELECT * FROM BankLocation;" + "\n")
        var resultSet200 = statement.executeQuery(query50) // Change query to your table
        while ( resultSet200.next() ) {
            print(resultSet200.getString(1) + " " + resultSet200.getString(2))
            println()
        }
        var bankLocationId = -1
        var badData = true
        while(badData) {
          badData = false
          println("Enter bank location id : ")
          try{
            bankLocationId = scanner.nextInt()
            scanner.nextLine()
            if(bankLocationId <= 0){
              badData = true
              throw new BadDataEntryException
            }

          }catch{
            case bde: BadDataEntryException => println("Please enter a valid bankLocation id ")
            case e: Exception => println("Exception was thrown, trying again...")
          }

        }
        if(bankLocationId >= 0){
          var query51 = "DELETE FROM BankLocation WHERE BranchId = " + bankLocationId + ";"
          log.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'DELETE FROM BankLocation WHERE BranchId = " + bankLocationId + ";'\n")
          var resultSet201 = statement.executeUpdate(query51)
          var query61 = "SELECT * FROM BankLocation"
          log.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'SELECT * FROM BankLocation;" + "\n")
          var resultSet300 = statement.executeQuery(query61)
          while ( resultSet300.next() ) {
            print(resultSet300.getString(1) + " " + resultSet300.getString(2))
            println()
          }

        }
        

      }
      //Delete row from customer table
      println("Do you want to delete data from customer (Y/N)")
      var delec = scanner.nextLine()
      if(delec == "Y") {
        println("------------------------------------Customers table-------------------------------------")
        var query53 = "SELECT * FROM Customers "
        log.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'SELECT * FROM Customers;" + "\n")
        var resultSet203 = statement.executeQuery(query53) // Change query to your table
        while ( resultSet203.next() ) {
            print(resultSet203.getString(1) + " " + resultSet203.getString(2))
            println()
        }
        var customerId = -1
        var badData = true
        while(badData){
          badData = false
          println("Enter customer id : ")
          try{
            customerId = scanner.nextInt()
            scanner.nextLine()
            if(customerId <= 0){
              badData = true
              throw new BadDataEntryException
            }
          }catch {
            case bde: BadDataEntryException => println("Please enter a valid customer id ")
            case e : Exception => println("Exception was thrown, trying again...")
          }
          if(customerId >= 0){
            var query54 = "DELETE FROM Customers WHERE CustomerId = " + customerId + ";"
            log.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'DELETE FROM Customers WHERE CustomerId = " + customerId + ";'\n")
            var resultSet204 = statement.executeUpdate(query54)
            var query62 = "SELECT * FROM Customers"
            log.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'SELECT * FROM Customers;" + "\n")
            var resultSet301 = statement.executeQuery(query62)
            while ( resultSet301.next() ) {
                print(resultSet301.getString(1) + " " + resultSet301.getString(2))
                println()
            }
          }
        }      
        

      }
      //Delete row from account table
      println("Do you want to delete data from account (Y/N)")
      var delea = scanner.nextLine()
      if(delea == "Y") {
        println("------------------------------------Accounts table-------------------------------------")
        var query70 = "SELECT * FROM Accounts "
        log.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'SELECT * FROM Accounts;" + "\n")
        var resultSet400 = statement.executeQuery(query70) // Change query to your table
        while ( resultSet400.next() ) {
            print(resultSet400.getString(1) + " " + resultSet400.getString(2)+ " " + resultSet400.getString(3)+ " " + resultSet400.getString(4)+ " " + resultSet400.getString(5))
            println()
        }
        var accountId = -1
        var badData = true
        while(badData){
          badData = false
          println("Enter account id : ")
          try {
            accountId = scanner.nextInt()
            scanner.nextLine()
            if(accountId <= 0){
              badData = true
              throw new BadDataEntryException
            }
          }catch {
            case bde: BadDataEntryException => println("Please enter a valid account id ")
            case e : Exception => println("Exception was thrown, trying again...")
          }
          if(accountId >= 0){
            var query71 = "DELETE FROM Accounts WHERE AccountId = " + accountId + ";"
            log.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'DELETE FROM Accounts WHERE AccountId = " + accountId + ";'\n")
            var resultSet401 = statement.executeUpdate(query71)
            var query72 = "SELECT * FROM Accounts"
            log.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'SELECT * FROM Accounts;" + "\n")
            var resultSet402 = statement.executeQuery(query72)
            while ( resultSet402.next() ) {
                print(resultSet402.getString(1) + " " + resultSet402.getString(2)+ " " + resultSet402.getString(3)+ " " + resultSet402.getString(4)+ " " + resultSet402.getString(5))
                println()
            }
          }
        }

      }
      //Delete row from account type table
      println("Do you want to delete data from account type (Y/N)")
      var deleac = scanner.nextLine()
      if(deleac == "Y") {
        println("------------------------------------Account type table-------------------------------------")
        var query80 = "SELECT * FROM AccountType"
        log.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'SELECT * FROM AccountType;" + "\n")
        var resultSet500 = statement.executeQuery(query80) // Change query to your table
        while ( resultSet500.next() ) {
            print(resultSet500.getString(1) + " " + resultSet500.getString(2) + " " + resultSet500.getString(3))
            println()
        }
        var badData = true
        var accountTypeId = -1
        while(badData){
          badData = false
          println("Enter account type id : ")
          try{
            accountTypeId = scanner.nextInt()
            scanner.nextLine()
            if(accountTypeId <= 0){
              badData = true
              throw new BadDataEntryException
            }
          }catch{
            case bde: BadDataEntryException => println("Please enter a valid account Type id ")
            case e : Exception => println("Exception was thrown, trying again...")
          }
          if(accountTypeId >= 0){
            var query81 = "DELETE FROM AccountType WHERE AccountTypeId = " + accountTypeId + ";"
            log.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'DELETE FROM AccountType WHERE AccountTypeId = " + accountTypeId + ";'\n")
            var resultSet501 = statement.executeUpdate(query81)
            var query82 = "SELECT * FROM AccountType"
            log.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'SELECT * FROM AccountType;" + "\n")
            var resultSet502 = statement.executeQuery(query82)
            while ( resultSet502.next() ) {
                print(resultSet502.getString(1) + " " + resultSet502.getString(2) + " " + resultSet502.getString(3))
                println()
            }
          }

        }
      }
      //Delete row from billing accounts table
      println("Do you want to delete data from billing table (Y/N)")
      var delebt = scanner.nextLine()
      if(delebt == "Y") {
        println("------------------------------------billing table-------------------------------------")
        var query90 = "SELECT * FROM BillAccounts "
        log.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'SELECT * FROM BillAccounts;" + "\n")
        var resultSet600 = statement.executeQuery(query90) // Change query to your table
        while ( resultSet600.next() ) {
            print(resultSet600.getString(1) + " " + resultSet600.getString(2) + " " + resultSet600.getString(3) + " " + resultSet600.getString(4) + " " + resultSet600.getString(5))
            println()
        }
        var billingId = -1
        var badData = true
        while(badData){
          badData = false
          println("Enter billing id : ")
          try{
            billingId = scanner.nextInt()
            scanner.nextLine()
            if(billingId <= 0){
              badData = true
              throw new BadDataEntryException
            }
          }catch {
            case bde: BadDataEntryException => println("Please enter a valid account Type id ")
            case e : Exception => println("Exception was thrown, trying again...")
          }
          if(billingId >= 0){
            var query91 = "DELETE FROM BillAccounts WHERE BillingId = " + billingId + ";"
            log.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'DELETE FROM BillAccounts WHERE BillingId = " + billingId +  ";'\n")
            var resultSet604 = statement.executeUpdate(query91)
            var query92 = "SELECT * FROM Customers"
            log.write(Calendar.getInstance().getTimeInMillis + " - Excecuting 'SELECT * FROM Customers;" + "\n")
            var resultSet601 = statement.executeQuery(query92)
            while ( resultSet601.next() ) {
                print(resultSet601.getString(1) + " " + resultSet601.getString(2) + " " + resultSet601.getString(3) + " " + resultSet601.getString(4) + " " + resultSet601.getString(5))
                println()
            }
          }
        }        

      }
   } catch {
          case e: Exception => e.printStackTrace
      } 
        connection.close()
        log.close()
  }
}

