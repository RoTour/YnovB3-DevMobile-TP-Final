package codes.routour.rotodo

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController

const val PREF_KEY_THEME = "THEME"
enum class THEME {
    light,
    dark
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val prefs = getSharedPreferences("common", Context.MODE_PRIVATE)
        Log.d("DEBUG", "PREF: ${prefs.getString(PREF_KEY_THEME, "holaquetal") == THEME.dark.name}")
        if (prefs.getString(PREF_KEY_THEME, null).isNullOrEmpty()){
            val prefsEditor = prefs.edit()
            prefsEditor.putString(PREF_KEY_THEME, THEME.dark.name)
            prefsEditor.apply()
        }
        if (prefs.getString(PREF_KEY_THEME, null) == THEME.dark.name) {
            Log.d("DEBUG", "SHOULD set theme to dark")
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            setTheme(R.style.Theme_RoToDoDark)
        } else {
            setTheme(R.style.Theme_RoToDoLight)
        }
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment_content_main).navigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d("DEBUG", "Main Activity option selected")
        return super.onOptionsItemSelected(item)
    }
}