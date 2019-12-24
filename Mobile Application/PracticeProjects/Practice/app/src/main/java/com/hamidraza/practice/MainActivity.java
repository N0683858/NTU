package com.hamidraza.practice;

        import androidx.appcompat.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.widget.EditText;
        import android.widget.ImageButton;
        import android.widget.ImageView;
        import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
//   changing img1 to img2 by clicking a button
   /*public void onClick(View view){
        ImageView image = findViewById(R.id.imgView);
        image.setImageResource(R.drawable.simba2);

    }*/

   public void convert_btn_clicked(View view){
       double dollar_exchangeRate = 1.23;

       EditText userNumb = findViewById(R.id.editText);
       double userNumb_double = Double.parseDouble(userNumb.getText().toString());


       double ans = userNumb_double * dollar_exchangeRate;

       Toast.makeText(MainActivity.this,"$" + String.format("%.2f", ans),Toast.LENGTH_SHORT).show();
   }
}
