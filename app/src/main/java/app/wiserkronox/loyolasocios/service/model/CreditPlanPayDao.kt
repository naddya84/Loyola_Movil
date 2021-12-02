package app.wiserkronox.loyolasocios.service.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CreditPlanPayDao {
    @Query("select * from CreditPlanPay order by id asc")
    fun getAllCreditsPlanPay(): List<CreditPlanPay>

    @Query("delete from CreditPlanPay")
    suspend fun deleteAllCreditsPlanPay()


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllCreditsPlanPay(creditPlanPay: List<CreditPlanPay>): List<Long>
}
