package com.example.note

import android.app.Activity
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat

class MainActivity : Activity() {

    private lateinit var noteEditText: EditText
    private lateinit var searchEditText: EditText
    private lateinit var clearSearchButton: ImageButton
    private lateinit var saveButton: Button
    private lateinit var clearButton: Button

    private val sharedPreferences by lazy {
        getSharedPreferences("NotesApp", MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupClickListeners()
        setupSearchListener()
        loadSavedNote()
    }

    private fun initViews() {
        noteEditText = findViewById(R.id.noteEditText)
        searchEditText = findViewById(R.id.searchEditText)
        clearSearchButton = findViewById(R.id.clearSearchButton)
        saveButton = findViewById(R.id.saveButton)
        clearButton = findViewById(R.id.clearButton)
    }

    private fun setupClickListeners() {

        saveButton.setOnClickListener {
            saveNote()
            showToast("Заметка сохранена")
        }

        clearButton.setOnClickListener {
            noteEditText.setText("")
            searchEditText.setText("")
            saveNote()
            showToast("Заметка очищена")
        }

        clearSearchButton.setOnClickListener {
            searchEditText.setText("")
        }
    }

    private fun setupSearchListener() {
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchText = s.toString()
                clearSearchButton.visibility = if (searchText.isNotEmpty()) View.VISIBLE else View.GONE
                highlightSearchText(searchText)
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun loadSavedNote() {
        val savedNote = sharedPreferences.getString("note", "") ?: ""
        noteEditText.setText(savedNote)
    }

    private fun saveNote() {
        sharedPreferences.edit()
            .putString("note", noteEditText.text.toString())
            .apply()
    }

    private fun highlightSearchText(searchText: String) {
        val noteText = noteEditText.text.toString()

        if (searchText.isEmpty()) {
            noteEditText.setText(noteText)
            return
        }

        val spannable = SpannableString(noteText)
        var startIndex = 0

        while (startIndex < noteText.length) {
            val index = noteText.indexOf(searchText, startIndex, ignoreCase = true)
            if (index == -1) break

            spannable.setSpan(
                BackgroundColorSpan(Color.YELLOW),
                index,
                index + searchText.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            startIndex = index + searchText.length
        }

        noteEditText.setText(spannable)

        noteEditText.setSelection(noteEditText.text.length)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
        saveNote()
    }
}