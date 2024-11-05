package com.zimmer.carritov3;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;


public class AdminMenuActivity extends AppCompatActivity {

    EditText etNombreProducto, etPrecioProducto;
    Button btnAgregar, btnEliminar, btnActualizar;
    ListView listViewProductos;
    Database db;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_menu);

        etNombreProducto = findViewById(R.id.etNombreProducto);
        etPrecioProducto = findViewById(R.id.etPrecioProducto);
        btnAgregar = findViewById(R.id.btnAgregar);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnActualizar = findViewById(R.id.btnActualizar);
        listViewProductos = findViewById(R.id.listViewProductos);
        db = new Database(this);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<String>());
        listViewProductos.setAdapter(adapter);

        showProducts();

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = etNombreProducto.getText().toString();
                String precioStr = etPrecioProducto.getText().toString();

                if (nombre.isEmpty() || precioStr.isEmpty()) {
                    Toast.makeText(AdminMenuActivity.this, "Porfavor, ingrese nombre y precio del producto"
                            , Toast.LENGTH_SHORT).show();
                    return;
            }
                double precio = Double.parseDouble(precioStr);
                boolean result = db.insertProduct(nombre, precio);

                if (result){
                    Toast.makeText(AdminMenuActivity.this, "Producto Agregado", Toast.LENGTH_SHORT).show();
                    showProducts();
                } else {
                    Toast.makeText(AdminMenuActivity.this, "Error al agregar el producto", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = etNombreProducto.getText().toString();

                if (nombre.isEmpty()) {
                    Toast.makeText(AdminMenuActivity.this, "Por favor, ingrese el nombre del producto", Toast.LENGTH_SHORT).show();
                    return;
                }
                db.deleteProduct(nombre);
                Toast.makeText(AdminMenuActivity.this, "Producto eliminado", Toast.LENGTH_SHORT).show();
                showProducts();
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldName = etNombreProducto.getText().toString();
                String newName = etNombreProducto.getText().toString();
                String precioStr = etPrecioProducto.getText().toString();

                if (oldName.isEmpty() || newName.isEmpty() || precioStr.isEmpty()) {
                    Toast.makeText(AdminMenuActivity.this, "Por favor, ingrese todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                double newPrice = Double.parseDouble(precioStr);
                db.updateProduct(oldName, newName, newPrice);
                Toast.makeText(AdminMenuActivity.this, "Producto actualizado", Toast.LENGTH_SHORT).show();
                showProducts();
            }
        });
    }


    private void showProducts() {
        ArrayList<String> productos = db.getAllProducts();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, productos);
        listViewProductos.setAdapter(adapter);
    }
}