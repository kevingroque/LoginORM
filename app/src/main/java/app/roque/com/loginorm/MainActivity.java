package app.roque.com.loginorm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import app.roque.com.loginorm.model.User;
import app.roque.com.loginorm.repository.UserRepository;

public class MainActivity extends AppCompatActivity {

    private static final int REGISTER_FORM_REQUEST = 100;

    private SharedPreferences sharedPreferences;
    private EditText usernameInput;
    private EditText passwordInput;
    private View loginPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameInput = (EditText)findViewById(R.id.username_input);
        passwordInput = (EditText)findViewById(R.id.password_input);
        loginPanel = findViewById(R.id.login_panel);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        String username = sharedPreferences.getString("username", null);
        if(username != null){
            usernameInput.setText(username);
            passwordInput.requestFocus();
        }

        if(sharedPreferences.getBoolean("islogged", false)){
            goHome();
        }

    }

    public void callLogin(View view){

        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();

        if(username.isEmpty()){
            usernameInput.setError("Campo Obligatorio");
            Toast.makeText(this, "You must complete these fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if(password.isEmpty()){
            passwordInput.setError("Campo Obligatorio");
            Toast.makeText(this, "You must complete these fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Login logic
        User user = UserRepository.login(username, password);

        if(user == null){
            Toast.makeText(this, "Usuario no existe. Registrate!!", Toast.LENGTH_SHORT).show();
            loginPanel.setVisibility(View.VISIBLE);
            callRegisterForm(view);
            return;
        }

        Toast.makeText(this, "Welcome " + user.getFullname(), Toast.LENGTH_SHORT).show();

        // Save to SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        boolean success = editor
                .putString("username", user.getEmail())
                .putBoolean("islogged", true)
                .commit();

        // Go to Dashboard
        goHome();
    }

    private void goHome(){
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    public void callRegisterForm(View view){
        String email = usernameInput.getText().toString();
        String passwword = passwordInput.getText().toString();

        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra("email",email);
        intent.putExtra("password",passwword);
        startActivityForResult(intent, REGISTER_FORM_REQUEST);
    }

}
