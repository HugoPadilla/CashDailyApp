package com.wenitech.cashdaily.framework.utils

import android.text.TextUtils
import android.util.Patterns

class ValidateForm {

    companion object {
        fun validateEmail(email: String): ResultValidator {
            val resultValidator = ResultValidator()
            if (TextUtils.isEmpty(email)) {
                resultValidator.massage = "scribe tu correo"

            }
            else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                resultValidator.massage = "Escribe un correo valido"
            }
            else {
                resultValidator.isValid = true
                resultValidator.value = email
            }
            return resultValidator
        }

        fun validatePassword(password: String): ResultValidator {
            val resultValidator = ResultValidator()
            if (TextUtils.isEmpty(password)) {
                resultValidator.massage = "Escribe una contraseña"
            }
            else if (password!!.length < 8) {
                resultValidator.massage = "Debe tener al menos 8 carapteres"
            }
            else {
                resultValidator.isValid = true
                resultValidator.value = password
            }
            return resultValidator
        }

        fun validatePasswordConfirm(password: String, passwordConfirm: String): ResultValidator {
            val resultValidator = ResultValidator()
            if (TextUtils.isEmpty(passwordConfirm)) {
                resultValidator.massage = "Debes confirmar tu contraseña"
            } else if (!TextUtils.equals(password, passwordConfirm)) {
                resultValidator.massage = "Contraseñas no coinciden"
            } else {
                resultValidator.isValid = true
                resultValidator.value = password
            }
            return resultValidator
        }
    }

}