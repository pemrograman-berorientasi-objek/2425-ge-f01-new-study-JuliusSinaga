package pbo;


import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.persistence.*;
import pbo.model.*;
/**
 * Main class
 *
 */
public class App {

  private static EntityManagerFactory factory;
  private static EntityManager entityManager;
  
  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    
    factory = Persistence.createEntityManagerFactory("study_plan_pu");
    entityManager = factory.createEntityManager();
    
    // Untuk membersihkan data pada tabel
    String[] sql = {
        "DELETE FROM Student",
        "DELETE FROM Course"
    };
    entityManager.getTransaction().begin();
    for(String s : sql){
        entityManager.createQuery(s).executeUpdate();
    }
    entityManager.getTransaction().commit();

    while(scan.hasNext()) {
      String commandLine = scan.nextLine();

      if (commandLine.equals("---")) {
        break;
      }
      
      String[] strTemp = commandLine.split("#");
      switch (strTemp[0]){
        case "student-add":
            String tempNim = strTemp[1];
            String tempNama = strTemp[2];
            String tempProdi = strTemp[3];

            entityManager.getTransaction().begin();
            Student tempStudent;
            if ((tempStudent = entityManager.find(Student.class, tempNim)) == null) {
              Student student = new Student(tempNim, tempNama, tempProdi);
              entityManager.persist(student);
            } else {
              if (!tempStudent.getNim().equals(tempNim)) {
                Student student = new Student(tempNim, tempNama, tempProdi);
                entityManager.persist(student);
              }
            }
            entityManager.getTransaction().commit();
            break;
        case "course-add":
            String tempKode = strTemp[1];
            String tempNamaMK = strTemp[2];
            int tempSemester = Integer.parseInt(strTemp[3]);
            int tempKredit = Integer.parseInt(strTemp[4]);

            entityManager.getTransaction().begin();
            Course tempCourse;
            if ((tempCourse = entityManager.find(Course.class, tempKode)) == null) {
                Course course = new Course(tempKode, tempNamaMK, tempSemester, tempKredit);
                entityManager.persist(course);
            } else {
              if (!tempCourse.getKode().equals(tempKode)) {
                Course course = new Course(tempKode, tempNamaMK, tempSemester, tempKredit);
                entityManager.persist(course);
              }
            }
            entityManager.getTransaction().commit();
            break;
        case "enroll":
            String tempNimStudent = strTemp[1];
            String tempKodeMK = strTemp[2];

            entityManager.getTransaction().begin();
            Student student = entityManager.find(Student.class, tempNimStudent);
            Course course = entityManager.find(Course.class, tempKodeMK);
            if (student != null && course != null) {
              student.getCourses().add(course);
              course.getStudents().add(student);
              entityManager.persist(student);
              entityManager.persist(course);
              entityManager.getTransaction().commit();
            } else {
              entityManager.getTransaction().rollback();
            }
            break;
        case "student-show":
            String tempNimStu = strTemp[1];

            entityManager.getTransaction().begin();
            Student studentt = entityManager.find(Student.class, tempNimStu);
            studentt.toString();
            entityManager.getTransaction().commit();
            break;
        case "student-show-all":
            String studentSql = "SELECT s FROM Student s ORDER BY s.nim";
            List<Student> studentList = entityManager.createQuery(studentSql, Student.class).getResultList();
            List<Student> sortStudent = studentList.stream().sorted(Comparator.comparing(Student::getNim))
                                                          .collect(Collectors.toList());
            for(Student tempStu : sortStudent){
              System.out.println(tempStu.toString());
            }
            break;
        default:
            System.out.println("Invalid Input!");
      }
      
    }

    scan.close();
    entityManager.close();
    factory.close();
  }
}
