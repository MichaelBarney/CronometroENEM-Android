# Architecture Components 
## Room
Foi criado um banco de dados utilizando a arquitetura Room (encontrado no arquivo Database.kt).

Este foi utilizado para armazenar informações temporais das provas realizadas com o aplicativo, seguindo a seguinte estrutura:

### Entidade
Uma Entidade Prova contendo ID, dia da prova, tempo que o usuário ficou realizando a prova e um booleano armazenando se a prova foi terminada antes da hora.
```
@Entity
data class Prova(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "day") val day: Int,
    @ColumnInfo(name = "duration") val duration: Int,
    @ColumnInfo(name = "cancelled") val cancelled: Boolean
)
```

### DAO
Um DAO contendo queries de inserção, remoção, e busca geral ou por dia. 
```
@Dao
interface ProvaDao {
    @Query("SELECT * FROM prova")
    fun getAll(): List<Prova>

    @Query("SELECT * FROM prova WHERE day IS :cDay")
    fun getByDay(cDay: Int): List<Prova>

    @Insert
    fun insertAll(vararg provas: Prova)

    @Delete
    fun delete(prova: Prova)
}
```

