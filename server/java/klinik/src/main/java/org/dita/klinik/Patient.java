package org.dita.klinik;

import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
// import javax.persistence.IdClass;
import lombok.Data;

@Entity
@Table(name = "`dita_table_patients`")
// @IdClass(CompositeKey.class)
@Data
class Patient {

  private @Id @GeneratedValue Long id;
  // private @Id String name;
  private String name;
  private String birthDate;
  private String phoneNumber;

  Patient() {}

  Patient(String name, String birthDate, String phoneNumber) {

    this.name = name;
    this.birthDate = birthDate;
    this.phoneNumber = phoneNumber;
  }

  public Long getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public String getBirthDate() {
    return this.birthDate;
  }
  
  public String getPhoneNumber() {
    return this.phoneNumber;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setBirthDate(String birthDate) {
    this.birthDate = birthDate;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

}