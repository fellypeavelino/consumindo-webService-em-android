package com.example.conexaoweb;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.james.mime4j.codec.EncoderUtil;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
//https://www.youtube.com/watch?v=7vaHLJFhF5Q
	public void enviar(View v){
		
		new Thread(){
			public void run(){
				EditText nome = (EditText)findViewById(R.id.nome) ;
				EditText sobreNome = (EditText)findViewById(R.id.sobrenome) ;
				EditText mail = (EditText)findViewById(R.id.mail) ;
				postHttp(nome.getText().toString(),sobreNome.getText().toString(), mail.getText().toString());
			}
		}.start();
		
	}
	
	public void postHttp(String nome,String sobreNome, String mail){
		HttpClient httpCliente = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost("http://10.0.2.2/webservice_android/service.php");
		try {
			ArrayList<NameValuePair> valores = new ArrayList<NameValuePair>();
			valores.add(new BasicNameValuePair("nome",nome));
			valores.add(new BasicNameValuePair("sobreNome",sobreNome));
			valores.add(new BasicNameValuePair("mail",mail));
			
			httpPost.setEntity(new UrlEncodedFormEntity(valores));
			final HttpResponse resposta = httpCliente.execute(httpPost);
			
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						Toast.makeText(getBaseContext(), EntityUtils.toString(resposta.getEntity()), Toast.LENGTH_SHORT).show();
					} catch (ParseException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.out.print(e.getMessage());
					}
				}
			});
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
