package teach.meskills.timetable

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import teach.meskills.timetable.databinding.SignInFragmentBinding

class SignInFragment : Fragment() {

    lateinit var launcher: ActivityResultLauncher<Intent>
    private lateinit var binding: SignInFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SignInFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val account = task.getResult(ApiException::class.java)
                if(account != null){
                    firebaseAuthWithGoogle(account.idToken!!)
                }
            } catch (e: ApiException){
                Log.d("signin", "Api exception")

            }
        }
        binding.signinButton.setOnClickListener {
            signInWithGoogle()
        }
        checkAuthState()
    }

    private fun getClient(): GoogleSignInClient {
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(requireContext(), gso)
    }

    private fun signInWithGoogle() {
        val signIngClient = getClient()

        launcher.launch(signIngClient.signInIntent)
    }

    private fun firebaseAuthWithGoogle(idToken: String){
        val credential = GoogleAuthProvider.getCredential(idToken,null)
        AUTH.signInWithCredential(credential).addOnCompleteListener {
            if(it.isSuccessful){
                checkAuthState()
                Log.d("signin", "Google auth done")
            }
            else {
                showToast("Some problem with connection")
                Log.d("signin", "Google auth error")
            }
        }
    }

    private fun checkAuthState(){
        if(AUTH.currentUser != null){
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, CalendarFragment.newInstance())
                .commit()
        }
    }


    companion object {
        fun newInstance() = SignInFragment()
    }
}