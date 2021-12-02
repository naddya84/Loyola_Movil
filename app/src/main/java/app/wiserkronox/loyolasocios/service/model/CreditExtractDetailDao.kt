package app.wiserkronox.loyolasocios.service.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CreditExtractDetailDao {
    @Query("select * from CreditExtractDetail order by id asc")
    fun getAllCreditsExtractDetail(): List<CreditExtractDetail>

    @Query("delete from CreditExtractDetail")
    suspend fun deleteAllCreditsExtractDetail()


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllCreditsExtractsDetail(creditExtractDetail: List<CreditExtractDetail>): List<Long>
}
