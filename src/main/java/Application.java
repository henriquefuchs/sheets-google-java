import service.ExamService;
import service.InputService;
import service.UpdateService;

import java.util.List;

public class Application {

  public static void main(String[] args) {
    String spreadsheetId = "1ROMWnrNEZ8PM4KjvveNDRcIBW8diSCe8b-Qcln2VjqY";
    String target = "engenharia_de_software!A4:F27";

    System.out.println("Google Sheet API Java Client");
    System.out.println();
    System.out.println("Spreadsheet ID: " + spreadsheetId);
    System.out.println("Target: " + target);
    System.out.println();

    InputService inputService = new InputService(spreadsheetId, target);
    inputService.readingSetUp();

    System.out.println("Reading the spreadsheet");
    List<List<Object>> resultsList = inputService.listAll();

    System.out.println("Calculating the values");
    inputService.setLectures();
    ExamService examService = new ExamService(resultsList);
    List<List<Object>> writingValues = examService.studentSituation();

    System.out.println("Writing on the spreadsheet");
    target = "engenharia_de_software!G4:H27";
    UpdateService updateService = new UpdateService(spreadsheetId, target);
    updateService.writeData(writingValues);

    System.out.println("Execution finished!");
  }
}
