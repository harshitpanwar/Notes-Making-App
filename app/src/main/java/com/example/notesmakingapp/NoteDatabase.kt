package com.example.notesmakingapp

abstract class NoteDatabase {

    abstract fun getNoteDao(): NoteDao


}