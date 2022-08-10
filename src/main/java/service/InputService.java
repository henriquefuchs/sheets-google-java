package service;

import com.google.api.services.sheets.v4.Sheets;
import security.Authentication;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputService {

  private String spreadsheetId;
  private final String target;
  private static String lectures;

  public InputService(String spreadsheetId, String target) {
    this.spreadsheetId = spreadsheetId;
    this.target = target;
  }

  public void readingSetUp() {

    boolean changedFromDefault = false;

    try (Scanner input = new Scanner(System.in)) {
      System.out.println("Would you like to change the target Spreadsheet? (y / n)");
      System.out.println("Note that you must be the owner of the spreadsheet to edit it.");
      System.out.println("Google will ask you for permission to edit your Sheets document (login in browser).");
      String wantsNewSpreadsheet = input.next();

      if (wantsNewSpreadsheet.equals("y")) {
        System.out.print("New target Spreadsheet ID: ");
        spreadsheetId = input.next();
        changedFromDefault = true;

      } else {
        System.out.println("Using default Spreadsheet ID.\n");
      }

    } catch (Exception e) {
      System.err.println("Something went wrong, please try again.");
    }

    if (changedFromDefault) {
      System.out.println("\n");
      System.out.println("The target Spreadsheet ID is: " + spreadsheetId);
      System.out.println("Target: " + target);
    }
  }


  public List<List<Object>> listAll() {

    List<List<Object>> resultsList = new ArrayList<>();

    try {
      Authentication studentAuthentication = new Authentication();
      Sheets clientService = studentAuthentication.clientService();

      List<List<Object>> values = studentAuthentication.readSheet(clientService, spreadsheetId, target);
      if (values == null || values.isEmpty()) {
        System.out.println("No data found.");
      } else {
        System.out.println("");
        System.out.println(values.size() + " student(s) found.");
        for (List<Object> row : values) {
          System.out.println("Mat., Aluno");
          System.out.printf(" %s , %s\n", row.get(0), row.get(1));
          System.out.println(" Falt , P1 , P2, P3 ");
          System.out.printf("   %s , %s , %s , %s \n\n", row.get(2), row.get(3), row.get(4), row.get(5));
        }
      }
      resultsList = values;
    } catch (IOException | GeneralSecurityException e) {
      throw new RuntimeException(e.getMessage());
    }

    return resultsList;
  }

  public void setLectures() {

    String lectureRange = target.substring(0, 23) + "A2"; // "engenharia_de_software!A2"

    try {
      Authentication studentAuthentication = new Authentication();
      Sheets clientService = studentAuthentication.clientService();

      List<List<Object>> values = studentAuthentication.readSheet(clientService, spreadsheetId, lectureRange);
      if (values == null || values.isEmpty()) {
        System.out.println("No data found.");
      } else {
        System.out.println("");
        System.out.println("Lectures found.");
        for (List<Object> row : values) {
          InputService.lectures = (String) row.get(0);
        }
      }
    } catch (IOException | GeneralSecurityException e) {
      System.err.println("Something went wrong, please try again. Java class: " + e.getMessage());
    }
  }

  public static int getLectures() {
    return Integer.parseInt(lectures.substring(28, 30));
  }
}
