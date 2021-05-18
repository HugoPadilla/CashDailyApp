package com.wenitech.cashdaily.data.model

import com.google.firebase.firestore.DocumentId

data class Ruta(
        @DocumentId
        val documentId: String? = null,
)
