package com.mahmood.woro

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var iconButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Initialize the DrawerLayout and icon button
        drawerLayout = findViewById(R.id.drawer_layout)
        iconButton = findViewById(R.id.icon)

        // Set click listener on the icon button to open the drawer
        iconButton.setOnClickListener {
            Log.d("MainActivity", "Icon button clicked to open drawer")
            drawerLayout.openDrawer(GravityCompat.START)
        }

        val navigationView: NavigationView = findViewById(R.id.navigation_view)
        val headerView: View = navigationView.getHeaderView(0)

        // Initialize close button
        val closeButton: ImageView = headerView.findViewById(R.id.close_button)
        closeButton.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START) // Close the drawer when the close button is clicked
        }

        // Menu container for items
        val menuContainer: LinearLayout = headerView.findViewById(R.id.menu_container)

        // Add menu items dynamically
        val menuItems = arrayOf(
            "Home" to R.drawable.homefilled,
            "Dashboard" to R.drawable.ic_dash,
            "Inbox" to R.drawable.ic_inbox,
            "Broadcast" to R.drawable.ic_broadcast,
            "Contacts" to R.drawable.ic_contacts,
            "Template" to R.drawable.ic_template,
            "Media Library" to R.drawable.ic_media_library,
            "Quotation & Invoice" to R.drawable.ic_quotation_invoice,
            "Chatbot/Automation" to R.drawable.ic_chatbot_automation
        )

        val inflater = LayoutInflater.from(this)

        for (item in menuItems) {
            // Inflate menu item layout
            val menuItemView = inflater.inflate(R.layout.nav_drawer_layout, menuContainer, false)



            // Get references to the views
            val menuIcon: ImageView = menuItemView.findViewById(R.id.menu_icon)
            val menuText: TextView = menuItemView.findViewById(R.id.menu_text)

            // Set icon and text
            menuIcon.setImageResource(item.second)
            menuText.text = item.first

            // Add click listener for the menu item
            menuItemView.setOnClickListener {
                Toast.makeText(this, "Clicked: $item", Toast.LENGTH_SHORT).show() // Test Toast
                handleMenuClick(item.first)
            }

            // Add the inflated view to the menu container
            menuContainer.addView(menuItemView)
        }
    }

    private fun handleMenuClick(menuItem: String) {
        Log.d("MainActivity", "Menu item clicked: $menuItem") // Log the clicked menu item

        val fragment: Fragment = when (menuItem) {
            "Dashboard" -> DashboardFragment() // Replace with your actual Dashboard Fragment class
            "Inbox" -> {
                Log.d("MainActivity", "Navigating to Inbox Fragment")
                InboxFragment() // Ensure this is the correct fragment
            }
            "Media Library" -> MediaLibraryFragment()
            // Add cases for other menu items as needed
            else -> return // Handle other menu items or do nothing
        }

        // Replace the current fragment with the selected one
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment) // Ensure you have a FrameLayout with this ID in your activity_main.xml
            .addToBackStack(null) // Add to back stack to allow navigation back
            .commit()

        // Close the drawer with a slight delay to allow the fragment to load
        Handler().postDelayed({
            drawerLayout.closeDrawer(Gravity.LEFT)
        }, 300) // Adjust delay as needed
    }
}
