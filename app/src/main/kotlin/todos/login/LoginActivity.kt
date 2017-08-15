package todos.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.polydevops.todos.R
import kotlinx.android.synthetic.main.activity_login.*
import todos.TodosApplication
import todos.auth.AuthInteractor

class LoginActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener  {

    private val RC_SIGN_IN = 1

    lateinit var presenter: LoginContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        presenter = LoginPresenter(TodosApplication.router, LoginInteractor(AuthInteractor(this)))

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        val googleApiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()

        google_sign_in_button.setOnClickListener({ view ->
            val signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
            startActivityForResult(signInIntent, RC_SIGN_IN)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            presenter.handleGoogleSignInResult(this, result)
        }
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Log.d(LoginActivity::class.java.simpleName, "Failed to connect to google's auth APIs")
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}