package facens.worthit.view.account;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import facens.worthit.R;
import facens.worthit.helper.FragmentHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    private FirebaseAuth mAuth;
    public RegisterFragment() {
        mAuth = FirebaseAuth.getInstance();
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragment_register, container, false);

      ((Button)view.findViewById(R.id.btnRegisterUser)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText emailEdit = ((EditText)view.findViewById(R.id.txt_user_email));
                EditText passEdit = ((EditText)view.findViewById(R.id.txt_user_password));
                EditText confPassEdit = ((EditText)view.findViewById(R.id.txt_user_confirm_password));

                if(emailEdit != null && passEdit != null && confPassEdit !=null){

                    String email = emailEdit.getText().toString();
                    String password = passEdit.getText().toString();
                    String confPassword = confPassEdit.getText().toString();

                 if(password.equals(confPassword)){
                     try{

                         // Inflate the layout for this fragment

                         mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                             @Override
                             public void onComplete(@NonNull Task<AuthResult> task) {

                                 if(task.isSuccessful()){
                                     Toast.makeText(getActivity(), "Usuário cadastrado com sucesso.", Toast.LENGTH_SHORT).show();

                                     Handler handler = new Handler();

                                     final ArrayList<Fragment> fragments = new ArrayList<Fragment>() {{
                                         add(new LoginFragment());
                                         add(new RegisterFragment());
                                         add(new ProfileFragment());
                                     }};

                                     handler.postDelayed(new Runnable() {
                                         @Override
                                         public void run() {

                                             FragmentHelper mFragmentHelper = new FragmentHelper(getFragmentManager(),fragments ,0, R.id.frame_account, false, "");


                                         }
                                     },1000);


                                 }
                                 else{
                                     Toast.makeText(getActivity(), "Erro ao realizar o cadastro. Verifique os campos.", Toast.LENGTH_SHORT).show();
                                 }

                             }
                         });

                     }
                     catch (Exception ex){
                         int x =4;
                     }
                 }
                 else{
                     Toast.makeText(getActivity(),"Senhas não coincidem.", Toast.LENGTH_SHORT).show();

                 }

                }
                else{
                    Toast.makeText(getActivity(),"Preencha todos os dados.", Toast.LENGTH_SHORT).show();
                }
            }
        });


      return view;
    }

}
