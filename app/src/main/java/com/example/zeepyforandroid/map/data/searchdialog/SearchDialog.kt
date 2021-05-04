package com.example.zeepyforandroid.map.data.searchdialog

import ir.mirrajabi.searchdialog.core.Searchable

class SearchDialog(private val title: String) : Searchable{
    override fun getTitle(): String = title
}