package br.com.theoldpinkeye.exemplobinding;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import br.com.theoldpinkeye.exemplobinding.models.UserInfo;
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

  // criando uma implementação própria do OnClickListener
  // esse é um método reaproveitável
  private OnClickListener corkyListener = (v) -> {

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
    edtName.isFocused();

    // imprime todos os registros da List
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

    // como adicionar o evento click num botão android java

    // Adicionando o onClickListener criado acima ao botão
    btnConfirm.setOnClickListener(corkyListener);

    // Instanciando a List como ArrayList para funcionalidades adicionais
    users = new ArrayList<>();

  }
}
