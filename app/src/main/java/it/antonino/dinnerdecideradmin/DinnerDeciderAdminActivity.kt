package it.antonino.dinnerdecideradmin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import it.antonino.dinnerdecideradmin.databinding.DinnerdecideradminActivityBinding
import it.antonino.dinnerdecideradmin.ui.DinnerDeciderFragment

class DinnerDeciderAdminActivity : AppCompatActivity() {

    private lateinit var binding: DinnerdecideradminActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DinnerdecideradminActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, DinnerDeciderFragment())
            .commit()

    }
}