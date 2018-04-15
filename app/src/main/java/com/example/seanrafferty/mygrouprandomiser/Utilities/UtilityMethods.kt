package com.example.seanrafferty.mygrouprandomiser.Utilities

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class UtilityMethods
{
    companion object {
        /**
         *
         */
        fun ConvertStringToDateTime(dateString: String): LocalDateTime {
            var localDateTime = LocalDateTime.parse(dateString)
            return localDateTime
        }

        /**
         *
         */
        fun ConvertDateToSQLString(date: LocalDateTime): String {
            val stringDate = date.format(DateTimeFormatter.ISO_DATE_TIME)

            return stringDate
        }
    }
}