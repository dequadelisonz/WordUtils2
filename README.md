# WordUtils2
This is small helper library written in Java for Android development.
It extends the well-known class org.apache.commons.lang3.text.WordUtils.

The most interesting method is: 

public static String wordToNumberIt(String number)

this method takes an input paremeter "number" which is a integer number expressed in words from 0 to 999999 and gives back an other string with numeral representation of this number.

This is working for Italian language only (pls notice the 'It' at the end of method name).

Example #1:
String numeral = WordUtils2.wordToNumberIt("centoventisei"); --> "centoventisei" is "onehundred and twenty six"

Logcat.v("output from wordToNumberIt : ",numeral); --> it prints out "output from wordToNumberIt : 126"

Example #2:
String numeral = WordUtils2.wordToNumberIt("ventimiladuecentonovantaquattro"); --> "ventimiladuecentonovantaquattro" is "twentythousand and two hundred-ninety-four"

Logcat.v("output from wordToNumberIt : ",numeral); --> it prints out "output from wordToNumberIt : 20294"

The algoritm tries to catch also mixed inputs such as "3 ventidue (3 twentytwo)" to transform it in "322".

This method was implemented to convert spoken numbers grabbed from ASR of Google/ Android into numbers useful for calculation or other purposes.

The main class can be improved to include other methods to convert numbers from other languages, or to create a general purpose method which read the default language of the local system and decides by itself the language to select.
