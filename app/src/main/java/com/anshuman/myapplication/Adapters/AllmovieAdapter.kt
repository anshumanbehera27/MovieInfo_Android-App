import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anshuman.myapplication.Activities.DeatilsViewActivity
import com.anshuman.myapplication.Models.Film
import com.anshuman.myapplication.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.database.FirebaseDatabase
import java.util.Locale

class AllmovieAdapter(private var items: MutableList<Film>, private val context: Context) :
    RecyclerView.Adapter<AllmovieAdapter.ViewHolder>(), Filterable {

    private var itemsFiltered: MutableList<Film> = items
    private val favoritesRef = FirebaseDatabase.getInstance().getReference("favorites")
    private val favoriteFilms = mutableSetOf<String>() // Track favorite film IDs

    init {
        loadFavoriteFilms()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.allmovie_viewholder, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val film = itemsFiltered[position]
        holder.titleTxt.text = film.Title

        val requestOptions = RequestOptions().apply {
            transform(CenterCrop(), RoundedCorners(30))
        }

        Glide.with(context)
            .load(film.Poster)
            .apply(requestOptions)
            .into(holder.pic)

        // Set favorite button image based on whether the film is in favorites
        if (favoriteFilms.contains(film.Title)) {
            holder.favoriteBtn.setImageResource(R.drawable.fav) // Update to favorite image
        } else {
            holder.favoriteBtn.setImageResource(R.drawable.btn_2) // Update to not favorite image
        }

        holder.favoriteBtn.setOnClickListener {
            toggleFavorite(film)
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DeatilsViewActivity::class.java).apply {
                putExtra("object", film)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = itemsFiltered.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTxt: TextView = itemView.findViewById(R.id.nameTxt)
        val pic: ImageView = itemView.findViewById(R.id.pic)
        val favoriteBtn: ImageButton = itemView.findViewById(R.id.btnSave)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val query = charSequence?.toString()?.lowercase(Locale.getDefault())
                val filteredList = mutableListOf<Film>()

                if (query.isNullOrEmpty()) {
                    filteredList.addAll(items)
                } else {
                    for (film in items) {
                        if (film.Title.lowercase(Locale.getDefault()).contains(query)) {
                            filteredList.add(film)
                        }
                    }
                }

                return FilterResults().apply { values = filteredList }
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults?) {
                itemsFiltered = (filterResults?.values as? List<Film>)?.toMutableList() ?: mutableListOf()
                notifyDataSetChanged()
            }
        }
    }

    fun setData(newItems: MutableList<Film>) {
        this.items = newItems
        this.itemsFiltered = newItems
        notifyDataSetChanged()
    }

    private fun toggleFavorite(film: Film) {
        val filmId = film.Title

        if (favoriteFilms.contains(filmId)) {
            favoritesRef.child(filmId).removeValue() // Remove from favorites
            favoriteFilms.remove(filmId)
        } else {
            favoritesRef.child(filmId).setValue(film) // Add to favorites
            favoriteFilms.add(filmId)
        }

        // Notify adapter to refresh UI
        notifyDataSetChanged()
    }

    private fun loadFavoriteFilms() {
        favoritesRef.get().addOnSuccessListener { snapshot ->
            favoriteFilms.clear()
            for (child in snapshot.children) {
                val filmId = child.key
                filmId?.let { favoriteFilms.add(it) }
            }
            notifyDataSetChanged() // Refresh adapter with updated favorites
        }.addOnFailureListener {
            Log.e("Favorites", "Error loading favorites: ${it.message}")
        }
    }
}
