import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.sql.SQLContext
import java.sql.DriverManager
import java.sql.Connection
import java.util.Scanner
import java.util.Base64
import java.util.Base64.Encoder
import java.util.Base64.Decoder
import sun.misc.{BASE64Encoder, BASE64Decoder}





object SportApp {
    def main(args: Array[String]): Unit = {
        // This block of code is all necessary for spark/hive/hadoop
        System.setSecurityManager(null)
        System.setProperty("hadoop.home.dir", "C:\\hadoop\\") // change if winutils.exe is in a different bin folder
        val conf = new SparkConf()
            .setMaster("local") 
            .setAppName("SportApp")    //  app name 
        val sc = new SparkContext(conf)
        sc.setLogLevel("ERROR")
        val hiveCtx = new HiveContext(sc)
        import hiveCtx.implicits._



        // connect to the database named "mysql" on the localhost
        val driver = "com.mysql.jdbc.Driver"
        val url = "jdbc:mysql://localhost:3306/p1" // Modify for whatever port you are running your DB on
        val username = "root"
        val password = "monalisa.123" // Update to include your password

        var connection:Connection = null
        
        Class.forName(driver)
        connection = DriverManager.getConnection(url, username, password)
        val statement = connection.createStatement()

        var scanner = new Scanner(System.in)
        println("Do you want to make an new account (Y/N): ")
        var account = scanner.nextLine()
        if(account == "Y"){
            register()
        }

        println("============================================= Welcom to the login system =================================== ")
        // Method to check login credentials

        val adminCheck = login(connection)
        if (adminCheck) {
            println("======================== Welcome Admin! Loading in data...======================================")
            println("Do you want to change a passowrd or delete (C/D/N) : ")
            var chandelPass = scanner.nextLine()
            if(chandelPass == "C"){
                println("============================== user accounts table ====================================== ")
                val resultSet1 = statement.executeQuery("SELECT * FROM user_accounts;")
                while (resultSet1.next()) {
                    print(resultSet1.getString(1) + " " + resultSet1.getString(2) + " " + resultSet1.getString(3))
                    println()
                }
                println("Enter the Id of this user : ")
                var idUser = scanner.nextLine()
                println("Enter the new password : ")
                var password = scanner.nextLine()
                var query100 = "UPDATE user_accounts SET password =\" " + password + "\" WHERE Id = " +  idUser + ";"
                statement.executeUpdate(query100)
                println("============================== user accounts table ====================================== ")
                val resultSet122 = statement.executeQuery("SELECT * FROM user_accounts;")
                while (resultSet122.next()) {
                    print(resultSet122.getString(1) + " " + resultSet122.getString(2) + " " + resultSet122.getString(3))
                    println()
                } 

            }else if(chandelPass == "D"){
                println("============================== user accounts table ====================================== ")
                val resultSet1 = statement.executeQuery("SELECT * FROM user_accounts;")
                while (resultSet1.next()) {
                    print(resultSet1.getString(1) + " " + resultSet1.getString(2) + " " + resultSet1.getString(3))
                    println()
                }
                /// delete a user account
                println("Enter the id of the user you want to delete : ")
                var idUser = scanner.nextLine()
                                       
                var query51 = "DELETE FROM user_accounts WHERE Id = " + idUser + ";"
                var resultSet201 = statement.executeUpdate(query51)
                println("================================= user accounts table ==================================")
                var query61 = "SELECT * FROM user_accounts"
                var resultSet300 = statement.executeQuery(query61)
                while ( resultSet300.next() ) {
                    print(resultSet300.getString(1) + " " + resultSet300.getString(2))
                    println()
                }
            }
            var back = true
            while(back == true){
                println("============================ Chose a topic you want to see =================================")

                //insertSData(hiveCtx)
                println("(1): Top 10 strikers who played with his teams in 2016")
                println("(2): Top 20 strikers who shot on target with his teams in 2017")
                println("(3): Top 5 strikers who scored the most goals in USA in 2020")
                println("(4): Top 10 clubs shots per match in 2018")
                println("(5): Top 8 strikers who played as substitution with his teams in 2019")
                println("(6): Top 10 strikers have efficent in front of the goal")
                println("(7): Back")
                println("(8): Exit")
                println("Chose a topic you want to see : ")
                var choOne = scanner.nextInt()
                scanner.nextLine()
            
                if(choOne == 1){
                    println("================Top 10 strikers who played with his teams in 2016=======================")
                    att10plyWteam(hiveCtx)
                    back = false
                }else if(choOne == 2){
                    println("================Top 20 strikers who shot on target with his teams in 2017===============")
                    shot10ply(hiveCtx)
                    back = false
                }else if(choOne == 3){
                    println("=================Top 5 strikers who scored the most goals in USA in 2020================")
                    s10plyNam(hiveCtx)
                    back = false
                }else if(choOne == 4){     
                //m10plyWteam(hiveCtx)
                    println("=========================Top 10 clubs shots per match in 2018============================")
                    shPer10Clueffi(hiveCtx)
                    back = false
                }else if(choOne == 5){
                    println("===============Top 8 strikers who played as substition with his teams in 2019=============")
                    substPlayName(hiveCtx)
                    back = false
                }else if(choOne == 6){
                    println("====================Top 10 strikers have efficent in front of the goal====================")
                    effPlayName(hiveCtx)
                    back = false
                }else if(choOne == 7){
                    back = true

                }else if (choOne == 8){
                    back = false 
                
                }else {
                    println("This number doesn't exist in the list ")
                    back = true
                }
                
                sc.stop()

                }



            } else {
                println("=========================== Welcome User! Loading in data...==================================")
                var back = true
                while(back == true){
                    println("============================ Chose a topic you want to see =================================")

                    //insertSData(hiveCtx)
                    println("(1): Top 10 strikers who played with his teams in 2016")
                    println("(2): Top 20 strikers who shot on target with his teams in 2017")
                    println("(3): Top 5 strikers who scored the most goals in USA in 2020")
                    println("(4): Top 10 clubs shots per match in 2018")
                    println("(5): Top 8 strikers who played as substition with his teams in 2019")
                    println("(6): Top 10 strikers have efficent in front of the goal")
                    println("(7): Back")
                    println("(8): Exit")
                    println("Chose a topic you want to see : ")
                    var choOne = scanner.nextInt()
                    scanner.nextLine()
                
                    if(choOne == 1){
                        println("================Top 10 strikers who played with his teams in 2016=======================")
                        att10plyWteam(hiveCtx)
                        back = false
                    }else if(choOne == 2){
                        println("================Top 20 strikers who shot on target with his teams in 2017===============")
                        shot10ply(hiveCtx)
                        back = false
                    }else if(choOne == 3){
                        println("=================Top 5 strikers who scored the most goals in USA in 2020================")
                        s10plyNam(hiveCtx)
                        back = false
                    }else if(choOne == 4){     
                    //m10plyWteam(hiveCtx)
                        println("=========================Top 10 clubs shots per match in 2018============================")
                        shPer10Clueffi(hiveCtx)
                        back = false
                    }else if(choOne == 5){
                        println("===============Top 8 strikers who played as substition with his teams in 2019=============")
                        substPlayName(hiveCtx)
                        back = false
                    }else if(choOne == 6){
                        println("====================Top 10 strikers have efficent in front of the goal====================")
                        effPlayName(hiveCtx)
                        back = false
                    }else if(choOne == 7){
                        back = true

                    }else if (choOne == 8){
                        back = false 
                    
                    }else {
                        println("This number doesn't exist in the list ")
                        back = true
                    }
                    
                    sc.stop()

                    }
        }

      
        connection.close()
            
    }
    def register(): Unit={
        println("======================================== Register ==============================================")
        // connect to the database named "mysql" on the localhost
        val driver = "com.mysql.cj.jdbc.Driver"
        val url = "jdbc:mysql://localhost:3306/p1" // Modify for whatever port you are running your DB on
        val username = "root"
        val password = "monalisa.123" // Update to include your password


        var connection:Connection = null
        try {
            // make the connection
            Class.forName(driver)
            connection = DriverManager.getConnection(url, username, password)
            val statement = connection.createStatement()
            var badData = true
            var scanner = new Scanner(System.in)
            var users = Set("Admin", "Basic")
            var user = ""
            
            while(badData){
                badData = false
                println("chose between Admin or Basic: ")
                try{
                    user = scanner.nextLine()
                    if(! users.contains(user)){
                        badData = true
                        throw new BadDataEntryException
                    }

                }catch{
                    case bde: BadDataEntryException => println("this user is invalide! Enter a valid user")
                    case e: Exception => println("Exception was thrown, trying again..") 
                }
            }
        
            if(user == "Admin"){
                println("================================= New Admin User ====================================")
                var userName = ""
                var password = ""
                var password1 = ""
                var badData = true
                while(badData){
                    badData = false
                    println("Username : ")
                    try{
                        userName = scanner.nextLine()
                        if(userName == ""){
                            badData = true
                            throw new BadDataEntryException
                        }
                    }catch{
                        case bde: BadDataEntryException => println("enter a valid user name ! ")
                        case e: Exception => println("Exception was thrown, trying again...")
                    }
                    
                    try{
                        println("Password : ")
                        password = scanner.nextLine()
                        println("Confirme your password : ")
                        password1 = scanner.nextLine()
                        if(password != password1 || password.length <= 6){
                            badData = true
                            throw new BadDataEntryException
                        }
                        if(password == password1 || password.length >= 6){
                            var query3= "INSERT INTO admin_accounts (userName, password) Values (" + "'" + userName + "', '" + password +"'" +");" 
                            println(query3)
                            statement.executeUpdate(query3)
                        }
                    }catch{
                        case bde: BadDataEntryException => println("Password don't match or too short ")
                        case e : Exception => println("Trying again...")
                    }

                }
 

            }else if(user == "Basic"){
                println("======================================= Basic User ======================================")
                var userName = ""
                var password = ""
                var password1 = ""
                var badData = true
                while(badData){
                    badData = false
                    println("Username : ")
                    try{
                        userName = scanner.nextLine()
                        if(userName == ""){
                            badData = true
                            throw new BadDataEntryException
                        }
                    }catch{
                        case bde: BadDataEntryException => println("enter a valid user name ! ")
                        case e: Exception => println("Exception was thrown, trying again...")
                    }
                    
                    try{
                        println("Password : ")
                        password = scanner.nextLine()
                        println("Confirme your password : ")
                        password1 = scanner.nextLine()
                        if(password != password1 || password.length <= 6){
                            badData = true
                            throw new BadDataEntryException
                        }
                    }catch{
                        case bde: BadDataEntryException => println("Password don't match or too short ")
                        case e : Exception => println("Trying again...")
                    }
                    if(password == password1 || password.length >= 6){
                        var query3= "INSERT INTO user_accounts (userName, password) Values (" + "'" + userName + "', '" + password +"'" + ");" 
                        println(query3)
                        statement.executeUpdate(query3)
                    }
                }
            }
        }catch{
            case e: Exception => e.printStackTrace
        }
        connection.close()
    }
        def insertSData(hiveCtx:HiveContext): Unit = {
            //hiveCtx.sql("LOAD DATA LOCAL INPATH 'input/covid_19_data.txt' OVERWRITE INTO TABLE data1")
            //hiveCtx.sql("INSERT INTO data1 VALUES (1, 'date', 'California', 'US', 'update', 10, 1, 0)")

            // This statement creates a DataFrameReader from your file that you wish to pass in. We can infer the schema and retrieve
            // column names if the first row in your csv file has the column names. If not wanted, remove those options. This can 
            // then be 
            val output = hiveCtx.read
                .format("csv")
                .option("inferSchema", "true")
                .option("header", "true")
                .load("input/DataS.csv")
            output.limit(15).show() // Prints out the first 15 lines of the dataframe

            // output.registerTempTable("data2") // This will create a temporary table from your dataframe reader that can be used for queries. 

            // These next three lines will create a temp view from the dataframe you created and load the data into a permanent table inside
            // of Hadoop. Thus, we will have data persistence, and this code only needs to be ran once. Then, after the initializatio, this 
            // code as well as the creation of output will not be necessary.
        
            output.createOrReplaceTempView("temp_data")
            hiveCtx.sql("create table IF NOT EXISTS temps_table (country STRING, league STRING, club STRING, player_name STRING, matchs_played INT, substition INT, mins INT, goals INT, expect_goal DOUBLE, expect_goalperavr DOUBLE, shots INT, shots_ontarget INT, shots_permatch DOUBLE, shotsontr_permatch DOUBLE, year INT)row format delimited fields terminated by ',' stored as textfile")
            hiveCtx.sql("INSERT OVERWRITE TABLE temps_table SELECT * FROM temp_data")
            

            hiveCtx.sql("SET hive.exec.dynamic.partition.mode=nonstrict")
            hiveCtx.sql("SET hive.enforce.bucketing=false")
            hiveCtx.sql("SET hive.enforce.sorting=false")
            
            //hiveCtx.sql("DROP TABLE Sdata_CountryB")
            hiveCtx.sql("create table IF NOT EXISTS Sdata_CountryB (league STRING, club STRING, player_name STRING, matchs_played INT, substition INT, mins INT, goals INT, expect_goal DOUBLE, expect_goalperavr DOUBLE, shots INT, shots_ontarget INT, shots_permatch DOUBLE, shotsontr_permatch DOUBLE, year INT) PARTITIONED BY (country STRING) CLUSTERED BY (year) INTO 4 BUCKETS row format delimited fields terminated by ',' stored as textfile TBLPROPERTIES(\"skip.header.line.count\"=\"1\")")
            hiveCtx.sql("INSERT OVERWRITE TABLE Sdata_CountryB SELECT league, club, player_name, matchs_played, substition, mins, goals, expect_goal, expect_goalperavr, shots, shots_ontarget, shots_permatch, shotsontr_permatch, year, country FROM temps_table")
            
            // To query the data1 table. When we make a query, the result set ius stored using a dataframe. In order to print to the console, 
            // we can use the .show() method.
            val summary = hiveCtx.sql("SELECT * FROM Sdata_CountryB LIMIT 10")
            summary.show()
    }
    def att10plyWteam(hiveCtx:HiveContext): Unit = {
        var query = "SELECT player_name, MAX(mins) total_Pmin FROM Sdata_CountryB WHERE year=2016 GROUP BY player_name ORDER BY total_Pmin DESC LIMIT 10" 
        val result = hiveCtx.sql(query)
        result.show

    }
    def shot10ply(hiveCtx:HiveContext): Unit = {
        var query = "SELECT player_name, MAX(shots_ontarget) shot_on_target FROM Sdata_CountryB WHERE year=2017 GROUP BY player_name ORDER BY shot_on_target DESC LIMIT 20" 
        val result = hiveCtx.sql(query)
        result.show

    }
    def s10plyNam(hiveCtx:HiveContext): Unit = {
        var query = "SELECT player_name, MAX(goals) goals FROM Sdata_CountryB WHERE Country=\"USA\" AND year = 2020 GROUP BY player_name ORDER BY goals DESC LIMIT 5" 
        
        val result = hiveCtx.sql(query)
        result.show

    }
    //def m10plyWteam(hiveCtx:HiveContext): Unit = {
        //var query = "SELECT player_name, MAX(mins) total_Pmin FROM Sdata_CountryB WHERE year = 2018 GROUP BY player_name ORDER BY total_Pmin DESC LIMIT 3" 
        //val result = hiveCtx.sql(query)
        //result.show

    //}
    def shPer10Clueffi(hiveCtx:HiveContext): Unit = {
        var query = "SELECT club, MAX(shots_permatch) shot_permatch FROM Sdata_CountryB WHERE year=2019 GROUP BY club ORDER BY shot_permatch DESC LIMIT 10" 
        val result = hiveCtx.sql(query)
        result.show

    }

    def substPlayName(hiveCtx:HiveContext): Unit = {
        var query = "SELECT player_name, MAX(substition) total_Psust FROM Sdata_CountryB WHERE year=2020 GROUP BY player_name ORDER BY total_Psust DESC LIMIT 8" 
        val result = hiveCtx.sql(query)
        result.show

    }

    def effPlayName(hiveCtx:HiveContext): Unit = {
        var query = "SELECT player_name, (MIN(shots) - Max(goals)) eff_goals FROM Sdata_CountryB WHERE year=2020 GROUP BY player_name ORDER BY eff_goals DESC LIMIT 10" 
        val result = hiveCtx.sql(query)
        result.show

    }
    def login(connection: Connection): Boolean = {
        
        while (true) {
            val statement = connection.createStatement()
            val statement2 = connection.createStatement()

            println("Enter username: ")
            var scanner = new Scanner(System.in)
            var username = scanner.nextLine().trim()

            println("Enter password: ")
            var password = scanner.nextLine().trim()
            //var passowrd1 =Base64.getEncoder.encode(password) 
         
            val resultSet = statement.executeQuery("SELECT COUNT(*) FROM admin_accounts WHERE username='"+username+"' AND password='"+password+"';")
            while ( resultSet.next() ) {
                if (resultSet.getString(1) == "1") {
                    return true;
                }
            }

            val resultSet2 = statement2.executeQuery("SELECT COUNT(*) FROM user_accounts WHERE username='"+username+"' AND password='"+password+"';")
            while ( resultSet2.next() ) {
                if (resultSet2.getString(1) == "1") {
                    return false;
                }
            }

            println("Username/password combo not found. Try again!")
        }
        return false
    }
    
}