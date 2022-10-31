package app.wiserkronox.loyolasocios.view.ui

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import app.wiserkronox.loyolasocios.R
import app.wiserkronox.loyolasocios.service.LoyolaApplication
import app.wiserkronox.loyolasocios.service.model.Course
import app.wiserkronox.loyolasocios.service.model.User
import app.wiserkronox.loyolasocios.service.repository.LoyolaService
import com.android.volley.*
import com.android.volley.toolbox.HttpHeaderParser
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import us.zoom.sdk.*

class HomeActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    companion object {
        private const val TAG = "HomeActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_data, R.id.nav_pictures, R.id.nav_assembly, R.id.nav_info
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        initializeSdk(this)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.action_logout -> {
                closeSession()
                true
            } else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun closeSession(){
        val sharedPreferences = getSharedPreferences(
            getString(R.string.app_name),
            Context.MODE_PRIVATE
        )?: return
        with(sharedPreferences.edit()){
            remove("oauth_uid")
            remove("email")
            remove("password")
            commit()

            LoyolaApplication.getInstance()?.user = null

            val intent = Intent(this@HomeActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    /********************************************************************************************
     *  Funciones para controles de fragmentos
     */

    fun goHome(){
        navController.navigate(R.id.nav_home)
    }

    fun fixData(){
        val user = LoyolaApplication.getInstance()?.user
        if( user != null ) {
            //Modificamos el estado del usuario para que se realize la actualizacion de sus datos
            user.state = User.REGISTER_LOGIN_STATE
            user.data_online = false
            user.picture_1_online = false
            user.picture_2_online = false
            user.selfie_online = false
            LoyolaApplication.getInstance()?.user = user
            val intent = Intent(this@HomeActivity, MainActivity::class.java)
            intent.putExtra(MainActivity.FLAG_UPDATE_USER_DATA, true)
            startActivity(intent)
            finish()
        }
    }

    fun goCertificate( ){
        navController.navigate(R.id.nav_certificate, null, null);
    }
    fun goCredit( ){
        navController.navigate(R.id.nav_credit, null, null);
    }
    fun goCourse( ){
        navController.navigate(R.id.nav_course, null, null);
    }

    fun goMaps(){
        navController.navigate(R.id.nav_map, null, null);
    }


    /*********************************************************************************************
     * Funciones de conexion con el servidor
     */

    fun showMessage(message: String){
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }
    }

    fun backUpdate(user: User){
        GlobalScope.launch {
            LoyolaApplication.getInstance()?.repository?.update2(user)
        }
    }

    fun isOnline(): Boolean {
        val connMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connMgr.activeNetworkInfo
        return networkInfo?.isConnected == true
    }

    /**********************************************************************************************
     * Integracion con Zoom
     */

    fun initializeSdk(context: Context?) {
        val sdk = ZoomSDK.getInstance()

        val params = ZoomSDKInitParams()
        params.appKey = getString(R.string.zoom_app_key)
        params.appSecret = getString(R.string.zoom_app_secret)
        params.domain = "zoom.us"
        params.enableLog = true

        val listener: ZoomSDKInitializeListener = object : ZoomSDKInitializeListener {
            /**
             * @param errorCode [us.zoom.sdk.ZoomError.ZOOM_ERROR_SUCCESS] if the SDK has been initialized successfully.
             */
            override fun onZoomSDKInitializeResult(errorCode: Int, internalErrorCode: Int) {
                if( errorCode == ZoomError.ZOOM_ERROR_SUCCESS ){
                    Log.d(TAG, "Init Zoom Success");
                } else {
                    Log.d(
                        TAG,
                        "No se pudo inicializar el SDK de zoom, errorCode: $errorCode, Internal Error Code $internalErrorCode"
                    )
                }
            }
            override fun onZoomAuthIdentityExpired() {
                Log.d(TAG,"Autentificacion expiro")
            }
        }
        sdk.initialize(context, listener, params)
    }

    fun joinMeeting(zoom_code: String, zomm_password: String, userName: String) {
        val meetingService = ZoomSDK.getInstance().meetingService
        val options = JoinMeetingOptions()
        val params = JoinMeetingParams()
        params.displayName = userName
        params.meetingNo = zoom_code
        params.password = zomm_password
        meetingService.joinMeetingWithParams(this, params, options)
    }
    fun downloadDocumentCourse(output_directory: String, url_document: String, course: Course){

        val imageRequest = InputStreamVolleyRequest(
                Request.Method.GET,
                url_document,
                { response -> // response listener
                    val name = "curso.pdf"
                    val outputStream = openFileOutput(name, Context.MODE_PRIVATE);
                    outputStream.write(response);
                    outputStream.close();
                    Toast.makeText(this, "Descarga completada.", Toast.LENGTH_LONG).show();
                },
                { error -> // error listener
                    Log.d(MainActivity.TAG, error.message.toString())
                    Log.d(MainActivity.TAG, url_document)
                },
                null
        )
        LoyolaService.getInstance(this).addToRequestQueue(imageRequest)
    }
}
/**********************************************************************************************
 * Donwload document PDF course
 */
internal class InputStreamVolleyRequest(
        method: Int,
        mUrl: String?,
        listener: Response.Listener<ByteArray>,
        errorListener: Response.ErrorListener?,
        params: HashMap<String, String>?) : Request<ByteArray?>(method, mUrl, errorListener) {
    private val mListener: Response.Listener<ByteArray>
    private val mParams: Map<String, String>

    //create a static map for directly accessing headers
    var responseHeaders: Map<String, String>? = null

    @Throws(AuthFailureError::class)
    override fun getParams(): Map<String, String>? {
        return mParams
    }

    override fun deliverResponse(response: ByteArray?) {
        mListener.onResponse(response)
    }

    override fun parseNetworkResponse(response: NetworkResponse): Response<ByteArray?> {

        //Initialise local responseHeaders map with response headers received
        responseHeaders = response.headers

        //Pass the response data here
        return Response.success(response.data, HttpHeaderParser.parseCacheHeaders(response))
    }

    init {
        // TODO Auto-generated constructor stub
        // this request would never use cache.
        setShouldCache(false)
        mListener = listener
        if (params != null) {
            mParams = params
        } else {
            mParams = hashMapOf<String, String>()
        }
    }
}