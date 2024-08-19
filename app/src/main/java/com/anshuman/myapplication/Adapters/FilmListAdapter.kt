import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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


class FilmListAdapter(private val items: MutableList<Film>) : RecyclerView.Adapter<FilmListAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.movie_viewholder, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val film = items[position]
        holder.titleTxt.text = film.Title

        val requestOptions = RequestOptions().apply {
            transform(CenterCrop(), RoundedCorners(30))
        }

        Glide.with(context)
            .load(film.Poster)
            .apply(requestOptions)
            .into(holder.pic)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DeatilsViewActivity::class.java).apply {
                putExtra("object", film)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTxt: TextView = itemView.findViewById(R.id.nameTxt)
        val pic: ImageView = itemView.findViewById(R.id.pic)
    }
}