Car Dealer App
Purpose: WGU Capstone

This application allows tracking of part and car inventory. It also allows creating customers and appointments.

Author: Tanner Freemon
Email tanner2110@gmail.com
Version 1.0

Created on IntelliJ IDEA Community 2020.2
IntelliJ IDEA 2020.3.1 (Community Edition)
Build #IC-203.6682.168, built on December 29, 2020
Runtime version: 11.0.9.1+11-b1145.63 amd64
VM: OpenJDK 64-Bit Server VM by JetBrains s.r.o.
Windows 10 10.0
GC: ParNew, ConcurrentMarkSweep
Memory: 1977M
Cores: 8
Non-Bundled Plugins: org.jetbrains.kotlin

To run this program via the jar file, you must have either java run time environment installed on your computer or java development kit 15 installed on your computer.
You must also have the javafx library. I will include this in the folder. You will have to change the path in the batch file I will include to where you have the jar file, and the
javafx folder. The jar file is the executable that will start the program. The javafx folder is required for the program to run. You will have to edit the batch file, called carProgramBat, right click on it,
click edit. It will loook like the line below. You will need to change the path below in two spots. Change C:\Users\Tanner\Documents\javafx-sdk-11.0.2\lib to where your java fx folder is located and include it up to the lib folder.
next change, "C:\Users\Tanner\Documents\CarDealerApp\out\artifacts\Scheduling_App_jar\Scheduling App.jar" to where you have the jar application. So it should look exactly like the line below, but it will have your computers
location stuff for the folder and jar file. Save the bat file. you should be able to double click on it and it will start the program.

java --module-path C:\Users\Tanner\Documents\javafx-sdk-11.0.2\lib --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.web -jar "C:\Users\Tanner\Documents\CarDealerApp\out\artifacts\Scheduling_App_jar\Scheduling App.jar"


If this method does not work for you. You can still run the program via intelij. I used JDK 15 for this project. You will have to add the java fx folder to your project structure, go to intelij, file, project structure, click on libraries
click the plus sign, navigate to your javafx folder to select it, click on it, click on the lib folder, then click done. This will create the link to your javafx folder. Next, you must add the sql connector. Go back to libraries under
project structure, and add this connector mysql:mysql-connector-java:5.1.40. Next you must go to intelij, the run tab, click on it, click on edit configurations, then you must find the box that says vm options
and paste in this,        --module-path C:\Users\Tanner\Documents\javafx-sdk-11.0.2\lib --add-modules javafx.fxml,javafx.controls,javafx.graphics
You must change the location of the java fx folder to where yours is located, then click save, make sure it goes up to the lib folder like mine. Now you should be able to run the application in intelij.

General user instructions
First you log onto the application with test and test as the username and password. On, the first screen you can see all appointments. You can narrow them down by month with the month box. You can also search appointments within a week with the button named that.
If you click the reports button, you can search by appointment type and month. You can also do the same for customers and the month of their appointments. If you go back and click generate schedule, you can choose a garage and month and generate a schedule in the table view for that specific garage.
Hit back, and you can click on the inventory button to view parts and car inventory. Here you can add different parts or cars, and you can also search for them as well by id or name.  Click add to add cars or parts, and to modify, select an item and click modify. If you hit the appointments button, it will take you back to the main screen.
Next click on the customers and appointments button. Here you can add new customers. First you must click the add new customer button, then enter their information. If you wish to modify a customer, click on the customer in the table, and their info will show up on the left and you will be able to modify it.
Once you are done modifying the data, click save. To add an appointment, click on a customer, then click add appointment. To modify an appointment, click on a customer, then click on the appointment, then click modify. To delete a customer or appointment, click on the row and click the respective delete button.
On the appointment screen, you select a garage for the appointment, enter information and select what type of car the customer has.
The car will bring up associated parts which you can add to a list to be associated with the appointment. If a time slot is taken, it will not let you schedule an appointment during that time, you must schedule it 15 minutes after or before the conflicting one.
Or you can see if another garage is available. To do this, you must change the garage and try to save the appointment. If the time is available for the different garage, it will save. If parts are not available for an appointment, you will not be able to schedule an appointment, unless you schedule it two weeks out to allow time for parts to arrive.
Appointments must be within business hours, which is east coast time 8 to 10 pm. Scheduling an appointment with parts will remove them from inventory. Once a new order of parts arrives, you must manually reset the stock of the part or car in the inventory screen. Certain tables will be expandable if you increase the size of your viewing window.
You can do this by dragging the window vertically down. You can also view a log of who has logged on to the program in a special log file named login activity. Also if you are adding required parts for an appointment, if you
add one you need to remove, you must cancel and restart creating the appointment. If a part inventory says negative, it will not affect teh performance of the program. When you reset the part inventory when a new shipment comes,
it will reset this number to the proper inventory level. Whenever you modify a part's inventory, the number you enter will be the inventory level. There is no adding to the inventory level.

Maintenance of the app is simple. When a new order of parts is delivered, or a new shipment of cars arrives. You go to the inventory and update the levels. You must also delete all appointments for the previous day to keep them from building up. 


