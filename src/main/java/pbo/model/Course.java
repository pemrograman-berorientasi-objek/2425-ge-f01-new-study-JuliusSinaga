package pbo.model;

import java.util.*;

import javax.persistence.*;

@Entity
@Table(name = "course")
public class Course {

  @Id
  @Column(name = "kode", length = 10, nullable = false)
  private String kode;

  @Column(name = "nama_matakuliah", length = 50, nullable = false)
  private String nama;

  @Column(name = "semester", nullable = false)
  private int semester;

  @Column(name = "kredit", nullable = false)
  private int kredit;

  @ManyToMany(mappedBy = "courses")
  private List<Student> students;

  public Course () {

  }

  public Course(String kode, String nama, int semester, int kredit) {
    this.kode = kode;
    this.nama = nama;
    this.semester = semester;
    this.kredit = kredit;
  }

  // Getter
  public String getKode() {
    return kode;
  }

  public String getNama() {
    return nama;
  }

  public int getSemester() {
    return semester;
  }

  public int getKredit() {
    return kredit;
  }

  public List<Student> getStudents() {
    return students;
  }

  // Setter
  public void setKode(String kode) {
    this.kode = kode;
  }

  public void setNama(String nama) {
    this.nama = nama;
  }

  public void setSemester(int semester) {
    this.semester = semester;
  }

  public void setKredit(int kredit) {
    this.kredit = kredit;
  }

  public void setStudents(List<Student> students) {
    this.students = students;
  }

  @Override
  public String toString() {
    return kode + "|" + nama + "|" + semester + "|" + kredit;
  }
}
