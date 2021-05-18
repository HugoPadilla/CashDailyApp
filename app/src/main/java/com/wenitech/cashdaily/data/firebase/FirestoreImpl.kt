package com.wenitech.cashdaily.data.firebase

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*
import com.wenitech.cashdaily.core.Resource
import com.wenitech.cashdaily.data.firebase.routes.FirestoreRouters
import com.wenitech.cashdaily.data.model.*
import com.wenitech.cashdaily.core.ResourceAuth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@Suppress("UNCHECKED_CAST", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class FirestoreImpl @Inject constructor(
        private val db: FirebaseFirestore,
        auth: FirebaseAuth,
) : Firestore {

    private val user: FirebaseUser = auth.currentUser
    private val firestoreRouters: FirestoreRouters = FirestoreRouters(db, auth, user)

    // Todo: Application
    @ExperimentalCoroutinesApi
    override fun getProfileUser(): Flow<ResourceAuth<User>> = callbackFlow {
        offer(ResourceAuth.loading(null))

        val query = firestoreRouters.getDocUserApp()
        val listener = query.addSnapshotListener { documentSnap, error ->
            if (documentSnap != null) {
                offer(Resource.Success(documentSnap.toObject(User::class.java)))
            }

            error?.let {
                offer(Resource.Error(it.message.toString(), null))
                cancel(it.message.toString())
            }
        }

        awaitClose {
            listener.remove()
            cancel()
        }
    } as Flow<ResourceAuth<User>>

    @ExperimentalCoroutinesApi
    override fun getBoxUser(): Flow<Resource<Caja>> = callbackFlow {
        offer(Resource.Loading())

        val query = firestoreRouters.getDocRefBox()
        val listener = query.addSnapshotListener { snap, erro ->

            if (snap != null) {
                offer(Resource.Success(snap.toObject(Caja::class.java)))
            }

            erro?.let {
                offer(Resource.Error(it.message.toString(), null))
                cancel(it.message.toString())
            }

        }
        awaitClose {
            listener.remove()
            cancel()
        }
    } as Flow<Resource<Caja>>

    @ExperimentalCoroutinesApi
    override fun getRecentCashMovements(): Flow<Resource<List<MovimientoCaja>>> = callbackFlow {
        offer(Resource.Loading())
        val query = firestoreRouters.getDocRefBox().collection("movimientos").orderBy("fecha", Query.Direction.DESCENDING).limit(10)

        val listener = query.addSnapshotListener { documentSnapshot, error ->
            if (documentSnapshot != null) {
                offer(Resource.Success(documentSnapshot.toObjects(MovimientoCaja::class.java)))
            }

            error?.let {
                offer(Resource.Error(it.message.toString(), null))
                cancel(it.message.toString())
            }
        }

        awaitClose {
            listener.remove()
            cancel()
        }
    } as Flow<Resource<List<MovimientoCaja>>>

    @ExperimentalCoroutinesApi
    override fun saveMoneyOnBox(moneyToCash: Double): Flow<Resource<String>> = callbackFlow {
        offer(Resource.Loading())

        // Referencia a un nuevo documento en firestore para movimientos de caja
        val newBoxMovementDocument = firestoreRouters.getCollectionBoxMovimento().document()

        val transaction = db.runTransaction { transaction ->
            val cajaDocument = firestoreRouters.getDocRefBox() // Ref a Documento caja

            val documentSnapshotCaja = transaction[cajaDocument]
            if (documentSnapshotCaja.exists()) {
                // Valor actual de la caja
                val valorCaja = documentSnapshotCaja.getDouble("totalCaja")!!

                // Actualiza la caja sumandole el valor del dinero a agregar
                transaction.update(firestoreRouters.getDocRefBox(), "totalCaja", valorCaja + moneyToCash)

                // Crea un nuevo objeto movimiento de caja
                val date = Date(System.currentTimeMillis())
                val timestamp = Timestamp(date)
                val newMovimientoCaja = MovimientoCaja(
                        newBoxMovementDocument.id,
                        moneyToCash,
                        user.displayName,
                        timestamp,
                        true
                )

                transaction[newBoxMovementDocument] = newMovimientoCaja
                // Todo: mas transacciones para guardar infomacion estadistica
            }

        }.addOnSuccessListener {
            Log.d("FIRESTORE", "Se agrego dinero")
            offer(Resource.Success("Se agrego: $moneyToCash"))
        }.addOnFailureListener { e ->
            Log.d("FIRESTORE", "No fue posible agregar dinero a la caja" + e.message)
            offer(Resource.Error("No fue posible agregar dinero", e.message.toString()))
        }

        awaitClose {

        }
    }

    @ExperimentalCoroutinesApi
    override fun removeMoneyOnBox(moneyToCash: Double): Flow<Resource<String>> = callbackFlow {
        offer(Resource.Loading())

        val newBoxMovementDocRef: DocumentReference = firestoreRouters.getCollectionBoxMovimento().document()

        val transaction = db.runTransaction { transaction ->

            val snapshotCaja = transaction[firestoreRouters.getDocRefBox()]
            if (snapshotCaja.exists()) {
                val valorCaja = snapshotCaja.getDouble("totalCaja")!!
                transaction.update(firestoreRouters.getDocRefBox(), "totalCaja", valorCaja - moneyToCash)

                // Crea un nuevo objeto movimiento y se agrega un nuevo registro de movimiento de caja
                val date = Date(System.currentTimeMillis())
                val timestamp = Timestamp(date)
                val newMovimientoCaja = MovimientoCaja(
                        newBoxMovementDocRef.id,
                        moneyToCash,
                        user.displayName,
                        timestamp,
                        false
                )
                transaction[newBoxMovementDocRef] = newMovimientoCaja
            }

        }.addOnSuccessListener {
            Log.d("FIRESTORE", "Retiro de dinero exitoso")
            offer(Resource.Success("Retiro exitoso"))
        }.addOnFailureListener {
            Log.d("FIRESTORE", "No fue posible retirar dinero")
            offer(Resource.Error("No fue posible retirar dinero", it.message.toString()))
        }

        awaitClose {

        }

    }

    // Todo: Client
    @ExperimentalCoroutinesApi
    override fun getClients(): Flow<Resource<List<Cliente>>> = callbackFlow {

        var lastClientReceived: DocumentSnapshot? = null

        val query = firestoreRouters.getCollectionClients()
                .orderBy("fullName", Query.Direction.ASCENDING)
                .limit(10)
        val paginatedQuery =
                if (lastClientReceived != null) query.startAfter(lastClientReceived)
                else query

        val listenerRegistration = paginatedQuery.addSnapshotListener { querySnapshot, error ->

            if (querySnapshot != null && !querySnapshot.isEmpty) {
                lastClientReceived = querySnapshot.documents.last()
                offer(Resource.Success(querySnapshot.toObjects(Cliente::class.java)))
            }

            error?.let {
                offer(Resource.Error(it.message.toString(), it))
                cancel(it.message.toString())
            }
        }

        awaitClose {
            listenerRegistration.remove()
            cancel()
        }
    } as Flow<Resource<List<Cliente>>>

    @ExperimentalCoroutinesApi
    override fun getClientsCobrarHoy(): Flow<Resource<List<Cliente>>> = callbackFlow {

        val dateFormat = SimpleDateFormat("dd-MM-yyyy")
        val date = "02-05-2021"
        val date1 = dateFormat.parse(date)
        val startDate = Timestamp(date1)

        val query = firestoreRouters.getCollectionClients().whereEqualTo("fechaCobro", startDate)
                .orderBy("fullName", Query.Direction.ASCENDING)
                .limit(10)

        val listener = query.addSnapshotListener { value, error ->
            if (value != null && !value.isEmpty){
                offer(Resource.Success(value.toObjects(Cliente::class.java)))
            }

            error?.let {
                offer(Resource.Error(it.message.toString(),it))
                cancel(it.message.toString())
            }
        }

        awaitClose{
            listener.remove()
            cancel()
        }

    } as Flow<Resource<List<Cliente>>>

    override fun getClientsAtrasados(): Flow<Resource<List<Cliente>>> {
        TODO("Not yet implemented")
    }

    override fun getClientsVencidos(): Flow<Resource<List<Cliente>>> {
        TODO("Not yet implemented")
    }

    @ExperimentalCoroutinesApi
    override fun saveNewClient(cliente: Cliente): Flow<Resource<String>> = callbackFlow {
        offer(Resource.Loading())
        val newDocumentClient = firestoreRouters.getCollectionClients().document()

        newDocumentClient.set(cliente)
                .addOnCompleteListener { task ->

                    if (task.isComplete) {
                        offer(Resource.Success("Se comleto el proceso"))
                    } else if (task.isSuccessful) {
                        offer(Resource.Success("se completo exitosamente"))
                    } else if (task.isCanceled) {
                        offer(Resource.Success("La operacion se cancelo"))
                    }

                }.addOnFailureListener {

                    Log.d("TAG", "On Failure")
                    offer(Resource.Error("Se presento un incoveniente al agragar el cliente", null))

                }

        awaitClose {

        }
    } as Flow<Resource<String>>

    override fun updateClient(cliente: Cliente): Flow<Resource<String>> {
        TODO("Not yet implemented")
    }

    @ExperimentalCoroutinesApi
    override fun removeClient(clientId: String): Flow<Resource<String>> = callbackFlow {

        firestoreRouters.getDocClientById(clientId).delete()
                .addOnCompleteListener(OnCompleteListener {
                    when {
                        it.isComplete -> {
                            offer(Resource.Success("Cliente elimado complet"))
                        }
                        it.isSuccessful -> {
                            offer(Resource.Success("Cliente eliminado desde cache"))
                        }
                        it.isCanceled -> {
                            offer(Resource.Error("Cancelado", "El proceso fue cancelado"))
                        }
                    }
                })
                .addOnSuccessListener {
                    offer(Resource.Success("Cliente eliminado"))
                }
                .addOnFailureListener {
                    offer(Resource.Error("Failure", "No se pudo eliminar este cliente"))
                }

    }

    // Todo: Credit
    @ExperimentalCoroutinesApi
    override fun getRecentCreditClient(idClient: String): Flow<Resource<List<Credito>>> = callbackFlow {
        offer(Resource.Loading())

        val query = firestoreRouters.getCollectionClients().document(idClient).collection("credits").limit(5)

        val listener = query.get().addOnCompleteListener {
            when {
                it.isComplete -> {
                    offer(it.result?.let { it1 -> Resource.Success(it1.toObjects(Credito::class.java)) })
                }
                it.isSuccessful -> {
                    offer(it.result?.let { it1 -> Resource.Success(it1.toObjects(Credito::class.java)) })
                }
                it.isCanceled -> {
                    cancel("Tarea cancelada")
                }
            }
        }.addOnFailureListener {
            offer(Resource.Error(it.message.toString(), it))
        }

        awaitClose {
            cancel()
        }

    } as Flow<Resource<List<Credito>>>

    /**
     * Cosulta el credito de un cliente
     */
    @ExperimentalCoroutinesApi
    override fun getCreditClient(creditClientDocRef: String): Flow<Resource<Credito>> = callbackFlow {
        offer(Resource.Loading())
        // Referencia al documento en firestore
        val eventDocument = db.document(creditClientDocRef)

        if (creditClientDocRef.isEmpty()) {
            Log.d("TAG", creditClientDocRef)
            cancel("Referencia a credito vacia")
        }

        // Register listener
        val listener = eventDocument.addSnapshotListener { snapshot, error ->

            if (snapshot != null) {
                offer(Resource.Success(snapshot.toObject(Credito::class.java)))
            }

            // If exception occurs, cancel this scope with exception message.
            error?.let {
                offer(Resource.Error(it.message.toString(), null))
                cancel(it.message.toString())
            }
        }

        awaitClose {
            // This block is executed when producer channel is cancelled
            // This function resumes with a cancellation exception.

            // Dispose listener
            listener.remove()
            cancel()
        }

    } as Flow<Resource<Credito>>

    override fun saveNewCreditClientById(idClient: String, newCredit: Credito): MutableLiveData<ResourceAuth<String>> {

        val resultNewCredit = MutableLiveData<ResourceAuth<String>>()

        val documentReferenceUserApp = db.collection("usuarios").document(user.uid)
        val docReferenceBox = documentReferenceUserApp.collection("caja").document("myCaja")
        val docReferenceClient = documentReferenceUserApp.collection("clientes").document(idClient)
        val documentReferenceCredito = docReferenceClient.collection("creditos").document()

        db.runTransaction { transaction ->
            val snapshot = transaction[docReferenceBox]
            val valorCaja = snapshot.getDouble("totalCaja")!!
            val valorPrestamo = newCredit.valorPrestamo
            if (valorCaja >= valorPrestamo!!) {
                val updateCliente: MutableMap<String, Any> = HashMap()
                updateCliente["creditActive"] = true
                updateCliente["documentReferenceCreditActive"] = documentReferenceCredito
                transaction.update(docReferenceBox, "totalCaja", valorCaja - newCredit.valorPrestamo!!)
                transaction.update(docReferenceClient, updateCliente)
                transaction[documentReferenceCredito] = newCredit
                documentReferenceCredito.path
            } else {
                throw FirebaseFirestoreException("No hay suficiente dinero en la caja",
                        FirebaseFirestoreException.Code.ABORTED)
            }
        }.addOnSuccessListener { docRefNewCredit ->
            Log.d("FIRESTORE", "Nuevo credito agregado con exito $docRefNewCredit")
            resultNewCredit.setValue(ResourceAuth.success(docRefNewCredit))
        }.addOnFailureListener { e ->
            Log.d("FIRESTORE", "Error exception $e")
            resultNewCredit.setValue(ResourceAuth.failed("Error al agregar nuevo credito", e.message.toString()))
        }
        return resultNewCredit
    }

    /**
     * Consultar las cuotas de un credtio espesifico
     *
     */
    @ExperimentalCoroutinesApi
    override fun getQuotaCredit(docRefCreditActive: String): Flow<Resource<List<Cuota>>> = callbackFlow {
        val eventDocument = db.document(docRefCreditActive).collection("/cuotas").orderBy("fechaCreacion", Query.Direction.DESCENDING)

        val listenerRegistration = eventDocument.addSnapshotListener { querySnapshot, error ->
            if (querySnapshot != null) {
                offer(Resource.Success(querySnapshot.toObjects(Cuota::class.java)))
            }

            error?.let {
                offer(Resource.Error(it.message.toString(), null))
                cancel(it.message.toString())
            }
        }

        awaitClose {
            listenerRegistration.remove()
            cancel()
        }
    } as Flow<Resource<List<Cuota>>>

    @ExperimentalCoroutinesApi
    override fun saveNewQuota(valueNewQuota: Double, idClient: String, referenceCredit: String, user: FirebaseUser): Flow<Resource<String>> = callbackFlow {
        offer(Resource.Loading())
        val refUserApp = db.collection("usuarios").document(user.uid)
        val refBox = refUserApp.collection("caja").document("myCaja")
        val refClient = refUserApp.collection("clientes").document(idClient)
        val refCredit = db.document(referenceCredit)
        val refNewQuote = db.document(referenceCredit).collection("cuotas").document()

        db.runTransaction { transaction ->
            // Get valor actual dispobible de la caja,
            val documentSnapshotCaja = transaction[refBox]
            val box = documentSnapshotCaja.toObject(Caja::class.java)

            // Get customer debt value
            val snapshotCredito = transaction[refCredit]
            val deudaPrestamo = snapshotCredito.getDouble("deudaPrestamo")!!

            // Calculate value of new post-debt
            val posDeuda = deudaPrestamo - valueNewQuota
            var returnTrasaction = "True"

            // Object new quote
            val newCuota = Cuota()
            newCuota.fechaCreacion = Timestamp.now()
            newCuota.nombreCreacion = user.displayName
            newCuota.estadoEditado = "Normal"
            newCuota.valorCuota = valueNewQuota

            // se valida si hay una deuda en el credito actual
            if (deudaPrestamo > 0 || posDeuda <= 0) {
                // se validad si hay una deuda despues despues de agregar la cuota
                if (posDeuda > 0) {
                    transaction.update(refCredit, "deudaPrestamo", posDeuda)
                    transaction.update(refBox, "totalCaja", box!!.totalCaja?.plus(valueNewQuota))
                    transaction[refNewQuote] = newCuota
                    returnTrasaction = "True"
                } else {
                    if (posDeuda == 0.0) {
                        transaction.update(refClient, "creditActive", false)
                        transaction.update(refClient, "documentReferenceCreditActive", null)
                        transaction.update(refCredit, "deudaPrestamo", posDeuda)
                        transaction.update(refBox, "totalCaja", box!!.totalCaja?.plus(valueNewQuota))
                        transaction[refNewQuote] = newCuota
                        returnTrasaction = "False"
                    }
                }
                returnTrasaction
            } else {
                throw FirebaseFirestoreException("No hay deuda disponible",
                        FirebaseFirestoreException.Code.ABORTED)   // It is not possible to add a higher installment to the debt
            }

        }.addOnSuccessListener { creditoActive -> // On Success
            Log.d("TAG", creditoActive.toString())
            offer(Resource.Success(creditoActive.toString()))
        }.addOnFailureListener { e ->// On Error
            Log.d("TAG", "No fue posible agregar una nueva cuota")
            offer(Resource.Error(e.message.toString(), null))
        }

        awaitClose {

        }

    } as Flow<Resource<String>>

    @ExperimentalCoroutinesApi
    override fun getReports(): Flow<Resource<ReportsDaily>> = callbackFlow {

    }
}