@Dao
interface UserDao {

    @Insert
    suspend fun insert(user: "DATA_CLASS_NAME")

    @Query("SELECT * FROM {"DATA_CLASS_NAME IN CAMEL CASE"}")
    suspend fun getAllUsers(): List<DATA_CLASS_NAME>
}