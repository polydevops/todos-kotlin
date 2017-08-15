package todos.login

import todos.auth.AuthInteractor

class LoginInteractor(val authInteractor: AuthInteractor) : LoginContract.Interactor {

    override fun cacheToken(token: String) {
        authInteractor.cacheToken(token)
    }

}