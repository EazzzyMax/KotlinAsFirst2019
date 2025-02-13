@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson7.task1

import java.io.File
import java.lang.StringBuilder
import kotlin.math.max

/**
 * Пример
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Вывести его в выходной файл с именем outputName, выровняв по левому краю,
 * чтобы длина каждой строки не превосходила lineLength.
 * Слова в слишком длинных строках следует переносить на следующую строку.
 * Слишком короткие строки следует дополнять словами из следующей строки.
 * Пустые строки во входном файле обозначают конец абзаца,
 * их следует сохранить и в выходном файле
 */
fun alignFile(inputName: String, lineLength: Int, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    var currentLineLength = 0
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) {                         //ето получается сюда залетаю када строчка пустая
            outputStream.newLine()                    //а ну вопщем удаляю кучу лишних пустых строчек оставляю одну да скипаю поулчается континуе
            if (currentLineLength > 0) {
                outputStream.newLine()
                currentLineLength = 0
            }
            continue
        }
        for (word in line.split(" ")) {             //хоба парсим на словечечки
            if (currentLineLength > 0) {            //ну типа если  уже чото написал
                if (word.length + currentLineLength >= lineLength) {  //а ето типа если уже не влазит
                    outputStream.newLine() //создал
                    currentLineLength = 0 //обнулил
                } else {
                    outputStream.write(" ") //пробел
                    currentLineLength++ //щетчик
                }
            }
            outputStream.write(word) //словечечко
            currentLineLength += word.length //щетчик
        }
    }
    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * На вход подаётся список строк substrings.
 * Вернуть ассоциативный массив с числом вхождений каждой из строк в текст.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 */
fun countSubstrings(inputName: String, substrings: List<String>): Map<String, Int> {
    val input = File(inputName).readLines()
    val answer = substrings.toSet().map { it to 0 }.toMap().toMutableMap()
    for (str in input) { //по строчечкам
        for (wordZ in str.split(" ")) { //по словечечкам
            val word = wordZ.toLowerCase()
            for (search in substrings.toSet()) {  //проверяю каждую тупа по отдельности есть ли в слове
                for (i in 0..word.length - search.length) {
                    if (word.substring(i, i + search.length) == search.toLowerCase()) {
                        answer[search] = answer[search]!! + 1
                    }
                }
            }
        }
    }
    return answer

}


/**
 * Средняя
 *
 * В русском языке, как правило, после букв Ж, Ч, Ш, Щ пишется И, А, У, а не Ы, Я, Ю.
 * Во входном файле с именем inputName содержится некоторый текст на русском языке.
 * Проверить текст во входном файле на соблюдение данного правила и вывести в выходной
 * файл outputName текст с исправленными ошибками.
 *
 * Регистр заменённых букв следует сохранять.
 *
 * Исключения (жюри, брошюра, парашют) в рамках данного задания обрабатывать не нужно
 *
 */
fun sibilants(inputName: String, outputName: String) {

    fun letterReplace(z: Int, string: String): Char { //берет букву и выдает нужную
        val set = setOf('ж', 'ш', 'ч', 'щ')
        val map = mapOf('ы' to 'и', 'Ы' to 'И', 'я' to 'а', 'Я' to 'А', 'ю' to 'у', 'Ю' to 'У')
        if (string[z - 1].toLowerCase() in set)
            return (map[string[z]] ?: string[z])
        return (string[z])
    }

    val outputStream = File(outputName).bufferedWriter().use {
        val input = File(inputName).readLines()
        for (str in input) { //строчки целиком
            if (str.isNotEmpty()) {
                it.write(str[0].toString()) //записывает первый символ
                for (i in 1 until str.length) { //перебереат все кроме 1
                    it.write(letterReplace(i, str).toString())
                }
            }
            it.newLine()
        }
    }
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по центру
 * относительно самой длинной строки.
 *
 * Выравнивание следует производить путём добавления пробелов в начало строки.
 *
 *
 * Следующие правила должны быть выполнены:
 * 1) Пробелы в начале и в конце всех строк не следует сохранять.
 * 2) В случае невозможности выравнивания строго по центру, строка должна быть сдвинута в ЛЕВУЮ сторону
 * 3) Пустые строки не являются особым случаем, их тоже следует выравнивать
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых)
 *
 */
fun centerFile(inputName: String, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    if (File(inputName).readLines().isEmpty()) {
        outputStream.write("")
        outputStream.close()
        return
    }
    val input = File(inputName).readLines().map { it.trim() } //имею текст без пробелов в массиве по строчкам
    val maxLength = input.maxBy { it.length }!!.length  //имею макс длину
    for (str in input) {
        outputStream.write(str.padStart((maxLength - str.length) / 2 + str.length))
        outputStream.newLine()
    }
    outputStream.close()
}

/**
 * Сложная
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по левому и правому краю относительно
 * самой длинной строки.
 * Выравнивание производить, вставляя дополнительные пробелы между словами: равномерно по всей строке
 *
 * Слова внутри строки отделяются друг от друга одним или более пробелом.
 *
 * Следующие правила должны быть выполнены:
 * 1) Каждая строка входного и выходного файла не должна начинаться или заканчиваться пробелом.
 * 2) Пустые строки или строки из пробелов трансформируются в пустые строки без пробелов.
 * 3) Строки из одного слова выводятся без пробелов.
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых).
 *
 * Равномерность определяется следующими формальными правилами:
 * 5) Число пробелов между каждыми двумя парами соседних слов не должно отличаться более, чем на 1.
 * 6) Число пробелов между более левой парой соседних слов должно быть больше или равно числу пробелов
 *    между более правой парой соседних слов.
 *
 * Следует учесть, что входной файл может содержать последовательности из нескольких пробелов  между слвоами. Такие
 * последовательности следует учитывать при выравнивании и при необходимости избавляться от лишних пробелов.
 * Из этого следуют следующие правила:
 * 7) В самой длинной строке каждая пара соседних слов должна быть отделена В ТОЧНОСТИ одним пробелом
 * 8) Если входной файл удовлетворяет требованиям 1-7, то он должен быть в точности идентичен выходному файлу
 */
fun alignFileByWidth(inputName: String, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    if (File(inputName).readLines().isEmpty()) {
        outputStream.write("")
        outputStream.close()
        return
    }
    val input = File(inputName).readLines().map { it.split(" ").filter { it.isNotEmpty() } }
    //имею готовйы список строк (строка - список слов без пробелов)
    val maxString = input.maxBy { it.map { it.length }.sum() + it.size - 1 }
    val maxLen = maxString!!.sumBy { it.length } + maxString.size - 1
    for (str in input) {
        val toAns = StringBuilder()
        when {
            str.size > 1 -> {
                val noSpace = str.sumBy { it.length }
                val lot = str.size - 1 //lot - количество "дырок" 5
                val space = maxLen - noSpace   //space - то скок надо распределить на "дырки" 27
                val inFirstCycle = space / lot + 1 //количество пробелов (тех что больше)
                val inSecondCycle = inFirstCycle - 1 //количество пробелов (тех что меньше)
                val firstCycle = space - inSecondCycle * lot //сколько раз по большему количеству пробелов
                val secondCycle = lot + 1 - firstCycle
                for (i in 0 until firstCycle) {
                    toAns.append(str[i])
                    for (z in 1..inFirstCycle)
                        toAns.append(" ")
                }
                for (i in 0 until secondCycle) {
                    toAns.append(str[firstCycle + i])
                    for (z in 1..inSecondCycle)
                        toAns.append(" ")
                }

            }
            str.size == 1 -> {
                for (i in 1..maxLen - str[0].length) toAns.append(" ")
                toAns.append(str[0])
            }
            else -> for (i in 1..maxLen) toAns.append(" ")
        }
        outputStream.write(toAns.toString().trim())
        outputStream.newLine()
    }
    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * Вернуть ассоциативный массив, содержащий 20 наиболее часто встречающихся слов с их количеством.
 * Если в тексте менее 20 различных слов, вернуть все слова.
 *
 * Словом считается непрерывная последовательность из букв (кириллических,
 * либо латинских, без знаков препинания и цифр).
 * Цифры, пробелы, знаки препинания считаются разделителями слов:
 * Привет, привет42, привет!!! -привет?!
 * ^ В этой строчке слово привет встречается 4 раза.
 *
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 * Ключи в ассоциативном массиве должны быть в нижнем регистре.
 *
 */
fun top20Words(inputName: String): Map<String, Int> = TODO()

/**
 * Средняя
 *
 * Реализовать транслитерацию текста из входного файла в выходной файл посредством динамически задаваемых правил.

 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * В ассоциативном массиве dictionary содержится словарь, в котором некоторым символам
 * ставится в соответствие строчка из символов, например
 * mapOf('з' to "zz", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "yy", '!' to "!!!")
 *
 * Необходимо вывести в итоговый файл с именем outputName
 * содержимое текста с заменой всех символов из словаря на соответствующие им строки.
 *
 * При этом регистр символов в словаре должен игнорироваться,
 * но при выводе символ в верхнем регистре отображается в строку, начинающуюся с символа в верхнем регистре.
 *
 * Пример.
 * Входной текст: Здравствуй, мир!
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Пример 2.
 *
 * Входной текст: Здравствуй, мир!
 * Словарь: mapOf('з' to "zZ", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "YY", '!' to "!!!")
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun transliterate(inputName: String, dictionary: Map<Char, String>, outputName: String) {
    TODO()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName имеется словарь с одним словом в каждой строчке.
 * Выбрать из данного словаря наиболее длинное слово,
 * в котором все буквы разные, например: Неряшливость, Четырёхдюймовка.
 * Вывести его в выходной файл с именем outputName.
 * Если во входном файле имеется несколько слов с одинаковой длиной, в которых все буквы разные,
 * в выходной файл следует вывести их все через запятую.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 * Пример входного файла:
 * Карминовый
 * Боязливый
 * Некрасивый
 * Остроумный
 * БелогЛазый
 * ФиолетОвый

 * Соответствующий выходной файл:
 * Карминовый, Некрасивый
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun chooseLongestChaoticWord(inputName: String, outputName: String) {
    TODO()
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе элементы текстовой разметки следующих типов:
 * - *текст в курсивном начертании* -- курсив
 * - **текст в полужирном начертании** -- полужирный
 * - ~~зачёркнутый текст~~ -- зачёркивание
 *
 * Следует вывести в выходной файл этот же текст в формате HTML:
 * - <i>текст в курсивном начертании</i>
 * - <b>текст в полужирном начертании</b>
 * - <s>зачёркнутый текст</s>
 *
 * Кроме того, все абзацы исходного текста, отделённые друг от друга пустыми строками, следует обернуть в теги <p>...</p>,
 * а весь текст целиком в теги <html><body>...</body></html>.
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 * Отдельно следует заметить, что открывающая последовательность из трёх звёздочек (***) должна трактоваться как "<b><i>"
 * и никак иначе.
 *
 * При решении этой и двух следующих задач полезно прочитать статью Википедии "Стек".
 *
 * Пример входного файла:
Lorem ipsum *dolor sit amet*, consectetur **adipiscing** elit.
Vestibulum lobortis, ~~Est vehicula rutrum *suscipit*~~, ipsum ~~lib~~ero *placerat **tortor***,

Suspendisse ~~et elit in enim tempus iaculis~~.
 *
 * Соответствующий выходной файл:
<html>
<body>
<p>
Lorem ipsum <i>dolor sit amet</i>, consectetur <b>adipiscing</b> elit.
Vestibulum lobortis. <s>Est vehicula rutrum <i>suscipit</i></s>, ipsum <s>lib</s>ero <i>placerat <b>tortor</b></i>.
</p>
<p>
Suspendisse <s>et elit in enim tempus iaculis</s>.
</p>
</body>
</html>
 *
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlSimple(inputName: String, outputName: String) {
    TODO()
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе набор вложенных друг в друга списков.
 * Списки бывают двух типов: нумерованные и ненумерованные.
 *
 * Каждый элемент ненумерованного списка начинается с новой строки и символа '*', каждый элемент нумерованного списка --
 * с новой строки, числа и точки. Каждый элемент вложенного списка начинается с отступа из пробелов, на 4 пробела большего,
 * чем список-родитель. Максимально глубина вложенности списков может достигать 6. "Верхние" списки файла начинются
 * прямо с начала строки.
 *
 * Следует вывести этот же текст в выходной файл в формате HTML:
 * Нумерованный список:
 * <ol>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ol>
 *
 * Ненумерованный список:
 * <ul>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ul>
 *
 * Кроме того, весь текст целиком следует обернуть в теги <html><body>...</body></html>
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 *
 * Пример входного файла:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
 * Утка по-пекински
 * Утка
 * Соус
 * Салат Оливье
1. Мясо
 * Или колбаса
2. Майонез
3. Картофель
4. Что-то там ещё
 * Помидоры
 * Фрукты
1. Бананы
23. Яблоки
1. Красные
2. Зелёные
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 *
 *
 * Соответствующий выходной файл:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
<html>
<body>
<ul>
<li>
Утка по-пекински
<ul>
<li>Утка</li>
<li>Соус</li>
</ul>
</li>
<li>
Салат Оливье
<ol>
<li>Мясо
<ul>
<li>
Или колбаса
</li>
</ul>
</li>
<li>Майонез</li>
<li>Картофель</li>
<li>Что-то там ещё</li>
</ol>
</li>
<li>Помидоры</li>
<li>
Фрукты
<ol>
<li>Бананы</li>
<li>
Яблоки
<ol>
<li>Красные</li>
<li>Зелёные</li>
</ol>
</li>
</ol>
</li>
</ul>
</body>
</html>
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlLists(inputName: String, outputName: String) {
    TODO()
}

/**
 * Очень сложная
 *
 * Реализовать преобразования из двух предыдущих задач одновременно над одним и тем же файлом.
 * Следует помнить, что:
 * - Списки, отделённые друг от друга пустой строкой, являются разными и должны оказаться в разных параграфах выходного файла.
 *
 */
fun markdownToHtml(inputName: String, outputName: String) {
    TODO()
}

/**
 * Средняя
 *
 * Вывести в выходной файл процесс умножения столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 111):
19935
 *    111
--------
19935
+ 19935
+19935
--------
2212785
 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 * Нули в множителе обрабатывать так же, как и остальные цифры:
235
 *  10
-----
0
+235
-----
2350
 *
 */
fun printMultiplicationProcess(lhv: Int, rhv: Int, outputName: String) {
    TODO()
}


/**
 * Сложная
 *
 * Вывести в выходной файл процесс деления столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 22):
19935 | 22
-198     906
----
13
-0
--
135
-132
----
3

 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 *
 */

fun len(t: Int): Int =    //длина int
    t.toString().length

fun printDivisionProcess(lhv: Int, rhv: Int, outputName: String) {
    if (lhv < rhv) {
        val qw = File(outputName).bufferedWriter()
        if (lhv < 10) {
            qw.write(" $lhv | $rhv")
            qw.newLine()
            qw.write("-0   0")
            qw.newLine()
            qw.write("--")
            qw.newLine()
            qw.write(" $lhv")
        } else {
            qw.write("$lhv | $rhv")
            qw.newLine()
            for (i in 3..len(lhv)) qw.write(" ")
            qw.write("-0   0")
            qw.newLine()
            for (i in 1..len(lhv)) qw.write("-")
            qw.newLine()
            qw.write(lhv.toString())
        }
        qw.close()
        return

    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////
    val mainList = lhv.toString().split("").filter { it != "" }.map { it.toInt() }  //делимое в листе
    val ansString = (lhv / rhv).toString()                      //строчка ответ
    val cycles = ansString.length - 1                           //количество репитов в главном цикле
    val ansList = ansString.split("").filter { it != "" }.map { it.toInt() }    //ответ в листе
    var digit = ansList[0] * rhv                                 //вычитаемое
    var upperDigit = lhv.toString().substring(0, len(digit)).toInt() //то из чего вычитаю на данном этапе
    var number = len(upperDigit)                    //номер сносимой цифры из mainList
    if (upperDigit < digit) {
        upperDigit = upperDigit * 10 + mainList[number]
        number++
    }
    val firstSpace = len(digit) == len(upperDigit)                            //нужен ли первый пробел

//конец прилюдий. приступаем к пожилому шрексу
    val qw = File(outputName).bufferedWriter()
    if (firstSpace) qw.write(" ")  //ну ето тот первый пробел которого не будет при делениее 111 на 9 например
    qw.write("$lhv | $rhv")
    qw.newLine()
    qw.write("-$digit")
    for (i in 1..len(lhv) - len(digit) + 2) qw.write(" ") //пробелы перед ответом
    if (firstSpace) qw.write(" ")
    qw.write(ansString)
    qw.newLine()
    for (i in 1..len(digit) + 1) qw.write("-") //первое подчеркивание
    qw.newLine()
    upperDigit -= digit
    if (firstSpace) qw.write(" ")
    for (i in 1..number - (len(upperDigit))) qw.write(" ") //пожилая арефметика (пробелы перед upperDigit)
    qw.write(upperDigit.toString()) //записываю остаточек сразу тк может быть 0 а условия гавёные
    if (ansString.length > 1) qw.write(mainList[number].toString()) //если это не конец то сношу цифорку
    if (ansString.length > 1) upperDigit =
        upperDigit * 10 + mainList[number] //если это не конец изменяю upperDigit (остаток+снос)
    number++



    for (i in 1..cycles) { //main cycle
        qw.newLine() //111111111111111111111111111111111 вычитаемое
        digit = rhv * ansList[i]
        if (firstSpace && number - len(digit) != 0) qw.write(" ")
        println("$upperDigit - $digit")
        println("number $number")
        for (i in 1..number - len(digit) - 1) qw.write(" ") //пробелы перед digit
        qw.write("-$digit")
        qw.newLine() //222222222222222222222222222222222 подчеркивание
        if (firstSpace && number - len(digit) != 0) qw.write(" ")
        for (i in 1..number - max(len(digit) + 1, len(upperDigit))) qw.write(" ")
        for (i in 1..max(len(digit) + 1, len(upperDigit))) qw.write("-")
        qw.newLine() //333333333333333333333333333333333 результат
        if (firstSpace) qw.write(" ")
        upperDigit -= digit
        for (i in 1..number - len(upperDigit)) qw.write(" ")
        qw.write(upperDigit.toString())
        if (number != len(lhv)) qw.write(mainList[number].toString())
        if (number != len(lhv)) upperDigit = upperDigit * 10 + mainList[number] //условие для конца
        number++


    }
    qw.close()
}

