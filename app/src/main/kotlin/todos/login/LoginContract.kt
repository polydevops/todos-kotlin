package todos.login

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignInResult

/**
 * Created by connor on 7/26/17.
 */
interface LoginContract {

    interface Presenter {
        fun handleGoogleSignInResult(context: Context, result: GoogleSignInResult)
    }

    interface Interactor {
        fun cacheToken(token: String)
    }
}