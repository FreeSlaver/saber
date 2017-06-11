package com.song.saber.office.excel;

import java.io.Serializable;

/**
 * Created by 00013708 on 2017/6/9.
 *
 */
public class Student implements Serializable{
  private String name;

  private int age;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  @Override public String toString() {
    return "name:"+name+",age:"+age;
  }
}
