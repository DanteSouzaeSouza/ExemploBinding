package br.com.theoldpinkeye.exemplobinding;

import android.content.Intent;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import br.com.theoldpinkeye.exemplobinding.models.UserInfo;
import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

  // criando elementos para uso
  ListView listView;
  List<UserInfo> users;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list);

    // Fazendo o binding dos Componentes
     listView = findViewById(R.id.usersListView);

    // capturando o intent enviado da outra Activity
    Intent intent = getIntent();

    // instando a variável Users como ArrayList
    users = new ArrayList<>();

    // passando os dados da lista que vieram no Extra para uma List
    users = intent.getParcelableArrayListExtra("Lista");

    // TODO: Atrelar listview ao adapter!

  }
}
