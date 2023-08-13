package com.example.hub

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.example.hub.databinding.ActivityDetailBinding


class DetailActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val facebookIcon = findViewById<ImageView>(R.id.facebook)
        val twitterIcon = findViewById<ImageView>(R.id.twitter)
        val instagramIcon = findViewById<ImageView>(R.id.instagram)
        val whatsappButton = findViewById<ImageView>(R.id.whatsapp)
        val linkedinButton = findViewById<ImageView>(R.id.linkedin)

        facebookIcon.setOnClickListener {
            val facebookUrl = "https://www.facebook.com/ummauniversity"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl))
            startActivity(intent)
        }

        twitterIcon.setOnClickListener {
            val twitterUrl = "https://twitter.com/ummauniversity"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(twitterUrl))
            startActivity(intent)
        }

        instagramIcon.setOnClickListener {
            val instagramUrl = "https://www.instagram.com/ummauniversity/?hl=en"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(instagramUrl))
            startActivity(intent)
        }

        whatsappButton.setOnClickListener {
            val phoneNumber = "+254700875933" // Replace with the actual phone number
            openWhatsAppChat(phoneNumber)
        }
        linkedinButton.setOnClickListener {
            val instagramUrl = "https://www.linkedin.com/company/ummauniversity?originalSubdomain=ke"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(instagramUrl))
            startActivity(intent)
        }


        val bookID = intent.getIntExtra(BOOK_ID_EXTRA, -1)
        val book = bookFromID(bookID)
        if(book != null)
        {
            binding.cover.setImageResource(book.cover)
            binding.title.text = book.title
            binding.description.text = book.description
            binding.author.text = book.author
        }
    }

    private fun openWhatsAppChat(phoneNumber: String) {
        val packageManager = packageManager
        val intent = Intent(Intent.ACTION_VIEW)

        try {
            val url = "https://api.whatsapp.com/send?phone=$phoneNumber"
            intent.data = Uri.parse(url)
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            // Handle the case where WhatsApp is not installed on the device
            Toast.makeText(this, "WhatsApp is not installed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun bookFromID(bookID: Int): Book?
    {
        for(book in bookList)
        {
            if(book.id == bookID)
                return book
        }
        return null
    }
}