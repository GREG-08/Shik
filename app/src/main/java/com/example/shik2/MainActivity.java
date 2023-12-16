package com.example.shik2;

import static com.example.shik2.Utility.getCollectionReferenceForUserModel;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Locale;
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
   EditText EdMarka, EdModel,  EdDatatime, EdOtsyv;
   Spinner Usluga;
   String item;
    Button OnclickSave;
    ImageButton Logout;
    UserModel userModel;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EdMarka = findViewById(R.id.marka);
        EdModel = findViewById(R.id.model);
        EdDatatime = findViewById(R.id.data);
        EdOtsyv = findViewById(R.id.time);
        Logout = findViewById(R.id.logout);
        OnclickSave = findViewById(R.id.onclickSave);
        Usluga =(Spinner) findViewById(R.id.consultation_deseases);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Шиномонтаж колес");
        arrayList.add("Балансировка");
        arrayList.add("Правка дисков");
        arrayList.add("Вулканизация");
        arrayList.add("Исправленение покрышек от деффектов");
        arrayList.add("Ремонт боковых порезов шин");
        arrayList.add("Подкачка шин");
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, R.layout.spinner_item,arrayList);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        Usluga.setAdapter(adapter);
        Spinner spinner = (Spinner) findViewById(R.id.ras_spinner);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
                this,
                R.array.ras_array, R.layout.spinner_item);
        adapter2.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(adapter2);
        OnclickSave.setOnClickListener((v) ->save() );
        Logout.setOnClickListener((v -> showMenu()));
    }
    void save() {
        String marka = EdMarka.getText().toString();
        String model = EdModel.getText().toString();
        String datatime = EdDatatime.getText().toString();
        String otsyv = EdOtsyv.getText().toString();
     String usluga = Usluga.getSelectedItem().toString();
        if (marka == null || marka.isEmpty()) {
            EdMarka.setError("Вы не заполнили поле");
            return;
        }
        if (model == null || model.isEmpty()) {
            EdModel.setError("Вы не заполнили поле");
            return;
        }
        if (datatime == null || datatime.isEmpty()) {
            EdDatatime.setError("Вы не заполнили поле");
            return;
        }
        if (otsyv == null || otsyv.isEmpty()) {
            EdOtsyv.setError("Вы не заполнили поле");
            return;
        }
            UserModel userModel = new UserModel();
            userModel.setMarka(marka);
            userModel.setModel(model);
            userModel.setDatatime(datatime);
            userModel.setOtsyv(otsyv);
            userModel.setTimestamp(Timestamp.now());
             userModel.setUsluga(usluga);
            saveUserModelToFirebase(userModel);
    }
    void saveUserModelToFirebase(UserModel userModel){
        DocumentReference documentReference;
        documentReference = getCollectionReferenceForUserModel().document();
        documentReference.set(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    //значения в полях добавлены
                        Utility.showToast(MainActivity.this,"Вы записаны!");
                        finish();
                }else{
                 //значения в полях не добавлены
                    Utility.showToast(MainActivity.this,"Ошибка!  Вы не записаны!");
                }
            }
        });
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
         item = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), "выбрано!", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
    void  showMenu(){
        PopupMenu popupMenu = new PopupMenu(MainActivity.this,Logout);
        popupMenu.getMenu().add("Logout");
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this,loginActivity.class));
                finish();
                return true;
            }
        });
    }
}