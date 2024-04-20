@Database(entities = ["Data_Class_Name"::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun "dao name"(): "DAO_CLASS_NAME"
}