package com.example.seanrafferty.mygrouprandomiser.Utilities

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class UtilityMethods
{
    companion object {
        /**
         *
         */
        fun ConvertStringToDateTime(dateString: String): LocalDateTime
        {
            var localDateTime = LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
            return localDateTime
        }

        fun ConvertISODateStringToDateTime(dateString: String): LocalDateTime {
            var localDateTime = LocalDateTime.parse(dateString, DateTimeFormatter.ISO_DATE_TIME)
            return localDateTime
        }

        /**
         *
         */
        fun ConvertDateToSQLString(date: LocalDateTime): String {
            val stringDate = date.format(DateTimeFormatter.ISO_DATE_TIME)

            return stringDate
        }

        /**
         * Handle conversion of int to boolean
         * converts 1 to true else everything is false
         * @param value : value to check
         */
        fun ConvertIntToBoolean(value:Int) : Boolean
        {
            var result = false

            result = when(value) {
                1 -> true
                else -> { // Note the block
                    false
                }
            }
            return result
        }
    }
}