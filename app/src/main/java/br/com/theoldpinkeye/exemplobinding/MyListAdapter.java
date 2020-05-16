package br.com.theoldpinkeye.exemplobinding;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import br.com.theoldpinkeye.exemplobinding.models.UserInfo;
import java.util.List;

// Criada a classe Adapter que herda da classe BaseAdapter
public class MyListAdapter extends BaseAdapter {

  // Variável que recebe o contexto da aplicação
  private Context context;

  // Variável que recebe os itens da lista
  private List<UserInfo> users;


  // implementando os métodos
  @Override
  public int getCount() {

    // pega a quantidade de itens na lista
    return users.size();
  }

  @Override
  public Object getItem(int position) {
    return null;
  }

  @Override
  public long getItemId(int position) {
    return 0;
  }

  // esse método faz a ligação das views o conteúdo
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {

    // criando a view do card e mandando o Android inflar o layout criado para o card nela
    @SuppressLint("ViewHolder") View card = LayoutInflater.from(context)
        .inflate(R.layout.users_list_item, parent, false);

    TextView nameTextView = card.findViewById(R.id.nameTextView);
    TextView emailTextView = card.findViewById(R.id.emailTextView);
    CheckBox acceptCheck = card.findViewById(R.id.acceptCheckBox);

    nameTextView.setText(users.get(position).getName());
    emailTextView.setText(users.get(position).getEmail());
    acceptCheck.isChecked(users.get(position).isAccept());

    return card;
  }
}
