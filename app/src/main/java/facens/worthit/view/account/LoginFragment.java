package facens.worthit.view.account;


import android.os.Bundle;
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
public class LoginFragment extends Fragment {

    private FragmentHelper mFragmentHelper;
    private FirebaseAuth mAuth;

    public LoginFragment() {
        mAuth = FirebaseAuth.getInstance();
        // Required empty public constructor
    }

    protected ArrayList<Fragment> createFragments(){
        return  new ArrayList<Fragment>() {{
            add(new LoginFragment());
            add(new RegisterFragment());
            add(new ProfileFragment());
        }};
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_login, container, false);



        ((Button)v.findViewById(R.id.login_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String email = ((EditText)v.findViewById(R.id.txtLoginEmail)).getText().toString();
                final String password = ((EditText)v.findViewById(R.id.txtLoginPassword)).getText().toString();

                if(!email.isEmpty() && !password.isEmpty()){
                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                Toast.makeText(getActivity(), "Login realizado com sucesso.", Toast.LENGTH_SHORT).show();
                                mFragmentHelper = new FragmentHelper(getFragmentManager(),createFragments(),2, R.id.frame_account, false, "");
                            }
                            else{
                                Toast.makeText(getActivity(), "Erro ao realizar login. Verifique suas credenciais!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(getActivity(), "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        ((Button)v.findViewById(R.id.register_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mFragmentHelper = new FragmentHelper(getFragmentManager(),createFragments(),1, R.id.frame_account, false, "");

            }
        });

        return v;
    }

}
