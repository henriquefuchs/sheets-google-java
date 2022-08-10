package service;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import security.Authentication;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public class UpdateService {
  private final String spreadsheetId;
  private final String range;

  public UpdateService(String spreadsheetId, String range) {
    this.spreadsheetId = spreadsheetId;
    this.range = range;
  }

  public void writeData(List<List<Object>> writingValues) {
    try {
      Authentication studentController = new Authentication();
      Sheets clientService = studentController.clientService();

      UpdateValuesResponse result = studentController.writeData(spreadsheetId, range, writingValues, clientService);
      System.out.println("\n");
      System.out.printf("%d cells updated. \n", result.getUpdatedCells());
    } catch (IOException | GeneralSecurityException excep) {
      System.err.println("Something went wrong, please try again. Java class: " + excep.toString());
    }
  }
}
