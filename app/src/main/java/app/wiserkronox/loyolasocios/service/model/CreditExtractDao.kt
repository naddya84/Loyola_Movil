package app.wiserkronox.loyolasocios.service.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CreditExtractDao {
    @Query("select * from CreditExtract order by number asc")
    fun getAllCreditsExtract(): List<CreditExtract>

    @Query("delete from CreditExtract")
    suspend fun deleteAllCreditsExtract()


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllCreditsExtract(creditExtract: List<CreditExtract>): List<Long>
}
