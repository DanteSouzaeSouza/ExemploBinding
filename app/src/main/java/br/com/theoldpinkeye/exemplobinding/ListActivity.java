package br.com.theoldpinkeye.exemplobinding;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import br.com.theoldpinkeye.exemplobinding.models.UserInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

  // criando elementos para uso
  ListView listView;
  List<UserInfo> users;

  // criando uma constante que conserva o nome padrão do arquivo a salvar
  String FILENAME = "storage.json";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list);

    if(isFilePresent(getApplicationContext(), FILENAME)){
      users = deserializeJson(readFile(getApplicationContext(), FILENAME));
    } else{
      if (createFile(getApplicationContext(), FILENAME, "{}")){
        Log.d("Funcionou até aqui?", "SIM!");
      }
    }




    // Fazendo o binding dos Componentes
    listView = findViewById(R.id.usersListView);

    // capturando o intent enviado da outra Activity
    Intent intent = getIntent();

    // instanciando a variável Users como ArrayList
    users = new ArrayList<>();

    // passando os dados da lista que vieram no Extra para uma List
    users = intent.getParcelableArrayListExtra("Lista");

    // criando e instanciando o adapter
    MyListAdapter adapter = new MyListAdapter(this, users);
    // instanciando o ListView fazendo o seu binding
    listView = findViewById(R.id.usersListView);
    // fazendo o Listview e o adapter se comunicarem
    listView.setAdapter(adapter);

  }


  // Checando se o arquivo existe antes de salvarmos o conteúdo
  public boolean isFilePresent(Context context, String filename) {
    // para checar a existência do arquivo, precisamos do caminho onde ele possívelmente estaria
    // Esse caminho é dado pelo Context da aplicação
    String path = context.getFilesDir().getAbsolutePath() + "/" + filename;
    // achando ou não, jogue numa variável File
    File file = new File(path);

    // Existindo arquivo retorna true, mas se File estiver vazia, retorna false;
    return file.exists();
  }

  // Criando método para salvamento/criação do arquivo no armazenamento
  private boolean createFile(Context context, String fileName, String jsonString) {
    try {
      // criando a stream de saída do arquivo
      FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
      // se a string do Json gerado não estiver vazia, gravar os Bytes na FileOutputStream
      if (jsonString != null) {
        // adicionando os dados à FileOutputStream
        fos.write(jsonString.getBytes());
      }
      // fechando a FileOutputStream
      fos.close();
      // Se deu tudo certo, retorna true
      return true;

    } catch (Exception ex) {
      // tratando a exceção
      // mandando imprimir a exceção no Logcat
      ex.printStackTrace();
      // retornando false caso haja erro
      return false;
    }
  }

  // Criando método para leitura do arquivo com os dados de usuários
  private String readFile(Context context, String fileName){
    try {
      // Chamando e abrindo o arquivo
      FileInputStream fileInputStream = context.openFileInput(fileName);
      // lendo conteúdo do arquivo
      InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
      // processar o conteúdo do arquivo
      BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
      // criando um StringBuilder para receber os dados lidos e formar a string completa
      StringBuilder stringBuilder = new StringBuilder();
      // criando variável temporária para receber as linhas do arquivo
      String line;
      // enquanto houver linhas no arquivo, adicione-as ao StringBuilder
      while ((line = bufferedReader.readLine()) != null){
        // adicionando cada linha ao StringBuilder
        stringBuilder.append(line);
      }
      // retornando a string gerada pelo StringBuilder
      return stringBuilder.toString();

    } catch (Exception ex){
      // mostrando o conteúdo da exception
      ex.printStackTrace();
      // retornando null caso não consiga ler o arquivo
      return null;
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


  @Override
  public void onBackPressed() {
    createFile(getApplicationContext(), FILENAME, serializeList(users));
    finish();
    onNavigateUpFromChild(this);
  }


}
