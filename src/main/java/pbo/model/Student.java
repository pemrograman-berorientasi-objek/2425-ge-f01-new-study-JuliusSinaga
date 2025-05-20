package pbo.model;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.*;


@Entity
@Table(name = "student")
public class Student {

  @Id
  @Column(name = "nim", length = 10, nullable = false)
  private String nim;

  @Column(name = "nama_student", length = 50, nullable = false)
  private String nama;

  @Column(name = "prodi", length = 50, nullable = false)
  private String prodi;

  @ManyToMany
  @JoinTable(name = "STU_COUR", 
    joinColumns = 
        @JoinColumn(name = "STU_NIM", referencedColumnName = "nim"),
    inverseJoinColumns =
        @JoinColumn(name = "COUR_KODE", referencedColumnName = "kode")
  )
  private List<Course> courses;

  public Student () {

  }

  public Student(String nim, String nama, String prodi) {
    this.nim = nim;
    this.nama = nama;
    this.prodi = prodi;
  }

  // Getter
  public String getNim() {
    return nim;
  }

  public String getNama() {
    return nama;
  }

  public String getProdi() {
    return prodi;
  }

  public List<Course> getCourses() {
    return courses;
  }

  // Setter
  public void setNim(String nim) {
    this.nim = nim;
  }

  public void setNama(String nama) {
    this.nama = nama;
  }

  public void setProdi(String prodi) {
    this.prodi = prodi;
  }

  public void setCourses(List<Course> courses) {
    this.courses = courses;
  }  

  @Override
  public String toString() {
    if(courses == null){
      System.out.println(nim + "|" + nama + "|" + prodi);
      List<Course> sortCourses = courses.stream().sorted(Comparator.comparing(Course::getSemester)
                                                    .thenComparing(Course::getKredit))
                                          .collect(Collectors.toList());
        for (Course course : sortCourses) {
          System.out.println(course.toString());
        }
      return "";

    } else {
      return nim + "|" + nama + "|" + prodi;
    }
  }
}
