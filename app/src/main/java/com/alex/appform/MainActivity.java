package com.alex.appform;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText txtNombre;
    private EditText txtApellido;
    private EditText txtEmail;
    private EditText txtEdad;
    private Spinner dropdown;
    private EditText txtSalario;
    private ListView lvLista;
    private Button btnAgregar;
    private Button btnListar;
    private Button btnEdad;
    private Button btnSalario;
    private ArrayAdapter<String> adapter;
    private ArrayList<Persona> personas = new ArrayList<>();

    String colores[] = {"Amarillo","Azul","Rojo","Negro"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtNombre = findViewById(R.id.txtNombre);
        txtApellido = findViewById(R.id.txtApellido);
        txtEmail = findViewById(R.id.txtEmail);
        dropdown = findViewById(R.id.spinner);
        txtSalario = findViewById(R.id.txtSalario);
        lvLista = findViewById(R.id.lvLista);
        btnAgregar = findViewById(R.id.btnAgregar);
        btnListar = findViewById(R.id.btnListar);
        btnEdad = findViewById(R.id.btnEdad);
        btnSalario = findViewById(R.id.btnSalario);
        btnAgregar.setOnClickListener(this);
        btnListar.setOnClickListener(this);
        btnEdad.setOnClickListener(this);
        btnSalario.setOnClickListener(this);

        ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource(this,
                R.array.cargos, android.R.layout.simple_spinner_item);
        dropdown.setAdapter(adapterSpinner);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAgregar:
                agregarPersona();
                limpiarCampos();
                adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, personas);
                lvLista.setAdapter(adapter);
                break;
            case R.id.btnListar:
                adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, colores);
                lvLista.setAdapter(adapter);
                break;
            case R.id.btnEdad:
                ordenarMenorMayorEdad();
                ArrayList<String> edadPersonas = new ArrayList<>();
                String joven = "La Persona mas Joven es " + personas.get(0);
                String vieja = "La Persona mas Vieja es " + personas.get(personas.size()-1);
                edadPersonas.add(joven);
                edadPersonas.add(vieja);
                adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, edadPersonas);
                lvLista.setAdapter(adapter);
                break;
            case R.id.btnSalario:
                ordenarMenorMayorSalario();
                ArrayList<String> salarioPersonas = new ArrayList<>();
                String bajo = "El Salario mas bajo es para " + personas.get(0);
                String alto = "El salario mas alto es para " + personas.get(personas.size()-1);
                String salarioPromedio = "El Salario promedio es $ " + salarioPromedio();
                salarioPersonas.add(bajo);
                salarioPersonas.add(alto);
                salarioPersonas.add(salarioPromedio);
                adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, salarioPersonas);
                lvLista.setAdapter(adapter);
                break;
        }
    }

    private void agregarPersona(){
        String nombre = txtNombre.getText().toString();
        String apellido = txtApellido.getText().toString();
        String email = txtEmail.getText().toString();
        int edad = Integer.parseInt(txtEdad.getText().toString());
        String cargo = dropdown.getSelectedItem().toString();
        double salario = Double.parseDouble(txtSalario.getText().toString());

        Persona persona = new Persona(nombre, apellido, email, edad, salario, cargo);

        personas.add(persona);
    }

    private void limpiarCampos(){
        txtNombre.getText().clear();
        txtApellido.getText().clear();
        txtEmail.getText().clear();
        txtEdad.getText().clear();
        dropdown.setSelection(0);
        txtSalario.getText().clear();
    }

    private  void ordenarMenorMayorEdad(){
        Collections.sort(personas, new Comparator<Persona>() {
            @Override
            public int compare(Persona p1, Persona p2) {
                return new Integer(p1.getEdad()).compareTo(new Integer(p2.getEdad()));
            }
        });
    }

    private  void ordenarMenorMayorSalario(){
        Collections.sort(personas, new Comparator<Persona>() {
            @Override
            public int compare(Persona p1, Persona p2) {
                return new Double(p1.getSalario()).compareTo(new Double(p2.getSalario()));
            }
        });
    }

    private Double salarioPromedio(){
        int contador = 0;
        double salarioTotal = 0;
        double promedio = 0;
        for (Persona persona : personas){
            salarioTotal += persona.getSalario();
            contador++;
        }
        promedio = salarioTotal / contador;
        return promedio;
    }
}