package me.oleg.taglibro.framework.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import me.oleg.taglibro.R
import me.oleg.taglibro.databinding.ActivityMainBinding


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var b: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    private val viewModel: NoteViewModel by viewModels()

    companion object {
        private const val READ_REQUEST_CODE: Int = 42
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = DataBindingUtil.setContentView(this, R.layout.activity_main)

        drawerLayout = b.drawerLayout
        navController = findNavController(R.id.main_nav_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)

        // Set up ActionBar
        setSupportActionBar(b.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Set up navigation menu
        b.toolbar.setupWithNavController(
            navController,
            appBarConfiguration
        )

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            b.appbar.setExpanded(true, true)
        }


        b.navigationView.setNavigationItemSelectedListener {
            it.isChecked = true
            drawerLayout.closeDrawers()
            return@setNavigationItemSelectedListener when (it.itemId) {
                R.id.content_resolver -> {
                    performFileSearch()
                    true
                }
                R.id.notes -> {
                    navController.navigate(R.id.note_list_fragment)
                    true
                }
                else -> false
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            try {
                if (resultData?.data == null) {
                    resultData?.clipData.let {

                        for (i in 0 until it!!.itemCount) {

                            val uri = it.getItemAt(i)?.uri
                            uri?.let { viewModel.saveFromUri(it) }
                        }
                    }
                } else {
                    resultData.data?.also { uri ->

                        viewModel.saveFromUri(uri)

                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun performFileSearch() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            type = "*/*"
        }

        startActivityForResult(intent, READ_REQUEST_CODE)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
