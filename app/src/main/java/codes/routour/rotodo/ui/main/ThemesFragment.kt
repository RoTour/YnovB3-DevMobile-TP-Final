package codes.routour.rotodo.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import codes.routour.rotodo.MainActivity
import codes.routour.rotodo.PREF_KEY_THEME
import codes.routour.rotodo.R
import codes.routour.rotodo.THEME
import codes.routour.rotodo.databinding.FragmentThemesBinding
import codes.routour.rotodo.databinding.MainFragmentBinding

class ThemesFragment : Fragment() {
    private var _binding: FragmentThemesBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_themes, container, false)
        val prefs = requireActivity().getSharedPreferences("common", Context.MODE_PRIVATE)
        binding.darkThemeRadio.setOnClickListener {
            Log.d("DEBUG", "Clicked light theme")
            prefs.edit().putString(PREF_KEY_THEME, THEME.dark.name).apply()
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        binding.lightThemeRadio.setOnClickListener {
            Log.d("DEBUG", "Clicked light theme")
            prefs.edit().putString(PREF_KEY_THEME, THEME.light.name).apply()
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        return binding.root
    }
}