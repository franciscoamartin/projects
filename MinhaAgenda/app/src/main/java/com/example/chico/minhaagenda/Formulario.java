package com.example.chico.minhaagenda;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.NavUtils;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.InputStream;


public class Formulario extends AppCompatActivity {

    FormularioHelper helper;
    Contato contato;

    private String localArquivoFoto;
    private static final int TIRA_FOTO = 123;
    private boolean fotoResource = false;

    Bitmap bitmap;
    ImageView imagemContato;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (toolbar != null) {
            toolbar.setTitle("Editar Contato");
            toolbar.setNavigationIcon(R.drawable.ic_confirmar);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavUtils.navigateUpFromSameTask(Formulario.this);
                }
            });
        }

        this.helper = new FormularioHelper(this);

        Intent intent = this.getIntent();
        this.contato = (Contato) intent.getSerializableExtra("contatoSelecionado");

        if (this.contato != null) {
            this.helper.colocaNoFormulario(contato);
        }

        final Button botaoFoto = helper.getBotaoFoto();
        botaoFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertaSourceImagem();
            }
        });

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_formulario_ok:

                Contato contato = helper.pegaContatoDoFormulario();
                ContatoDAO dao = new ContatoDAO(Formulario.this);

                if (contato.getId() == null) {

                    dao.inserirContato(contato);
                } else {
                    dao.alterarContato(contato);
                }
                dao.close();


                finish();

                return false;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void carregaFotoCamera() {

        fotoResource = true;

        localArquivoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";
        Intent irParaCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        irParaCamera.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".provider", new File(localArquivoFoto)));
        startActivityForResult(irParaCamera, 123);
    }

    private void carregaFotoDiretorio() {
        fotoResource=false;
        /*Intent intent = new Intent();
        intent.setType("image*//*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecione imagem de contato"), 1);*/

        Intent i = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, 1);
    }



    public void alertaSourceImagem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Selecione o local da imagem");
        builder.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                carregaFotoCamera();
            }
        });
        builder.setNegativeButton("Diretório", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                carregaFotoDiretorio();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!fotoResource) {
            if (resultCode == RESULT_OK && null != data) {
                Uri imagemSel = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(imagemSel, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String caminhoFoto = cursor.getString(columnIndex);
                cursor.close();

                helper.carregaImagem(caminhoFoto);
            } else {
                if (requestCode == TIRA_FOTO) {
                    if (resultCode == Activity.RESULT_OK) {
                        helper.carregaImagem(this.localArquivoFoto);
                    }
                } else {
                    this.localArquivoFoto = null;


                }

            }
        }
    }
}


