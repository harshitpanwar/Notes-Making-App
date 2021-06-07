package com.example.notesmakingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.w3c.dom.Text

class MainActivity : AppCompatActivity(), INotesRVAdapter {

    private lateinit var viewModel: NoteViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var inputText: EditText
    private lateinit var addButton: FloatingActionButton
    private lateinit var text: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//all components declaration

        recyclerView = findViewById(R.id.recyclerview)
        inputText = findViewById(R.id.input)
        addButton = findViewById(R.id.addButton)
        text = findViewById(R.id.display_text)

//creating view model instance in the main activity
        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory
                .getInstance(application))
                .get(NoteViewModel::class.java)

//handling button click event
        addButton.setOnClickListener{
            submitData()
        }


//setting attributes of recycler view
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = NotesRVAdapter(this, this)
        recyclerView.adapter = adapter

//observing data from the view model

        viewModel.allNotes.observe(this, Observer {list ->
            list?.let {
                adapter.updateList(it)
                if(list.size==0)
                    text.visibility = View.VISIBLE
                else
                    text.visibility = View.INVISIBLE
            }
        })





    }

    override fun onItemClicked(note: Note) {
        viewModel.deleteNote(note)
    }


    fun submitData() {
        val noteText = inputText.text.toString()
        if(noteText.isNotEmpty()){
            viewModel.insertNote(Note(noteText))

        }

        inputText.setText("")

    }


}