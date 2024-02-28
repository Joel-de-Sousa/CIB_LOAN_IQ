This is a MVC Thymeleaf Spring application that was developed to test basic functionalities for https requests.
In order to test this application download the ZIP folder or clone it to a folder.

Open the folder with an IDE (this was developed with IntelliJ Ultimate)
Let the IDE resolve any dependencies issues, if that doesn't automatically start type in the console
mvn clean install -U (this will force update the project)
Run the program and open a browser with the url: "localhost:8080"

Create a Transfer:
Click "Create a new transfer" button to start a new transfer. Select a date and an amount according the information given in the page.
Click on the "Submit" button and it will show the information on the home screen with the bank fee already calculated.

Delete a Transfer:
You can delete a transfer by clicking the button "Delete" on each row. It will take the transfer ID and use that to delete it from the repository.

Edit a Transfer:
To edit a transfer, click on the button "Edit" and change the date and amount to your needs taking into account the information on the screen.
Click on the button "Edit" to submit the new information and will update the information on the screen with the new bank fee.
