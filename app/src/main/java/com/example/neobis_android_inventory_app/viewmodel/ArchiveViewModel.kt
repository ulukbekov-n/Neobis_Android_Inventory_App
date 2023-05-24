import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.neobis_android_inventory_app.model.Product

class ArchiveViewModel : ViewModel() {
    val archivedItems: MutableLiveData<List<Product>> = MutableLiveData()
    private var itemList = mutableListOf<Product>()

    fun addItem(item: Product) {
        val currentItems = archivedItems.value ?: emptyList()
        val updatedItems = currentItems + item
        archivedItems.value = updatedItems


        Log.d("ArchiveViewModel", "Item added: $item")
        Log.d("ArchiveViewModel", "Updated items: $updatedItems")
    }
    fun removeItem(position: Int) {
        val newList = itemList.toMutableList()
        newList.removeAt(position)
        itemList = newList
    }

    fun getItems(): LiveData<List<Product>> = archivedItems
}