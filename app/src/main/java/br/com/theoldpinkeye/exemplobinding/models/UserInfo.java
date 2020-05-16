package br.com.theoldpinkeye.exemplobinding.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

public class UserInfo implements Parcelable {

  // variáveis que irão receber os dados do formulário
  private String name;
  private String password;
  private String confirmPassword;
  private String email;
  private boolean accept;


  // constructor permite criar objetos já passando todos os dados pros campos
  public UserInfo(String name, String password, String confirmPassword, String email,
      boolean accept) {
    this.name = name;
    this.password = password;
    this.confirmPassword = confirmPassword;
    this.email = email;
    this.accept = accept;
  }

  protected UserInfo(Parcel in) {
    name = in.readString();
    password = in.readString();
    confirmPassword = in.readString();
    email = in.readString();
    accept = in.readByte() != 0;
  }

  public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
    @Override
    public UserInfo createFromParcel(Parcel in) {
      return new UserInfo(in);
    }

    @Override
    public UserInfo[] newArray(int size) {
      return new UserInfo[size];
    }
  };

  // Só serão usados se precisarmos manipular dados já dentro de objetos produto desta classe
  public String getName() {
    return name;
  }

  public UserInfo setName(String name) {
    this.name = name;
    return this;
  }

  public String getPassword() {
    return password;
  }

  public UserInfo setPassword(String password) {
    this.password = password;
    return this;
  }

  public String getConfirmPassword() {
    return confirmPassword;
  }

  public UserInfo setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
    return this;
  }

  public String getEmail() {
    return email;
  }

  public UserInfo setEmail(String email) {
    this.email = email;
    return this;
  }

  public boolean isAccept() {
    return accept;
  }

  public UserInfo setAccept(boolean accept) {
    this.accept = accept;
    return this;
  }

  // toString é usado para imprimir os dados da classe

  @NonNull
  @Override
  public String toString() {
    return "UserInfo{" +
        "name='" + name + '\'' +
        ", password='" + password + '\'' +
        ", confirmPassword='" + confirmPassword + '\'' +
        ", email='" + email + '\'' +
        ", accept=" + accept +
        '}';
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(name);
    dest.writeString(password);
    dest.writeString(confirmPassword);
    dest.writeString(email);
    dest.writeByte((byte) (accept ? 1 : 0));
  }
}
