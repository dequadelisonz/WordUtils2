/*****************************************************************
 Copyright 2015 Paolo Martinello; created on 18/04/15

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 *******************************************************************/
package it.code.martin;

import org.apache.commons.lang3.text.WordUtils;

import java.util.ArrayList;
import java.util.Random;

public class WordUtils2 extends WordUtils {

    protected final static String TAG="WordUtils2";

    public WordUtils2() {
        super();
    }

    public static String cleanBracketTags(String str,char openingBracket,char closingBracket) {

        if (!str.equals("")) {
            int begin = str.indexOf(openingBracket);
            if (begin!=-1) {
                int end = str.indexOf(closingBracket);
                String first=str.substring(0, begin);
                String last=cleanBracketTags(str.substring(end + 1),
                        openingBracket, closingBracket);
                str = first+last;
            }
        }
        return str;

    }


    public static String cleanStringToSearchUri(Boolean toCapitalize,String... params) {
        //capitalize or uncapitalize first letter of each word and replace spaces with %20
        String toSearch = params[0];
        toSearch=toSearch.trim();
        if (toCapitalize) {
            toSearch = WordUtils.capitalize(toSearch);
        } else toSearch=WordUtils.uncapitalize(toSearch);
        toSearch=toSearch.replaceAll(" ", "%20");
        return toSearch;
    }


    public static String getRandomStringFromArray(ArrayList<String> a){

        if ((a!=null)&&(a.size()>0)) {
            Random r=new Random();
            r.setSeed(System.currentTimeMillis());
            int i = r.nextInt(a.size());
            return a.get(i);
        } else
            throw new IllegalArgumentException(TAG+" The input array is empty or null");
    }


    public static String wordToNumberIt(String number) {
        /*convert a number written such as "undici (eleven)" in "11"
            or "centoventidue (one hundered and twenty two)" in "122"
            this is for Italian language only and works for numbers up to 999999
         */
        Double result=0.0;
        number=number.trim();
        number=number.replaceAll("-", "");
        String[] resultsS=number.split(" ");
        for (int i = 0; i < resultsS.length; i++) {
            try {
                result += Double.parseDouble(resultsS[i]);
            } catch (NumberFormatException e) {
                result+=convertSpokenToDouble(resultsS[i]);
            }
        }
        return Double.toString(result);
    }

    private static Double convertSpokenToDouble(String number) {
        Double result=0.0;
        String remainNumber=number;
        int mul=1;
        int x=0;//needed to consider that in case of hundred or thousand,in the next step a hundred
        //or thousand has already been added
        int dec=0;//needed to consider that the thousand multiplier has to be
        //propagated through the next 3 steps
        Boolean match;
        do {
            match=false;
            for (NumbersIt NI : NumbersIt.values()) {
                if (remainNumber.endsWith(NI.toString())) {
                    match = true;
                    //if current number is "thousand", the multiplier coming from previous step
                    //has to be set to one
                    if ((NI == NumbersIt.mila)
                            || (NI == NumbersIt.milae)
                            || (NI == NumbersIt.mille)
                            || (NI == NumbersIt.millee)) {
                        mul = 1;
                        x = 0;
                    }
                    result = result + (NI.value - x) * mul;
                    remainNumber = remainNumber.substring(0,
                            remainNumber.length()
                                    - NI.toString().length());
                    if (dec == 0) {
                        mul = 1;

                    } else
                        dec--;
                    x = 0;
                    //set the mul (multiplier) for next parse step, unless a "thousand" string
                    //
                    switch (NI) {
                        case cento:
                        case centoe:
                            mul *= 100;
                            x = 1;
                            break;
                        case mila:
                        case milae:
                            mul *= 1000;
                            x = 1;
                            dec = 3;
                            break;
                        default:
                            break;
                    }
                    break;
                }
            }
        } while (!remainNumber.equalsIgnoreCase("")&&match);
        if (!match) result=Double.NaN;
        return result;
    }

    public enum NumbersIt  {
        mila(1000),
        milae(1000),
        mille(1000),
        millee(1000),
        cento(100),
        centoe(100),
        novant(90),
        novanta(90),
        ottant(80),
        ottanta(80),
        settant(70),
        settanta(70),
        sessant(60),
        sessanta(60),
        cinquant(50),
        cinquanta(50),
        quarant(40),
        quaranta(40),
        trent(30),
        trenta(30),
        vent(20),
        venti(20),
        diciannove(19),
        diciotto(18),
        diciassette(17),
        sedici(16),
        quindici(15),
        quattordici(14),
        tredici(13),
        dodici(12),
        undici(11),
        dieci(10),
        nove(9),
        otto(8),
        sette(7),
        sei(6),
        cinque(5),
        quattro(4),
        tre(3),
        due(2),
        uno(1),
        zero(0);

        private int value;

        private NumbersIt(int value) {
            this.value=value;
        }

    }

}