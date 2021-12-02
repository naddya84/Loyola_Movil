package app.wiserkronox.loyolasocios.service.repository

import androidx.annotation.WorkerThread
import app.wiserkronox.loyolasocios.service.model.*
import kotlinx.coroutines.flow.Flow

class LoyolaRepository(
    private val userDao: UserDao,
    private val assemblyDao: AssemblyDao,
    private val courseDao: CourseDao,
    private val certificateDao: CertificateDao,
    private val creditDao: CreditDao,
    private val creditPlanPayDao: CreditPlanPayDao,
    private val creditPlanPayDetailDao: CreditPlanPayDetailDao,
    private val creditExtractDao: CreditExtractDao,
    private val creditExtractDetailDao: CreditExtractDetailDao
) {

    val allUsers: Flow<List<User>> = userDao.getUsers()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(user: User): Long {
        return userDao.insert( user )
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update2(user: User): Int {
        return userDao.update2( user )
    }

    /*fun insert2(user: User): Flow<Long>{
        return userDao.insert2( user )
    }*/

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAll(){
        userDao.deleteAll()
    }


    fun getUserByEmail(email: String): User {
        return userDao.getUserByEmail( email )
    }

    fun getUserEmailPassword(email: String, password: String): User {
        return userDao.getUserByEmailPassword( email, password )
    }

    /*fun getUserEmail2(email: String): Flow<User> {
        return userDao.getUserByEmail2( email )
    }*/

    fun getUserByOauthUid(oauthUid: String): User {
        return userDao.getUserByOauth_uid( oauthUid)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(user: User){
        userDao.update(user)
    }

    /*************************************************************************************
     * Funciones para la instancia de la asamblea
     */
    val allAssemblys: Flow<List<Assembly>> = assemblyDao.getAssemblys()
    fun getAllAssembly(): List<Assembly> {
        return assemblyDao.getAllAssemblys()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertAssembly(assembly: Assembly): Long {
        return assemblyDao.insert( assembly )
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertAllAssembly(assemblys: List<Assembly>): List<Long>{
        return assemblyDao.insertAll( assemblys )
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAllAssemblys(){
        assemblyDao.deleteAll()
    }

    fun getAllAssemblysStatus(status: String): List<Assembly> {
        return assemblyDao.getAllAssemblysStatus(status)
    }
/*************************************************************************************
 * Funciones para la instancia de cursos
 */
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateCourse(course: Course): Int {
        return courseDao.updateC( course )
    }
    val allCourses: Flow<List<Course>> = courseDao.getCourses()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getAllCourses(): List<Course> {
        return courseDao.getAllCourses()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertAllCourses(courses: List<Course>): List<Long>{
        return courseDao.insertAllCourses( courses )
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAllCourses(){
        courseDao.deleteAll()
    }
    fun getAllCoursesStatus(status: String): List<Course> {
        return courseDao.getAllCoursesStatus(status)
    }

    /*************************************************************************************
     * Funciones para la instancia de certificados
     */
    @Suppress("RedundantSuspendModifier")
    suspend fun getAllCertificates():List<Certificate> {
        return  certificateDao.getAllCertificates()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertAllCertificates(certificates: List<Certificate>): List<Long>{
        return certificateDao.insertAllCertificates(certificates)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAllCertificates(){
        certificateDao.deleteAll()
    }

    /*************************************************************************************
     * Funciones para la instancia de creditos
     */
    @Suppress("RedundantSuspendModifier")
    suspend fun getAllCredits():List<Credit> {
        return  creditDao.getAllCredits()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertAllCredits(credits: List<Credit>): List<Long>{
        return creditDao.insertAllCredits(credits)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAllCredits(){
        creditDao.deleteAllCredits()
    }

    /*************************************************************************************
     * Funciones para la instancia de creditos plan de pagos
     */
    @Suppress("RedundantSuspendModifier")
    suspend fun getAllCreditsPlanPay():List<CreditPlanPay> {
        return  creditPlanPayDao.getAllCreditsPlanPay()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertAllCreditsPlanPay(credtisplanpay: List<CreditPlanPay>): List<Long>{
        return creditPlanPayDao.insertAllCreditsPlanPay(credtisplanpay)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAllCreditsPlanPay(){
        creditPlanPayDao.deleteAllCreditsPlanPay()
    }

    /*************************************************************************************
     * Funciones para la instancia de creditos plan de pagos detalle
     */
    @Suppress("RedundantSuspendModifier")
    suspend fun getAllCreditsPlanPayDetail():List<CreditPlanPayDetail> {
        return  creditPlanPayDetailDao.getAllCreditsPlanPayDetail()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertAllCreditsPlanPayDetail(creditsplanpaydetail: List<CreditPlanPayDetail>): List<Long>{
        return creditPlanPayDetailDao.insertAllCreditsPlanPayDetail(creditsplanpaydetail)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAllCreditsPlanPayDetail(){
        creditPlanPayDetailDao.deleteAllCreditsPlanPayDetail()
    }

    /*************************************************************************************
     * Funciones para la instancia de creditos extracto
     */
    @Suppress("RedundantSuspendModifier")
    suspend fun getAllCreditsExtract():List<CreditExtract> {
        return  creditExtractDao.getAllCreditsExtract()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertAllCreditsExtract(creditsextract: List<CreditExtract>): List<Long>{
        return creditExtractDao.insertAllCreditsExtract(creditsextract)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAllCreditsExtract(){
        creditExtractDao.deleteAllCreditsExtract()
    }
    /*************************************************************************************
     * Funciones para la instancia de creditos extracto detalle
     */
    @Suppress("RedundantSuspendModifier")
    suspend fun getAllCreditsExtractDetail():List<CreditExtractDetail> {
        return  creditExtractDetailDao.getAllCreditsExtractDetail()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertAllCreditsExtractDetail(creditsextractdetail: List<CreditExtractDetail>): List<Long>{
        return creditExtractDetailDao.insertAllCreditsExtractsDetail(creditsextractdetail)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAllCreditsExtractDetail(){
        creditExtractDetailDao.deleteAllCreditsExtractDetail()
    }
}