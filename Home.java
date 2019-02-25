import org.json.JSONObject;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;

/**
*the Home class is where the user navigates to different tabs, sees the current goals and set news ones
*/
public class Home {
  /**
  * checks if the user.json data file exists within the root directory
  */
  public static boolean userFileExists() {
    //checks if file exists and if it doesn't, print message
    File userFile = new File("./user.json");
    if(userFile.exists()) {
      return true;
    }
    return false;
  }

  /**
  * prompts the user for navigation to different classes (Goals, Expenses, Today, etc)
  */
  public static void navigation() {

    System.out.println("To navigate TrackR+, use the following commands: ");
    System.out.println("Press 't' to access the today's tab");
    System.out.println("Press 'g' to access your current goals or set a new one");
    System.out.println("Press 'e' to access your expense history");
    System.out.println("Press 'q' to quit the application");

    // keep taking user input until the user enters a valid option
    Scanner navInput = new Scanner(System.in);
    char option;

    //doesn't allow user to enter char other than t,g,e or q
    while (true) {
      option = navInput.next().toLowerCase().charAt(0);
      if(option=='t'||option=='g'||option=='e'||option=='q'){
        break;
      }
      else {
        // System.out.println(option);
        option = navInput.next().toLowerCase().charAt(0);
      }
    }

    switch(option) {
      case 't':
        Today.todayNavigation();
        break;
      case 'g':
        Goals.goalsNavigation();
        break;
      case 'e':
        Expenses.expensesNavigation();
        break;
      case 'q':
        System.out.print("Goodbye\n");
        System.exit(0);
      default:
        System.out.print("Invalid option. Please select one of the navigation options.\n");
    }

  }

  public static JSONObject createJSONObject() {
    try {
      // read the user.json file as a String
      String jsonContent = new Scanner(new File("./user.json")).useDelimiter("\\Z").next();
      // create a JSONObject from the String `jsonContent`
      JSONObject userData = new JSONObject(jsonContent);
      return userData;
    }
    // catch the exception if `user.json` doesn't exist (can't happen because Login takes care of that)
    catch (FileNotFoundException error){
      error.printStackTrace();
    }
    return null;
  }

  /**
  * put the userData JSONObject (after updating it and stuff) back into the user.json file
  */
  public static void putJSONObjectIntoFile(JSONObject data) {
    try {
      File userFile = new File("user.json");
      FileWriter fileWriter = new FileWriter(userFile.getAbsoluteFile());
      String jsonText = data.toString();
      fileWriter.write(jsonText);
      // close the fileWriter to release the system resources being used and to prevent potential file corruption
      fileWriter.close();
    }
    catch (FileNotFoundException error) {
      error.printStackTrace();
    }
    catch (IOException error) {
      error.printStackTrace();
    }
  }

  public static void main(String[] args) {
    System.out.print("Welcome to TrackR+\n");

    if(userFileExists()) {
      navigation();
    }
    else {
      Login.userLogin();
      navigation();
    }
  }
}