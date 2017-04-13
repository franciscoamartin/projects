package com.example.chico.minhaagenda;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Chico on 08/04/2017.
 */
public class ContatoAdaptador extends BaseAdapter {

    private final List<Contato> contatos;
    private final Activity activity;

    public ContatoAdaptador(Activity activity,List<Contato> contatos) {
        this.contatos = contatos;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return this.contatos.size();
    }

    @Override
    public Object getItem(int position) {
        return this.contatos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.contatos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View linha = convertView;
        Contato contato = contatos.get(position);
        Bitmap bitmap;

        if (linha == null) {
            linha = this.activity.getLayoutInflater().inflate(R.layout.celula_contato, parent, false);

        }
        TextView nome = (TextView) linha.findViewById(R.id.nomeCelula);
        TextView email = (TextView) linha.findViewById(R.id.emailCelula);
        TextView site = (TextView) linha.findViewById(R.id.siteCelula);
        TextView telefone = (TextView) linha.findViewById(R.id.telefoneCelula);
        TextView endereco = (TextView) linha.findViewById(R.id.enderecoCelula);


        if (position % 2 == 0) {
            linha.setBackgroundColor(activity.getResources().getColor(R.color.corImpar));
        } else {
            linha.setBackgroundColor(activity.getResources().getColor(R.color.corPar));
        }

        nome.setText(contato.getNome());


                    if (contato.getFoto() != null) {

                        bitmap = BitmapFactory.decodeFile(contato.getFoto());

                    } else {
                        bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_no_image);


                    }

                    bitmap = Bitmap.createScaledBitmap(bitmap, 180, 120, true);
    ImageView foto = (ImageView)linha.findViewById(R.id.fotoCelula);
                    foto.setImageBitmap(bitmap);

        if(email != null){email.setText(contato.getEmail());}
        if(site != null){site.setText(contato.getSite());}
        if(telefone != null){telefone.setText(contato.getTelefone());}
        if(endereco != null){endereco.setText(contato.getEndereco());}


        return linha;

                }
            }



