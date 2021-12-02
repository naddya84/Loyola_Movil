package app.wiserkronox.loyolasocios.service.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CreditPlanPayDetailDao {
    @Query("select * from CreditPlanPayDetail order by id asc")
    fun getAllCreditsPlanPayDetail(): List<CreditPlanPayDetail>

    @Query("delete from CreditPlanPayDetail")
    suspend fun deleteAllCreditsPlanPayDetail()


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllCreditsPlanPayDetail(creditPlanPayDetail: List<CreditPlanPayDetail>): List<Long>
}
