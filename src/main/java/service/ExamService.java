package service;

import model.Student;

import java.util.ArrayList;
import java.util.List;

public class ExamService {
  private final List<List<Object>> resultList;

  public ExamService(List<List<Object>> resultList) {
    this.resultList = resultList;
  }

  public List<List<Object>> studentSituation() {
    List<List<Object>> writingValues = new ArrayList<>();

    int numberStudents = resultList.size();
    int count = 0;

    for (List<Object> row : resultList) {

      List<Object> situationResults = new ArrayList<>();

      Student student = new Student();

      student.setSituation(row);

      situationResults.add(student.getSituacao());
      situationResults.add(student.getNaf());

      writingValues.add(situationResults);

      count++;
      System.out.println(count + " of " + numberStudents + " done. ");
    }
    return writingValues;
  }
}
