package com.zimmer.carritov3;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class CarritoActivity extends AppCompatActivity {

    private ListView listViewCarrito;
    private TextView txtTotal;
    private Button bntVolverT, btnComprar;

    private ArrayList<String> carritoProductos;
    private ArrayAdapter<String> adapter;

    private double total = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);

        // Inicialización de vistas
        listViewCarrito = findViewById(R.id.listViewCarrito);
        txtTotal = findViewById(R.id.txtTotal);
        bntVolverT = findViewById(R.id.btnVolverT);
        btnComprar = findViewById(R.id.btnComprar);

        // Obtención de los productos del carrito
        carritoProductos = CarritoSingleton.getInstance().getProductos();
        total = calcularTotal();

        // Usamos ArrayAdapter para vincular los datos al ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, carritoProductos);
        listViewCarrito.setAdapter(adapter);

        // Mostrar el total
        txtTotal.setText("Total: $" + total);

        // Configuración del botón "Volver"
        bntVolverT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Configuración del botón "Comprar"
        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (carritoProductos.isEmpty()) {
                    Toast.makeText(CarritoActivity.this, "El carrito está vacío", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CarritoActivity.this, "Compra realizada con éxito", Toast.LENGTH_SHORT).show();

                    // Limpiar el carrito
                    carritoProductos.clear();
                    CarritoSingleton.getInstance().clear();
                    adapter.notifyDataSetChanged();

                    // Resetear el total
                    total = 0.0;
                    txtTotal.setText("Total: $" + total);
                }
            }
        });
    }

    // Método para calcular el total de la compra
    private double calcularTotal() {
        double suma = 0.0;
        for (String producto : carritoProductos) {
            String[] partes = producto.split(" \\| ");
            if (partes.length >= 3) {
                String precioStr = partes[2].replace("Precio: $", "").trim();
                try {
                    suma += Double.parseDouble(precioStr);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        return suma;
    }
}
