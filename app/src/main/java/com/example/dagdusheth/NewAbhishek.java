package com.example.dagdusheth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class NewAbhishek extends AppCompatActivity {
    private Spinner gurujiNamesSpinner, abhishekTypesSpinner, gotrasSpinner;
    EditText name, contact, city;
    RadioGroup paymentMode, treeOffering;
//    RadioButton cash, online, yes, no;
    private ArrayAdapter<String> adp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_abhishek);

        treeOffering = findViewById(R.id.TreeOffering);
        paymentMode = findViewById(R.id.Payment_mode);

        gurujiNamesSpinner = findViewById(R.id.selectGuruji);
        abhishekTypesSpinner = findViewById(R.id.selectAbhishekType);
        gotrasSpinner = findViewById(R.id.GotraInput);

        name = findViewById(R.id.NameInput);
        contact = findViewById(R.id.MobileNo);
        city = findViewById(R.id.CityInput);

        String[] GurujiNames = {"Satya", "atharva", "Abhijeet"};
        String[] AbhishekTypes = {"Regular", "Tuesday", "Chaturthi"};
        String[] Gotras = {"Kashyap", "Jamdagni", "Agasthi"};

        adp = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                GurujiNames);
        gurujiNamesSpinner.setAdapter(adp);
        adp = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                AbhishekTypes);
        abhishekTypesSpinner.setAdapter(adp);
        adp = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                Gotras);
        gotrasSpinner.setAdapter(adp);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int paymentModeID = paymentMode.getCheckedRadioButtonId();
                int treeOptionsID = treeOffering.getCheckedRadioButtonId();

                // Find the selected radio buttons by their IDs
                RadioButton selectedPaymentRadioButton = findViewById(paymentModeID);
                RadioButton treeOptionRadioButton = findViewById(treeOptionsID);

                String selectedPaymentMode = "";
                String treeOffered = "";
                if (name.getText().toString().isEmpty()) {
                    Toast.makeText(NewAbhishek.this,"Enter Name...",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (contact.getText().toString().isEmpty()) {
                    Toast.makeText(NewAbhishek.this,"Contact is required",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (city.getText().toString().isEmpty()) {
                    Toast.makeText(NewAbhishek.this,"City is required",Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if any payment mode radio button is selected
                if (selectedPaymentRadioButton != null) {
                    // Get the text of the selected payment mode radio button
                    selectedPaymentMode = selectedPaymentRadioButton.getText().toString();
                }else {
                    Toast.makeText(NewAbhishek.this,"Payment Mode is required",Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if any tree offering radio button is selected
                if (treeOptionRadioButton != null) {
                    // Get the text of the selected tree offering radio button
                    treeOffered = treeOptionRadioButton.getText().toString();
                }else {
                    Toast.makeText(NewAbhishek.this,"Tree Offering should be selected",Toast.LENGTH_SHORT).show();
                    return;
                }

                // Call the uploadDataToExcel method with the extracted values
                uploadDataToExcel(name.getText().toString(),
                        contact.getText().toString(),
                        city.getText().toString(),
                        gotrasSpinner.getSelectedItem().toString(),
                        abhishekTypesSpinner.getSelectedItem().toString(),
                        gurujiNamesSpinner.getSelectedItem().toString(),
                        treeOffered,
                        selectedPaymentMode);
            }
        });
    }

    public void uploadDataToExcel(String name, String contact, String city, String gotra, String abhishekType, String gurujiName, String treeOffered, String selectedPaymentMode) {
        // Concatenate the input values
//        String message = "Name: " + name + "\n" +
//                "Contact: " + contact + "\n" +
//                "City: " + city + "\n" +
//                "Gotra: " + gotra + "\n" +
//                "Abhishek Type: " + abhishekType + "\n" +
//                "Guruji Name: " + gurujiName + "\n" +
//                "Tree Offered: " + treeOffered + "\n" +
//                "Payment Mode: " + selectedPaymentMode;


        String url = "https://script.google.com/macros/s/AKfycbyhwZdVPYbLh1WeD23-BCIEaEDZD_Vr8O62sFZ93XtaaStSL6-jnbnSi8DAnRW2yYNe/exec?";
        url = url + "action=create&name=" + name + "&contact=" + contact + "&city=" + city+"&gotra="+gotra+"&abhishek="+abhishekType+"&gurujiname="+gurujiName+"&treeOffered="+treeOffered+"&payment="+selectedPaymentMode;
        // Show a toast with the concatenated message
//        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        Log.d("Request URL", url); // Log the constructed URL for debugging
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(NewAbhishek.this, "ok", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NewAbhishek.this, "nook", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }
}
