package com.example.hub

interface BookClickListener
{
    fun onClick(book: Book)
    fun openWhatsAppChat(phoneNumber: String)
}