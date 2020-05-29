package br.com.theoldpinkeye.exemplobinding;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import br.com.theoldpinkeye.exemplobinding.models.UserInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  // Instanciar os objetos a serem manipulados em código
  EditText edtName;
  EditText edtPassword;
  EditText edtConfirmPassword;
  EditText edtEmail;
  CheckBox chkAccept;
  Button btnConfirm;
  List<UserInfo> users;
  Button btnList;

  // Criando novo método para chamar a Lista de cadastrados
  public void chamaListActivity(View view){

    // testando funcionamento do botão
    Log.i("Mensagem", "Eu funciono!");

    // Criando o intent, instanciando com new Intent
    // e passando contexto atual (this)
    // e a classe da activity de destino
    Intent intent = new Intent(this, ListActivity.class);

    // criando a mensagem a ser enviada
    String mensagem = "Batatinha quando nasce...";

    // colocando essa mensagem como um Extra dentro do Intent
    intent.putExtra("Mensagem", mensagem);

    // Ajustando a inserção do Extra para contemplar a interface Parcelable
    intent.putParcelableArrayListExtra("Lista", (ArrayList<? extends Parcelable>) users);

    // Startando a activity com um intent como parâmetro
    startActivity(intent);
  }




  // criando uma implementação própria do OnClickListener
  // esse é um método reaproveitável
  private OnClickListener meuClickListener = (v) -> {

    // criando uma mensagem de log com os dados das Views
    Log.e("Dados","Nome: " + edtName.getText() + " Senha: " + edtPassword.getText() +
        " Confirmação: " + edtConfirmPassword.getText() + " Aceite: " + chkAccept.isChecked());

    // criando um objeto temporário para receber os valores dos campos do form
    UserInfo user = new UserInfo(edtName.getText().toString(),
                                edtPassword.getText().toString(),
                                edtConfirmPassword.getText().toString(),
                                edtEmail.getText().toString(),
                                chkAccept.isChecked());

    // adicionando os valores recebidos à lista de usuários
    users.add(user);

    // limpando o formulário para um novo cadastro
    edtName.setText("");
    edtPassword.setText("");
    edtConfirmPassword.setText("");
    chkAccept.setChecked(false);
    edtEmail.setText("");

    // atribuindo o foco do formulário ao edtName
    edtName.requestFocus();

    // imprime no Logcat todos os registros da List
    // Lê-se: para cada UserInfo em users, faça
    for (UserInfo u: users){
      Log.d("Usuario",u.toString());
    }


  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // O Binding é ligar os componentes do Layout a componentes de código java
    // Fazendo o Binding dos elementos no modo padrão do Android
    edtName = findViewById(R.id.editTextName);
    edtPassword = findViewById(R.id.editTextPassword);
    edtConfirmPassword = findViewById(R.id.editTextConfirmPassword);
    edtEmail = findViewById(R.id.editTextEmail);
    chkAccept = findViewById(R.id.checkBoxAccept);
    btnConfirm = findViewById(R.id.buttonConfirm);
    btnList = findViewById(R.id.verListaButton);


    // como adicionar o evento click num botão android java

    // Adicionando o onClickListener criado acima ao botão
    btnConfirm.setOnClickListener(meuClickListener);

    // Instanciando a List como ArrayList para funcionalidades adicionais
    users = new ArrayList<>();

    if(restorePrefs(this.getPreferences(Context.MODE_PRIVATE)) != null){
      users = deserializeJson(restorePrefs(this.getPreferences(Context.MODE_PRIVATE)));
    }

  }

  // método que serializa o ArrayList para JSON
  private String serializeList(List<UserInfo> users){
    // 1º passo - Criar e instanciar um objeto Gson
    Gson gson = new Gson();
    // convertendo nossa lista para json e retornando essa string
    Log.e("Json gerado", gson.toJson(users));

    return gson.toJson(users);
  }

  // método que deserializa o Json
  private List<UserInfo> deserializeJson(String json){
    // 1º passo - Criar e instanciar um objeto Gson
    Gson gson = new Gson();
    // definindo qual tipo de objeto o Gson deve se basear para deserializar os dados
    Type userListType = new TypeToken<ArrayList<UserInfo>>(){}.getType();
    // convertendo nosso json em List<UserInfo> e retornando essa List

    Log.e("Json deserializado", gson.fromJson(json, userListType).toString());

    // convertendo nosso json em List<UserInfo> e retornando essa List
    return gson.fromJson(json, userListType);
  }

  public void saveSharedPrefs(String jsonSave){
    // criando uma variável para receber as preferências atuais do App
    SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
    // Editando as preferências pra colocar os dados a serem salvos
    SharedPreferences.Editor editor = sharedPref.edit();
    // colocando uma String dentro do SharedPreferences
    editor.putString("users", jsonSave);
    // validando a inserção dos valores
    editor.apply();
  }

  public String restorePrefs(SharedPreferences data){
    return data.getString("users", null);
  }

  @Override
  protected void onPause() {
    super.onPause();

    // mandando salvar SharedPreferences
    saveSharedPrefs(serializeList(users));
  }

  @Override
  protected void onResume() {
    super.onResume();

    restorePrefs(this.getPreferences(Context.MODE_PRIVATE));
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();

    // mandando salvar SharedPreferences
    saveSharedPrefs(serializeList(users));
  }


}
