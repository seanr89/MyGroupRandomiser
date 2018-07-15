package com.example.seanrafferty.mygrouprandomiser.Utilities

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class UtilityMethods
{
    companion object {

        /**
         * read in date time string (dd/mm/yyyy hh:mm) and convert to local date time
         * @param dateString :
         * @return
         */
        fun ConvertStringToDateTime(dateString: String): LocalDateTime
        {
            var localDateTime = LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
            return localDateTime
        }

        /**
         * process a date time string in ISO date time format and convert to LocalDateTime
         * @param dateString :
         * @return
         */
        fun ConvertISODateStringToDateTime(dateString: String): LocalDateTime {
            var localDateTime = LocalDateTime.parse(dateString, DateTimeFormatter.ISO_DATE_TIME)
            return localDateTime
        }

        /**
         * read in datetime parameter and convert to ISO DATE TIME formatted string
         * @param date :
         * @return
         */
        fun ConvertDateToSQLString(date: LocalDateTime): String {
            val stringDate = date.format(DateTimeFormatter.ISO_DATE_TIME)
            return stringDate
        }

        /**
         * converts a local date time param to human readable string format
         * @param date : the date to convert
         * @return string in format dd/MM/yyyy HH:mm
         */
        fun ConvertDateTimeToString(date: LocalDateTime): String
        {
            val stringDate = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
            return stringDate
        }

        /**
         * Handle conversion of int to boolean
         * converts 1 to true else everything is false
         * @param value : value to check to convert
         * @return boolean parameter
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

        /**
         * Convert a boolean parameter to its correspondiong integer
         * @param value : the boolean value to convert
         * @return (true = 1, false = 0)
         */
        fun ConvertBooleanToInt(value : Boolean) : Int
        {
            if(value)
            {
                return 1
            }
            return 0
        }
    }
}