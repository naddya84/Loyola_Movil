package app.wiserkronox.loyolasocios.service.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CreditDao {
    @Query("select * from Credit order by date_desem asc")
    fun getAllCredits(): List<Credit>

    @Query("delete from Credit")
    suspend fun deleteAllCredits()


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllCredits(credits: List<Credit>): List<Long>
}
