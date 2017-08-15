package todos.login

import android.content.Context
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import todos.MainActivity
import todos.util.mvp.Router

class LoginPresenter(val router: Router<String>, val interactor: LoginContract.Interactor) : LoginContract.Presenter {

    val TAG: String = LoginPresenter::class.java.simpleName

    override fun handleGoogleSignInResult(context: Context, result: GoogleSignInResult) {

        val firebaseAuth = FirebaseAuth.getInstance()

        if (result.isSuccess && result.signInAccount != null) {
            val uid = result.signInAccount!!.id
            Log.d(TAG, "Got user id: " + uid!!)

            val account = result.signInAccount
            val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
            firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success");
                    val user = firebaseAuth.currentUser
                    user?.getIdToken(true)?.addOnCompleteListener { otherTask ->
                        val tokenId = otherTask.result.token
                        Log.d(TAG, "Got token: " + tokenId)
                        interactor.cacheToken(tokenId!!)
                        router.goToActivity(MainActivity::class.java, context, null)
                    }

                }
            }





        } else {
            Log.d(TAG, "Failed to login: " + result.status)
        }
    }

}