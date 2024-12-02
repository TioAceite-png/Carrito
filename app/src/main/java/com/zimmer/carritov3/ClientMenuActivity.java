package com.zimmer.carritov3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ClientMenuActivity extends AppCompatActivity {

    private ListView listViewProductosCliente;
    private Button btnVolverC;
    private ImageButton btnCarrito;
    private Database db;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_client_menu);

        listViewProductosCliente = findViewById(R.id.listViewProductosCliente);
        btnVolverC = findViewById(R.id.btnVolverC);
        btnCarrito = findViewById(R.id.btnCarrito);

        db = new Database(this);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        listViewProductosCliente.setAdapter(adapter);

        // showProducts();

        btnVolverC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClientMenuActivity.this, CarritoActivity.class);
                startActivity(intent);
            }
        });

        listViewProductosCliente.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedProduct = adapter.getItem(i);
                Toast.makeText(ClientMenuActivity.this, "Seleccionaste: "+ selectedProduct, Toast.LENGTH_SHORT).show();

                CarritoSingleton.getInstance().agregarProducto(selectedProduct);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        showProduct();
    }
    private void showProduct(){
        ArrayList<String> productos = db.getAllProducts();
                adapter.clear();
                adapter.addAll(productos);
                adapter.notifyDataSetChanged();
    }
}