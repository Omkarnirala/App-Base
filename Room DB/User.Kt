@Entity
data class "MODEL_NAME"(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val email: String
)