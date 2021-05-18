package com.wenitech.cashdaily.data.firebase

import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.FirebaseFirestore
import com.wenitech.cashdaily.data.model.User
import com.wenitech.cashdaily.core.ResourceAuth
import com.wenitech.cashdaily.core.ResourceAuth.Companion.collicion
import com.wenitech.cashdaily.core.ResourceAuth.Companion.error
import com.wenitech.cashdaily.core.ResourceAuth.Companion.failed
import com.wenitech.cashdaily.core.ResourceAuth.Companion.success
import javax.inject.Inject

@Suppress("UNREACHABLE_CODE", "RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class AuthenticationImpl @Inject constructor(
        private val firestore: FirebaseFirestore,
        private val auth: FirebaseAuth,
) : Authentication {

    /**
     * Login in dadaBase
     *
     * @param email
     * @param password
     */
    override fun LoginFirebase(email: String, password: String): MutableLiveData<ResourceAuth<String>> {

        val resultLogin = MutableLiveData<ResourceAuth<String>>()

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                resultLogin.setValue(success("Se ha iniciado sesion"))
            } else {
                resultLogin.setValue(error("Inicio de sesion fallido", "No pudimos iniciar sesion"))
            }
        }
        return resultLogin
    }

    /**
     * New account for user with Email and Password
     *
     * @param email
     * @param password
     * @param user
     */
    override fun SignInFirebase(email: String, password: String, user: User): MutableLiveData<ResourceAuth<String>> {

        val resultSignIn = MutableLiveData<ResourceAuth<String>>()

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {

                val newUserApp = User()

                val resultSaveUserApp: MutableLiveData<ResourceAuth<String>> = MutableLiveData()

                val newUserAppDocRef = firestore.collection("usuarios").document(task.result!!.user.uid)

                newUserAppDocRef.set(newUserApp)
                        .addOnCompleteListener(OnCompleteListener {
                            resultSaveUserApp.setValue(success(task.isSuccessful.toString()))
                        }).addOnFailureListener(OnFailureListener {
                            resultSaveUserApp.setValue(failed("No fue Posible agregar los datos", it.toString()))
                        })

            } else {
                if (task.exception is FirebaseAuthUserCollisionException) {
                    resultSignIn.setValue(collicion("Esta cuenta ya existe", ""))
                } else {
                    resultSignIn.setValue(failed("No fue posible crear el usuario", "Error al crear cuenta"))
                }
            }
        }.addOnFailureListener {
            resultSignIn.setValue(error("Error al crear el usuario", it.message.toString()))
        }
        return resultSignIn
    }

    /**
     * Reset account for new password.
     * Send  the email at user for recovery account
     *
     * @param email
     * @param password
     */
    override fun ResetPasswordFirebase(email: String?, password: String?): MutableLiveData<ResourceAuth<String>> {

        var result = MutableLiveData<ResourceAuth<String>>()

        auth.sendPasswordResetEmail(email!!).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // On Success
                result.value = ResourceAuth.success("Se ha enviado un correo a $email con informacion para restablecer tu cuenta. ")
            }
        }.addOnFailureListener {
            // On Failure
            result.value = error(it.message.toString())
        }
        return result
    }
}