@file:Suppress("DEPRECATION")

package com.example.hub

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.recyclerview.widget.GridLayoutManager
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ActionTypes
import com.denzcoskun.imageslider.constants.AnimationTypes
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemChangeListener
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.interfaces.TouchListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.hub.databinding.ActivityMainBinding
import com.example.hub.model.Rating
import com.google.android.gms.location.*
import com.google.firebase.auth.FirebaseAuth
import soup.neumorphism.NeumorphCardView
import java.util.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import android.app.ProgressDialog
import android.widget.RatingBar

class MainActivity : AppCompatActivity(), BookClickListener{
    lateinit var locationRequest: LocationRequest
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var binding: ActivityMainBinding
    lateinit var toggle : ActionBarDrawerToggle
    private lateinit var database: DatabaseReference


    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)



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

        val imageSlider = findViewById<ImageSlider>(R.id.image_slider)
        val imageList = ArrayList<SlideModel>() // Create image list
        imageList.add(SlideModel(R.drawable.kipchoge, "Kipchoge making history in the Ineos 1:59 Challenge."))
        imageList.add(SlideModel(R.drawable.volleyball, "Kenya men's volleyball team ranked fifth in Africa"))
        imageList.add(SlideModel(R.drawable.obiri, "Kenya to bid for 2025 World Athletics Championships."))
        imageList.add(SlideModel(R.drawable.olunga, "Olunga next target to be seen in the EPL."))
        imageList.add(SlideModel(R.drawable.omanyalas, "Ferdinand Omanyala wins first African 100m title."))
        imageList.add(SlideModel(R.drawable.kipchoge, "Kipchoge making history in the Ineos 1:59 Challenge."))
        imageList.add(SlideModel(R.drawable.volleyball, "Kenya men's volleyball team ranked fifth in Africa"))
        imageList.add(SlideModel(R.drawable.obiri, "Kenya to bid for 2025 World Athletics Championships."))



        imageSlider.setImageList(imageList, ScaleTypes.CENTER_CROP)

        imageSlider.setSlideAnimation(AnimationTypes.ZOOM_OUT)
        imageSlider.setItemClickListener(object : ItemClickListener {
            override fun onItemSelected(position: Int) {
                // You can listen here.
                println("normal")
            }

            override fun doubleClick(position: Int) {
                // Do not use onItemSelected if you are using a double click listener at the same time.
                // Its just added for specific cases.
                // Listen for clicks under 250 milliseconds.
                println("its double")
            }
        })
        imageSlider.setItemChangeListener(object : ItemChangeListener {
            override fun onItemChanged(position: Int) {
                //println("Pos: " + position)
            }
        })
        imageSlider.setTouchListener(object : TouchListener {
            override fun onTouched(touched: ActionTypes, position: Int) {
                if (touched == ActionTypes.DOWN){
                    imageSlider.stopSliding()
                } else if (touched == ActionTypes.UP ) {
                    imageSlider.startSliding(3000)
                }
            }
        })














        val homeInput = findViewById<ImageView>(R.id.HomeImageView)
        homeInput.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }


        val drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
        val navView : NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home -> {
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)

                }

                R.id.rate_us-> {


                    val builder = AlertDialog.Builder(this)
                    val view = layoutInflater.inflate(R.layout.dialog_rate,null)

                    builder.setView(view)
                    val dialog = builder.create()


                    val ratingImage = view.findViewById<ImageView>(R.id.ratingImage)
                    val ratingBtn = view.findViewById<RatingBar>(R.id.ratingBar)
                    ratingBtn.setOnRatingBarChangeListener { rBar, _, _ ->
                        when (rBar.rating.toInt()) {
                            1 -> ratingImage.setImageResource(R.drawable.one_star)
                            2 -> ratingImage.setImageResource(R.drawable.two_star)
                            3 -> ratingImage.setImageResource(R.drawable.three_star)
                            4 -> ratingImage.setImageResource(R.drawable.four_star)
                            5 -> ratingImage.setImageResource(R.drawable.five_star)
                        }
                    }

                    val rateNowBtn = view.findViewById<AppCompatButton>(R.id.rateNowBtn)
                    rateNowBtn.setOnClickListener {
                        val ratingBar = view.findViewById<RatingBar>(R.id.ratingBar)
                        val rating = ratingBar.rating

                        // Show progress dialog while rating is being saved to Firebase
                        val progressDialog = ProgressDialog(this)
                        progressDialog.setMessage("Submitting rating...")
                        progressDialog.setCancelable(false)
                        progressDialog.show()

                        // Get the current user's ID from FirebaseAuth
                        val currentUser = FirebaseAuth.getInstance().currentUser
                        val userId = currentUser?.uid ?: ""

                        // Create a Rating object to be saved in Firebase
                        val userRating = Rating(userId, rating)

                        // Save the rating in Firebase Realtime Database
                        database = FirebaseDatabase.getInstance().reference.child("ratings")
                        val newRatingRef = database.push()
                        newRatingRef.setValue(userRating)
                            .addOnSuccessListener {
                                progressDialog.dismiss()
                                Toast.makeText(this, "Rating submitted successfully", Toast.LENGTH_SHORT).show()
                                dialog.dismiss()
                            }
                            .addOnFailureListener {
                                progressDialog.dismiss()
                                Toast.makeText(this, "Failed to submit rating", Toast.LENGTH_SHORT).show()
                            }
                    }


                    view.findViewById<Button>(R.id.laterBtn).setOnClickListener{
                        dialog.dismiss()
                    }


                    if (dialog.window != null) {
                        dialog.window!!.setBackgroundDrawable(ColorDrawable(0))
                    }
                    dialog.show()

                }

             

                R.id.logout-> {
                    FirebaseAuth.getInstance().signOut()
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                }


                R.id.share-> {
                    val shareIntent = Intent().apply {
                        this.action = Intent.ACTION_SEND
                        this.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id=com.whatsapp")
                        this.type = "text/plain"
                    }
                    startActivity(shareIntent)
                }
            }
            true
        }


        populateBooks()


        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            adapter = CardAdapter(bookList, this@MainActivity)
        }


    }


    override fun onClick(book: Book)
    {
        val intent = Intent(applicationContext, DetailActivity::class.java)
        intent.putExtra(BOOK_ID_EXTRA, book.id)
        startActivity(intent)
    }

    override fun openWhatsAppChat(phoneNumber: String) {
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

    private fun populateBooks()
    {
        bookList.clear()
        val book1 = Book(
            R.drawable.footballpictures,

            "Main Campus",
            "FOOTBALL (SOCCER)",
            "Football is by far the most popular sport in Kenya. The popularity of football is so high that Kenyans of all ages like to play it and watch it.In addition, Kenyans like betting on sports, especially football, so it doesn't surprise how many passionate bettors Kenya has. Also, some Kenyan footballers have gained global fame, including Dennis Oliech, Victor Wanyama, Michael Olunga, and Francis Kahata.\n"

        )
        bookList.add(book1)

        val book2 = Book(
            R.drawable.athleticspictures,
            "Main Campus",
            "ATHLETICS",
            "You may not think about Kenya when football is mentioned, but when we hear athletics, we think about Kenya as the country is proud of some of the best athletes in the world. They are present and dominate in many categories, including cross country, field, track, and marathon.Catherine Ndereba is a two-time world champion and four-time women's Boston Marathon winner. However, the list of famous and successful athletics players from Kenya is long. It includes Samuel Wanjiru, Eliud Kipchoge, Kipchoge Keino, Vivian Cheruiyot, LinetMasai, Paul Tergat, JanethJepkosgei, and Pamela Jelimo.\n"

        )
        bookList.add(book2)

        val book3 = Book(
            R.drawable.basketballpictures,
            "Main Campus",
            "BASKETBALL",
            "Basketball is growing in popularity in Kenya. The late 20th century was a great time for the Kenyan national basketball team. The team managed to qualify for the ABC (African Basketball Championship) a few times between the mid-80 and mid-90. They even got to the semi-finals in the 1993 championship.The most popular basketball players in Kenya are Ligare Griffin, Ongoro Joseph, KorangaOkall, Abuto Crispin, Onono Fredrick, and Bosire Victor.\n"

        )
        bookList.add(book3)


        val book4 = Book(
            R.drawable.volleyballpictures,
            "Main Campus",
            "VOLLEYBALL",
            "Volleyball is a sport most popular among the younger generation. Various Kenyan clubs won over ten African championships in the past three decades. The women's national team was wildly successful, winning nine continental titles.The most popular vollebay players in Kenya are Sharon Chepchumba, Jane Wairimu, Noel Murambi, Kasaya, AgripinaKundu, and Violet Makuto.\n"

        )
        bookList.add(book4)

        val book5 = Book(
            R.drawable.rugbypictures,
            "Main Campus",
            "RUGBY UNION",
            "Although we put athletics as the number two on our list, the second most popular sport in Kenya is rugby union. The sport was introduced at the beginning of the 20th century when settlers and British officials played the first match in Mombasa.Since then, rugby union has become pretty popular in the country. Today, there are over 50,000 licensed rugby players in Kenya, including the icons such as Oscar Osir, Humphrey Kayange, Edward Rombo, \u200B\u200BPaul Murunga, and Roger Akena. \n"

        )
        bookList.add(book5)

        val book6 = Book(
            R.drawable.boxingpictures,
            "Main Campus",
            "BOXING",
            "Boxing is such an underrated sport—well, at least in Kenya.Before you start boxing, it is in your interest to understand its terms and conditions.For one, you cannot compete professionally until you are 18 years of age.Now, there are a few names that have taken up the sport seriously. These are the likes of Elly Ajowi, Christine Ongare, Richard Murunga, Fatuma Zarika, Stephen Muchoki, Elizabeth Akinyi, and Felix Maina among others.\n"

        )
        bookList.add(book6)

        val book7 = Book(
            R.drawable.tennisspictures,
            "Main Campus",
            "TENNIS",
            "In a peaceful retirement village, four unlikely friends meet up once a week to investigate unsolved murders.But when a brutal killing takes place on their very doorstep, the Thursday Murder Club find themselves in the middle of their first live case.Elizabeth, Joyce, Ibrahim and Ron might be pushing eighty but they still have a few tricks up their sleeves.\n"

        )
        bookList.add(book7)

        val book8 = Book(
            R.drawable.handballpictures,
            "Main Campus",
            "HANDBALL",
            "In a peaceful retirement village, four unlikely friends meet up once a week to investigate unsolved murders.But when a brutal killing takes place on their very doorstep, the Thursday Murder Club find themselves in the middle of their first live case.Elizabeth, Joyce, Ibrahim and Ron might be pushing eighty but they still have a few tricks up their sleeves.\n"

        )
        bookList.add(book8)
        val book9 = Book(
            R.drawable.rallyingpictures,
            "Main Campus",
            "RALLYING",
            "In a peaceful retirement village, four unlikely friends meet up once a week to investigate unsolved murders.But when a brutal killing takes place on their very doorstep, the Thursday Murder Club find themselves in the middle of their first live case.Elizabeth, Joyce, Ibrahim and Ron might be pushing eighty but they still have a few tricks up their sleeves.\n"

        )
        bookList.add(book9)

        val book10 = Book(
            R.drawable.badmintonpictures,
            "Main Campus",
            "BADMINTON",
            "In a peaceful retirement village, four unlikely friends meet up once a week to investigate unsolved murders.But when a brutal killing takes place on their very doorstep, the Thursday Murder Club find themselves in the middle of their first live case.Elizabeth, Joyce, Ibrahim and Ron might be pushing eighty but they still have a few tricks up their sleeves.\n"

        )
        bookList.add(book10)







    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)


    }


}