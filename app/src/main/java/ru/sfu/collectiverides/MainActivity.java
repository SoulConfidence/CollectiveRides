package ru.sfu.collectiverides;
//подключение библиотек
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
//описание действий для кнопки "Поехали"
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnMap = (Button)findViewById(R.id.btnGO);
        btnMap.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                EditText PointA = (EditText) findViewById(R.id.tbPointA);
                EditText PointB = (EditText) findViewById(R.id.tbPointB);

                Intent goMapIntent = new Intent(getApplicationContext(), MapsActivity.class);

                goMapIntent.putExtra("pointA", PointA.getText().toString());

                goMapIntent.putExtra("pointB", PointB.getText().toString());
                startActivity(goMapIntent);
            }
        });
    }
}
